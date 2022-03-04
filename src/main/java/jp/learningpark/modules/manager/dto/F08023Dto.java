package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstStuEntity;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/11/3 ： NWT)hxl ： 新規作成
 * @date 2020/11/3 11:12
 */
public class F08023Dto extends MstStuEntity {
    /**
     * 組織ID
     */
    private String orgId;
    /**
     * 生徒変更後ユーザID
     */
    private String stuLoginId;
    /**
     * 保護者変更後ユーザID
     */
    private String guardLoginId;
    /**
     * 生徒氏名
     */
    private String stuNm;
    /**
     * 保護者氏名
     */
    private String guardNm;
    /**
     * 生徒ユーザーID
     */
    private String stuUsrId;
    /**
     * 保護者ユーザーID
     */
    private String guardUsrId;
    /**
     * 閲覧状況区分
     */
    private String readingStsDiv;
    /**
     * メールアドレス
     */
    private String mailad;
    /**
     *指導報告書配信コード
     */
     private String guidReprDeliverCd;

    /**
     * 電話番号
     */
    private String telnum;

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

    /**
     * 保護者ユーザーIDを取得する
     *
     * @return guardUsrId 保護者ユーザーID
     */
    public String getGuardUsrId() {
        return this.guardUsrId;
    }

    /**
     * 保護者ユーザーIDを設定する
     *
     * @param guardUsrId 保護者ユーザーID
     */
    public void setGuardUsrId(String guardUsrId) {
        this.guardUsrId = guardUsrId;
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
     * 指導報告書配信コードを取得する
     *
     * @return guidReprDeliverCd 指導報告書配信コード
     */
    public String getGuidReprDeliverCd() {
        return this.guidReprDeliverCd;
    }

    /**
     * 指導報告書配信コードを設定する
     *
     * @param guidReprDeliverCd 指導報告書配信コード
     */
    public void setGuidReprDeliverCd(String guidReprDeliverCd) {
        this.guidReprDeliverCd = guidReprDeliverCd;
    }

    /**
     * 電話番号を取得する
     *
     * @return telnum 電話番号
     */
    public String getTelnum() {
        return this.telnum;
    }

    /**
     * 電話番号を設定する
     *
     * @param telnum 電話番号
     */
    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }
}
