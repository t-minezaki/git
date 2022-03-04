package jp.learningpark.modules.manager.dto;

import java.io.Serializable;

/**
 * <p>
 * F09001ページエンティティクラス
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/12 ： NWT)hxl ： 新規作成
 * @date 2019/11/12 9:47
 */
public class F09001HistoryDto implements Serializable {
    /**
     * 組織名
     */
    private String orgName;
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * 変更されたID
     */
    private String afterUsrId;
    /**
     * 生徒名
     */
    private String stuName;
    /**
     * 学年区分
     */
    private String schyDiv;
    /**
     * 入校日時
     */
    private String entryTime;
    /**
     * 下校日時
     */
    private String exitTime;

    /**
     * 組織名を取得する
     * @return
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 組織名を設定する
     * @param orgName
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 生徒IDを取得する
     * @return
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * 生徒IDを設定する
     * @param stuId
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 生徒名を取得する
     * @return
     */
    public String getStuName() {
        return stuName;
    }

    /**
     * 生徒名を設定する
     * @param stuName
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * 学年区分を取得する
     * @return
     */
    public String getSchyDiv() {
        return schyDiv;
    }

    /**
     * 学年区分を設定する
     * @param schyDiv
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * 入校日時を取得する
     * @return
     */
    public String getEntryTime() {
        return entryTime;
    }

    /**
     * 入校日時を設定する
     * @param entryTime
     */
    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    /**
     * 下校日時を取得する
     * @return
     */
    public String getExitTime() {
        return exitTime;
    }

    /**
     * 下校日時を設定する
     * @param exitTime
     */
    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    /**
     * 変更されたIDを取得する
     * @return
     */
    public String getAfterUsrId() {
        return afterUsrId;
    }

    /**
     * 変更されたIDを設定する
     * @param afterUsrId
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }
}
