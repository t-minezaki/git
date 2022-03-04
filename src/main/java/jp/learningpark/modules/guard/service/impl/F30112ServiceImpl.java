/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.guard.dao.F30112Dao;
import jp.learningpark.modules.guard.dto.F30112Dto;
import jp.learningpark.modules.guard.service.F30112Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>塾からの連絡通知一覧画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/07 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
public class F30112ServiceImpl implements F30112Service {
    /**
     * 塾からの連絡通知一覧画面 Dao
     */
    @Autowired
    F30112Dao f30112Dao;

    /**
     * 通知とイベントのデータを取得する
     *
     * @param stuId 生徒Id
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @param limit limit
     * @param page page
     * @return
     */
    @Override
    public List<F30112Dto> getNotices(String stuId, String guardId, String orgId, Integer limit, Integer page) {
        return f30112Dao.selectNotices(stuId, guardId, orgId, limit, page);
    }

    /**
     * 通知とイベントの総数を取得する
     *
     * @param stuId 生徒Id
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @return
     */
    @Override
    public Integer getNoticeCount(String stuId, String guardId, String orgId) {
        return f30112Dao.selectNoticeCount(stuId, guardId, orgId);
    }

    /**
     * お知らせ未読カウント数
     *
     * @param guardId
     * @param stuId
     * @param orgId
     * @return
     */
    @Override
    public Integer selectNoticeUnreadCount(String guardId, String stuId, String orgId, String levDiv) {
        return f30112Dao.getNoticeUnreadCount(guardId, stuId, orgId, levDiv);
    }


    /**
     * 塾・教室ニュース未読件数
     *
     * @param guardId
     * @param stuId
     * @param orgId
     * @return
     */
    @Override
    public F30112Dto updateStatus(Integer id, String orgId, String stuId, String guardId, Integer guidReprId, String deliverCd) {
        return f30112Dao.updateStatus(id, orgId, stuId, guardId, guidReprId, deliverCd);
    }
}
