package jp.learningpark.modules.manager.dto;

import java.io.Serializable;

public class F08010Dto implements Serializable {

    private String stuId;
    private String guardId;
    private String afterUsrId;
    private String schyDiv;
    private String stuName;
    private String stuNm;
    private boolean readFlg;
    private String replyFlg;
    /* NWT崔 manmiru1-726 2021/07/07 add start */
    private String orgId;
    /* NWT崔 manmiru1-726 2021/07/07 add end */

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
     * @return stuName
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
     * を取得する
     *
     * @return readFlg
     */
    public boolean isReadFlg() {
        return this.readFlg;
    }

    /**
     * を設定する
     *
     * @param readFlg
     */
    public void setReadFlg(boolean readFlg) {
        this.readFlg = readFlg;
    }

    /**
     * を取得する
     *
     * @return replyFlg
     */
    public String getReplyFlg() {
        return this.replyFlg;
    }

    /**
     * を設定する
     *
     * @param replyFlg
     */
    public void setReplyFlg(String replyFlg) {
        this.replyFlg = replyFlg;
    }
    /* NWT崔 2021/07/07 manmiru1-726 add start */
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    /* NWT崔 2021/07/07 manmiru1-726 add end */
}
