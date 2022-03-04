package jp.learningpark.modules.manager.dto;

import java.io.Serializable;

public class F21009Dto implements Serializable {
    /**
     * 指導報告書ヘーダ・ID
     */
    private Integer id;
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * 姓名_姓 + 姓名_名
     */
    private String stuNm;
    /**
     * 出欠ステータス区分
     */
    private String absStsDiv;
    /**
     * 出欠ステータスCod
     */
    private String absStsCod;

    /**
     * 使用テキスト
     */
    private String useCont;

    /**
     * 指導内容
     */
    private String guidCont;

    /**
     * 宿題内容
     */
    private String hwkCont;

    /**
     * 小テスト単元名
     */
    private String testUnitNm;

    /**
     * 前回宿題完成区分
     */
    private String lastTimeHwkDiv;
    /**
     * 前回宿題完成Cod
     */
    private String lastTimeHwkCod;

    /**
     * 進捗ステータス区分
     */
    private String schStsDiv;
    /**
     * 進捗ステータスCod
     */
    private String schStsCod;

    /**
     * 授業様子区分
     */
    private String lectShapeDiv;
    /**
     * 授業様子Cod
     */
    private String lectShapeCod;

    /**
     * 連絡事項内容
     */
    private String concItemCont;
    /**
     * 変更後ユーザーID
     */
    private String afterUsrId;

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
     * 出欠ステータス区分を取得する
     *
     * @return absStsDiv 出欠ステータス区分
     */
    public String getAbsStsDiv() {
        return this.absStsDiv;
    }

    /**
     * 出欠ステータス区分を設定する
     *
     * @param absStsDiv 出欠ステータス区分
     */
    public void setAbsStsDiv(String absStsDiv) {
        this.absStsDiv = absStsDiv;
    }

    /**
     * 指導内容を取得する
     *
     * @return guidCont 指導内容
     */
    public String getGuidCont() {
        return this.guidCont;
    }

    /**
     * 指導内容を設定する
     *
     * @param guidCont 指導内容
     */
    public void setGuidCont(String guidCont) {
        this.guidCont = guidCont;
    }

    /**
     * 宿題内容を取得する
     *
     * @return hwkCont 宿題内容
     */
    public String getHwkCont() {
        return this.hwkCont;
    }

    /**
     * 宿題内容を設定する
     *
     * @param hwkCont 宿題内容
     */
    public void setHwkCont(String hwkCont) {
        this.hwkCont = hwkCont;
    }

    /**
     * 小テスト単元名を取得する
     *
     * @return testUnitNm 小テスト単元名
     */
    public String getTestUnitNm() {
        return this.testUnitNm;
    }

    /**
     * 小テスト単元名を設定する
     *
     * @param testUnitNm 小テスト単元名
     */
    public void setTestUnitNm(String testUnitNm) {
        this.testUnitNm = testUnitNm;
    }

    /**
     * 前回宿題完成区分を取得する
     *
     * @return lastTimeHwkDiv 前回宿題完成区分
     */
    public String getLastTimeHwkDiv() {
        return this.lastTimeHwkDiv;
    }

    /**
     * 前回宿題完成区分を設定する
     *
     * @param lastTimeHwkDiv 前回宿題完成区分
     */
    public void setLastTimeHwkDiv(String lastTimeHwkDiv) {
        this.lastTimeHwkDiv = lastTimeHwkDiv;
    }

    /**
     * 進捗ステータス区分を取得する
     *
     * @return schStsDiv 進捗ステータス区分
     */
    public String getSchStsDiv() {
        return this.schStsDiv;
    }

    /**
     * 進捗ステータス区分を設定する
     *
     * @param schStsDiv 進捗ステータス区分
     */
    public void setSchStsDiv(String schStsDiv) {
        this.schStsDiv = schStsDiv;
    }

    /**
     * 授業様子区分を取得する
     *
     * @return lectShapeDiv 授業様子区分
     */
    public String getLectShapeDiv() {
        return this.lectShapeDiv;
    }

    /**
     * 授業様子区分を設定する
     *
     * @param lectShapeDiv 授業様子区分
     */
    public void setLectShapeDiv(String lectShapeDiv) {
        this.lectShapeDiv = lectShapeDiv;
    }

    /**
     * 連絡事項内容を取得する
     *
     * @return concItemCont 連絡事項内容
     */
    public String getConcItemCont() {
        return this.concItemCont;
    }

    /**
     * 連絡事項内容を設定する
     *
     * @param concItemCont 連絡事項内容
     */
    public void setConcItemCont(String concItemCont) {
        this.concItemCont = concItemCont;
    }

    /**
     * 出欠ステータスCodを取得する
     *
     * @return absStsCod 出欠ステータスCod
     */
    public String getAbsStsCod() {
        return this.absStsCod;
    }

    /**
     * 出欠ステータスCodを設定する
     *
     * @param absStsCod 出欠ステータスCod
     */
    public void setAbsStsCod(String absStsCod) {
        this.absStsCod = absStsCod;
    }

    /**
     * 前回宿題完成Codを取得する
     *
     * @return lastTimeHwkCod 前回宿題完成Cod
     */
    public String getLastTimeHwkCod() {
        return this.lastTimeHwkCod;
    }

    /**
     * 前回宿題完成Codを設定する
     *
     * @param lastTimeHwkCod 前回宿題完成Cod
     */
    public void setLastTimeHwkCod(String lastTimeHwkCod) {
        this.lastTimeHwkCod = lastTimeHwkCod;
    }

    /**
     * 進捗ステータスCodを取得する
     *
     * @return schStsCod 進捗ステータスCod
     */
    public String getSchStsCod() {
        return this.schStsCod;
    }

    /**
     * 進捗ステータスCodを設定する
     *
     * @param schStsCod 進捗ステータスCod
     */
    public void setSchStsCod(String schStsCod) {
        this.schStsCod = schStsCod;
    }

    /**
     * 授業様子Codを取得する
     *
     * @return lectShapeCod 授業様子Cod
     */
    public String getLectShapeCod() {
        return this.lectShapeCod;
    }

    /**
     * 授業様子Codを設定する
     *
     * @param lectShapeCod 授業様子Cod
     */
    public void setLectShapeCod(String lectShapeCod) {
        this.lectShapeCod = lectShapeCod;
    }

    /**
     * 指導報告書ヘーダ・IDを取得する
     *
     * @return id 指導報告書ヘーダ・ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 指導報告書ヘーダ・IDを設定する
     *
     * @param id 指導報告書ヘーダ・ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 使用テキストを取得する
     *
     * @return useCont 使用テキスト
     */
    public String getUseCont() {
        return this.useCont;
    }

    /**
     * 使用テキストを設定する
     *
     * @param useCont 使用テキスト
     */
    public void setUseCont(String useCont) {
        this.useCont = useCont;
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
}
