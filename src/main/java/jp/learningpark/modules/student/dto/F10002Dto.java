/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

import java.util.Date;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/11 : gong: 新規<br />
 * @version 1.0
 */
public class F10002Dto {
    /**
     * 生徒基本マスタのID
     */
    private Integer id;

    /**
     * ユーザ基本マスタのid
     */
    private Integer usrId;

    /**
     * 写真
     */
    private String photPath;

    /**
     * 生徒姓
     */
    private String stuFnm;

    /**
     * 生徒名
     */
    private String stuLnm;

    /**
     * 生徒姓名
     */
    private String stuNm;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * 生年月日
     */
    private Date birthd;

    /**
     * 性別区分
     */
    private String gendrDiv;

    /**
     * 性別(コード値)
     */
    private String gendrVal;

    /**
     * 学校学年
     */
    private String schy;

    /**
     * 学校名
     */
    private String schNm;

    /**
     * 学校コード
     */
    private String schCd;

    /**
     * 学校学年
     */
    private String schyVal;

    /**
     * 学校学年(コード値)
     */
    private String schyDiv;

    /**
     * 塾名
     */
    private String orgNm;

    /**
     * ユーザログインPW
     */
    private String usrPassword;

    /**
     * 郵便番号_主番
     */
    private String postcdMnum;

    /**
     * 郵便番号_枝番
     */
    private String postcdBnum;

    /**
     * 住所
     */
    private String adr;

    /**
     * 生年月日String
     */
    private String birthdayString;

    /**
     * 変更後ユーザーID
     */
    private String afterUsrId;

    /**
     * 生徒基本マスタのIDを設定する
     *
     * @return id 生徒基本マスタのID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 生徒基本マスタのIDを取得する
     *
     * @param id 生徒基本マスタのID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * ユーザ基本マスタのidを設定する
     *
     * @return usrId ユーザ基本マスタのid
     */
    public Integer getUsrId() {
        return this.usrId;
    }

    /**
     * ユーザ基本マスタのidを取得する
     *
     * @param usrId ユーザ基本マスタのid
     */
    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    /**
     * 写真を設定する
     *
     * @return photPath 写真
     */
    public String getPhotPath() {
        return this.photPath;
    }

    /**
     * 写真を取得する
     *
     * @param photPath 写真
     */
    public void setPhotPath(String photPath) {
        this.photPath = photPath;
    }

    /**
     * 生徒姓を設定する
     *
     * @return stuFnm 生徒姓
     */
    public String getStuFnm() {
        return this.stuFnm;
    }

    /**
     * 生徒姓を取得する
     *
     * @param stuFnm 生徒姓
     */
    public void setStuFnm(String stuFnm) {
        this.stuFnm = stuFnm;
    }

    /**
     * 生徒名を設定する
     *
     * @return stuLnm 生徒名
     */
    public String getStuLnm() {
        return this.stuLnm;
    }

    /**
     * 生徒名を取得する
     *
     * @param stuLnm 生徒名
     */
    public void setStuLnm(String stuLnm) {
        this.stuLnm = stuLnm;
    }

    /**
     * 生徒姓名を設定する
     *
     * @return stuNm 生徒姓名
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 生徒姓名を取得する
     *
     * @param stuNm 生徒姓名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 生徒IDを設定する
     *
     * @return stuId 生徒ID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒IDを取得する
     *
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 生年月日を設定する
     *
     * @return birthd 生年月日
     */
    public Date getBirthd() {
        return this.birthd;
    }

    /**
     * 生年月日を取得する
     *
     * @param birthd 生年月日
     */
    public void setBirthd(Date birthd) {
        this.birthd = birthd;
    }

    /**
     * 性別区分を設定する
     *
     * @return gendrDiv 性別区分
     */
    public String getGendrDiv() {
        return this.gendrDiv;
    }

    /**
     * 性別区分を取得する
     *
     * @param gendrDiv 性別区分
     */
    public void setGendrDiv(String gendrDiv) {
        this.gendrDiv = gendrDiv;
    }

    /**
     * 性別(コード値)を設定する
     *
     * @return gendrVal 性別(コード値)
     */
    public String getGendrVal() {
        return this.gendrVal;
    }

    /**
     * 性別(コード値)を取得する
     *
     * @param gendrVal 性別(コード値)
     */
    public void setGendrVal(String gendrVal) {
        this.gendrVal = gendrVal;
    }

    /**
     * 学校学年を設定する
     *
     * @return schy 学校学年
     */
    public String getSchy() {
        return this.schy;
    }

    /**
     * 学校学年を取得する
     *
     * @param schy 学校学年
     */
    public void setSchy(String schy) {
        this.schy = schy;
    }

    /**
     * 学校名を設定する
     *
     * @return schNm 学校名
     */
    public String getSchNm() {
        return this.schNm;
    }

    /**
     * 学校名を取得する
     *
     * @param schNm 学校名
     */
    public void setSchNm(String schNm) {
        this.schNm = schNm;
    }

    /**
     * 学校コードを設定する
     *
     * @return schCd 学校コード
     */
    public String getSchCd() {
        return this.schCd;
    }

    /**
     * 学校コードを取得する
     *
     * @param schCd 学校コード
     */
    public void setSchCd(String schCd) {
        this.schCd = schCd;
    }

    /**
     * 学校学年を設定する
     *
     * @return schyVal 学校学年
     */
    public String getSchyVal() {
        return this.schyVal;
    }

    /**
     * 学校学年を取得する
     *
     * @param schyVal 学校学年
     */
    public void setSchyVal(String schyVal) {
        this.schyVal = schyVal;
    }

    /**
     * 学校学年(コード値)を設定する
     *
     * @return schyDiv 学校学年(コード値)
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * 学校学年(コード値)を取得する
     *
     * @param schyDiv 学校学年(コード値)
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * 塾名を設定する
     *
     * @return orgNm 塾名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 塾名を取得する
     *
     * @param orgNm 塾名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * ユーザログインPWを設定する
     *
     * @return usrPassword ユーザログインPW
     */
    public String getUsrPassword() {
        return this.usrPassword;
    }

    /**
     * ユーザログインPWを取得する
     *
     * @param usrPassword ユーザログインPW
     */
    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    /**
     * 郵便番号_主番を設定する
     *
     * @return postcdMnum 郵便番号_主番
     */
    public String getPostcdMnum() {
        return this.postcdMnum;
    }

    /**
     * 郵便番号_主番を取得する
     *
     * @param postcdMnum 郵便番号_主番
     */
    public void setPostcdMnum(String postcdMnum) {
        this.postcdMnum = postcdMnum;
    }

    /**
     * 郵便番号_枝番を設定する
     *
     * @return postcdBnum 郵便番号_枝番
     */
    public String getPostcdBnum() {
        return this.postcdBnum;
    }

    /**
     * 郵便番号_枝番を取得する
     *
     * @param postcdBnum 郵便番号_枝番
     */
    public void setPostcdBnum(String postcdBnum) {
        this.postcdBnum = postcdBnum;
    }

    /**
     * 住所を設定する
     *
     * @return adr 住所
     */
    public String getAdr() {
        return this.adr;
    }

    /**
     * 住所を取得する
     *
     * @param adr 住所
     */
    public void setAdr(String adr) {
        this.adr = adr;
    }

    /**
     * 生年月日Stringを設定する
     *
     * @return birthdayString 生年月日String
     */
    public String getBirthdayString() {
        return this.birthdayString;
    }

    /**
     * 生年月日Stringを取得する
     *
     * @param birthdayString 生年月日String
     */
    public void setBirthdayString(String birthdayString) {
        this.birthdayString = birthdayString;
    }

    /**
     * 変更後ユーザーIDを設定する
     *
     * @return afterUsrId 変更後ユーザーID
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * 変更後ユーザーIDを取得する
     *
     * @param afterUsrId 変更後ユーザーID
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }
}
