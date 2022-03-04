package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstMessageEntity;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/19 ： NWT)hxl ： 新規作成
 * @date 2020/05/19 18:00
 */
public class F21062Dto extends MstMessageEntity {
    /**
     * 更新日時
     */
    private String updDatimeStr;

    /**
     * 本組織区分
     */
    private String orgFlg;

    /**
     * 更新日時を取得する
     *
     * @return updDatimeStr 更新日時
     */
    public String getUpdDatimeStr() {
        return this.updDatimeStr;
    }

    /**
     * 更新日時を設定する
     *
     * @param updDatimeStr 更新日時
     */
    public void setUpdDatimeStr(String updDatimeStr) {
        this.updDatimeStr = updDatimeStr;
    }

    /**
     * 本組織区分を取得する
     *
     * @return orgFlg 本組織区分
     */
    public String getOrgFlg() {
        return this.orgFlg;
    }

    /**
     * 本組織区分を設定する
     *
     * @param orgFlg 本組織区分
     */
    public void setOrgFlg(String orgFlg) {
        this.orgFlg = orgFlg;
    }
}
