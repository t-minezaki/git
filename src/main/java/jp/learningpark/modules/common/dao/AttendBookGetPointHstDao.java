/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.dao;

import jp.learningpark.framework.dao.SqlMapper;
import jp.learningpark.modules.common.entity.AttendBookGetPointHstEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 出席簿付与ポイント履歴
 * 
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@Mapper
public interface AttendBookGetPointHstDao extends SqlMapper<AttendBookGetPointHstEntity> {
	List<AttendBookGetPointHstEntity> selectAllPointByStuIdAndOrgId(@Param("stuId")String stuId, @Param("orgId")String orgId, @Param("resetDatime")Date date);
}
