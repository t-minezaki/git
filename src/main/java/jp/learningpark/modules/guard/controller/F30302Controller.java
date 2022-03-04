package jp.learningpark.modules.guard.controller;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.xapi.Activitys;
import jp.learningpark.modules.xapi.Extensions;
import jp.learningpark.modules.xapi.Verbs;
import jp.learningpark.modules.xapi.XApiConstant;
import jp.learningpark.modules.xapi.XApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>F30302_他サイトニュース詳細表示画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/05/21 : xiong: 新規<br />
 */
@RestController
@RequestMapping("guard/F30302/")
public class F30302Controller {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * ビッグデータ
     * @param id 　ニュースID
     * @param url 当画面URL
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R f30302init(String id, String url) {
        // 生徒ID
        String stuId = (String) ShiroUtils.getSessionAttribute(GakkenConstant.STU_ID);
        // bigData
        Extensions exts = new Extensions();
        // 利用者のシステムID
        exts.put(XApiConstant.EXT_KEY_USER_ID, ShiroUtils.getUserId());
        // ログイン時間
        exts.put(XApiConstant.EXT_KEY_VISIT_TIME, DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
        // 生徒ID
        exts.put(XApiConstant.EXT_KEY_STU_ID, stuId);
        // ニュースＩＤ
        exts.put(XApiConstant.EXT_KEY_NEWS_ID, id);
        // 画面URL
        exts.put(XApiConstant.EXT_KEY_URL, url);
        try {
            XApiUtils.saveStatement(Verbs.launched(), Activitys.academy(), exts);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return R.ok();
    }
}
