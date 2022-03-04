/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dto.F30112Dto;
import jp.learningpark.modules.guard.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>塾からの連絡通知一覧画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/07 : hujunjie: 新規<br />
 * @version 1.0
 * 2019/07/30 : hujiaxing: 改修<br />
 */
@RestController
@RequestMapping("guard/F30112/")
public class F30112Controller {
    /**
     * 塾からの連絡通知一覧画面 Service
     */
    @Autowired
    F30112Service f30112Service;
    /**
     * 塾からのイベント情報一覧画面 Service
     */
    @Autowired
    F30401Service f30401Service;

    /**
     * コードマスタ Service
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     * 保護者お知らせ閲覧状況 Service
     */
    @Autowired
    GuardReadingStsService guardReadingStsService;

    /**
     * 保護者指導報告閲覧状況 Service
     */
    @Autowired
    GuidReprApplyStsService guidReprApplyStsService;
    /**
     * お知らせ Service
     */
    @Autowired
    MstNoticeService mstNoticeService;

    /**
     * F30110Service Service
     */
    @Autowired
    F30110Service f30110Service;


    @Autowired
    private F30419Service f30419Service;

	/**
     * mstDeviceTokenDao
     */
    @Autowired
    MstDeviceTokenService mstDeviceTokenService;
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
     * 生徒基本マスタService
     */
    @Autowired
    MstStuService mstStuService;
    /**
     * f30421Service
     */
    @Autowired
    F30421Service f30421Service;
    @Value("${ans-url.token}")
    String token;
    @Autowired
    CommonService commonService;

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初期表示
     *
     * @param limit limit
     * @param page page
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page, String url, String stuId) {
        //セッション・生徒Id
        if (StringUtils.isEmpty(stuId)){
            stuId = (String)ShiroUtils.getSessionAttribute("stuId");
        }else{
            ShiroUtils.setSessionAttribute(GakkenConstant.STU_ID, stuId);
        }
        //セッション・保護者Id
        String guardId = ShiroUtils.getUserId();
        //セッション・生徒組織Id
        String orgId = (String)ShiroUtils.getSessionAttribute("orgId");
        //コードマスタ取得
        MstCodDEntity mstCodDEntity = mstCodDService.getOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value_2").eq("cod_key", "LOCAL_IMG").eq("cod_value", orgId).eq("del_flg", 0));
        String imgPath = "";
        //コードマスタ・画像パス
        if (mstCodDEntity != null) {
            imgPath = mstCodDEntity.getCodValue2();
        } else {
            imgPath = "../img/logo2.png";
        }
        //お知らせの件数の取得
        Integer noticeCount = f30421Service.getNoticeCount(orgId, guardId, stuId);
        //イベントの件数の取得
        Integer eventCount = f30421Service.getEventCount(guardId, stuId);
        //マナミルチャンネル件数の取得
        Integer channelCount = f30419Service.selectUnreadCount(guardId, orgId,stuId);

        //表示するデータ(お知らせ+イベント) 現在のページ
        List<F30112Dto> showList = f30112Service.getNotices(stuId, guardId, orgId, limit, (page - 1) * limit);

        //お知らせデータ数取得  すべて
        Integer total = f30112Service.getNoticeCount(stuId, guardId, orgId);
        R r = R.ok();
        return r.put("showList", showList).put("total", total).put("noticeCount", noticeCount + eventCount).put("channelCount", channelCount).put("imgPath", imgPath);
    }
}
