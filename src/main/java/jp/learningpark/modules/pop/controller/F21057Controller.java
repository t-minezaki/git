package jp.learningpark.modules.pop.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.pop.service.F21057Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/06/02 ： NWT)hxl ： 新規作成
 * @date 2020/06/02 14:56
 */
@RestController
@RequestMapping("/pop/F21057")
public class F21057Controller {
    /**
     * f21057Service
     */
    @Autowired
    F21057Service f21057Service;

    /**
     * <p>IDで詳細な遅刻欠席連絡情報を取得</p>
     *
     * @param messageId メッセージID
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public R getDetail(Integer messageId) {
        String userId = ShiroUtils.getUserId();
        return f21057Service.getDetail(userId, messageId);
    }
}
