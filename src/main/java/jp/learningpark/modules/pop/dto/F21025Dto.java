/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dto;
/**
 * <p>スマホ_連絡確認画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/27 : zpa: 新規<br />
 * @version 1.0
 */
public class F21025Dto {
    /**
     * 遅欠連絡ステータス
     */
    private String content;
    /**
     * 遅欠理由
     */
    private String reason;
    /**
     * 備考
     */
    private String bikou;
    /**
     * 遅刻時間
     */
    private Integer lateTime;
    /**
     * 変更後ユーザーID
     */
   private String afterUsrId;

    /**
     * 送信日
     */
    private String cretDatime;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getBikou() {
        return bikou;
    }

    public void setBikou(String bikou) {
        this.bikou = bikou;
    }

    public Integer getLateTime() {
        return lateTime;
    }

    public void setLateTime(Integer lateTm) {
        this.lateTime = lateTm;
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
     * 送信日を取得する
     *
     * @return cretDatime 送信日
     */
    public String getCretDatime() {
        return this.cretDatime;
    }

    /**
     * 送信日を設定する
     *
     * @param cretDatime 送信日
     */
    public void setCretDatime(String cretDatime) {
        this.cretDatime = cretDatime;
    }
}
