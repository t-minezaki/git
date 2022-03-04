/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

public class F21026Dto {
    /**
     * ユーザーＩＤ
     */
    private String stuId;
    /**
     * 変更後ユーザーＩＤ
     */
    private String afterUsrId;
    /**
     * 生徒名
     */
    private String stuNm;
    /**
     * 保護者ID
     */
    private String guardId;
    /**
     * 学年
     */
    private String schy;
    /**
     * ポイント
     */
    private Integer point;
    /**
     * ステータス
     */
    private String userSts;

    /**
     * ユーザーＩＤを取得する
     *
     * @return stuId ユーザーＩＤ
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * ユーザーＩＤを設定する
     *
     * @param stuId ユーザーＩＤ
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
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
     * 保護者IDを取得する
     *
     * @return guardId 保護者ID
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * 保護者IDを設定する
     *
     * @param guardId 保護者ID
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
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
     * ポイントを取得する
     *
     * @return point ポイント
     */
    public Integer getPoint() {
        return this.point;
    }

    /**
     * ポイントを設定する
     *
     * @param point ポイント
     */
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * ステータスを取得する
     *
     * @return userSts ステータス
     */
    public String getUserSts() {
        return this.userSts;
    }

    /**
     * ステータスを設定する
     *
     * @param userSts ステータス
     */
    public void setUserSts(String userSts) {
        this.userSts = userSts;
    }

    /**
     * 変更後ユーザーＩＤを取得する
     *
     * @return afterUsrId 変更後ユーザーＩＤ
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * 変更後ユーザーＩＤを設定する
     *
     * @param afterUsrId 変更後ユーザーＩＤ
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }
}
