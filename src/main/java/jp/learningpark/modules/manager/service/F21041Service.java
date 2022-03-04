package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F21041Dto;

import java.util.Date;
import java.util.List;

/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/30
 */
public interface F21041Service {
    /**
     *
     * @param stuId
     * @param startDate
     * @param endDate
     * @return
     */
    List<F21041Dto> getDegree(String stuId, Date startDate, Date endDate);

    /**
     *
     * @param stuId
     * @param startDate
     * @param endDate
     * @return
     */
    List<F21041Dto> getDegreeWeek(String stuId, Date startDate, Date endDate);

    /**
     *
     * @param stuId
     * @param startDate
     * @param endDate
     * @return
     */
    List<F21041Dto> getDegreeMonth(String stuId, Date startDate, Date endDate);

    /**
     * @param nowYear
     * @return
     */
    List<F21041Dto> getTalk(String stuId, Date nowYear, Integer limit, Integer page);

    /**
     *
     * @param id
     * @param orgId
     * @return
     */
    List<F21041Dto> getPop(Integer id ,String orgId);
}
