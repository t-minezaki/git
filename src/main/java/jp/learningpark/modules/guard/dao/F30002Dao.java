/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30002Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F30002_子供選択画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/12 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F30002Dao {
    /**
     * <p>塾学習期間IDの取得</p>
     *
     * @param stuId 生徒Id
     * @return
     */
    Integer getCrmLearnPrdId(String stuId);

    /**
     * <p>保護者の全子供を取得し</p>
     *
     * @param guard 保護者id
     * @return
     */
    List<F30002Dto> selectAboutStus(String guard);

    /**
     * <p>保護者の全子供を取得し</p>
     *
     * @param guards 保護者IDリスト
     * @return
     */
    List<F30002Dto> selectStudents(String guards);

    /**
     * 保護者を取得
     * @param stuId
     * @return
     */
    List<F30002Dto> selectGuardInfo(String stuId);

    /**
     * QRコード暫定対応
     *
     * @param guards 保護者Id
     * @return
     */
    List<F30002Dto> selectStudentsCount(String guards);

    /**
     * brandcdの取得
     *
     * @param stuId 生徒Id
     * @return
     * 2021/12/14 manamiru1-785 cuikl add
     */
    String getBrandcdByStu(@Param("stuId") String stuId);
}
