package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F21022Dto;

import java.util.List;

public interface F21022Service {
    List<F21022Dto> init(String orgId,  String complimentDt, String stuId, Integer limit, Integer page);
    Integer getCount(String orgId,  String complimentDt, String stuId);
}
