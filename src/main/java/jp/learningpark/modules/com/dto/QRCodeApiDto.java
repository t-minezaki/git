package jp.learningpark.modules.com.dto;

import jp.learningpark.modules.common.entity.MstStuEntity;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/04/08 ： NWT)hxl ： 新規作成
 * @date 2020/04/08 14:01
 */
public class QRCodeApiDto extends MstStuEntity {
    /**
     * 組織ID
     */
    private String orgId;

    /**
     * 保護者基本マスタ．メールアドレス
     */
    private String mailAddr;

    /**
     * 組織名
     */
    private String orgNm;

    /**
     * 学年
     */
    private String codValue;

    /**
     * 学年div
     */
    private String codCd;

    /**
     * 組織IDを取得する
     *
     * @return orgId 組織ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織IDを設定する
     *
     * @param orgId 組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 保護者基本マスタ．メールアドレスを取得する
     *
     * @return mailAddr 保護者基本マスタ．メールアドレス
     */
    public String getMailAddr() {
        return this.mailAddr;
    }

    /**
     * 保護者基本マスタ．メールアドレスを設定する
     *
     * @param mailAddr 保護者基本マスタ．メールアドレス
     */
    public void setMailAddr(String mailAddr) {
        this.mailAddr = mailAddr;
    }

    /**
     * 組織名を取得する
     *
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織名を設定する
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * 学年を取得する
     *
     * @return codValue 学年
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * 学年を設定する
     *
     * @param codValue 学年
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * 学年divを取得する
     *
     * @return codCd 学年div
     */
    public String getCodCd() {
        return this.codCd;
    }

    /**
     * 学年divを設定する
     *
     * @param codCd 学年div
     */
    public void setCodCd(String codCd) {
        this.codCd = codCd;
    }
}
