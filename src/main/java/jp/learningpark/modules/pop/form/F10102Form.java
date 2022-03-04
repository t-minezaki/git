/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.form;

/**
 * <p>固定活動登録画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/06 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F10102Form {
    /**
     * id
     */
    private int id;
    /**
     * ブロークID
     */
    private int blockId;

    /**
     * ブロック表示名
     */
    private String blockDispyNm;

    /**
     * ブロック画像区分
     */
    private String blockPicDiv;

    /**
     * ブロック開始日
     */
    private String blockStartDate;

    /**
     * ブロック終了日
     */
    private String blockEndDate;

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
     * ブロック開始時間
     */
    private String blockStartTime;

    /**
     * ブロック終了時間
     */
    private String blockEndTime;

    /**
     * 更新日時
     */
    private String updateTime;

    /**
     * idを取得する
     *
     * @return id id
     */
    public int getId() {
        return id;
    }

    /**
     * idを設定する
     *
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * ブロック表示名を取得する
     *
     * @return blockDispyNm ブロック表示名
     */
    public String getBlockDispyNm() {
        return blockDispyNm;
    }

    /**
     * ブロック表示名を設定する
     *
     * @param blockDispyNm ブロック表示名
     */
    public void setBlockDispyNm(String blockDispyNm) {
        this.blockDispyNm = blockDispyNm;
    }

    /**
     * ブロック開始日を取得する
     *
     * @return blockStartDate ブロック開始日
     */
    public String getBlockStartDate() {
        return blockStartDate;
    }

    /**
     * ブロック開始日を設定する
     *
     * @param blockStartDate ブロック開始日
     */
    public void setBlockStartDate(String blockStartDate) {
        this.blockStartDate = blockStartDate;
    }

    /**
     * ブロック終了日を取得する
     *
     * @return blockEndDate ブロック終了日
     */
    public String getBlockEndDate() {
        return blockEndDate;
    }

    /**
     * ブロック終了日を設定する
     *
     * @param blockEndDate ブロック終了日
     */
    public void setBlockEndDate(String blockEndDate) {
        this.blockEndDate = blockEndDate;
    }

    /**
     * 月曜日を取得する
     *
     * @return moDwChocFlg 月曜日
     */
    public String getMoDwChocFlg() {
        return moDwChocFlg;
    }

    /**
     * 月曜日を設定する
     *
     * @param moDwChocFlg 月曜日
     */
    public void setMoDwChocFlg(String moDwChocFlg) {
        this.moDwChocFlg = moDwChocFlg;
    }

    /**
     * 火曜日を取得する
     *
     * @return tuDwChocFlg 火曜日
     */
    public String getTuDwChocFlg() {
        return tuDwChocFlg;
    }

    /**
     * 火曜日を設定する
     *
     * @param tuDwChocFlg 火曜日
     */
    public void setTuDwChocFlg(String tuDwChocFlg) {
        this.tuDwChocFlg = tuDwChocFlg;
    }

    /**
     * 水曜日を取得する
     *
     * @return weDwChocFlg 水曜日
     */
    public String getWeDwChocFlg() {
        return weDwChocFlg;
    }

    /**
     * 水曜日を設定する
     *
     * @param weDwChocFlg 水曜日
     */
    public void setWeDwChocFlg(String weDwChocFlg) {
        this.weDwChocFlg = weDwChocFlg;
    }

    /**
     * 木曜日を取得する
     *
     * @return thDwChocFlg 木曜日
     */
    public String getThDwChocFlg() {
        return thDwChocFlg;
    }

    /**
     * 木曜日を設定する
     *
     * @param thDwChocFlg 木曜日
     */
    public void setThDwChocFlg(String thDwChocFlg) {
        this.thDwChocFlg = thDwChocFlg;
    }

    /**
     * 金曜日を取得する
     *
     * @return frDwChocFlg 金曜日
     */
    public String getFrDwChocFlg() {
        return frDwChocFlg;
    }

    /**
     * 金曜日を設定する
     *
     * @param frDwChocFlg 金曜日
     */
    public void setFrDwChocFlg(String frDwChocFlg) {
        this.frDwChocFlg = frDwChocFlg;
    }

    /**
     * 土曜日を取得する
     *
     * @return saDwChocFlg 土曜日
     */
    public String getSaDwChocFlg() {
        return saDwChocFlg;
    }

    /**
     * 土曜日を設定する
     *
     * @param saDwChocFlg 土曜日
     */
    public void setSaDwChocFlg(String saDwChocFlg) {
        this.saDwChocFlg = saDwChocFlg;
    }

    /**
     * 日曜日を取得する
     *
     * @return suDwChocFlg 日曜日
     */
    public String getSuDwChocFlg() {
        return suDwChocFlg;
    }

    /**
     * 日曜日を設定する
     *
     * @param suDwChocFlg 日曜日
     */
    public void setSuDwChocFlg(String suDwChocFlg) {
        this.suDwChocFlg = suDwChocFlg;
    }

    /**
     * ブロック開始時間を取得する
     *
     * @return blockStartTime ブロック開始時間
     */
    public String getBlockStartTime() {
        return blockStartTime;
    }

    /**
     * ブロック開始時間を設定する
     *
     * @param blockStartTime ブロック開始時間
     */
    public void setBlockStartTime(String blockStartTime) {
        this.blockStartTime = blockStartTime;
    }

    /**
     * ブロック終了時間を取得する
     *
     * @return blockEndTime ブロック終了時間
     */
    public String getBlockEndTime() {
        return blockEndTime;
    }

    /**
     * ブロック終了時間を設定する
     *
     * @param blockEndTime ブロック終了時間
     */
    public void setBlockEndTime(String blockEndTime) {
        this.blockEndTime = blockEndTime;
    }

    /**
     * 更新日時を取得する
     *
     * @return updateTime 更新日時
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新日時を設定する
     *
     * @param updateTime 更新日時
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * ブロック画像区分を取得する
     *
     * @return blockPicDiv ブロック画像区分
     */
    public String getBlockPicDiv() {
        return blockPicDiv;
    }

    /**
     * ブロック画像区分を設定する
     *
     * @return blockPicDiv ブロック画像区分
     */
    public void setBlockPicDiv(String blockPicDiv) {
        this.blockPicDiv = blockPicDiv;
    }

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }
}
