/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>面談記録結果配信設定画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/18 : wq: 新規<br />
 * @version 1.0
 */
public class F21076Dto {

    /**
     * イベントID
     */
    private Integer eventId;

    /**
     * <p>変更後ユーザーID</p>
     * 生徒ID
     */
    private String stuId;

    /**
     * <p>変更後ユーザーID</p>
     * 保護者ID
     */
    private String guardId;

    /**
     * 生徒ID
     */
    private String sId;

    /**
     * 保護者ID
     */
    private String gId;

    /**
     * イベントIDを取得する
     *
     * @return eventId イベントID
     */
    public Integer getEventId() {
        return this.eventId;
    }

    /**
     * イベントIDを設定する
     *
     * @param eventId イベントID
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
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
     * 生徒IDを取得する
     *
     * @return sId 生徒ID
     */
    public String getSId() {
        return this.sId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param sId 生徒ID
     */
    public void setSId(String sId) {
        this.sId = sId;
    }

    /**
     * 保護者IDを取得する
     *
     * @return gId 保護者ID
     */
    public String getGId() {
        return this.gId;
    }

    /**
     * 保護者IDを設定する
     *
     * @param gId 保護者ID
     */
    public void setGId(String gId) {
        this.gId = gId;
    }
}
