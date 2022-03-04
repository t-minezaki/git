/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.task.service.impl;

import jp.learningpark.modules.job.task.dao.BTGKA1001Dao;
import jp.learningpark.modules.job.task.dto.BTGKA1001Dto;
import jp.learningpark.modules.job.task.service.BTGKA1001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>イベント公開時定時メール送信日次バッチ ServiceImpl</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/27 : wq: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class BTGKA1001ServiceImpl implements BTGKA1001Service {

    /**
     * イベント公開時定時メール送信日次バッチ dao
     */
    @Autowired
    private BTGKA1001Dao btgka1001Dao;

    /**
     * @param pubStart 開始時間
     * @param pubEnd 終了時間
     * @return
     */
    @Override
    public List<BTGKA1001Dto> selectDeliverInfo(Timestamp pubStart, Timestamp pubEnd) {
        return btgka1001Dao.selectDeliverInfo(pubStart, pubEnd);
    }
}
