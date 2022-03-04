/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;

import java.util.List;

/**
 * <p>F08002 イベント新規・編集画面 Dto</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/07/26 : wq: 新規<br />
 * @version 1.0
 */
public class F08002Dto extends MstEventEntity {

    /**
     * 排他チェックの利用更新日時
     */
    private String updateStrForCheck;

    /**
     * 画面．配信先組織リスト
     */
    private List<OrgAndLowerOrgIdDto> orgIdList;

    /**
     * 画面．配信先組織リストを取得する
     *
     * @return orgIdList 画面．配信先組織リスト
     */
    public List<OrgAndLowerOrgIdDto> getOrgIdList() {
        return this.orgIdList;
    }

    /**
     * 画面．配信先組織リストを設定する
     *
     * @param orgIdList 画面．配信先組織リスト
     */
    public void setOrgIdList(List<OrgAndLowerOrgIdDto> orgIdList) {
        this.orgIdList = orgIdList;
    }

    /**
     * 排他チェックの利用更新日時を取得する
     *
     * @return updateStrForCheck 排他チェックの利用更新日時
     */
    public String getUpdateStrForCheck() {
        return this.updateStrForCheck;
    }

    /**
     * 排他チェックの利用更新日時を設定する
     *
     * @param updateStrForCheck 排他チェックの利用更新日時
     */
    public void setUpdateStrForCheck(String updateStrForCheck) {
        this.updateStrForCheck = updateStrForCheck;
    }
}
