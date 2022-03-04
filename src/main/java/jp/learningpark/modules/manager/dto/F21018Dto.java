package jp.learningpark.modules.manager.dto;

import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>
 * F21018Dto
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/20 ： NWT)hxl ： 新規作成
 * @date 2020/02/20 17:32
 */
public class F21018Dto {

    /**
     * 学校名
     */
    private String sch;

    /**
     * 保護者ID
     */
    private String guardId;

    /**
     * 保護者1ID
     */
    private String guard1Id;

    /**
     * 保護者2ID
     */
    private String guard2Id;

    /**
     * 保護者3ID
     */
    private String guard3Id;

    /**
     * 保護者4ID
     */
    private String guard4Id;

    /**
     * 保護者ログイン状況
     */
    private String guardLoginStatus;
    /**
     * 姓名
     */
    private String stuNm;

    /**
     * カナ姓名
     */
    private String stuKnNm;

    /**
     * 学年
     */
    private String schy;

    /**
     * 学年区分
     */
    private String schyDiv;

    /**
     * 生年月日
     */
    private Date birthd;

    /**
     * 写真パス
     */
    private String photPath;

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
     * ＱＲコード
     */
    private String qrCod;

    /**
     * メールアドレス
     */
    private String mailad;

    /**
     * 保護者姓名
     */
    private String guardNm;

    /**
     * 電話番号
     */
    private String telnum;

    /**
     * 続柄区分
     */
    private String reltnspDiv;

    /**
     * 会員番号
     */
    private String memberCd;

    /**
     * 現在コース
     */
    private String currentCourse;

    /**
     * 入会年月日
     */
    private String admissionDate;

    /**
     * ぐるみ登録済ID
     */
    private String registeredID;

    /**
     * クラウドルーム登録済ID
     */
    private String classroomRegisteredID;

    /**
     * マナミルアプリダウンロード済ID
     */
    private String appRegisteredID;

    /**
     * updDatime
     */
    private Timestamp updDatime;

    /**
     * 緊急電話番号
     */
    private String urgTelnum;

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
     * 保護者2IDを取得する
     *
     * @return guard2Id 保護者2ID
     */
    public String getGuard2Id() {
        return this.guard2Id;
    }

    /**
     * 保護者2IDを設定する
     *
     * @param guard2Id 保護者2ID
     */
    public void setGuard2Id(String guard2Id) {
        this.guard2Id = guard2Id;
    }

    /**
     * 保護者3IDを取得する
     *
     * @return guard3Id 保護者3ID
     */
    public String getGuard3Id() {
        return this.guard3Id;
    }

    /**
     * 保護者3IDを設定する
     *
     * @param guard3Id 保護者3ID
     */
    public void setGuard3Id(String guard3Id) {
        this.guard3Id = guard3Id;
    }

    /**
     * 保護者4IDを取得する
     *
     * @return guard4Id 保護者4ID
     */
    public String getGuard4Id() {
        return this.guard4Id;
    }

    /**
     * 保護者4IDを設定する
     *
     * @param guard4Id 保護者4ID
     */
    public void setGuard4Id(String guard4Id) {
        this.guard4Id = guard4Id;
    }

    /**
     * 姓名を取得する
     *
     * @return stuNm 姓名
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 姓名を設定する
     *
     * @param stuNm 姓名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * カナ姓名を取得する
     *
     * @return stuKnNm カナ姓名
     */
    public String getStuKnNm() {
        return this.stuKnNm;
    }

    /**
     * カナ姓名を設定する
     *
     * @param stuKnNm カナ姓名
     */
    public void setStuKnNm(String stuKnNm) {
        this.stuKnNm = stuKnNm;
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
     * 保護者姓名を取得する
     *
     * @return guardNm 保護者姓名
     */
    public String getGuardNm() {
        return this.guardNm;
    }

    /**
     * 保護者姓名を設定する
     *
     * @param guardNm 保護者姓名
     */
    public void setGuardNm(String guardNm) {
        this.guardNm = guardNm;
    }

    /**
     * 電話番号を取得する
     *
     * @return telnum 電話番号
     */
    public String getTelnum() {
        return this.telnum;
    }

    /**
     * 電話番号を設定する
     *
     * @param telnum 電話番号
     */
    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    /**
     * 続柄区分を取得する
     *
     * @return reltnspDiv 続柄区分
     */
    public String getReltnspDiv() {
        return this.reltnspDiv;
    }

    /**
     * 続柄区分を設定する
     *
     * @param reltnspDiv 続柄区分
     */
    public void setReltnspDiv(String reltnspDiv) {
        this.reltnspDiv = reltnspDiv;
    }


    /**
     * 現在コースを取得する
     *
     * @return currentCourse 現在コース
     */
    public String getCurrentCourse() {
        return this.currentCourse;
    }

    /**
     * 現在コースを設定する
     *
     * @param currentCourse 現在コース
     */
    public void setCurrentCourse(String currentCourse) {
        this.currentCourse = currentCourse;
    }

    /**
     * ぐるみ登録済IDを取得する
     *
     * @return registeredID ぐるみ登録済ID
     */
    public String getRegisteredID() {
        return this.registeredID;
    }

    /**
     * ぐるみ登録済IDを設定する
     *
     * @param registeredID ぐるみ登録済ID
     */
    public void setRegisteredID(String registeredID) {
        this.registeredID = registeredID;
    }

    /**
     * クラウドルーム登録済IDを取得する
     *
     * @return classroomRegisteredID クラウドルーム登録済ID
     */
    public String getClassroomRegisteredID() {
        return this.classroomRegisteredID;
    }

    /**
     * クラウドルーム登録済IDを設定する
     *
     * @param classroomRegisteredID クラウドルーム登録済ID
     */
    public void setClassroomRegisteredID(String classroomRegisteredID) {
        this.classroomRegisteredID = classroomRegisteredID;
    }

    /**
     * マナミルアプリダウンロード済IDを取得する
     *
     * @return appRegisteredID マナミルアプリダウンロード済ID
     */
    public String getAppRegisteredID() {
        return this.appRegisteredID;
    }

    /**
     * マナミルアプリダウンロード済IDを設定する
     *
     * @param appRegisteredID マナミルアプリダウンロード済ID
     */
    public void setAppRegisteredID(String appRegisteredID) {
        this.appRegisteredID = appRegisteredID;
    }

    /**
     * updDatimeを取得する
     *
     * @return updDatime updDatime
     */
    public Timestamp getUpdDatime() {
        return this.updDatime;
    }

    /**
     * updDatimeを設定する
     *
     * @param updDatime updDatime
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
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
     * 会員番号を取得する
     *
     * @return memberCd 会員番号
     */
    public String getMemberCd() {
        return this.memberCd;
    }

    /**
     * 会員番号を設定する
     *
     * @param memberCd 会員番号
     */
    public void setMemberCd(String memberCd) {
        this.memberCd = memberCd;
    }

    /**
     * 入会年月日を取得する
     *
     * @return admissionDate 入会年月日
     */
    public String getAdmissionDate() {
        return this.admissionDate;
    }

    /**
     * 入会年月日を設定する
     *
     * @param admissionDate 入会年月日
     */
    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }
    // add at 2021/08/09 for V9.02 by NWT LiGX START
    /**
     * 保護者ログイン状況を取得する
     *
     * @param guardLoginStatus 保護者ログイン状況
     */
    public String getGuardLoginStatus() {
        return guardLoginStatus;
    }
    /**
     * 保護者ログイン状況を設定する
     *
     * @param guardLoginStatus 保護者ログイン状況
     */
    public void setGuardLoginStatus(String guardLoginStatus) {
        this.guardLoginStatus = guardLoginStatus;
    }
    //add at 2021/08/09 for V9.02 by NWT LiGX END
}
