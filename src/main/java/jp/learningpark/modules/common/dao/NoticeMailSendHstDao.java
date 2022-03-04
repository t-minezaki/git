/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.dao;

import jp.learningpark.framework.dao.SqlMapper;
import jp.learningpark.modules.common.entity.NoticeMailSendHstEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 指導報告書明細
 * 
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@Mapper
public interface NoticeMailSendHstDao extends SqlMapper<NoticeMailSendHstEntity> {
	
}
