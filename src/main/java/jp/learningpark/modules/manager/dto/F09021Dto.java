package jp.learningpark.modules.manager.dto;

/**
 * <p>
 * F09021Dto
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/28 ： NWT)hxl ： 新規作成
 * @date 2020/02/28 11:22
 */
public class F09021Dto {

    /**
     * 組織ＩＤ
     */
    private String orgId;

    /**
     * 組織名
     */
    private String orgNm;

    /**
     * 本組織区分
     */
    private String orgDiv;

    /**
     * 階層
     */
    private Integer level;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * 生徒姓名
     */
    private String stuNm;

    /**
     * 学年区分
     */
    private String schyDiv;

    /**
     * 学年
     */
    private String schy;

    /**
     * 組織ＩＤを取得する
     *
     * @return orgId 組織ＩＤ
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織ＩＤを設定する
     *
     * @param orgId 組織ＩＤ
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 組織名を取得する
     *
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織名を設定する
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * 本組織区分を取得する
     *
     * @return orgDiv 本組織区分
     */
    public String getOrgDiv() {
        return this.orgDiv;
    }

    /**
     * 本組織区分を設定する
     *
     * @param orgDiv 本組織区分
     */
    public void setOrgDiv(String orgDiv) {
        this.orgDiv = orgDiv;
    }

    /**
     * 階層を取得する
     *
     * @return level 階層
     */
    public Integer getLevel() {
        return this.level;
    }

    /**
     * 階層を設定する
     *
     * @param level 階層
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

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
     * 生徒姓名を取得する
     *
     * @return stuNm 生徒姓名
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 生徒姓名を設定する
     *
     * @param stuNm 生徒姓名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
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
}
