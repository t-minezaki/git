/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import jp.learningpark.modules.student.dao.F10503Dao;
import jp.learningpark.modules.student.dto.F10503Dto;
import jp.learningpark.modules.student.form.F10503Form;
import jp.learningpark.modules.student.service.F10503Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>定期テスト結果確認画面 ServiceImpl</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/12 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
public class F10503ServiceImpl implements F10503Service {
    /**
     * 定期テスト結果確認画面 Dao
     */
    @Autowired
    F10503Dao f10503Dao;

    /**
     * <p>科目別得点推移図エリア取得</p>
     *
     * @param stuId       生徒ID
     * @param testTypeDiv 　テスト種別区分
     * @param schyDiv     　学年区分
     * @return
     */
    @Override
    public List<F10503Form> getResultPointsArea(String stuId, String testTypeDiv, String schyDiv) {
        return f10503Dao.getResultPointsArea(stuId, testTypeDiv, schyDiv);
    }

    /**
     * <p>学年リスト取得</p>
     *
     * @param stuId       生徒ID
     * @param testTypeDiv テスト分類区分
     * @return
     */
    @Override
    public List<F10503Form> getSchyList(String stuId, String testTypeDiv) {
        return f10503Dao.getSchyList(stuId, testTypeDiv);
    }

    /**
     * <p>生徒学年、学年区分取得</p>
     *
     * @param stuId 生徒ID
     * @return
     */
    @Override
    public F10503Form getStuSchy(String stuId) {
        return f10503Dao.getStuSchy(stuId);
    }

    /**
     * 教科タイトルリスト取得
     *
     * @param schyDiv 学年区分
     * @return
     */
    @Override
    public List<F10503Dto> getSubjtList(String schyDiv) {
        return f10503Dao.getSubjtList(schyDiv);
    }
}
