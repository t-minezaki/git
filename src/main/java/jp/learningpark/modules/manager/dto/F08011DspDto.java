package jp.learningpark.modules.manager.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class F08011DspDto implements Serializable {
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
     * 表示項目
     */
    private String dspItems;
    /**
     * 表示しなければならない項目
     */
    private List<String> mustItems;
    /**
     * 全項目
     */
    private List<String> allItems;
    /**
     * ユーザ表示項目設定.作成日時
     */
    private Timestamp cretDatime;
    /**
     * ユーザ表示項目設定.作成ユーザＩＤ
     */
    private String cretUsrId;
    /**
     * ユーザ表示項目設定.更新日時
     */
    private Timestamp updDatime;
    /**
     * ユーザ表示項目設定.更新ユーザＩＤ
     */
    private String updUsrId;
    /**
     * ユーザ表示項目設定.削除フラグ
     */
    private Integer delFlg;

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
    public Timestamp getCretDatime() {
        return cretDatime;
    }

    /**
     * 画面．作成日時
     *
     * @param cretDatime 画面．作成日時
     */
    public void setCretDatime(Timestamp cretDatime) {
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
    public Timestamp getUpdDatime() {
        return updDatime;
    }

    /**
     * 画面．更新日時
     *
     * @param updDatime 画面．更新日時
     */
    public void setUpdDatime(Timestamp updDatime) {
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
     * 表示しなければならない項目を取得する
     *
     * @return mustItems 表示しなければならない項目
     */
    public List<String> getMustItems() {
        return this.mustItems;
    }

    /**
     * 表示しなければならない項目を設定する
     *
     * @param mustItems 表示しなければならない項目
     */
    public void setMustItems(List<String> mustItems) {
        this.mustItems = mustItems;
    }

    /**
     * 全項目を取得する
     *
     * @return allItems 全項目
     */
    public List<String> getAllItems() {
        return this.allItems;
    }

    /**
     * 全項目を設定する
     *
     * @param allItems 全項目
     */
    public void setAllItems(List<String> allItems) {
        this.allItems = allItems;
    }

    /**
     * 表示項目を取得する
     *
     * @return dspItems 表示項目
     */
    public String getDspItems() {
        return this.dspItems;
    }

    /**
     * 表示項目を設定する
     *
     * @param dspItems 表示項目
     */
    public void setDspItems(String dspItems) {
        this.dspItems = dspItems;
    }
}
