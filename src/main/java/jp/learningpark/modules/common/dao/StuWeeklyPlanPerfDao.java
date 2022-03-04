/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.dao;

import jp.learningpark.framework.dao.SqlMapper;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

/**
 * 生徒ウィークリー計画実績設定
 * 
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@Mapper
public interface StuWeeklyPlanPerfDao extends SqlMapper<StuWeeklyPlanPerfEntity> {
    /**
     *
     * @param stuId         生徒ID
     * @param resetDatime   リセット日時
     * @return
     */
	int selectAllPerfTime(@Param("stuId")String stuId, @Param("resetDatime")Timestamp resetDatime);
}
