/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>F10101 固定スケジュール表示・編集画面 dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/11 : gong: 新規<br />
 * @version 1.0
 */
public class F10101Dto extends StuWeeklyPlanPerfEntity {
    private static final long serialVersionUID = 1L;

    /** ブロックID */
    private String blockId;

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
    @NotNull
    private Integer id;

    /**
     * ブロック表示名
     */
    private String blockDispyNm;

    /**
     * 上層ブロックID
     */
    private Integer upplevBlockId;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * ブロック種類区分
     */
    @NotNull
    private String blockTypeDiv;

    /**
     * ブロック画像区分
     */
    private String blockPicDiv;

    /**
     * 作成日時
     */
    private Timestamp cretDatime;

    /**
     * 作成ユーザＩＤ
     */
    private String cretUsrId;

    /**
     * 更新日時
     */
    private Timestamp updDatime;

    /**
     * 更新ユーザＩＤ
     */
    private String updUsrId;

    /**
     * 削除フラグ
     */
    private Integer delFlg;
    /**
     * カラーID
     */
    private String colorId;
    /**
     *フォントのカラー
     */
    private String fontColor;

    /**
     * ブロックIDを設定する
     *
     * @return blockId ブロックID
     */
    public String getBlockId() {
        return this.blockId;
    }

    /**
     * ブロックIDを取得する
     *
     * @param blockId ブロックID
     */
    public void setBlockId(String blockId) {
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
     * 曜日を設定する
     *
     * @return dayWeek 曜日
     */
    public String getDayWeek() {
        return this.dayWeek;
    }

    /**
     * 曜日を取得する
     *
     * @param dayWeek 曜日
     */
    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
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
     * @NotNullを取得する
     *
     * @return id @NotNull
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * @NotNullを設定する
     *
     * @param id @NotNull
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * ブロック表示名を取得する
     *
     * @return blockDispyNm ブロック表示名
     */
    public String getBlockDispyNm() {
        return this.blockDispyNm;
    }

    /**
     * ブロック表示名を設定する
     *
     * @param blockDispyNm ブロック表示名
     */
    public void setBlockDispyNm(String blockDispyNm) {
        this.blockDispyNm = blockDispyNm;
    }

    /**
     * 上層ブロックIDを取得する
     *
     * @return upplevBlockId 上層ブロックID
     */
    public Integer getUpplevBlockId() {
        return this.upplevBlockId;
    }

    /**
     * 上層ブロックIDを設定する
     *
     * @param upplevBlockId 上層ブロックID
     */
    public void setUpplevBlockId(Integer upplevBlockId) {
        this.upplevBlockId = upplevBlockId;
    }

    /**
     * 生徒IDを取得する
     *
     * @return stuId 生徒ID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * ブロック種類区分を取得する
     *
     * @return blockTypeDiv ブロック種類区分
     */
    public String getBlockTypeDiv() {
        return this.blockTypeDiv;
    }

    /**
     * ブロック種類区分を設定する
     *
     * @param blockTypeDiv ブロック種類区分
     */
    public void setBlockTypeDiv(String blockTypeDiv) {
        this.blockTypeDiv = blockTypeDiv;
    }

    /**
     * ブロック画像区分を取得する
     *
     * @return blockPicDiv ブロック画像区分
     */
    public String getBlockPicDiv() {
        return this.blockPicDiv;
    }

    /**
     * ブロック画像区分を設定する
     *
     * @param blockPicDiv ブロック画像区分
     */
    public void setBlockPicDiv(String blockPicDiv) {
        this.blockPicDiv = blockPicDiv;
    }

    /**
     * 作成日時を取得する
     *
     * @return cretDatime 作成日時
     */
    public Timestamp getCretDatime() {
        return this.cretDatime;
    }

    /**
     * 作成日時を設定する
     *
     * @param cretDatime 作成日時
     */
    public void setCretDatime(Timestamp cretDatime) {
        this.cretDatime = cretDatime;
    }

    /**
     * 作成ユーザＩＤを取得する
     *
     * @return cretUsrId 作成ユーザＩＤ
     */
    public String getCretUsrId() {
        return this.cretUsrId;
    }

    /**
     * 作成ユーザＩＤを設定する
     *
     * @param cretUsrId 作成ユーザＩＤ
     */
    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }

    /**
     * 更新日時を取得する
     *
     * @return updDatime 更新日時
     */
    public Timestamp getUpdDatime() {
        return this.updDatime;
    }

    /**
     * 更新日時を設定する
     *
     * @param updDatime 更新日時
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }

    /**
     * 更新ユーザＩＤを取得する
     *
     * @return updUsrId 更新ユーザＩＤ
     */
    public String getUpdUsrId() {
        return this.updUsrId;
    }

    /**
     * 更新ユーザＩＤを設定する
     *
     * @param updUsrId 更新ユーザＩＤ
     */
    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }

    /**
     * 削除フラグを取得する
     *
     * @return delFlg 削除フラグ
     */
    public Integer getDelFlg() {
        return this.delFlg;
    }

    /**
     * 削除フラグを設定する
     *
     * @param delFlg 削除フラグ
     */
    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
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
     * フォントのカラーを取得する
     *
     * @return fontColor フォントのカラー
     */
    public String getFontColor() {
        return this.fontColor;
    }

    /**
     * フォントのカラーを設定する
     *
     * @param fontColor フォントのカラー
     */
    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }
}
