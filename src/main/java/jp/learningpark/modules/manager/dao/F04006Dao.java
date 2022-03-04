/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F04006Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>配信先既読未読状態確認一覧画面（ニュース）</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/05 : hujunjie: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F04006Dao {
    /**
     * 「組織ID」＝セッションデータ．組織IDの場合
     * お知らせ配信先、組織情報、保護者お知らせ閲覧状況より、取得する
     *
     * @param noticeId お知らせId
     * @return
     */
    List<F04006Dto> selectReadCountByOrgId(@Param("noticeId") Integer noticeId, @Param("limit") Integer limit, @Param("page") Integer page);

    /**
     * 「組織ID」＝セッションデータ．組織IDの場合
     * お知らせ配信先、組織情報、保護者お知らせ閲覧状況より、取得する
     *
     * @param noticeId お知らせId
     * @return
     */
    Integer selectCountByOrgId(@Param("noticeId") Integer noticeId);

    /**
     * 「組織ID」≠セッションデータ．組織IDの場合
     * 本組織及び上位組織リストの取得
     *
     * @param orgId 組織ID
     * @param brandCd ブランドコード
     * @return
     */
    List<F04006Dto> selectAllLowLevOrgListByOrgId(@Param("orgId") String orgId, @Param("brandCd") String brandCd);

    /**
     * 「組織ID」≠セッションデータ．組織IDの場合
     * お知らせ配信先より、上層組織から指定された配信先組織のうち、本組織と、本組織の下層組織対応する配信先組織の既読未読集計一覧を取得する。
     *
     * @param orgList 本組織と、本組織の下層組織リスト
     * @param noticeId お知らせId
     * @return
     */
    List<F04006Dto> selectReadCountByLowLevOrgId(
            @Param("orgIdList") List<String> orgList, @Param("noticeId") Integer noticeId, @Param("limit") Integer limit, @Param("page") Integer page);

    /**
     * 「組織ID」≠セッションデータ．組織IDの場合
     * お知らせ配信先より、上層組織から指定された配信先組織のうち、本組織と、本組織の下層組織対応する配信先組織の既読未読集計一覧を取得する。
     *
     * @param orgList 本組織と、本組織の下層組織リスト
     * @param noticeId お知らせId
     * @return
     */
    Integer selectCountByLowLevOrgId(@Param("orgIdList") List<String> orgList, @Param("noticeId") Integer noticeId);

    /**
     * @param orgId 組織ID
     * @param brandCd ブランドコード
     * @param noticeId お知らせID
     * @param limit
     * @param page
     * @return 本組織の下層組織対応する配信先組織の既読未読集計一覧を取得する。
     */
    List<F04006Dto> getReadStsDatas(
            @Param("orgId") String orgId,
            @Param("brandCd") String brandCd, @Param("noticeId") Integer noticeId, @Param("limit") Integer limit, @Param("page") Integer page);
}
