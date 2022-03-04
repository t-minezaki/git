/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.common.utils.dto.SchdlDto;
import jp.learningpark.modules.student.dto.F10101Dto;
import jp.learningpark.modules.student.dto.F10101FixDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>F10101固定スケジュール表示・編集画面</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/11 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F10101Dao {
    /**
     * <p>対象日より、生徒固定スケジュール設定情報取得(F10101)</p>
     *
     * @param stuId 生徒ID
     * @param date 対象日
     * @param flg 曜日フラグ
     * @return 生徒固定スケジュール設定情報
     */
    List<F10101FixDto> selectFixList(@Param("stuId") String stuId, @Param("date") Date date, @Param("flg") String flg);

    /**
     * 自然カレンダーの対象日当週の生徒固定スケジュールと計画ブロックの取得
     *
     * @param stuId 生徒ID
     * @param startYmd 開始日
     * @param endYmd 終了日
     * @param flg all 全て、以外の場合R1：復習 , S1：学習 , P1：予習, W1：学校の宿題
     * @return 生徒固定スケジュールと計画ブロック情報
     */
    List<SchdlDto> selectSchdlList(
            @Param("id") Integer id,
            @Param("stuId") String stuId, @Param("startYmd") Date startYmd, @Param("checkStart") boolean checkStart, @Param("checkEnd") boolean checkEnd);

    /**
     * <p>固定ブロックエリア情報の取得処理</p>
     *
     * @param stuId
     * @return
     */
    List<F10101Dto> getBlock(@Param("stuId") String stuId);
}
