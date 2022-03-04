package jp.learningpark.modules.job.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.job.entity.BTGKA1003Dto;

import java.util.List;

public interface BTGKA1003Service {
    List<BTGKA1003Dto> getUserList(String sysTime, String roleType, Integer delType);

    List<BTGKA1003Dto> getOrgList(String sysTime, Integer delType);

    List<BTGKA1003Dto> getOrgAllList(String sysTime, Integer delType);

    List<BTGKA1003Dto> getPwList(String sysTime, Integer delType);

    R downloadCSV(String codCd, List<BTGKA1003Dto> userList, String roleType);
}
