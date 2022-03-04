/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>F20003 理解度と枝番</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/10/25 : gong: 新規<br />
 * @version 1.0
 */
public class F20003BnumLearnLevDto {

    /**
     * 枝番
     */
    private Integer bnum;

    /**
     * 学習理解度
     */
    private String learnLevUnds;

    /**
     * 計画学習時間（分）
     */
    private Integer planLearnTm;

    /**
     * 生徒タームプラン設定対象フラグ
     */
    private String termFlg;

    /**
     * 計画登録フラグ
     */
    private String planRegFlg;

    /**
     * 計画学習時間（分）/ 30
     */
    private Integer blockQuantity;

    /**
     * tdのID生成する
     */
    private String id;

    /**
     * 補足td
     */
    private boolean isEmpty;

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
     * tdのID生成するを設定する
     *
     * @return id tdのID生成する
     */
    public String getId() {
        return this.id;
    }

    /**
     * tdのID生成するを取得する
     *
     * @param id tdのID生成する
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 補足tdを設定する
     *
     * @return isEmpty 補足td
     */
    public boolean isIsEmpty() {
        return this.isEmpty;
    }

    /**
     * 補足tdを取得する
     *
     * @param isEmpty 補足td
     */
    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    /**
     * 生徒タームプラン設定対象フラグを設定する
     *
     * @return termFlg 生徒タームプラン設定対象フラグ
     */
    public String getTermFlg() {
        return this.termFlg;
    }

    /**
     * 生徒タームプラン設定対象フラグを取得する
     *
     * @param termFlg 生徒タームプラン設定対象フラグ
     */
    public void setTermFlg(String termFlg) {
        this.termFlg = termFlg;
    }

    /**
     * 計画学習時間（分） 30を設定する
     *
     * @return blockQuantity 計画学習時間（分） 30
     */
    public Integer getBlockQuantity() {
        return this.blockQuantity;
    }

    /**
     * 計画学習時間（分） 30を取得する
     *
     * @param blockQuantity 計画学習時間（分） 30
     */
    public void setBlockQuantity(Integer blockQuantity) {
        this.blockQuantity = blockQuantity;
    }

    /**
     * 計画登録フラグを設定する
     *
     * @return planRegFlg 計画登録フラグ
     */
    public String getPlanRegFlg() {
        return this.planRegFlg;
    }

    /**
     * 計画登録フラグを取得する
     *
     * @param planRegFlg 計画登録フラグ
     */
    public void setPlanRegFlg(String planRegFlg) {
        this.planRegFlg = planRegFlg;
    }

    /**
     * 計画学習時間（分）を取得する
     *
     * @return planLearnTm 計画学習時間（分）
     */
    public Integer getPlanLearnTm() {
        return this.planLearnTm;
    }

    /**
     * 計画学習時間（分）を設定する
     *
     * @param planLearnTm 計画学習時間（分）
     */
    public void setPlanLearnTm(Integer planLearnTm) {
        this.planLearnTm = planLearnTm;
    }
}
