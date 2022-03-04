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
public class F04003DtoIn {
    private String guardName;
    private String stuName;
    private String usrId;
    private String orgId;
    private String sId;
    private String gId;
    private String guardStuNm;
    /**
     * 保護者ID
     */
    private String gUserId;

    public String getGuardStuNm() {
        return guardStuNm;
    }

    public void setGuardStuNm(String guardStuNm) {
        this.guardStuNm = guardStuNm;
    }

    public String getGuardName() {
        return guardName;
    }

    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }



    public String getUsrId() {
        return usrId;
    }

    public String getOrgId() {
        return orgId;
    }





    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsId() {
        return sId;
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
