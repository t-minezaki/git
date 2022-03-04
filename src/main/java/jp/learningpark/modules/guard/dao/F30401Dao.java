/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30401Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>塾からのイベント情報一覧画面</p >
 *
 * @author NWT : hujiaxing <br />
 * 変更履歴 <br />
 * 2019/07/29 : hujiaxing: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F30401Dao {
    /**
     * 塾からのイベントを取得し、画面で表示される。
     *
     * @param stuId   生徒Id
     * @param guardId 保護者Id
     * @param orgId   組織Id
     * @param limit   limit
     * @param page    page
     * @return
     */
    List<F30401Dto> selectNews(@Param("stuId") String stuId, @Param("guardId") String guardId
            , @Param("limit") Integer limit, @Param("page") Integer page);

    /**
     * 塾からのイベントを取得し、画面で表示される。
     *
     * @param stuId   生徒Id
     * @param guardId 保護者Id
     * @param orgId   組織Id
     * @return
     */
    Integer selectNewsCount(@Param("stuId") String stuId, @Param("guardId") String guardId);

    /**
     * 塾からのイベント未読カウント数
     *
     * @param guardId
     * @param stuId
     * @param orgId
     * @return
     */
    Integer getNewsCount(@Param("guardId") String guardId, @Param("stuId") String stuId);
}
