package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F21013Dto;

import java.util.List;
import java.util.Set;

public interface F21013Service {
    //初期クエリ
    List<F21013Dto> select(String orgId,String userId,String month, String roleDiv,Set<String> stuIdListLast,String years);
    //プロジェクトの変更を示します
    List<F21013Dto> reSelect(String orgId, Set<String> stuIdList, String value, String cd, String month, Set<String> stuIdListLast, String roleDiv,String years);
    //cdは10
    List<F21013Dto> selectTen(String orgId,String month, Set<String> stu, String cd,String years);

}
