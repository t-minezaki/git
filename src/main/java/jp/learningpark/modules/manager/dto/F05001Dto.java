/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;


import jp.learningpark.modules.common.entity.MstNoticeEntity;

/**
 * <p>F05001_知らせ一覧画面 Dto</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/03/12 : tan: 新規<br />
 * @version 1.0
 */
public class F05001Dto extends MstNoticeEntity {

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
