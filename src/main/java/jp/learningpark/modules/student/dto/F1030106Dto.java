/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * <p>印刷ブロック(R10001印刷用) DTO。</p >
 *
 * @author NWT : yangwei <br />
 * 変更履歴 <br />
 * 2019/11/12 : yangwei: 新規<br />
 * @version 1.0
 */
public class F1030106Dto {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

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
    @NotNull
    private String blockTypeDiv;

    /**
     * ブロック画像区分
     */
    private String blockPicDiv;

    /**
     * 作成日時
     */
    private Timestamp cretDatime;

    /**
     * 作成ユーザＩＤ
     */
    private String cretUsrId;

    /**
     * 更新日時
     */
    private Timestamp updDatime;

    /**
     * 更新ユーザＩＤ
     */
    private String updUsrId;

    /**
     * 削除フラグ
     */
    private Integer delFlg;
    /**
     * ブロックカラー
     */
    private String colorId;
    /**
     * フォントの色
     */
    private String fontColor;
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
     * 上層ブロックIDを取得する
     *
     * @return upplevBlockId 上層ブロックID
     */
    public Integer getUpplevBlockId() {
        return this.upplevBlockId;
    }

    /**
     * 上層ブロックIDを設定する
     *
     * @param upplevBlockId 上層ブロックID
     */
    public void setUpplevBlockId(Integer upplevBlockId) {
        this.upplevBlockId = upplevBlockId;
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
     * 作成日時を取得する
     *
     * @return cretDatime 作成日時
     */
    public Timestamp getCretDatime() {
        return this.cretDatime;
    }

    /**
     * 作成日時を設定する
     *
     * @param cretDatime 作成日時
     */
    public void setCretDatime(Timestamp cretDatime) {
        this.cretDatime = cretDatime;
    }

    /**
     * 作成ユーザＩＤを取得する
     *
     * @return cretUsrId 作成ユーザＩＤ
     */
    public String getCretUsrId() {
        return this.cretUsrId;
    }

    /**
     * 作成ユーザＩＤを設定する
     *
     * @param cretUsrId 作成ユーザＩＤ
     */
    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }

    /**
     * 更新日時を取得する
     *
     * @return updDatime 更新日時
     */
    public Timestamp getUpdDatime() {
        return this.updDatime;
    }

    /**
     * 更新日時を設定する
     *
     * @param updDatime 更新日時
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }

    /**
     * 更新ユーザＩＤを取得する
     *
     * @return updUsrId 更新ユーザＩＤ
     */
    public String getUpdUsrId() {
        return this.updUsrId;
    }

    /**
     * 更新ユーザＩＤを設定する
     *
     * @param updUsrId 更新ユーザＩＤ
     */
    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }

    /**
     * 削除フラグを取得する
     *
     * @return delFlg 削除フラグ
     */
    public Integer getDelFlg() {
        return this.delFlg;
    }

    /**
     * 削除フラグを設定する
     *
     * @param delFlg 削除フラグ
     */
    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
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
