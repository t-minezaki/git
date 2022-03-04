/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ${comments}
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_event")
public class MstEventEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * イベントCD
     */
    @NotNull
    private String eventCd;

    /**
     * テンプレートID
     */
    private Integer tempId;

    /**
     * 組織ID
     */
    @NotNull
    private String orgId;

    /**
     * レベルタップ区分
     */
    @NotNull
    private String eventLevelDiv;

    /**
     * イベントタイトル
     */
    @NotNull
    private String eventTitle;

    /**
     * イベント内容
     */
    private String eventCont;

    /**
     * カテゴリ
     */
    @NotNull
    private String ctgyNm;

    /**
     * 公開開始日時
     */
    private Timestamp pubStartDt;

    /**
     * 公開終了日時
     */
    private Timestamp pubEndDt;

    /**
     * 申込み可能開始日時
     */
    private Timestamp applyStartDt;

    /**
     * 申込み可能終了日時
     */
    private Timestamp applyEndDt;

    /**
     * 添付ファイルパス
     */
    private String attachFilePath;

    /**
     * タイトル画像パス
     */
    private String titleImgPath;

    /**
     * イベントステータス区分
     */
    @NotNull
    private String eventStsDiv;

    /**
     * 通知有無フラグ
     */
    private String noitceFlg;

    /**
     * リマインド通知有無フラグ
     */
    private String remandFlg;

    /**
     * 変更可能期間
     */
    private Integer chgLimtDays;

    /**
     * 関連タイプ
     */
    @NotNull
    private String refType;

    /**
     * 1コマ時間
     */
    private Integer unitTime;

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
     * IDを設定する
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * IDを取得する
     */
    public Integer getId() {
        return id;
    }
    /**
     * イベントCDを設定する
     */
    public void setEventCd(String eventCd) {
        this.eventCd = eventCd;
    }
    
    /**
     * イベントCDを取得する
     */
    public String getEventCd() {
        return eventCd;
    }
    /**
     * テンプレートIDを設定する
     */
    public void setTempId(Integer tempId) {
        this.tempId = tempId;
    }
    
    /**
     * テンプレートIDを取得する
     */
    public Integer getTempId() {
        return tempId;
    }
    /**
     * 組織IDを設定する
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    
    /**
     * 組織IDを取得する
     */
    public String getOrgId() {
        return orgId;
    }
    /**
     * レベルタップ区分を設定する
     */
    public void setEventLevelDiv(String eventLevelDiv) {
        this.eventLevelDiv = eventLevelDiv;
    }
    
    /**
     * レベルタップ区分を取得する
     */
    public String getEventLevelDiv() {
        return eventLevelDiv;
    }
    /**
     * イベントタイトルを設定する
     */
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }
    
    /**
     * イベントタイトルを取得する
     */
    public String getEventTitle() {
        return eventTitle;
    }
    /**
     * イベント内容を設定する
     */
    public void setEventCont(String eventCont) {
        this.eventCont = eventCont;
    }
    
    /**
     * イベント内容を取得する
     */
    public String getEventCont() {
        return eventCont;
    }
    /**
     * カテゴリを設定する
     */
    public void setCtgyNm(String ctgyNm) {
        this.ctgyNm = ctgyNm;
    }
    
    /**
     * カテゴリを取得する
     */
    public String getCtgyNm() {
        return ctgyNm;
    }
    /**
     * 公開開始日時を設定する
     */
    public void setPubStartDt(Timestamp pubStartDt) {
        this.pubStartDt = pubStartDt;
    }
    
    /**
     * 公開開始日時を取得する
     */
    public Timestamp getPubStartDt() {
        return pubStartDt;
    }
    /**
     * 公開終了日時を設定する
     */
    public void setPubEndDt(Timestamp pubEndDt) {
        this.pubEndDt = pubEndDt;
    }
    
    /**
     * 公開終了日時を取得する
     */
    public Timestamp getPubEndDt() {
        return pubEndDt;
    }
    /**
     * 申込み可能開始日時を設定する
     */
    public void setApplyStartDt(Timestamp applyStartDt) {
        this.applyStartDt = applyStartDt;
    }
    
    /**
     * 申込み可能開始日時を取得する
     */
    public Timestamp getApplyStartDt() {
        return applyStartDt;
    }
    /**
     * 申込み可能終了日時を設定する
     */
    public void setApplyEndDt(Timestamp applyEndDt) {
        this.applyEndDt = applyEndDt;
    }
    
    /**
     * 申込み可能終了日時を取得する
     */
    public Timestamp getApplyEndDt() {
        return applyEndDt;
    }
    /**
     * 添付ファイルパスを設定する
     */
    public void setAttachFilePath(String attachFilePath) {
        this.attachFilePath = attachFilePath;
    }
    
    /**
     * 添付ファイルパスを取得する
     */
    public String getAttachFilePath() {
        return attachFilePath;
    }
    /**
     * タイトル画像パスを設定する
     */
    public void setTitleImgPath(String titleImgPath) {
        this.titleImgPath = titleImgPath;
    }
    
    /**
     * タイトル画像パスを取得する
     */
    public String getTitleImgPath() {
        return titleImgPath;
    }
    /**
     * イベントステータス区分を設定する
     */
    public void setEventStsDiv(String eventStsDiv) {
        this.eventStsDiv = eventStsDiv;
    }
    
    /**
     * イベントステータス区分を取得する
     */
    public String getEventStsDiv() {
        return eventStsDiv;
    }
    /**
     * 通知有無フラグを設定する
     */
    public void setNoitceFlg(String noitceFlg) {
        this.noitceFlg = noitceFlg;
    }
    
    /**
     * 通知有無フラグを取得する
     */
    public String getNoitceFlg() {
        return noitceFlg;
    }
    /**
     * リマインド通知有無フラグを設定する
     */
    public void setRemandFlg(String remandFlg) {
        this.remandFlg = remandFlg;
    }
    
    /**
     * リマインド通知有無フラグを取得する
     */
    public String getRemandFlg() {
        return remandFlg;
    }
    /**
     * 変更可能期間を設定する
     */
    public void setChgLimtDays(Integer chgLimtDays) {
        this.chgLimtDays = chgLimtDays;
    }
    
    /**
     * 変更可能期間を取得する
     */
    public Integer getChgLimtDays() {
        return chgLimtDays;
    }
    /**
     * 関連タイプを設定する
     */
    public void setRefType(String refType) {
        this.refType = refType;
    }
    
    /**
     * 関連タイプを取得する
     */
    public String getRefType() {
        return refType;
    }
    /**
     * 1コマ時間を設定する
     */
    public void setUnitTime(Integer unitTime) {
        this.unitTime = unitTime;
    }
    
    /**
     * 1コマ時間を取得する
     */
    public Integer getUnitTime() {
        return unitTime;
    }
    /**
     * 作成日時を設定する
     */
    public void setCretDatime(Timestamp cretDatime) {
        this.cretDatime = cretDatime;
    }
    
    /**
     * 作成日時を取得する
     */
    public Timestamp getCretDatime() {
        return cretDatime;
    }
    /**
     * 作成ユーザＩＤを設定する
     */
    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }
    
    /**
     * 作成ユーザＩＤを取得する
     */
    public String getCretUsrId() {
        return cretUsrId;
    }
    /**
     * 更新日時を設定する
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }
    
    /**
     * 更新日時を取得する
     */
    public Timestamp getUpdDatime() {
        return updDatime;
    }
    /**
     * 更新ユーザＩＤを設定する
     */
    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }
    
    /**
     * 更新ユーザＩＤを取得する
     */
    public String getUpdUsrId() {
        return updUsrId;
    }
    /**
     * 削除フラグを設定する
     */
    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }
    
    /**
     * 削除フラグを取得する
     */
    public Integer getDelFlg() {
        return delFlg;
    }
}
