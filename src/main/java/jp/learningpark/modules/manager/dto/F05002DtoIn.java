/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;


/**
 * <p>F05002 知らせ新規画面 Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/06/24 : hujiaxing: 新規<br />
 *
 * @version 1.0
 */
public class F05002DtoIn{
    /**
     * student name
     */
    private String stuName;
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * 組織ID
     */
    private String orgId;
    /**
     * 生徒変更後ユーザーID
     */
    private String  stuAfterId;
    /**
     * 保護者変更後ユーザーID
     */
    private String  guardAfterId;
    /**
     * 保護者Id
     */
    private String  guardId;

    /**
     * student nameを取得する
     *
     * @return stuName student name
     */
    public String getStuName() {
        return this.stuName;
    }

    /**
     * student nameを設定する
     *
     * @param stuName student name
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
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
     * 生徒変更後ユーザーIDを取得する
     *
     * @return stuAfterId 生徒変更後ユーザーID
     */
    public String getStuAfterId() {
        return this.stuAfterId;
    }

    /**
     * 生徒変更後ユーザーIDを設定する
     *
     * @param stuAfterId 生徒変更後ユーザーID
     */
    public void setStuAfterId(String stuAfterId) {
        this.stuAfterId = stuAfterId;
    }

    /**
     * 保護者変更後ユーザーIDを取得する
     *
     * @return guardAfterId 保護者変更後ユーザーID
     */
    public String getGuardAfterId() {
        return this.guardAfterId;
    }

    /**
     * 保護者変更後ユーザーIDを設定する
     *
     * @param guardAfterId 保護者変更後ユーザーID
     */
    public void setGuardAfterId(String guardAfterId) {
        this.guardAfterId = guardAfterId;
    }

    /**
     * 保護者Idを取得する
     *
     * @return guardId 保護者Id
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * 保護者Idを設定する
     *
     * @param guardId 保護者Id
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }
}
