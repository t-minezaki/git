package jp.learningpark.modules.manager.dto;

import java.io.Serializable;

public class F09004Dto implements Serializable {

    /**
     * 組織コード
     */
    private String orgCd;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * 生徒名
     */
    private String stuNm;

    /**
     * 学年
     */
    private String schyDiv;

    /**
     * ユーザID
     */
    private String usrId;

    /**
     * 変更後ユーザーＩＤ
     */
    private String afterUsrId;

    /**
     * 保護者ID
     */
    private String guardId;

    /**
     * ポイント
     */
    private Integer point;

    /**
     * 組織コード
     * @return 組織コード
     */
    public String getOrgCd() {
        return orgCd;
    }

    /**
     * 組織名
     * @param orgCd 組織コード
     */
    public void setOrgCd(String orgCd) {
        this.orgCd = orgCd;
    }

    /**
     * 生徒ID
     * @return 生徒ID
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * 生徒ID
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 生徒名
     * @return 生徒名
     */
    public String getStuNm() {
        return stuNm;
    }

    /**
     * 生徒名
     * @param stuNm 生徒名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 学年
     * @return 学年
     */
    public String getSchyDiv() {
        return schyDiv;
    }

    /**
     * 学年
     * @param schyDiv 学年
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * ユーザID
     * @return ユーザID
     */
    public String getUsrId() {
        return usrId;
    }

    /**
     * ユーザID
     * @param usrId ユーザID
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     * 変更後ユーザーＩＤ
     * @return 変更後ユーザーＩＤ
     */
    public String getAfterUsrId() {
        return afterUsrId;
    }

    /**
     * 変更後ユーザーＩＤ
     * @param afterUsrId 変更後ユーザーＩＤ
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * 保護者ID
     * @return 保護者ID
     */
    public String getGuardId() {
        return guardId;
    }

    /**
     * 保護者ID
     * @param guardId 保護者ID
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * ポイント
     * @return ポイント
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * ポイント
     * @param point ポイント
     */
    public void setPoint(Integer point) {
        this.point = point;
    }
}
