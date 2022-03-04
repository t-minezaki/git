package jp.learningpark.modules.manager.dto;

import java.io.Serializable;

public class F21008Dto implements Serializable {
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * グループ名
     */
    private String grpName;
    /**
     * 姓名_姓 + 姓名_名
     */
    private String stuNm;
    /**
     * 遅欠連絡ステータス
     */
    private String absSts;
    /**
     * 遅欠理由
     */
    private String absReason;
    /**
     * 遅刻時間(分)
     */
    private Integer lateTm;
    /**
     * 教科Div
     */
    private String subjtDiv;
    /**
     * 出欠Div
     */
    private String absStsDiv;
    /**
     * 宿題Div
     */
    private String homeWkDiv;
    /**
     * 小テスト
     */
    private Integer testPoints;
    /**
     * ケア
     */
    private String careDiv;
    /**
     * add at 2021/08/11 for V9.02 by NWT wen
     * 小テスト合否
     */
    private String testPassKbn;
    /**
     * 教科Cod
     */
    private String subjectCod;
    /**
     * 出欠Cod
     */
    private String absStsCod;
    /**
     * 宿題Cod
     */
    private String homeWkCod;
    /**
     * ケアCod
     */
    private String careCod;
    /**
     * add at 2021/08/11 for V9.02 by NWT wen
     * 小テスト合否Cod
     */
    private String testCod;
    /**
     * 表示項目
     */
    private String dspItems;
    /**
     * 表示しなければならない項目
     */
    private String mustItems;
    /**
     * 全項目
     */
    private String allItems;
    /**
     * 変更後ユーザーID
     */
    private String afterUsrId;
    /**
     * 学年区分
     */
    private String schyDiv;
    /**
     * 教科名
     */
    private String subjtName;
    /**
     * 予備１
     */
    private String yobi1;
    /**
     * 連絡区分
     */
    private String connectFlg;

    /**
     * 生徒IDを取得する
     *
     * @return stuId 生徒ID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * グループ名を取得する
     *
     * @return grpName グループ名
     */
    public String getGrpName() {
        return this.grpName;
    }

    /**
     * グループ名を設定する
     *
     * @param grpName グループ名
     */
    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }

    /**
     * 姓名_姓 + 姓名_名を取得する
     *
     * @return stuNm 姓名_姓 + 姓名_名
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 姓名_姓 + 姓名_名を設定する
     *
     * @param stuNm 姓名_姓 + 姓名_名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 遅欠連絡ステータスを取得する
     *
     * @return absSts 遅欠連絡ステータス
     */
    public String getAbsSts() {
        return this.absSts;
    }

    /**
     * 遅欠連絡ステータスを設定する
     *
     * @param absSts 遅欠連絡ステータス
     */
    public void setAbsSts(String absSts) {
        this.absSts = absSts;
    }

    /**
     * 遅欠理由を取得する
     *
     * @return absReason 遅欠理由
     */
    public String getAbsReason() {
        return this.absReason;
    }

    /**
     * 遅欠理由を設定する
     *
     * @param absReason 遅欠理由
     */
    public void setAbsReason(String absReason) {
        this.absReason = absReason;
    }

    /**
     * 遅刻時間(分)を取得する
     *
     * @return lateTm 遅刻時間(分)
     */
    public Integer getLateTm() {
        return this.lateTm;
    }

    /**
     * 遅刻時間(分)を設定する
     *
     * @param lateTm 遅刻時間(分)
     */
    public void setLateTm(Integer lateTm) {
        this.lateTm = lateTm;
    }

    /**
     * 教科を取得する
     *
     * @return subjtDiv 教科
     */
    public String getSubjtDiv() {
        return this.subjtDiv;
    }

    /**
     * 教科を設定する
     *
     * @param subjtDiv 教科
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }

    /**
     * 出欠を取得する
     *
     * @return absStsDiv 出欠
     */
    public String getAbsStsDiv() {
        return this.absStsDiv;
    }

    /**
     * 出欠を設定する
     *
     * @param absStsDiv 出欠
     */
    public void setAbsStsDiv(String absStsDiv) {
        this.absStsDiv = absStsDiv;
    }

    /**
     * 宿題を取得する
     *
     * @return homeWkDiv 宿題
     */
    public String getHomeWkDiv() {
        return this.homeWkDiv;
    }

    /**
     * 宿題を設定する
     *
     * @param homeWkDiv 宿題
     */
    public void setHomeWkDiv(String homeWkDiv) {
        this.homeWkDiv = homeWkDiv;
    }

    /**
     * 小テストを取得する
     *
     * @return testPoints 小テスト
     */
    public Integer getTestPoints() {
        return this.testPoints;
    }

    /**
     * 小テストを設定する
     *
     * @param testPoints 小テスト
     */
    public void setTestPoints(Integer testPoints) {
        this.testPoints = testPoints;
    }

    /**
     * ケアを取得する
     *
     * @return careDiv ケア
     */
    public String getCareDiv() {
        return this.careDiv;
    }

    /**
     * ケアを設定する
     *
     * @param careDiv ケア
     */
    public void setCareDiv(String careDiv) {
        this.careDiv = careDiv;
    }

    /**
     * 教科を取得する
     *
     * @return subjectCod 教科
     */
    public String getSubjectCod() {
        return this.subjectCod;
    }

    /**
     * 教科を設定する
     *
     * @param subjectCod 教科
     */
    public void setSubjectCod(String subjectCod) {
        this.subjectCod = subjectCod;
    }

    /**
     * 出欠を取得する
     *
     * @return absStsCod 出欠
     */
    public String getAbsStsCod() {
        return this.absStsCod;
    }

    /**
     * 出欠を設定する
     *
     * @param absStsCod 出欠
     */
    public void setAbsStsCod(String absStsCod) {
        this.absStsCod = absStsCod;
    }

    /**
     * 宿題を取得する
     *
     * @return homeWkCod 宿題
     */
    public String getHomeWkCod() {
        return this.homeWkCod;
    }

    /**
     * 宿題を設定する
     *
     * @param homeWkCod 宿題
     */
    public void setHomeWkCod(String homeWkCod) {
        this.homeWkCod = homeWkCod;
    }

    /**
     * ケアを取得する
     *
     * @return careCod ケア
     */
    public String getCareCod() {
        return this.careCod;
    }

    /**
     * ケアを設定する
     *
     * @param careCod ケア
     */
    public void setCareCod(String careCod) {
        this.careCod = careCod;
    }

    /**
     * 表示項目を取得する
     *
     * @return dspItems 表示項目
     */
    public String getDspItems() {
        return this.dspItems;
    }

    /**
     * 表示項目を設定する
     *
     * @param dspItems 表示項目
     */
    public void setDspItems(String dspItems) {
        this.dspItems = dspItems;
    }

    /**
     * 表示しなければならない項目を取得する
     *
     * @return mustItems 表示しなければならない項目
     */
    public String getMustItems() {
        return this.mustItems;
    }

    /**
     * 表示しなければならない項目を設定する
     *
     * @param mustItems 表示しなければならない項目
     */
    public void setMustItems(String mustItems) {
        this.mustItems = mustItems;
    }

    /**
     * 全項目を取得する
     *
     * @return allItems 全項目
     */
    public String getAllItems() {
        return this.allItems;
    }

    /**
     * 全項目を設定する
     *
     * @param allItems 全項目
     */
    public void setAllItems(String allItems) {
        this.allItems = allItems;
    }

    /**
     * 変更後ユーザーIDを取得する
     *
     * @return afterUsrId 変更後ユーザーID
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * 変更後ユーザーIDを設定する
     *
     * @param afterUsrId 変更後ユーザーID
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
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
     * 教科名を取得する
     *
     * @return subjtName 教科名
     */
    public String getSubjtName() {
        return this.subjtName;
    }

    /**
     * 教科名を設定する
     *
     * @param subjtName 教科名
     */
    public void setSubjtName(String subjtName) {
        this.subjtName = subjtName;
    }

    /**
     * 予備１を取得する
     *
     * @return yobi1 予備１
     */
    public String getYobi1() {
        return this.yobi1;
    }

    /**
     * 予備１を設定する
     *
     * @param yobi1 予備１
     */
    public void setYobi1(String yobi1) {
        this.yobi1 = yobi1;
    }

    /**
     * 連絡区分を取得する
     *
     * @return connectFlg 連絡区分
     */
    public String getConnectFlg() {
        return this.connectFlg;
    }

    /**
     * 連絡区分を設定する
     *
     * @param connectFlg 連絡区分
     */
    public void setConnectFlg(String connectFlg) {
        this.connectFlg = connectFlg;
    }

    /**
     * 小テスト合否を取得する
     *
     * @return testPassKbn 小テスト合否
     */
    public String getTestPassKbn() {
        return this.testPassKbn;
    }

    /**
     * 小テスト合否を設定する
     *
     * @param testPassKbn 小テスト合否
     */
    public void setTestPassKbn(String testPassKbn) {
        this.testPassKbn = testPassKbn;
    }

    /**
     * 小テスト合否Codを取得する
     * <p>
     * add at 2021/08/11 for V9.02 by NWT wen
     *
     * @return testCod 小テスト合否Cod
     */
    public String getTestCod() {
        return this.testCod;
    }

    /**
     * 小テスト合否Codを設定する
     * <p>
     * add at 2021/08/11 for V9.02 by NWT wen
     *
     * @param testCod 小テスト合否Cod
     */
    public void setTestCod(String testCod) {
        this.testCod = testCod;
    }
}
