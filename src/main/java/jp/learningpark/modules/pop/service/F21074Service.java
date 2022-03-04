package jp.learningpark.modules.pop.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.pop.dto.F21074Dto;

import java.util.List;

/**
 * <p>配信先選択画面（POP） Service</p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/06/04 ： NWT)hxl ： 新規作成
 */
public interface F21074Service {

    /**
     * <p>ユーザー情報を取得する。</p>
     *
     * @param type ユーザー区分
     * @param searchName 　画面入力した名
     * @param loginId 画面入力した登録ID
     * @param orgIdList 　組織IDリスト
     * @return Result<List>
     */
    R getEntity(String type, String searchName, String loginId, List<String> orgIdList);

    /**
     * <p>ユーザー情報数を取得する。</p>
     *
     * @param orgIdList 組織IDList
     * @param loginId 画面入力した名
     * @param searchName 画面入力した名
     * @return Result<Integer>
     */
    Integer selectUserCount(String searchName, String loginId, List<String> orgIdList);
}
