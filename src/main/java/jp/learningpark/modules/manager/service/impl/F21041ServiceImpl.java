/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/30
 */

import jp.learningpark.modules.manager.dao.F21041Dao;
import jp.learningpark.modules.manager.dto.F21041Dto;
import jp.learningpark.modules.manager.service.F21041Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/04/30 : lyh: 新規<br />
 * @version 1.0
 */
@Service
public class F21041ServiceImpl implements F21041Service {

    @Autowired
    F21041Dao f21041Dao;

    /**
     *
     * @param stuId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<F21041Dto> getDegree(String stuId, Date startDate, Date endDate) {
        return f21041Dao.getDegree(stuId,startDate,endDate);
    }

    /**
     *
     * @param stuId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<F21041Dto> getDegreeWeek(String stuId, Date startDate, Date endDate) {
        return f21041Dao.getDegreeWeek(stuId,startDate,endDate);
    }
    /**
     *
     * @param stuId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<F21041Dto> getDegreeMonth(String stuId, Date startDate, Date endDate) {
        return f21041Dao.getDegreeMonth(stuId,startDate,endDate);
    }

    /**
     * @param nowYear
     * @return
     */
    @Override
    public List<F21041Dto> getTalk(String stuId, Date nowYear, Integer limit, Integer page) {
        return f21041Dao.getTalk(stuId, nowYear, limit, page);
    }

    /**
     *
     * @param id
     * @param orgId
     * @return
     */
    @Override
    public List<F21041Dto> getPop(Integer id,String orgId){
        return f21041Dao.getPop(id,orgId);
    }

}