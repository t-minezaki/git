/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.manager.dto.F20001Dto;

import java.util.List;
import java.util.Map;

/**
 * <p>F20001_生徒一覧画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/05 : gong: 新規<br />
 * @version 1.0
 */
public interface F20001Service {
    /**
     * <p>生徒情報を取得し、一覧画面に表示する</p>
     *
     * @param map 条件
     * @return
     */
    List<F20001Dto> getInfo(Map<String, Object> map);

    /**
     * <p>ログインユーザが「管理者」の場合
     * 生徒情報を取得し、一覧画面に表示する</p>
     * @param map
     * @return
     */
    List<F20001Dto> getManagerStu(Map<String, Object> map);

    /**
     * <p>塾学習期間IDの取得</p>
     *
     * @param orgId 塾ID
     * @param stuId 生徒ID
     * @return
     */
    MstCrmschLearnPrdEntity getCrmLearnPrdId(String orgId, String stuId);

    /**
     * <p>ログインユーザーの権利を取得する</p>
     *
     * @param userId ログインユーザーID
     * @return
     */
//    List<String> getPermission(String userId);
}
