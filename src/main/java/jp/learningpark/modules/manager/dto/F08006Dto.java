package jp.learningpark.modules.manager.dto;

/**
 * <p>F08006_イベント日程時間設定画面(POP) Dto</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/01 : yang: 新規<br />
 * @version 1.0
 */

public class F08006Dto {
    /**
     * メンタ姓名
     */
    public String mentorName;
    /**
     * userId
     */
    public String usrId;

    /**
     * 月曜日選択フラグ
     */
    private String moDwChocFlg;

    /**
     * 火曜日選択フラグ
     */
    private String tuDwChocFlg;

    /**
     * 水曜日選択フラグ
     */
    private String weDwChocFlg;

    /**
     * 木曜日選択フラグ
     */
    private String thDwChocFlg;

    /**
     * 金曜日選択フラグ
     */
    private String frDwChocFlg;

    /**
     * 土曜日選択フラグ
     */
    private String saDwChocFlg;

    /**
     * 日曜日選択フラグ
     */
    private String suDwChocFlg;

    /**
     * メンタ姓名を取得する
     *
     * @return mentorName メンタ姓名
     */
    public String getMentorName() {
        return this.mentorName;
    }

    /**
     * メンタ姓名を設定する
     *
     * @param mentorName メンタ姓名
     */
    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    /**
     * 月曜日選択フラグを取得する
     *
     * @return moDwChocFlg 月曜日選択フラグ
     */
    public String getMoDwChocFlg() {
        return this.moDwChocFlg;
    }

    /**
     * 月曜日選択フラグを設定する
     *
     * @param moDwChocFlg 月曜日選択フラグ
     */
    public void setMoDwChocFlg(String moDwChocFlg) {
        this.moDwChocFlg = moDwChocFlg;
    }

    /**
     * 火曜日選択フラグを取得する
     *
     * @return tuDwChocFlg 火曜日選択フラグ
     */
    public String getTuDwChocFlg() {
        return this.tuDwChocFlg;
    }

    /**
     * 火曜日選択フラグを設定する
     *
     * @param tuDwChocFlg 火曜日選択フラグ
     */
    public void setTuDwChocFlg(String tuDwChocFlg) {
        this.tuDwChocFlg = tuDwChocFlg;
    }

    /**
     * 水曜日選択フラグを取得する
     *
     * @return weDwChocFlg 水曜日選択フラグ
     */
    public String getWeDwChocFlg() {
        return this.weDwChocFlg;
    }

    /**
     * 水曜日選択フラグを設定する
     *
     * @param weDwChocFlg 水曜日選択フラグ
     */
    public void setWeDwChocFlg(String weDwChocFlg) {
        this.weDwChocFlg = weDwChocFlg;
    }

    /**
     * 木曜日選択フラグを取得する
     *
     * @return thDwChocFlg 木曜日選択フラグ
     */
    public String getThDwChocFlg() {
        return this.thDwChocFlg;
    }

    /**
     * 木曜日選択フラグを設定する
     *
     * @param thDwChocFlg 木曜日選択フラグ
     */
    public void setThDwChocFlg(String thDwChocFlg) {
        this.thDwChocFlg = thDwChocFlg;
    }

    /**
     * 金曜日選択フラグを取得する
     *
     * @return frDwChocFlg 金曜日選択フラグ
     */
    public String getFrDwChocFlg() {
        return this.frDwChocFlg;
    }

    /**
     * 金曜日選択フラグを設定する
     *
     * @param frDwChocFlg 金曜日選択フラグ
     */
    public void setFrDwChocFlg(String frDwChocFlg) {
        this.frDwChocFlg = frDwChocFlg;
    }

    /**
     * 土曜日選択フラグを取得する
     *
     * @return saDwChocFlg 土曜日選択フラグ
     */
    public String getSaDwChocFlg() {
        return this.saDwChocFlg;
    }

    /**
     * 土曜日選択フラグを設定する
     *
     * @param saDwChocFlg 土曜日選択フラグ
     */
    public void setSaDwChocFlg(String saDwChocFlg) {
        this.saDwChocFlg = saDwChocFlg;
    }

    /**
     * 日曜日選択フラグを取得する
     *
     * @return suDwChocFlg 日曜日選択フラグ
     */
    public String getSuDwChocFlg() {
        return this.suDwChocFlg;
    }

    /**
     * 日曜日選択フラグを設定する
     *
     * @param suDwChocFlg 日曜日選択フラグ
     */
    public void setSuDwChocFlg(String suDwChocFlg) {
        this.suDwChocFlg = suDwChocFlg;
    }

    /**
     * userIdを取得する
     *
     * @return usrId userId
     */
    public String getUsrId() {
        return this.usrId;
    }

    /**
     * userIdを設定する
     *
     * @param usrId userId
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }
}
