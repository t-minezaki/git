package jp.learningpark.modules.manager.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class F08009DspDto implements Serializable {
    /**
     * ユーザ表示項目設定.ID
     */
    private Integer id;
    /**
     * ユーザ表示項目設定.ユーザID
     */
    private String userId;
    /**
     * ユーザ表示項目設定.画面ID
     */
    private String menuId;
    /**
     * ユーザ表示項目設定.表示項目
     */
    private List<String> dspItemslist;
    /**
     * 表示しなければならない項目
     */
    private List<String> mustItemslist;
    /**
     * 全項目
     */
    private List<String> allItemslist;

    /**
     * ユーザ表示項目設定.作成日時
     */
    private Date cretDatime;
    /**
     * ユーザ表示項目設定.作成ユーザＩＤ
     */
    private String cretUsrId;
    /**
     * ユーザ表示項目設定.更新日時
     */
    private Date updDatime;
    /**
     * ユーザ表示項目設定.更新ユーザＩＤ
     */
    private String updUsrId;
    /**
     * ユーザ表示項目設定.削除フラグ
     */
    private Integer delFlg;
    /**
     * ユーザ表示項目設定.項目文字列を表示する
     */
    private String dspItems;

    /**
     * 画面．ユーザID
     *
     * @return id 画面．ユーザID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 画面．ユーザID
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 画面．ユーザID
     *
     * @return id 画面．ユーザID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 画面．ユーザID
     *
     * @param userId 画面．ユーザID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 画面．menuId
     *
     * @return menuId 画面．menuId
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * 画面．menuId
     *
     * @param menuId 画面．menuId
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }


    /**
     * 画面．作成日時
     *
     * @return cretDatime 画面．作成日時
     */
    public Date getCretDatime() {
        return cretDatime;
    }

    /**
     * 画面．作成日時
     *
     * @param cretDatime 画面．作成日時
     */
    public void setCretDatime(Date cretDatime) {
        this.cretDatime = cretDatime;
    }

    /**
     * 画面．作成ユーザＩＤ
     *
     * @return cretUsrId 画面．作成ユーザＩＤ
     */
    public String getCretUsrId() {
        return cretUsrId;
    }

    /**
     * 画面．作成ユーザＩＤ
     *
     * @param cretUsrId 画面．作成ユーザＩＤ
     */
    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }

    /**
     * 画面．更新日時
     *
     * @return updDatime 画面．更新日時
     */
    public Date getUpdDatime() {
        return updDatime;
    }

    /**
     * 画面．更新日時
     *
     * @param updDatime 画面．更新日時
     */
    public void setUpdDatime(Date updDatime) {
        this.updDatime = updDatime;
    }

    /**
     * 画面．更新ユーザＩＤ
     *
     * @return id 画面．更新ユーザＩＤ
     */
    public String getUpdUsrId() {
        return updUsrId;
    }

    /**
     * 画面．更新ユーザＩＤ
     *
     * @param updUsrId 画面．更新ユーザＩＤ
     */
    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }

    /**
     * 画面．削除フラグ
     *
     * @return delFlg 画面．削除フラグ
     */
    public Integer getDelFlg() {
        return delFlg;
    }

    /**
     * 画面．削除フラグ
     *
     * @param delFlg 画面．削除フラグ
     */
    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    /**
     * 画面．表示項目
     *
     * @return dspItemslist 画面．表示項目
     */
    public List<String> getDspItemslist() {
        return dspItemslist;
    }

    /**
     * 画面．表示項目
     *
     * @param dspItemslist 画面．表示項目
     */
    public void setDspItemslist(List<String> dspItemslist) {
        this.dspItemslist = dspItemslist;
    }

    /**
     * 画面．項目文字列を表示する
     *
     * @return dspItems 画面．項目文字列を表示する
     */
    public String getDspItems() {
        return dspItems;
    }

    /**
     * 画面．項目文字列を表示する
     *
     * @param dspItems 画面．項目文字列を表示する
     */
    public void setDspItems(String dspItems) {
        this.dspItems = dspItems;
    }

    /**
     * 表示しなければならない項目を取得する
     *
     * @return mustItemslist 表示しなければならない項目
     */
    public List<String> getMustItemslist() {
        return this.mustItemslist;
    }

    /**
     * 表示しなければならない項目を設定する
     *
     * @param mustItemslist 表示しなければならない項目
     */
    public void setMustItemslist(List<String> mustItemslist) {
        this.mustItemslist = mustItemslist;
    }

    /**
     * 全項目を取得する
     *
     * @return allItemslist 全項目
     */
    public List<String> getAllItemslist() {
        return this.allItemslist;
    }

    /**
     * 全項目を設定する
     *
     * @param allItemslist 全項目
     */
    public void setAllItemslist(List<String> allItemslist) {
        this.allItemslist = allItemslist;
    }
}
