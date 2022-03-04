package jp.learningpark.modules.com.dto;

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
 * @date 2020/02/24 12:26
 */
public class QRResponseVerInfoDto {

    /**
     * 教科コード
     */
    private String kyokacd;

    /**
     * yyyymmddhhss　端末該当教科持っている最新のバージョン日時
     */
    private String verdatetime;

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
     * yyyymmddhhss　端末該当教科持っている最新のバージョン日時を取得する
     *
     * @return verdatetime yyyymmddhhss　端末該当教科持っている最新のバージョン日時
     */
    public String getVerdatetime() {
        return this.verdatetime;
    }

    /**
     * yyyymmddhhss　端末該当教科持っている最新のバージョン日時を設定する
     *
     * @param verdatetime yyyymmddhhss　端末該当教科持っている最新のバージョン日時
     */
    public void setVerdatetime(String verdatetime) {
        this.verdatetime = verdatetime;
    }
}
