package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21018Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * F21018Dao
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/20 ： NWT)hxl ： 新規作成
 * @date 2020/02/20 17:31
 */
@Mapper
public interface F21018Dao {

    /**
     * 生徒IDに基づいて生徒情報を取得する
     *
     * @param stuId 生徒ID
     * @return 生徒情報
     */
    F21018Dto selectStuInfo(@Param("stuId") String stuId);

    /**
     * 保護者IDに基づいて保護者情報を取得する
     *
     * @param guardIdList 保護者IDリスト
     * @return 保護者情報リスト
     */
    List<F21018Dto> selectGuardInfo(@Param("guardIdList") List<String> guardIdList);

    /**
     * 保護者ログインID取得
     */
    String selectAfterIdByUsrId(@Param("usrId")String guardId);
}
