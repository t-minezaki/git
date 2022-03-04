/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F04006Dto;

import java.util.List;

/**
 * <p>配信先既読未読状態確認一覧画面（ニュース）</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/05 : hujunjie: 新規<br />
 * @version 1.0
 */
public interface F04006Service {
    /**
     * 「組織ID」＝セッションデータ．組織IDの場合
     * お知らせ配信先、組織情報、保護者お知らせ閲覧状況より、取得する
     *
     * @param noticeId お知らせId
     * @return
     */
    List<F04006Dto> selectReadCountByOrgId(Integer noticeId, Integer limit, Integer page);

    /**
     * 「組織ID」＝セッションデータ．組織IDの場合
     * お知らせ配信先、組織情報、保護者お知らせ閲覧状況より、取得する
     *
     * @param noticeId お知らせId
     * @return
     */
    Integer selectCountByOrgId(Integer noticeId);

    /**
     * 「組織ID」≠セッションデータ．組織IDの場合
     * 本組織及び上位組織リストの取得
     *
     * @param orgId 組織ID
     * @param brandCd ブランドコード
     * @return
     */
    List<F04006Dto> selectAllLowLevOrgListByOrgId(String orgId, String brandCd);

    /**
     * 「組織ID」≠セッションデータ．組織IDの場合
     * お知らせ配信先より、上層組織から指定された配信先組織のうち、本組織と、本組織の下層組織対応する配信先組織の既読未読集計一覧を取得する。
     *
     * @param orgList 本組織と、本組織の下層組織リスト
     * @param noticeId お知らせId
     * @return
     */
    List<F04006Dto> selectReadCountByLowLevOrgId(List<String> orgList, Integer noticeId, Integer limit, Integer page);

    /**
     * 「組織ID」≠セッションデータ．組織IDの場合
     * お知らせ配信先より、上層組織から指定された配信先組織のうち、本組織と、本組織の下層組織対応する配信先組織の既読未読集計一覧を取得する。
     *
     * @param orgList 本組織と、本組織の下層組織リスト
     * @param noticeId お知らせId
     * @return
     */
    Integer selectCountByLowLevOrgId(List<String> orgList, Integer noticeId);

    /**
     * @param orgId 組織ID
     * @param brandCd ブランドコード
     * @param noticeId お知らせID
     * @param limit
     * @param page
     * @return 本組織の下層組織対応する配信先組織の既読未読集計一覧を取得する。
     */
    List<F04006Dto> selectReadStsDatas(String orgId, String brandCd, Integer noticeId, Integer limit, Integer page);
}
