package jp.learningpark.modules.student.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.student.service.F11020Service;
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
 * 2020/05/14 ： NWT)hxl ： 新規作成
 */
@RestController
@RequestMapping("/student/F11020")
public class F11020Controller {
    /**
     * f11020Service
     */
    @Autowired
    F11020Service f11020Service;

    /**
     * 初期表示
     *
     * @param messageId メッセージID
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer messageId) {
        if (messageId == null) {
            return R.ok();
        }
        String stuId = ShiroUtils.getUserId();
        String deliverId = ShiroUtils.getUserId();
        if (stuId.startsWith("p")) {
            stuId = (String)ShiroUtils.getSessionAttribute("stuId");
        }
        if (StringUtils.isEmpty(stuId)) {
            return R.ok();
        }
        return f11020Service.getAskAndTalkList(messageId, stuId, deliverId);
    }
}
