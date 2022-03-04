/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p>F00061_メンター生徒関係検索一覧 Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/3/18 : gong: 新規<br />
 * @version 1.0
 */
public class F00061Dto {
    /**
     * ID
     */
    private Integer id;

    /**
     * 組織ＩＤ
     */
    private String orgId;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * メンターID
     */
    private String mentorId;

    /**
     * 生徒姓名_姓 || 姓名_名
     */
    private String stuNm;

    /**
     * メンター姓名_姓 || 姓名_名
     */
    private String mentorNm;

    /**
     * 性別区分
     */
    private String gendrDiv;

    /**
     * 性別
     */
    private String gendr;

    /**
     * 生年月日
     */
    private Date birthd;

    /**
     * 生年月日str
     */
    private String birthdStr;

    /**
     * 学年
     */
    private String schy;

    /**
     * 学年区分
     */
    private String schyDiv;
    /**
     * 更新日時
     */
    private Timestamp updDatime;

    /**
     * 更新日時Str
     */
    private String updDatimeStr;
    /**
     * 組織マスタ．組織名
     */
    private String orgNm;
    /**
     * 組織ID Stringlist
     */
    private String orgIdStringList;
    /**
     * 組織IDlist
     */
    private List<String> orgIdList;


    /**
     * IDを設定する
     *
     * @return id ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * IDを取得する
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
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
     * メンターIDを設定する
     *
     * @return mentorId メンターID
     */
    public String getMentorId() {
        return this.mentorId;
    }

    /**
     * メンターIDを取得する
     *
     * @param mentorId メンターID
     */
    public void setMentorId(String mentorId) {
        this.mentorId = mentorId;
    }

    /**
     * 生徒姓名_姓 || 姓名_名を設定する
     *
     * @return stuNm 生徒姓名_姓 || 姓名_名
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 生徒姓名_姓 || 姓名_名を取得する
     *
     * @param stuNm 生徒姓名_姓 || 姓名_名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * メンター姓名_姓 || 姓名_名を設定する
     *
     * @return mentorNm メンター姓名_姓 || 姓名_名
     */
    public String getMentorNm() {
        return this.mentorNm;
    }

    /**
     * メンター姓名_姓 || 姓名_名を取得する
     *
     * @param mentorNm メンター姓名_姓 || 姓名_名
     */
    public void setMentorNm(String mentorNm) {
        this.mentorNm = mentorNm;
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
     * 性別を設定する
     *
     * @return gendr 性別
     */
    public String getGendr() {
        return this.gendr;
    }

    /**
     * 性別を取得する
     *
     * @param gendr 性別
     */
    public void setGendr(String gendr) {
        this.gendr = gendr;
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
     * 組織ＩＤを設定する
     *
     * @return orgId 組織ＩＤ
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織ＩＤを取得する
     *
     * @param orgId 組織ＩＤ
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 生年月日strを設定する
     *
     * @return birthdStr 生年月日str
     */
    public String getBirthdStr() {
        return this.birthdStr;
    }

    /**
     * 生年月日strを取得する
     *
     * @param birthdStr 生年月日str
     */
    public void setBirthdStr(String birthdStr) {
        this.birthdStr = birthdStr;
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
     * 更新日時Strを設定する
     *
     * @return updDatimeStr 更新日時Str
     */
    public String getUpdDatimeStr() {
        return this.updDatimeStr;
    }

    /**
     * 更新日時Strを取得する
     *
     * @param updDatimeStr 更新日時Str
     */
    public void setUpdDatimeStr(String updDatimeStr) {
        this.updDatimeStr = updDatimeStr;
    }

    /**
     * 組織マスタ．組織名を取得する
     *
     * @return orgNm 組織マスタ．組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織マスタ．組織名を設定する
     *
     * @param orgNm 組織マスタ．組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * 組織ID Stringlistを取得する
     *
     * @return orgIdStringList 組織ID Stringlist
     */
    public String getOrgIdStringList() {
        return this.orgIdStringList;
    }

    /**
     * 組織ID Stringlistを設定する
     *
     * @param orgIdStringList 組織ID Stringlist
     */
    public void setOrgIdStringList(String orgIdStringList) {
        this.orgIdStringList = orgIdStringList;
    }

    /**
     * 組織IDlistを取得する
     *
     * @return orgIdList 組織IDlist
     */
    public List<String> getOrgIdList() {
        return this.orgIdList;
    }

    /**
     * 組織IDlistを設定する
     *
     * @param orgIdList 組織IDlist
     */
    public void setOrgIdList(List<String> orgIdList) {
        this.orgIdList = orgIdList;
    }
}
