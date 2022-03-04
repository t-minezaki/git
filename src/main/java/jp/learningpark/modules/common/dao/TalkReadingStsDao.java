/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.dao;

import jp.learningpark.framework.dao.SqlMapper;
import jp.learningpark.modules.common.entity.TalkReadingStsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 面談メッセージ閲覧状況
 * 
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@Mapper
public interface TalkReadingStsDao extends SqlMapper<TalkReadingStsEntity> {
	
}
