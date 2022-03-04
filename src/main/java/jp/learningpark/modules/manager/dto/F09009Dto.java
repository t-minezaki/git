package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstVariousSetEntity;

public class F09009Dto extends MstVariousSetEntity {
    /**
     * 日付を更新
     */
    // 2020/11/9 zhangminghao modify start
    private String updateStr;
    // 2020/11/9 zhangminghao modify end
    /**
     * 更新日時チェック
     */
    // 2020/11/9 zhangminghao modify start
    private String updateStrCheck;
    // 2020/11/9 zhangminghao modify end

    public String getUpdateStr() {
        return updateStr;
    }

    public void setUpdateStr(String updateStr) {
        this.updateStr = updateStr;
    }

    public String getUpdateStrCheck() {
        return updateStrCheck;
    }

    public void setUpdateStrCheck(String updateStrCheck) {
        this.updateStrCheck = updateStrCheck;
    }
}
