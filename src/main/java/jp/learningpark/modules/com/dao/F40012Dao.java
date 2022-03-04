package jp.learningpark.modules.com.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>生徒共通メニュー画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/23 : zpa: 新規<br />
 * @version 7.0
 */
@Mapper
public interface F40012Dao {
    Integer getNum(@Param("orgId") String orgId, @Param("userId") String userId);
}
