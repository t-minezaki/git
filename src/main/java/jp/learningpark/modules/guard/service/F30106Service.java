/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;

import jp.learningpark.modules.guard.dto.F30106Dto;
import jp.learningpark.modules.guard.dto.F30106LowLevDto;

import java.util.List;

/**
 * <p>定期テスト科目別成績推移画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/02/18 : hujunjie: 新規<br />
 * @version 1.0
 */
public interface F30106Service {
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
    List<F30106Dto> getResultPointsArea(String stuId, String testTypeDiv, String schyDiv, String testKindDIv, Integer tgtY, Integer tgtM, Integer size);

    /**
     * <p>学年リスト取得</p>
     *
     * @param stuId       生徒ID
     * @param testTypeDiv テスト分類区分
     * @return
     */
    List<F30106Dto> getSchyList(String stuId, String testTypeDiv);

    /**
     * <p>生徒学年、学年区分取得</p>
     *
     * @param stuId 生徒ID
     * @return
     */
    F30106Dto getStuSchy(String stuId);

    /**
     * 教科タイトルリスト取得
     *
     * @param schyDiv 学年区分
     * @return
     */
    List<F30106LowLevDto> getSubjtList(String schyDiv, Integer size);

    /**
     * 最新更新日のデータ取得
     *
     * @param stuId       生徒ID
     * @param testTypeDiv 　テスト分類区分
     * @param schyDiv     　学年区分
     * @return
     */
    F30106Dto getResultPointsNewUpDateTime(String stuId, String testTypeDiv, String schyDiv);

    /**
     * テスト種別・テスト対象年月リストの取得
     *
     * @param stuId       生徒ID
     * @param testTypeDiv 　テスト分類区分
     * @param schyDiv     　学年区分
     * @return
     */
    List<F30106Dto> getTgtYMList(String stuId, String testTypeDiv, String schyDiv);
}
