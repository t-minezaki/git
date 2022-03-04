/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.manager.dao.F04014Dao;
import jp.learningpark.modules.manager.service.F04014Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F04014_マナミルチャンネルル参照画面</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/25 : yang: 新規<br />
 * @version 6.0
 */
@Service
public class F04014ServiceImpl implements F04014Service {
    /**
     * f04014Dao
     */
    @Autowired
    F04014Dao f04014Dao;

    /**
     * 全体の送信組織を抽出
     * @param noticeId
     * @return
     */
    @Override
    public List<MstOrgEntity> getOrgList(Integer noticeId) {
        return f04014Dao.getOrgList(noticeId);
    }
}