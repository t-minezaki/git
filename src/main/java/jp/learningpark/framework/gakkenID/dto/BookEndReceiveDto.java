package jp.learningpark.framework.gakkenID.dto;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/3/22 ： NWT)hxl ： 新規作成
 * @date 2021/3/22 16:35
 */
public class BookEndReceiveDto {
    /**
     * 学研IDプライマリーキー
     */
    private String gidpk;
    /**
     * グループID
     */
    private String groupid;
    /**
     * 結果
     */
    private String result;

    /**
     * 学研IDプライマリーキーを取得する
     *
     * @return gidpk 学研IDプライマリーキー
     */
    public String getGidpk() {
        return this.gidpk;
    }

    /**
     * 学研IDプライマリーキーを設定する
     *
     * @param gidpk 学研IDプライマリーキー
     */
    public void setGidpk(String gidpk) {
        this.gidpk = gidpk;
    }

    /**
     * グループIDを取得する
     *
     * @return groupid グループID
     */
    public String getGroupid() {
        return this.groupid;
    }

    /**
     * グループIDを設定する
     *
     * @param groupid グループID
     */
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    /**
     * 結果を取得する
     *
     * @return result 結果
     */
    public String getResult() {
        return this.result;
    }

    /**
     * 結果を設定する
     *
     * @param result 結果
     */
    public void setResult(String result) {
        this.result = result;
    }
}
