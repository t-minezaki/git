/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;

import jp.learningpark.modules.guard.dto.F30101Dto;
import jp.learningpark.modules.guard.dto.F30101stuConplimentDto;

import java.util.Date;
import java.util.List;

/**
 * <p>学習者の進捗一覧画面（デイリー）Service</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/21 : hujunjie: 新規<br />
 * @version 1.0
 */
public interface F30101Service {
    /**
     * <p>対象日より、生徒ウィークリー計画実績設定情報取得</p>
     *
     * @param stuId 生徒ID
     * @param stgtYmd 当月開始日
     * @return 生徒ウィークリー計画実績設定情報
     */
    List<F30101Dto> getPlanBlockList(String stuId, Date stgtYmd);

    /**
     * 生徒固定ブロック情報取得
     *
     * @param stuId
     * @param tgtYmd
     * @return
     */
    List<F30101Dto> getSchdlBlockList(String stuId, Date tgtYmd);

    /**
     * @param stuId 生徒
     * @param orgId 　組織ID
     * @param stgtYmd 　当月開始日
     * @return
     */
    List<F30101stuConplimentDto> getStuComplimentList(String stuId, String orgId, Date stgtYmd);
}
