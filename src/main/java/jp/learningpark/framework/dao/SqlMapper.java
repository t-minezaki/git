package jp.learningpark.framework.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

public interface SqlMapper<T> extends BaseMapper<T> {

    /**
     * <p>
     * 根据 ID 修改
     * </p>
     *
     * @param entity 实体对象
     */
    int updateAllColumnById(@Param(Constants.ENTITY) T entity);
}
