package jp.learningpark.modules.job.controller;

import jp.learningpark.modules.job.service.BTGKA1012Service;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>マナミル側のデバイスTOKEN管理を通知アプリ側へ同期するため、日次バッチ処理を行う。</p >
 * <p>実行時間がバッチ実行スケジュールテーブルには設定すること。</p >
 *
 * @author NWT : wq <br />
 * <p>
 * 2021/10/9
 * @version 1.0
 */
//@Component("BTGKA1012")
    @RestController
    @RequestMapping(value = "/common")
public class BTGKA1012Controller {

    /**
     * バッチ1012 Service
     */
    final BTGKA1012Service service;

    public BTGKA1012Controller(BTGKA1012Service service) {
        this.service = service;
    }

    /**
     * バッチを実行する
     */
    @RequestMapping(value = "/BTGKA1012")
    public void batchExecute() {
        service.execute();
    }
}
