package jp.learningpark.modules.student.service;

import jp.learningpark.modules.student.dto.F11013Dto;

/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/05/12
 */
public interface F11013Service {
    /**
     *
     * @param eventId
     * @return
     */
    F11013Dto init(Integer eventId);

    /**
     *
     * @return
     */
    String count();
}
