package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F08022Dto;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>F08022_未読・未回答者送信一覧画面（インフォメーション） Dao</p>
 *
 * @author NWT文
 * @version 9.0
 * 変更履歴:
 * 2020/11/3 ： NWT文 ： 新規作成
 */
@Mapper
public interface F08022Dao {
    /**
     * 保護者配信対象データ
     *
     * @param messageId ＩＤ
     * @param readDiv 閲覧状況区分
     * @param orgId 組織ID
     * * @return
     */
    List<F08022Dto> getGrsList(@Param("readDiv") String readDiv, @Param("messageId") Integer messageId, @Param("orgId") String orgId);

    /**
     * 適格なダウンロードデータを取得する
     *
     * @param readDiv 閲覧状況区分
     * @param messageId メッセージＩＤ
     * @param orgId 組織ID
     * @param usrIdList 管理者OR先生list
     * * @return
     */
    List<F08022Dto> getDownloadInfo(
            @Param("readDiv") String readDiv, @Param("messageId") Integer messageId, @Param("orgId") String orgId, @Param("usrIdList") List<String> usrIdList);

}
