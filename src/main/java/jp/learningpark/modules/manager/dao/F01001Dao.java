package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F01001Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>F01001_塾時期検索一覧画面 Dao</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/08 : xiong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F01001Dao {
    /**
     * 下位組織一覧情報
     * @param orgId    組織ID
     * @param brandCd  ブランドコード
     * @param pageSize
     * @param limit
     * @return
     */
    List<F01001Dto> getUpplevInfomation(@Param("orgId") String orgId, @Param("brandCd") String brandCd, @Param("pageSize") Integer pageSize, @Param("limit") Integer limit);

    /**
     *
     * @param orgId   組織ID
     * @param brandCd ブランドコード
     * @return
     */
    Integer getTotalCount(@Param("orgId") String orgId, @Param("brandCd") String brandCd);
}
