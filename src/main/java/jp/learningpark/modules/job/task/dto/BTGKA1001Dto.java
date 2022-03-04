/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.task.dto;

/**
 * <p>イベント公開時定時メール送信日次バッチ dto</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/27 : wq: 新規<br />
 * @version 1.0
 */
public class BTGKA1001Dto {

    /**
     * イベント．ID
     */
    private Integer eventId;
    /**
     * 組織ID
     */
    private String orgId;
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * 保護者ID
     */
    private String guardId;
    /**
     * 変更後ユーザーID
     */
    private String afterUsrId;
    /**
     * 姓名_姓
     */
    private String flnmNm;
    /**
     * 姓名_名
     */
    private String flnmLnm;
    /**
     * 保護者氏名
     */
    private String guardNm;
    /**
     * メールアドレス
     */
    private String mailad;
    /**
     * ブランドコード
     */
    private String brandCd;
    /**
     * 組織名
     */
    private String orgNm;
    /**
     * イベントタイトル
     */
    private String eventTitle;
    /**
     * タイトル画像パス
     */
    private String titleImgPath;

    /**
     * イベント．IDを取得する
     *
     * @return eventId イベント．ID
     */
    public Integer getEventId() {
        return this.eventId;
    }

    /**
     * イベント．IDを設定する
     *
     * @param eventId イベント．ID
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
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
     * 保護者IDを取得する
     *
     * @return guardId 保護者ID
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * 保護者IDを設定する
     *
     * @param guardId 保護者ID
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * 変更後ユーザーIDを取得する
     *
     * @return afterUsrId 変更後ユーザーID
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * 変更後ユーザーIDを設定する
     *
     * @param afterUsrId 変更後ユーザーID
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * 姓名_姓を取得する
     *
     * @return flnmNm 姓名_姓
     */
    public String getFlnmNm() {
        return this.flnmNm;
    }

    /**
     * 姓名_姓を設定する
     *
     * @param flnmNm 姓名_姓
     */
    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    /**
     * 姓名_名を取得する
     *
     * @return flnmLnm 姓名_名
     */
    public String getFlnmLnm() {
        return this.flnmLnm;
    }

    /**
     * 姓名_名を設定する
     *
     * @param flnmLnm 姓名_名
     */
    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }

    /**
     * ブランドコードを取得する
     *
     * @return brandCd ブランドコード
     */
    public String getBrandCd() {
        return this.brandCd;
    }

    /**
     * ブランドコードを設定する
     *
     * @param brandCd ブランドコード
     */
    public void setBrandCd(String brandCd) {
        this.brandCd = brandCd;
    }

    /**
     * 組織名を取得する
     *
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織名を設定する
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * メールアドレスを取得する
     *
     * @return mailad メールアドレス
     */
    public String getMailad() {
        return this.mailad;
    }

    /**
     * メールアドレスを設定する
     *
     * @param mailad メールアドレス
     */
    public void setMailad(String mailad) {
        this.mailad = mailad;
    }

    /**
     * 保護者氏名を取得する
     *
     * @return guardNm 保護者氏名
     */
    public String getGuardNm() {
        return this.guardNm;
    }

    /**
     * 保護者氏名を設定する
     *
     * @param guardNm 保護者氏名
     */
    public void setGuardNm(String guardNm) {
        this.guardNm = guardNm;
    }

    /**
     * イベントタイトルを取得する
     *
     * @return eventTitle イベントタイトル
     */
    public String getEventTitle() {
        return this.eventTitle;
    }

    /**
     * イベントタイトルを設定する
     *
     * @param eventTitle イベントタイトル
     */
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    /**
     * タイトル画像パスを取得する
     *
     * @return titleImgPath タイトル画像パス
     */
    public String getTitleImgPath() {
        return this.titleImgPath;
    }

    /**
     * タイトル画像パスを設定する
     *
     * @param titleImgPath タイトル画像パス
     */
    public void setTitleImgPath(String titleImgPath) {
        this.titleImgPath = titleImgPath;
    }
}
