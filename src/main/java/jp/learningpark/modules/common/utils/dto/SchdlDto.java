/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.utils.dto;

import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;

import java.util.Date;

/**
 * <p>生徒スケジュール Dtoです。</p>
 *
 * @author NWT : gaoli <br />
 * 変更履歴 <br />
 * 2018/10/08 : gaoli: 新規<br />
 * @version 1.0
 */
public class SchdlDto extends StuWeeklyPlanPerfEntity {

    private static final long serialVersionUID = 1L;

    /** ブロックID */
    private Integer blockId;

    /** 塾ID */
    private String crmschId;

    /** 対象日 */
    private Date tgtYmd;

    /** 開始時間 */
    private String startTime;

    /** 生徒タームプラン設定flg */
    private String isTerm;

    /** 終了時間 */
    private String endTime;

    /** 背景色 */
    private String backgroundColor;

    private String memo;
//    
//    /** ボーダー色 */
//    private String borderColor;
    
    /** 編集可否 */
    private boolean editable = false;
    
    /** 固定ブロック */
    private boolean isFixed = false;



    /***
     * 曜日
     */
    private String dayWeek;
    
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
     * カラーID
     */
    private String colorId;



//
//    /***
//     * ブロック詳細
//     */
//    private String blockDetail;

    public Integer getWeeklyPlanPerfId() {
		return weeklyPlanPerfId;
	}

	public void setWeeklyPlanPerfId(Integer weeklyPlanPerfId) {
		this.weeklyPlanPerfId = weeklyPlanPerfId;
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
     * @return targetYmd 対象日
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
//
//    /**
//     * ボーダー色を設定する
//     *
//     * @return borderColor ボーダー色
//     */
//    public String getBorderColor() {
//        return this.borderColor;
//    }
//
//    /**
//     * ボーダー色を取得する
//     *
//     * @param borderColor ボーダー色
//     */
//    public void setBorderColor(String borderColor) {
//        this.borderColor = borderColor;
//    }

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

    public String getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
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
     * 生徒タームプラン設定flgを設定する
     *
     * @return isTerm 生徒タームプラン設定flg
     */
    public String getIsTerm() {
        return this.isTerm;
    }

    /**
     * 生徒タームプラン設定flgを取得する
     *
     * @param isTerm 生徒タームプラン設定flg
     */
    public void setIsTerm(String isTerm) {
        this.isTerm = isTerm;
    }

    /**
     * 章名を取得する
     *
     * @return chaptNm 章名
     */
    public String getChaptNm() {
        return this.chaptNm;
    }

    /**
     * 章名を設定する
     *
     * @param chaptNm 章名
     */
    public void setChaptNm(String chaptNm) {
        this.chaptNm = chaptNm;
    }

    /**
     * 枝番を取得する
     *
     * @return bnum 枝番
     */
    public Integer getBnum() {
        return this.bnum;
    }

    /**
     * 枝番を設定する
     *
     * @param bnum 枝番
     */
    public void setBnum(Integer bnum) {
        this.bnum = bnum;
    }

    /**
     * カラーIDを取得する
     *
     * @return colorId カラーID
     */
    public String getColorId() {
        return this.colorId;
    }

    /**
     * カラーIDを設定する
     *
     * @param colorId カラーID
     */
    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    /**
     * を取得する
     *
     * @return memo
     */
    public String getMemo() {
        return this.memo;
    }

    /**
     * を設定する
     *
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }
}
