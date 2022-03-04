/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F20010Dto;
import jp.learningpark.modules.manager.dto.F20010LowLevDto;

import java.util.List;

/**
 * <p>定期テスト科目別成績推移画面（PC）</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/02/18 : hujunjie: 新規<br />
 * @version 1.0
 */
public interface F20010Service {
    /**
     * <p>科目別得点推移図エリア取得</p>
     *
     * @param stuId       生徒ID
     * @param testTypeDiv 　テスト種別区分
     * @param schyDiv     　学年区分
     * @return
     */
    List<F20010Dto> getResultPointsArea(String stuId, String testTypeDiv, String schyDiv);

    /**
     * <p>学年リスト取得</p>
     *
     * @param stuId       生徒ID
     * @param testTypeDiv テスト分類区分
     * @return
     */
    List<F20010Dto> getSchyList(String stuId, String testTypeDiv);

    /**
     * <p>生徒学年、学年区分取得</p>
     *
     * @param stuId 生徒ID
     * @return
     */
    F20010Dto getStuSchy(String stuId);

    /**
     * 教科タイトルリスト取得
     *
     * @param schyDiv 学年区分
     * @return
     */
    List<F20010LowLevDto> getSubjtList(String schyDiv);
}
