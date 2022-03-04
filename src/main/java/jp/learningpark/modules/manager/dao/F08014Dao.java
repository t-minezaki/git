package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F08014Dto;
import jp.learningpark.modules.manager.dto.F08014StudentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface F08014Dao {

    /**
     *
     * @param id ID
     * @return
     */
    F08014Dto selectEvent(@Param("id") Integer id);

    /**
     * @param espdId   ID
     * @param userFlag 保護者フラグ
     * @return
     */
    List<F08014StudentDto> selectStudent(@Param("espdId") Integer espdId, @Param("userFlag") Boolean userFlag);
}
