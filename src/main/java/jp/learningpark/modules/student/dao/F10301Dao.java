/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F1030101Dto;
import jp.learningpark.modules.student.dto.F1030103Dto;
import jp.learningpark.modules.student.dto.F1030105Dto;
import jp.learningpark.modules.student.dto.F1030106Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>ウィークリープラン計画管理画面DAO</p >
 *
 * @author NWT : gaoli <br />
 * 変更履歴 <br />
 * 2018/10/19 : gaoli: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F10301Dao {

    /**
     * <p>学習ブロックを取得する</p>
     *
     * @param stuId    生徒ID
     * @param crmLearnPrdId 塾学習期間ID
     * @return 学習ブロックリスト
     */
    List<F1030101Dto> selectLearnBlock(@Param("stuId") String stuId, @Param("crmLearnPrdId") Integer crmLearnPrdId);

    /**
     * <p>予習ブロックを取得する(F10301)</p>
     *
     * @param stuId 生徒ID
     * @param crmLearnPrdId 塾学習期間ID
     * @return 予習ブロック
     */
    List<F1030101Dto> selectPreviewBlock(@Param("stuId") String stuId);

    /**
     * <p>生徒ウィークリー実績履歴登録処理</p>
     *
     * @param stuId 生徒ID
     * @param updateTime 生徒ID
     * @param id 生徒ウィークリー計画実績設定のID
     * @return 予習ブロックリスト
     */
    int insertWeeklyPerfHst(@Param("stuId") String stuId, @Param("updateTime") Timestamp updateTime, @Param("id") Integer id);

    /**
     * 生徒ウィークリー計画実績設定を取得する。
     *
     * @param stuId 生徒ID
     * @param blockTypeDiv ブロック種類区分リスト
     * @param crmLearnPrdId 塾学習期間ID
     * @return 生徒ウィークリー計画実績設定情報リスト
     */
    List<F1030103Dto> selectPlannedBlock(@Param("stuId") String stuId, @Param("blockTypeDiv") List<String> blockTypeDiv, @Param("crmLearnPrdId") Integer crmLearnPrdId);

    /**
     * <p>生徒基本情報取得処理</p>
     *
     * @param stuId 生徒ID
     * @return 生徒基本情報
     */
    Map<String, String> selectStudentInfo(@Param("stuId") String stuId);


    /**
     * <p>生徒基本情報取得処理</p>
     *
     * @param stuId 生徒ID
     * @return 生徒基本情報
     */
    List<F1030105Dto> selectPrintPlannedBlock(@Param("stuId") String stuId, @Param("startYmd") Date startYmd, @Param("endYmd") Date endYmd);

    /**
     * <p>その他ブロックエリア情報の取得</p>
     *
     * @param stuId 生徒ID
     * @return 生徒基本情報
     */
    List<F1030106Dto> selectOtherBlock(@Param("stuId") String stuId);

    /**
     *
     * @param blockTypeDiv
     * @param stuId
     * @return
     */
    List<F1030106Dto> getReviewBlockList(@Param("blockTypeDiv") List<String> blockTypeDiv, @Param("stuId") String stuId);

}
