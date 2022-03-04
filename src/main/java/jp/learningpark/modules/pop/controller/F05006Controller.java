/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstNoticeService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.pop.dto.F04007Dto;
import jp.learningpark.modules.pop.service.F04007Service;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>保護者既読未読詳細一覧画面（知らせ）</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/06 : xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/pop/F05006")
@RestController
public class F05006Controller {

    /**
     * f04007Service
     */
    @Autowired
    private F04007Service f04007Service;

    /**
     * お知らせ Service
     */
    @Autowired
    private MstNoticeService mstNoticeService;

    /**
     * 組織 Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * お知らせ配信先、組織情報、保護者お知らせ閲覧状況より、取得する。
     * @param page ページ
     * @param limit '1ページのMAX件数30件
     * @param paramOrgId 保護者お知らせ閲覧状況．組織ID
     * @param noticeId お知らせId
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer page, Integer limit, @Param("paramOrgId") String paramOrgId, @Param("noticeId") Integer noticeId, @Param("readFlg") String readFlg){
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 組織名
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("del_flg", 0).eq("org_id",orgId)));
        // 保護者お知らせ閲覧状況．組織名
        String guardNoticeOrgNm = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("del_flg", 0).eq("org_id",paramOrgId))).getOrgNm();
        String orgNm = org.getOrgNm();
        // お知らせマスタ取得
        MstNoticeEntity mstNoticeEntity = mstNoticeService.getOne(new QueryWrapper<MstNoticeEntity>()
                .select("org_id", "notice_title", "pub_plan_start_dt", "pub_plan_end_dt").eq("id", noticeId).eq("del_flg", 0));
        // 掲載日時フォーマット
        String pubPlanStartDt = DateUtils.format(mstNoticeEntity.getPubPlanStartDt(), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
        String pubPlanEndDt = DateUtils.format(mstNoticeEntity.getPubPlanEndDt(), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
        // 情報の総数
        Integer total = f04007Service.selectGuardCount(noticeId,paramOrgId,readFlg);
        // 保護者お知らせ閲覧状況より、取得する。
        List<F04007Dto> readSum = f04007Service.selectGuardStuById(noticeId,paramOrgId,(page-1)*limit,readFlg,limit);
        return R.ok().put("mstNoticeEntity", mstNoticeEntity)
                .put("pubPlanStartDt", pubPlanStartDt)
                .put("pubPlanEndDt", pubPlanEndDt)
                .put("page",new PageUtils(readSum,total,limit,page))
                .put("orgId",orgId)
                .put("orgNm",orgNm)
                .put("guardNoticeOrgNm",guardNoticeOrgNm);
    }

}
