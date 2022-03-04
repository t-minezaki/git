/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/18 : gong: 新規<br />
 * @version 1.0
 */
public class F20002TextListDto {
    /**
     * 教科書ID:9999・・・シーケンスによる連番
     */
    private Integer textbId;


    /**
     * 教科書名
     */
    private String textbNm;

    /**
     * 教科書選択区分
     */
    private String selFlg;

    /**
     * 教科区分:教科を識別する区分
     KG：国學
     MT：数学
     SC：理科
     ST：社会
     EG：英語
     ※区分についてコードマスタより管理
     */
    private String subjtDiv;

    /**
     * 更新日時
     */
    private String updateTimeStr;

    /**
     * 教科書ID:9999・・・シーケンスによる連番を設定する
     *
     * @return textbId 教科書ID:9999・・・シーケンスによる連番
     */
    public Integer getTextbId() {
        return this.textbId;
    }

    /**
     * 教科書ID:9999・・・シーケンスによる連番を取得する
     *
     * @param textbId 教科書ID:9999・・・シーケンスによる連番
     */
    public void setTextbId(Integer textbId) {
        this.textbId = textbId;
    }

    /**
     * 教科書名を設定する
     *
     * @return textbNm 教科書名
     */
    public String getTextbNm() {
        return this.textbNm;
    }

    /**
     * 教科書名を取得する
     *
     * @param textbNm 教科書名
     */
    public void setTextbNm(String textbNm) {
        this.textbNm = textbNm;
    }

    /**
     * 教科書選択区分を設定する
     *
     * @return selFlg 教科書選択区分
     */
    public String getSelFlg() {
        return this.selFlg;
    }

    /**
     * 教科書選択区分を取得する
     *
     * @param selFlg 教科書選択区分
     */
    public void setSelFlg(String selFlg) {
        this.selFlg = selFlg;
    }

    /**
     * 教科区分:教科を識別する区分     KG：国學     MT：数学     SC：理科     ST：社会     EG：英語     ※区分についてコードマスタより管理を設定する
     *
     * @return subjtDiv 教科区分:教科を識別する区分     KG：国學     MT：数学     SC：理科     ST：社会     EG：英語     ※区分についてコードマスタより管理
     */
    public String getSubjtDiv() {
        return this.subjtDiv;
    }

    /**
     * 教科区分:教科を識別する区分     KG：国學     MT：数学     SC：理科     ST：社会     EG：英語     ※区分についてコードマスタより管理を取得する
     *
     * @param subjtDiv 教科区分:教科を識別する区分     KG：国學     MT：数学     SC：理科     ST：社会     EG：英語     ※区分についてコードマスタより管理
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }

    /**
     * 更新日時を設定する
     *
     * @return updateTimeStr 更新日時
     */
    public String getUpdateTimeStr() {
        return this.updateTimeStr;
    }

    /**
     * 更新日時を取得する
     *
     * @param updateTimeStr 更新日時
     */
    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }
}
