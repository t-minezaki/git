package jp.learningpark.modules.student.service;

import jp.learningpark.modules.student.dto.F11001Dto;

import java.util.List;

/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/24
 */
public interface F11001Service {
    /**
     *
     * @param stuId
     * @return
     */
    List<F11001Dto> getSubjt(String stuId);

}
