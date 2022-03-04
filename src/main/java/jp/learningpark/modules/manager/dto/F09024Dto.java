package jp.learningpark.modules.manager.dto;

/**
 * <p>入退室履歴画面</p >
 *
 * @author NWT : wq <br />
 * <p>
 * 2021/08/18
 * @version 1.0
 */
public class F09024Dto {

    /**
     * 编号
     */
    private Integer entryNum;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * 生徒名
     */
    private String stuName;

    /**
     * 学年
     */
    private String schyDiv;

    /**
     * 入室時間
     */
    private String entryTime;

    /**
     * 退室時間
     */
    private String exitTime;

    /**
     * 编号を取得する
     *
     * @return entryNum 编号
     */
    public Integer getEntryNum() {
        return this.entryNum;
    }

    /**
     * 编号を設定する
     *
     * @param entryNum 编号
     */
    public void setEntryNum(Integer entryNum) {
        this.entryNum = entryNum;
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
     * 生徒名を取得する
     *
     * @return stuName 生徒名
     */
    public String getStuName() {
        return this.stuName;
    }

    /**
     * 生徒名を設定する
     *
     * @param stuName 生徒名
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * 学年を取得する
     *
     * @return schyDiv 学年
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * 学年を設定する
     *
     * @param schyDiv 学年
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * 入室時間を取得する
     *
     * @return entryTime 入室時間
     */
    public String getEntryTime() {
        return this.entryTime;
    }

    /**
     * 入室時間を設定する
     *
     * @param entryTime 入室時間
     */
    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    /**
     * 退室時間を取得する
     *
     * @return exitTime 退室時間
     */
    public String getExitTime() {
        return this.exitTime;
    }

    /**
     * 退室時間を設定する
     *
     * @param exitTime 退室時間
     */
    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }
}
