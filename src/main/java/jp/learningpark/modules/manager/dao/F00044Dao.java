/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.learningpark.modules.manager.dto.F00044Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F00044_生徒保護者関係検索一覧画面 Dao</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/03/15 : tan: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00044Dao extends BaseMapper<F00044Dto> {
    /**
     * 初期化学年区分を取得
     *
     * @return
     */
    List<F00044Dto> schySearch();

    /**
     * <p>生徒基本マスタ、保護者基本マスタを元に一覧情報を取得</p>
     *
     * @param sOrgId    session 組織ＩＤ
     * @param stuId     画面．検索条件．生徒ID
     * @param guardId   画面．検索条件．保護者ID
     * @param stuName   画面．検索条件．生徒姓名
     * @param guardName 画面．検索条件．保護者姓名
     * @param Schy      画面．検索条件．学年
     * @param limit     １ページ最大件数
     * @param page      現在のページ数
     * @param orgIdList 画面  检索条件  組織ID
     * @return
     */
    List<F00044Dto> search(
            @Param("stuId1") String stuId,
            @Param("guardId1") String guardId,
            @Param("stuName1") String stuName,
            @Param("guardName1") String guardName,
            @Param("Schy1") String Schy,
            @Param("limit1") Integer limit,
            @Param("page1") Integer page,
            @Param("orgIdList") List<String> orgIdList);

    /**
     * <p>総件数をとる</p>
     *
     * @param stuId     画面．検索条件．生徒ID
     * @param guardId   画面．検索条件．保護者ID
     * @param stuName   画面．検索条件．生徒姓名
     * @param guardName 画面．検索条件．保護者姓名
     * @param Schy      画面．検索条件．学年
     * @param orgIdList 画面  检索条件  組織ID
     * @return
     */
    Integer totalCount(@Param("stuId") String stuId,
                       @Param("guardId") String guardId,
                       @Param("stuName") String stuName,
                       @Param("guardName") String guardName,
                       @Param("Schy") String Schy,
                       @Param("orgIdList") List<String> orgIdList);
}

