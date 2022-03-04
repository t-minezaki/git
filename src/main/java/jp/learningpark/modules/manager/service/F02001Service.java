/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F02001Dto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>F02001 単元情報インポート・エクスポート画面 Service</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/01/08 : wen: 新規<br />
 * @version 1.0
 */
public interface F02001Service {

    /**
     * <p>単元情報を取得する</p>
     *
     * @param orgId      組織ID
     * @param schyDiv    学年コード
     * @param subjtDiv   教科コード
     * @param upOrgId    上層組織リスト
     * @param lowerOrgId 下層組織リスト
     * @return 単元情報
     */
    List<F02001Dto> getMstUnitInfo(String orgId, String schyDiv, String subjtDiv, List<String> upOrgId, List<String> lowerOrgId);

    /**
     * <p>インポート</p>
     *
     * @param file ファイル
     * @param type 新規・修正（エラーとする）または新規・修正（上書き）選択フラッグ
     * @return
     */
    R importFile(MultipartFile file, Integer type);

    /**
     * <p>下層組織リストを取得する</p>
     *
     * @param orgId 組織ID
     * @return
     */
    List getLowerOrg(String orgId);

    /**
     * <p>上層組織リストを取得する</p>
     *
     * @param orgId 組織ID
     * @return
     */
    List getUpOrg(String orgId);
}
