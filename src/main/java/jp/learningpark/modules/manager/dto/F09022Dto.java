/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstStuEntity;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/02/27 : lyh: 新規<br />
 * @version 1.0
 */
public class F09022Dto extends MstStuEntity {
    /**
     *生徒名
     */
    private String stuName;
    /**
     *コードマスタ_明細・コード値
     */
    private String codValue;
    /**
     * 保護者お知らせ閲覧状況・保護者ID
     */
    private String guardId;
    /**
     * 保護者お知らせ閲覧状況・閲覧状況区分
     */
    private String readingStsDiv;
    /**
     * 保護者基本マスタ・電話番号
     */
    private String telnum;

    /**
     * コードマスタ_明細・コード値を取得する
     *
     * @return codValue コードマスタ_明細・コード値
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * コードマスタ_明細・コード値を設定する
     *
     * @param codValue コードマスタ_明細・コード値
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * 保護者お知らせ閲覧状況・保護者IDを取得する
     *
     * @return guardId 保護者お知らせ閲覧状況・保護者ID
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * 保護者お知らせ閲覧状況・保護者IDを設定する
     *
     * @param guardId 保護者お知らせ閲覧状況・保護者ID
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * 保護者お知らせ閲覧状況・閲覧状況区分を取得する
     *
     * @return readingStsDiv 保護者お知らせ閲覧状況・閲覧状況区分
     */
    public String getReadingStsDiv() {
        return this.readingStsDiv;
    }

    /**
     * 保護者お知らせ閲覧状況・閲覧状況区分を設定する
     *
     * @param readingStsDiv 保護者お知らせ閲覧状況・閲覧状況区分
     */
    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }

    /**
     * 生徒名を取得する
     *
     * @return stuName 生徒名
     */
    public String getStuName() {
        return this.stuName;
    }

    /**
     * 生徒名を設定する
     *
     * @param stuName 生徒名
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * 保護者基本マスタ・電話番号を取得する
     *
     * @return telnum 保護者基本マスタ・電話番号
     */
    public String getTelnum() {
        return this.telnum;
    }

    /**
     * 保護者基本マスタ・電話番号を設定する
     *
     * @param telnum 保護者基本マスタ・電話番号
     */
    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }
}