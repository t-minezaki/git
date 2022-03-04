/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dto;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/04/07 : lyh: 新規<br />
 * @version 6.1
 */
public class F00003Dto {

    /**
     * メールアドレス
     */
    private String mailad;

    /**
     * 姓名_姓
     */
    @NotNull
    private String flnmNm;

    /**
     * 姓名_名
     */
    @NotNull
    private String flnmLnm;

    /**
     * 姓名_カナ姓
     */
    @NotNull
    private String flnmKnNm;

    /**
     * 姓名_カナ名
     */
    @NotNull
    private String flnmKnLnm;

    /**
     * 電話番号
     */
    private String telnum;

    /**
     * 指導者管理コード
     */
    private String tchCd;



    /**
     * 郵便番号_主番
     */
    private String postcdMnum;

    /**
     * 郵便番号_枝番
     */
    private String postcdBnum;

    /**
     * 住所１
     */
    private String adr1;

    /**
     * 住所２
     */
    private String adr2;

    /**
     * 緊急電話番号
     */
    private String urgTelnum;

    /**
     * 続柄区分
     */
    private String reltnspDiv;


    /**
     * 学校名
     */
    private String sch;

    /**
     * 保護者ID
     */
    private String guardId;

    /**
     * 保護者１ID
     */
    private String guard1Id;

    /**
     * 保護者２ID
     */
    private String guard2Id;

    /**
     * 保護者３ID
     */
    private String guard3Id;

    /**
     * 保護者４ID
     */
    private String guard4Id;

    /**
     * 性別区分
     */
    private String gendrDiv;

    /**
     * 生年月日
     */
    private Date birthd;

    /**
     * 写真パス
     */
    private String photPath;

    /**
     * 学年区分
     */
    private String schyDiv;

    /**
     * QRコード
     */
    private String qrCod;

    /**
     * オリジナルCD
     */
    private String oriaCd;

    /**
     * 英語氏名
     */
    private String englishNm;

    /**
     * 通塾曜日区分
     */
    private String dayweekDiv;

    /**
     * メモ
     */
    private String memoCont;

    /**
     * 担当者氏名
     */
    private String resptyNm;

    /**
     * 習い事
     */
    private String studyCont;

    /**
     * 得意科目区分
     */
    private String goodSubjtDiv;

    /**
     * 不得意科目区分
     */
    private String nogoodSubjtDiv;

    /**
     * 希望職種
     */
    private String hopeJobNm;

    /**
     * 希望大学
     */
    private String hopeUnityNm;

    /**
     * 希望学部学科
     */
    private String hopeLearnSub;

    /**
     * 特記事項
     */
    private String specCont;

    /**
     * 会員コード
     */
    private String memberCd;

    public String getMailad() {
        return mailad;
    }

    public void setMailad(String mailad) {
        this.mailad = mailad;
    }

    public String getFlnmNm() {
        return flnmNm;
    }

    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    public String getFlnmLnm() {
        return flnmLnm;
    }

    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }

    public String getFlnmKnNm() {
        return flnmKnNm;
    }

    public void setFlnmKnNm(String flnmKnNm) {
        this.flnmKnNm = flnmKnNm;
    }

    public String getFlnmKnLnm() {
        return flnmKnLnm;
    }

    public void setFlnmKnLnm(String flnmKnLnm) {
        this.flnmKnLnm = flnmKnLnm;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    public String getTchCd() {
        return tchCd;
    }

    public void setTchCd(String tchCd) {
        this.tchCd = tchCd;
    }

    public String getPostcdMnum() {
        return postcdMnum;
    }

    public void setPostcdMnum(String postcdMnum) {
        this.postcdMnum = postcdMnum;
    }

    public String getPostcdBnum() {
        return postcdBnum;
    }

    public void setPostcdBnum(String postcdBnum) {
        this.postcdBnum = postcdBnum;
    }

    public String getAdr1() {
        return adr1;
    }

    public void setAdr1(String adr1) {
        this.adr1 = adr1;
    }

    public String getAdr2() {
        return adr2;
    }

    public void setAdr2(String adr2) {
        this.adr2 = adr2;
    }

    public String getUrgTelnum() {
        return urgTelnum;
    }

    public void setUrgTelnum(String urgTelnum) {
        this.urgTelnum = urgTelnum;
    }

    public String getReltnspDiv() {
        return reltnspDiv;
    }

    public void setReltnspDiv(String reltnspDiv) {
        this.reltnspDiv = reltnspDiv;
    }

    public String getSch() {
        return sch;
    }

    public void setSch(String sch) {
        this.sch = sch;
    }

    public String getGuardId() {
        return guardId;
    }

    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    public String getGuard1Id() {
        return guard1Id;
    }

    public void setGuard1Id(String guard1Id) {
        this.guard1Id = guard1Id;
    }

    public String getGuard2Id() {
        return guard2Id;
    }

    public void setGuard2Id(String guard2Id) {
        this.guard2Id = guard2Id;
    }

    public String getGuard3Id() {
        return guard3Id;
    }

    public void setGuard3Id(String guard3Id) {
        this.guard3Id = guard3Id;
    }

    public String getGuard4Id() {
        return guard4Id;
    }

    public void setGuard4Id(String guard4Id) {
        this.guard4Id = guard4Id;
    }

    public String getGendrDiv() {
        return gendrDiv;
    }

    public void setGendrDiv(String gendrDiv) {
        this.gendrDiv = gendrDiv;
    }

    public Date getBirthd() {
        return birthd;
    }

    public void setBirthd(Date birthd) {
        this.birthd = birthd;
    }

    public String getPhotPath() {
        return photPath;
    }

    public void setPhotPath(String photPath) {
        this.photPath = photPath;
    }

    public String getSchyDiv() {
        return schyDiv;
    }

    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    public String getQrCod() {
        return qrCod;
    }

    public void setQrCod(String qrCod) {
        this.qrCod = qrCod;
    }

    public String getOriaCd() {
        return oriaCd;
    }

    public void setOriaCd(String oriaCd) {
        this.oriaCd = oriaCd;
    }

    public String getEnglishNm() {
        return englishNm;
    }

    public void setEnglishNm(String englishNm) {
        this.englishNm = englishNm;
    }

    public String getDayweekDiv() {
        return dayweekDiv;
    }

    public void setDayweekDiv(String dayweekDiv) {
        this.dayweekDiv = dayweekDiv;
    }

    public String getMemoCont() {
        return memoCont;
    }

    public void setMemoCont(String memoCont) {
        this.memoCont = memoCont;
    }

    public String getResptyNm() {
        return resptyNm;
    }

    public void setResptyNm(String resptyNm) {
        this.resptyNm = resptyNm;
    }

    public String getStudyCont() {
        return studyCont;
    }

    public void setStudyCont(String studyCont) {
        this.studyCont = studyCont;
    }

    public String getGoodSubjtDiv() {
        return goodSubjtDiv;
    }

    public void setGoodSubjtDiv(String goodSubjtDiv) {
        this.goodSubjtDiv = goodSubjtDiv;
    }

    public String getNogoodSubjtDiv() {
        return nogoodSubjtDiv;
    }

    public void setNogoodSubjtDiv(String nogoodSubjtDiv) {
        this.nogoodSubjtDiv = nogoodSubjtDiv;
    }

    public String getHopeJobNm() {
        return hopeJobNm;
    }

    public void setHopeJobNm(String hopeJobNm) {
        this.hopeJobNm = hopeJobNm;
    }

    public String getHopeUnityNm() {
        return hopeUnityNm;
    }

    public void setHopeUnityNm(String hopeUnityNm) {
        this.hopeUnityNm = hopeUnityNm;
    }

    public String getHopeLearnSub() {
        return hopeLearnSub;
    }

    public void setHopeLearnSub(String hopeLearnSub) {
        this.hopeLearnSub = hopeLearnSub;
    }

    public String getSpecCont() {
        return specCont;
    }

    public void setSpecCont(String specCont) {
        this.specCont = specCont;
    }

    public String getMemberCd() {
        return memberCd;
    }

    public void setMemberCd(String memberCd) {
        this.memberCd = memberCd;
    }
}