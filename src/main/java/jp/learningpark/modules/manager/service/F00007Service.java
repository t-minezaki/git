/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F00007Dto;
import jp.learningpark.modules.manager.dto.F00007ExportDto;

import java.util.List;
import java.util.Map;

/**
 * <p>生徒集計画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/01/10 : hujunjie: 新規<br />
 * @version 1.0
 */
public interface F00007Service {
    /**
     * 属する下位組織情報を取得
     *
     * @param brandCd      ブランドコード
     * @param upplevOrgId  条件設定用上位組織
     * @return
     */
    List<F00007Dto> selectLowerLevOrg(String brandCd, String upplevOrgId);

    /**
     * 集計人数リスト取得
     *
     * @param list 下層組織ID
     * @return
     */
    Map<String, String> selectUsrCount(List<F00007Dto> list);

    /**
     * エクスポートデータ取得
     *
     * @param list
     * @return
     */
    List<F00007ExportDto> exportFile(List<F00007Dto> list);
}
