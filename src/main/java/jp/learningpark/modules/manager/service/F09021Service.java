package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;

import java.util.List;

/**
 * <p>
 * F09021Service
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/28 ： NWT)hxl ： 新規作成
 * @date 2020/02/28 11:23
 */
public interface F09021Service {

    /**
     * 初期化
     *
     * @param brandCd   ブランドコード
     * @param orgId     組織ID
     * @param orgIdList 組織IDリスト
     * @return
     */
    R init(String brandCd, String orgId, List<String> orgIdList);

    /**
     * 生徒情報のクエリ
     *
     * @param orgIdList 編集した組織ID
     * @param grpId     指定したグループID
     * @param schyDiv   指定した学年区分
     * @return
     */
    R getStuList(List<String> orgIdList, Integer grpId, String schyDiv);
}
