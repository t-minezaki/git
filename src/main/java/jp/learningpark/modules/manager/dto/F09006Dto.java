package jp.learningpark.modules.manager.dto;

public class F09006Dto {
    private String usrId;

    /**
     * コードマスタ．コードＣＤ
     */
    private String codCd;
    /**
     * コードマスタ．コード値
     */
    private String codValue;
    /**
     *	組織ID
     */
    private String orgId;
    /**
     * 組織名
     */
    private String orgNm;
    /**
     *ユーザ基本マスタ．変更後ユーザーＩＤ(生徒ID)
     */
    private String stuId;
    /**
     * 生徒基本マスタ．姓名
     */
    private String stuNm;
    /**
     * コード値（学年）
     */
    private String schy;
    /**
     * 生徒ポイント．現在ポイント
     */
    private String point;
    /**
     * 生徒ポイント．調整ポイント
     */
    private Integer movePoint;
    /**
     * 生徒ポイント. 累計ポイント
     */
    private Integer allPoint;

    private  Integer pointNum;

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getCodCd() {
        return codCd;
    }

    public void setCodCd(String codCd) {
        this.codCd = codCd;
    }

    public String getCodValue() {
        return codValue;
    }

    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgNm() {
        return orgNm;
    }

    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuNm() {
        return stuNm;
    }

    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    public String getSchy() {
        return schy;
    }

    public void setSchy(String schy) {
        this.schy = schy;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Integer getAllPoint() { return allPoint; }

    public void setAllPoint(Integer allPoint) { this.allPoint = allPoint;}

    /**
     * 生徒ポイント．調整ポイントを取得する
     *
     * @return movePoint 生徒ポイント．調整ポイント
     */
    public Integer getMovePoint() {
        return this.movePoint;
    }

    /**
     * 生徒ポイント．調整ポイントを設定する
     *
     * @param movePoint 生徒ポイント．調整ポイント
     */
    public void setMovePoint(Integer movePoint) {
        this.movePoint = movePoint;
    }

    /**
     * を取得する
     *
     * @return pointNum
     */
    public Integer getPointNum() {
        return this.pointNum;
    }

    /**
     * を設定する
     *
     * @param pointNum
     */
    public void setPointNum(Integer pointNum) {
        this.pointNum = pointNum;
    }
}
