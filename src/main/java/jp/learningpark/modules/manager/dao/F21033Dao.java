/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.guard.dto.F30112Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface F21033Dao {
    /**
     * 各対象日をもとに、組織単位で全て生徒の実績個数と計画個数を集計する
     *
     * @param ymdStart
     * @param ymdEnd
     * @param orgId
     * @return
     */
    Double getSchoolData(@Param("ymdStart") String ymdStart, @Param("ymdEnd") String ymdEnd, @Param("orgId") String orgId);

    /**
     * 各対象日をもとに、指定したグループ単位で全て生徒の実績個数と計画個数を集計する
     *
     * @param ymdStart
     * @param ymdEnd
     * @param grpId
     * @return
     */
    Double getGroupData(@Param("ymdStart") String ymdStart, @Param("ymdEnd") String ymdEnd, @Param("grpId") Integer grpId);

    Double getMap2(@Param("ymdStart") String ymdStart, @Param("ymdEnd") String ymdEnd, @Param("orgId") String orgId, @Param("schyDiv") String schyDiv, @Param(
            "grpId") Integer grpId, @Param("lineType") String lineType);

    /**
     * 通知とイベントのデータを取得する
     *
     * @param usrId ユーザーId
     * @param limit limit
     * @param page  page
     * @return
     */
    List<F30112Dto> getInformation(
            @Param("usrId") String usrId, @Param("limit") Integer limit, @Param("page") Integer page);

    /**
     * 通知とイベントの総数を取得する
     *
     * @param usrId ユーザーId
     * @return
     */
    Integer getInformationCount(@Param("usrId") String usrId);

}
