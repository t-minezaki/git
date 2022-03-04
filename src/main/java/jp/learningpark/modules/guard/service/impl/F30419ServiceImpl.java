package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.guard.dao.F30419Dao;
import jp.learningpark.modules.guard.dto.F30419Dto;
import jp.learningpark.modules.guard.service.F30419Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * F30419ServiceImpl
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/18 ： NWT)hxl ： 新規作成
 * @date 2020/02/18 14:30
 */
@Service
public class F30419ServiceImpl implements F30419Service {
    /**
     * 塾からの連絡通知一覧画面 Dao
     */
    @Autowired
    F30419Dao f30419Dao;

    /**
     * チャンネルを取得し、画面で表示される。
     *
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @param limit limit
     * @return
     */
    @Override
    public List<F30419Dto> selectNews(String guardId, String orgId,String stuId, Integer offset, Integer limit) {
        return f30419Dao.selectNews(guardId, orgId,stuId,offset, limit);
    }

    /**
     * チャンネルを取得し、画面で表示される。
     *
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @param limit limit
     * @return
     */
    @Override
    public Integer selectCount(String guardId, String orgId,String stuId) {
        return f30419Dao.selectCount(guardId, orgId,stuId);
    }

    /**
     * チャンネルの総数を取得し、画面で表示される。
     *
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @return
     */
    @Override
    public Integer selectNewsCount(String guardId, String orgId,String stuId) {
        return f30419Dao.selectNewsCount(guardId, orgId,stuId);
    }

    /**
     * 未読チャンネルの総数を取得し、画面で表示される。
     *
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @return
     */
    @Override
    public Integer selectUnreadCount(String guardId, String orgId,String stuId) {
        return f30419Dao.selectUnreadCount(guardId, orgId,stuId);
    }
}
