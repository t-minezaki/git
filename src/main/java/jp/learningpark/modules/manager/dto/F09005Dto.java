package jp.learningpark.modules.manager.dto;

import java.io.Serializable;

public class F09005Dto implements Serializable {
    /**
     *組織CD
     */
    private String orgCd;
    /**
     *生徒ID
     */
    private String stuId;
    /**
     *生徒ID1
     */
    private String stuId1;
    /**
     * 生徒名
     */
    private String stuNm;
    /**
     * 学年
     */
    private String schyDiv;
    /**
     * ポイント
     */
    private Integer point;
    /**
     * 時間
     */
    private  String time;
    /**
     * ステータス
     */
    private Integer stauts;
    /**
     * 保護者ID
     */
    private Integer id;
    private String entry_Dt;
    private Integer entry_Flg;
    private  String dx;

    private String guardId;

    public String getOrgCd() {
        return orgCd;
    }

    public void setOrgCd(String orgCd) {
        this.orgCd = orgCd;
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

    public String getSchyDiv() {
        return schyDiv;
    }

    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getStauts() {
        return stauts;
    }

    public void setStauts(Integer stauts) {
        this.stauts = stauts;
    }

    public String getGuardId() {
        return guardId;
    }

    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getStuId1() {
        return stuId1;
    }

    public void setStuId1(String stuId1) {
        this.stuId1 = stuId1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntry_Dt() {
        return entry_Dt;
    }

    public void setEntry_Dt(String entry_Dt) {
        this.entry_Dt = entry_Dt;
    }

    public Integer getEntry_Flg() {
        return entry_Flg;
    }

    public void setEntry_Flg(Integer entry_Flg) {
        this.entry_Flg = entry_Flg;
    }

    public String getDx() {
        return dx;
    }

    public void setDx(String dx) {
        this.dx = dx;
    }
}
