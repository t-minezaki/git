/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.manager.dto.F20001Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>F20001_生徒一覧画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/05 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F20001Dao {
    /**
     * <p>生徒情報を取得し、一覧画面に表示する</p>
     *
     * @param map 条件
     * @return
     */
    List<F20001Dto> selectInfo(Map<String, Object> map);
    /**
     * <p>ログインユーザが「管理者」の場合
     * 生徒情報を取得し、一覧画面に表示する</p>
     * orgId 組織ID
     *  * @return
     */
    List<F20001Dto> getManagerStu(Map<String, Object> map);

    /**
     * <p>塾学習期間IDの取得</p>
     *
     * @param orgId 塾ID
     * @param stuId 生徒ID
     * @return
     */
    MstCrmschLearnPrdEntity selectCrmLearnPrdId(@Param("orgId") String orgId, @Param("stuId") String stuId);

    /**
     * <p>個人の許可テーブルから許可情報を取得する</>
     *
     * @param userId
     * @return
     */
    List<String> getPermissionFromMstUserNmFunList(@Param("user_id") String userId);

    /**
     * <p>組織の許可テーブルから許可情報を取得する</p>
     *
     * @param userId
     * @return
     */
    List<String> getPermissionFromMstOrgFunList(@Param("user_id") String userId);
}
