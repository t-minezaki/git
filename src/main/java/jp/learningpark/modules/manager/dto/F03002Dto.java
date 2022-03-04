/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.sql.Timestamp;

/**
 * <p>F03002_教科書単元編集画面 Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/01/08 : gong: 新規<br />
 * @version 1.0
 */
public class F03002Dto {

    /**
     * 教科書デフォルトターム情報Id
     */
    private Integer tdtiId;

    /**
     * 単元マスタId
     */
    private Integer unitId;

    /**
     * 教科書ID
     */
    private Integer textbId;

    /**
     * 教科区分
     */
    private String subjtDiv;

    /**
     * 教科書名
     */
    private String textbNm;

    /**
     * 出版社区分
     */
    private String publisherDiv;

    /**
     * 学年区分
     */
    private String schyDiv;

    /**
     * 単元表示名
     */
    private String unitDispyNm;

    /**
     * 単元NO
     */
    private String unitNo;

    /**
     * 節NO
     */
    private String sectnNo;

    /**
     * 章NO
     */
    private String chaptNo;

    /**
     * 教科書ページ
     */
    private String textbPage;

    /**
     * 学年
     */
    private String schy;

    /**
     * 教科
     */
    private String subjt;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 計画学習時期
     */
    private Integer planLearnSeasn;

    /**
     * 計画学習時期Id
     */
    private Integer planLearnSeasnId;

    /**
     * 計画学習時間（分）
     */
    private Integer planLearnTm;

    /**
     * 表示順番
     */
    private Integer dispyOrder;

    /**
     * 単元名
     */
    private String unitNm;

    /**
     * 節名
     */
    private String sectnNm;

    /**
     * 章名
     */
    private String chaptNm;

    /**
     * 更新日時Str
     */
    private String updateStr;

    /**
     * 選択Flg(0,1)
     */
    private Integer isCheck;

    /**
     * 更新日時
     */
    private Timestamp updDatime;

    /**
     * 教科書の更新日時
     */
    private Timestamp textbUpdatime;

    /**
     * 教科書の更新日時Str
     */
    private String textbUpdatimeStr;

    /**
     * 教科書デフォルトターム情報Idを設定する
     *
     * @return tdtiId 教科書デフォルトターム情報Id
     */
    public Integer getTdtiId() {
        return this.tdtiId;
    }

    /**
     * 教科書デフォルトターム情報Idを取得する
     *
     * @param tdtiId 教科書デフォルトターム情報Id
     */
    public void setTdtiId(Integer tdtiId) {
        this.tdtiId = tdtiId;
    }

    /**
     * 単元マスタIdを設定する
     *
     * @return unitId 単元マスタId
     */
    public Integer getUnitId() {
        return this.unitId;
    }

    /**
     * 単元マスタIdを取得する
     *
     * @param unitId 単元マスタId
     */
    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    /**
     * 教科書IDを設定する
     *
     * @return textbId 教科書ID
     */
    public Integer getTextbId() {
        return this.textbId;
    }

    /**
     * 教科書IDを取得する
     *
     * @param textbId 教科書ID
     */
    public void setTextbId(Integer textbId) {
        this.textbId = textbId;
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
     * 教科書名を設定する
     *
     * @return textbNm 教科書名
     */
    public String getTextbNm() {
        return this.textbNm;
    }

    /**
     * 教科書名を取得する
     *
     * @param textbNm 教科書名
     */
    public void setTextbNm(String textbNm) {
        this.textbNm = textbNm;
    }

    /**
     * 出版社区分を設定する
     *
     * @return publisherDiv 出版社区分
     */
    public String getPublisherDiv() {
        return this.publisherDiv;
    }

    /**
     * 出版社区分を取得する
     *
     * @param publisherDiv 出版社区分
     */
    public void setPublisherDiv(String publisherDiv) {
        this.publisherDiv = publisherDiv;
    }

    /**
     * 学年区分を設定する
     *
     * @return schyDiv 学年区分
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * 学年区分を取得する
     *
     * @param schyDiv 学年区分
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * 単元表示名を設定する
     *
     * @return unitDispyNm 単元表示名
     */
    public String getUnitDispyNm() {
        return this.unitDispyNm;
    }

    /**
     * 単元表示名を取得する
     *
     * @param unitDispyNm 単元表示名
     */
    public void setUnitDispyNm(String unitDispyNm) {
        this.unitDispyNm = unitDispyNm;
    }

    /**
     * 単元NOを設定する
     *
     * @return unitNo 単元NO
     */
    public String getUnitNo() {
        return this.unitNo;
    }

    /**
     * 単元NOを取得する
     *
     * @param unitNo 単元NO
     */
    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    /**
     * 節NOを設定する
     *
     * @return sectnNo 節NO
     */
    public String getSectnNo() {
        return this.sectnNo;
    }

    /**
     * 節NOを取得する
     *
     * @param sectnNo 節NO
     */
    public void setSectnNo(String sectnNo) {
        this.sectnNo = sectnNo;
    }

    /**
     * 章NOを設定する
     *
     * @return chaptNo 章NO
     */
    public String getChaptNo() {
        return this.chaptNo;
    }

    /**
     * 章NOを取得する
     *
     * @param chaptNo 章NO
     */
    public void setChaptNo(String chaptNo) {
        this.chaptNo = chaptNo;
    }

    /**
     * 教科書ページを設定する
     *
     * @return textbPage 教科書ページ
     */
    public String getTextbPage() {
        return this.textbPage;
    }

    /**
     * 教科書ページを取得する
     *
     * @param textbPage 教科書ページ
     */
    public void setTextbPage(String textbPage) {
        this.textbPage = textbPage;
    }

    /**
     * 計画学習時期を設定する
     *
     * @return planLearnSeasn 計画学習時期
     */
    public Integer getPlanLearnSeasn() {
        return this.planLearnSeasn;
    }

    /**
     * 計画学習時期を取得する
     *
     * @param planLearnSeasn 計画学習時期
     */
    public void setPlanLearnSeasn(Integer planLearnSeasn) {
        this.planLearnSeasn = planLearnSeasn;
    }

    /**
     * 計画学習時間（分）を設定する
     *
     * @return planLearnTm 計画学習時間（分）
     */
    public Integer getPlanLearnTm() {
        return this.planLearnTm;
    }

    /**
     * 計画学習時間（分）を取得する
     *
     * @param planLearnTm 計画学習時間（分）
     */
    public void setPlanLearnTm(Integer planLearnTm) {
        this.planLearnTm = planLearnTm;
    }

    /**
     * 表示順番を設定する
     *
     * @return dispyOrder 表示順番
     */
    public Integer getDispyOrder() {
        return this.dispyOrder;
    }

    /**
     * 表示順番を取得する
     *
     * @param dispyOrder 表示順番
     */
    public void setDispyOrder(Integer dispyOrder) {
        this.dispyOrder = dispyOrder;
    }

    /**
     * 単元名を設定する
     *
     * @return unitNm 単元名
     */
    public String getUnitNm() {
        return this.unitNm;
    }

    /**
     * 単元名を取得する
     *
     * @param unitNm 単元名
     */
    public void setUnitNm(String unitNm) {
        this.unitNm = unitNm;
    }

    /**
     * 節名を設定する
     *
     * @return sectnNm 節名
     */
    public String getSectnNm() {
        return this.sectnNm;
    }

    /**
     * 節名を取得する
     *
     * @param sectnNm 節名
     */
    public void setSectnNm(String sectnNm) {
        this.sectnNm = sectnNm;
    }

    /**
     * 章名を設定する
     *
     * @return chaptNm 章名
     */
    public String getChaptNm() {
        return this.chaptNm;
    }

    /**
     * 章名を取得する
     *
     * @param chaptNm 章名
     */
    public void setChaptNm(String chaptNm) {
        this.chaptNm = chaptNm;
    }

    /**
     * 学年を設定する
     *
     * @return schy 学年
     */
    public String getSchy() {
        return this.schy;
    }

    /**
     * 学年を取得する
     *
     * @param schy 学年
     */
    public void setSchy(String schy) {
        this.schy = schy;
    }

    /**
     * 教科を設定する
     *
     * @return subjt 教科
     */
    public String getSubjt() {
        return this.subjt;
    }

    /**
     * 教科を取得する
     *
     * @param subjt 教科
     */
    public void setSubjt(String subjt) {
        this.subjt = subjt;
    }

    /**
     * 出版社を設定する
     *
     * @return publisher 出版社
     */
    public String getPublisher() {
        return this.publisher;
    }

    /**
     * 出版社を取得する
     *
     * @param publisher 出版社
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * 更新日時Strを設定する
     *
     * @return updateStr 更新日時Str
     */
    public String getUpdateStr() {
        return this.updateStr;
    }

    /**
     * 更新日時Strを取得する
     *
     * @param updateStr 更新日時Str
     */
    public void setUpdateStr(String updateStr) {
        this.updateStr = updateStr;
    }

    /**
     * 選択Flg(01)を設定する
     *
     * @return isCheck 選択Flg(01)
     */
    public Integer getIsCheck() {
        return this.isCheck;
    }

    /**
     * 選択Flg(01)を取得する
     *
     * @param isCheck 選択Flg(01)
     */
    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    /**
     * 更新日時を設定する
     *
     * @return updDatime 更新日時
     */
    public Timestamp getUpdDatime() {
        return this.updDatime;
    }

    /**
     * 更新日時を取得する
     *
     * @param updDatime 更新日時
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }

    /**
     * 教科書の更新日時を設定する
     *
     * @return textbUpdatime 教科書の更新日時
     */
    public Timestamp getTextbUpdatime() {
        return this.textbUpdatime;
    }

    /**
     * 教科書の更新日時を取得する
     *
     * @param textbUpdatime 教科書の更新日時
     */
    public void setTextbUpdatime(Timestamp textbUpdatime) {
        this.textbUpdatime = textbUpdatime;
    }

    /**
     * 教科書の更新日時Strを設定する
     *
     * @return textbUpdatimeStr 教科書の更新日時Str
     */
    public String getTextbUpdatimeStr() {
        return this.textbUpdatimeStr;
    }

    /**
     * 教科書の更新日時Strを取得する
     *
     * @param textbUpdatimeStr 教科書の更新日時Str
     */
    public void setTextbUpdatimeStr(String textbUpdatimeStr) {
        this.textbUpdatimeStr = textbUpdatimeStr;
    }

    /**
     * 計画学習時期Idを設定する
     *
     * @return planLearnSeasnId 計画学習時期Id
     */
    public Integer getPlanLearnSeasnId() {
        return this.planLearnSeasnId;
    }

    /**
     * 計画学習時期Idを取得する
     *
     * @param planLearnSeasnId 計画学習時期Id
     */
    public void setPlanLearnSeasnId(Integer planLearnSeasnId) {
        this.planLearnSeasnId = planLearnSeasnId;
    }
}
