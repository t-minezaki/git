/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dto;


import java.util.List;

/**
 * <p>配信先設定画面 Dto</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/06/18: yang: 新規<br />
 * @version 1.0
 */
public class F04004Dto {
    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * ID
     */
    private String ID;

    /**
     * 保護者生徒姓名
     */
    private String  guardStuNm;

    /**
     * 保護者変更後ユーザーID
     */
    private String  guardAfterId;

    /**
     * 保護者Id
     */
    private String  guardId;

    /**
     * 保護者姓名
     */
    private String  guardName;

    /**
     * 生徒変更後ユーザーID
     */
    private String  stuAfterId;

    /**
     * 生徒姓名
     */
    private String  stuName;

    /**
     * 組織ID
     */
    private String orgId;
    /**
     *学年区分
     */
    private String schy;
    /**
     * 組織IDList
     */
    private List<String> orgIdList;
    /**
     * 組織名
     */
    private String orgNm;

    /**
     *コード値
     */
    private String codeValue;
    /**
     * readFlg
     */
    private boolean readFlg;
    /**
     * 学年ソート
     */
    private Integer schySort;

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
     * IDを取得する
     *
     * @return ID ID
     */
    public String getID() {
        return this.ID;
    }

    /**
     * IDを設定する
     *
     * @param ID ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * 保護者生徒姓名を取得する
     *
     * @return guardStuNm 保護者生徒姓名
     */
    public String getGuardStuNm() {
        return this.guardStuNm;
    }

    /**
     * 保護者生徒姓名を設定する
     *
     * @param guardStuNm 保護者生徒姓名
     */
    public void setGuardStuNm(String guardStuNm) {
        this.guardStuNm = guardStuNm;
    }

    /**
     * 保護者変更後ユーザーIDを取得する
     *
     * @return guardAfterId 保護者変更後ユーザーID
     */
    public String getGuardAfterId() {
        return this.guardAfterId;
    }

    /**
     * 保護者変更後ユーザーIDを設定する
     *
     * @param guardAfterId 保護者変更後ユーザーID
     */
    public void setGuardAfterId(String guardAfterId) {
        this.guardAfterId = guardAfterId;
    }

    /**
     * 保護者Idを取得する
     *
     * @return guardId 保護者Id
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * 保護者Idを設定する
     *
     * @param guardId 保護者Id
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * 保護者姓名を取得する
     *
     * @return guardName 保護者姓名
     */
    public String getGuardName() {
        return this.guardName;
    }

    /**
     * 保護者姓名を設定する
     *
     * @param guardName 保護者姓名
     */
    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    /**
     * 生徒変更後ユーザーIDを取得する
     *
     * @return stuAfterId 生徒変更後ユーザーID
     */
    public String getStuAfterId() {
        return this.stuAfterId;
    }

    /**
     * 生徒変更後ユーザーIDを設定する
     *
     * @param stuAfterId 生徒変更後ユーザーID
     */
    public void setStuAfterId(String stuAfterId) {
        this.stuAfterId = stuAfterId;
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
     * 組織IDを取得する
     *
     * @return orgId 組織ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織IDを設定する
     *
     * @param orgId 組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgNm() {
        return orgNm;
    }

    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    /**
     * 組織IDListを取得する
     *
     * @return orgIdList 組織IDList
     */
    public List<String> getOrgIdList() {
        return this.orgIdList;
    }

    /**
     * 組織IDListを設定する
     *
     * @param orgIdList 組織IDList
     */
    public void setOrgIdList(List<String> orgIdList) {
        this.orgIdList = orgIdList;
    }

    /**
     * 学年区分を取得する
     *
     * @return schy 学年区分
     */
    public String getSchy() {
        return this.schy;
    }

    /**
     * 学年区分を設定する
     *
     * @param schy 学年区分
     */
    public void setSchy(String schy) {
        this.schy = schy;
    }

    /**
     * を取得する
     *
     * @return readFlg
     */
    public boolean isReadFlg() {
        return this.readFlg;
    }

    /**
     * を設定する
     *
     * @param readFlg
     */
    public void setReadFlg(boolean readFlg) {
        this.readFlg = readFlg;
    }

    /**
     * 学年ソートを取得する
     *
     * @return schySort 学年ソート
     */
    public Integer getSchySort() {
        return this.schySort;
    }

    /**
     * 学年ソートを設定する
     *
     * @param schySort 学年ソート
     */
    public void setSchySort(Integer schySort) {
        this.schySort = schySort;
    }
}
