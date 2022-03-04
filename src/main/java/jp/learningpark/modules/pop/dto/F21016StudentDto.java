package jp.learningpark.modules.pop.dto;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/21 ： NWT)hxl ： 新規作成
 * @date 2019/11/21 13:24
 */
public class F21016StudentDto {
    /**
     * 生徒名
     */
    private String stuName;
    /**
     * 生徒カナ名
     */
    private String stuKnName;
    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * 生徒名を取得する
     *
     * @return
     */
    public String getStuName() {
        return stuName;
    }

    /**
     * 生徒名を設定する
     *
     * @param stuName
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * 生徒カナ名を取得する
     *
     * @return
     */
    public String getStuKnName() {
        return stuKnName;
    }

    /**
     * 生徒カナ名を設定する
     *
     * @param stuKnName
     */
    public void setStuKnName(String stuKnName) {
        this.stuKnName = stuKnName;
    }

    /**
     * 生徒IDを取得する
     *
     * @return
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param stuId
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
}
