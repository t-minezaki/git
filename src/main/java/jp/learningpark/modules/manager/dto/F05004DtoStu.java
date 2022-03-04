/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>F05004_知らせ照会画面 Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/3/14 : gong: 新規<br />
 * 2019/06/18 : hujiaxing: mod<br />
 * @version 1.0
 */
public class F05004DtoStu {
    /**
     * student id
     */
    private String stuId;
    /**
     * student after id
     */
    private String afterUserId;
    /**
     * student name
     */
    private String stuName;
    /**
     * guard id
     */
    private String guaId;
    /**
     * guard name
     */
    private String guaName;

    /**
     * stuIdを設定する
     * @param stuId student id
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * afterUserIdを取得する
     * @param afterUserId student after id
     */
    public void setAfterUserId(String afterUserId) {
        this.afterUserId = afterUserId;
    }

    /**
     * stuNameを設定する
     * @param stuName student name
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * guard idを設定する
     * @param guaId guard id
     */
    public void setGuaId(String guaId) {
        this.guaId = guaId;
    }

    /**
     * guaNameを設定する
     * @param guaName guard name
     */
    public void setGuaName(String guaName) {
        this.guaName = guaName;
    }

    /**
     * student idを取得する
     * @return
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * student after idを取得する
     * @return
     */
    public String getAfterUserId() {
        return afterUserId;
    }

    /**
     * student nameを取得する
     * @return
     */
    public String getStuName() {
        return stuName;
    }

    /**
     * guard idを取得する
     * @return
     */
    public String getGuaId() {
        return guaId;
    }

    /**
     * guard nameを取得する
     * @return
     */
    public String getGuaName() {
        return guaName;
    }
}
