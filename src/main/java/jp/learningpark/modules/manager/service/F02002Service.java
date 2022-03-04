/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstOrgEntity;

import java.util.List;

/**
 * F00002 F02002_単元情報検索一覧画面
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/20 : xiong: 新規<br />
 * @version 1.0
 */
public interface F02002Service {

    /**
     * 単元マスタから論理削除
     * @param unitId     単元マスタ．ＩＤ
     * @param updateTime 排他
     * @return
     */
    R deleteUnit(Integer unitId,String updateTime);

    /**
     * 全部の下層組織
     * @param orgId 組織ID
     * @return
     */
    List<MstOrgEntity> getLowerOrg(String orgId);

    /**
     * 全部の上層組織
     * @param orgId 組織ID
     * @return
     */
    List<MstOrgEntity> getUpOrg(String orgId);
}
