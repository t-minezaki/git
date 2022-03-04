package jp.learningpark.modules.job.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.job.service.BTGKA1004Service;
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
 * 2020/8/26 ： NWT)hxl ： 新規作成
 * @date 2020/8/26 12:18
 */
@RestController
@RequestMapping(value = "/manager/BTGKA1004")
public class BTGKA1004Controller {

    /**
     * btgka1004Service
     */
    @Autowired
    BTGKA1004Service btgka1004Service;

    /**
     * タスクを開始
     * @return R
     */
    @RequestMapping(value = "/importCsvFile", method = RequestMethod.GET)
    public R importCsvFile(){
        return btgka1004Service.importDateFromCsvFile();
    }

    @RequestMapping(value = "/updateFirstPassword", method = RequestMethod.GET)
    public R updatePassword(){
        if (StringUtils.equals(ShiroUtils.getUserEntity().getAfterUsrId(), "GKLE")) {
            return btgka1004Service.updatePassword();
        }else {
            return R.error("404 not found");
        }
    }
}
