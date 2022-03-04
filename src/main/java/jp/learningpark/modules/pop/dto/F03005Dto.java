package jp.learningpark.modules.pop.dto;

import jp.learningpark.modules.common.entity.MstUnitEntity;

/**
 * <p>F03005_単元追加・編集画面 Dto</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/20 : wen: 新規<br />
 * @version 1.0
 */
public class F03005Dto extends MstUnitEntity {

    /**
     * 更新日時
     */
    private String updateTimeStr;

    /**
     * を取得する
     *
     * @return updateTimeStr
     */
    public String getUpdateTimeStr() {
        return this.updateTimeStr;
    }

    /**
     * を設定する
     *
     * @param updateTimeStr
     */
    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }
}
