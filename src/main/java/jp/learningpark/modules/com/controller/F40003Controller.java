package jp.learningpark.modules.com.controller;

import jp.learningpark.modules.manager.service.F20001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * F40003_メンター共通メニュー コントローラ
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/07 ： NWT)hxl ： 新規作成
 * @date 2019/11/07 16:45
 */
@Controller
public class F40003Controller {

    /**
     * F20001_生徒一覧画面  Service
     */
    @Autowired
    F20001Service f20001Service;

    /**
     * <p>インターセプトページリクエスト</p>
     *
     * @return
     */
//    @RequestMapping(value = "/common/F40003.html")
//    public ModelAndView initPermission() {
//        String userId = ShiroUtils.getUserId();
//        ShiroUtils.setSessionAttribute("f20001permission", f20001Service.getPermission(userId));
//        Session session = ShiroUtils.getSession();
//        return new ModelAndView("/common/F40003");
//    }
}
