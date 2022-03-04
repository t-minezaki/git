
package jp.learningpark.modules.job.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.job.service.BTGKA1013Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>BTGKA1013_有効期限超過デバイストーケン無効化バッチ</p>
 * <p>デバイスTOKEN管理には有効期間超過デバイストーケンを無効化するため、定刻バッチを行う。</p>
 * <p></p>
 * @author NWT : liguangxin <br />
 * 2021/10/08 : liguangxin: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/manager/BTGKA1013")
public class BTGKA1013Controller {
    /**
     * btgka1013Service
     */
    @Autowired
    BTGKA1013Service btgka1013Service;
    /**
     * タスクを開始
     * @return R
     */
    @RequestMapping(value = "/updateDelFlgByDeviceId")
    public R updateDelFlgByDeviceId(){ return btgka1013Service.updateDelFlgByDeviceId();}
}
