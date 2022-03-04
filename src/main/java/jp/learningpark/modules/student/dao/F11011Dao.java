package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F11011Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

@Mapper
public interface F11011Dao {
    F11011Dto init(@Param("messageId") Integer messageId, @Param("stuId") String stuId);

    // 2020/11/12 zhangminghao modify start
    void updateOpenedFlg(@Param("noticeId") Integer noticeId,
                         @Param("userId") String userId,
                         @Param("latestTime") Timestamp latestTime);
    // 2020/11/12 zhangminghao modify end
}
