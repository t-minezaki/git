
package jp.learningpark.modules.manager.dto;

/**
 * <p>F07012 成績教科追加・編集画面 Dto</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/04/26: yang: 新規<br />
 * @version 1.0
 */
public class F07012Dto {
    /**
     * 学年
     */

    private String schy;

    /**
     * 学年ID
     */

    private Integer schyId;
    /**
     * 教科CD
     */
    private String codCd;
    /**
     * 教科名
     */
    private String subName;
    /**
     * 背景色
     */
    private String backGround;
    /*
     * 表示用画像パス
     */
    private String codCont;
    /**
     * ソート
     */
    private Integer sort;

    public String getSchy() {
        return schy;
    }

    public void setSchy(String schy) {
        this.schy = schy;
    }
    public Integer getSchyId() {
        return schyId;
    }

    public void setSchyId(Integer schyId) {
        this.schyId = schyId;
    }

    public String getCodCd() {
        return codCd;
    }

    public void setCodCd(String codCd) {
        this.codCd = codCd;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }

    public String getCodCont() {
        return codCont;
    }

    public void setCodCont(String codCont) {
        this.codCont = codCont;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
