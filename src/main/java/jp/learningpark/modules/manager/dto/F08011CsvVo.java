package jp.learningpark.modules.manager.dto;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/1/28 ： NWT)hxl ： 新規作成
 * @date 2021/1/28 16:37
 */
public class F08011CsvVo {
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * 学年
     */
    private String schy;
    /**
     * 性別
     */
    private String sex;
    /**
     * 生徒名
     */
    private String stuNm;
    /**
     * 保護者名
     */
    private String guardNm;
    /**
     * 学校名
     */
    private String sch;
    /**
     * 電話番号
     */
    private String tel;

    /**
     * 生徒IDを取得する
     *
     * @return stuId 生徒ID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 学年を取得する
     *
     * @return schy 学年
     */
    public String getSchy() {
        return this.schy;
    }

    /**
     * 学年を設定する
     *
     * @param schy 学年
     */
    public void setSchy(String schy) {
        this.schy = schy;
    }

    /**
     * 性別を取得する
     *
     * @return sex 性別
     */
    public String getSex() {
        return this.sex;
    }

    /**
     * 性別を設定する
     *
     * @param sex 性別
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 生徒名を取得する
     *
     * @return stuNm 生徒名
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 生徒名を設定する
     *
     * @param stuNm 生徒名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 保護者名を取得する
     *
     * @return guardNm 保護者名
     */
    public String getGuardNm() {
        return this.guardNm;
    }

    /**
     * 保護者名を設定する
     *
     * @param guardNm 保護者名
     */
    public void setGuardNm(String guardNm) {
        this.guardNm = guardNm;
    }

    /**
     * 学校名を取得する
     *
     * @return sch 学校名
     */
    public String getSch() {
        return this.sch;
    }

    /**
     * 学校名を設定する
     *
     * @param sch 学校名
     */
    public void setSch(String sch) {
        this.sch = sch;
    }

    /**
     * 電話番号を取得する
     *
     * @return tel 電話番号
     */
    public String getTel() {
        return this.tel;
    }

    /**
     * 電話番号を設定する
     *
     * @param tel 電話番号
     */
    public void setTel(String tel) {
        this.tel = tel;
    }
}
