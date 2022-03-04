/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>F04001_塾ニュース一覧画面 Dto</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/02/26 : tan: 新規<br />
 * @version 1.0
 */
public class F04016Dto {
    /**
     * ．変更後ユーザID
     */
    private String afterUserId;
    /**
     * 　保護者氏名
     */
    private String guardName;
    /**
     *   変更後ユーザID
     */
    private String stuId;
    /**
     *  生徒名
     */
    private String stuName;

    /**
     * 　保護者氏名を取得する
     *
     * @return guardName 　保護者氏名
     */
    public String getGuardName() {
        return this.guardName;
    }

    /**
     * 　保護者氏名を設定する
     *
     * @param guardName 　保護者氏名
     */
    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    /**
     * ．変更後ユーザIDを取得する
     *
     * @return afterUserId ．変更後ユーザID
     */
    public String getAfterUserId() {
        return this.afterUserId;
    }

    /**
     * ．変更後ユーザIDを設定する
     *
     * @param afterUserId ．変更後ユーザID
     */
    public void setAfterUserId(String afterUserId) {
        this.afterUserId = afterUserId;
    }

    /**
     * 変更後ユーザIDを取得する
     *
     * @return stuId 変更後ユーザID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 変更後ユーザIDを設定する
     *
     * @param stuId 変更後ユーザID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 生徒名を取得する
     *
     * @return stuName 生徒名
     */
    public String getStuName() {
        return this.stuName;
    }

    /**
     * 生徒名を設定する
     *
     * @param stuName 生徒名
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }
}
