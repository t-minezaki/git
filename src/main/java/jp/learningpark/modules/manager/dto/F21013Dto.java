/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2019/12/5 : lyh: 新規<br />
 * @version 1.0
 */
public class F21013Dto {
    /**
     * 変更後ユーザーID
     */
    private String afterUsrId;
    /**
     * 生徒姓名
     */
    private String stuNm;
    /**
     *対象年月日
     */
    private String tgtYmd;
    /**
     * コード値（教科）
     */
    private String subjtDiv;
    /**
     * コード値
     */
    private String codValue;
    /**
     * 出欠ステータス区分
     */
    private String absStsDiv;
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * 回数
     */
    private Integer timesNum;


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
     * 生徒姓名を取得する
     *
     * @return stuNm 生徒姓名
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 生徒姓名を設定する
     *
     * @param stuNm 生徒姓名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }



    /**
     * コード値（教科）を取得する
     *
     * @return subjtDiv コード値（教科）
     */
    public String getSubjtDiv() {
        return this.subjtDiv;
    }

    /**
     * コード値（教科）を設定する
     *
     * @param subjtDiv コード値（教科）
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }


    /**
     * 出欠ステータス区分を取得する
     *
     * @return absStsDiv 出欠ステータス区分
     */
    public String getAbsStsDiv() {
        return this.absStsDiv;
    }

    /**
     * 出欠ステータス区分を設定する
     *
     * @param absStsDiv 出欠ステータス区分
     */
    public void setAbsStsDiv(String absStsDiv) {
        this.absStsDiv = absStsDiv;
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
     * 回数を取得する
     *
     * @return timesNum 回数
     */
    public Integer getTimesNum() {
        return this.timesNum;
    }

    /**
     * 回数を設定する
     *
     * @param timesNum 回数
     */
    public void setTimesNum(Integer timesNum) {
        this.timesNum = timesNum;
    }

    /**
     * 対象年月日を取得する
     *
     * @return tgtYmd 対象年月日
     */
    public String getTgtYmd() {
        return this.tgtYmd;
    }

    /**
     * 対象年月日を設定する
     *
     * @param tgtYmd 対象年月日
     */
    public void setTgtYmd(String tgtYmd) {
        this.tgtYmd = tgtYmd;
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
}