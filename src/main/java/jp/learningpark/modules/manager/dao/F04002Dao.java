/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F04002Dto;
import jp.learningpark.modules.manager.dto.F04002DtoIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F04002 塾ニュース新規画面 Dao</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/06 : wen: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F04002Dao {

    /**
     * @param stuIdList 学生IDList
     * @return
     */
    List<F04002Dto> getStuList(@Param("stuIdList") List<String> stuIdList);
    /**
     * @param stuIdList 学生IDList
     * @return
     */
    List<F04002DtoIn> getList(@Param("stuIdList") List<String> stuIdList);
}
