package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.pop.dto.F21074Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>配信先選択画面（POP） Service</p>
 *
 * @author NWT)hxl
 * @version 1.0
 * 変更履歴:
 * 2020/06/04 ： NWT)hxl ： 新規作成
 * 2020/11/09 ： NWT)文 ： 要件変更
 */
@Mapper
public interface F21074Dao {

    /**
     * <p>管理者と先生の情報を取得する。</p>
     *
     * @param orgIdList 組織IDリスト
     * @param searchName 画面入力した名
     * @param loginId 画面入力した登録ID
     * @return Result<List>
     */
    List<F21074Dto> selectManagerAndMentorList(
            @Param("searchName") String searchName, @Param("loginId") String loginId, @Param("orgIdList") List<String> orgIdList);

    /**
     * <p>管理者と先生の情報数を取得する。</p>
     *
     * @param orgIdList 組織IDリスト
     * @param searchName 画面入力した名
     * @param loginId 画面入力した登録ID
     * @return Result<Integer>
     */
    Integer selectUserCount(@Param("searchName") String searchName, @Param("loginId") String loginId, @Param("orgIdList") List<String> orgIdList);
}
