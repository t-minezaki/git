package jp.learningpark.modules.job.entity;

import java.util.Date;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/8/27 ： NWT)hxl ： 新規作成
 * @date 2020/8/27 17:39
 */
public class BTGKA1004MemberDto extends BTGKA1004ManaMemberDto {

    /**
     * 会員姓
     */
    private String stuFlnmNm;

    /**
     * 会員名
     */
    private String stuFlnmLnm;

    /**
     * 会員姓（カナ）
     */
    private String stuFlnmKnNm;

    /**
     * 会員名（カナ）
     */
    private String stuFlnmKnLnm;

    /**
     * 学年
     */
    private String schyDiv;

    /**
     * 生年月日
     */
    private Date birthd;

    /**
     * 性別
     */
    private String gendrDiv;

    /**
     * 保護者名(漢字)
     */
    private String guardNm;

    /**
     * 保護者名(カナ)
     */
    private String guardKnNm;

    /**
     * 住所(郵便番号)
     */
    private String postCd;

    /**
     * 住所(都道府県)
     */
    private String prefecturesCd;

    /**
     * 住所(市区町村)
     */
    private String city;

    /**
     * 住所(番地以降)
     */
    private String address;

    /**
     * 電話番号
     */
    private String telnum;

    /**
     * 緊急電話番号
     */
    private String urgTelnum;

    /**
     * 会員状況
     */
    private String memberStatus;

    /**
     * 学校
     */
    private String sch;

    /**
     * メールアドレス
     */
    private String mailad;

    /**
     * 指導者名
     */
    private String teacherNm;

    /**
     * コース
     */
    private String course;

    /**
     * 登録年月日
     */
    private Date loginDate;

    /**
     * 移動年月日
     */
    private Date moveDate;

    /**
     * 入会年月日
     */
    private Date registerDate;

    /**
     * 復会年月日
     */
    private Date recoverDate;

    /**
     * 申請取消フラグ
     */
    private String replyCancelFlag;

    /**
     * 申込内容区分
     */
    private String replyContentDiv;

    /**
     * 変更前コース
     */
    private String courseBeforeChange;

    /**
     * 取消前の会員状況
     */
    private String memberStatusBeforeCancel;

    /**
     * 取消前の移動年月日
     */
    private Date moveDateBeforeCancel;

    /**
     * 会員姓を取得する
     *
     * @return stuFlnmNm 会員姓
     */
    public String getStuFlnmNm() {
        return this.stuFlnmNm;
    }

    /**
     * 会員姓を設定する
     *
     * @param stuFlnmNm 会員姓
     */
    public void setStuFlnmNm(String stuFlnmNm) {
        this.stuFlnmNm = stuFlnmNm;
    }

    /**
     * 会員名を取得する
     *
     * @return stuFlnmLnm 会員名
     */
    public String getStuFlnmLnm() {
        return this.stuFlnmLnm;
    }

    /**
     * 会員名を設定する
     *
     * @param stuFlnmLnm 会員名
     */
    public void setStuFlnmLnm(String stuFlnmLnm) {
        this.stuFlnmLnm = stuFlnmLnm;
    }

    /**
     * 会員姓（カナ）を取得する
     *
     * @return stuFlnmKnNm 会員姓（カナ）
     */
    public String getStuFlnmKnNm() {
        return this.stuFlnmKnNm;
    }

    /**
     * 会員姓（カナ）を設定する
     *
     * @param stuFlnmKnNm 会員姓（カナ）
     */
    public void setStuFlnmKnNm(String stuFlnmKnNm) {
        this.stuFlnmKnNm = stuFlnmKnNm;
    }

    /**
     * 会員名（カナ）を取得する
     *
     * @return stuFlnmKnLnm 会員名（カナ）
     */
    public String getStuFlnmKnLnm() {
        return this.stuFlnmKnLnm;
    }

    /**
     * 会員名（カナ）を設定する
     *
     * @param stuFlnmKnLnm 会員名（カナ）
     */
    public void setStuFlnmKnLnm(String stuFlnmKnLnm) {
        this.stuFlnmKnLnm = stuFlnmKnLnm;
    }

    /**
     * 学年を取得する
     *
     * @return schyDiv 学年
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * 学年を設定する
     *
     * @param schyDiv 学年
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
     * 性別を取得する
     *
     * @return gendrDiv 性別
     */
    public String getGendrDiv() {
        return this.gendrDiv;
    }

    /**
     * 性別を設定する
     *
     * @param gendrDiv 性別
     */
    public void setGendrDiv(String gendrDiv) {
        this.gendrDiv = gendrDiv;
    }

    /**
     * 住所(郵便番号)を取得する
     *
     * @return postCd 住所(郵便番号)
     */
    public String getPostCd() {
        return this.postCd;
    }

    /**
     * 住所(郵便番号)を設定する
     *
     * @param postCd 住所(郵便番号)
     */
    public void setPostCd(String postCd) {
        this.postCd = postCd;
    }

    /**
     * 住所(都道府県)を取得する
     *
     * @return prefecturesCd 住所(都道府県)
     */
    public String getPrefecturesCd() {
        return this.prefecturesCd;
    }

    /**
     * 住所(都道府県)を設定する
     *
     * @param prefecturesCd 住所(都道府県)
     */
    public void setPrefecturesCd(String prefecturesCd) {
        this.prefecturesCd = prefecturesCd;
    }

    /**
     * 住所(市区町村)を取得する
     *
     * @return city 住所(市区町村)
     */
    public String getCity() {
        return this.city;
    }

    /**
     * 住所(市区町村)を設定する
     *
     * @param city 住所(市区町村)
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 住所(番地以降)を取得する
     *
     * @return address 住所(番地以降)
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 住所(番地以降)を設定する
     *
     * @param address 住所(番地以降)
     */
    public void setAddress(String address) {
        this.address = address;
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
     * 会員状況を取得する
     *
     * @return memberStatus 会員状況
     */
    public String getMemberStatus() {
        return this.memberStatus;
    }

    /**
     * 会員状況を設定する
     *
     * @param memberStatus 会員状況
     */
    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    /**
     * 学校を取得する
     *
     * @return sch 学校
     */
    public String getSch() {
        return this.sch;
    }

    /**
     * 学校を設定する
     *
     * @param sch 学校
     */
    public void setSch(String sch) {
        this.sch = sch;
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
     * 指導者名を取得する
     *
     * @return teacherNm 指導者名
     */
    public String getTeacherNm() {
        return this.teacherNm;
    }

    /**
     * 指導者名を設定する
     *
     * @param teacherNm 指導者名
     */
    public void setTeacherNm(String teacherNm) {
        this.teacherNm = teacherNm;
    }

    /**
     * コースを取得する
     *
     * @return course コース
     */
    public String getCourse() {
        return this.course;
    }

    /**
     * コースを設定する
     *
     * @param course コース
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * 登録年月日を取得する
     *
     * @return loginDate 登録年月日
     */
    public Date getLoginDate() {
        return this.loginDate;
    }

    /**
     * 登録年月日を設定する
     *
     * @param loginDate 登録年月日
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * 移動年月日を取得する
     *
     * @return moveDate 移動年月日
     */
    public Date getMoveDate() {
        return this.moveDate;
    }

    /**
     * 移動年月日を設定する
     *
     * @param moveDate 移動年月日
     */
    public void setMoveDate(Date moveDate) {
        this.moveDate = moveDate;
    }

    /**
     * 入会年月日を取得する
     *
     * @return registerDate 入会年月日
     */
    public Date getRegisterDate() {
        return this.registerDate;
    }

    /**
     * 入会年月日を設定する
     *
     * @param registerDate 入会年月日
     */
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * 復会年月日を取得する
     *
     * @return recoverDate 復会年月日
     */
    public Date getRecoverDate() {
        return this.recoverDate;
    }

    /**
     * 復会年月日を設定する
     *
     * @param recoverDate 復会年月日
     */
    public void setRecoverDate(Date recoverDate) {
        this.recoverDate = recoverDate;
    }

    /**
     * 申請取消フラグを取得する
     *
     * @return replyCancelFlag 申請取消フラグ
     */
    public String getReplyCancelFlag() {
        return this.replyCancelFlag;
    }

    /**
     * 申請取消フラグを設定する
     *
     * @param replyCancelFlag 申請取消フラグ
     */
    public void setReplyCancelFlag(String replyCancelFlag) {
        this.replyCancelFlag = replyCancelFlag;
    }

    /**
     * 申込内容区分を取得する
     *
     * @return replyContentDiv 申込内容区分
     */
    public String getReplyContentDiv() {
        return this.replyContentDiv;
    }

    /**
     * 申込内容区分を設定する
     *
     * @param replyContentDiv 申込内容区分
     */
    public void setReplyContentDiv(String replyContentDiv) {
        this.replyContentDiv = replyContentDiv;
    }

    /**
     * 変更前コースを取得する
     *
     * @return courseBeforeChange 変更前コース
     */
    public String getCourseBeforeChange() {
        return this.courseBeforeChange;
    }

    /**
     * 変更前コースを設定する
     *
     * @param courseBeforeChange 変更前コース
     */
    public void setCourseBeforeChange(String courseBeforeChange) {
        this.courseBeforeChange = courseBeforeChange;
    }

    /**
     * 取消前の会員状況を取得する
     *
     * @return memberStatusBeforeCancel 取消前の会員状況
     */
    public String getMemberStatusBeforeCancel() {
        return this.memberStatusBeforeCancel;
    }

    /**
     * 取消前の会員状況を設定する
     *
     * @param memberStatusBeforeCancel 取消前の会員状況
     */
    public void setMemberStatusBeforeCancel(String memberStatusBeforeCancel) {
        this.memberStatusBeforeCancel = memberStatusBeforeCancel;
    }

    /**
     * 取消前の移動年月日を取得する
     *
     * @return moveDateBeforeCancel 取消前の移動年月日
     */
    public Date getMoveDateBeforeCancel() {
        return this.moveDateBeforeCancel;
    }

    /**
     * 取消前の移動年月日を設定する
     *
     * @param moveDateBeforeCancel 取消前の移動年月日
     */
    public void setMoveDateBeforeCancel(Date moveDateBeforeCancel) {
        this.moveDateBeforeCancel = moveDateBeforeCancel;
    }

    /**
     * 保護者名(漢字)を取得する
     *
     * @return guardNm 保護者名(漢字)
     */
    public String getGuardNm() {
        return this.guardNm;
    }

    /**
     * 保護者名(漢字)を設定する
     *
     * @param guardNm 保護者名(漢字)
     */
    public void setGuardNm(String guardNm) {
        this.guardNm = guardNm;
    }

    /**
     * 保護者名(カナ)を取得する
     *
     * @return guardKnNm 保護者名(カナ)
     */
    public String getGuardKnNm() {
        return this.guardKnNm;
    }

    /**
     * 保護者名(カナ)を設定する
     *
     * @param guardKnNm 保護者名(カナ)
     */
    public void setGuardKnNm(String guardKnNm) {
        this.guardKnNm = guardKnNm;
    }
}
