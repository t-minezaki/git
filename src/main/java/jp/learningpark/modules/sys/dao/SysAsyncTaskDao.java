/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.sys.dao;

import jp.learningpark.framework.dao.SqlMapper;
import jp.learningpark.modules.sys.entity.SysAsyncTaskEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 非同期タスクマスタ
 * 
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@Mapper
public interface SysAsyncTaskDao extends SqlMapper<SysAsyncTaskEntity> {
	
}
