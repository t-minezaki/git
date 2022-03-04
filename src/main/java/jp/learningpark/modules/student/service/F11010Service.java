package jp.learningpark.modules.student.service;

import jp.learningpark.modules.student.dto.F11010Dto;

import java.util.List;

public interface F11010Service {
    /**
     * 初期化
     * @param stuId     生徒Id
     * @param limit     limit
     * @param page      page
     * @return
     */
    List<F11010Dto> getList(String stuId, Integer limit, Integer page);

    Integer getCount(String stuId);

    /**
     * 未読カウント数
     * @param stuId     生徒Id
     * @return
     */
    Integer getUnreadCount(String stuId);
}
