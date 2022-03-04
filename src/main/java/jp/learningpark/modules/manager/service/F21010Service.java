package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F21010Dto;

import java.util.List;
import java.util.Map;

public interface F21010Service {
    List<F21010Dto> select(String orgId, Map<String,String> paramsMap,Integer limit, Integer offset);
    Integer count(String orgId, Map<String,String> paramsMap);
    R getDetail(String guidReprDeliverCd, String readFlg);
}
