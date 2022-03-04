/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.dao;

import jp.learningpark.framework.dao.SqlMapper;
import jp.learningpark.modules.job.entity.SysScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * スケジュールジョブログ
 * 
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@Mapper
public interface SysScheduleJobLogDao extends SqlMapper<SysScheduleJobLogEntity> {
	
}
