/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F04006Dao;
import jp.learningpark.modules.manager.dto.F04006Dto;
import jp.learningpark.modules.manager.service.F04006Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>配信先既読未読状態確認一覧画面（ニュース）</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/05 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
public class F04006ServiceImpl implements F04006Service {
    /**
     * 配信先既読未読状態確認一覧画面（ニュース）Dao
     */
    @Autowired
    private F04006Dao f04006Dao;

    /**
     * 「組織ID」＝セッションデータ．組織IDの場合
     * お知らせ配信先、組織情報、保護者お知らせ閲覧状況より、取得する
     *
     * @param noticeId お知らせId
     * @return
     */
    @Override
    public List<F04006Dto> selectReadCountByOrgId(Integer noticeId, Integer limit, Integer page) {
        return f04006Dao.selectReadCountByOrgId(noticeId, limit, page);
    }

    /**
     * 「組織ID」＝セッションデータ．組織IDの場合
     * お知らせ配信先、組織情報、保護者お知らせ閲覧状況より、取得する
     *
     * @param noticeId お知らせId
     * @return
     */
    @Override
    public Integer selectCountByOrgId(Integer noticeId) {
        return f04006Dao.selectCountByOrgId(noticeId);
    }

    /**
     * 「組織ID」≠セッションデータ．組織IDの場合
     * 本組織及び上位組織リストの取得
     *
     * @param orgId   組織ID
     * @param brandCd ブランドコード
     * @return
     */
    @Override
    public List<F04006Dto> selectAllLowLevOrgListByOrgId(String orgId, String brandCd) {
        return f04006Dao.selectAllLowLevOrgListByOrgId(orgId, brandCd);
    }

    /**
     * 「組織ID」≠セッションデータ．組織IDの場合
     * お知らせ配信先より、上層組織から指定された配信先組織のうち、本組織と、本組織の下層組織対応する配信先組織の既読未読集計一覧を取得する。
     *
     * @param orgList  本組織と、本組織の下層組織リスト
     * @param noticeId お知らせId
     * @return
     */
    @Override
    public List<F04006Dto> selectReadCountByLowLevOrgId(List<String> orgList, Integer noticeId, Integer limit, Integer page) {
        return f04006Dao.selectReadCountByLowLevOrgId(orgList, noticeId, limit, page);
    }

    /**
     * 「組織ID」≠セッションデータ．組織IDの場合
     * お知らせ配信先より、上層組織から指定された配信先組織のうち、本組織と、本組織の下層組織対応する配信先組織の既読未読集計一覧を取得する。
     *
     * @param orgList  本組織と、本組織の下層組織リスト
     * @param noticeId お知らせId
     * @return
     */
    @Override
    public Integer selectCountByLowLevOrgId(List<String> orgList, Integer noticeId) {
        return f04006Dao.selectCountByLowLevOrgId(orgList, noticeId);
    }

    /**
     *
     * @param orgId 組織ID
     * @param brandCd ブランドコード
     * @param noticeId お知らせID
     * @param limit
     * @param page
     * @return 本組織の下層組織対応する配信先組織の既読未読集計一覧を取得する
     */
    @Override
    public List<F04006Dto> selectReadStsDatas(String orgId, String brandCd, Integer noticeId, Integer limit, Integer page) {
        return f04006Dao.getReadStsDatas(orgId, brandCd, noticeId, limit, page);
    }
}
