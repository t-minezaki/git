/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.util.List;

/**
 * push受信者
 */
public class pushMsgReceives {

    /**
     * 生徒ID
     */
    private List<String> stuId;

    /**
     * 受信者ID
     */
    private List<String> receiverId;

    /**
     * 生徒姓名
     */
    private String stuName;

    /**
     * メッセージの備考内容
     */
    private String comment;

    /**
     * メッセージtype
     */
    private String msgType;

    /**
     * メッセージ本文Code
     */
    private String messageCode;

    /**
     * 優先
     */
    private Integer priority;

    /**
     * 予定送信開始時間
     */
    private Long sendTime;

    /**
     * 受信者IDを取得する
     *
     * @return receiverId 受信者ID
     */
    public List<String> getReceiverId() {
        return this.receiverId;
    }

    /**
     * 受信者IDを設定する
     *
     * @param receiverId 受信者ID
     */
    public void setReceiverId(List<String> receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * 生徒姓名を取得する
     *
     * @return stuName 生徒姓名
     */
    public String getStuName() {
        return this.stuName;
    }

    /**
     * 生徒姓名を設定する
     *
     * @param stuName 生徒姓名
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * メッセージの備考内容を取得する
     *
     * @return comment メッセージの備考内容
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * メッセージの備考内容を設定する
     *
     * @param comment メッセージの備考内容
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * メッセージtypeを取得する
     *
     * @return msgType メッセージtype
     */
    public String getMsgType() {
        return this.msgType;
    }

    /**
     * メッセージtypeを設定する
     *
     * @param msgType メッセージtype
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * メッセージ本文Codeを取得する
     *
     * @return messageCode メッセージ本文Code
     */
    public String getMessageCode() {
        return this.messageCode;
    }

    /**
     * メッセージ本文Codeを設定する
     *
     * @param messageCode メッセージ本文Code
     */
    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    /**
     * 優先を取得する
     *
     * @return priority 優先
     */
    public Integer getPriority() {
        return this.priority;
    }

    /**
     * 優先を設定する
     *
     * @param priority 優先
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * 予定送信開始時間。を取得する
     *
     * @return sendTime 予定送信開始時間。
     */
    public Long getSendTime() {
        return this.sendTime;
    }

    /**
     * 予定送信開始時間。を設定する
     *
     * @param sendTime 予定送信開始時間。
     */
    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 生徒IDを取得する
     *
     * @return stuId 生徒ID
     */
    public List<String> getStuId() {
        return this.stuId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param stuId 生徒ID
     */
    public void setStuId(List<String> stuId) {
        this.stuId = stuId;
    }
}
