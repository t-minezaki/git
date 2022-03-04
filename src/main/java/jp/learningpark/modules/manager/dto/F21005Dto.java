package jp.learningpark.modules.manager.dto;

/**
 * <p>
 * F21005Dto
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/19 ： NWT)hxl ： 新規作成
 * @date 2019/11/19 13:34
 */
public class F21005Dto {
    /**
     * ID
     */
    private Integer id;
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * 保護者ID
     */
    private String guardId;
    /**
     * 対応する状態
     */
    private String corrspdSts;
    /**
     * 保護者名
     */
    private String guardName;
    /**
     * 作成日時
     */
    private String dateTime;
    /**
     * 生徒名
     */
    private String stuNm;
    /**
     * 学年
     */
    private String codValue;

    /**
     * 生徒IDを取得する
     *
     * @return
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param stuId
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 保護者IDを取得する
     *
     * @return
     */
    public String getGuardId() {
        return guardId;
    }

    /**
     * 保護者IDを設定する
     *
     * @param guardId
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * 対応する状態を取得する
     *
     * @return
     */
    public String getCorrspdSts() {
        return corrspdSts;
    }

    /**
     * 対応する状態を設定する
     *
     * @param corrspdSts
     */
    public void setCorrspdSts(String corrspdSts) {
        this.corrspdSts = corrspdSts;
    }

    /**
     * IDを取得する
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * IDを設定する
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 保護者名を取得する
     *
     * @return
     */
    public String getGuardName() {
        return guardName;
    }

    /**
     * 保護者名を設定する
     *
     * @param guardName
     */
    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    /**
     * 作成日時を取得する
     *
     * @return
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * 作成日時を設定する
     *
     * @param dateTime
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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
     * 学年を取得する
     *
     * @return codValue 学年
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * 学年を設定する
     *
     * @param codValue 学年
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }
}
