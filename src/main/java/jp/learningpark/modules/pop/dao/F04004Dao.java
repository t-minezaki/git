/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.pop.dto.F04004Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * <p>配信先設定画面 Dao</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/06/18: yang: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F04004Dao {

    /**
     * @param orgIdList 組織IDList
     * @return
     */
    List<F04004Dto> selectStuAndGuardList(@Param("schy") String schy, @Param("groupList") List<Integer> group, @Param("orgIdList") List<String> orgIdList,
                                          @Param("stuId") String stuId, @Param("stuNm") String stuNm, @Param("stuIds") List<String> stuIds,@Param("rowIdS") List<String> rowIds,@Param("stuIdList")List<String> stuIdList );
}
