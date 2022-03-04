/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F09023Dto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>既存利用者ログインID同報設定画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/08/03 : wq: 新規<br />
 * @version 1.0
 */
public interface F09023Dao {

    /**
     * @param roleDiv    　ロール区分
     * @param orgId      　組織ID
     * @param afterUsrId 　変更後ユーザーID
     */
    List<F09023Dto> getRoleDataList(
            @Param("roleDiv") String roleDiv,
            @Param("checkRole") String checkRole,
            @Param("orgId") String orgId, @Param("afterUsrId") String afterUsrId);

    /**
     * ログインIDをもとに、組織マスタから該当ログインIDに対するブランドを取得する。
     *
     * @param userId  セッションデータ ログインID
     * @param orgId セッションデータ 組織ID
     * @return 組織マスタ ブランドコード（取得したブランドコード１）
     */
    String getBrandCode(@Param("userId") String userId, @Param("orgId") String orgId);
}
