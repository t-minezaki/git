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
 * <p>F03002_教科書単元編集画面 Controller</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/25 : gong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F03002")
@RestController
public class F03002Controller extends AbstractController {

    /**
     * F03002_教科書単元編集画面 service
     */
    @Autowired
    private F03002Service f03002Service;

    /**
     * コードマスタ Service
     */
    @Autowired
    private MstCodDService codMstDService;

    /**
     * 学習時期マスタ Service
     */
    @Autowired
    private MstLearnSeasnService mstLearnSeasnService;

    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * <p>初期表示</p>
     *
     * @param textbId 教科書ID
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f03002Init(Integer textbId) {
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));

        //出版社区分リスト
        List<MstCodDEntity> publisherList = codMstDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(wrapper -> wrapper.eq("cod_key", "PUBLISHER_DIV").eq("del_flg", 0)).orderByAsc("sort"));
        if (publisherList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "出版社")).put("org",org);
        }

        //塾学習期間ID
        Integer crmLearnPrdId = (Integer) ShiroUtils.getSessionAttribute(GakkenConstant.CRM_LEARN_PRD_ID);

        //学習時期リスト
        List<MstLearnSeasnEntity> mstLearnSeasnEntities = mstLearnSeasnService.list(new QueryWrapper<MstLearnSeasnEntity>().and(w -> w.eq("crm_learn_prd_id", crmLearnPrdId).eq("del_flg", 0)).orderByAsc("plan_learn_seasn"));
        if (mstLearnSeasnEntities.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "学習時期")).put("org",org);
        }
        for (MstLearnSeasnEntity entity : mstLearnSeasnEntities) {
            //学習時期開始日の表示
            entity.setCretUsrId(DateUtils.format(entity.getLearnSeasnStartDy(), GakkenConstant.DATE_FORMAT_M_D_SLASH + "～"));
        }


        //教科書情報と教科書単元情報を表示するため
        List<F03002Dto> f03002DtoList = f03002Service.getTexdiff(textbId, crmLearnPrdId);
        if (f03002DtoList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科書")).put("org",org);
        }
        for(F03002Dto dto:f03002DtoList){
            dto.setUpdateStr(DateUtils.format(dto.getUpdDatime(),GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        }
        int[] planTmList = new int[]{15,30,45, 60,75, 90,105, 120};

        //教科書の更新日時
        String textbUpdatime=DateUtils.format(f03002DtoList.get(0).getTextbUpdatime(),GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
        return R.ok()
                .put("list", f03002DtoList)
                .put("publisherList", publisherList)
                .put("mstLearnSeasnEntities", mstLearnSeasnEntities)
                .put("planTmList", planTmList)
                .put("org",org)
                .put("textbUpdatime",textbUpdatime);
    }

    /**
     * <p>編集保存ボタン押下</p>
     *
     * @param dtos 画面の情報
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public R f03002edit(@RequestBody List<F03002Dto> dtos) {
        return f03002Service.editFn(dtos);
    }

    /**
     * <p>7．教科書作成ボタン押下</p>
     *
     * @param dtos 画面の情報
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public R f03002create(@RequestBody List<F03002Dto> dtos) {
        return f03002Service.createFn(dtos);
    }
}

