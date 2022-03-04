package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F08020Dto;
import jp.learningpark.modules.manager.dto.F08023Dto;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)ckl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/11/5 ： NWT)ckl ： 新規作成
 * @date 2020/11/5 11:15
 */
@Mapper
public interface F08023Dao {
    /**
     * 保護者配信対象データ
     * @param div           閲覧状況区分
     * @param orgId         組織ID
     * @param guidReprDeliverCd  指導報告書配信コード
     * @return
     */
    List<F08023Dto> getGrsList(@Param("div") String div, @Param("orgId") String orgId,@Param("guidReprDeliverCd")String guidReprDeliverCd);

    /**
     * 適格なダウンロードデータを取得する
     * @param div           閲覧状況区分
     * @param orgId         組織ID
     * @param guidReprDeliverCd  指導報告書配信コード
     * @param stuIdList     生徒IDリスト
     * @return
     */
    List<F08023Dto> getDownloadInfo(@Param("guidReprDeliverCd") String guidReprDeliverCd, @Param("div") String div,
                                    @Param("orgId") String orgId, @Param("stuIdList") List<String> stuIdList);
}
