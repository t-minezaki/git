/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F21017Dao;
import jp.learningpark.modules.manager.dto.F21017Dto;
import jp.learningpark.modules.manager.service.F21017Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>F21017_マスホ_生徒一覧画面_V6.0</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/21 : yang: 新規<br />
 * @version 6.0
 */
@Service
@Transactional
public class F21017ServiceImpl implements F21017Service {

    @Autowired
    F21017Dao f21017Dao;

    /**
     * @param orgId 組織ID
     * @return
     */
    @Override
    public Integer orgDatasCount(String orgId) {
        return f21017Dao.orgDatasCount(orgId);
    }

    /**
     * @param mentorId 　先生ID
     * @param orgId 　組織ID
     * @return
     */
    @Override
    public Integer mentorDatasCount(String mentorId, String orgId) {
        return f21017Dao.mentorDatasCount(mentorId, orgId);
    }

    /**
     * 未確認連絡数の取得
     *
     * @param roleDiv
     * @param mentorId
     * @param orgId
     * @param date
     * @return
     */
    @Override
    public Integer getUnreadCount(String roleDiv, String mentorId, String orgId, String date) {
        return f21017Dao.getUnreadCount(roleDiv, mentorId, orgId, date);
    }

    /**
     * @param map
     * @return
     */
    @Override
    public List<F21017Dto> selectStuInfo(Map<String, Object> map) {
        return f21017Dao.getStuInfo(map);
    }

    /**
     * <p>学生総数を取得する。</p>
     * <p>
     * add at 2021/08/10 for V9.02 by NWT wen
     *
     * @param map
     * @return
     */
    @Override
    public Integer selectStudentCount(Map<String, Object> map) {
        return f21017Dao.getStudentCount(map);
    }
}
