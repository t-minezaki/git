package jp.learningpark.modules.manager.dto;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/28 ： NWT)hxl ： 新規作成
 * @date 2019/11/28 18:26
 */
public class F21012Dto {
    /**
     * 対象者リスト．生徒ID
     */
    private String stuId;
    /**
     * 指導報告書明細．指導報告書ID
     */
    private Integer guiRepId;
    /**
     * 保護者指導報告書申込状況．ID
     */
    private Integer guidReprApplyStsId;
    /**
     * 教科名
     */
    private String codValue;

    /**
     * 対象者リスト．生徒IDを取得する
     *
     * @return
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * 対象者リスト．生徒IDを設定する
     *
     * @param stuId
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 指導報告書明細．指導報告書IDを取得する
     *
     * @return
     */
    public Integer getGuiRepId() {
        return guiRepId;
    }

    /**
     * 指導報告書明細．指導報告書IDを設定する
     *
     * @param guiRepId
     */
    public void setGuiRepId(Integer guiRepId) {
        this.guiRepId = guiRepId;
    }

    /**
     * 保護者指導報告書申込状況．IDを取得する
     *
     * @return
     */
    public Integer getGuidReprApplyStsId() {
        return guidReprApplyStsId;
    }

    /**
     * 保護者指導報告書申込状況．IDを設定する
     *
     * @param guidReprApplyStsId
     */
    public void setGuidReprApplyStsId(Integer guidReprApplyStsId) {
        this.guidReprApplyStsId = guidReprApplyStsId;
    }

    /**
     * 教科名を取得する
     *
     * @return
     */
    public String getCodValue() {
        return codValue;
    }

    /**
     * 教科名を設定する
     *
     * @param codValule
     */
    public void setCodValue(String codValule) {
        this.codValue = codValule;
    }
}
