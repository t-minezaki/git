/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F20010Dao;
import jp.learningpark.modules.manager.dto.F20010Dto;
import jp.learningpark.modules.manager.dto.F20010LowLevDto;
import jp.learningpark.modules.manager.service.F20010Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>定期テスト科目別成績推移画面（PC）</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/02/18 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
public class F20010ServiceImpl implements F20010Service {
    /**
     * 定期テスト科目別成績推移画面 Dao
     */
    @Autowired
    F20010Dao f20010Dao;

    /**
     * <p>科目別得点推移図エリア取得</p>
     *
     * @param stuId       生徒ID
     * @param testTypeDiv 　テスト種別区分
     * @param schyDiv     　学年区分
     * @return
     */
    @Override
    public List<F20010Dto> getResultPointsArea(String stuId, String testTypeDiv, String schyDiv) {
        return f20010Dao.getResultPointsArea(stuId, testTypeDiv, schyDiv);
    }

    /**
     * <p>学年リスト取得</p>
     *
     * @param stuId       生徒ID
     * @param testTypeDiv テスト分類区分
     * @return
     */
    @Override
    public List<F20010Dto> getSchyList(String stuId, String testTypeDiv) {
        return f20010Dao.getSchyList(stuId, testTypeDiv);
    }

    /**
     * <p>生徒学年、学年区分取得</p>
     *
     * @param stuId 生徒ID
     * @return
     */
    @Override
    public F20010Dto getStuSchy(String stuId) {
        return f20010Dao.getStuSchy(stuId);
    }

    /**
     * 教科タイトルリスト取得
     *
     * @param schyDiv 学年区分
     * @return
     */
    @Override
    public List<F20010LowLevDto> getSubjtList(String schyDiv) {
        return f20010Dao.getSubjtList(schyDiv);
    }
}
