package jp.learningpark.modules.guard.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dao.F30112Dao;
import jp.learningpark.modules.guard.dto.F30411Dto;
import jp.learningpark.modules.guard.service.F30411Service;
import jp.learningpark.modules.manager.service.F21017Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * F30412_遅刻・欠席連絡画面
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/18 ： NWT)hxl ： 新規作成
 * @date 2019/11/18 10:00
 */
@RestController
@RequestMapping("/guard/F30411")
public class F30411Controller {
    /**
     * お知らせService
     */
    @Autowired
    MstNoticeService mstNoticeService;
    /**
     * お知らせ配信先Service
     */
    @Autowired
    MstNoticeDeliverService mstNoticeDeliverService;
    /**
     * セッションデータService
     */
    @Autowired
    MstOrgService mstOrgService;
    /**
     * guardReadingStsService
     */
    @Autowired
    GuardReadingStsService guardReadingStsService;
    /**
     * lateAbsHstService
     */
    @Autowired
    LateAbsHstService lateAbsHstService;
    /**
     * F30411Service
     */
    @Autowired
    F30411Service f30411Service;

    /**
     * mstDeviceTokenService
     */
    @Autowired
    MstDeviceTokenService mstDeviceTokenService;

    /**
     * f30112Dao
     */
    @Autowired
    F30112Dao f30112Dao;

    /**
     * mstCodDService
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     * mstStuService
     */
    @Autowired
    MstStuService mstStuService;

    /**
     * mstGuardService
     */
    @Autowired
    MstGuardService mstGuardService;

    /**
     * noticeApiService
     */
    @Autowired
    NoticeApiService noticeApiService;

    /**
     * commonService
     */
    @Autowired
    CommonService commonService;
    /**
     * f21017Service
     */
    @Autowired
    F21017Service f21017Service;
    /**
     * ユーザ基本マスタ Service
     */
    @Autowired
    MstUsrService mstUsrService;

    /**
     * 休暇タイプと休暇理由を取得する
     *
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R getReasons() {
        R info = R.ok();
        //休暇タイプ
        List<F30411Dto> contents = f30411Service.getContents();
        info.put("contents", contents);
        //休暇理由
        List<F30411Dto> reasons = f30411Service.getReasons();
        info.put("reasons", reasons);
        return info;
    }

    /**
     * 遅刻欠席連絡履歴の登録処理を行う
     *
     * @param type     遅欠連絡ステータス
     * @param lateTime 遅刻時間(分)
     * @param lateDay  対象年月日
     * @param reason   遅欠理由
     * @param remark   備考
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public R submit(String type, Integer lateTime, String lateDay, String reason, String remark) {
        R info = f30411Service.submit(type, lateTime, lateDay, reason, remark);
        R r = f30411Service.sendMessage(info.get("stuId").toString(),(MstNoticeEntity) info.get("notice"));
        info.put("deviceIdList",r.get("deviceIdList")).put("deviceListGuard",r.get("deviceListGuard"));
        return info;
    }
}