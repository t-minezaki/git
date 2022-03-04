/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * F00054_生徒グループ関係設定修正画面
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/19 : xiong: 新規<br />
 * @version 1.0
 */
public class F00054Dto {

    /**
     * 変更後ユーザＩＤ（生徒ID）
     */
    private String afterUsrId;
    /**
     * 生徒姓名
     */
    private String stuName;
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * 学年
     */
    private String schy;
    /**
     * 姓名_姓
     */
    private String flnmNm;
    /**
     * 姓名_名
     */
    private String flnmLnm;
    /**
     * カナ姓名_姓
     */
    private String flnmKnNm;
    /**
     * カナ姓名_名
     */
    private String flnmKnLnm;

    /**
     * 生年月日
     */
    private String birthd;
    /**
     * コード値（学年）
     */
    private String codValue1;
    /**
     * コード値（性別）
     */
    private String codValue2;
    /**
     * グループID（リスト）
     */
    private Integer groupId;
    /**
     * グループ名（リスト）
     */
    private String groupName;

    /**
     * afterUsrIdを取得する
     *
     * @return afterUsrId afterUsrId
     */
    public String getAfterUsrId() {
        return afterUsrId;
    }

    /**
     * afterUsrIdを設定する
     *
     * @param afterUsrId afterUsrId
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * flnmNmを取得する
     *
     * @return flnmNm flnmNm
     */
    public String getFlnmNm() {
        return flnmNm;
    }

    /**
     * flnmNmを設定する
     *
     * @param flnmNm flnmNm
     */
    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    /**
     * flnmLnmを取得する
     *
     * @return flnmLnm flnmLnm
     */
    public String getFlnmLnm() {
        return flnmLnm;
    }

    /**
     * flnmLnmを設定する
     *
     * @param flnmLnm flnmLnm
     */
    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }

    /**
     * flnmKnNmを取得する
     *
     * @return flnmKnNm flnmKnNm
     */
    public String getFlnmKnNm() {
        return flnmKnNm;
    }

    /**
     * flnmKnNmを設定する
     *
     * @param flnmKnNm flnmKnNm
     */
    public void setFlnmKnNm(String flnmKnNm) {
        this.flnmKnNm = flnmKnNm;
    }

    /**
     * flnmKnLnmを取得する
     *
     * @return flnmKnLnm flnmKnLnm
     */
    public String getFlnmKnLnm() {
        return flnmKnLnm;
    }

    /**
     * flnmKnLnmを設定する
     *
     * @param flnmKnLnm flnmKnLnm
     */
    public void setFlnmKnLnm(String flnmKnLnm) {
        this.flnmKnLnm = flnmKnLnm;
    }

    /**
     * birthdを取得する
     *
     * @return birthd birthd
     */
    public String getBirthd() {
        return birthd;
    }

    /**
     * birthdを設定する
     *
     * @param birthd birthd
     */
    public void setBirthd(String birthd) {
        this.birthd = birthd;
    }

    /**
     * codValue1を取得する
     *
     * @return codValue1 codValue1
     */
    public String getCodValue1() {
        return codValue1;
    }

    /**
     * codValue1を設定する
     *
     * @param codValue1 codValue1
     */
    public void setCodValue1(String codValue1) {
        this.codValue1 = codValue1;
    }

    /**
     * codValue2を取得する
     *
     * @return codValue2 codValue2
     */
    public String getCodValue2() {
        return codValue2;
    }

    /**
     * codValue2を設定する
     *
     * @param codValue2 codValue2
     */
    public void setCodValue2(String codValue2) {
        this.codValue2 = codValue2;
    }


    /**
     * 生徒姓名を取得する
     *
     * @return stuName 生徒姓名
     */
    public String getStuName() {
        return this.stuName;
    }

    /**
     * 生徒姓名を設定する
     *
     * @param stuName 生徒姓名
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
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
     * グループID（リスト）を取得する
     *
     * @return groupId グループID（リスト）
     */
    public Integer getGroupId() {
        return this.groupId;
    }

    /**
     * グループID（リスト）を設定する
     *
     * @param groupId グループID（リスト）
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * グループ名（リスト）を取得する
     *
     * @return groupName グループ名（リスト）
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * グループ名（リスト）を設定する
     *
     * @param groupName グループ名（リスト）
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
