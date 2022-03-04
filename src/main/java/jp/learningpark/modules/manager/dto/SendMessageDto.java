/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.util.List;

/**
 * <p>F09005_代理入退登録確認画面 SendMessageDto</p>
 * <p></p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/07/13 : xie: 新規<br />
 * @version 7.1
 */
public class SendMessageDto {
    /**
     * メッセージの備考内容。
     */
    private String comment;
    /**
     * 受信先デバイスIDの集合
     */
    private List<String> deviceList;
    /**
     * メッセージに含まれる画像のリンク。
     */
    private String imgUrl;
    /**
     * メッセージ本文
     */
    private String message;
    /**
     * シート「お知らせ送信」1.1
     */
    private String params;
    /**
     * 優先
     */
    private Integer priority;
    /**
     * お知らせの予定送信開始時間。
     */
    private String sendTime;
    /**
     * お知らせのタイトル（プッシュタイトル）
     */
    private String title;
    /**
     * トークン
     */
    private String token;

    /**
     *  メッセージの備考内容を取得する
     *
     * @return comment メッセージの備考内容
     */
    public String getComment() {
        return comment;
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
     *  メッセージに含まれる画像のリンクを取得する
     *
     * @return imgUrl メッセージに含まれる画像のリンク
     */
    public String getImgUrl() {
        return imgUrl;
    }
    /**
     * メッセージに含まれる画像のリンクを設定する
     *
     * @param imgUrl メッセージに含まれる画像のリンク
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     *  メッセージ本文を取得する
     *
     * @return message メッセージ本文
     */
    public String getMessage() {
        return message;
    }
    /**
     * メッセージ本文を設定する
     *
     * @param message メッセージ本文
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *  シート「お知らせ送信」1.1を取得する
     *
     * @return params シート「お知らせ送信」1.1
     */
    public String getParams() {
        return params;
    }
    /**
     * シート「お知らせ送信」1.1を設定する
     *
     * @param params シート「お知らせ送信」1.1
     */
    public void setParams(String params) {
        this.params = params;
    }
    /**
     *  優先を取得する
     *
     * @return priority 優先
     */
    public Integer getPriority() {
        return priority;
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
     *  お知らせの予定送信開始時間を取得する
     *
     * @return sendTime お知らせの予定送信開始時間
     */
    public String getSendTime() {
        return sendTime;
    }
    /**
     * お知らせの予定送信開始時間を設定する
     *
     * @param sendTime お知らせの予定送信開始時間
     */
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
    /**
     *  お知らせのタイトルを取得する
     *
     * @return title お知らせのタイトル
     */
    public String getTitle() {
        return title;
    }
    /**
     * お知らせのタイトルを設定する
     *
     * @param title お知らせのタイトル
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     *  トークンを取得する
     *
     * @return token トークン
     */
    public String getToken() {
        return token;
    }
    /**
     * トークンを設定する
     *
     * @param token トークン
     */
    public void setToken(String token) {
        this.token = token;
    }


    /**
     * 受信先デバイスIDの集合を取得する
     *
     * @return deviceList 受信先デバイスIDの集合
     */
    public List<String> getDeviceList() {
        return this.deviceList;
    }

    /**
     * 受信先デバイスIDの集合を設定する
     *
     * @param deviceList 受信先デバイスIDの集合
     */
    public void setDeviceList(List<String> deviceList) {
        this.deviceList = deviceList;
    }
}
