/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dao.F02002Dao;
import jp.learningpark.modules.manager.dto.F02002Dto;
import jp.learningpark.modules.manager.dto.F02002UnitDto;
import jp.learningpark.modules.manager.service.F02002Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * F00002 F02002_単元情報検索一覧画面
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/20 : xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/f02002")
@RestController
public class F02002Controller extends AbstractController {

    /**
     * コードマスタ_明細
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * 組織マスタ
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * F02002_単元情報検索一覧画面 Dao
     */
    @Autowired
    F02002Dao f02002Dao;

    /**
     * F02002_単元情報検索一覧画面 Service
     */
    @Autowired
    private F02002Service f02002Service;

    /**
     * 単元情報検索一覧を表示するため init
     * @param   page
     * @param   limit
     * @return  初期情報
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R initial(Integer page, Integer limit) {
        R info = new R();

        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // ブランドコード
        String brandCd = ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_BRAND_CD).toString();
        // 組織
        MstOrgEntity mstOrgEntity =
                mstOrgService.getOne(new QueryWrapper<MstOrgEntity>()
                        .and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        info.put("mstOrgEntity", mstOrgEntity);

        // 全部の下層組織
        List<MstOrgEntity> lowerOrgId = f02002Service.getLowerOrg(orgId);
        // 全部の上層組織
        List<MstOrgEntity> upOrgId = f02002Service.getUpOrg(orgId);
        // 本組織及全部の下層組織、上層組織リストを取得する
        upOrgId.addAll(lowerOrgId);
        info.put("orgAll",upOrgId);
        // 本組織及び上下層組織リストの取得
        List<F02002Dto> orgList = f02002Dao.getOrgList(orgId,brandCd);
        info.put("orgList", orgList);
        if (orgList.isEmpty()){
            return R.error(MessageUtils.getMessage("MSGCOMN0017","組織マスタ"));
        }
        // 学年区分を取得
        List<MstCodDEntity> schyDiv = mstCodDService.list(new QueryWrapper<MstCodDEntity>()
                .select("cod_cd","cod_value").eq("cod_key","SCHY_DIV").eq("del_flg",0).orderByAsc("sort"));
        if (schyDiv.isEmpty()){
            return R.error(MessageUtils.getMessage("MSGCOMN0017","学年")).put("mstOrgEntity", mstOrgEntity).put("orgAll",upOrgId);
        }
        info.put("schyDiv",schyDiv);
        String orderRule = "case\n" +
                "            WHEN cod_cd = 'k1' then 1\n" +
                "            WHEN cod_cd = 'm1' or cod_cd = 'm2' then 2\n" +
                "            WHEN cod_cd = 'r1' then 3\n" +
                "            WHEN cod_cd = 's4' then 4\n" +
                "            WHEN cod_cd = 'e1' then 5\n" +
                "            WHEN cod_cd = 's5' then 6\n" +
                "            WHEN cod_cd = 'r2' then 7\n" +
                "            WHEN cod_cd = 'r3' then 8\n" +
                "            WHEN cod_cd = 'r4' then 9\n" +
                "            WHEN cod_cd = 'r5' then 10\n" +
                "            WHEN cod_cd = 's1' then 11\n" +
                "            WHEN cod_cd = 's2' then 12\n" +
                "            WHEN cod_cd = 's3' then 13\n" +
                "            WHEN cod_cd = 't1' then 14\n" +
                "            WHEN cod_cd = 'o1' then 15\n" +
                "            WHEN cod_cd = 'z1' then 16 end";
        // 教科区分を取得
        List<MstCodDEntity> subjtDiv = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "SUBJT_DIV").eq("cod_value_4", "0").eq("del_flg", 0).orderByAsc(orderRule));
        if (subjtDiv.isEmpty()){
            return R.error(MessageUtils.getMessage("MSGCOMN0017","教科")).put("mstOrgEntity", mstOrgEntity).put("orgAll",upOrgId).put("schyDiv",schyDiv);
        }
        info.put("subjtDiv",subjtDiv);



        List<String> orgIdList = new LinkedList<>();
        for (F02002Dto f02002Dto:orgList
             ) {
                orgIdList.add(f02002Dto.getOrgId());
        }
        // 単元情報検索一覧を表示するため
        List<F02002UnitDto> unitList = f02002Dao.getUnitList(orgId,(page-1)*limit,limit,orgIdList);
        if (unitList.isEmpty()){
            return R.error(MessageUtils.getMessage("MSGCOMN0017","単元")).put("mstOrgEntity", mstOrgEntity).put("orgAll",upOrgId).put("schyDiv",schyDiv).put("subjtDiv",subjtDiv);
        }
        // updatetime format
        for (F02002UnitDto f02002UnitDto:unitList
        ) {
            f02002UnitDto.setUpdDatimeFormat(DateUtils.format(f02002UnitDto.getUpdDatime(),Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        }
        // 単元情報検索一覧総数
        Integer total = f02002Dao.getUnitListCount(orgId,orgIdList);
        info.put("page", new PageUtils(unitList,total,limit,page));
        return info;
    }

    /**
     * 単元情報検索一覧を表示するため search
     * @param page
     * @param limit
     * @param schyDiv  画面．検索条件．学年区分
     * @param subjtDiv 画面．検索条件．教科
     * @param orgId    画面．検索条件．組織ID
     * @param unitNm   画面．検索条件．単元名
     * @return         検索情報
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public R searchUnit(Integer page, Integer limit, String schyDiv, String subjtDiv,
                         String orgId, String unitNm) {
        // 組織ID
        String currentOrgId = ShiroUtils.getUserEntity().getOrgId();
        // ブランドコード
        String brandCd = ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_BRAND_CD).toString();
        List<String> orgIdList = new LinkedList<>();
        if (orgId == null || StringUtils.equals("",orgId)){
            // 本組織及び上下層組織リストの取得
            List<F02002Dto> orgList = f02002Dao.getOrgList(currentOrgId,brandCd);
            if (orgList.isEmpty()){
                return R.error(MessageUtils.getMessage("MSGCOMN0017","組織マスタ"));
            }
            for (F02002Dto f02002Dto:orgList
            ) {
                    orgIdList.add(f02002Dto.getOrgId());
            }
        }else {
            orgIdList.add(orgId);
        }
        // 単元情報検索一覧を表示するため
        List<F02002UnitDto> unitList = f02002Dao.searchUnitList(currentOrgId,(page-1)*limit,limit,orgIdList,schyDiv,subjtDiv,StringUtils.trim(unitNm));
        if (unitList.isEmpty()){
            return R.error(MessageUtils.getMessage("MSGCOMN0017","単元"));
        }
        // updatetime format
        for (F02002UnitDto f02002UnitDto:unitList
        ) {
            f02002UnitDto.setUpdDatimeFormat(DateUtils.format(f02002UnitDto.getUpdDatime(),Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        }
        // 単元情報検索一覧総数
        Integer total = f02002Dao.searchUnitListCount(orgIdList,schyDiv,subjtDiv,StringUtils.trim(unitNm));
        return R.ok().put("page", new PageUtils(unitList,total,limit,page));
    }

    /**
     * 単元マスタから論理削除
     * @param unitId      単元マスタ．ＩＤ
     * @param updateTime  排他
     * @return            削除情報
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R deleteUnit(Integer unitId,String updateTime) {
        return f02002Service.deleteUnit(unitId,updateTime) ;
    }
}