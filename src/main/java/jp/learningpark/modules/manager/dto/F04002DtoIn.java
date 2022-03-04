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
 * 2019/3/7 : gong: 新規<br />
 * @version 1.0
 */
public class F04002DtoIn {
    /**
     * 保護者の名前
     */
    private String guardName;
    /**
     *
     * 学生の名前
     */
    private String stuName;
    /**
     * userId
     */
    private String usrId;
    /**
     * 組織ID
     */
    private String orgId;
    /**
     * 生徒ID
     */
    private String sId;
    /**
     * 保護者ID
     */
    private String gId;
    /**
     * 組織名+学生名
     */
    private String guardStuNm;
    /**
     * 保護者ID
     */
    private String gUserId;
    /**
     * 保護者の名前を取得する
     *
     * @return guardName 保護者の名前
     */
    public String getGuardName() {
        return this.guardName;
    }

    /**
     * 保護者の名前を設定する
     *
     * @param guardName 保護者の名前
     */
    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    /**
     * 学生の名前を取得する
     *
     * @return stuName 学生の名前
     */
    public String getStuName() {
        return this.stuName;
    }

    /**
     * 学生の名前を設定する
     *
     * @param stuName 学生の名前
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * userIdを取得する
     *
     * @return usrId userId
     */
    public String getUsrId() {
        return this.usrId;
    }

    /**
     * userIdを設定する
     *
     * @param usrId userId
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
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
     * @return sId 生徒ID
     */
    public String getSId() {
        return this.sId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param sId 生徒ID
     */
    public void setSId(String sId) {
        this.sId = sId;
    }

    /**
     * 保護者IDを取得する
     *
     * @return gId 保護者ID
     */
    public String getGId() {
        return this.gId;
    }

    /**
     * 保護者IDを設定する
     *
     * @param gId 保護者ID
     */
    public void setGId(String gId) {
        this.gId = gId;
    }

    /**
     * 組織名+学生名を取得する
     *
     * @return guardStuNm 組織名+学生名
     */
    public String getGuardStuNm() {
        return this.guardStuNm;
    }

    /**
     * 組織名+学生名を設定する
     *
     * @param guardStuNm 組織名+学生名
     */
    public void setGuardStuNm(String guardStuNm) {
        this.guardStuNm = guardStuNm;
    }

    /**
     * 保護者IDを取得する
     * @return
     */
    public String getgUserId() {
        return gUserId;
    }

    /**
     * 保護者ID を設定する
     * @param gUserId
     */
    public void setgUserId(String gUserId) {
        this.gUserId = gUserId;
    }
}
