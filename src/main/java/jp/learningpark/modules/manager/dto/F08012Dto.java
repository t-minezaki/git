package jp.learningpark.modules.manager.dto;

import java.io.Serializable;

public class F08012Dto implements Serializable {

    private String stuId;
    private String guardId;
    private String afterUsrId;
    private String schyDiv;
    private String stuName;
    private String stuNm;
    private boolean readFlg;
    private String codCd;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getGuardId() {
        return guardId;
    }

    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    public String getStuNm() {
        return stuNm;
    }

    public void setStuNm(String guardStu) {
        this.stuNm = guardStu;
    }

    /**
     * ���擾����
     *
     * @return afterUsrId
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * ��ݒ肷��
     *
     * @param afterUsrId
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * ���擾����
     *
     * @return stuname
     */
    public String getStuName() {
        return this.stuName;
    }

    /**
     * ��ݒ肷��
     *
     * @param stuName
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * ���擾����
     *
     * @return schyDiv
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * ��ݒ肷��
     *
     * @param schyDiv
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * ���擾����
     *
     * @return readFlg
     */
    public boolean isReadFlg() {
        return this.readFlg;
    }

    /**
     * ��ݒ肷��
     *
     * @param readFlg
     */
    public void setReadFlg(boolean readFlg) {
        this.readFlg = readFlg;
    }

    /**
     * を取得する
     *
     * @return codCd
     */
    public String getCodCd() {
        return this.codCd;
    }

    /**
     * を設定する
     *
     * @param codCd
     */
    public void setCodCd(String codCd) {
        this.codCd = codCd;
    }
}
