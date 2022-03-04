/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;

import jp.learningpark.modules.guard.dto.F30002Dto;

import java.util.List;

/**
 * <p>F30002_子供選択画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/12 : gong: 新規<br />
 * @version 1.0
 */
public interface F30002Service {
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
    List<F30002Dto> getAboutStus(String guard);

    /**
     *
     * @param guards 保護者IDリスト
     * @return
     */
    List<F30002Dto> getStudents(String guards);

    /**
     * 保護者を取得
     * @param stuId
     * @return
     */
    List<F30002Dto> selectGuardInfo(String stuId);

    /**
     * QRコード暫定対応
     * @param guards 保護者IDリスト
     * @return
     */
    List<F30002Dto> getStudentsCount(String guards);

    // 2021/12/14 MANAMIRU1-785 add Start
    /**
     * getBrandcdByStu
     * @param stuId 生徒Id
     * @return
     */
    String getBrandcdByStu(String stuId);
    // 2021/12/14 MANAMIRU1-785 add end
}
