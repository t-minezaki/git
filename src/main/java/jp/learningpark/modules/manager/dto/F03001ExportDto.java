/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>F03001ExportDto</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/01/09 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F03001ExportDto {
    /**
     * 単元ID
     */
    private Integer unitId;

    /**
     * 単元NO
     */
    private String unitNo;

    /**
     * 節NO
     */
    private String sectnNo;

    /**
     * 章NO
     */
    private String chaptNo;

    /**
     * 章名
     */
    private String chaptNm;

    /**
     * 節名
     */
    private String sectnNm;

    /**
     * 項目表示名
     */
    private String unitDispyNm;

    /**
     * ページ（開始）
     */
    private String pageStart;

    /**
     * ページ（終了）
     */
    private String pageEnd;

    /**
     * 学習時期
     */
    private Integer planLearnSeasn;

    /**
     * 目標学習時間
     */
    private Integer planLearnTm;

    /**
     * unitIdを取得する
     *
     * @return unitId unitId
     */
    public Integer getUnitId() {
        return unitId;
    }

    /**
     * unitIdを設定する
     *
     * @param unitId unitId
     */
    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    /**
     * unitNoを取得する
     *
     * @return unitNo unitNo
     */
    public String getUnitNo() {
        return unitNo;
    }

    /**
     * unitNoを設定する
     *
     * @param unitNo unitNo
     */
    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    /**
     * sectnNoを取得する
     *
     * @return sectnNo sectnNo
     */
    public String getSectnNo() {
        return sectnNo;
    }

    /**
     * sectnNoを設定する
     *
     * @param sectnNo sectnNo
     */
    public void setSectnNo(String sectnNo) {
        this.sectnNo = sectnNo;
    }

    /**
     * chaptNoを取得する
     *
     * @return chaptNo chaptNo
     */
    public String getChaptNo() {
        return chaptNo;
    }

    /**
     * chaptNoを設定する
     *
     * @param chaptNo chaptNo
     */
    public void setChaptNo(String chaptNo) {
        this.chaptNo = chaptNo;
    }

    /**
     * chaptNmを取得する
     *
     * @return chaptNm chaptNm
     */
    public String getChaptNm() {
        return chaptNm;
    }

    /**
     * chaptNmを設定する
     *
     * @param chaptNm chaptNm
     */
    public void setChaptNm(String chaptNm) {
        this.chaptNm = chaptNm;
    }

    /**
     * sectnNmを取得する
     *
     * @return sectnNm sectnNm
     */
    public String getSectnNm() {
        return sectnNm;
    }

    /**
     * sectnNmを設定する
     *
     * @param sectnNm sectnNm
     */
    public void setSectnNm(String sectnNm) {
        this.sectnNm = sectnNm;
    }

    /**
     * unitDispyNmを取得する
     *
     * @return unitDispyNm unitDispyNm
     */
    public String getUnitDispyNm() {
        return unitDispyNm;
    }

    /**
     * unitDispyNmを設定する
     *
     * @param unitDispyNm unitDispyNm
     */
    public void setUnitDispyNm(String unitDispyNm) {
        this.unitDispyNm = unitDispyNm;
    }

    /**
     * pageStartを取得する
     *
     * @return pageStart pageStart
     */
    public String getPageStart() {
        return pageStart;
    }

    /**
     * pageStartを設定する
     *
     * @param pageStart pageStart
     */
    public void setPageStart(String pageStart) {
        this.pageStart = pageStart;
    }

    /**
     * pageEndを取得する
     *
     * @return pageEnd pageEnd
     */
    public String getPageEnd() {
        return pageEnd;
    }

    /**
     * pageEndを設定する
     *
     * @param pageEnd pageEnd
     */
    public void setPageEnd(String pageEnd) {
        this.pageEnd = pageEnd;
    }

    /**
     * planLearnSeasnを取得する
     *
     * @return planLearnSeasn planLearnSeasn
     */
    public Integer getPlanLearnSeasn() {
        return planLearnSeasn;
    }

    /**
     * planLearnSeasnを設定する
     *
     * @param planLearnSeasn planLearnSeasn
     */
    public void setPlanLearnSeasn(Integer planLearnSeasn) {
        this.planLearnSeasn = planLearnSeasn;
    }

    /**
     * planLearnTmを取得する
     *
     * @return planLearnTm planLearnTm
     */
    public Integer getPlanLearnTm() {
        return planLearnTm;
    }

    /**
     * planLearnTmを設定する
     *
     * @param planLearnTm planLearnTm
     */
    public void setPlanLearnTm(Integer planLearnTm) {
        this.planLearnTm = planLearnTm;
    }
}
