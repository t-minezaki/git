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
 * @date 2019/11/21 9:21
 */
public class F21016GuardDto {
    /**
     * 保護者名
     */
    private String guardName;
    /**
     * 保護者カナ名
     */
    private String guardKnName;
    /**
     * 保護者ID
     */
    private String guardId;

    /**
     * 保護者名を取得する
     *
     * @return
     */
    public String getGuardName() {
        return guardName;
    }

    /**
     * 保護者名を設定する
     *
     * @param guardName
     */
    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    /**
     * 保護者カナ名を取得する
     *
     * @return
     */
    public String getGuardKnName() {
        return guardKnName;
    }

    /**
     * 保護者カナ名を設定する
     *
     * @param guardKnName
     */
    public void setGuardKnName(String guardKnName) {
        this.guardKnName = guardKnName;
    }

    /**
     * 保護者IDを取得する
     *
     * @return
     */
    public String getGuardId() {
        return guardId;
    }

    /**
     * 保護者IDを設定する
     *
     * @param guardId
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }
}
