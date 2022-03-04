package jp.learningpark.modules.com.dao;

import jp.learningpark.modules.common.entity.MstStuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * マナミルSAMLMAPPING Dao
 * </p>
 *
 * @author NWT)lyx
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/03/17 ： NWT)lyx ： 新規作成
 * @date 2021/03/17 10:27
 */
@Mapper
public interface OnLineApiDao {

    /**
     * 更新内容
     * @param entity
     * @return
     */
    Integer updateStudentInfo(@Param("entity") Map<String, Object> entity);

    /**
     * 更新内容
     * @param entity
     * @return
     */
    Integer updateGuardInfo(@Param("entity") Map<String, Object> entity);

    /**
     * 更新内容
     * @param entity
     * @return
     */
    Integer updateUsrInfo(@Param("entity") Map<String, Object> entity);

    /**
     * <p>保護者IDを取得する</p>
     * @param afterUsrId
     * @return
     */
    MstStuEntity selectGurdId(@Param("afterUsrId") String afterUsrId);

    /**
     * 更新内容
     * @param entity
     * @return
     */
    Integer updateMstGrp(@Param("entity") Map<String, Object> entity);

    /**
     * 生徒とグループ関連関係を削除する。　※物理削除
     * @param stuId
     * @return
     */
    Integer deleteStuGrpByStuId(@Param("stuId") String stuId);
}
