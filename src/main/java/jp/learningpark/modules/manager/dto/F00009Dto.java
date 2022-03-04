/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jp.learningpark.modules.common.entity.MstOrgEntity;

import java.sql.Timestamp;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/01/18 : gong: 新規<br />
 * @version 1.0
 */
public class F00009Dto extends MstOrgEntity {
    /**
     * ユーザID
     */
    private String usrId;
    /**
     * 変更後ユーザーID
     */
    private String afterUsrId;
    /**
     * 特殊権限フラグ
     */
    private String specAuthFlg;
    /**
     * 組織ID
     */
    private String orgId;
    /**
     * 管理組織
     */
    private String mgrVal;

    /**
     * 上層組織名
     */
    private String upLevOrgNm;
    /**
     * 更新日時
     */
    @JsonFormat(pattern = "yyyyMMddHHmmssSSS")
    private Timestamp updDatime;

    /**
     * mgrValを取得する
     *
     * @return mgrVal mgrVal
     */
    public String getMgrVal() {
        return mgrVal;
    }

    /**
     * mgrValを設定する
     *
     * @param mgrVal mgrVal
     */
    public void setMgrVal(String mgrVal) {
        this.mgrVal = mgrVal;
    }

    /**
     * upLevOrgNmを取得する
     *
     * @return upLevOrgNm upLevOrgNm
     */
    public String getUpLevOrgNm() {
        return upLevOrgNm;
    }

    /**
     * upLevOrgNmを設定する
     *
     * @param upLevOrgNm upLevOrgNm
     */
    public void setUpLevOrgNm(String upLevOrgNm) {
        this.upLevOrgNm = upLevOrgNm;
    }

    /**
     * ユーザIDを取得する
     *
     * @return usrId ユーザID
     */
    public String getUsrId() {
        return this.usrId;
    }

    /**
     * ユーザIDを設定する
     *
     * @param usrId ユーザID
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     * 変更後ユーザーIDを取得する
     *
     * @return afterUsrId 変更後ユーザーID
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * 変更後ユーザーIDを設定する
     *
     * @param afterUsrId 変更後ユーザーID
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * 特殊権限フラグを取得する
     *
     * @return specAuthFlg 特殊権限フラグ
     */
    public String getSpecAuthFlg() {
        return this.specAuthFlg;
    }

    /**
     * 特殊権限フラグを設定する
     *
     * @param specAuthFlg 特殊権限フラグ
     */
    public void setSpecAuthFlg(String specAuthFlg) {
        this.specAuthFlg = specAuthFlg;
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
}
