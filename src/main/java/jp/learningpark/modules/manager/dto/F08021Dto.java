package jp.learningpark.modules.manager.dto;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/11/9 ： NWT)wyh ： 新規作成
 * @date 2020/11/9 18:12
 */
public class F08021Dto {
    /**
     * 組織ID
     */
    private String orgId;

    /**
     * メッセージ．ＩＤ
     * * @return
     */
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    /**
     * メッセージ．ＩＤ
     * @return
     */
    private String messageId;
    public String getMessageTitle() {
        return this.messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }
    /**
     * お知らせタイトル
     */
    private String messageTitle;
    /**
     * 掲載予定開始日時
     */
    private String pubPlanStartDt;
    /**
     * 生徒変更後ユーザID
     */
    private String stuLoginId;
    /**
     * 生徒氏名
     */
    private String stuNm;
    /**
     * 生徒ユーザーID
     */
    private String stuUsrId;
    /**
     * 閲覧状況区分
     */
    private String readingStsDiv;
    /**
     * 開封状況区分
     */
    private String openedFlg;
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
     * 生徒氏名を取得する
     *
     * @return stuNm 生徒氏名
     */
    public String getStuNm() {
        return this.stuNm;
    }
    /**
     * 生徒氏名を設定する
     *
     * @param stuNm 生徒氏名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
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
     * 掲載予定開始日時を取得する
     *
     * @return pubPlanStartDt 掲載予定開始日時
     */
    public String getPubPlanStartDt() {
        return this.pubPlanStartDt;
    }
    /**
     * 掲載予定開始日時を設定する
     *
     * @param pubPlanStartDt 掲載予定開始日時
     */
    public void setPubPlanStartDt(String pubPlanStartDt) {
        this.pubPlanStartDt = pubPlanStartDt;
    }
    /**
     * 生徒ユーザーIDを取得する
     *
     * @return stuUsrId 生徒ユーザーID
     */
    public String getStuUsrId() {
        return this.stuUsrId;
    }
    /**
     * 生徒ユーザーIDを設定する
     *
     * @param stuUsrId 生徒ユーザーID
     */
    public void setStuUsrId(String stuUsrId) {
        this.stuUsrId = stuUsrId;
    }
}