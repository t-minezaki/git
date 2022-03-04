/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dto.F03004Dto;
import jp.learningpark.modules.pop.service.F03004Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F03002_教科書単元編集画面 Controller</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/01/14 : gong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F03004")
public class F03004Controller extends AbstractController {

    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * F03002_教科書単元編集画面 Service
     */
    @Autowired
    private F03004Service f03004Service;

    /**
     * コードマスタ_明細 Service
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * 共通処理 Service
     */
    @Autowired
    private CommonService commonService;

    /**
     * <p>初期表示と検索</p>
     *
     * @param schyDiv  学年
     * @param subjtDiv 教科
     * @param orgId    組織ID
     * @param unitNm   単元名
     * @param sectnNm  節名
     * @param chaptNm  章名
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f03004Init(String schyDiv, String subjtDiv, String orgId, String unitNm, String sectnNm, String chaptNm, Integer limit, Integer page) {
        List<OrgAndLowerOrgIdDto> orgEntityList = null;
        List<F03004Dto> unitEntityList = null;
        String schy = "";
        String subjt = "";
        MstCodDEntity mstCodDEntity = null;
        Integer total = 0;
        if (StringUtils.isEmpty(orgId)) {
            orgId = ShiroUtils.getUserEntity().getOrgId();
            //本組織の情報
            MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", ShiroUtils.getUserEntity().getOrgId()).eq("del_flg", 0)));
            //学年
            mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_cd", schyDiv).eq("cod_key", "SCHY_DIV").eq("del_flg", 0));
            if (mstCodDEntity != null) {
                schy = mstCodDEntity.getCodValue();
            }
            //教科
            mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_cd", subjtDiv).eq("cod_key", "SUBJT_DIV").eq("del_flg", 0));
            if (mstCodDEntity != null) {
                subjt = mstCodDEntity.getCodValue();
            }

            //本組織及上下層組織リストの取得
            orgEntityList = commonService.getAllOrgList(orgId);
            if (orgEntityList.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "組織マスタ")).put("org", org).put("schy",schy).put("subjt",subjt);
            }

            //単元情報
            unitEntityList = f03004Service.getUnitList(schyDiv, subjtDiv, sectnNm, chaptNm, orgId, unitNm, limit, (page - 1) * limit);
            if (unitEntityList.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "単元")).put("orgList", orgEntityList).put("org", org).put("schy",schy).put("subjt",subjt);
            }
            total = f03004Service.getUnitCount(schyDiv, subjtDiv, sectnNm, chaptNm, orgId, unitNm);
            return R.ok().put("page", new PageUtils(unitEntityList, total, limit, page)).put("orgList", orgEntityList).put("org", org).put("schy",schy).put("subjt",subjt);
        } else {
            unitEntityList = f03004Service.getUnitList(schyDiv, subjtDiv, sectnNm, chaptNm, orgId, unitNm, limit, (page - 1) * limit);
            if (unitEntityList.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "単元"));
            }
            total = f03004Service.getUnitCount(schyDiv, subjtDiv, sectnNm, chaptNm, orgId, unitNm);
        }

        return R.ok().put("page", new PageUtils(unitEntityList, total, limit, page));

    }
}
