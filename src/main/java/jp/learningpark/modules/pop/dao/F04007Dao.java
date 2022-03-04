/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.pop.dto.F04007Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>保護者既読未読詳細一覧画面（ニュース）</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/06 : xiong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F04007Dao {
    /**　
     * お知らせ配信先、組織情報、保護者お知らせ閲覧状況より、取得する。　
     * @param noticeId セッションデータ．ＩＤ
     * @param orgId    セッションデータ．組織ＩＤ
     * @param readFlg  既読未読フラグ
     * @param pageSize 1ページのMAX件数30件
     * @return
     */
    List<F04007Dto> selectGuardStuById(@Param("noticeId")Integer noticeId, @Param("orgId")String orgId, @Param("pageSize")Integer pageSize
            , @Param("readFlg") String readFlg, @Param("limit")Integer limit);

    /**
     * @param noticeId セッションデータ．ＩＤ
     * @param orgId    セッションデータ．組織ＩＤ
     * @param readFlg  既読未読フラグ
     * @return
     */
    Integer selectGuardCount(@Param("noticeId")Integer noticeId, @Param("orgId")String orgId, @Param("readFlg") String readFlg);

}
