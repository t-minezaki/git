package jp.learningpark.modules.job.dao;

import jp.learningpark.modules.job.entity.BTGKA1013Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface BTGKA1013Dao {
    /**
     * 1 デバイスTOKEN管理から有効期間超過デバイスを取得する。
     * @return
     */
    List<BTGKA1013Dto> getUsrIds();

    /**
     * 2 デバイスTOKEN管理更新
     * @return
     */
    Integer updateDelFlgByDeviceId(@Param("usrId") Integer usrId,@Param("updDatime") Timestamp updDatime);
}
