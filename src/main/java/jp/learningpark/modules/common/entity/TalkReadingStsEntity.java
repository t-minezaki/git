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
 * 面談メッセージ閲覧状況
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("talk_reading_sts")
public class TalkReadingStsEntity implements Serializable {
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
     * メッセージID
     */
    @NotNull
    private Integer messageId;

    /**
     * 配信先ID
     */
    private String deliverId;

    /**
     * 閲覧状況区分
     */
    @NotNull
    private String readingStsDiv;

    /**
     * 質問事項表示フラグ
     */
    private String askDispFlg;

    /**
     * 面談事項表示フラグ
     */
    private String talkDispFlg;

    /**
     * 開封状況区分
     */
    private String openedFlg;

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
     * メッセージIDを設定する
     */
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
    
    /**
     * メッセージIDを取得する
     */
    public Integer getMessageId() {
        return messageId;
    }
    /**
     * 配信先IDを設定する
     */
    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }
    
    /**
     * 配信先IDを取得する
     */
    public String getDeliverId() {
        return deliverId;
    }
    /**
     * 閲覧状況区分を設定する
     */
    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }
    
    /**
     * 閲覧状況区分を取得する
     */
    public String getReadingStsDiv() {
        return readingStsDiv;
    }
    /**
     * 質問事項表示フラグを設定する
     */
    public void setAskDispFlg(String askDispFlg) {
        this.askDispFlg = askDispFlg;
    }
    
    /**
     * 質問事項表示フラグを取得する
     */
    public String getAskDispFlg() {
        return askDispFlg;
    }
    /**
     * 面談事項表示フラグを設定する
     */
    public void setTalkDispFlg(String talkDispFlg) {
        this.talkDispFlg = talkDispFlg;
    }
    
    /**
     * 面談事項表示フラグを取得する
     */
    public String getTalkDispFlg() {
        return talkDispFlg;
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

    /**
     * 開封状況区分を取得する
     *
     * @return openedFlg 開封状況区分
     */
    public String getOpenedFlg() {
        return this.openedFlg;
    }

    /**
     * 開封状況区分を設定する
     *
     * @param openedFlg 開封状況区分
     */
    public void setOpenedFlg(String openedFlg) {
        this.openedFlg = openedFlg;
    }
}
