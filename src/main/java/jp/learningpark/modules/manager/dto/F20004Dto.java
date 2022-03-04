/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.sql.Date;

/**
 * <p>・メンタートップ画面で選択した生徒の学習時間（タームプランで設定した教科の時間の合計）を週別で表示する。</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/10/24 : gong: 新規<br />
 * @version 1.0
 */
public class F20004Dto {

    /**
     * 生徒ID:ユーザID
     */
    private String stuId;

    /**
     * 氏名
     */
    private String stuNm;

    /**
     * 学年区分
     */
    private String schyDiv;

    /**
     * 学年
     */
    private String schy;

    /**
     * 学校名
     */
    private String schNm;

    /**
     * 学校ID
     */
    private String schId;

    /**
     * 組織名
     */
    private String orgNm;

    /**
     * 塾ID
     */
    private String orgId;

    /**
     * 計画学習時間（分）
     */
    private Integer planLearnTm;

    /**
     * 学習時期開始日
     */
    private Date learnSeasnStartDy;

    /**
     * 計画学習時期
     */
    private Integer planLearnSeasn;

    /**
     * 学習時期マスタの学習時期開始日
     */
    private String learnSeasnStartDyDisplay;

    /**
     * 生徒タームプラン設定の計画学習時間（分）※合計値SUM（）
     */
    private Integer planLearnTmSum;

    /**
     * 生徒タームプラン設定の計画学習時間（分）を設定する
     *
     * @return planLearnTm 生徒タームプラン設定の計画学習時間（分）
     */
    public Integer getPlanLearnTm() {
        return this.planLearnTm;
    }

    /**
     * 生徒タームプラン設定の計画学習時間（分）を取得する
     *
     * @param planLearnTm 生徒タームプラン設定の計画学習時間（分）
     */
    public void setPlanLearnTm(Integer planLearnTm) {
        this.planLearnTm = planLearnTm;
    }

    /**
     * 計画学習時期を設定する
     *
     * @return planLearnSeasn 計画学習時期
     */
    public Integer getPlanLearnSeasn() {
        return this.planLearnSeasn;
    }

    /**
     * 計画学習時期を取得する
     *
     * @param planLearnSeasn 計画学習時期
     */
    public void setPlanLearnSeasn(Integer planLearnSeasn) {
        this.planLearnSeasn = planLearnSeasn;
    }

    /**
     * 学習時期マスタの学習時期開始日を設定する
     *
     * @return learnSeasnStartDyDisplay 学習時期マスタの学習時期開始日
     */
    public String getLearnSeasnStartDyDisplay() {
        return this.learnSeasnStartDyDisplay;
    }

    /**
     * 学習時期マスタの学習時期開始日を取得する
     *
     * @param learnSeasnStartDyDisplay 学習時期マスタの学習時期開始日
     */
    public void setLearnSeasnStartDyDisplay(String learnSeasnStartDyDisplay) {
        this.learnSeasnStartDyDisplay = learnSeasnStartDyDisplay;
    }

    /**
     * 生徒タームプラン設定の計画学習時間（分）※合計値SUM（）を設定する
     *
     * @return planLearnTmSum 生徒タームプラン設定の計画学習時間（分）※合計値SUM（）
     */
    public Integer getPlanLearnTmSum() {
        return this.planLearnTmSum;
    }

    /**
     * 生徒タームプラン設定の計画学習時間（分）※合計値SUM（）を取得する
     *
     * @param planLearnTmSum 生徒タームプラン設定の計画学習時間（分）※合計値SUM（）
     */
    public void setPlanLearnTmSum(Integer planLearnTmSum) {
        this.planLearnTmSum = planLearnTmSum;
    }

    /**
     * 学習時期開始日を設定する
     *
     * @return learnSeasnStartDy 学習時期開始日
     */
    public Date getLearnSeasnStartDy() {
        return this.learnSeasnStartDy;
    }

    /**
     * 学習時期開始日を取得する
     *
     * @param learnSeasnStartDy 学習時期開始日
     */
    public void setLearnSeasnStartDy(Date learnSeasnStartDy) {
        this.learnSeasnStartDy = learnSeasnStartDy;
    }

    /**
     * 生徒ID:ユーザIDを設定する
     *
     * @return stuId 生徒ID:ユーザID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒ID:ユーザIDを取得する
     *
     * @param stuId 生徒ID:ユーザID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 氏名を設定する
     *
     * @return stuNm 氏名
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 氏名を取得する
     *
     * @param stuNm 氏名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 学年区分を設定する
     *
     * @return schyDiv 学年区分
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * 学年区分を取得する
     *
     * @param schyDiv 学年区分
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * 学年を設定する
     *
     * @return schy 学年
     */
    public String getSchy() {
        return this.schy;
    }

    /**
     * 学年を取得する
     *
     * @param schy 学年
     */
    public void setSchy(String schy) {
        this.schy = schy;
    }

    /**
     * 学校名を設定する
     *
     * @return schNm 学校名
     */
    public String getSchNm() {
        return this.schNm;
    }

    /**
     * 学校名を取得する
     *
     * @param schNm 学校名
     */
    public void setSchNm(String schNm) {
        this.schNm = schNm;
    }

    /**
     * 学校IDを設定する
     *
     * @return schId 学校ID
     */
    public String getSchId() {
        return this.schId;
    }

    /**
     * 学校IDを取得する
     *
     * @param schId 学校ID
     */
    public void setSchId(String schId) {
        this.schId = schId;
    }

    /**
     * 組織名を設定する
     *
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織名を取得する
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * 塾IDを設定する
     *
     * @return orgId 塾ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 塾IDを取得する
     *
     * @param orgId 塾ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
