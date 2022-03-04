/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dto;

import java.util.Date;

/**
 * <p>報告書詳細画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2019/12/09 : zpa: 新規<br />
 * @version 1.0
 */
public class F30413Dto {
    //組織ID
    private String orgId;
    //ユーザID
    private String userId;
    //指導報告書ID
    private Integer GrId;
    //対象年月日
    private Date Ymd;
    //生徒ID
    private String stuId;
    //出欠ステータス区分
    private String absStsDiv;
    //指導報告書配信コード
    private String guidReprDeliverCd;
    //指導内容
    private String guidCont;
    //指導内容
    private String hwkCont;
    //宿題区分
    private String hwkDiv;
    //前回宿題完成区分
    private String lastHwkDiv;
    //小テスト単元名
    private String testUnitNm;
    //小テスト点数
    private Integer testPoints;
    //ケア区分
    private String careDiv;
    //進捗ステータス区分
    private String schStsDiv;
    //授業様子区分
    private String lectShapeDiv;
    //使用テキスト
    private String useCont;
    //更新ユーザＩＤ
    private String updUsrId;
    //作成ユーザＩＤ
    private String cretUsrId;
    //連絡事項内容
    private String concItemCont;
    //上層組織ID
    private String upplevOrgId;
    //表示項目
    private String displayItems;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Integer getGrId() {
        return GrId;
    }

    public void setGrId(Integer grId) {
        GrId = grId;
    }

    public Date getYmd() {
        return Ymd;
    }

    public void setYmd(Date ymd) {
        Ymd = ymd;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getAbsStsDiv() {
        return absStsDiv;
    }

    public void setAbsStsDiv(String absStsDiv) {
        this.absStsDiv = absStsDiv;
    }

    public String getGuidCont() {
        return guidCont;
    }

    public void setGuidCont(String guidCont) {
        this.guidCont = guidCont;
    }

    public String getHwkCont() {
        return hwkCont;
    }

    public void setHwkCont(String hwkCont) {
        this.hwkCont = hwkCont;
    }

    public String getHwkDiv() {
        return hwkDiv;
    }

    public void setHwkDiv(String hwkDiv) {
        this.hwkDiv = hwkDiv;
    }

    public String getLastHwkDiv() {
        return lastHwkDiv;
    }

    public void setLastHwkDiv(String lastHwkDiv) {
        this.lastHwkDiv = lastHwkDiv;
    }

    public String getTestUnitNm() {
        return testUnitNm;
    }

    public void setTestUnitNm(String testUnitNm) {
        this.testUnitNm = testUnitNm;
    }

    public Integer getTestPoints() {
        return testPoints;
    }

    public void setTestPoints(Integer testPoints) {
        this.testPoints = testPoints;
    }

    public String getCareDiv() {
        return careDiv;
    }

    public void setCareDiv(String careDiv) {
        this.careDiv = careDiv;
    }

    public String getSchStsDiv() {
        return schStsDiv;
    }

    public void setSchStsDiv(String schStsDiv) {
        this.schStsDiv = schStsDiv;
    }

    public String getLectShapeDiv() {
        return lectShapeDiv;
    }

    public void setLectShapeDiv(String lectShapeDiv) {
        this.lectShapeDiv = lectShapeDiv;
    }

    public String getConcItemCont() {
        return concItemCont;
    }

    public void setConcItemCont(String concItemCont) {
        this.concItemCont = concItemCont;
    }

    public String getGuidReprDeliverCd() {
        return guidReprDeliverCd;
    }

    public void setGuidReprDeliverCd(String guidReprDeliverCd) {
        this.guidReprDeliverCd = guidReprDeliverCd;
    }

    public String getUseCont() {
        return useCont;
    }

    public void setUseCont(String useCont) {
        this.useCont = useCont;
    }

    public String getUpdUsrId() {
        return updUsrId;
    }

    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }

    public String getCretUsrId() {
        return cretUsrId;
    }

    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }

    public String getUpplevOrgId() {
        return upplevOrgId;
    }

    public void setUpplevOrgId(String upplevOrgId) {
        this.upplevOrgId = upplevOrgId;
    }

    public String getDisplayItems() {
        return displayItems;
    }

    public void setDisplayItems(String displayItems) {
        this.displayItems = displayItems;
    }
}