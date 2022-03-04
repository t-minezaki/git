package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F09022Dto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface F09022Dao {
    /**
     * 生徒情報のクエリ
     *
     * @param orgId        組織ID
     * @param noticeId     お知らせID
     * @param flg
     * @return
     */
    List<F09022Dto> init(@Param("orgId" )String orgId,@Param("noticeId")Integer noticeId,@Param("flg")String flg);
}
