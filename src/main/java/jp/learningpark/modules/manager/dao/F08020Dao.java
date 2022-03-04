package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F08020Dto;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

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
 * 2020/11/3 ： NWT)hxl ： 新規作成
 * @date 2020/11/3 11:15
 */
@Mapper
public interface F08020Dao {
    /**
     * 保護者配信対象データ
     * @param openDiv       開封状況区分
     * @param readDiv       閲覧状況区分
     * @param noticeId      お知らせＩＤ
     * @param orgId         組織ID
     * @return
     */
    List<F08020Dto> getGrsList(@Param("openDiv") String openDiv, @Param("readDiv")String readDiv,
                                    @Param("noticeId") Integer noticeId, @Param("orgId")String orgId);

    /**
     * 適格なダウンロードデータを取得する
     * @param openDiv       開封状況区分
     * @param readDiv       閲覧状況区分
     * @param noticeId      お知らせＩＤ
     * @param orgId         組織ID
     * @return
     */
    List<F08020Dto> getDownloadInfo(@Param("openDiv") String openDiv, @Param("readDiv")String readDiv,
                                    @Param("noticeId") Integer noticeId, @Param("orgId")String orgId, @Param("stuIdList")List<String> stuIdList);
}
