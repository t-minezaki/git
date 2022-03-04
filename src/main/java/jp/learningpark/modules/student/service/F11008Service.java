package jp.learningpark.modules.student.service;

import jp.learningpark.modules.student.dto.F11008Dto;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/04/23 ： NWT)hxl ： 新規作成
 * @date 2020/04/23 11:03
 */
public interface F11008Service {
    /**
     * 計画ブロックリストの取得
     *
     * @param stuId  生徒ID
     * @param tgtYmd 対象日付
     * @return
     */
    List<F11008Dto> getPlanBlockList(String stuId, Date tgtYmd);

    /**
     * 生徒固定ブロック情報取得
     * @param stuId
     * @param tgtYmd
     * @return
     */
    List<F11008Dto> getSchdlBlockList(String stuId, Date tgtYmd, Date date);
}
