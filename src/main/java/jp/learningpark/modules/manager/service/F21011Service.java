package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F21011Dto;

import java.util.List;

public interface F21011Service {
    List<F21011Dto> select(String orgId,String guidRepeCd,Integer page,Integer limit);
    List<F21011Dto> selectTotal(String orgId,String guidRepeCd);
    List<F21011Dto> reselect(Object stuIdList,Integer page,Integer limit);
    List<F21011Dto> reselectTotal(Object stuIdList);
    List<F21011Dto> selectStatus();
}
