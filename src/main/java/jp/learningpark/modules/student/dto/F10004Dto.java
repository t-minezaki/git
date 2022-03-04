/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

/**
 *
 * @author NWT : zhaoxiaoqin <br />
 * @version 1.0
 */
public class F10004Dto {

    //カラーブロック管理id
    private Integer id;

    // 教科アイコン
    private String cod;

    // 教科名
    private String name;

    // 画像パス
    private String imgSrc;

    // カラーCD
    private String color;

    //ブロック表示名
    private String blockDispyNm;

    //ブロック画像区分
    private String blockPicDiv;

    //カラーCD
    private String colorId;

    //ブロックID
    private Integer blockId;

    /**
     * 画面．教科アイコン
     *
     * @return cod 画面．教科アイコン
     */
    public String getCod() {
        return cod;
    }

    /**
     * 画面．教科アイコン
     *
     * @param cod 画面．教科アイコン
     */
    public void setCod(String cod) {
        this.cod = cod;
    }


    /**
     * 画面．教科名
     *
     * @return name 画面．教科名
     */
    public String getName() {
        return name;
    }

    /**
     * 画面．教科名
     *
     * @param name 画面．教科名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 画面．画像パス
     *
     * @return imgSrc 画面．画像パス
     */
    public String getImgSrc() {
        return imgSrc;
    }

    /**
     * 画面．画像パス
     *
     * @param imgSrc 画面．画像パス
     */
    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    /**
     * 画面．カラーCD
     *
     * @return color 画面．カラーCD
     */
    public String getColor() {
        return color;
    }

    /**
     * 画面．カラーCD
     *
     * @param color 画面．カラーCD
     */
    public void setColor(String color) {
        this.color = color;
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
     * ブロック画像区分を取得する
     *
     * @return blockPicDiv ブロック画像区分
     */
    public String getBlockPicDiv() {
        return this.blockPicDiv;
    }

    /**
     * ブロック画像区分を設定する
     *
     * @param blockPicDiv ブロック画像区分
     */
    public void setBlockPicDiv(String blockPicDiv) {
        this.blockPicDiv = blockPicDiv;
    }

    /**
     * カラーCDを取得する
     *
     * @return colorId カラーCD
     */
    public String getColorId() {
        return this.colorId;
    }

    /**
     * カラーCDを設定する
     *
     * @param colorId カラーCD
     */
    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    /**
     * カラーブロック管理idを取得する
     *
     * @return id カラーブロック管理id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * カラーブロック管理idを設定する
     *
     * @param id カラーブロック管理id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * ブロックIDを取得する
     *
     * @return blockId ブロックID
     */
    public Integer getBlockId() {
        return this.blockId;
    }

    /**
     * ブロックIDを設定する
     *
     * @param blockId ブロックID
     */
    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }
}
