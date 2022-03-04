/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.learningpark.modules.manager.dto.F00053Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F00053_生徒グループ関係検索一覧画面 Dao</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/03/18 : tan: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00053Dao extends BaseMapper<F00053Dto> {

    /**
     * 初期化
     *
     * @param orgId    session 組織ＩＤ
     * @param limit     １ページ最大件数
     * @param page      現在のページ数
     * @return
     */
    List<F00053Dto> getGroupList(@Param("orgId") String orgId,
                                 @Param("groupName")String groupName,
                                 @Param("limit") Integer limit,
                                 @Param("page") Integer page);

    /**
     * 総件数をとる
     *
     * @param orgId    session 組織ＩＤ
     * @return
     */
    Integer getTotalCount(@Param("orgId") String orgId,@Param("groupName")String groupName);
}
