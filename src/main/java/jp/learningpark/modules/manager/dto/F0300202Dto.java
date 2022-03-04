/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>F03002_教科書単元編集画面 --生徒登録リスト</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/3/28 : gong: 新規<br />
 * @version 1.0
 */
public class F0300202Dto {

    /**
     * 生徒登録
     */
    private String stuId;

    /**
     * 計画済み件数
     */
    private Integer planedCount;

    /**
     * 生徒登録を設定する
     *
     * @return stuId 生徒登録
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒登録を取得する
     *
     * @param stuId 生徒登録
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 計画済み件数を設定する
     *
     * @return planedCount 計画済み件数
     */
    public Integer getPlanedCount() {
        return this.planedCount;
    }

    /**
     * 計画済み件数を取得する
     *
     * @param planedCount 計画済み件数
     */
    public void setPlanedCount(Integer planedCount) {
        this.planedCount = planedCount;
    }
}
