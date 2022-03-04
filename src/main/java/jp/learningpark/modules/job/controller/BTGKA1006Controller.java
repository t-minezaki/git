
package jp.learningpark.modules.job.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.job.service.BTGKA1006Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>BTGKA1006_創学高等部生徒連携ファイル導入日次バッチ</p>
 * <p>详细描述</p>
 * <p></p>
 * @author NWT : liguangxin <br />
 * 2020/12/24 : liguangxin: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/manager/BTGKA1006")
public class BTGKA1006Controller {
    /**
     * btgka1006Service
     */
    @Autowired
    BTGKA1006Service btgka1006Service;
    /**
     * タスクを開始
     * @return R
     */
    @RequestMapping(value = "/importDateFromGoogleSheet")
    public R importDateFromGoogleSheet(){ return btgka1006Service.importDateFromGoogleSheet();}
}
