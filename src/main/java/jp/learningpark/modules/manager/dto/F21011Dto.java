package jp.learningpark.modules.manager.dto;

/**
 * <p></p >
 *
 * @author NWT : LiYuHuan <br />
 * @version 1.0
 */
public class F21011Dto {
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * 変更後ユーザーID
     */
    private String afterUsrId;
    /**
     * 生徒基本マスタ．姓名_姓
     */
    private String stuNm;
    /**
     * 生徒基本マスタ．姓名_名
     */
    private String stuLnm;
    /**
     * 保護者基本マスタ．姓名_姓
     */
    private String flnmNm;

    /**
     * 保護者基本マスタ．姓名_名
     */
    private String flnmLnm;
    /**
     * コード値
     */
    private String codValue;

    /**
     * コードCD
     */
    private String codCd;

    /**
     * 指導報告書ステータス区分
     */
    private String status;
    /**
     * 学年区分
     */
    private String schyDiv;
    /**
     * お知らせ．お知らせレベル区分
     */
    private String noticeLevelDiv;



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
     * 生徒基本マスタ．姓名_姓を取得する
     *
     * @return stuNm 生徒基本マスタ．姓名_姓
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 生徒基本マスタ．姓名_姓を設定する
     *
     * @param stuNm 生徒基本マスタ．姓名_姓
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 生徒基本マスタ．姓名_名を取得する
     *
     * @return stuLnm 生徒基本マスタ．姓名_名
     */
    public String getStuLnm() {
        return this.stuLnm;
    }

    /**
     * 生徒基本マスタ．姓名_名を設定する
     *
     * @param stuLnm 生徒基本マスタ．姓名_名
     */
    public void setStuLnm(String stuLnm) {
        this.stuLnm = stuLnm;
    }

    /**
     * 保護者基本マスタ．姓名_姓を取得する
     *
     * @return flnmNm 保護者基本マスタ．姓名_姓
     */
    public String getFlnmNm() {
        return this.flnmNm;
    }

    /**
     * 保護者基本マスタ．姓名_姓を設定する
     *
     * @param flnmNm 保護者基本マスタ．姓名_姓
     */
    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    /**
     * 保護者基本マスタ．姓名_名を取得する
     *
     * @return flnmLnm 保護者基本マスタ．姓名_名
     */
    public String getFlnmLnm() {
        return this.flnmLnm;
    }

    /**
     * 保護者基本マスタ．姓名_名を設定する
     *
     * @param flnmLnm 保護者基本マスタ．姓名_名
     */
    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }

    /**
     * コード値を取得する
     *
     * @return codValue コード値
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * コード値を設定する
     *
     * @param codValue コード値
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * 指導報告書ステータス区分を取得する
     *
     * @return status 指導報告書ステータス区分
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 指導報告書ステータス区分を設定する
     *
     * @param status 指導報告書ステータス区分
     */
    public void setStatus(String status) {
        this.status = status;
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

    /**
     * 学年区分を取得する
     *
     * @return schyDiv 学年区分
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * 学年区分を設定する
     *
     * @param schyDiv 学年区分
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * お知らせ．お知らせレベル区分を取得する
     *
     * @return noticeLevelDiv お知らせ．お知らせレベル区分
     */
    public String getNoticeLevelDiv() {
        return this.noticeLevelDiv;
    }

    /**
     * お知らせ．お知らせレベル区分を設定する
     *
     * @param noticeLevelDiv お知らせ．お知らせレベル区分
     */
    public void setNoticeLevelDiv(String noticeLevelDiv) {
        this.noticeLevelDiv = noticeLevelDiv;
    }
}
