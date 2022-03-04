/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service;

import jp.learningpark.modules.student.dto.F1030101Dto;
import jp.learningpark.modules.student.dto.F1030102Dto;
import jp.learningpark.modules.student.dto.F1030103Dto;
import jp.learningpark.modules.student.dto.F1030105Dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ウィークリープラン計画管理画面 Service。
 * </p >
 *
 * @author NWT : yangfan <br />
 *         変更履歴 <br />
 *         2018/10/17 : yangfan: 新規<br />
 * @version 1.0
 */
public interface F10301Service {

    /**
     * <p>
     * 登録処理
     * </p>
     *
     * @param dto 画面情報
     * @return ブロック情報
     */
    F1030103Dto doUpdate(F1030102Dto dto);

    /**
     * <p>
     * 削除処理
     * </p>
     *
     * @param id 生徒ウィークリー計画実績設定のID
     * @return 削除件数
     */
    Integer doDelete(Integer id);

    /**
     * <p>
     * 学習週の取得処理
     * </p>
     * 
     * @return 学習週情報
     */
    Map<String, Object> getLearnSeasnInfo();

    /**
     * <p>
     * 学習ブロックの取得処理
     * </p>
     * 
     * @param weekStartDay 今週開始日
     * @return 学習ブロック情報
     */
    Map<String, Object> getLearnBlockInfo(String weekStartDay);

    /**
     * <p>
     * 計画済み学習ブロックの取得処理
     * </p>
     * 
     * @return 計画済み学習ブロック情報
     */
    Map<Integer, F1030103Dto> getPlannedBlock();

    /**
     * <p>
     * その他ブロックの取得処理
     * </p>
     *
     * @return その他ブロックリスト
     */
    List<F1030101Dto> getOtherBlock();

    /**
     * <p>
     * 復習ブロックの取得処理(F10301)
     * </p>
     *
     * @return 復習ブロック情報リスト
     */
    List<F1030101Dto> getReviewBlock();

    /**
     * <p>
     * 予習ブロックの取得処理(F10301)
     * </p>
     *
     * @return 予習ブロックリスト
     */
    List<F1030101Dto> getPreviewBlock();

    /**
     * <p>
     * 生徒基本情報取得処理
     * </p>
     *
     * @param stuId 生徒ID
     * @return 生徒基本情報
     */
    Map<String, String> getStudentInfo();

    /**
     * <p>
     * 計画済み学習ブロックの取得処理(F10302)
     * </p>
     *
     * @return 計画済み学習ブロック情報
     */
    Map<String, Object> getLearnBlock();
    
    /**
     * <p>
     * 印刷計画済み学習ブロックの取得処理(R00001)
     * </p>
     * @param startYmd 週開始日
     * @param endYmd   週終了日
     * @return 計画済み学習ブロック情報
     */
    List<F1030105Dto> getPrintPlannedBlock(Date startYmd, Date endYmd);
}
