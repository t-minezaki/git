/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.form;

/**
 * <p>理解度詳細画面(F10402,F20009,F30105)</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/08 : hujunjie: 新規<br />
 * @version 1.0
 */
public class ComprehensionDetailedForm {
    /**
     * 学習理解度（コード値）
     */
    private String learnLevUndsVal;

    /**
     * 学習理解度色（コード値２）
     */
    private String learnLevUndsColor;

    /**
     * 教科名
     */
    private String subjtVal;

    /**
     * ブロック種類区分
     */
    private String blockTypeDiv;

    /**
     * 章名
     */
    private String chaptNm;

    /**
     * learnLevUndsを取得する
     *
     * @return learnLevUnds 学習理解度（コード値）
     */
    public String getLearnLevUndsVal() {
        return learnLevUndsVal;
    }

    /**
     * learnLevUndsを設定する
     *
     * @param learnLevUnds 学習理解度（コード値）
     */
    public void setLearnLevUndsVal(String learnLevUndsVal) {
        this.learnLevUndsVal = learnLevUndsVal;
    }

    /**
     * learnLevUndsColorを取得する
     *
     * @return learnLevUndsColor 学習理解度色（コード値２）
     */
    public String getLearnLevUndsColor() {
        return learnLevUndsColor;
    }

    /**
     * learnLevUndsColorを設定する
     *
     * @param learnLevUndsColor 学習理解度色（コード値２）
     */
    public void setLearnLevUndsColor(String learnLevUndsColor) {
        this.learnLevUndsColor = learnLevUndsColor;
    }

    /**
     * subjtValを取得する
     *
     * @return subjtVal 教科名
     */
    public String getSubjtVal() {
        return subjtVal;
    }

    /**
     * subjtValを設定する
     *
     * @param subjtVal 教科名
     */
    public void setSubjtVal(String subjtVal) {
        this.subjtVal = subjtVal;
    }

    /**
     * blockTypeDivを取得する
     *
     * @return blockTypeDiv ブロック種類区分
     */
    public String getBlockTypeDiv() {
        return blockTypeDiv;
    }

    /**
     * blockTypeDivを設定する
     *
     * @param blockTypeDiv ブロック種類区分
     */
    public void setBlockTypeDiv(String blockTypeDiv) {
        this.blockTypeDiv = blockTypeDiv;
    }

    /**
     * chaptNmを取得する
     *
     * @return chaptNm 章名
     */
    public String getChaptNm() {
        return chaptNm;
    }

    /**
     * chaptNmを設定する
     *
     * @param chaptNm 章名
     */
    public void setChaptNm(String chaptNm) {
        this.chaptNm = chaptNm;
    }
}
