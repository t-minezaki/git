package jp.learningpark.modules.com.dto;

import java.util.List;

/**
 * <p>
 * QR解答相关 Dto
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/24 ： NWT)hxl ： 新規作成
 * @date 2020/02/24 16:45
 */
public class QRResponseDownloadInfoDto {

    /**
     * 教科コード
     */
    private String kyokacd;

    /**
     * ダウンロード要否フラグ
     */
    private String downloadflg;

    /**
     * ダウンロード用url、downloadflg＝0の場合、空白
     */
    private String downloadurl;

    /**
     * ダウンロードファイルの解凍パスワード
     */
    private String password;

    /**
     * yyyymmddhhmm 最新バージョンダウンロード開始日時
     */
    private String startdate;

    /**
     * Pdf情報配列名
     */
    private List<QRResponsePdfInfoDto> pdfinfo;

    /**
     * ダウンロード用token
     */
    private String token;

    /**
     * 教科コードを取得する
     *
     * @return kyokacd 教科コード
     */
    public String getKyokacd() {
        return this.kyokacd;
    }

    /**
     * 教科コードを設定する
     *
     * @param kyokacd 教科コード
     */
    public void setKyokacd(String kyokacd) {
        this.kyokacd = kyokacd;
    }

    /**
     * ダウンロード要否フラグを取得する
     *
     * @return downloadflg ダウンロード要否フラグ
     */
    public String getDownloadflg() {
        return this.downloadflg;
    }

    /**
     * ダウンロード要否フラグを設定する
     *
     * @param downloadflg ダウンロード要否フラグ
     */
    public void setDownloadflg(String downloadflg) {
        this.downloadflg = downloadflg;
    }

    /**
     * ダウンロード用url、downloadflg＝0の場合、空白を取得する
     *
     * @return downloadurl ダウンロード用url、downloadflg＝0の場合、空白
     */
    public String getDownloadurl() {
        return this.downloadurl;
    }

    /**
     * ダウンロード用url、downloadflg＝0の場合、空白を設定する
     *
     * @param downloadurl ダウンロード用url、downloadflg＝0の場合、空白
     */
    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    /**
     * ダウンロードファイルの解凍パスワードを取得する
     *
     * @return password ダウンロードファイルの解凍パスワード
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * ダウンロードファイルの解凍パスワードを設定する
     *
     * @param password ダウンロードファイルの解凍パスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * yyyymmddhhmm 最新バージョンダウンロード開始日時を取得する
     *
     * @return startdate yyyymmddhhmm 最新バージョンダウンロード開始日時
     */
    public String getStartdate() {
        return this.startdate;
    }

    /**
     * yyyymmddhhmm 最新バージョンダウンロード開始日時を設定する
     *
     * @param startdate yyyymmddhhmm 最新バージョンダウンロード開始日時
     */
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    /**
     * Pdf情報配列名を取得する
     *
     * @return pdfinfo Pdf情報配列名
     */
    public List<QRResponsePdfInfoDto> getPdfinfo() {
        return this.pdfinfo;
    }

    /**
     * Pdf情報配列名を設定する
     *
     * @param pdfinfo Pdf情報配列名
     */
    public void setPdfinfo(List<QRResponsePdfInfoDto> pdfinfo) {
        this.pdfinfo = pdfinfo;
    }

    /**
     * ダウンロード用tokenを取得する
     *
     * @return token ダウンロード用token
     */
    public String getToken() {
        return this.token;
    }

    /**
     * ダウンロード用tokenを設定する
     *
     * @param token ダウンロード用token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
