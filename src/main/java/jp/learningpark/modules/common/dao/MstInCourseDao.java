/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.dao;

import jp.learningpark.framework.dao.SqlMapper;
import jp.learningpark.modules.common.entity.MstInCourseEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>入会コース管理 Dao</p >
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/26 : xie: 新規<br />
 * @version 1.0
 */
@Mapper
public interface MstInCourseDao extends SqlMapper<MstInCourseEntity> {
}
