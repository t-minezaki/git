/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

import jp.learningpark.modules.common.entity.StuFixdSchdlEntity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * <p>F10101固定スケジュール表示・編集画面 Extend Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/11 : gong: 新規<br />
 * @version 1.0
 */
public class F10101FixDto extends StuFixdSchdlEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 曜日選択フラグ
     */
    private String dwChocFlg;

    /**
     * 調整ブロック表示名
     */
    private String adjustNm;

    /**
     * 調整ブロック開始時間
     */
    private Timestamp adjustStartTime;

    /**
     * 調整ブロック終了時間
     */
    private Timestamp adjustEndTime;

    /**
     * 固定ブロック廃止フラグ
     */
    private String aboltFlg;

    /**
     * 計画年月日
     */
    private Date planYmd;

    /**
     * 曜日選択フラグを設定する
     *
     * @return dwChocFlg 曜日選択フラグ
     */
    public String getDwChocFlg() {
        return this.dwChocFlg;
    }

    /**
     * 曜日選択フラグを取得する
     *
     * @param dwChocFlg 曜日選択フラグ
     */
    public void setDwChocFlg(String dwChocFlg) {
        this.dwChocFlg = dwChocFlg;
    }

    /**
     * 調整ブロック表示名を設定する
     *
     * @return adjustNm 調整ブロック表示名
     */
    public String getAdjustNm() {
        return this.adjustNm;
    }

    /**
     * 調整ブロック表示名を取得する
     *
     * @param adjustNm 調整ブロック表示名
     */
    public void setAdjustNm(String adjustNm) {
        this.adjustNm = adjustNm;
    }

    /**
     * 調整ブロック開始時間を設定する
     *
     * @return adjustStartTime 調整ブロック開始時間
     */
    public Timestamp getAdjustStartTime() {
        return this.adjustStartTime;
    }

    /**
     * 調整ブロック開始時間を取得する
     *
     * @param adjustStartTime 調整ブロック開始時間
     */
    public void setAdjustStartTime(Timestamp adjustStartTime) {
        this.adjustStartTime = adjustStartTime;
    }

    /**
     * 調整ブロック終了時間を設定する
     *
     * @return adjustEndTime 調整ブロック終了時間
     */
    public Timestamp getAdjustEndTime() {
        return this.adjustEndTime;
    }

    /**
     * 調整ブロック終了時間を取得する
     *
     * @param adjustEndTime 調整ブロック終了時間
     */
    public void setAdjustEndTime(Timestamp adjustEndTime) {
        this.adjustEndTime = adjustEndTime;
    }

    /**
     * 固定ブロック廃止フラグを設定する
     *
     * @return aboltFlg 固定ブロック廃止フラグ
     */
    public String getAboltFlg() {
        return this.aboltFlg;
    }

    /**
     * 固定ブロック廃止フラグを取得する
     *
     * @param aboltFlg 固定ブロック廃止フラグ
     */
    public void setAboltFlg(String aboltFlg) {
        this.aboltFlg = aboltFlg;
    }

    /**
     * 計画年月日を設定する
     *
     * @return planYmd 計画年月日
     */
    public Date getPlanYmd() {
        return this.planYmd;
    }

    /**
     * 計画年月日を取得する
     *
     * @param planYmd 計画年月日
     */
    public void setPlanYmd(Date planYmd) {
        this.planYmd = planYmd;
    }
}
