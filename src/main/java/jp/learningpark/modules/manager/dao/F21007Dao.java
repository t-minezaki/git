package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21007Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>F21007_出席簿一覧画面 Dao</p >
 *
 * @author NWT : LiYuHuan <br />
 * @author NWT文
 * @version 9.0要件変更 2020/11/11
 */
@Mapper
public interface F21007Dao {

    /**
     * <p>出席簿情報を取得する</p>
     *
     * @param orgId 組織ID
     * @param date 日付
     * @param grpNm グループ名
     * @return
     */
    List<F21007Dto> selectAll(@Param("orgId") String orgId, @Param("date") Date date, @Param("grpNm") String grpNm);

    /**
     * <p>出席簿ヘーダから回数を算出する</p>
     *
     * @param orgId 組織ID
     * @param grpId グループID
     * @param date 　日付
     * @return
     */
    Integer selectNumMax(@Param("orgId") String orgId, @Param("grpId") Integer grpId, @Param("date") Date date);

    /**
     * <p>グループ情報を取得する</p>
     *
     * @param orgId 　組織ID
     * @param grpIdList 　グループIDリスト
     * @param dayweekDiv 　曜日区分
     * @param grpNm グループ名
     */
    List<F21007Dto> selectMstGrpNm(
            @Param("orgId") String orgId, @Param("grpIdList") List<Integer> grpIdList, @Param("dayweekDiv") String dayweekDiv, @Param("grpNm") String grpNm);
}
