package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.manager.service.F21067Service;
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
 * 2020/05/22 ： NWT)hxl ： 新規作成
 * @date 2020/05/22 14:23
 */
@RequestMapping("/manager/F21067")
@RestController
public class F21067Controller {
    /**
     * f21067Service
     */
    @Autowired
    F21067Service f21067Service;

    /**
     * 初期化
     *
     * @param messageId メッセージＩＤ
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f21067Init(Integer messageId) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        return f21067Service.init(orgId, messageId);
    }
}
