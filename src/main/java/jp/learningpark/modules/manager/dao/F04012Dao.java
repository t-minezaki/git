package jp.learningpark.modules.manager.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * マナミルチャンネル新規·編集 Dao
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/10 ： NWT)hxl ： 新規作成
 * @date 2020/02/10 13:30
 */
@Mapper
public interface F04012Dao {
    /**
     * お知らせ配信先 insert
     * @param noticeId
     * @param orgIdList
     * @param sysTimestamp
     * @param userId
     * 楊　2021/08/19　MANAMIRU1-733　Add
     * @return
     */
    Boolean doInsertMstNoticeDeliver(@Param("noticeId") Integer noticeId, @Param("orgIdList") List<String> orgIdList, @Param("sysTimestamp")Timestamp sysTimestamp,@Param("userId")String userId);

    /**
     * 保護者お知らせ閲覧状況 insert
     * @param noticeId
     * 楊　2021/08/26　MANAMIRU1-733　Edit
     * @return
     */
    Boolean doInsertGuardReadingSts(@Param("noticeId") Integer noticeId);

    /**
     *
     * 初期化
     *
     * @param id            お知らせID
     * @return
     */
    List<String> selectChosenOrgIdList(@Param("id") Integer id);
}
