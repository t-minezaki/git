/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.task.service;

import jp.learningpark.modules.job.task.dto.BTGKA1001Dto;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>イベント公開時定時メール送信日次バッチ Service</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/27 : wq: 新規<br />
 * @version 1.0
 */
public interface BTGKA1001Service {

    /**
     * @param pubStart 開始時間
     * @param pubEnd 終了時間
     * @return
     */
    List<BTGKA1001Dto> selectDeliverInfo(Timestamp pubStart, Timestamp pubEnd);
}
