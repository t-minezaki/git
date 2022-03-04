/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.dao;

import jp.learningpark.framework.dao.SqlMapper;
import jp.learningpark.modules.common.entity.StuTextbChocEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 生徒教科書選択管理
 * 
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@Mapper
public interface StuTextbChocDao extends SqlMapper<StuTextbChocEntity> {
	
}
