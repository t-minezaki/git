/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.pop.dto.F09003Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>F09003_対象者選択画面 Dao</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/11/28 : yang: 新規<br />
 * @version 5.0
 */
@Mapper
public interface F09003Dao {
    /**
     * 検索
     * @param orgId
     * @param paramsMap
     * @return
     */
    List<F09003Dto> getStuList(@Param("orgId")String orgId,@Param("paramsMap")Map<String, String> paramsMap,@Param("userId")String userId,@Param("roleDiv")String roleDiv);
}
