package jp.learningpark.modules.manager.dto;

import java.io.Serializable;

public class F08014StudentDto implements Serializable {

    /**
     * 学生の氏名
     */
    private String stuNm;

    /**
     * 保護者イベント申込状況.ID
     */
    private Integer guardEventApplyStsId;

    /**
     * 画面．学生の氏名
     *
     * @return stuNm 画面．学生の氏名
     */
    public String getStuNm() {
        return stuNm;
    }

    /**
     * 画面．学生の氏名
     *
     * @param stuNm 画面．学生の氏名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 画面．ID
     *
     * @return guardEventApplyStsId 画面．ID
     */
    public Integer getGuardEventApplyStsId() {
        return guardEventApplyStsId;
    }

    /**
     * 画面．ID
     *
     * @param guardEventApplyStsId 画面．ID
     */
    public void setGuardEventApplyStsId(Integer guardEventApplyStsId) {
        this.guardEventApplyStsId = guardEventApplyStsId;
    }
}
