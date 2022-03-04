/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F00009Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F00009_組織一覧画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/20 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00009Dao {
    /**
     * <p>下位組織一覧情報</p>
     *
     * @param orgId   条件設定用上位組織
     * @param brandCd ブランドコード
     * @return
     */
    List<F00009Dto> selectLowerOrgList(@Param("orgId") String orgId, @Param("brandCd") String brandCd);

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
    List<F00009Dto> lowerOrgList(
            @Param("orgId") String orgId,
            @Param("brandCd") String brandCd,
            @Param("orgIdList") List<String> orgIdList,
            @Param("currentOrgId") String currentOrgId,
            @Param("orgNm") String orgNm,
            @Param("upLevOrgId") String upLevOrgId,
            @Param("mgrFlg") Integer mgrFlg, @Param("level") Integer level, @Param("limit") Integer limit, @Param("page") Integer page);

    /**
     * 下位組織一覧取得
     *
     * @param orgId
     * @param brandCd
     * @param orgIdList
     * @return
     */
    Integer lowerOrgCount(
            @Param("orgId") String orgId,
            @Param("brandCd") String brandCd,
            @Param("orgIdList") List<String> orgIdList,
            @Param("currentOrgId") String currentOrgId,
            @Param("orgNm") String orgNm, @Param("upLevOrgId") String upLevOrgId, @Param("mgrFlg") Integer mgrFlg, @Param("level") Integer level);

    /**
     * ＱＲログインデータ
     *
     * @param orgIdList
     * @return
     */
    List<F00009Dto> getQrUser(@Param("orgIdList") List<String> orgIdList);

    List<F00009Dto> getAftUsrIds(@Param("orgIdList") List<String> orgIdList);

    /**
     * 管理者基本マスタデータを削除する
     *
     * @param orgId
     */
    void updateMgr(@Param("orgId") String orgId);

    /**
     * メンター基本マスタデータを削除する
     *
     * @param orgId
     */
    void updateMen(@Param("orgId") String orgId);

    /**
     * 保護者基本マスタデータを削除する
     *
     * @param orgId
     */
    void updateGuard(@Param("orgId") String orgId);

    /**
     * 生徒基本マスタデータを削除する
     *
     * @param orgId
     */
    void updateStu(@Param("orgId") String orgId);

    /**
     * ユーザーキャラデータを削除する。　※物理削除
     * @param orgId
     */
    void deleteSysUserRole(@Param("orgId") String orgId);

}
