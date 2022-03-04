/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.util.Date;

/**
 * <p>学習者の進捗一覧画面(ウィークリー)（PC）DTO。</p >
 *
 * @author NWT : yangfan <br />
 * 変更履歴 <br />
 * 2018/10/16 : yangfan: 新規<br />
 * @version 1.0
 */
public class F20006Dto {

    /**
     * ID
     */
    private Integer id;

    /**
     * 生徒タームプラン設定Id
     */
    private Integer termId;

    /**
     * 生徒タームプラン設定flg
     */
    private String isTerm;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * 教科区分
     */
    private String subjtDiv;

    /**
     * 教科名
     */
    private String subjtNm;

    /**
     * ブロック種類区分
     */
    private String blockTypeDiv;

    /**
     * ブロック表示名
     */
    private String blockDispyNm;

    /**
     * 計画年月日
     */
    private Date planYmd;

    /**
     * 開始時間
     */
    private String startTime;

    /**
     * 終了時間
     */
    private String endTime;

    /***
     * 理解度枠（色付き）
     */
    private String learnLevUnds;
    /**
     * 積み残し対象フラグ
     */
    private String remainDispFlg;

    /**
     * 生徒削除フラグ
     */
    private String stuDelFlg;

    /***
     * 曜日
     */
    private String dayWeek;

    /***
     * 単元ID
     */
    private String unitId;

    /***
     * 単元名
     */
    private String unitNm;

    /***
     * 枝番
     */
    private String bnum;


    /**
     * IDを取得する
     *
     * @return id ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * IDを設定する
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 教科区分を取得する
     *
     * @return subjtDiv 教科区分
     */
    public String getSubjtDiv() {
        return this.subjtDiv;
    }

    /**
     * 教科区分を設定する
     *
     * @param subjtDiv 教科区分
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }

    /**
     * 教科名を取得する
     *
     * @return subjtNm 教科名
     */
    public String getSubjtNm() {
        return this.subjtNm;
    }

    /**
     * 教科名を設定する
     *
     * @param subjtNm 教科名
     */
    public void setSubjtNm(String subjtNm) {
        this.subjtNm = subjtNm;
    }

    /**
     * ブロック種類区分を取得する
     *
     * @return blockTypeDiv ブロック種類区分
     */
    public String getBlockTypeDiv() {
        return this.blockTypeDiv;
    }

    /**
     * ブロック種類区分を設定する
     *
     * @param blockTypeDiv ブロック種類区分
     */
    public void setBlockTypeDiv(String blockTypeDiv) {
        this.blockTypeDiv = blockTypeDiv;
    }

    /**
     * ブロック表示名を取得する
     *
     * @return blockDispyNm ブロック表示名
     */
    public String getBlockDispyNm() {
        return this.blockDispyNm;
    }

    /**
     * ブロック表示名を設定する
     *
     * @param blockDispyNm ブロック表示名
     */
    public void setBlockDispyNm(String blockDispyNm) {
        this.blockDispyNm = blockDispyNm;
    }

    /**
     * 計画年月日を取得する
     *
     * @return planYmd 計画年月日
     */
    public Date getPlanYmd() {
        return this.planYmd;
    }

    /**
     * 計画年月日を設定する
     *
     * @param planYmd 計画年月日
     */
    public void setPlanYmd(Date planYmd) {
        this.planYmd = planYmd;
    }

    /**
     * 開始時間を取得する
     *
     * @return startTime 開始時間
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * 開始時間を設定する
     *
     * @param startTime 開始時間
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 終了時間を取得する
     *
     * @return endTime 終了時間
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     * 終了時間を設定する
     *
     * @param endTime 終了時間
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 理解度枠（色付き）を取得する
     *
     * @return learnLevUnds 理解度枠（色付き）
     */
    public String getLearnLevUnds() {
        return this.learnLevUnds;
    }

    /**
     * 理解度枠（色付き）を設定する
     *
     * @param learnLevUnds 理解度枠（色付き）
     */
    public void setLearnLevUnds(String learnLevUnds) {
        this.learnLevUnds = learnLevUnds;
    }

    /**
     * 積み残し対象フラグを取得する
     *
     * @return remainDispFlg 積み残し対象フラグ
     */
    public String getRemainDispFlg() {
        return this.remainDispFlg;
    }

    /**
     * 積み残し対象フラグを設定する
     *
     * @param remainDispFlg 積み残し対象フラグ
     */
    public void setRemainDispFlg(String remainDispFlg) {
        this.remainDispFlg = remainDispFlg;
    }

    /**
     * 生徒削除フラグを取得する
     *
     * @return stuDelFlg 生徒削除フラグ
     */
    public String getStuDelFlg() {
        return this.stuDelFlg;
    }

    /**
     * 生徒削除フラグを設定する
     *
     * @param stuDelFlg 生徒削除フラグ
     */
    public void setStuDelFlg(String stuDelFlg) {
        this.stuDelFlg = stuDelFlg;
    }

    /**
     * 曜日を取得する
     *
     * @return dayWeek 曜日
     */
    public String getDayWeek() {
        return this.dayWeek;
    }

    /**
     * 曜日を設定する
     *
     * @param dayWeek 曜日
     */
    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }

    /**
     * 単元IDを取得する
     *
     * @return unitId 単元ID
     */
    public String getUnitId() {
        return this.unitId;
    }

    /**
     * 単元IDを設定する
     *
     * @param unitId 単元ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    /**
     * 単元名を取得する
     *
     * @return unitNm 単元名
     */
    public String getUnitNm() {
        return this.unitNm;
    }

    /**
     * 単元名を設定する
     *
     * @param unitNm 単元名
     */
    public void setUnitNm(String unitNm) {
        this.unitNm = unitNm;
    }

    /**
     * 枝番を取得する
     *
     * @return bnum 枝番
     */
    public String getBnum() {
        return this.bnum;
    }

    /**
     * 枝番を設定する
     *
     * @param bnum 枝番
     */
    public void setBnum(String bnum) {
        this.bnum = bnum;
    }

    /**
     * 生徒タームプラン設定Idを設定する
     *
     * @return termId 生徒タームプラン設定Id
     */
    public Integer getTermId() {
        return this.termId;
    }

    /**
     * 生徒タームプラン設定Idを取得する
     *
     * @param termId 生徒タームプラン設定Id
     */
    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    /**
     * 生徒タームプラン設定flgを設定する
     *
     * @return isTerm 生徒タームプラン設定flg
     */
    public String getIsTerm() {
        return this.isTerm;
    }

    /**
     * 生徒タームプラン設定flgを取得する
     *
     * @param isTerm 生徒タームプラン設定flg
     */
    public void setIsTerm(String isTerm) {
        this.isTerm = isTerm;
    }
}
