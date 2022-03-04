package jp.learningpark.modules.manager.dto;

import java.sql.Timestamp;

/**
 * <p>F08022_未読・未回答者送信一覧画面（インフォメーション） Dto</p>
 *
 * @author NWT文
 * @version 9.0
 * 変更履歴:
 * 2020/11/3 ： NWT文 ： 新規作成
 */
public class F08022Dto {
    /**
     * 組織ID
     */
    private String orgId;
    /**
     * メッセージ タイトル
     */
    private String messageTitle;
    /**
     * 掲載予定開始日時
     */
    private Timestamp pubPlanStartDt;
    /**
     * 掲載予定終了日時
     */
    private Timestamp pubPlanEndDt;
    /**
     * 生徒変更後ユーザID
     */
    private String stuLoginId;
    /**
     * 保護者変更後ユーザID
     */
    private String guardLoginId;
    /**
     * 名
     */
    private String userName;
    /**
     * ID
     */
    private String afterUserId;
    /**
     * 一意ユーザーID
     */
    private String usrId;

    /**
     * 配信先ID
     */
    private String deliverId;
    /**
     * 閲覧状況区分
     */
    private String readingStsDiv;
    /**
     * 開封状況区分
     */
    private String openedFlg;

    /**
     * 閲覧状況ステータス
     */
    private String status;

    /**
     * 組織IDを取得する
     *
     * @return orgId 組織ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織IDを設定する
     *
     * @param orgId 組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * メッセージＩＤタイトルを取得する
     *
     * @return messageTitle メッセージＩＤタイトル
     */
    public String getMessageTitle() {
        return this.messageTitle;
    }

    /**
     * メッセージＩＤタイトルを設定する
     *
     * @param messageTitle メッセージＩＤタイトル
     */
    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    /**
     * 掲載予定開始日時を取得する
     *
     * @return pubPlanStartDt 掲載予定開始日時
     */
    public Timestamp getPubPlanStartDt() {
        if (pubPlanStartDt != null) {
            //            return new Timestamp(this.pubPlanStartDt.getTime());
            //or
            return (Timestamp)this.pubPlanStartDt.clone();
        } else {
            return null;
        }
    }

    /**
     * 掲載予定開始日時を設定する
     *
     * @param pubPlanStartDt 掲載予定開始日時
     */
    public void setPubPlanStartDt(Timestamp pubPlanStartDt) {
        if (pubPlanStartDt != null) {
            //            this.pubPlanStartDt = new Timestamp(pubPlanStartDt.getTime());
            // or
            this.pubPlanStartDt = (Timestamp)pubPlanStartDt.clone();
        } else {
            this.pubPlanStartDt = null;
        }
    }

    /**
     * 掲載予定終了日時を取得する
     *
     * @return pubPlanEndDt 掲載予定終了日時
     */
    public Timestamp getPubPlanEndDt() {
        if (pubPlanEndDt != null) {
            //            return new Timestamp(this.pubPlanEndDt.getTime());
            //or
            return (Timestamp)this.pubPlanEndDt.clone();
        } else {
            return null;
        }
    }

    /**
     * 掲載予定終了日時を設定する
     *
     * @param pubPlanEndDt 掲載予定終了日時
     */
    public void setPubPlanEndDt(Timestamp pubPlanEndDt) {
        if (pubPlanEndDt != null) {
            //            this.pubPlanEndDt = new Timestamp(pubPlanEndDt.getTime());
            // or
            this.pubPlanEndDt = (Timestamp)pubPlanEndDt.clone();
        } else {
            this.pubPlanEndDt = null;
        }
    }

    /**
     * 生徒変更後ユーザIDを取得する
     *
     * @return stuLoginId 生徒変更後ユーザID
     */
    public String getStuLoginId() {
        return this.stuLoginId;
    }

    /**
     * 生徒変更後ユーザIDを設定する
     *
     * @param stuLoginId 生徒変更後ユーザID
     */
    public void setStuLoginId(String stuLoginId) {
        this.stuLoginId = stuLoginId;
    }

    /**
     * 保護者変更後ユーザIDを取得する
     *
     * @return guardLoginId 保護者変更後ユーザID
     */
    public String getGuardLoginId() {
        return this.guardLoginId;
    }

    /**
     * 保護者変更後ユーザIDを設定する
     *
     * @param guardLoginId 保護者変更後ユーザID
     */
    public void setGuardLoginId(String guardLoginId) {
        this.guardLoginId = guardLoginId;
    }

    /**
     * 名を取得する
     *
     * @return userName 名
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * 名を設定する
     *
     * @param userName 名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * IDを取得する
     *
     * @return afterUserId ID
     */
    public String getAfterUserId() {
        return this.afterUserId;
    }

    /**
     * IDを設定する
     *
     * @param afterUserId ID
     */
    public void setAfterUserId(String afterUserId) {
        this.afterUserId = afterUserId;
    }

    /**
     * 一意ユーザーIDを取得する
     *
     * @return usrId 一意ユーザーID
     */
    public String getUsrId() {
        return this.usrId;
    }

    /**
     * 一意ユーザーIDを設定する
     *
     * @param usrId 一意ユーザーID
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     * 配信先IDを取得する
     *
     * @return deliverId 配信先ID
     */
    public String getDeliverId() {
        return this.deliverId;
    }

    /**
     * 配信先IDを設定する
     *
     * @param deliverId 配信先ID
     */
    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    /**
     * 閲覧状況区分を取得する
     *
     * @return readingStsDiv 閲覧状況区分
     */
    public String getReadingStsDiv() {
        return this.readingStsDiv;
    }

    /**
     * 閲覧状況区分を設定する
     *
     * @param readingStsDiv 閲覧状況区分
     */
    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
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

    /**
     * 閲覧状況ステータスを取得する
     *
     * @return status 閲覧状況ステータス
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 閲覧状況ステータスを設定する
     *
     * @param status 閲覧状況ステータス
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
