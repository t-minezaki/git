/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.job.service.BTGKA1009Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 本棚入会日次バッチController
 *
 * @author NWT)ckl
 * @date 2021/3/22 16:19
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/3/22 ： NWT)ckl ： 新規作成
 */
@RestController
@RequestMapping(value = "/manager/BTGKA1009")
public class BTGKA1009Controller {

    @Autowired
    BTGKA1009Service btgka1009Service;

    /**
     * 本棚へ連携
     * @return
     */
    @RequestMapping(value = "/dayRegistbulk", method = RequestMethod.GET)
    public R dayRegistbulk(){
        return btgka1009Service.dayRegistbulk();
    }

    /**
     * 本棚へ連携
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public R test(){
        return btgka1009Service.test();
    }
}
