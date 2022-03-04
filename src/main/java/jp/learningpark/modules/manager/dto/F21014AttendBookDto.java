package jp.learningpark.modules.manager.dto;

import java.util.Date;

/**
 * <p>
 * 出席簿出席簿明細Dto
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/12/05 ： NWT)hxl ： 新規作成
 * @date 2019/12/05 10:40
 */
public class F21014AttendBookDto {
    /**
     * 対象年月日
     */
    private Date date;
    /**
     * 使用テキスト
     */
    private String useContent;
    /**
     * 指導内容
     */
    private String guidContent;
    /**
     * 宿題内容
     */
    private String hwkContent;
    /**
     * 小テスト単元名
     */
    private String testUnitName;
    /**
     * 連絡事項内容
     */
    private String connectItemContent;
    /**
     * 前回宿題完成区分
     */
    private String lastHwkValue;
    /**
     * 進捗ステータス区分
     */
    private String schStsValue;
    /**
     * 授業様子区分
     */
    private String lectShapeValue;
    /**
     * 小テスト点数
     */
    private Integer testPoints;
    /**
     * 教科区分
     */
    private String subjtValue;
    /**
     * 宿題区分
     */
    private String homeworkValue;
    /**
     * ケア区分
     */
    private String careValue;
    /**
     * 出欠区分
     */
    private String absStsValue;

    /**
     * 小テスト点数を取得する
     *
     * @return
     */
    public Integer getTestPoints() {
        return testPoints;
    }

    /**
     * 小テスト点数を設定する
     *
     * @param testPoints
     */
    public void setTestPoints(Integer testPoints) {
        this.testPoints = testPoints;
    }

    /**
     * 教科区分を取得する
     *
     * @return
     */
    public String getSubjtValue() {
        return subjtValue;
    }

    /**
     * 教科区分を設定する
     *
     * @param subjtValue
     */
    public void setSubjtValue(String subjtValue) {
        this.subjtValue = subjtValue;
    }

    /**
     * 宿題区分を取得する
     *
     * @return
     */
    public String getHomeworkValue() {
        return homeworkValue;
    }

    /**
     * 宿題区分を設定する
     *
     * @param homeworkValue
     */
    public void setHomeworkValue(String homeworkValue) {
        this.homeworkValue = homeworkValue;
    }

    /**
     * ケア区分を取得する
     *
     * @return
     */
    public String getCareValue() {
        return careValue;
    }

    /**
     * ケア区分を設定する
     *
     * @param careValue
     */
    public void setCareValue(String careValue) {
        this.careValue = careValue;
    }

    /**
     * 対象年月日を取得する
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * 対象年月日を設定する
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 指導内容を取得する
     *
     * @return
     */
    public String getGuidContent() {
        return guidContent;
    }

    /**
     * 指導内容を設定する
     *
     * @param guidContent
     */
    public void setGuidContent(String guidContent) {
        this.guidContent = guidContent;
    }

    /**
     * 宿題内容を取得する
     *
     * @return
     */
    public String getHwkContent() {
        return hwkContent;
    }

    /**
     * 宿題内容を設定する
     *
     * @param hwkContent
     */
    public void setHwkContent(String hwkContent) {
        this.hwkContent = hwkContent;
    }

    /**
     * 小テスト単元名を取得する
     *
     * @return
     */
    public String getTestUnitName() {
        return testUnitName;
    }

    /**
     * 小テスト単元名を設定する
     *
     * @param testUnitName
     */
    public void setTestUnitName(String testUnitName) {
        this.testUnitName = testUnitName;
    }

    /**
     * 連絡事項内容を取得する
     *
     * @return
     */
    public String getConnectItemContent() {
        return connectItemContent;
    }

    /**
     * 連絡事項内容を設定する
     *
     * @param connectItemContent
     */
    public void setConnectItemContent(String connectItemContent) {
        this.connectItemContent = connectItemContent;
    }

    /**
     * 前回宿題完成区分を取得する
     *
     * @return
     */
    public String getLastHwkValue() {
        return lastHwkValue;
    }

    /**
     * 前回宿題完成区分を設定する
     *
     * @param lastHwkValue
     */
    public void setLastHwkValue(String lastHwkValue) {
        this.lastHwkValue = lastHwkValue;
    }

    /**
     * 進捗ステータス区分を取得する
     *
     * @return
     */
    public String getSchStsValue() {
        return schStsValue;
    }

    /**
     * 進捗ステータス区分を設定する
     *
     * @param schStsValue
     */
    public void setSchStsValue(String schStsValue) {
        this.schStsValue = schStsValue;
    }

    /**
     * 授業様子区分を取得する
     *
     * @return
     */
    public String getLectShapeValue() {
        return lectShapeValue;
    }

    /**
     * 授業様子区分を設定する
     *
     * @param lectShapeValue
     */
    public void setLectShapeValue(String lectShapeValue) {
        this.lectShapeValue = lectShapeValue;
    }

    /**
     * 出欠区分を取得する
     *
     * @return
     */
    public String getAbsStsValue() {
        return absStsValue;
    }

    /**
     * 出欠区分を設定する
     *
     * @param absStsValue
     */
    public void setAbsStsValue(String absStsValue) {
        this.absStsValue = absStsValue;
    }

    /**
     * 使用テキストを取得する
     *
     * @return useContent 使用テキスト
     */
    public String getUseContent() {
        return this.useContent;
    }

    /**
     * 使用テキストを設定する
     *
     * @param useContent 使用テキスト
     */
    public void setUseContent(String useContent) {
        this.useContent = useContent;
    }
}
