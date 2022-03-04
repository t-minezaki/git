
package jp.learningpark.modules.manager.dto;


public class F07011Dto  {
    /**
     * 学年
     */
    private String schy;
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
