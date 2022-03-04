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
 * メッセージ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_message")
public class MstMessageEntity implements Serializable {
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
     * 面談記録ID
     */
    private Integer talkId;

    /**
     * メッセージタイトル
     */
    @NotNull
    private String messageTitle;

    /**
     * メッセージ内容
     */
    private String messageCont;

    /**
     * メッセージタイプ区分
     */
    @NotNull
    private String messageTypeDiv;

    /**
     * メッセージレベル区分
     */
    @NotNull
    private String messageLevelDiv;

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
     * 面談記録IDを設定する
     */
    public void setTalkId(Integer talkId) {
        this.talkId = talkId;
    }
    
    /**
     * 面談記録IDを取得する
     */
    public Integer getTalkId() {
        return talkId;
    }
    /**
     * メッセージタイトルを設定する
     */
    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }
    
    /**
     * メッセージタイトルを取得する
     */
    public String getMessageTitle() {
        return messageTitle;
    }
    /**
     * メッセージ内容を設定する
     */
    public void setMessageCont(String messageCont) {
        this.messageCont = messageCont;
    }
    
    /**
     * メッセージ内容を取得する
     */
    public String getMessageCont() {
        return messageCont;
    }
    /**
     * メッセージタイプ区分を設定する
     */
    public void setMessageTypeDiv(String messageTypeDiv) {
        this.messageTypeDiv = messageTypeDiv;
    }
    
    /**
     * メッセージタイプ区分を取得する
     */
    public String getMessageTypeDiv() {
        return messageTypeDiv;
    }
    /**
     * メッセージレベル区分を設定する
     */
    public void setMessageLevelDiv(String messageLevelDiv) {
        this.messageLevelDiv = messageLevelDiv;
    }
    
    /**
     * メッセージレベル区分を取得する
     */
    public String getMessageLevelDiv() {
        return messageLevelDiv;
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
