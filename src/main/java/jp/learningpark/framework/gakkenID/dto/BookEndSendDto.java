package jp.learningpark.framework.gakkenID.dto;

import java.util.Date;

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
public class BookEndSendDto {
    /**
     * サービスコード
     */
    private String serviced;
    /**
     * 学研IDプライマリーキー
     */
    private String gidpk;
    /**
     * グループID
     */
    private String groupid;
    /**
     * 退会日
     */
    private Date withdrawaldate;

    /**
     * サービスコードを取得する
     *
     * @return serviced サービスコード
     */
    public String getServiced() {
        return this.serviced;
    }

    /**
     * サービスコードを設定する
     *
     * @param serviced サービスコード
     */
    public void setServiced(String serviced) {
        this.serviced = serviced;
    }

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
     * 退会日を取得する
     *
     * @return withdrawaldate 退会日
     */
    public Date getWithdrawaldate() {
        return this.withdrawaldate;
    }

    /**
     * 退会日を設定する
     *
     * @param withdrawaldate 退会日
     */
    public void setWithdrawaldate(Date withdrawaldate) {
        this.withdrawaldate = withdrawaldate;
    }
}
