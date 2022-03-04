/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstOrgEntity;

/**
 * <p>F04005 塾ニュース照会画面 Dto</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/14 : wen: 新規<br />
 * @version 1.0
 */
public class F04005Dto extends MstOrgEntity {
    /**
     * usrId
     */
    private String usrId;
    /**
     * 生徒ID
     */
    private String sId;
    /**
     *生徒氏名
     */
    private String stuName;
    /**
     * 保護者ID
     */
    private String gId;
    /**
     * 保護者姓名
     */
    private String guardName;
    /**
     * 生徒+保護者姓名
     */
    private String guardStuNm;

    /**
     * usrIdを取得する
     *
     * @return usrId usrId
     */
    public String getUsrId() {
        return this.usrId;
    }

    /**
     * usrIdを設定する
     *
     * @param usrId usrId
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     * 生徒IDを取得する
     *
     * @return sId 生徒ID
     */
    public String getSId() {
        return this.sId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param sId 生徒ID
     */
    public void setSId(String sId) {
        this.sId = sId;
    }

    /**
     * 生徒氏名を取得する
     *
     * @return stuName 生徒氏名
     */
    public String getStuName() {
        return this.stuName;
    }

    /**
     * 生徒氏名を設定する
     *
     * @param stuName 生徒氏名
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * 保護者IDを取得する
     *
     * @return gId 保護者ID
     */
    public String getGId() {
        return this.gId;
    }

    /**
     * 保護者IDを設定する
     *
     * @param gId 保護者ID
     */
    public void setGId(String gId) {
        this.gId = gId;
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
     * 生徒+保護者姓名を取得する
     *
     * @return guardStuNm 生徒+保護者姓名
     */
    public String getGuardStuNm() {
        return this.guardStuNm;
    }

    /**
     * 生徒+保護者姓名を設定する
     *
     * @param guardStuNm 生徒+保護者姓名
     */
    public void setGuardStuNm(String guardStuNm) {
        this.guardStuNm = guardStuNm;
    }
}
