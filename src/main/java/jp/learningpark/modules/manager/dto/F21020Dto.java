/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>F21020スマホ_褒めポイント登録画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/02/14 : wq: 新規<br />
 * @version 1.0
 */
public class F21020Dto {

    /**
     * 褒めポイント管理．褒め日時
     */
    private String complimentDt;

    /**
     * コードcd　(スタンプ)
     */
    private String stamp;

    /**
     * コード値
     */
    private String stampImg;

    /**
     * 褒めポイント管理．コメント
     */
    private String complimentCont;

    /**
     * コートマスター.コート値2
     */
    private String codValue2;

    /**
     * 保護者お知らせ閲覧状況．閲覧状況区分
     */
    private String readingStsDiv;

    /**
     * 生徒基本マスタ．保護者ID
     */
    private String guardId;

    /**
     * 褒めポイント管理．褒め日時を取得する
     *
     * @return complimentDt 褒めポイント管理．褒め日時
     */
    public String getComplimentDt() {
        return this.complimentDt;
    }

    /**
     * 褒めポイント管理．褒め日時を設定する
     *
     * @param complimentDt 褒めポイント管理．褒め日時
     */
    public void setComplimentDt(String complimentDt) {
        this.complimentDt = complimentDt;
    }

    /**
     * コード値　(スタンプ)を取得する
     *
     * @return stamp コード値　(スタンプ)
     */
    public String getStamp() {
        return this.stamp;
    }

    /**
     * コード値　(スタンプ)を設定する
     *
     * @param stamp コード値　(スタンプ)
     */
    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    /**
     * 褒めポイント管理．コメントを取得する
     *
     * @return complimentCont 褒めポイント管理．コメント
     */
    public String getComplimentCont() {
        return this.complimentCont;
    }

    /**
     * 褒めポイント管理．コメントを設定する
     *
     * @param complimentCont 褒めポイント管理．コメント
     */
    public void setComplimentCont(String complimentCont) {
        this.complimentCont = complimentCont;
    }

    /**
     * 保護者お知らせ閲覧状況．閲覧状況区分を取得する
     *
     * @return readingStsDiv 保護者お知らせ閲覧状況．閲覧状況区分
     */
    public String getReadingStsDiv() {
        return this.readingStsDiv;
    }

    /**
     * 保護者お知らせ閲覧状況．閲覧状況区分を設定する
     *
     * @param readingStsDiv 保護者お知らせ閲覧状況．閲覧状況区分
     */
    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }

    /**
     * 生徒基本マスタ．保護者IDを取得する
     *
     * @return guardId 生徒基本マスタ．保護者ID
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * 生徒基本マスタ．保護者IDを設定する
     *
     * @param guardId 生徒基本マスタ．保護者ID
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * コード値を取得する
     *
     * @return stampImg コード値
     */
    public String getStampImg() {
        return this.stampImg;
    }

    /**
     * コード値を設定する
     *
     * @param stampImg コード値
     */
    public void setStampImg(String stampImg) {
        this.stampImg = stampImg;
    }

    /**
     * コートマスター.コート値2を取得する
     *
     * @return codValue2 コートマスター.コート値2
     */
    public String getCodValue2() {
        return this.codValue2;
    }

    /**
     * コートマスター.コート値2を設定する
     *
     * @param codValue2 コートマスター.コート値2
     */
    public void setCodValue2(String codValue2) {
        this.codValue2 = codValue2;
    }
}
