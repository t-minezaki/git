package jp.learningpark.modules.manager.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class F08024Dto implements Serializable {

    /**
     * 排他チェックの利用更新日時
     */
    private String updateStrForCheck;

    /**
     * イベント.ID
     */
    private Integer id;

    /**
     * テンプレート.テンプレートCD
     */
    private String tmplateCd;

    /**
     * テンプレート.作成日時
     */
    private Timestamp cretDatime;

    /**
     * テンプレート.更新日時
     */
    private Timestamp updDatime;

    /**
     * テンプレート.カテゴリ
     */
    private String ctgyNm;

    /**
     * テンプレート.添付ファイルパス
     */
    private String attachFilePath;

    /**
     * テンプレート	.テンプレート内容
     */
    private String tmplateCnt;

    /**
     * テンプレート.作成ユーザ名称
     */
    private String cretUserName;

    /**
     * テンプレート.更新ユーザ名称
     */
    private String updUserName;

    /**
     * テンプレート	.テンプレートタイトル
     */
    private String tmplateTitle;

    /**
     * テンプレート.組織ID
     */
    private String orgId;

    /**
     * 組織マスタ．組織名
     */
    private String orgNm;
    /**
     * 表示項目
     */
    private String dspItems;
    /**
     * 表示しなければならない項目
     */
    private String mustItems;
    /**
     * 全項目
     */
    private String allItems;

    /**
     *  类别区分
     */
    private String tmplateTypeDiv;
    public String getTmplateTypeDiv() {
        return tmplateTypeDiv;
    }
    public void setTmplateTypeDiv(String tmplateTypeDiv) {
        this.tmplateTypeDiv = tmplateTypeDiv;
    }
    /**
     * 排他チェックの利用更新日時
     * @return 排他チェックの利用更新日時
     */
    public String getUpdateStrForCheck() {
        return updateStrForCheck;
    }

    /**
     *
     * @param updateStrForCheck 排他チェックの利用更新日時
     */
    public void setUpdateStrForCheck(String updateStrForCheck) {
        this.updateStrForCheck = updateStrForCheck;
    }

    /**
     * 画面．id
     *
     * @return id 画面．id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 画面．id
     *
     * @param id 画面．配信先組織リスト
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 画面．テンプレートCD
     *
     * @return tmplateCd 画面．テンプレートCD
     */
    public String getTmplateCd() {
        return tmplateCd;
    }

    /**
     * 画面．テンプレートCD
     *
     * @param tmplateCd 画面．テンプレートCD
     */
    public void setTmplateCd(String tmplateCd) {
        this.tmplateCd = tmplateCd;
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
     * 画面．カテゴリ
     *
     * @return ctgyNm 画面．カテゴリ
     */
    public String getCtgyNm() {
        return ctgyNm;
    }

    /**
     * 画面．カテゴリ
     *
     * @param ctgyNm 画面．カテゴリ
     */
    public void setCtgyNm(String ctgyNm) {
        this.ctgyNm = ctgyNm;
    }

    /**
     * 画面．テンプレートタイトル
     *
     * @return eventTitle 画面．イベントタイトル
     */
    public String getTmplateitle() {
        return tmplateTitle;
    }

    /**
     * 画面．テンプレートタイトル
     *
     * @param tmplateTitle 画面．テンプレートタイトル
     */
    public void setEventTitle(String tmplateTitle) {
        this.tmplateTitle = tmplateTitle;
    }

    /**
     * 画面．添付ファイルパス
     *
     * @return attachFilePath 画面．添付ファイルパス
     */
    public String getAttachFilePath() {
        return attachFilePath;
    }

    /**
     * 画面．添付ファイルパス
     *
     * @param attachFilePath 画面．添付ファイルパス
     */
    public void setAttachFilePath(String attachFilePath) {
        this.attachFilePath = attachFilePath;
    }

    /**
     * 画面．テンプレート内容
     *
     * @return eventCont 画面．テンプレート内容
     */
    public String getTmplateCnt() {
        return tmplateCnt;
    }

    /**
     * 画面．テンプレート内容
     *
     * @param tmplateCnt 画面．テンプレート内容
     */
    public void setTmplateCnt(String tmplateCnt) {
        this.tmplateCnt = tmplateCnt;
    }

    /**
     * 画面．作成ユーザ名称
     *
     * @return cretUserName 画面．作成ユーザ名称
     */
    public String getCretUserName() {
        return cretUserName;
    }

    /**
     * 画面．作成ユーザ名称
     *
     * @param cretUserName 画面．作成ユーザ名称
     */
    public void setCretUserName(String cretUserName) {
        this.cretUserName = cretUserName;
    }

    /**
     * 画面．更新ユーザ名称
     *
     * @return updUserName 画面．更新ユーザ名称
     */
    public String getUpdUserName() {
        return updUserName;
    }

    /**
     * 画面．更新ユーザ名称
     *
     * @param updUserName 画面．更新ユーザ名称
     */
    public void setUpdUserName(String updUserName) {
        this.updUserName = updUserName;
    }

    /**
     * 画面．テンプレートタイトル
     *
     * @return tmplateTitle 画面．テンプレートタイトル
     */
    public String getTmplateTitle() {
        return tmplateTitle;
    }

    /**
     * 画面．テンプレートタイトル
     *
     * @param tmplateTitle 画面．テンプレートタイトル
     */
    public void setTmplateTitle(String tmplateTitle) {
        this.tmplateTitle = tmplateTitle;
    }

    /**
     * 画面．組織ID
     *
     * @return orgId 画面．組織ID
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 画面．組織ID
     *
     * @param orgId 画面．組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 画面．組織名
     *
     * @return orgId 画面．組織名
     */
    public String getOrgNm() {
        return orgNm;
    }

    /**
     * 画面．組織名
     *
     * @param orgNm 画面．組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
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

    /**
     * 表示しなければならない項目を取得する
     *
     * @return mustItems 表示しなければならない項目
     */
    public String getMustItems() {
        return this.mustItems;
    }

    /**
     * 表示しなければならない項目を設定する
     *
     * @param mustItems 表示しなければならない項目
     */
    public void setMustItems(String mustItems) {
        this.mustItems = mustItems;
    }

    /**
     * 全項目を取得する
     *
     * @return allItems 全項目
     */
    public String getAllItems() {
        return this.allItems;
    }

    /**
     * 全項目を設定する
     *
     * @param allItems 全項目
     */
    public void setAllItems(String allItems) {
        this.allItems = allItems;
    }
}
