package jp.learningpark.modules.guard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.GuardReadingStsService;
import jp.learningpark.modules.common.service.GuidReprApplyStsService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstNoticeService;
import jp.learningpark.modules.guard.dto.F30419Dto;
import jp.learningpark.modules.guard.service.F30112Service;
import jp.learningpark.modules.guard.service.F30419Service;
import jp.learningpark.modules.guard.service.F30421Service;
import jp.learningpark.modules.xapi.Activitys;
import jp.learningpark.modules.xapi.Extensions;
import jp.learningpark.modules.xapi.Verbs;
import jp.learningpark.modules.xapi.XApiConstant;
import jp.learningpark.modules.xapi.XApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * F30419Controller
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/18 ： NWT)hxl ： 新規作成
 * @date 2020/02/18 14:26
 */
@RestController
@RequestMapping("guard/F30419/")
public class F30419Controller {
    /**
     * チャンネル一覧画面 Service
     */
    @Autowired
    F30419Service f30419Service;

    /**
     * お知らせ一覧画面 Service
     */
    @Autowired
    F30421Service f30421Service;

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
    @Autowired
    F30112Service f30112Service;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初期表示
     *
     * @param limit limit
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R init(Integer limit, String url) {
        //セッション・生徒Id
        String stuId = (String) ShiroUtils.getSessionAttribute("stuId");
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
        // 未読チャンネルの総数を取得し、画面で表示される。
        Integer count1 = f30419Service.selectUnreadCount(guardId, orgId,stuId);

        //表示するデータ(チャンネル イベント)
        List<F30419Dto> showList = f30419Service.selectNews(guardId, orgId, stuId,(limit - GakkenConstant.NOTICE_PAGE_SIZE) ,GakkenConstant.NOTICE_PAGE_SIZE);
        Integer dataCount = f30419Service.selectCount(guardId, orgId,stuId);

        //チャンネルの総数を取得し、画面で表示される。
        Integer total = f30419Service.selectNewsCount(guardId, orgId,stuId);

        //コード最適化
        for (F30419Dto dto : showList) {
            dto.setStartTime(dto.getStartTime().substring(0, 16).replace("-", "/"));
        }

        //ビッグデータ
        Extensions exts = new Extensions();
        //利用者のシステムID
        exts.put(XApiConstant.EXT_KEY_USER_ID, ShiroUtils.getUserId());
        //当画面URL
        exts.put(XApiConstant.EXT_KEY_URL, url);
        //当画面訪問時間
        exts.put(XApiConstant.EXT_KEY_VISIT_TIME, DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS));
        //塾ニュースＩＤリスト
//        exts.put(XApiConstant.EXT_KEY_NOTICE_ID, idList);
        //生徒ＩＤ
        exts.put(XApiConstant.EXT_KEY_STU_ID, stuId);
        try {
            XApiUtils.saveStatement(Verbs.launched(), Activitys.academy(), exts);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return R.ok().put("showList", showList).put("total", total).put("count", noticeCount + eventCount).put("count1", count1).put("imgPath",imgPath).put("dataCount",dataCount);
    }
}
