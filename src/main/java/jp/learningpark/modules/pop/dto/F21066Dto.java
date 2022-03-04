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
 * 2020/05/22 ： NWT)hxl ： 新規作成
 * @date 2020/05/22 11:56
 */
public class F21066Dto {
    /**
     * 生徒ＩＤ
     */
    String stuId;
    /**
     * 生徒氏名
     */
    String stunm;

    /**
     * 生徒ＩＤを取得する
     *
     * @return stuId 生徒ＩＤ
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒ＩＤを設定する
     *
     * @param stuId 生徒ＩＤ
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 生徒氏名を取得する
     *
     * @return stunm 生徒氏名
     */
    public String getStunm() {
        return this.stunm;
    }

    /**
     * 生徒氏名を設定する
     *
     * @param stunm 生徒氏名
     */
    public void setStunm(String stunm) {
        this.stunm = stunm;
    }
}
