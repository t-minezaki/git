/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.dao;

import jp.learningpark.framework.dao.SqlMapper;
import jp.learningpark.modules.common.entity.StuWeeklySumHstEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 子供ニュース週次集計履歴
 * 
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@Mapper
public interface StuWeeklySumHstDao extends SqlMapper<StuWeeklySumHstEntity> {
	
}
