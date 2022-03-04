/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

/**
 * 生徒ウィークリー計画実績設定
 *
 * @author gaoli <br />
 *         変更履歴 <br />
 *         2018/10/17 : gaoli: 新規<br />
 * @version 1.0
 */
public class F1030103Dto {

    /**
     * 生徒ウィークリー計画実績設定ID
     */
    private Integer planPerfId;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * 単元ID
     */
    private Integer unitId;

    /**
     * 生徒タームプラン設定ID
     */
    private Integer stuTermPlanId;

    /**
     * ブロック表示名
     */
    private String blockDispyNm;

    /**
     * ブロック種類区分
     */
    private String blockTypeDiv;

    /**
     * 計画年月日
     */
    private String planYmd;

    /**
     * 教科区分
     */
    private String subjtDiv;

    /**
     * 教科
     */
    private String subjtNm;

    /**
     * 生徒計画学習時間（分）
     */
    private Integer stuPlanLearnTm;

    /**
     * 計画学習時期ID
     */
    private Integer planLearnSeasnId;

    /**
     * 実績学習時間（分）
     */
    private Integer perfLearnTm;

    /**
     * 学習理解度
     */
    private String learnLevUnds;

    /**
     * 積み残し対象フラグ
     */
    private String remainDispFlg;
    
    /**
     * 学習時期開始日
     */
    private String learnSeasnStartDy;
    
    /**
     * ブロック表示コンテキスト
     */
    private String blockContext;
    /**
     * ブロックカラー
     */
    private String colorId;
    /**
     * フォントの色
     */
    private String fontColor;

    /**
     * ブロック表示コンテキストを設定する
     *
     * @return blockContext ブロック表示コンテキスト
     */
    public String getBlockContext() {
        return blockContext;
    }

    /**
     * ブロック表示コンテキストを取得する
     *
     * @param blockContext ブロック表示コンテキスト
     */
    public void setBlockContext(String blockContext) {
        this.blockContext = blockContext;
    }

    /**
     * 生徒IDを設定する
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 生徒IDを取得する
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * 単元IDを設定する
     */
    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    /**
     * 単元IDを取得する
     */
    public Integer getUnitId() {
        return unitId;
    }

    /**
     * 生徒タームプラン設定IDを設定する
     */
    public void setStuTermPlanId(Integer stuTermPlanId) {
        this.stuTermPlanId = stuTermPlanId;
    }

    /**
     * 生徒タームプラン設定IDを取得する
     */
    public Integer getStuTermPlanId() {
        return stuTermPlanId;
    }

    /**
     * ブロック表示名を設定する
     */
    public void setBlockDispyNm(String blockDispyNm) {
        this.blockDispyNm = blockDispyNm;
    }

    /**
     * ブロック表示名を取得する
     */
    public String getBlockDispyNm() {
        return blockDispyNm;
    }

    /**
     * ブロック種類区分を設定する
     */
    public void setBlockTypeDiv(String blockTypeDiv) {
        this.blockTypeDiv = blockTypeDiv;
    }

    /**
     * ブロック種類区分を取得する
     */
    public String getBlockTypeDiv() {
        return blockTypeDiv;
    }

    /**
     * 計画年月日を設定する
     */
    public void setPlanYmd(String planYmd) {
        this.planYmd = planYmd;
    }

    /**
     * 計画年月日を取得する
     */
    public String getPlanYmd() {
        return planYmd;
    }

    /**
     * 教科区分を設定する
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }

    /**
     * 教科区分を取得する
     */
    public String getSubjtDiv() {
        return subjtDiv;
    }

    /**
     * 教科を設定する
     */
    public void setSubjtNm(String subjtNm) {
        this.subjtNm = subjtNm;
    }

    /**
     * 教科を取得する
     */
    public String getSubjtNm() {
        return subjtNm;
    }

    /**
     * 生徒計画学習時間（分）を設定する
     */
    public void setStuPlanLearnTm(Integer stuPlanLearnTm) {
        this.stuPlanLearnTm = stuPlanLearnTm;
    }

    /**
     * 生徒計画学習時間（分）を取得する
     */
    public Integer getStuPlanLearnTm() {
        return stuPlanLearnTm;
    }

    /**
     * 計画学習時期IDを設定する
     */
    public void setPlanLearnSeasnId(Integer planLearnSeasnId) {
        this.planLearnSeasnId = planLearnSeasnId;
    }

    /**
     * 計画学習時期IDを取得する
     */
    public Integer getPlanLearnSeasnId() {
        return planLearnSeasnId;
    }

    /**
     * 実績学習時間（分）を設定する
     */
    public void setPerfLearnTm(Integer perfLearnTm) {
        this.perfLearnTm = perfLearnTm;
    }

    /**
     * 実績学習時間（分）を取得する
     */
    public Integer getPerfLearnTm() {
        return perfLearnTm;
    }

    /**
     * 学習理解度を設定する
     */
    public void setLearnLevUnds(String learnLevUnds) {
        this.learnLevUnds = learnLevUnds;
    }

    /**
     * 学習理解度を取得する
     */
    public String getLearnLevUnds() {
        return learnLevUnds;
    }

    /**
     * 積み残し対象フラグを設定する
     */
    public void setRemainDispFlg(String remainDispFlg) {
        this.remainDispFlg = remainDispFlg;
    }

    /**
     * 積み残し対象フラグを取得する
     */
    public String getRemainDispFlg() {
        return remainDispFlg;
    }
    
    /**
     * 生徒ウィークリー計画実績設定IDを設定する
     *
     * @return weeklyPlanPerfId 生徒ウィークリー計画実績設定ID
     */
    public Integer getPlanPerfId() {
        return this.planPerfId;
    }

    /**
     * 生徒ウィークリー計画実績設定IDを取得する
     *
     * @param weeklyPlanPerfId 生徒ウィークリー計画実績設定ID
     */
    public void setPlanPerfId(Integer planPerfId) {
        this.planPerfId = planPerfId;
    }
    
    /**
     * 学習時期開始日を設定する
     */
    public void setLearnSeasnStartDy(String learnSeasnStartDy) {
        this.learnSeasnStartDy = learnSeasnStartDy;
    }
    
    /**
     * 学習時期開始日を取得する
     */
    public String getLearnSeasnStartDy() {
        return learnSeasnStartDy;
    }

    /**
     * フォントの色を取得する
     *
     * @return fontColor フォントの色
     */
    public String getFontColor() {
        return this.fontColor;
    }

    /**
     * フォントの色を設定する
     *
     * @param fontColor フォントの色
     */
    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    /**
     * ブロックカラーを取得する
     *
     * @return colorId ブロックカラー
     */
    public String getColorId() {
        return this.colorId;
    }

    /**
     * ブロックカラーを設定する
     *
     * @param colorId ブロックカラー
     */
    public void setColorId(String colorId) {
        this.colorId = colorId;
    }
}
