/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.guard.dao.F30106Dao;
import jp.learningpark.modules.guard.dto.F30106Dto;
import jp.learningpark.modules.guard.dto.F30106LowLevDto;
import jp.learningpark.modules.guard.service.F30106Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>定期テスト科目別成績推移画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/02/18 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
public class F30106ServiceImpl implements F30106Service {
    /**
     * 定期テスト科目別成績推移画面 Dao
     */
    @Autowired
    F30106Dao f30106Dao;

    /**
     * <p>科目別得点推移図エリア取得</p>
     *
     * @param stuId       生徒ID
     * @param testTypeDiv 　テスト種別区分
     * @param schyDiv     　学年区分
     * @param testKindDIv テスト種別区分
     * @param tgtY        テスト対象年
     * @param tgtM        テスト対象月
     * @return
     */
    @Override
    public List<F30106Dto> getResultPointsArea(String stuId, String testTypeDiv, String schyDiv, String testKindDIv, Integer tgtY, Integer tgtM, Integer size) {
        return f30106Dao.getResultPointsArea(stuId, testTypeDiv, schyDiv, testKindDIv, tgtY, tgtM, size);
    }

    /**
     * <p>学年リスト取得</p>
     *
     * @param stuId       生徒ID
     * @param testTypeDiv テスト分類区分
     * @return
     */
    @Override
    public List<F30106Dto> getSchyList(String stuId, String testTypeDiv) {
        return f30106Dao.getSchyList(stuId, testTypeDiv);
    }

    /**
     * <p>生徒学年、学年区分取得</p>
     *
     * @param stuId 生徒ID
     * @return
     */
    @Override
    public F30106Dto getStuSchy(String stuId) {
        return f30106Dao.getStuSchy(stuId);
    }

    /**
     * 教科タイトルリスト取得
     *
     * @param schyDiv 学年区分
     * @return
     */
    @Override
    public List<F30106LowLevDto> getSubjtList(String schyDiv, Integer size) {
        return f30106Dao.getSubjtList(schyDiv, size);
    }

    /**
     * 最新更新日のデータ取得
     *
     * @param stuId       生徒ID
     * @param testTypeDiv 　テスト分類区分
     * @param schyDiv     　学年区分
     * @return
     */
    @Override
    public F30106Dto getResultPointsNewUpDateTime(String stuId, String testTypeDiv, String schyDiv) {
        return f30106Dao.getResultPointsNewUpDateTime(stuId, testTypeDiv, schyDiv);
    }

    /**
     * テスト種別・テスト対象年月リストの取得
     *
     * @param stuId       生徒ID
     * @param testTypeDiv 　テスト分類区分
     * @param schyDiv     　学年区分
     * @return
     */
    @Override
    public List<F30106Dto> getTgtYMList(String stuId, String testTypeDiv, String schyDiv) {
        return f30106Dao.getTgtYMList(stuId, testTypeDiv, schyDiv);
    }
}
