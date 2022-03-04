/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F00009Dto;

import java.util.List;

/**
 * <p>F00009_組織一覧画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/20 : gong: 新規<br />
 * @version 1.0
 */
public interface F00009Service {
    /**
     * <p>下位組織一覧情報</p>
     *
     * @param orgId 条件設定用上位組織
     * @param brandCd ブランドコード
     * @return
     */
    List<F00009Dto> getLowerOrgList(String orgId, String brandCd);

    /**
     * 対応する組織を組織マスタから論理削除処理行う。
     *
     * @param id
     * @return
     */
    R updateById(Integer id);

    /**
     * 下位組織一覧取得
     *
     * @param orgId
     * @param brandCd
     * @param orgIdList
     * @param limit
     * @param page
     * @return
     */
    List<F00009Dto> lowerOrgList(String orgId, String brandCd, List<String> orgIdList, String currentOrgId, String orgNm, String upLevOrgId, Integer mgrFlg, Integer level, Integer limit, Integer page);

    /**
     * 下位組織一覧取得
     *
     * @param orgId
     * @param brandCd
     * @param orgIdList
     * @return
     */
    Integer lowerOrgCount(String orgId, String brandCd, List<String> orgIdList, String currentOrgId, String orgNm, String upLevOrgId, Integer mgrFlg, Integer level);

    /**
     * ＱＲログインデータ
     *
     * @param orgIdList
     * @return
     */
    List<F00009Dto> getQrUser(List<String> orgIdList);

    List<F00009Dto> getAftUsrIds(List<String> orgIdList);
}
