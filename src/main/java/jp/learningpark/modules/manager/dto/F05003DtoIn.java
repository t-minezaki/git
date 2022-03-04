/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>F05002 知らせ新規画面 Dto</p >
 *
 * @author NWT : hujiaxing <br />
 * 変更履歴 <br />
 * 2019/06/18 : hujiaxing: 新規<br />
 * @version 1.0
 */
public class F05003DtoIn {
    /**
     * guard name
     */
    private String guardName;
    /**
     * student name
     */
    private String stuName;
    /**
     * user id
     */
    private String guardAfterId;
    /**
     * 組織ID
     */
    private String orgId;
    /**
     * student id (after id)
     */
    private String stuAfterId;
    /**
     * student id
     */
    private String stuId;
    /**
     * 保護者ID
     */
    private String guardId;

    /**
     * guard nameを取得する
     *
     * @return guardName guard name
     */
    public String getGuardName() {
        return this.guardName;
    }

    /**
     * guard nameを設定する
     *
     * @param guardName guard name
     */
    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

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
     * user idを取得する
     *
     * @return guardAfterId user id
     */
    public String getGuardAfterId() {
        return this.guardAfterId;
    }

    /**
     * user idを設定する
     *
     * @param guardAfterId user id
     */
    public void setGuardAfterId(String guardAfterId) {
        this.guardAfterId = guardAfterId;
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
     * student id (after id)を取得する
     *
     * @return stuAfterId student id (after id)
     */
    public String getStuAfterId() {
        return this.stuAfterId;
    }

    /**
     * student id (after id)を設定する
     *
     * @param stuAfterId student id (after id)
     */
    public void setStuAfterId(String stuAfterId) {
        this.stuAfterId = stuAfterId;
    }

    /**
     * student idを取得する
     *
     * @return stuId student id
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * student idを設定する
     *
     * @param stuId student id
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
}
