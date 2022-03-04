/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F09019Dto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F09019_一斉お知らせ配信（一覧）（スマホ） Dao</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/02/19 : wq: 新規<br />
 * @version 1.0
 */
public interface F09019Dao {

    /**
     * @param orgId 組織ID
     * @param offset
     * @param limit
     * @return
     */
    List<F09019Dto> selectNotices(@Param("orgId") String orgId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * @param orgId 組織ID
     * @return
     */
    Integer selectNoticesCount(String orgId);
}
