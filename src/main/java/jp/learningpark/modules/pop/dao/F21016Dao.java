package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.pop.dto.F21016GuardDto;
import jp.learningpark.modules.pop.dto.F21016SchyDto;
import jp.learningpark.modules.pop.dto.F21016StudentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * F21016Dao
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/20 ： NWT)hxl ： 新規作成
 * @date 2019/11/20 18:29
 */
@Mapper
public interface F21016Dao {
    /**
     * 保護者情報を取得する
     *
     * @param orgId     組織ID
     * @param guardName 画面．検索条件．姓名
     * @return
     */
    List<F21016GuardDto> getGuardList(@Param("orgId") String orgId, @Param("guardName") String guardName);

    /**
     * 生徒情報を取得する
     *
     * @param orgId   組織ID
     * @param schy    学年
     * @param stuName 生徒名
     * @return
     */
    List<F21016StudentDto> getStudentList(@Param("orgId") String orgId, @Param("schy") String schy, @Param("stuName") String stuName);

    /**
     * 学年の区別を取得する
     *
     * @return
     */
    List<F21016SchyDto> getSchy();
}
