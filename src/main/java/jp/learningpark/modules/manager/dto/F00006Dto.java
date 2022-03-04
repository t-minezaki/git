/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>F00006 メンター生徒管理画面 Dto</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/12/27 : wen: 新規<br />
 * @version 1.0
 */
public class F00006Dto {
    /**
     * メンターー生徒ID（システム採番）
     */
    private Integer id;

    /**
     * メンターID
     */
    private String mentorId;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * メンターIDを取得する
     *
     * @return mentorId メンターID
     */
    public String getMentorId() {
        return this.mentorId;
    }

    /**
     * メンターIDを設定する
     *
     * @param mentorId メンターID
     */
    public void setMentorId(String mentorId) {
        this.mentorId = mentorId;
    }

    /**
     * stuIdを取得する
     *
     * @return stuId 生徒ID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * stuIdを設定する
     *
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * idを取得する
     *
     * @return id メンターー生徒ID（システム採番）
     */
    public Integer getId() {
        return id;
    }

    /**
     * idを設定する
     *
     * @param id メンターー生徒ID（システム採番）
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
