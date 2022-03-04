/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>教科追加・編集画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/04/04: xiong: 新規<br />
 * @version 1.0
 */
public class F07004Dto {

    /**
     * 教科CD
     */
    String subCd;
    /**
     * 教科名
     */
    String subNm;
    /**
     * ソート
     */
    Integer sort;
    /**
     * 背景色
     */
    String backgroudColor;
    /**
     * 更新flg
     */
    Integer flg;
    /**
     * コード値４ ブロックタイプ
     */
    String codValue4;
    /**
     * コード値５ 学年区分
     */
    String codValue5;
    /**
     * subCdを取得する
     *
     * @return subCd subCd
     */
    public String getSubCd() {
        return subCd;
    }

    /**
     * subCdを設定する
     *
     * @param subCd subCd
     */
    public void setSubCd(String subCd) {
        this.subCd = subCd;
    }

    /**
     * subNmを取得する
     *
     * @return subNm subNm
     */
    public String getSubNm() {
        return subNm;
    }

    /**
     * subNmを設定する
     *
     * @param subNm subNm
     */
    public void setSubNm(String subNm) {
        this.subNm = subNm;
    }

    /**
     * sortを取得する
     *
     * @return sort sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * sortを設定する
     *
     * @param sort sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * backgroudColorを取得する
     *
     * @return backgroudColor backgroudColor
     */
    public String getBackgroudColor() {
        return backgroudColor;
    }

    /**
     * backgroudColorを設定する
     *
     * @param backgroudColor backgroudColor
     */
    public void setBackgroudColor(String backgroudColor) {
        this.backgroudColor = backgroudColor;
    }

    /**
     * flgを取得する
     *
     * @return flg flg
     */
    public Integer getFlg() {
        return flg;
    }

    /**
     * flgを設定する
     *
     * @param flg flg
     */
    public void setFlg(Integer flg) {
        this.flg = flg;
    }

    /**
     * codValue4を取得する
     * @return codValue4
     */
    public String getCodValue4() {
        return codValue4;
    }
    /**
     * codValue5を取得する
     * @return codValue5
     */
    public String getCodValue5() {
        return codValue5;
    }

    /**
     * codValue4を設定する
     * @param codValue4
     */
    public void setCodValue4(String codValue4) {
        this.codValue4 = codValue4;
    }

    /**
     * codValue5を設定する
     * @param codValue5
     */
    public void setCodValue5(String codValue5) {
        this.codValue5 = codValue5;
    }
}
