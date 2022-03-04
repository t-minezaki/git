package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.common.entity.MstCodDEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface F21006Dao {
    List<MstCodDEntity> getSubjt(@Param("subjtDiv") String[] subjtDiv);
}
