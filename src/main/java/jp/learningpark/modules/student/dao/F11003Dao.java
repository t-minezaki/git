package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.common.entity.MstBlockEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/27
 */
@Mapper
public interface F11003Dao {
    /**
     *
     * @return
     */
    List<MstBlockEntity> init();
}
