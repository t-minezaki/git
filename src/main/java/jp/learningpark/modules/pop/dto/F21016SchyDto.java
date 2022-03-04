package jp.learningpark.modules.pop.dto;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/22 ： NWT)hxl ： 新規作成
 * @date 2019/11/22 14:13
 */
public class F21016SchyDto {
    /**
     * コードCD
     */
    private String codCd;
    /**
     * コード値
     */
    private String codValue;

    /**
     * コード値を取得する
     *
     * @return
     */
    public String getCodValue() {
        return codValue;
    }

    /**
     * コード値を設定する
     *
     * @param codValue
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * コードCDを取得する
     *
     * @return codCd コードCD
     */
    public String getCodCd() {
        return this.codCd;
    }

    /**
     * コードCDを設定する
     *
     * @param codCd コードCD
     */
    public void setCodCd(String codCd) {
        this.codCd = codCd;
    }
}
