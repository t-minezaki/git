/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.dao;

import jp.learningpark.framework.dao.SqlMapper;
import jp.learningpark.modules.common.entity.MstUsrFirtPwEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * ユーザ初期パスワード管理
 * 
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@Mapper
public interface MstUsrFirtPwDao extends SqlMapper<MstUsrFirtPwEntity> {
	
}
