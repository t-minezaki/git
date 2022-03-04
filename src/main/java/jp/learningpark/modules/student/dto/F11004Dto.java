/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;

/**
 * <p>学習情報登録一覧</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/24 : zpa: 新規<br />
 * @version 7.0
 */
public class F11004Dto extends StuWeeklyPlanPerfEntity {

    private String subImgPath;

    private String subName;

    private String startPlanTm;

    private String endPlanTm;

    private String planYmd1;

    private String weekDay;

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSubImgPath() {
        return subImgPath;
    }

    public void setSubImgPath(String subImgPath) {
        this.subImgPath = subImgPath;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getEndPlanTm() {
        return endPlanTm;
    }

    public void setEndPlanTm(String endPlanTm) {
        this.endPlanTm = endPlanTm;
    }

    public String getStartPlanTm() {
        return startPlanTm;
    }

    public void setStartPlanTm(String startPlanTm) {
        this.startPlanTm = startPlanTm;
    }

    public String getPlanYmd1() {
        return planYmd1;
    }

    public void setPlanYmd1(String planYmd1) {
        this.planYmd1 = planYmd1;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }
}