package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstMessageEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.TalkReadingStsEntity;
import jp.learningpark.modules.common.service.MstMessageService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.TalkReadingStsService;
import jp.learningpark.modules.manager.service.F21068Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/22 ： NWT)hxl ： 新規作成
 * @date 2020/05/22 17:22
 */
@RequestMapping("/manager/F21068")
@RestController
public class F21068Controller {

    @Autowired
    F21068Service f21068Service;
    /**
     * 組織マスタ Service
     */
    @Autowired
    MstOrgService mstOrgService;

    /**
     * mstMessageService
     */
    @Autowired
    MstMessageService mstMessageService;

    /**
     * talkReadingStsService
     */
    @Autowired
    TalkReadingStsService talkReadingStsService;

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
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", ShiroUtils.getUserEntity().getOrgId())));
        if (org != null) {
            orgNm = org.getOrgNm();
        }
        R r = f21068Service.getDetail(ShiroUtils.getUserEntity().getOrgId(), null, null, null, null, null, limit, page);
        return r.put("sOrgId", ShiroUtils.getUserEntity().getOrgId()).put("orgNm", orgNm);
    }

    /**
     * 検索ボタン押下
     *
     * @param orgId          組織ＩＤ
     * @param id             メッセージID
     * @param messageTitle   件名
     * @param pubPlanStartDt 掲載開始日時
     * @param pubPlanEndDt   掲載終了日時
     * @param limit          １ページ最大件数
     * @param page           現在のページ数
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public R search(String orgId, Integer id, String messageTitle, String pubPlanStartDt, String pubPlanEndDt, Integer limit, Integer page) {
        if (pubPlanStartDt != null && !StringUtils.isEmpty(pubPlanStartDt) && pubPlanEndDt != null && !StringUtils.isEmpty(pubPlanEndDt)) {
            //掲載開始日時
            String pubPlanStartDtCompare = DateUtils.format(DateUtils.parse(pubPlanStartDt, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            //掲載終了日時
            String pubPlanEndDtCompare = DateUtils.format(DateUtils.parse(pubPlanEndDt, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            //比較日の大きさ
            if (pubPlanStartDtCompare.compareTo(pubPlanEndDtCompare) > 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0024", "掲載開始日時", "掲載終了日時"));
            }
            return f21068Service.getDetail(ShiroUtils.getUserEntity().getOrgId(), orgId, id, messageTitle, DateUtils.parse(pubPlanStartDt, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM), DateUtils.parse(pubPlanEndDt, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM), limit, page);
        } else {
            if (StringUtils.isEmpty(pubPlanStartDt)) {
                if (!StringUtils.isEmpty(pubPlanEndDt)) {
                    return f21068Service.getDetail(ShiroUtils.getUserEntity().getOrgId(), orgId, id, messageTitle, null, DateUtils.parse(pubPlanEndDt, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM), limit, page);
                } else {
                    return f21068Service.getDetail(ShiroUtils.getUserEntity().getOrgId(), orgId, id, messageTitle, null, null, limit, page);
                }
            } else {
                if (StringUtils.isEmpty(pubPlanEndDt)) {
                    return f21068Service.getDetail(ShiroUtils.getUserEntity().getOrgId(), orgId, id, messageTitle, DateUtils.parse(pubPlanStartDt, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM), null, limit, page);
                }
            }
        }
        return R.ok();
    }

    /**
     * 削除リンク押下
     *
     * @param id           メッセージＩＤ
     * @param updDatimeStr 最新更新日時
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R f21062Delete(Integer id, String updDatimeStr) {
        //メッセージ．IDの取得
        MstMessageEntity mstMessageEntity = mstMessageService.getById(id);
        //当メッセージ関連するすべての面談メッセージ閲覧状況から該当データを論理削除する
        if (mstMessageEntity != null && DateUtils.format(mstMessageEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS).equals(updDatimeStr)) {
            //メッセージ閲覧状況．メッセージIDの取得
            List<TalkReadingStsEntity> talkReadingStsEntityList = talkReadingStsService.list(new QueryWrapper<TalkReadingStsEntity>().eq("message_id", id));
            //mstMessage削除
            mstMessageEntity.setDelFlg(1);
            mstMessageEntity.setUpdDatime(DateUtils.getSysTimestamp());
            mstMessageEntity.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
            mstMessageService.updateById(mstMessageEntity);
            // 2021/1/4 huangxinliang modify start
            threadPoolTaskExecutor.execute(() -> {
                //メッセージ閲覧状況削除
                TalkReadingStsEntity talkReadingStsEntity = new TalkReadingStsEntity();
                talkReadingStsEntity.setDelFlg(1);
                talkReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
                talkReadingStsEntity.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
                talkReadingStsService.update(talkReadingStsEntity, new QueryWrapper<TalkReadingStsEntity>()
                        .in("id", talkReadingStsEntityList.stream()
                                .map(TalkReadingStsEntity::getId)
                                .collect(Collectors.toList())));
            });
            // 2021/1/4 huangxinliang modify end
            return R.ok(MessageUtils.getMessage("MSGCOMN0022", "削除"));
        } else {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }

    }
}
