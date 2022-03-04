package jp.learningpark.modules.manager.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>未読・未回答者送信一覧画面 Dto</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/18 : NWT文: 新規<br />
 * 2020/11/12 : NWT文: 変更<br />
 * @version 9.0
 */
public class F08019Dto implements Serializable {

    /**
     * 保護者イベント申込状況.保護者ID
     */
    private String guardId;

    /**
     * 保護者イベント申込状況.保護者AFTER_ID
     */
    private String guardAfterId;

    /**
     * 保護者基本マスタ.姓名
     */
    private String guardNm;

    /**
     * 保護者イベント申込状況.生徒ID
     */
    private String stuId;

    /**
     * 保護者イベント申込状況.生徒AFTER_ID
     */
    private String stuAfterId;

    /**
     * 生徒基本マスタ.姓名
     */
    private String stuNm;

    /**
     * 生徒基本マスタ.学年
     */
    private String schyDiv;

    /**
     * 保護者イベント申込状況.区分
     */
    private String status;

    /**
     * 保護者基本マスタ.メールアドレス
     */
    private String mailAddress;

    /**
     * イベント.カテゴリ
     */
    private String ctgyNm;

    /**
     * イベント日程(詳細).日程予定日もじれつ
     */
    private Date sgdPlanDate;

    /**
     * 組織マスタ.組織名
     */
    private String orgNm;

    /**
     * 保護者イベント申込状況.質問回答
     */
    private String replyCnt;

    /**
     * イベント日程(詳細).関連タイプ
     */
    private String refTypeDiv;

    /**
     * メンター基本マスタ.姓名_姓
     */
    private String mentorNm;

    /**
     * イベント日程(詳細).日程開始日時
     */
    private Timestamp sgdStartDatime;

    /**
     * イベント.イベントタイトル
     */
    private String eventTitle;

    /**
     * 組織マスタ．ブランドコード
     */
    private String brandCd;

    /**
     * 保護者イベント申込状況.組織ID
     */
    private String orgId;

    /**
     * 保護者イベント申込状況．閲覧状況区分
     */
    private String readingStsDiv;

    /**
     * 保護者イベント申込状況．閲覧回答区分
     */
    private String replyStsDiv;

    /**
     * 面談記録．面談申込状態区分
     */
    private String statusDiv;

    /**
     * 面談記録．ID
     */
    private Integer talkId;

    /**
     * 面談記録.面談申込状態区分
     */
    private String talkApplyStsDiv;

    private Integer trhId;

    /**
     * 画面.保護者ID
     *
     * @return guardId 画面.保護者ID
     */
    public String getGuardId() {
        return guardId;
    }

    /**
     * 画面.保護者ID
     *
     * @param guardId 画面.保護者ID
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * 画面.保護者基本マスタ.姓名
     *
     * @return guardNm 画面.保護者基本マスタ.姓名
     */
    public String getGuardNm() {
        return guardNm;
    }

    /**
     * 画面.保護者基本マスタ.姓名
     *
     * @param guardNm 画面.保護者基本マスタ.姓名
     */
    public void setGuardNm(String guardNm) {
        this.guardNm = guardNm;
    }

    /**
     * 画面.生徒ID
     *
     * @return stuId 画面.生徒ID
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * 画面.生徒ID
     *
     * @param stuId 画面.生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 画面.生徒基本マスタ.姓名
     *
     * @return stuNm 画面.生徒基本マスタ.姓名
     */
    public String getStuNm() {
        return stuNm;
    }

    /**
     * 画面.生徒基本マスタ.姓名
     *
     * @param stuNm 画面.生徒基本マスタ.姓名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 画面.区分
     *
     * @return status 画面.区分
     */
    public String getStatus() {
        return status;
    }

    /**
     * 画面.区分
     *
     * @param status 画面.区分
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 画面.メールアドレス
     *
     * @return mailAddress 画面.メールアドレス
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * 画面.メールアドレス
     *
     * @param mailAddress 画面.メールアドレス
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    /**
     * 画面.カテゴリ
     *
     * @return ctgyNm 画面.カテゴリ
     */
    public String getCtgyNm() {
        return ctgyNm;
    }

    /**
     * 画面.カテゴリ
     *
     * @param ctgyNm 画面.カテゴリ
     */
    public void setCtgyNm(String ctgyNm) {
        this.ctgyNm = ctgyNm;
    }

    /**
     * 画面.日程予定日もじれつ
     *
     * @return sgdPlanDate 画面.日程予定日もじれつ
     */
    public Date getSgdPlanDate() {
        return sgdPlanDate;
    }

    /**
     * 画面.日程予定日もじれつ
     *
     * @param sgdPlanDate 画面.日程予定日もじれつ
     */
    public void setSgdPlanDate(Date sgdPlanDate) {
        this.sgdPlanDate = sgdPlanDate;
    }

    /**
     * 画面.組織名
     *
     * @return orgNm 画面.組織名
     */
    public String getOrgNm() {
        return orgNm;
    }

    /**
     * 画面.組織名
     *
     * @param orgNm 画面.組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * 画面.質問回答
     *
     * @return replyCnt 画面.質問回答
     */
    public String getReplyCnt() {
        return replyCnt;
    }

    /**
     * 画面.質問回答
     *
     * @param replyCnt 画面.質問回答
     */
    public void setReplyCnt(String replyCnt) {
        this.replyCnt = replyCnt;
    }

    /**
     * 画面.関連タイプ
     *
     * @return refTypeDiv 画面.関連タイプ
     */
    public String getRefTypeDiv() {
        return refTypeDiv;
    }

    /**
     * 画面.関連タイプ
     *
     * @param refTypeDiv 画面.関連タイプ
     */
    public void setRefTypeDiv(String refTypeDiv) {
        this.refTypeDiv = refTypeDiv;
    }

    /**
     * 画面.メンター基本マスタ.姓名
     *
     * @return mentorNm 画面.メンター基本マスタ.姓名
     */
    public String getMentorNm() {
        return mentorNm;
    }

    /**
     * 画面.メンター基本マスタ.姓名
     *
     * @param mentorNm 画面.メンター基本マスタ.姓名
     */
    public void setMentorNm(String mentorNm) {
        this.mentorNm = mentorNm;
    }

    /**
     * 画面.日程開始日時
     *
     * @return sgdStartDatime 画面.日程開始日時
     */
    public Timestamp getSgdStartDatime() {
        return sgdStartDatime;
    }

    /**
     * 画面.日程開始日時
     *
     * @param sgdStartDatime 画面.日程開始日時
     */
    public void setSgdStartDatime(Timestamp sgdStartDatime) {
        this.sgdStartDatime = sgdStartDatime;
    }

    /**
     * 画面.イベントタイトル
     *
     * @return eventTitle 画面.イベントタイトル
     */
    public String getEventTitle() {
        return eventTitle;
    }

    /**
     * 画面.イベントタイトル
     *
     * @param eventTitle 画面.イベントタイトル
     */
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    /**
     * 画面.ブランドコード
     *
     * @return brandCd 画面.ブランドコード
     */
    public String getBrandCd() {
        return brandCd;
    }

    /**
     * 画面.ブランドコード
     *
     * @param brandCd 画面.ブランドコード
     */
    public void setBrandCd(String brandCd) {
        this.brandCd = brandCd;
    }

    /**
     * 画面.組織ID
     *
     * @return orgId 画面.組織ID
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 画面.組織ID
     *
     * @param orgId 画面.組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 画面.閲覧状況区分
     *
     * @return readingStsDiv 画面.閲覧状況区分
     */
    public String getReadingStsDiv() {
        return readingStsDiv;
    }

    /**
     * 画面.閲覧状況区分
     *
     * @param readingStsDiv 画面.閲覧状況区分
     */
    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }

    /**
     * 画面.閲覧回答区分
     *
     * @return replyStsDiv 画面.閲覧回答区分
     */
    public String getReplyStsDiv() {
        return replyStsDiv;
    }

    /**
     * 画面.閲覧回答区分
     *
     * @param replyStsDiv 画面.閲覧回答区分
     */
    public void setReplyStsDiv(String replyStsDiv) {
        this.replyStsDiv = replyStsDiv;
    }

    /**
     * 保護者イベント申込状況.保護者AFTER_IDを取得する
     *
     * @return guardAfterId 保護者イベント申込状況.保護者AFTER_ID
     */
    public String getGuardAfterId() {
        return this.guardAfterId;
    }

    /**
     * 保護者イベント申込状況.保護者AFTER_IDを設定する
     *
     * @param guardAfterId 保護者イベント申込状況.保護者AFTER_ID
     */
    public void setGuardAfterId(String guardAfterId) {
        this.guardAfterId = guardAfterId;
    }

    /**
     * 保護者イベント申込状況.生徒AFTER_IDを取得する
     *
     * @return stuAfterId 保護者イベント申込状況.生徒AFTER_ID
     */
    public String getStuAfterId() {
        return this.stuAfterId;
    }

    /**
     * 保護者イベント申込状況.生徒AFTER_IDを設定する
     *
     * @param stuAfterId 保護者イベント申込状況.生徒AFTER_ID
     */
    public void setStuAfterId(String stuAfterId) {
        this.stuAfterId = stuAfterId;
    }

    /**
     * 面談記録．面談申込状態区分を取得する
     *
     * @return statusDiv 面談記録．面談申込状態区分
     */
    public String getStatusDiv() {
        return this.statusDiv;
    }

    /**
     * 面談記録．面談申込状態区分を設定する
     *
     * @param statusDiv 面談記録．面談申込状態区分
     */
    public void setStatusDiv(String statusDiv) {
        this.statusDiv = statusDiv;
    }

    /**
     * 面談記録．IDを取得する
     *
     * @return talkId 面談記録．ID
     */
    public Integer getTalkId() {
        return this.talkId;
    }

    /**
     * 面談記録．IDを設定する
     *
     * @param talkId 面談記録．ID
     */
    public void setTalkId(Integer talkId) {
        this.talkId = talkId;
    }

    /**
     * 生徒基本マスタ.学年を取得する
     *
     * @return schyDiv
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * 生徒基本マスタ.学年を設定する
     *
     * @param schyDiv
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * 面談記録.面談申込状態区分を取得する
     *
     * @return talkApplyStsDiv 面談記録.面談申込状態区分
     */
    public String getTalkApplyStsDiv() {
        return this.talkApplyStsDiv;
    }

    /**
     * 面談記録.面談申込状態区分を設定する
     *
     * @param talkApplyStsDiv 面談記録.面談申込状態区分
     */
    public void setTalkApplyStsDiv(String talkApplyStsDiv) {
        this.talkApplyStsDiv = talkApplyStsDiv;
    }

    /**
     * を取得する
     *
     * @return trhId
     */
    public Integer getTrhId() {
        return this.trhId;
    }

    /**
     * を設定する
     *
     * @param trhId
     */
    public void setTrhId(Integer trhId) {
        this.trhId = trhId;
    }
}
