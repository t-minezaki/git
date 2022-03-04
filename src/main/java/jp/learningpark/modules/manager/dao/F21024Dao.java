/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21024Dto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>出欠席連絡一覧（スマホ）</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/25 : zpa: 新規<br />
 * @version 1.0
 */
public interface F21024Dao {
    List<F21024Dto> init(@Param("userId")String userId, @Param("orgId")String orgId, @Param("corrspdSts")String corrspdSts, @Param("calendar") String tgtYmd, @Param("roleDiv")String roleDiv, @Param("limit") Integer limit, @Param("offset") Integer offset);

    Integer getCount(@Param("userId")String userId, @Param("orgId")String orgId, @Param("corrspdSts")String corrspdSts, @Param("calendar") String tgtYmd, @Param("roleDiv")String roleDiv, @Param("limit") Integer limit, @Param("offset") Integer offset);
}
