package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F08017Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface F08017Dao {
    /**
     * 質問面談テンプレートから取得データをそのままで表示する
     * @param tempId
     * @param orgId
     * @return
     */
    List<F08017Dto> selectMstAskTalkData(@Param("tempId")Integer tempId, @Param("orgId")String orgId);

}
