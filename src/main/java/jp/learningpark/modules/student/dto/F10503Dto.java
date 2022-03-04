/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

import java.math.BigDecimal;

/**
 * <p>定期テスト結果確認画面Dto</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/19 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F10503Dto {
    /**
     * 教科名（コード値）
     */
    private String subjtVal;

    /**
     * 教科区分
     */
    private String subjtDiv;

    /**
     * 結果点数
     */
    private Integer resultPoints;

    /**
     * 目標満点
     */
    private Integer goalPerfectPoints;

    /**
     * 結果満点
     */
    private Integer resultPerfectPoints;

    /**
     * 平均結果
     */
    private BigDecimal resultAvg;

    /**
     * 結果順位
     */
    private Integer resultRank;

    /**
     * subjtValを取得する
     *
     * @return subjtVal subjtVal
     */
    public String getSubjtVal() {
        return subjtVal;
    }

    /**
     * subjtValを設定する
     *
     * @param subjtVal subjtVal
     */
    public void setSubjtVal(String subjtVal) {
        this.subjtVal = subjtVal;
    }

    /**
     * subjtDivを取得する
     *
     * @return subjtDiv subjtDiv
     */
    public String getSubjtDiv() {
        return subjtDiv;
    }

    /**
     * subjtDivを設定する
     *
     * @param subjtDiv subjtDiv
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }

    /**
     * resultPointsを取得する
     *
     * @return resultPoints resultPoints
     */
    public Integer getResultPoints() {
        return resultPoints;
    }

    /**
     * resultPointsを設定する
     *
     * @param resultPoints resultPoints
     */
    public void setResultPoints(Integer resultPoints) {
        this.resultPoints = resultPoints;
    }

    /**
     * goalPerfectPointsを取得する
     *
     * @return goalPerfectPoints goalPerfectPoints
     */
    public Integer getGoalPerfectPoints() {
        return goalPerfectPoints;
    }

    /**
     * goalPerfectPointsを設定する
     *
     * @param goalPerfectPoints goalPerfectPoints
     */
    public void setGoalPerfectPoints(Integer goalPerfectPoints) {
        this.goalPerfectPoints = goalPerfectPoints;
    }

    /**
     * resultAvgを取得する
     *
     * @return resultAvg resultAvg
     */
    public BigDecimal getResultAvg() {
        return resultAvg;
    }

    /**
     * resultAvgを設定する
     *
     * @param resultAvg resultAvg
     */
    public void setResultAvg(BigDecimal resultAvg) {
        this.resultAvg = resultAvg;
    }

    /**
     * resultRankを取得する
     *
     * @return resultRank resultRank
     */
    public Integer getResultRank() {
        return resultRank;
    }

    /**
     * resultRankを設定する
     *
     * @param resultRank resultRank
     */
    public void setResultRank(Integer resultRank) {
        this.resultRank = resultRank;
    }

    /**
     * resultPerfectPointsを取得する
     *
     * @return resultPerfectPoints resultPerfectPoints
     */
    public Integer getResultPerfectPoints() {
        return resultPerfectPoints;
    }

    /**
     * resultPerfectPointsを設定する
     *
     * @param resultPerfectPoints resultPerfectPoints
     */
    public void setResultPerfectPoints(Integer resultPerfectPoints) {
        this.resultPerfectPoints = resultPerfectPoints;
    }
}
