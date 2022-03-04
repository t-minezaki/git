package jp.learningpark.modules.manager.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class F08009Dto implements Serializable {

    /**
     * イベント.ID
     */
    private Integer id;

    /**
     * イベント.イベントCD
     */
    private String eventCd;

    /**
     * イベント.作成日時
     */
    private Date cretDatime;

    /**
     * イベント.更新日時
     */
    private Date updDatime;

    /**
     * イベント.カテゴリ
     */
    private String ctgyNm;

    /**
     * イベント.イベントタイトル
     */
    private String eventTitle;

    /**
     * イベント.添付ファイルパス
     */
    private String attachFilePath;

    /**
     * イベント.イベントステータス区分->コードマスタ．コード値（イベントステータス）
     */
    private String status;

    /**
     * イベント．公開開始日時
     */
    private Timestamp pubStartDt;

    /**
     * イベント．公開終了日時
     */
    private Timestamp pubEndDt;

    /**
     * イベント．申込み可能開始日時
     */
    private Timestamp applyStartDt;

    /**
     * イベント．申込み可能終了日時
     */
    private Timestamp applyEndDt;

    /**
     * 保護者イベント申込状況．再送信日時
     */
    private Timestamp againSendDt;

    /**
     * 配信数
     */
    /**
     *通知プッシュ失敗件数
     */
    private Integer errorDataList;
    public Integer getErrorDataList() {
        return errorDataList;
    }

    public void setErrorDataList(Integer errorDataList) {
        this.errorDataList = errorDataList;
    }

    public Integer getRefType() {
        return refType;
    }

    public void setRefType(Integer refType) {
        this.refType = refType;
    }

    /**
     * イベントＩＤ．関連タイプ
     */
    private Integer refType;

    private Integer deleverSum;

    /**
     * 未読数
     */
    private Integer notReadingSum;

    /**
     * 既読数
     */
    private Integer readingSum;

    /**
     * 未回答数
     */
    private Integer notReplySum;

    /**
     * 予約数
     */
    private Integer replySum;

    /**
     * 回答数
     */
    private Integer confSum;

    /**
     * イベント.作成ユーザ名称
     */
    private String cretUserName;

    /**
     * イベント.更新ユーザ名称
     */
    private String updUserName;

    /**
     * イベント日程数
     */
    private Integer scheduleCount;
    /**
     * 表示項目
     */
    private String dspItems;
    /**
     * 表示しなければならない項目
     */
    private String mustItems;
    /**
     * 全項目
     */
    private String allItems;

    /**
     *
     */
    private String usrFlg;
    /**
     * 画面．id
     *
     * @return id 画面．id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 画面．id
     *
     * @param id 画面．配信先組織リスト
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 画面．イベントCD
     *
     * @return eventCd 画面．イベントCD
     */
    public String getEventCd() {
        return eventCd;
    }

    /**
     * 画面．イベントCD
     *
     * @param eventCd 画面．イベントCD
     */
    public void setEventCd(String eventCd) {
        this.eventCd = eventCd;
    }

    /**
     * 画面．作成日時
     *
     * @return cretDatime 画面．作成日時
     */
    public Date getCretDatime() {
        return cretDatime;
    }

    /**
     * 画面．作成日時
     *
     * @param cretDatime 画面．作成日時
     */
    public void setCretDatime(Date cretDatime) {
        this.cretDatime = cretDatime;
    }

    /**
     * 画面．更新日時
     *
     * @return updDatime 画面．更新日時
     */
    public Date getUpdDatime() {
        return updDatime;
    }

    /**
     * 画面．更新日時
     *
     * @param updDatime 画面．更新日時
     */
    public void setUpdDatime(Date updDatime) {
        this.updDatime = updDatime;
    }

    /**
     * 画面．カテゴリ
     *
     * @return ctgyNm 画面．カテゴリ
     */
    public String getCtgyNm() {
        return ctgyNm;
    }

    /**
     * 画面．カテゴリ
     *
     * @param ctgyNm 画面．カテゴリ
     */
    public void setCtgyNm(String ctgyNm) {
        this.ctgyNm = ctgyNm;
    }

    /**
     * 画面．イベントタイトル
     *
     * @return eventTitle 画面．イベントタイトル
     */
    public String getEventTitle() {
        return eventTitle;
    }

    /**
     * 画面．イベントタイトル
     *
     * @param eventTitle 画面．イベントタイトル
     */
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    /**
     * 画面．添付ファイルパス
     *
     * @return attachFilePath 画面．添付ファイルパス
     */
    public String getAttachFilePath() {
        return attachFilePath;
    }

    /**
     * 画面．添付ファイルパス
     *
     * @param attachFilePath 画面．添付ファイルパス
     */
    public void setAttachFilePath(String attachFilePath) {
        this.attachFilePath = attachFilePath;
    }

    /**
     * 画面．状態
     *
     * @return status 画面．状態
     */
    public String getStatus() {
        return status;
    }

    /**
     * 画面．状態
     *
     * @param status 画面．状態
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 画面．公開開始日時
     *
     * @return pubStartDt 画面．公開開始日時
     */
    public Timestamp getPubStartDt() {
        return pubStartDt;
    }

    /**
     * 画面．公開開始日時
     *
     * @param pubStartDt 画面．公開開始日時
     */
    public void setPubStartDt(Timestamp pubStartDt) {
        this.pubStartDt = pubStartDt;
    }

    /**
     * 画面．公開終了日時
     *
     * @return pubEndDt 画面．公開終了日時
     */
    public Timestamp getPubEndDt() {
        return pubEndDt;
    }

    /**
     * 画面．公開終了日時
     *
     * @param pubEndDt 画面．公開終了日時
     */
    public void setPubEndDt(Timestamp pubEndDt) {
        this.pubEndDt = pubEndDt;
    }

    /**
     * 画面．申込み可能開始日時
     *
     * @return applyStartDt 画面．申込み可能開始日時
     */
    public Timestamp getApplyStartDt() {
        return applyStartDt;
    }

    /**
     * 画面．申込み可能開始日時
     *
     * @param applyStartDt 画面．申込み可能開始日時
     */
    public void setApplyStartDt(Timestamp applyStartDt) {
        this.applyStartDt = applyStartDt;
    }

    /**
     * 画面．申込み可能終了日時
     *
     * @return applyEndDt 画面．申込み可能終了日時
     */
    public Timestamp getApplyEndDt() {
        return applyEndDt;
    }

    /**
     * 画面．申込み可能終了日時
     *
     * @param applyEndDt 画面．申込み可能終了日時
     */
    public void setApplyEndDt(Timestamp applyEndDt) {
        this.applyEndDt = applyEndDt;
    }

    /**
     * 画面．プッシュ通知連絡日
     *
     * @return replyTime 画面．プッシュ通知連絡日
     */
    public Timestamp getAgainSendDt() {
        return againSendDt;
    }

    /**
     * 画面．プッシュ通知連絡日
     *
     * @param replyTime 画面．プッシュ通知連絡日
     */
    public void setAgainSendDt(Timestamp replyTime) {
        this.againSendDt = replyTime;
    }

    /**
     * 画面．配信数
     *
     * @return deleverSum 画面．配信数
     */
    public Integer getDeleverSum() {
        return deleverSum;
    }

    /**
     * 画面．配信数
     *
     * @param deleverSum 画面．配信数
     */
    public void setDeleverSum(Integer deleverSum) {
        this.deleverSum = deleverSum;
    }

    /**
     * 画面．未読数
     *
     * @return notReadingSum 画面．未読数
     */
    public Integer getNotReadingSum() {
        return notReadingSum;
    }

    /**
     * 画面．未読数
     *
     * @param notReadingSum 画面．未読数
     */
    public void setNotReadingSum(Integer notReadingSum) {
        this.notReadingSum = notReadingSum;
    }

    /**
     * 画面．既読数
     *
     * @return readingSum 画面．既読数
     */
    public Integer getReadingSum() {
        return readingSum;
    }

    /**
     * 画面．既読数
     *
     * @param readingSum 画面．既読数
     */
    public void setReadingSum(Integer readingSum) {
        this.readingSum = readingSum;
    }

    /**
     * 画面．未回答数
     *
     * @return notReplySum 画面．未回答数
     */
    public Integer getNotReplySum() {
        return notReplySum;
    }

    /**
     * 画面．未回答数
     *
     * @param notReplySum 画面．未回答数
     */
    public void setNotReplySum(Integer notReplySum) {
        this.notReplySum = notReplySum;
    }

    /**
     * 画面．予約数
     *
     * @return replySum 画面．予約数
     */
    public Integer getReplySum() {
        return replySum;
    }

    /**
     * 画面．予約数
     *
     * @param replySum 画面．予約数
     */
    public void setReplySum(Integer replySum) {
        this.replySum = replySum;
    }

    /**
     * 画面．作成者名
     *
     * @return cretUserName 画面．作成者名
     */
    public String getCretUserName() {
        return cretUserName;
    }

    /**
     * 画面．作成者名
     *
     * @param cretUserName 画面．作成者名
     */
    public void setCretUserName(String cretUserName) {
        this.cretUserName = cretUserName;
    }

    /**
     * 画面．最終更新名
     *
     * @return updUserName 画面．最終更新名
     */
    public String getUpdUserName() {
        return updUserName;
    }

    /**
     * 画面．最終更新名
     *
     * @param updUserName 画面．最終更新名
     */
    public void setUpdUserName(String updUserName) {
        this.updUserName = updUserName;
    }

    /**
     * 画面．イベント日程数
     *
     * @return scheduleCount 画面．イベント日程数
     */
    public Integer getScheduleCount() {
        return scheduleCount;
    }

    /**
     * 画面．イベント日程数
     *
     * @param scheduleCount 画面．イベント日程数
     */
    public void setScheduleCount(Integer scheduleCount) {
        this.scheduleCount = scheduleCount;
    }

    /**
     * 画面．回答数
     *
     * @return scheduleCount 画面．回答数
     */
    public Integer getConfSum() {
        return confSum;
    }

    /**
     * 画面．回答数
     *
     * @param confSum 画面．回答数
     */
    public void setConfSum(Integer confSum) {
        this.confSum = confSum;
    }

    /**
     * 表示項目を取得する
     *
     * @return dspItems 表示項目
     */
    public String getDspItems() {
        return this.dspItems;
    }

    /**
     * 表示項目を設定する
     *
     * @param dspItems 表示項目
     */
    public void setDspItems(String dspItems) {
        this.dspItems = dspItems;
    }

    /**
     * 表示しなければならない項目を取得する
     *
     * @return mustItems 表示しなければならない項目
     */
    public String getMustItems() {
        return this.mustItems;
    }

    /**
     * 表示しなければならない項目を設定する
     *
     * @param mustItems 表示しなければならない項目
     */
    public void setMustItems(String mustItems) {
        this.mustItems = mustItems;
    }

    /**
     * 全項目を取得する
     *
     * @return allItems 全項目
     */
    public String getAllItems() {
        return this.allItems;
    }

    /**
     * 全項目を設定する
     *
     * @param allItems 全項目
     */
    public void setAllItems(String allItems) {
        this.allItems = allItems;
    }

    /**
     * を取得する
     *
     * @return usrFlg
     */
    public String getUsrFlg() {
        return this.usrFlg;
    }

    /**
     * を設定する
     *
     * @param usrFlg
     */
    public void setUsrFlg(String usrFlg) {
        this.usrFlg = usrFlg;
    }
}
