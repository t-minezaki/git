/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.util.Date;

/**
 * F00002 F02002_単元情報検索一覧画面
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/20 : xiong: 新規<br />
 * @version 1.0
 */
public class F02002UnitDto {
    /**
     * 単元マスタ．ID
     */
    Integer id;
    /**
     * 組織ＩＤ
     */
    String orgId;
    /**
     * 組織名
     */
    String orgNm;
    /**
     * コードマスタ（C1）．コード値（学年）
     */
    String codValue1;
    /**
     * コードマスタ（C2）．コード値（教科）
     */
    String codValue2;
    /**
     * 単元マスタ．単元管理コード
     */
    String unitMstCd;
    /**
     * 単元マスタ．単元名
     */
    String unitNm;
    /**
     * 単元マスタ．節名
     */
    String sectnNm;
    /**
     * 単元マスタ．章名
     */
    String chaptNm;
    /**
     * 単元マスタ．更新日時
     */
    Date updDatime;
    /**
     * 単元マスタ．更新日時 String
     */
    String updDatimeFormat;
    /**
     * 組織flg
     */
    Integer flg;

    /**
     * idを取得する
     *
     * @return id id
     */
    public Integer getId() {
        return id;
    }

    /**
     * idを設定する
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * orgIdを取得する
     *
     * @return orgId orgId
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * orgIdを設定する
     *
     * @param orgId orgId
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * orgNmを取得する
     *
     * @return orgNm orgNm
     */
    public String getOrgNm() {
        return orgNm;
    }

    /**
     * orgNmを設定する
     *
     * @param orgNm orgNm
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
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
     * unitMstCdを取得する
     *
     * @return unitMstCd unitMstCd
     */
    public String getUnitMstCd() {
        return unitMstCd;
    }

    /**
     * unitMstCdを設定する
     *
     * @param unitMstCd unitMstCd
     */
    public void setUnitMstCd(String unitMstCd) {
        this.unitMstCd = unitMstCd;
    }

    /**
     * unitNmを取得する
     *
     * @return unitNm unitNm
     */
    public String getUnitNm() {
        return unitNm;
    }

    /**
     * unitNmを設定する
     *
     * @param unitNm unitNm
     */
    public void setUnitNm(String unitNm) {
        this.unitNm = unitNm;
    }

    /**
     * sectnNmを取得する
     *
     * @return sectnNm sectnNm
     */
    public String getSectnNm() {
        return sectnNm;
    }

    /**
     * sectnNmを設定する
     *
     * @param sectnNm sectnNm
     */
    public void setSectnNm(String sectnNm) {
        this.sectnNm = sectnNm;
    }

    /**
     * chaptNmを取得する
     *
     * @return chaptNm chaptNm
     */
    public String getChaptNm() {
        return chaptNm;
    }

    /**
     * chaptNmを設定する
     *
     * @param chaptNm chaptNm
     */
    public void setChaptNm(String chaptNm) {
        this.chaptNm = chaptNm;
    }

    /**
     * updDatimeを取得する
     *
     * @return updDatime updDatime
     */
    public Date getUpdDatime() {
        return updDatime;
    }

    /**
     * updDatimeを設定する
     *
     * @param updDatime updDatime
     */
    public void setUpdDatime(Date updDatime) {
        this.updDatime = updDatime;
    }

    /**
     * updDatimeFormatを取得する
     *
     * @return updDatimeFormat updDatimeFormat
     */
    public String getUpdDatimeFormat() {
        return updDatimeFormat;
    }

    /**
     * updDatimeFormatを設定する
     *
     * @param updDatimeFormat updDatimeFormat
     */
    public void setUpdDatimeFormat(String updDatimeFormat) {
        this.updDatimeFormat = updDatimeFormat;
    }

    /**
     * flgを取得する
     *
     * @return flg flg
     */
    public Integer getFlg() {
        return flg;
    }

    /**
     * flgを設定する
     *
     * @param flg flg
     */
    public void setFlg(Integer flg) {
        this.flg = flg;
    }
}
