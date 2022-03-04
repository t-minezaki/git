/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

public class F21017Dto {
    /**
     * 生徒名
     */
    private String stuName;
    /**
     * 対象年月日
     */
    private String tgtYmd;
    /**
     * 学年区分
     */
    private String schyDiv;
    /**
     *
     * add at 2021/08/11 for V9.02 by NWT wen
     * 通塾曜日区分
     */
    private String dayweek_div;
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * 遅欠連絡ステータス
     */
    private String absSts;
    /**
     * 登校日時
     */
    private String entryDt;
    /**
     * add at 2021/08/19 for V9.02 by NWT wen
     * 展示用入室時間
     */
    private String display;
    /**
     * 下校日時
     */
    private String exitDt;
    /**
     * 背景色
     */
    private String backGround;
    /**
     *  生年月日
     */
    private String birthd;
    /**
     * 誕生日説明
     */
    private String birthDayTxt;
    /**
     * 写真パス
     */
    private String photPath;
    /**
     * 登校下校フラグ
     */
    private String entryFlg;
    /**
     * 褒めポイントFlg
     */
    private String complimentFlg;
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

    /**
     * 対象年月日を取得する
     *
     * @return tgtYmd 対象年月日
     */
    public String getTgtYmd() {
        return this.tgtYmd;
    }

    /**
     * 対象年月日を設定する
     *
     * @param tgtYmd 対象年月日
     */
    public void setTgtYmd(String tgtYmd) {
        this.tgtYmd = tgtYmd;
    }

    /**
     * 学年区分を取得する
     *
     * @return schyDiv 学年区分
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * 学年区分を設定する
     *
     * @param schyDiv 学年区分
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
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
     * 遅欠連絡ステータスを取得する
     *
     * @return absSts 遅欠連絡ステータス
     */
    public String getAbsSts() {
        return this.absSts;
    }

    /**
     * 遅欠連絡ステータスを設定する
     *
     * @param absSts 遅欠連絡ステータス
     */
    public void setAbsSts(String absSts) {
        this.absSts = absSts;
    }

    /**
     * 登校日時を取得する
     *
     * @return entryDt 登校日時
     */
    public String getEntryDt() {
        return this.entryDt;
    }

    /**
     * 登校日時を設定する
     *
     * @param entryDt 登校日時
     */
    public void setEntryDt(String entryDt) {
        this.entryDt = entryDt;
    }

    /**
     * 下校日時を取得する
     *
     * @return exitDt 下校日時
     */
    public String getExitDt() {
        return this.exitDt;
    }

    /**
     * 下校日時を設定する
     *
     * @param exitDt 下校日時
     */
    public void setExitDt(String exitDt) {
        this.exitDt = exitDt;
    }

    /**
     * 背景色を取得する
     *
     * @return backGround 背景色
     */
    public String getBackGround() {
        return this.backGround;
    }

    /**
     * 背景色を設定する
     *
     * @param backGround 背景色
     */
    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }


    /**
     * 生年月日を取得する
     *
     * @return birthd 生年月日
     */
    public String getBirthd() {
        return this.birthd;
    }

    /**
     * 生年月日を設定する
     *
     * @param birthd 生年月日
     */
    public void setBirthd(String birthd) {
        this.birthd = birthd;
    }

    /**
     * 誕生日説明を取得する
     *
     * @return birthDayTxt 誕生日説明
     */
    public String getBirthDayTxt() {
        return this.birthDayTxt;
    }

    /**
     * 誕生日説明を設定する
     *
     * @param birthDayTxt 誕生日説明
     */
    public void setBirthDayTxt(String birthDayTxt) {
        this.birthDayTxt = birthDayTxt;
    }

    /**
     * 写真パスを取得する
     *
     * @return photPath 写真パス
     */
    public String getPhotPath() {
        return this.photPath;
    }

    /**
     * 写真パスを設定する
     *
     * @param photPath 写真パス
     */
    public void setPhotPath(String photPath) {
        this.photPath = photPath;
    }

    /**
     * 登校下校フラグを取得する
     *
     * @return entryFlg 登校下校フラグ
     */
    public String getEntryFlg() {
        return this.entryFlg;
    }

    /**
     * 登校下校フラグを設定する
     *
     * @param entryFlg 登校下校フラグ
     */
    public void setEntryFlg(String entryFlg) {
        this.entryFlg = entryFlg;
    }

    /**
     * 褒めポイントFlgを取得する
     *
     * @return complimentFlg 褒めポイントFlg
     */
    public String getComplimentFlg() {
        return this.complimentFlg;
    }

    /**
     * 褒めポイントFlgを設定する
     *
     * @param complimentFlg 褒めポイントFlg
     */
    public void setComplimentFlg(String complimentFlg) {
        this.complimentFlg = complimentFlg;
    }

    /**
     * 通塾曜日区分を取得する
     *
     * @return dayweek_div 通塾曜日区分
     */
    public String getDayweek_div() {
        return this.dayweek_div;
    }

    /**
     * 通塾曜日区分を設定する
     *
     * add at 2021/08/11 for V9.02 by NWT wen
     * @param dayweek_div 通塾曜日区分
     */
    public void setDayweek_div(String dayweek_div) {
        this.dayweek_div = dayweek_div;
    }

    /**
     * 展示用入室時間を取得する
     *
     * @return display 展示用入室時間
     */
    public String getDisplay() {
        return this.display;
    }

    /**
     * 展示用入室時間を設定する
     *
     * @param display 展示用入室時間
     */
    public void setDisplay(String display) {
        this.display = display;
    }
}
