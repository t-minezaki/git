/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstLearnSeasnEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstLearnSeasnService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dao.F03001Dao;
import jp.learningpark.modules.manager.dto.F03002Dto;
import jp.learningpark.modules.manager.service.F03002Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * F00002 F02002_単元情報検索一覧画面
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/20 : xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/f03006")
@RestController
public class F03006Controller extends AbstractController {

    /**
     * F03002_教科書単元編集画面 service
     */
    @Autowired
    private F03002Service f03002Service;

    /**
     * 学習時期マスタ Service
     */
    @Autowired
    private MstLearnSeasnService mstLearnSeasnService;

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
     * 教科書一覧Service
     */
    @Autowired
    private F03001Dao f03001Dao;

    /**
     * 初期化
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R initial(String schy) {
        R info = new R();
        // 学年区分を取得
        List<MstCodDEntity> schyDiv = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "SCHY_DIV"));
        // 教科区分を取得
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
        List<MstCodDEntity> subjtDiv = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "SUBJT_DIV").eq("cod_value_4", "0").orderByAsc(orderRule));
        // 出版社区分を取得
        List<MstCodDEntity> publisherDiv = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "PUBLISHER_DIV"));
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 組織
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));

        info.put("mstOrgEntity", mstOrgEntity);
        info.put("schyDiv", schyDiv);
        info.put("subjtDiv", subjtDiv);
        info.put("publisherDiv", publisherDiv);
        int[] planTmList = new int[] {15, 30, 45, 60, 75, 90, 105, 120};
        info.put("planTmList", planTmList);
        // 塾学習期間IDの取得
        Integer crmLearnPrdId = f03001Dao.selectMstCrmschLearnPrdIdToImport(schy, orgId, ShiroUtils.getBrandcd());
        if (crmLearnPrdId == null) {
            return info.put("crmLearnPrdIdNull", MessageUtils.getMessage("MSGCOMN0123", "塾時期"));
        }
        //学習時期リスト
        List<MstLearnSeasnEntity> mstLearnSeasnEntities = mstLearnSeasnService.list(
                new QueryWrapper<MstLearnSeasnEntity>().and(w->w.eq("crm_learn_prd_id", crmLearnPrdId).eq("del_flg", 0)).orderByAsc("plan_learn_seasn"));
        if (mstLearnSeasnEntities.size() == 0) {
            return info.put("mstLearnSeasnEntitiesNull", MessageUtils.getMessage("MSGCOMN0123", "塾時期"));
        }
        for (MstLearnSeasnEntity entity : mstLearnSeasnEntities) {
            //学習時期開始日の表示
            entity.setCretUsrId(DateUtils.format(entity.getLearnSeasnStartDy(), GakkenConstant.DATE_FORMAT_M_D_SLASH + "～"));
        }
        info.put("mstLearnSeasnEntities", mstLearnSeasnEntities);
        return info;
    }

    /**
     * <p>教科書作成ボタン押下</p>
     *
     * @param dtos 画面の情報
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public R createUnit(@RequestBody List<F03002Dto> dtos) {
        return f03002Service.createFn(dtos);
    }
}
