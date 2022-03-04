/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.util.Date;

/**
 * <p>既存利用者ログインID同報設定画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/08/03 : wq: 新規<br />
 * @version 1.0
 */
public class F09023Dto {

    /**
     * ログインID
     */
    private String loginId;

    /**
     * 組織共用キー
     */
    private String commKey;

    /**
     * ロール区分
     */
    private String roleDiv;

    /**
     * ユーザーID
     */
    private String usrId;

    /**
     * 組織ID
     */
    private String orgId;

    private String brandCd;

    /**
     * 学研IDプライマリキー
     */
    private String gidpk;

    /**
     * GIDフラグ
     */
    private String gidFlg;

    /**
     * 他システム区分
     */
    private String systemKbn;

    /**
     * メールアドレス
     */
    private String mailad;

    /**
     * 姓名_姓
     */
    private String flnmNm;

    /**
     * 姓名_名
     */
    private String flnmLnm;

    /**
     * 姓名_カナ姓
     */
    private String flnmKnNm;

    /**
     * 姓名_カナ名
     */
    private String flnmKnLnm;

    /**
     * 電話番号
     */
    private String telNum;

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
    private String addr1;

    /**
     * 住所２
     */
    private String addr2;

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
     * ＱＲコード
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

    /**
     * 子供の続柄
     */
    private String reltnspDiv;

    /**
     * 緊急電話番号
     */
    private String urgTelnum;

    /**
     * GakkenID規約フラグ
     */
    private String gidRuleFlg;

    /**
     * マナミル規約フラグ
     */
    private String manaRuleFlg;

    /**
     * 個人情報保護規約フラグ
     */
    private String perlInfoRuleFlg;

    public String getBrandCd() {
        return brandCd;
    }

    public void setBrandCd(String brandCd) {
        this.brandCd = brandCd;
    }

    /**
     * ロール区分を取得する
     *
     * @return roleDiv ロール区分
     */
    public String getRoleDiv() {
        return this.roleDiv;
    }

    /**
     * ロール区分を設定する
     *
     * @param roleDiv ロール区分
     */
    public void setRoleDiv(String roleDiv) {
        this.roleDiv = roleDiv;
    }

    /**
     * ユーザーIDを取得する
     *
     * @return usrId ユーザーID
     */
    public String getUsrId() {
        return this.usrId;
    }

    /**
     * ユーザーIDを設定する
     *
     * @param usrId ユーザーID
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
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
     * 学研IDプライマリキーを取得する
     *
     * @return gidpk 学研IDプライマリキー
     */
    public String getGidpk() {
        return this.gidpk;
    }

    /**
     * 学研IDプライマリキーを設定する
     *
     * @param gidpk 学研IDプライマリキー
     */
    public void setGidpk(String gidpk) {
        this.gidpk = gidpk;
    }

    /**
     * GIDフラグを取得する
     *
     * @return gidFlg GIDフラグ
     */
    public String getGidFlg() {
        return this.gidFlg;
    }

    /**
     * GIDフラグを設定する
     *
     * @param gidFlg GIDフラグ
     */
    public void setGidFlg(String gidFlg) {
        this.gidFlg = gidFlg;
    }

    /**
     * 他システム区分を取得する
     *
     * @return systemKbn 他システム区分
     */
    public String getSystemKbn() {
        return this.systemKbn;
    }

    /**
     * 他システム区分を設定する
     *
     * @param systemKbn 他システム区分
     */
    public void setSystemKbn(String systemKbn) {
        this.systemKbn = systemKbn;
    }

    /**
     * メールアドレスを取得する
     *
     * @return mailad メールアドレス
     */
    public String getMailad() {
        return this.mailad;
    }

    /**
     * メールアドレスを設定する
     *
     * @param mailad メールアドレス
     */
    public void setMailad(String mailad) {
        this.mailad = mailad;
    }

    /**
     * 姓名_姓を取得する
     *
     * @return flnmNm 姓名_姓
     */
    public String getFlnmNm() {
        return this.flnmNm;
    }

    /**
     * 姓名_姓を設定する
     *
     * @param flnmNm 姓名_姓
     */
    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    /**
     * 姓名_名を取得する
     *
     * @return flnmLnm 姓名_名
     */
    public String getFlnmLnm() {
        return this.flnmLnm;
    }

    /**
     * 姓名_名を設定する
     *
     * @param flnmLnm 姓名_名
     */
    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }

    /**
     * 姓名_カナ姓を取得する
     *
     * @return flnmKnNm 姓名_カナ姓
     */
    public String getFlnmKnNm() {
        return this.flnmKnNm;
    }

    /**
     * 姓名_カナ姓を設定する
     *
     * @param flnmKnNm 姓名_カナ姓
     */
    public void setFlnmKnNm(String flnmKnNm) {
        this.flnmKnNm = flnmKnNm;
    }

    /**
     * 姓名_カナ名を取得する
     *
     * @return flnmKnLnm 姓名_カナ名
     */
    public String getFlnmKnLnm() {
        return this.flnmKnLnm;
    }

    /**
     * 姓名_カナ名を設定する
     *
     * @param flnmKnLnm 姓名_カナ名
     */
    public void setFlnmKnLnm(String flnmKnLnm) {
        this.flnmKnLnm = flnmKnLnm;
    }

    /**
     * 電話番号を取得する
     *
     * @return telNum 電話番号
     */
    public String getTelNum() {
        return this.telNum;
    }

    /**
     * 電話番号を設定する
     *
     * @param telNum 電話番号
     */
    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    /**
     * 指導者管理コードを取得する
     *
     * @return tchCd 指導者管理コード
     */
    public String getTchCd() {
        return this.tchCd;
    }

    /**
     * 指導者管理コードを設定する
     *
     * @param tchCd 指導者管理コード
     */
    public void setTchCd(String tchCd) {
        this.tchCd = tchCd;
    }

    /**
     * 郵便番号_主番を取得する
     *
     * @return postcdMnum 郵便番号_主番
     */
    public String getPostcdMnum() {
        return this.postcdMnum;
    }

    /**
     * 郵便番号_主番を設定する
     *
     * @param postcdMnum 郵便番号_主番
     */
    public void setPostcdMnum(String postcdMnum) {
        this.postcdMnum = postcdMnum;
    }

    /**
     * 郵便番号_枝番を取得する
     *
     * @return postcdBnum 郵便番号_枝番
     */
    public String getPostcdBnum() {
        return this.postcdBnum;
    }

    /**
     * 郵便番号_枝番を設定する
     *
     * @param postcdBnum 郵便番号_枝番
     */
    public void setPostcdBnum(String postcdBnum) {
        this.postcdBnum = postcdBnum;
    }

    /**
     * 住所１を取得する
     *
     * @return addr1 住所１
     */
    public String getAddr1() {
        return this.addr1;
    }

    /**
     * 住所１を設定する
     *
     * @param addr1 住所１
     */
    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    /**
     * 住所２を取得する
     *
     * @return addr2 住所２
     */
    public String getAddr2() {
        return this.addr2;
    }

    /**
     * 住所２を設定する
     *
     * @param addr2 住所２
     */
    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    /**
     * 学校名を取得する
     *
     * @return sch 学校名
     */
    public String getSch() {
        return this.sch;
    }

    /**
     * 学校名を設定する
     *
     * @param sch 学校名
     */
    public void setSch(String sch) {
        this.sch = sch;
    }

    /**
     * 保護者IDを取得する
     *
     * @return guardId 保護者ID
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * 保護者IDを設定する
     *
     * @param guardId 保護者ID
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * 保護者１IDを取得する
     *
     * @return guard1Id 保護者１ID
     */
    public String getGuard1Id() {
        return this.guard1Id;
    }

    /**
     * 保護者１IDを設定する
     *
     * @param guard1Id 保護者１ID
     */
    public void setGuard1Id(String guard1Id) {
        this.guard1Id = guard1Id;
    }

    /**
     * 保護者２IDを取得する
     *
     * @return guard2Id 保護者２ID
     */
    public String getGuard2Id() {
        return this.guard2Id;
    }

    /**
     * 保護者２IDを設定する
     *
     * @param guard2Id 保護者２ID
     */
    public void setGuard2Id(String guard2Id) {
        this.guard2Id = guard2Id;
    }

    /**
     * 保護者３IDを取得する
     *
     * @return guard3Id 保護者３ID
     */
    public String getGuard3Id() {
        return this.guard3Id;
    }

    /**
     * 保護者３IDを設定する
     *
     * @param guard3Id 保護者３ID
     */
    public void setGuard3Id(String guard3Id) {
        this.guard3Id = guard3Id;
    }

    /**
     * 保護者４IDを取得する
     *
     * @return guard4Id 保護者４ID
     */
    public String getGuard4Id() {
        return this.guard4Id;
    }

    /**
     * 保護者４IDを設定する
     *
     * @param guard4Id 保護者４ID
     */
    public void setGuard4Id(String guard4Id) {
        this.guard4Id = guard4Id;
    }

    /**
     * 性別区分を取得する
     *
     * @return gendrDiv 性別区分
     */
    public String getGendrDiv() {
        return this.gendrDiv;
    }

    /**
     * 性別区分を設定する
     *
     * @param gendrDiv 性別区分
     */
    public void setGendrDiv(String gendrDiv) {
        this.gendrDiv = gendrDiv;
    }

    /**
     * 生年月日を取得する
     *
     * @return birthd 生年月日
     */
    public Date getBirthd() {
        return this.birthd;
    }

    /**
     * 生年月日を設定する
     *
     * @param birthd 生年月日
     */
    public void setBirthd(Date birthd) {
        this.birthd = birthd;
    }

    /**
     * 写真パスを取得する
     *
     * @return photPath 写真パス
     */
    public String getPhotPath() {
        return this.photPath;
    }

    /**
     * 写真パスを設定する
     *
     * @param photPath 写真パス
     */
    public void setPhotPath(String photPath) {
        this.photPath = photPath;
    }

    /**
     * 学年区分を取得する
     *
     * @return schyDiv 学年区分
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * 学年区分を設定する
     *
     * @param schyDiv 学年区分
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * ＱＲコードを取得する
     *
     * @return qrCod ＱＲコード
     */
    public String getQrCod() {
        return this.qrCod;
    }

    /**
     * ＱＲコードを設定する
     *
     * @param qrCod ＱＲコード
     */
    public void setQrCod(String qrCod) {
        this.qrCod = qrCod;
    }

    /**
     * オリジナルCDを取得する
     *
     * @return oriaCd オリジナルCD
     */
    public String getOriaCd() {
        return this.oriaCd;
    }

    /**
     * オリジナルCDを設定する
     *
     * @param oriaCd オリジナルCD
     */
    public void setOriaCd(String oriaCd) {
        this.oriaCd = oriaCd;
    }

    /**
     * 英語氏名を取得する
     *
     * @return englishNm 英語氏名
     */
    public String getEnglishNm() {
        return this.englishNm;
    }

    /**
     * 英語氏名を設定する
     *
     * @param englishNm 英語氏名
     */
    public void setEnglishNm(String englishNm) {
        this.englishNm = englishNm;
    }

    /**
     * 通塾曜日区分を取得する
     *
     * @return dayweekDiv 通塾曜日区分
     */
    public String getDayweekDiv() {
        return this.dayweekDiv;
    }

    /**
     * 通塾曜日区分を設定する
     *
     * @param dayweekDiv 通塾曜日区分
     */
    public void setDayweekDiv(String dayweekDiv) {
        this.dayweekDiv = dayweekDiv;
    }

    /**
     * メモを取得する
     *
     * @return memoCont メモ
     */
    public String getMemoCont() {
        return this.memoCont;
    }

    /**
     * メモを設定する
     *
     * @param memoCont メモ
     */
    public void setMemoCont(String memoCont) {
        this.memoCont = memoCont;
    }

    /**
     * 担当者氏名を取得する
     *
     * @return resptyNm 担当者氏名
     */
    public String getResptyNm() {
        return this.resptyNm;
    }

    /**
     * 担当者氏名を設定する
     *
     * @param resptyNm 担当者氏名
     */
    public void setResptyNm(String resptyNm) {
        this.resptyNm = resptyNm;
    }

    /**
     * 習い事を取得する
     *
     * @return studyCont 習い事
     */
    public String getStudyCont() {
        return this.studyCont;
    }

    /**
     * 習い事を設定する
     *
     * @param studyCont 習い事
     */
    public void setStudyCont(String studyCont) {
        this.studyCont = studyCont;
    }

    /**
     * 得意科目区分を取得する
     *
     * @return goodSubjtDiv 得意科目区分
     */
    public String getGoodSubjtDiv() {
        return this.goodSubjtDiv;
    }

    /**
     * 得意科目区分を設定する
     *
     * @param goodSubjtDiv 得意科目区分
     */
    public void setGoodSubjtDiv(String goodSubjtDiv) {
        this.goodSubjtDiv = goodSubjtDiv;
    }

    /**
     * 不得意科目区分を取得する
     *
     * @return nogoodSubjtDiv 不得意科目区分
     */
    public String getNogoodSubjtDiv() {
        return this.nogoodSubjtDiv;
    }

    /**
     * 不得意科目区分を設定する
     *
     * @param nogoodSubjtDiv 不得意科目区分
     */
    public void setNogoodSubjtDiv(String nogoodSubjtDiv) {
        this.nogoodSubjtDiv = nogoodSubjtDiv;
    }

    /**
     * 希望職種を取得する
     *
     * @return hopeJobNm 希望職種
     */
    public String getHopeJobNm() {
        return this.hopeJobNm;
    }

    /**
     * 希望職種を設定する
     *
     * @param hopeJobNm 希望職種
     */
    public void setHopeJobNm(String hopeJobNm) {
        this.hopeJobNm = hopeJobNm;
    }

    /**
     * 希望大学を取得する
     *
     * @return hopeUnityNm 希望大学
     */
    public String getHopeUnityNm() {
        return this.hopeUnityNm;
    }

    /**
     * 希望大学を設定する
     *
     * @param hopeUnityNm 希望大学
     */
    public void setHopeUnityNm(String hopeUnityNm) {
        this.hopeUnityNm = hopeUnityNm;
    }

    /**
     * 希望学部学科を取得する
     *
     * @return hopeLearnSub 希望学部学科
     */
    public String getHopeLearnSub() {
        return this.hopeLearnSub;
    }

    /**
     * 希望学部学科を設定する
     *
     * @param hopeLearnSub 希望学部学科
     */
    public void setHopeLearnSub(String hopeLearnSub) {
        this.hopeLearnSub = hopeLearnSub;
    }

    /**
     * 特記事項を取得する
     *
     * @return specCont 特記事項
     */
    public String getSpecCont() {
        return this.specCont;
    }

    /**
     * 特記事項を設定する
     *
     * @param specCont 特記事項
     */
    public void setSpecCont(String specCont) {
        this.specCont = specCont;
    }

    /**
     * ログインIDを取得する
     *
     * @return loginId ログインID
     */
    public String getLoginId() {
        return this.loginId;
    }

    /**
     * ログインIDを設定する
     *
     * @param loginId ログインID
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * 組織共用キーを取得する
     *
     * @return commKey 組織共用キー
     */
    public String getCommKey() {
        return this.commKey;
    }

    /**
     * 組織共用キーを設定する
     *
     * @param commKey 組織共用キー
     */
    public void setCommKey(String commKey) {
        this.commKey = commKey;
    }

    /**
     * 会員コードを取得する
     *
     * @return memberCd 会員コード
     */
    public String getMemberCd() {
        return this.memberCd;
    }

    /**
     * 会員コードを設定する
     *
     * @param memberCd 会員コード
     */
    public void setMemberCd(String memberCd) {
        this.memberCd = memberCd;
    }

    /**
     * 子供の続柄を取得する
     *
     * @return reltnspDiv 子供の続柄
     */
    public String getReltnspDiv() {
        return this.reltnspDiv;
    }

    /**
     * 子供の続柄を設定する
     *
     * @param reltnspDiv 子供の続柄
     */
    public void setReltnspDiv(String reltnspDiv) {
        this.reltnspDiv = reltnspDiv;
    }

    /**
     * 緊急電話番号を取得する
     *
     * @return urgTelnum 緊急電話番号
     */
    public String getUrgTelnum() {
        return this.urgTelnum;
    }

    /**
     * 緊急電話番号を設定する
     *
     * @param urgTelnum 緊急電話番号
     */
    public void setUrgTelnum(String urgTelnum) {
        this.urgTelnum = urgTelnum;
    }

    /**
     * GakkenID規約フラグを取得する
     *
     * @return gidRuleFlg GakkenID規約フラグ
     */
    public String getGidRuleFlg() {
        return this.gidRuleFlg;
    }

    /**
     * GakkenID規約フラグを設定する
     *
     * @param gidRuleFlg GakkenID規約フラグ
     */
    public void setGidRuleFlg(String gidRuleFlg) {
        this.gidRuleFlg = gidRuleFlg;
    }

    /**
     * マナミル規約フラグを取得する
     *
     * @return manaRuleFlg マナミル規約フラグ
     */
    public String getManaRuleFlg() {
        return this.manaRuleFlg;
    }

    /**
     * マナミル規約フラグを設定する
     *
     * @param manaRuleFlg マナミル規約フラグ
     */
    public void setManaRuleFlg(String manaRuleFlg) {
        this.manaRuleFlg = manaRuleFlg;
    }

    /**
     * 個人情報保護規約フラグを取得する
     *
     * @return perlInfoRuleFlg 個人情報保護規約フラグ
     */
    public String getPerlInfoRuleFlg() {
        return this.perlInfoRuleFlg;
    }

    /**
     * 個人情報保護規約フラグを設定する
     *
     * @param perlInfoRuleFlg 個人情報保護規約フラグ
     */
    public void setPerlInfoRuleFlg(String perlInfoRuleFlg) {
        this.perlInfoRuleFlg = perlInfoRuleFlg;
    }
}
