package jp.learningpark.modules.pop.dto;

import java.io.Serializable;

/**
 * <p>F09003 Dto</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/11/28: yang: 新規<br />
 * @version 5.0
 */
public class F09003Dto implements Serializable {
    /**
     * 組織名
     */
    private String orgNm;
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * 生徒姓名
     */
    private String stuName;
    /**
     * 生徒学年区分
     */
    private String codValue;
    /**
     * 生徒基本マスタ・ＱＲコード
     */
    private String qrCod;
    /**
     * 生徒基本マスタ・オリジナルCD
     */
    private String oriaCd;

    /**
     *  変更後のユーザーID
     */
    private String afterUsrId;

    /**
     *  学年
     */
    private String schy;

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
     * 生徒IDを取得する
     *
     * @return stuId 生徒ID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 生徒姓名を取得する
     *
     * @return stuName 生徒姓名
     */
    public String getStuName() {
        return this.stuName;
    }

    /**
     * 生徒姓名を設定する
     *
     * @param stuName 生徒姓名
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * 生徒学年区分を取得する
     *
     * @return codValue 生徒学年区分
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * 生徒学年区分を設定する
     *
     * @param codValue 生徒学年区分
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * 生徒基本マスタ・ＱＲコードを取得する
     *
     * @return qrCod 生徒基本マスタ・ＱＲコード
     */
    public String getQrCod() {
        return this.qrCod;
    }

    /**
     * 生徒基本マスタ・ＱＲコードを設定する
     *
     * @param qrCod 生徒基本マスタ・ＱＲコード
     */
    public void setQrCod(String qrCod) {
        this.qrCod = qrCod;
    }

    /**
     * 生徒基本マスタ・オリジナルCDを取得する
     *
     * @return oriaCd 生徒基本マスタ・オリジナルCD
     */
    public String getOriaCd() {
        return this.oriaCd;
    }

    /**
     * 生徒基本マスタ・オリジナルCDを設定する
     *
     * @param oriaCd 生徒基本マスタ・オリジナルCD
     */
    public void setOriaCd(String oriaCd) {
        this.oriaCd = oriaCd;
    }

    /**
     * 変更後のユーザーIDを取得する
     *
     * @return afterUsrId 変更後のユーザーID
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * 変更後のユーザーIDを設定する
     *
     * @param afterUsrId 変更後のユーザーID
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * 学年を取得する
     *
     * @return schy 学年
     */
    public String getSchy() {
        return this.schy;
    }

    /**
     * 学年を設定する
     *
     * @param schy 学年
     */
    public void setSchy(String schy) {
        this.schy = schy;
    }
}
