/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.utils.dto;

import java.sql.Timestamp;

/**
 * <p>大分類その他ブロックの対応する小分類ブロックリストはブロックマスタをもとに取得し</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/10/09 : gong: 新規<br />
 * @version 1.0
 */
public class BlockDto {

    /**
     * ブロック表示名
     */
    private String blockDispyNm;

    /**
     * 上層ブロックID
     */
    private Integer upplevBlockId;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * ブロック種類区分
     */
    private String blockTypeDiv;

    /**
     * ブロック画像区分
     */
    private String blockPicDiv;

    /**
     * ブロックID
     */
    private String blockId;

    /**
     * 教科区分
     */
    private String subjtDiv;
    
    /**
     * 教科区分名
     */
    private String subjtNm;

    /**
     * 学習理解度
     */
    private String learnLevUnds;

    /**
     * 教科書ID
     */
    private String textbId;

    /**
     * 単元ID
     */
    private String unitId;

    /**
     * 積み残し対象フラグ
     */
    private String remainDispFlg;

    /**
     * 枝番
     */
    private Integer bnum;

    /**
     * 実績学習時間（分）
     */
    private Integer perfLearnTm;

    /**
     * 生徒ウィークリー計画実績設定ID
     */
    private Integer weeklyPlanPerfId;
    
    /**
     * 計画登録フラグ
     */
    private String planRegFlg;
    
    /**
     * 更新日時
     */
    private Timestamp updDatime;

    /**
     * ブロック表示名を設定する
     *
     * @return blockDispyNm ブロック表示名
     */
    public String getBlockDispyNm() {
        return this.blockDispyNm;
    }

    /**
     * ブロック表示名を取得する
     *
     * @param blockDispyNm ブロック表示名
     */
    public void setBlockDispyNm(String blockDispyNm) {
        this.blockDispyNm = blockDispyNm;
    }

    /**
     * 上層ブロックIDを設定する
     *
     * @return upplevBlockId 上層ブロックID
     */
    public Integer getUpplevBlockId() {
        return this.upplevBlockId;
    }

    /**
     * 上層ブロックIDを取得する
     *
     * @param upplevBlockId 上層ブロックID
     */
    public void setUpplevBlockId(Integer upplevBlockId) {
        this.upplevBlockId = upplevBlockId;
    }

    /**
     * 生徒IDを設定する
     *
     * @return stuId 生徒ID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒IDを取得する
     *
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * ブロック種類区分を設定する
     *
     * @return blockTypeDiv ブロック種類区分
     */
    public String getBlockTypeDiv() {
        return this.blockTypeDiv;
    }

    /**
     * ブロック種類区分を取得する
     *
     * @param blockTypeDiv ブロック種類区分
     */
    public void setBlockTypeDiv(String blockTypeDiv) {
        this.blockTypeDiv = blockTypeDiv;
    }

    /**
     * ブロック画像区分を設定する
     *
     * @return blockPicDiv ブロック画像区分
     */
    public String getBlockPicDiv() {
        return this.blockPicDiv;
    }

    /**
     * ブロック画像区分を取得する
     *
     * @param blockPicDiv ブロック画像区分
     */
    public void setBlockPicDiv(String blockPicDiv) {
        this.blockPicDiv = blockPicDiv;
    }

    /**
     * ブロックIDを設定する
     *
     * @return blockId ブロックID
     */
    public String getBlockId() {
        return this.blockId;
    }

    /**
     * ブロックIDを取得する
     *
     * @param blockId ブロックID
     */
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    /**
     * 教科区分を設定する
     *
     * @return subjtDiv 教科区分
     */
    public String getSubjtDiv() {
        return this.subjtDiv;
    }

    /**
     * 教科区分を取得する
     *
     * @param subjtDiv 教科区分
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }

    /**
     * 学習理解度を設定する
     *
     * @return learnLevUnds 学習理解度
     */
    public String getLearnLevUnds() {
        return this.learnLevUnds;
    }

    /**
     * 学習理解度を取得する
     *
     * @param learnLevUnds 学習理解度
     */
    public void setLearnLevUnds(String learnLevUnds) {
        this.learnLevUnds = learnLevUnds;
    }

    /**
     * 教科書IDを設定する
     *
     * @return textbId 教科書ID
     */
    public String getTextbId() {
        return this.textbId;
    }

    /**
     * 教科書IDを取得する
     *
     * @param textbId 教科書ID
     */
    public void setTextbId(String textbId) {
        this.textbId = textbId;
    }

    /**
     * 単元IDを設定する
     *
     * @return unitId 単元ID
     */
    public String getUnitId() {
        return this.unitId;
    }

    /**
     * 単元IDを取得する
     *
     * @param unitId 単元ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    /**
     * 積み残し対象フラグを設定する
     *
     * @return remainDispFlg 積み残し対象フラグ
     */
    public String getRemainDispFlg() {
        return this.remainDispFlg;
    }

    /**
     * 積み残し対象フラグを取得する
     *
     * @param remainDispFlg 積み残し対象フラグ
     */
    public void setRemainDispFlg(String remainDispFlg) {
        this.remainDispFlg = remainDispFlg;
    }

    /**
     * 枝番を設定する
     *
     * @return bnum 枝番
     */
    public Integer getBnum() {
        return this.bnum;
    }

    /**
     * 枝番を取得する
     *
     * @param bnum 枝番
     */
    public void setBnum(Integer bnum) {
        this.bnum = bnum;
    }

    /**
     * 実績学習時間（分）を設定する
     *
     * @return perfLearnTm 実績学習時間（分）
     */
    public Integer getPerfLearnTm() {
        return this.perfLearnTm;
    }

    /**
     * 実績学習時間（分）を取得する
     *
     * @param perfLearnTm 実績学習時間（分）
     */
    public void setPerfLearnTm(Integer perfLearnTm) {
        this.perfLearnTm = perfLearnTm;
    }

    /**
     * 生徒ウィークリー計画実績設定IDを設定する
     *
     * @return weeklyPlanPerfId 生徒ウィークリー計画実績設定ID
     */
    public Integer getWeeklyPlanPerfId() {
        return this.weeklyPlanPerfId;
    }

    /**
     * 生徒ウィークリー計画実績設定IDを取得する
     *
     * @param weeklyPlanPerfId 生徒ウィークリー計画実績設定ID
     */
    public void setWeeklyPlanPerfId(Integer weeklyPlanPerfId) {
        this.weeklyPlanPerfId = weeklyPlanPerfId;
    }
    
    /**
     * 計画登録フラグを設定する
     */
    public void setPlanRegFlg(String planRegFlg) {
        this.planRegFlg = planRegFlg;
    }
    
    /**
     * 計画登録フラグを取得する
     */
    public String getPlanRegFlg() {
        return planRegFlg;
    }

    /**
     * 教科区分名を設定する
     *
     * @return subjtNm 教科区分名
     */
    public String getSubjtNm() {
        return this.subjtNm;
    }

    /**
     * 教科区分名を取得する
     *
     * @param subjtNm 教科区分名
     */
    public void setSubjtNm(String subjtNm) {
        this.subjtNm = subjtNm;
    }
    
    /**
     * 更新日時を設定する
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }
    
    /**
     * 更新日時を取得する
     */
    public Timestamp getUpdDatime() {
        return updDatime;
    }
}
