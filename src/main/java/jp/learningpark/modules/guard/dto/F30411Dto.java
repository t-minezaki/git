package jp.learningpark.modules.guard.dto;

/**
 * <p>
 * F30411Dto
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/18 ： NWT)hxl ： 新規作成
 * @date 2019/11/18 9:49
 */
public class F30411Dto {
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
     * @return
     */
    public String getCodValue() {
        return codValue;
    }

    /**
     * コード値を設定する
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
