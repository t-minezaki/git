/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.sql.Date;

/**
 * <p>F20001_生徒一覧画面 Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/05 : gong: 新規<br />
 * @version 1.0
 */
public class F20001Dto {

    /**
     * Id
     */
    private Integer id;

    /***
     * 学生ID
     */
    private String stuId;

    /***
     * 姓
     */
    private String stuNM;

    /**
     * 状況:0-未計画 1-未完成 2-已完成
     */
    private String status;

    /**
     * ユーザID
     */
    private String userId;

    /**
     * ユーザ名
     */
    private String userName;

    /**
     * 学年区分
     */
    private String schyDiv;

    /**
     * グループ区分ID
     */
    private Integer grpDiv1;

    /**
     * 計画期間開始日
     */
    private Date planStartDy;
    /**
     *計画期間終了日
     */
    private Date planEndDy;

    /**
     * 未計画者に絞るがチェック
     */
    private String termPlanCheck;

    /***
     * 名
     */
    private String stuLNM;

    /***
     * タームプラン t-完成 f-未完成
     */
    private Boolean planStatus;

    /***
     * 名前活性状況　　t-活性　f-非活性
     */
    private Boolean nameStatus;

    /**
     * 学生IDを設定する
     *
     * @return stuId 学生ID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 学生IDを取得する
     *
     * @param stuId 学生ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 姓を設定する
     *
     * @return stuNM 姓
     */
    public String getStuNM() {
        return this.stuNM;
    }

    /**
     * 姓を取得する
     *
     * @param stuNM 姓
     */
    public void setStuNM(String stuNM) {
        this.stuNM = stuNM;
    }

    /**
     * 名を設定する
     *
     * @return stuLNM 名
     */
    public String getStuLNM() {
        return this.stuLNM;
    }

    /**
     * 名を取得する
     *
     * @param stuLNM 名
     */
    public void setStuLNM(String stuLNM) {
        this.stuLNM = stuLNM;
    }

    /**
     * タームプラン t-完成 f-未完成を設定する
     *
     * @return planStatus タームプラン t-完成 f-未完成
     */
    public Boolean getPlanStatus() {
        return this.planStatus;
    }

    /**
     * タームプラン t-完成 f-未完成を取得する
     *
     * @param planStatus タームプラン t-完成 f-未完成
     */
    public void setPlanStatus(Boolean planStatus) {
        this.planStatus = planStatus;
    }

    /**
     * 名前活性状況　　t-活性　f-非活性を設定する
     *
     * @return nameStatus 名前活性状況　　t-活性　f-非活性
     */
    public Boolean getNameStatus() {
        return this.nameStatus;
    }

    /**
     * 名前活性状況　　t-活性　f-非活性を取得する
     *
     * @param nameStatus 名前活性状況　　t-活性　f-非活性
     */
    public void setNameStatus(Boolean nameStatus) {
        this.nameStatus = nameStatus;
    }

    /**
     * 状況:0-未計画 1-未完成 2-已完成を設定する
     *
     * @return status 状況:0-未計画 1-未完成 2-已完成
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 状況:0-未計画 1-未完成 2-已完成を取得する
     *
     * @param status 状況:0-未計画 1-未完成 2-已完成
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * ユーザIDを設定する
     *
     * @return userId ユーザID
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * ユーザIDを取得する
     *
     * @param userId ユーザID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * ユーザ名を設定する
     *
     * @return userName ユーザ名
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * ユーザ名を取得する
     *
     * @param userName ユーザ名
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * 未計画者に絞るがチェックを設定する
     *
     * @return termPlanCheck 未計画者に絞るがチェック
     */
    public String getTermPlanCheck() {
        return this.termPlanCheck;
    }

    /**
     * 未計画者に絞るがチェックを取得する
     *
     * @param termPlanCheck 未計画者に絞るがチェック
     */
    public void setTermPlanCheck(String termPlanCheck) {
        this.termPlanCheck = termPlanCheck;
    }

    /**
     * Idを設定する
     *
     * @return id Id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Idを取得する
     *
     * @param id Id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * グループ区分IDを設定する
     *
     * @return grpDiv1 グループ区分ID
     */
    public Integer getGrpDiv1() {
        return this.grpDiv1;
    }

    /**
     * グループ区分IDを取得する
     *
     * @param grpDiv1 グループ区分ID
     */
    public void setGrpDiv1(Integer grpDiv1) {
        this.grpDiv1 = grpDiv1;
    }

    /**
     * 計画期間開始日取得する
     * @return
     */
    public Date getPlanStartDy() {
        return planStartDy;
    }

    /**
     * 計画期間開始日設定する
     * @param planStartDy
     */
    public void setPlanStartDy(Date planStartDy) {
        this.planStartDy = planStartDy;
    }

    /**
     * 計画期間終了日取得する
     * @return
     */
    public Date getPlanEndDy() {
        return planEndDy;
    }

    /**
     * 計画期間終了日設定する
     * @param planEndDy
     */
    public void setPlanEndDy(Date planEndDy) {
        this.planEndDy = planEndDy;
    }
}
