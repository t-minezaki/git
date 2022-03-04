package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F08021Dto;
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
 * 2020/11/9 ： NWT)wyh ： 新規作成
 * @date 2020/11/9 18:15
 */
@Mapper
public interface F08021Dao {
    /**
     * 保護者配信対象データ
     *
     * @param openDiv   開封状況区分
     * @param readDiv   閲覧状況区分
     * @param messageId お知らせＩＤ
     * @param orgId     組織ID
     *                  * @return
     */
    List<F08021Dto> getGrsList(@Param("openDiv") String openDiv, @Param("readDiv") String readDiv,
                               @Param("messageId") Integer messageId, @Param("orgId") String orgId);

    /**
     * 適格なダウンロードデータを取得する
     *
     * @param openDiv   開封状況区分
     * @param readDiv   閲覧状況区分
     * @param messageId お知らせＩＤ
     * @param orgId     組織ID
     *                  * @return
     */
    List<F08021Dto> getDownloadInfo(@Param("openDiv") String openDiv, @Param("readDiv") String readDiv,
                                    @Param("messageId") Integer messageId, @Param("orgId") String orgId, @Param("stuIdList") List<String> stuIdList);
}
