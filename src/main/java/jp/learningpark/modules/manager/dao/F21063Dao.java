package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21063DtoIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F21063_メッセージ作成画面 Dao</p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/20 ： NWT)hxl ： 新規作成
 * 2020/11/11 ： NWT)文 ： 要件変更
 */
@Mapper
public interface F21063Dao {
    /**
     * <p>生徒IDListによるdtotListの取得</p>
     *
     * @param stuIdList 生徒IDList
     * @return
     */
    List<F21063DtoIn> selectStuByIdList(@Param("stuIdList") List<String> stuIdList,@Param("page")Integer page,@Param("limit")Integer limit);
    /**
     * <p>生徒IDListによるdtotListの取得</p>
     *
     * @param stuIdList 生徒IDList
     * @return
     */
    List<F21063DtoIn> selectStuByIdListCount(@Param("stuIdList") List<String> stuIdList);
}
