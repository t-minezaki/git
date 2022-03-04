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
 * プッシュ失敗データ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("push_err_dat")
public class PushErrDatEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 配送先ID
     */
    @NotNull
    private String deliverId;

    /**
     * メッセージ区分
     */
    @NotNull
    private String msgTypeDiv;

    /**
     * メッセージID
     */
    @NotNull
    private Integer msgId;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * エラー件数
     */
    private Integer errDatCnt;

    /**
     * 送信ID
     */
    private Integer sendId;

    /**
     * デバイスID
     */
    private Integer deviceId;

    /**
     * 予定送信時間
     */
    private String sendStatTime;

    /**
     * システム異常内容
     */
    private String exceptionInfo;

    /**
     * メッセージタイトル
     */
    private String msgTitle;

    /**
     * メッセージ本文
     */
    private String msgTxt;

    /**
     * 付属パラメータ
     */
    private String otherPara;

    /**
     * メッセージ優先度
     */
    private Integer priority;

    /**
     * メッセージ備考
     */
    private String msgComt;

    /**
     * 異常内容
     */
    private String errCont;

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
     * 生徒ID
     */
    private String stuId;

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
     * 配送先IDを設定する
     */
    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }
    
    /**
     * 配送先IDを取得する
     */
    public String getDeliverId() {
        return deliverId;
    }
    /**
     * メッセージ区分を設定する
     */
    public void setMsgTypeDiv(String msgTypeDiv) {
        this.msgTypeDiv = msgTypeDiv;
    }
    
    /**
     * メッセージ区分を取得する
     */
    public String getMsgTypeDiv() {
        return msgTypeDiv;
    }
    /**
     * メッセージIDを設定する
     */
    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }
    
    /**
     * メッセージIDを取得する
     */
    public Integer getMsgId() {
        return msgId;
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
     * エラー件数を設定する
     */
    public void setErrDatCnt(Integer errDatCnt) {
        this.errDatCnt = errDatCnt;
    }
    
    /**
     * エラー件数を取得する
     */
    public Integer getErrDatCnt() {
        return errDatCnt;
    }
    /**
     * 送信IDを設定する
     */
    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }
    
    /**
     * 送信IDを取得する
     */
    public Integer getSendId() {
        return sendId;
    }
    /**
     * デバイスIDを設定する
     */
    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }
    
    /**
     * デバイスIDを取得する
     */
    public Integer getDeviceId() {
        return deviceId;
    }
    /**
     * 予定送信時間を設定する
     */
    public void setSendStatTime(String sendStatTime) {
        this.sendStatTime = sendStatTime;
    }
    
    /**
     * 予定送信時間を取得する
     */
    public String getSendStatTime() {
        return sendStatTime;
    }
    /**
     * システム異常内容を設定する
     */
    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }
    
    /**
     * システム異常内容を取得する
     */
    public String getExceptionInfo() {
        return exceptionInfo;
    }
    /**
     * メッセージタイトルを設定する
     */
    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }
    
    /**
     * メッセージタイトルを取得する
     */
    public String getMsgTitle() {
        return msgTitle;
    }
    /**
     * メッセージ本文を設定する
     */
    public void setMsgTxt(String msgTxt) {
        this.msgTxt = msgTxt;
    }
    
    /**
     * メッセージ本文を取得する
     */
    public String getMsgTxt() {
        return msgTxt;
    }
    /**
     * 付属パラメータを設定する
     */
    public void setOtherPara(String otherPara) {
        this.otherPara = otherPara;
    }
    
    /**
     * 付属パラメータを取得する
     */
    public String getOtherPara() {
        return otherPara;
    }
    /**
     * メッセージ優先度を設定する
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
    /**
     * メッセージ優先度を取得する
     */
    public Integer getPriority() {
        return priority;
    }
    /**
     * メッセージ備考を設定する
     */
    public void setMsgComt(String msgComt) {
        this.msgComt = msgComt;
    }
    
    /**
     * メッセージ備考を取得する
     */
    public String getMsgComt() {
        return msgComt;
    }
    /**
     * 異常内容を設定する
     */
    public void setErrCont(String errCont) {
        this.errCont = errCont;
    }
    
    /**
     * 異常内容を取得する
     */
    public String getErrCont() {
        return errCont;
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
}
