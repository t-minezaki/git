/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

/**
 * スケジュール情報イベント(R10001印刷用)DTO
 *
 * @author gaoli <br />
 * 変更履歴 <br />
 * 2018/10/17 : gaoli: 新規<br />
 * @version 1.0
 */
public class F1030104Dto {

    /**
     * ID
     */
    private int id;

    /**
     * 時間
     */
    private String time;

    /**
     * 表示名
     */
    private String dispyNm;

    /**
     * イベント数
     */
    private int count;

    /**
     * イベントインデックス
     */
    private int index;

    public F1030104Dto() {
    }

    public F1030104Dto(int id, int count, int index, String dispyNm) {
        this.id = id;
        this.count = count;
        this.index = index;
        this.dispyNm = dispyNm;
    }

    /**
     * IDを設定する
     *
     * @return id ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * IDを取得する
     *
     * @param id ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 時間を設定する
     *
     * @return time 時間
     */
    public String getTime() {
        return this.time;
    }

    /**
     * 時間を取得する
     *
     * @param time 時間
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 表示名を設定する
     *
     * @return dispyNm 表示名
     */
    public String getDispyNm() {
        return this.dispyNm;
    }

    /**
     * 表示名を取得する
     *
     * @param dispyNm 表示名
     */
    public void setDispyNm(String dispyNm) {
        this.dispyNm = dispyNm;
    }

    /**
     * イベント数を設定する
     *
     * @return count イベント数
     */
    public int getCount() {
        return this.count;
    }

    /**
     * イベント数を取得する
     *
     * @param count イベント数
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * イベントインデックスを設定する
     *
     * @return index イベントインデックス
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * イベントインデックスを取得する
     *
     * @param index イベントインデックス
     */
    public void setIndex(int index) {
        this.index = index;
    }
}
