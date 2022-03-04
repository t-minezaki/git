/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dto;

import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>学習者の進捗一覧画面（デイリー）Dto</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/21 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F30101Dto  extends StuWeeklyPlanPerfEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ブロックID
     */
    private Integer blockId;

    /**
     * ブロック表示名
     */
    private String mblockDispyNm;

    /**
     * ブロック開始時間
     */
    private Timestamp blockStartTime;

    /**
     * ブロック終了時間
     */
    private Timestamp blockEndTime;

    /** 塾ID */
    private String crmschId;

    /** 対象日 */
    private Date tgtYmd;

    /** 開始時間 */
    private String startTime;

    /** 終了時間 */
    private String endTime;

    /** 背景色 */
    private String backgroundColor;

    /** 編集可否 */
    private boolean editable = false;

    /** 固定ブロック */
    private boolean isFixed = false;

    /**
     * 月曜日選択フラグ
     */
    private boolean moDwChocFlg;
    /**
     * 火曜日選択フラグ
     */
    private boolean tuDwChocFlg;
    /**
     * 水曜日選択フラグ
     */
    private boolean weDwChocFlg;
    /**
     * 木曜日選択フラグ
     */
    private boolean thDwChocFlg;
    /**
     * 金曜日選択フラグ
     */
    private boolean frDwChocFlg;
    /**
     * 土曜日選択フラグ
     */
    private boolean saDwChocFlg;
    /**
     * 日曜日選択フラグ
     */
    private boolean suDwChocFlg;


    /**
     * 生徒ウィークリー計画実績設定ID
     */
    private Integer weeklyPlanPerfId;

    /**
     * 章名
     */
    private String chaptNm;
    /**
     * 枝番
     */
    private Integer bnum;

    /**
     * 曜日選択フラグ
     */
    private String dwChocFlg;

    /**
     * 教科
     */
    private String subjt;

    /**
     * 計画学習時間（分）
     */
    private Integer planLearnTm;

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
     * ブロック画像区分
     */
    private String blockPicDiv;

    /**
     * 教科書ID
     */
    private String textbId;

    /**
     * 計画登録フラグ
     */
    private String planRegFlg;

    /**
     * blockStartTimeを取得する
     *
     * @return blockStartTime blockStartTime
     */
    public Timestamp getBlockStartTime() {
        return blockStartTime;
    }

    /**
     * blockStartTimeを設定する
     *
     * @param blockStartTime blockStartTime
     */
    public void setBlockStartTime(Timestamp blockStartTime) {
        this.blockStartTime = blockStartTime;
    }

    /**
     * blockEndTimeを取得する
     *
     * @return blockEndTime blockEndTime
     */
    public Timestamp getBlockEndTime() {
        return blockEndTime;
    }

    /**
     * blockEndTimeを設定する
     *
     * @param blockEndTime blockEndTime
     */
    public void setBlockEndTime(Timestamp blockEndTime) {
        this.blockEndTime = blockEndTime;
    }

    /**
     * ブロックIDを設定する
     *
     * @return blockId ブロックID
     */
    public Integer getBlockId() {
        return this.blockId;
    }

    /**
     * ブロックIDを取得する
     *
     * @param blockId ブロックID
     */
    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    /**
     * 塾IDを設定する
     *
     * @return crmschId 塾ID
     */
    public String getCrmschId() {
        return this.crmschId;
    }

    /**
     * 塾IDを取得する
     *
     * @param crmschId 塾ID
     */
    public void setCrmschId(String crmschId) {
        this.crmschId = crmschId;
    }

    /**
     * 対象日を設定する
     *
     * @return tgtYmd 対象日
     */
    public Date getTgtYmd() {
        return this.tgtYmd;
    }

    /**
     * 対象日を取得する
     *
     * @param tgtYmd 対象日
     */
    public void setTgtYmd(Date tgtYmd) {
        this.tgtYmd = tgtYmd;
    }

    /**
     * 開始時間を設定する
     *
     * @return startTime 開始時間
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * 開始時間を取得する
     *
     * @param startTime 開始時間
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 終了時間を設定する
     *
     * @return endTime 終了時間
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     * 終了時間を取得する
     *
     * @param endTime 終了時間
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 背景色を設定する
     *
     * @return backgroundColor 背景色
     */
    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * 背景色を取得する
     *
     * @param backgroundColor 背景色
     */
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * 編集可否を設定する
     *
     * @return editable 編集可否
     */
    public boolean isEditable() {
        return this.editable;
    }

    /**
     * 編集可否を取得する
     *
     * @param editable 編集可否
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * 固定ブロックを設定する
     *
     * @return isFixed 固定ブロック
     */
    public boolean isIsFixed() {
        return this.isFixed;
    }

    /**
     * 固定ブロックを取得する
     *
     * @param isFixed 固定ブロック
     */
    public void setIsFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }

    /**
     * 生徒ウィークリー計画実績設定IDを設定する
     *
     * @return weeklyPlanPerfId 生徒ウィークリー計画実績設定ID
     */
    public Integer getWeeklyPlanPerfId() {
        return this.weeklyPlanPerfId;
    }

    /**
     * 生徒ウィークリー計画実績設定IDを取得する
     *
     * @param weeklyPlanPerfId 生徒ウィークリー計画実績設定ID
     */
    public void setWeeklyPlanPerfId(Integer weeklyPlanPerfId) {
        this.weeklyPlanPerfId = weeklyPlanPerfId;
    }

    /**
     * 章名を設定する
     *
     * @return chaptNm 章名
     */
    public String getChaptNm() {
        return this.chaptNm;
    }

    /**
     * 章名を取得する
     *
     * @param chaptNm 章名
     */
    public void setChaptNm(String chaptNm) {
        this.chaptNm = chaptNm;
    }

    /**
     * 枝番を設定する
     *
     * @return bnum 枝番
     */
    public Integer getBnum() {
        return this.bnum;
    }

    /**
     * 枝番を取得する
     *
     * @param bnum 枝番
     */
    public void setBnum(Integer bnum) {
        this.bnum = bnum;
    }

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
     * 教科を設定する
     *
     * @return subjt 教科
     */
    public String getSubjt() {
        return this.subjt;
    }

    /**
     * 教科を取得する
     *
     * @param subjt 教科
     */
    public void setSubjt(String subjt) {
        this.subjt = subjt;
    }

    /**
     * 計画学習時間（分）を設定する
     *
     * @return planLearnTm 計画学習時間（分）
     */
    public Integer getPlanLearnTm() {
        return this.planLearnTm;
    }

    /**
     * 計画学習時間（分）を取得する
     *
     * @param planLearnTm 計画学習時間（分）
     */
    public void setPlanLearnTm(Integer planLearnTm) {
        this.planLearnTm = planLearnTm;
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
     * ブロック画像区分を設定する
     *
     * @return blockPicDiv ブロック画像区分
     */
    public String getBlockPicDiv() {
        return this.blockPicDiv;
    }

    /**
     * ブロック画像区分を取得する
     *
     * @param blockPicDiv ブロック画像区分
     */
    public void setBlockPicDiv(String blockPicDiv) {
        this.blockPicDiv = blockPicDiv;
    }

    /**
     * 教科書IDを設定する
     *
     * @return textbId 教科書ID
     */
    public String getTextbId() {
        return this.textbId;
    }

    /**
     * 教科書IDを取得する
     *
     * @param textbId 教科書ID
     */
    public void setTextbId(String textbId) {
        this.textbId = textbId;
    }

    /**
     * 計画登録フラグを設定する
     */
    public void setPlanRegFlg(String planRegFlg) {
        this.planRegFlg = planRegFlg;
    }

    /**
     * 計画登録フラグを取得する
     */
    public String getPlanRegFlg() {
        return planRegFlg;
    }

    /**
     * serialVersionUIDを取得する
     *
     * @return serialVersionUID serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * isFixedを取得する
     *
     * @return isFixed isFixed
     */
    public boolean isFixed() {
        return isFixed;
    }

    /**
     * isFixedを設定する
     *
     * @param fixed isFixed
     */
    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    /**
     * ブロック表示名を取得する
     * @return
     */
    public String getMblockDispyNm() {
        return mblockDispyNm;
    }

    /**
     * ブロック表示名を設定する
     * @param mblockDispyNm
     */
    public void setMblockDispyNm(String mblockDispyNm) {
        this.mblockDispyNm = mblockDispyNm;
    }

    /**
     * 月曜日選択フラグを取得する
     *
     * @return moDwChocFlg 月曜日選択フラグ
     */
    public boolean isMoDwChocFlg() {
        return this.moDwChocFlg;
    }

    /**
     * 月曜日選択フラグを設定する
     *
     * @param moDwChocFlg 月曜日選択フラグ
     */
    public void setMoDwChocFlg(boolean moDwChocFlg) {
        this.moDwChocFlg = moDwChocFlg;
    }

    /**
     * 火曜日選択フラグを取得する
     *
     * @return tuDwChocFlg 火曜日選択フラグ
     */
    public boolean isTuDwChocFlg() {
        return this.tuDwChocFlg;
    }

    /**
     * 火曜日選択フラグを設定する
     *
     * @param tuDwChocFlg 火曜日選択フラグ
     */
    public void setTuDwChocFlg(boolean tuDwChocFlg) {
        this.tuDwChocFlg = tuDwChocFlg;
    }

    /**
     * 水曜日選択フラグを取得する
     *
     * @return weDwChocFlg 水曜日選択フラグ
     */
    public boolean isWeDwChocFlg() {
        return this.weDwChocFlg;
    }

    /**
     * 水曜日選択フラグを設定する
     *
     * @param weDwChocFlg 水曜日選択フラグ
     */
    public void setWeDwChocFlg(boolean weDwChocFlg) {
        this.weDwChocFlg = weDwChocFlg;
    }

    /**
     * 木曜日選択フラグを取得する
     *
     * @return thDwChocFlg 木曜日選択フラグ
     */
    public boolean isThDwChocFlg() {
        return this.thDwChocFlg;
    }

    /**
     * 木曜日選択フラグを設定する
     *
     * @param thDwChocFlg 木曜日選択フラグ
     */
    public void setThDwChocFlg(boolean thDwChocFlg) {
        this.thDwChocFlg = thDwChocFlg;
    }

    /**
     * 金曜日選択フラグを取得する
     *
     * @return frDwChocFlg 金曜日選択フラグ
     */
    public boolean isFrDwChocFlg() {
        return this.frDwChocFlg;
    }

    /**
     * 金曜日選択フラグを設定する
     *
     * @param frDwChocFlg 金曜日選択フラグ
     */
    public void setFrDwChocFlg(boolean frDwChocFlg) {
        this.frDwChocFlg = frDwChocFlg;
    }

    /**
     * 土曜日選択フラグを取得する
     *
     * @return saDwChocFlg 土曜日選択フラグ
     */
    public boolean isSaDwChocFlg() {
        return this.saDwChocFlg;
    }

    /**
     * 土曜日選択フラグを設定する
     *
     * @param saDwChocFlg 土曜日選択フラグ
     */
    public void setSaDwChocFlg(boolean saDwChocFlg) {
        this.saDwChocFlg = saDwChocFlg;
    }

    /**
     * 日曜日選択フラグを取得する
     *
     * @return suDwChocFlg 日曜日選択フラグ
     */
    public boolean isSuDwChocFlg() {
        return this.suDwChocFlg;
    }

    /**
     * 日曜日選択フラグを設定する
     *
     * @param suDwChocFlg 日曜日選択フラグ
     */
    public void setSuDwChocFlg(boolean suDwChocFlg) {
        this.suDwChocFlg = suDwChocFlg;
    }
}
