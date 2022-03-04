/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.manager.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F00004_利用者基本情報設定・修正 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/20 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00004Dao {
    /**
     * <p>max(a除いた数字)</p>
     *
     * @param brandCd ブランドコード
     * @return
     */
    List<F00004Dto> selectMax(String brandCd);

    /**
     * ⅱ ニックIDの一意チェック
     *
     * @param orgId 組織ID
     * @param afterId 変更後ユーザーID
     * @return
     */
    MstUsrEntity checkFn(@Param("orgId") String orgId, @Param("afterId") String afterId);

    /**
     * ⅱ ニックIDの一意チェック(新規)
     *
     * @param brandCd ブランドコード
     * @param afterId 変更後ユーザーID
     * @return
     */
    MstUsrEntity checkFnOfSave(@Param("brandCd") String brandCd, @Param("afterId") String afterId);

    /**
     * エクスポート処理、管理者が選択した場合
     *
     * @param orgId 組織ID
     * @return
     */
    List<F00004ManagerDto> selectExcelDataOfManager(String orgId);

    /**
     * エクスポート処理、メンターが選択した場合
     *
     * @param orgId 組織ID
     * @return
     */
    List<F00004MentorDto> selectExcelDataOfMentor(String orgId);

    /**
     * エクスポート処理、保護者が選択した場合
     *
     * @param orgId 組織ID
     * @return
     */
    List<F00004GuardDto> selectExcelDataOfGuard(String orgId);

    /**
     * エクスポート処理、生徒が選択した場合
     *
     * @param orgId 組織ID
     * @return
     */
    List<F00004StuDto> selectExcelDataOfStu(String orgId);

    /**
     * 生徒・保護者の依存関係情報エクスポート処理、生徒が選択した場合
     *
     * @param orgId 組織ID
     * @return
     */
    List<F00004StuDto> selectExcelDataOfStuWithGuard(String orgId);
}
