/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstNoticeService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F04006Dto;
import jp.learningpark.modules.manager.service.F04006Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>配信先既読未読状態確認一覧画面（ニュース）</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/05 : hujunjie: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F04006")
@RestController
public class F04006Controller {
    /**
     * 配信先既読未読状態確認一覧画面（ニュース）Service
     */
    @Autowired
    private F04006Service f04006Service;

    /**
     * お知らせ Service
     */
    @Autowired
    private MstNoticeService mstNoticeService;

    @Autowired
    private MstOrgService mstOrgService;

    /**
     * 初期表示
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer noticeId) {
        //お知らせマスタ取得
        MstNoticeEntity mstNoticeEntity = mstNoticeService.getOne(new QueryWrapper<MstNoticeEntity>()
                .select("id", "org_id", "notice_title", "pub_plan_start_dt", "pub_plan_end_dt").eq("id", noticeId).eq("del_flg", 0));
        //組織マスタ取得
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>()
                .eq("org_id", mstNoticeEntity.getOrgId()).eq("del_flg", 0));
        //組織名
        String orgNm = mstOrgEntity.getOrgNm();
        //掲載日時フォーマット
        String pubPlanStartDt = DateUtils.format(mstNoticeEntity.getPubPlanStartDt(), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
        String pubPlanEndDt = DateUtils.format(mstNoticeEntity.getPubPlanEndDt(), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM);

        return R.ok().put("mstNoticeEntity", mstNoticeEntity).put("orgNm", orgNm)
                .put("pubPlanStartDt", pubPlanStartDt).put("pubPlanEndDt", pubPlanEndDt);
    }

    /**
     * 既読未読集計一覧を取得する
     *
     * @param orgId    組織Id
     * @param noticeId お知らせId
     * @param limit    limit
     * @param page     page
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public R select(String orgId, Integer noticeId, Integer limit, Integer page) {
        //セッションデータ．組織ID
        String sessionOrgId = ShiroUtils.getUserEntity().getOrgId();
        //セッションデータ．ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        List<F04006Dto> showList = new ArrayList<F04006Dto>();
        int total = 0;
        if (StringUtils.equals(orgId, sessionOrgId)) {
            //「組織ID」＝セッションデータ．組織IDの場合
            total = f04006Service.selectCountByOrgId(noticeId);
            showList = f04006Service.selectReadCountByOrgId(noticeId, limit, (page - 1) * 30);
        } else {
            //「組織ID」≠セッションデータ．組織IDの場合
            List<F04006Dto> lowLevList = f04006Service.selectAllLowLevOrgListByOrgId(orgId, brandCd);
            List<String> list = new ArrayList<>();
            for (F04006Dto dto : lowLevList) {
                list.add(dto.getOrgId());
            }
            total = f04006Service.selectCountByLowLevOrgId(list, noticeId);
            showList = f04006Service.selectReadCountByLowLevOrgId(list, noticeId, limit, (page - 1) * 30);
        }
        return R.ok().put("page", new PageUtils(showList, total, limit, page));
    }
}
