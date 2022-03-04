/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstCodDEntity;

/**
 * <p>F70003_教科メンテナンス一覧画面 Dto</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/04/02 : wen: 新規<br />
 * @version 1.0
 */
public class F07003Dto extends MstCodDEntity {

    /**
     * 教科CD
     */
    private String subjtCd;
    /**
     * 教科名
     */
    private String subjtNm;
    /**
     * 背景色
     */
    private String bgColor;
    /**
     * 表示用画像
     */
    private String displayImg;
    /**
     * 復習フラグ
     */
    private String reviewFlg;
    /**
     * 学年
     */
    private String schy;
    /**
     * ボタン用画像
     */
    private String btnImg;
    /**
     * ソート
     */
    private Integer sort;

    /**
     * 排他チェック用更新日時
     */
    private String updateTmForCheck;

    /**
     * 教科CDを取得する
     *
     * @return subjtCd 教科CD
     */
    public String getSubjtCd() {
        return this.subjtCd;
    }

    /**
     * 教科CDを設定する
     *
     * @param subjtCd 教科CD
     */
    public void setSubjtCd(String subjtCd) {
        this.subjtCd = subjtCd;
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
     * 背景色を取得する
     *
     * @return bgColor 背景色
     */
    public String getBgColor() {
        return this.bgColor;
    }

    /**
     * 背景色を設定する
     *
     * @param bgColor 背景色
     */
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    /**
     * 表示用画像を取得する
     *
     * @return displayImg 表示用画像
     */
    public String getDisplayImg() {
        return this.displayImg;
    }

    /**
     * 表示用画像を設定する
     *
     * @param displayImg 表示用画像
     */
    public void setDisplayImg(String displayImg) {
        this.displayImg = displayImg;
    }

    /**
     * 復習フラグを取得する
     *
     * @return reviewFlg 復習フラグ
     */
    public String getReviewFlg() {
        return this.reviewFlg;
    }

    /**
     * 復習フラグを設定する
     *
     * @param reviewFlg 復習フラグ
     */
    public void setReviewFlg(String reviewFlg) {
        this.reviewFlg = reviewFlg;
    }

    /**
     * 学年を取得する
     *
     * @return schy 学年
     */
    public String getSchy() {
        return this.schy;
    }

    /**
     * 学年を設定する
     *
     * @param schy 学年
     */
    public void setSchy(String schy) {
        this.schy = schy;
    }

    /**
     * ボタン用画像を取得する
     *
     * @return btnImg ボタン用画像
     */
    public String getBtnImg() {
        return this.btnImg;
    }

    /**
     * ボタン用画像を設定する
     *
     * @param btnImg ボタン用画像
     */
    public void setBtnImg(String btnImg) {
        this.btnImg = btnImg;
    }

    /**
     * ソートを取得する
     *
     * @return sort ソート
     */
    public Integer getSort() {
        return this.sort;
    }

    /**
     * ソートを設定する
     *
     * @param sort ソート
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 排他チェック用更新日時を取得する
     *
     * @return updateTmForCheck 排他チェック用更新日時
     */
    public String getUpdateTmForCheck() {
        return this.updateTmForCheck;
    }

    /**
     * 排他チェック用更新日時を設定する
     *
     * @param updateTmForCheck 排他チェック用更新日時
     */
    public void setUpdateTmForCheck(String updateTmForCheck) {
        this.updateTmForCheck = updateTmForCheck;
    }
}
