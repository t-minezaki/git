/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.entity.MstNoticeDeliverEntity;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.GuardReadingStsService;
import jp.learningpark.modules.common.service.MstNoticeDeliverService;
import jp.learningpark.modules.common.service.MstNoticeService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F04001Dto;
import jp.learningpark.modules.manager.service.F04001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>F04001_塾ニュース一覧画面</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/03/12 : tan: 新規<br />
 * @version 1.0
 */

@RequestMapping("/manager/F04001")
@RestController
public class F04001Controller {

    /**
     * 塾ニュース一覧画面
     */
    @Autowired
    private F04001Service f04001Service;

    /**
     * お知らせ Service
     */
    @Autowired
    MstNoticeService mstNoticeService;

    /**
     * お知らせ配信先 Service
     */
    @Autowired
    MstNoticeDeliverService mstNoticeDeliverService;

    /**
     * 保護者お知らせ閲覧状況 Service
     */
    @Autowired
    GuardReadingStsService guardReadingStsService;

    /**
     * 組織マスタ Service
     */
    @Autowired
    MstOrgService mstOrgService;

    // 2021/1/4 huangxinliang modify start
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    // 2021/1/4 huangxinliang modify end

    /**
     * 初期化
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page) {
        // 組織名
        String orgNm = "";
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", ShiroUtils.getUserEntity().getOrgId())));
        if (org != null) {
            orgNm = org.getOrgNm();
        }
        R r = getDetail(ShiroUtils.getUserEntity().getOrgId(), null, null, null, null, null, limit, page);

        return r.put("sOrgId", ShiroUtils.getUserEntity().getOrgId()).put("orgNm", orgNm);
    }

    /**
     * 検索ボタン押下
     *
     * @param orgId 組織ＩＤ
     * @param id ニュースID
     * @param noticeTitle 件名
     * @param pubPlanStartDt 掲載開始日時
     * @param pubPlanEndDt 掲載終了日時
     * @param limit １ページ最大件数
     * @param page 現在のページ数
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public R search(String orgId, Integer id, String noticeTitle, String pubPlanStartDt, String pubPlanEndDt, Integer limit, Integer page) {
        if (pubPlanStartDt != null && !StringUtils.isEmpty(pubPlanStartDt) && pubPlanEndDt != null && !StringUtils.isEmpty(pubPlanEndDt)) {
            //掲載開始日時
            String pubPlanStartDtCompare = DateUtils.format(
                    DateUtils.parse(pubPlanStartDt, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            //掲載終了日時
            String pubPlanEndDtCompare = DateUtils.format(
                    DateUtils.parse(pubPlanEndDt, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            //比較日の大きさ
            if (pubPlanStartDtCompare.compareTo(pubPlanEndDtCompare) > 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0024", "掲載開始日時", "掲載終了日時"));
            }
            return getDetail(
                    ShiroUtils.getUserEntity().getOrgId(), orgId, id, noticeTitle, DateUtils.parse(pubPlanStartDt, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM),
                    DateUtils.parse(pubPlanEndDt, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM), limit, page);
        } else {
            if (StringUtils.isEmpty(pubPlanStartDt)) {
                if (!StringUtils.isEmpty(pubPlanEndDt)) {
                    return getDetail(
                            ShiroUtils.getUserEntity().getOrgId(), orgId, id, noticeTitle, null,
                            DateUtils.parse(pubPlanEndDt, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM), limit, page);
                } else {
                    return getDetail(ShiroUtils.getUserEntity().getOrgId(), orgId, id, noticeTitle, null, null, limit, page);
                }
            } else {
                if (StringUtils.isEmpty(pubPlanEndDt)) {
                    return getDetail(
                            ShiroUtils.getUserEntity().getOrgId(), orgId, id, noticeTitle,
                            DateUtils.parse(pubPlanStartDt, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM), null, limit, page);
                }
            }
        }
        return R.ok();
    }

    /**
     * 削除リンク押下
     *
     * @param id ニュースＩＤ
     * @param updDatimeStr 最新更新日時
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R f04001Delete(Integer id, String updDatimeStr) {
        //お知らせ．IDの取得
        MstNoticeEntity mstNoticeEntity = mstNoticeService.getById(id);
        //お知らせ配信先．お知らせIDの取得
        List<MstNoticeDeliverEntity> mstNoticeDeliverEntityList = mstNoticeDeliverService.list(new QueryWrapper<MstNoticeDeliverEntity>().eq("notice_id", id));
        //保護者お知らせ閲覧状況．IDの取得
        List<GuardReadingStsEntity> guardReadingStsEntityList = guardReadingStsService.list(new QueryWrapper<GuardReadingStsEntity>().eq("notice_id", id));
        //排他チェックエラーの場合、処理を中断し
        if (mstNoticeEntity != null && DateUtils.format(mstNoticeEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS).equals(updDatimeStr)) {
            mstNoticeEntity.setDelFlg(1);
            mstNoticeEntity.setUpdDatime(DateUtils.getSysTimestamp());
            mstNoticeEntity.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
            mstNoticeService.updateById(mstNoticeEntity);
            // 2021/1/4 huangxinliang modify start
            threadPoolTaskExecutor.execute(() -> {
                //MstNoticeDeliver削除
                MstNoticeDeliverEntity mstNoticeDeliverEntity = new MstNoticeDeliverEntity();
                mstNoticeDeliverEntity.setDelFlg(1);
                mstNoticeDeliverEntity.setUpdDatime(DateUtils.getSysTimestamp());
                mstNoticeDeliverEntity.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
                mstNoticeDeliverService.update(mstNoticeDeliverEntity, new UpdateWrapper<MstNoticeDeliverEntity>()
                        .in("id", mstNoticeDeliverEntityList.stream()
                                .map(MstNoticeDeliverEntity::getId)
                                .collect(Collectors.toList())));
                //guardReadingSts削除
                GuardReadingStsEntity guardReadingStsEntity = new GuardReadingStsEntity();
                guardReadingStsEntity.setDelFlg(1);
                guardReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
                guardReadingStsEntity.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
                guardReadingStsService.update(guardReadingStsEntity,new UpdateWrapper<GuardReadingStsEntity>()
                        .in("id", guardReadingStsEntityList.stream()
                                .map(GuardReadingStsEntity::getId)
                                .collect(Collectors.toList())));
            });
            // 2021/1/4 huangxinliang modify end
        } else {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        return R.ok();
    }

    /**
     * 初期化
     *
     * @param orgId 組織ＩＤ
     * @param id ニュースID
     * @param noticeTitle 件名
     * @param pubPlanStartDt 掲載開始日時
     * @param pubPlanEndDt 掲載終了日時
     * @param limit １ページ最大件数
     * @param page 現在のページ数
     * @return
     */
    private R getDetail(String sOrgId, String orgId, Integer id, String noticeTitle, Date pubPlanStartDt, Date pubPlanEndDt, Integer limit, Integer page) {
        List<F04001Dto> list = f04001Service.getSearchOrg(sOrgId, orgId, id, noticeTitle, pubPlanStartDt, pubPlanEndDt, limit, (page - 1) * limit);
        //総件数をとる
        Integer total = f04001Service.getTotalCount(sOrgId, orgId, id, noticeTitle, pubPlanStartDt, pubPlanEndDt);
        if (list.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "掲示板"));
        }
        for (F04001Dto dto : list) {
            dto.setUpdDatimeStr(DateUtils.format(dto.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        }
        return R.ok().put("page", new PageUtils(list, total, limit, page));
    }

}