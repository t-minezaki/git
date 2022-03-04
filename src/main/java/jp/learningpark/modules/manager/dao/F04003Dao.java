/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F04003Dto;
import jp.learningpark.modules.manager.dto.F04003DtoIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F04003 塾ニュース編集画面 Dao</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/12 : wen: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F04003Dao {

    /**
     * @param orgIdList 組織IDList
     * @return
     */
    List<F04003Dto> getStuList(@Param("id") Integer id);
    List<F04003DtoIn> getList(@Param("stuIdList") List<String> stuIdList);
}
