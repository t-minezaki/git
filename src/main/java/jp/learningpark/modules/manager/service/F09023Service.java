/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F09023Dto;

import java.util.List;

/**
 * <p>既存利用者ログインID同報設定画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/08/03 : wq: 新規<br />
 * @version 1.0
 */
public interface F09023Service {

    /**
     * @param roleDiv 　ロール区分
     * @param orgId 　組織ID
     * @param afterUsrId 　変更後ユーザーID
     * @return
     */
    List<F09023Dto> getRoleDataList(String roleDiv, String checkRole, String orgId, String afterUsrId);

    /**
     *
     * @param roleData
     * @return
     */
    R DBLogin(List<F09023Dto> roleData);

    /**
     * ログインIDをもとに、組織マスタから該当ログインIDに対するブランドを取得する。
     *
     * @param userId セッションデータ ログインID
     * @param brandCd セッションデータ 組織ID
     * @return 組織マスタ ブランドコード（取得したブランドコード１）
     */
    String getBrandCode(String userId, String brandCd);
}
