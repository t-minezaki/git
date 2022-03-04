package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F09001HistoryDto;
import jp.learningpark.modules.manager.dto.F09001OrgDto;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * F09001Daoインターフェイス
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/12 ： NWT)hxl ： 新規作成
 */
@Mapper
public interface F09001Dao {

    /**
     * <p>組織IDと時間範囲に基づいて生徒の学校記録を取得する</p>
     *
     * @param orgId 組織ID
     * @param startTime 開始時間範囲
     * @param endTime 終了時間範囲
     * @param limit 検索数
     * @param offset オフセット
     * @return
     */
    List<F09001HistoryDto> getEntryHistory(
            @Param("org_id") String orgId,
            @Param("roleDiv") String roleDiv,
            @Param("userId") String userId,
            @Param("start_time") Timestamp startTime, @Param("end_time") Timestamp endTime, @Param("limit") Integer limit, @Param("offset") Integer offset);

    /**
     * <p>レコードの総数を取得する</p>
     *
     * @param orgId 組織ID
     * @param startTime 開始時間範囲
     * @param endTime 終了時間範囲
     * @return
     */
    Integer getHistoryCount(
            @Param("org_id") String orgId,
            @Param("roleDiv") String roleDiv, @Param("userId") String userId, @Param("start_time") Timestamp startTime, @Param("end_time") Timestamp endTime);

    /**
     * <p>組織IDに応じて、このレイヤーと基になる組織の情報を取得します</p>
     *
     * @param orgId 組織ID
     * @return
     */
    List<F09001OrgDto> getLowerOrg(@Param("org_id") String orgId);
}
