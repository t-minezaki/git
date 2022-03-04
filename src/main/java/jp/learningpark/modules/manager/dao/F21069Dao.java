package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21069DtoIn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/25 ： NWT)hxl ： 新規作成
 * @date 2020/05/25 11:21
 */
public interface F21069Dao {
    /**
     * <p>管理者と先生のIDListによるdtotListの取得</p>
     *
     * @param adminIdList 管理者と先生のIDList
     * @return
     */
    List<F21069DtoIn> selectAdminByIdList(@Param("adminIdList") List<String> adminIdList,@Param("page")Integer page,@Param("limit")Integer limit);
    /**
     * <p>管理者と先生のIDListによるdtotListの取得</p>
     *
     * @param adminIdList 管理者と先生のIDList
     * @return
     */
    List<F21069DtoIn> selectStuByIdListTotal(@Param("adminIdList") List<String> adminIdList);
}
