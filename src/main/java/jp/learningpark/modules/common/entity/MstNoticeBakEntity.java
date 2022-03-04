/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.Time;

/**
 * 保護者お知らせ閲覧状況
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_notice_bak")
public class MstNoticeBakEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 組織ID
     */
    @NotNull
    private String orgId;

    /**
     * お知らせタイトル
     */
    @NotNull
    private String noticeTitle;

    /**
     * お知らせ内容
     */
    private String noticeCont;

    /**
     * お知らせタップ区分
     */
    @NotNull
    private String noticeTypeDiv;

    /**
     * お知らせレベル区分
     */
    @NotNull
    private String noticeLevelDiv;

    /**
     * 掲載予定開始日時
     */
    private Timestamp pubPlanStartDt;

    /**
     * 掲載予定終了日時
     */
    private Timestamp pubPlanEndDt;

    /**
     * 全体配信フラグ
     */
    private String allDeliverFlg;

    /**
     * 添付ファイルパス
     */
    private String attachFilePath;

    /**
     * タイトル画像パス
     */
    private String titleImgPath;

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
     * 退避日時
     */
    @NotNull
    private Timestamp evacuationDatime;

    /**
     * 退避ID
     */
    @NotNull
    private String evacuationUsrId;

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
     * お知らせタイトルを設定する
     */
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }
    
    /**
     * お知らせタイトルを取得する
     */
    public String getNoticeTitle() {
        return noticeTitle;
    }
    /**
     * お知らせ内容を設定する
     */
    public void setNoticeCont(String noticeCont) {
        this.noticeCont = noticeCont;
    }
    
    /**
     * お知らせ内容を取得する
     */
    public String getNoticeCont() {
        return noticeCont;
    }
    /**
     * お知らせタップ区分を設定する
     */
    public void setNoticeTypeDiv(String noticeTypeDiv) {
        this.noticeTypeDiv = noticeTypeDiv;
    }
    
    /**
     * お知らせタップ区分を取得する
     */
    public String getNoticeTypeDiv() {
        return noticeTypeDiv;
    }
    /**
     * お知らせレベル区分を設定する
     */
    public void setNoticeLevelDiv(String noticeLevelDiv) {
        this.noticeLevelDiv = noticeLevelDiv;
    }
    
    /**
     * お知らせレベル区分を取得する
     */
    public String getNoticeLevelDiv() {
        return noticeLevelDiv;
    }
    /**
     * 掲載予定開始日時を設定する
     */
    public void setPubPlanStartDt(Timestamp pubPlanStartDt) {
        this.pubPlanStartDt = pubPlanStartDt;
    }
    
    /**
     * 掲載予定開始日時を取得する
     */
    public Timestamp getPubPlanStartDt() {
        return pubPlanStartDt;
    }
    /**
     * 掲載予定終了日時を設定する
     */
    public void setPubPlanEndDt(Timestamp pubPlanEndDt) {
        this.pubPlanEndDt = pubPlanEndDt;
    }
    
    /**
     * 掲載予定終了日時を取得する
     */
    public Timestamp getPubPlanEndDt() {
        return pubPlanEndDt;
    }
    /**
     * 全体配信フラグを設定する
     */
    public void setAllDeliverFlg(String allDeliverFlg) {
        this.allDeliverFlg = allDeliverFlg;
    }
    
    /**
     * 全体配信フラグを取得する
     */
    public String getAllDeliverFlg() {
        return allDeliverFlg;
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
     * 退避日時を設定する
     */
    public void setEvacuationDatime(Timestamp evacuationDatime) {
        this.evacuationDatime = evacuationDatime;
    }
    
    /**
     * 退避日時を取得する
     */
    public Timestamp getEvacuationDatime() {
        return evacuationDatime;
    }
    /**
     * 退避IDを設定する
     */
    public void setEvacuationUsrId(String evacuationUsrId) {
        this.evacuationUsrId = evacuationUsrId;
    }
    
    /**
     * 退避IDを取得する
     */
    public String getEvacuationUsrId() {
        return evacuationUsrId;
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
