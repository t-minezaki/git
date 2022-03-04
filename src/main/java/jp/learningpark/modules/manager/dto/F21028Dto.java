package jp.learningpark.modules.manager.dto;

import java.util.Date;

/**
 * <p>
 * F21028Dto
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/13 ： NWT)hxl ： 新規作成
 * @date 2020/02/13 12:03
 */
public class F21028Dto implements Comparable<F21028Dto> {
    /**
     * コードCD
     */
    private String codCD;
    /**
     * コード値
     */
    private String codValue;
    /**
     * グループID
     */
    private Integer grpId;
    /**
     * グループ名
     */
    private String grpNm;
    /**
     * 変更後ユーザーID
     */
    private String afterUsrId;
    /**
     * 生徒名
     */
    private String stuNm;
    /**
     * コード値（学年）
     */
    private String schy;
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * 7日間のデータ
     */
    private Integer[] day = new Integer[7];
    /**
     * 実績学習時間
     */
    private Integer perfTime;
    /**
     * 実績年月日
     */
    private Date perfYmd;

    /**
     * コードCDを取得する
     *
     * @return codCD コードCD
     */
    public String getCodCD() {
        return this.codCD;
    }

    /**
     * コードCDを設定する
     *
     * @param codCD コードCD
     */
    public void setCodCD(String codCD) {
        this.codCD = codCD;
    }

    /**
     * コード値を取得する
     *
     * @return codValue コード値
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * コード値を設定する
     *
     * @param codValue コード値
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * グループIDを取得する
     *
     * @return grpId グループID
     */
    public Integer getGrpId() {
        return this.grpId;
    }

    /**
     * グループIDを設定する
     *
     * @param grpId グループID
     */
    public void setGrpId(Integer grpId) {
        this.grpId = grpId;
    }

    /**
     * グループ名を取得する
     *
     * @return grpNm グループ名
     */
    public String getGrpNm() {
        return this.grpNm;
    }

    /**
     * グループ名を設定する
     *
     * @param grpNm グループ名
     */
    public void setGrpNm(String grpNm) {
        this.grpNm = grpNm;
    }

    /**
     * 変更後ユーザーIDを取得する
     *
     * @return afterUsrId 変更後ユーザーID
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * 変更後ユーザーIDを設定する
     *
     * @param afterUsrId 変更後ユーザーID
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
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
     * コード値（学年）を取得する
     *
     * @return schy コード値（学年）
     */
    public String getSchy() {
        return this.schy;
    }

    /**
     * コード値（学年）を設定する
     *
     * @param schy コード値（学年）
     */
    public void setSchy(String schy) {
        this.schy = schy;
    }

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
     * 7日間のデータを取得する
     *
     * @return day 7日間のデータ
     */
    public Integer[] getDay() {
        return this.day;
    }

    /**
     * 7日間のデータを設定する
     *
     * @param day 7日間のデータ
     */
    public void setDay(Integer[] day) {
        this.day = day;
    }

    /**
     * 実績学習時間を取得する
     *
     * @return perfTime 実績学習時間
     */
    public Integer getPerfTime() {
        return this.perfTime;
    }

    /**
     * 実績学習時間を設定する
     *
     * @param perfTime 実績学習時間
     */
    public void setPerfTime(Integer perfTime) {
        this.perfTime = perfTime;
    }

    /**
     * 実績年月日を取得する
     *
     * @return perfYmd 実績年月日
     */
    public Date getPerfYmd() {
        return this.perfYmd;
    }

    /**
     * 実績年月日を設定する
     *
     * @param perfYmd 実績年月日
     */
    public void setPerfYmd(Date perfYmd) {
        this.perfYmd = perfYmd;
    }

    @Override
    public int compareTo(F21028Dto o) {
        return this.getAfterUsrId().toUpperCase().compareTo(o.getAfterUsrId().toUpperCase());
    }
}
