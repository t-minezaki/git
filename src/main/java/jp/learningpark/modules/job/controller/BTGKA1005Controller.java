/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.job.service.BTGKA1005Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>BTGKA1005_その他の連携ファイル導入</p>
 * <p>详细描述</p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/26 : xie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/manager/BTGKA1005")
public class BTGKA1005Controller {

    @Autowired
    BTGKA1005Service btgka1005Service;

    private static final Logger log = LoggerFactory.getLogger(BTGKA1005Controller.class);
    @RequestMapping(value = "/prepare")
    public R prepare(){
        btgka1005Service.prepare();
        //入会コース情報ファイルの導入処理を行う。（全量導入、ファイル数：2）
        btgka1005Service.courseData();
        //コース区分情報ファイルの導入処理を行う。（全量導入、ファイル数：１）
        btgka1005Service.courseDiv();
        btgka1005Service.releaseResource();
        return R.ok();
    }
}
