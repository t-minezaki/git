/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import jp.learningpark.modules.student.dao.F11017Dao;
import jp.learningpark.modules.student.dto.F11017Dto;
import jp.learningpark.modules.student.service.F11017Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>生徒面談の申込内容変更・キャンセル画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/14 : wq: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F11017ServiceImpl implements F11017Service {

    /**
     * 生徒面談の申込内容変更・キャンセル画面 Dao
     */
    @Autowired
    private F11017Dao f11017Dao;

    /**
     * <p>生徒イベント申込状況情報を取得</p>
     *
     * @param stuId 生徒ID
     * @param flg 判断マーク
     * @return
     */
    @Override
    public List<F11017Dto> selectInitApplyData(String stuId, Integer flg) {
        return f11017Dao.getInitApplyData(stuId, flg);
    }
}
