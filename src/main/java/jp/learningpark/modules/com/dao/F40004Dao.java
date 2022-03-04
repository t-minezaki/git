package jp.learningpark.modules.com.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>F40004 保護者共通メニュー画面_ServiceImpl</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/03/16 : zpa: 新規<br />
 * @version 6.0
 */
@Mapper
public interface F40004Dao {
    Integer getEventUnreadCount(@Param("guardId") String guardId,@Param("stuId") String stuId);
    Integer getNoticeUnreadCount(@Param("orgId") String orgId,@Param("guardId") String guardId,@Param("stuId") String stuId);
    Integer getGKGCCount(@Param("orgId") String orgId,@Param("guardId") String guardId, @Param("stuId")String stuId);
}
