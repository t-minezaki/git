/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import jp.learningpark.modules.student.dao.F11018Dao;
import jp.learningpark.modules.student.dto.F11018Dto;
import jp.learningpark.modules.student.service.F11018Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>生徒面談の申込内容キャンセル画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/14 : wq: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F11018ServiceImpl implements F11018Service {

    /**
     * 生徒面談の申込内容キャンセル画面 Dao
     */
    @Autowired
    private F11018Dao f11018Dao;

    /**
     * @param applyId 生徒イベント申込状況.ID
     * @param stuId 生徒ID
     * @param refType 関連タイプ
     * @return
     */
    @Override
    public F11018Dto selectInitTalkData(Integer applyId, String stuId, String refType) {
        return f11018Dao.getInitTalkData(applyId, stuId, refType);
    }

    /**
     * @param talkId 面談記録.ID
     * @return
     */
    @Override
    public List<F11018Dto> selectInitTalkDelData(Integer talkId) {
        return f11018Dao.getInitTalkDelData(talkId);
    }
}
