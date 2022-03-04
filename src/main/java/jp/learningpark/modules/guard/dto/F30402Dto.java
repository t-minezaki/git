package jp.learningpark.modules.guard.dto;

/**
 * <p>塾からのイベント情報詳細画面</p >
 *
 * @author NWT : hujiaxing <br />
 * 変更履歴 <br />
 * 2019/07/30: hujiaxing: 新規<br />
 * @version 1.0
 */
public class F30402Dto {

    /**
     * 保護者イベント申込状況．ＩＤ
     */
    private Integer id;

    /**
     * イベント．イベントタイトル
     */
    private String eventTitle;
    /**
     * イベント．添付ファイルパス
     */
    private String attFilePath;
    /**
     * コードマスタ．コード値（イベントステータス）
     */
    private String codValue;
    /**
     * イベント．イベント内容
     */
    private String eventCont;

    /**
     * イベント．イベント画像パス
     */
    private String titleImgPath;

    /**
     * 保護者イベント申込状況．閲覧状況区分
     */
    private String readingStsDiv;
    /**
     * 保護者イベント申込状況．閲覧回答区分
     */
    private String replyStsDiv;
    /**
     * イベント．申込み可能開始日時
     */
    private String applyStartDt;
    /**
     * イベント．申込み可能終了日時
     */
    private String applyEndDt;
    /**
     * idを取得する
     *
     * @return id id
     */
    public Integer getId() {
        return id;
    }

    /**
     * idを設定する
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * titleImgPathを取得する
     *
     * @return titleImgPath titleImgPath
     */
    public String getTitleImgPath() {
        return titleImgPath;
    }

    /**
     * titleImgPathを設定する
     *
     * @param titleImgPath titleImgPath
     */
    public void setTitleImgPath(String titleImgPath) {
        this.titleImgPath = titleImgPath;
    }

    /**
     * readingStsDivを取得する
     *
     * @return readingStsDiv readingStsDiv
     */
    public String getReadingStsDiv() {
        return readingStsDiv;
    }

    /**
     * readingStsDivを設定する
     *
     * @param readingStsDiv readingStsDiv
     */
    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }

    /**
     * eventTitleを取得する
     * @return eventTitle eventTitle
     */
    public String getEventTitle() {
        return eventTitle;
    }

    /**
     * eventTitleを設定する
     * @param eventTitle eventTitle
     */
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    /**
     * attFilePathを取得する
     * @return
     */
    public String getAttFilePath() {
        return attFilePath;
    }

    /**
     * attFilePathを設定する
     * @param attFilePath attFilePath
     */
    public void setAttFilePath(String attFilePath) {
        this.attFilePath = attFilePath;
    }

    /**
     * codValueを取得する
     * @return
     */
    public String getCodValue() {
        return codValue;
    }

    /**
     * codValueを設定する
     * @param codValue codValue
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * eventContを取得する
     * @return
     */
    public String getEventCont() {
        return eventCont;
    }

    /**
     * eventContを設定する
     * @param eventCont eventCont
     */
    public void setEventCont(String eventCont) {
        this.eventCont = eventCont;
    }

    /**
     * applyEndDtを取得する
     * @return
     */
    public String getApplyEndDt() {
        return applyEndDt;
    }

    /**
     * applyEndDtを設定する
     * @param applyEndDt
     */
    public void setApplyEndDt(String applyEndDt) {
        this.applyEndDt = applyEndDt;
    }

    /**
     * applyStartDtを取得する
     * @return
     */
    public String getApplyStartDt() {
        return applyStartDt;
    }

    /**
     * applyStartDtを取得する
     * @param applyStartDt
     */
    public void setApplyStartDt(String applyStartDt) {
        this.applyStartDt = applyStartDt;
    }

    /**
     * 保護者イベント申込状況．閲覧回答区分を取得する
     *
     * @return replyStsDiv 保護者イベント申込状況．閲覧回答区分
     */
    public String getReplyStsDiv() {
        return this.replyStsDiv;
    }

    /**
     * 保護者イベント申込状況．閲覧回答区分を設定する
     *
     * @param replyStsDiv 保護者イベント申込状況．閲覧回答区分
     */
    public void setReplyStsDiv(String replyStsDiv) {
        this.replyStsDiv = replyStsDiv;
    }
}
