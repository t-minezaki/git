package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F21007Dao;
import jp.learningpark.modules.manager.dto.F21007Dto;
import jp.learningpark.modules.manager.service.F21007Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>F21007_出席簿一覧画面 ServiceImpl</p >
 *
 * @author NWT : LiYuHuan <br />
 * @author NWT文
 * @version 9.0要件変更 2020/11/11
 */
@Service
public class F21007ServiceImpl implements F21007Service {
    /**
     * 出席簿一覧画面 Dao
     */
    @Autowired
    F21007Dao f21007Dao;

    /**
     * @param orgId 組織ID
     * @param date 　日付
     * @param grpNm グループ名
     * @return
     */
    @Override
    public List<F21007Dto> selectAll(String orgId, Date date, String grpNm) {
        return f21007Dao.selectAll(orgId, date, grpNm);
    }

    /**
     * @param orgId 　組織ID
     * @param grpId 　グループID
     * @param date 　日付
     * @return
     */
    @Override
    public Integer selectNumMax(String orgId, Integer grpId, Date date) {
        return f21007Dao.selectNumMax(orgId, grpId, date);
    }

    /**
     * @param orgId 　組織ID
     * @param grpIdList 　グループIDリスト
     * @param dayweekDiv 　曜日区分
     * @return
     */
    @Override
    public List<F21007Dto> selectMstGrpNm(String orgId, List<Integer> grpIdList, String dayweekDiv, String grpNm) {
        return f21007Dao.selectMstGrpNm(orgId, grpIdList, dayweekDiv, grpNm);
    }
}
