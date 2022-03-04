/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/24
 */

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/04/24 : lyh: 新規<br />
 * @version 7.0
 */
public class F11001Dto {

    /**
     * ブロック表示名
     */
    private String blockDispyNm;

    /**
     * 上層ブロックID(その他ブロック用)
     */
    private Integer upplevBlockId;

    /**
     * ブロック画像区分(その他ブロック用)
     */
    private String blockPicDiv;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * ブロック種類区分
     * C1  通常
     * O1  その他「趣味」
     * O2  その他「習い事」
     * O3  その他「その他」
     * P1  予習
     * R1  復習
     * S1  学習
     */
    private String blockTypeDiv;

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
     * 教科書デフォルト単元ID
     */
    private Integer textbDefUnitId;


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
     * 学習理解度
     */
    private String learnLevUnds;

    /**
     * 生徒ウィークリー計画実績設定ID
     */
    private Integer planPerfId;

    /**
     * 生徒タームプラン設定ID
     */
    private Integer stuTermPlanId;

    /**
     * 計画登録フラグ
     */
    private String planRegFlg;

//    /**
//     * 更新日時
//     */
//    private Timestamp updDatime;

    /**
     * 学習時期開始日
     */
    private String learnSeasnStartDy;

    /**
     * ブロック表示コンテキスト
     */
    private String blockContext;

    /**
     * 計画学習時間（分）
     */
    private Integer planLearnTm;
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
     * @return planPerfId 生徒ウィークリー計画実績設定ID
     */
    public Integer getPlanPerfId() {
        return this.planPerfId;
    }

    /**
     * 生徒ウィークリー計画実績設定IDを取得する
     *
     * @param planPerfId 生徒ウィークリー計画実績設定ID
     */
    public void setPlanPerfId(Integer planPerfId) {
        this.planPerfId = planPerfId;
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

//    /**
//     * 更新日時を設定する
//     */
//    public void setUpdDatime(Timestamp updDatime) {
//        this.updDatime = updDatime;
//    }
//
//    /**
//     * 更新日時を取得する
//     */
//    public Timestamp getUpdDatime() {
//        return updDatime;
//    }

    /**
     * 教科書デフォルト単元IDを設定する
     */
    public void setTextbDefUnitId(Integer textbDefUnitId) {
        this.textbDefUnitId = textbDefUnitId;
    }

    /**
     * 教科書デフォルト単元IDを取得する
     */
    public Integer getTextbDefUnitId() {
        return textbDefUnitId;
    }

    /**
     * 生徒タームプラン設定IDを設定する
     *
     * @return stuTermPlanId 生徒タームプラン設定ID
     */
    public Integer getStuTermPlanId() {
        return this.stuTermPlanId;
    }

    /**
     * 生徒タームプラン設定IDを取得する
     *
     * @param stuTermPlanId 生徒タームプラン設定ID
     */
    public void setStuTermPlanId(Integer stuTermPlanId) {
        this.stuTermPlanId = stuTermPlanId;
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
     * 計画学習時間（分）を設定する
     */
    public void setPlanLearnTm(Integer planLearnTm) {
        this.planLearnTm = planLearnTm;
    }

    /**
     * 計画学習時間（分）を取得する
     */
    public Integer getPlanLearnTm() {
        return planLearnTm;
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
