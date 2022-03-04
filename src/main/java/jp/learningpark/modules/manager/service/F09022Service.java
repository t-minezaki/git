package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F09022Dto;

import java.util.List;

public interface F09022Service {
    //初期表示
    List<F09022Dto> init(String orgId,Integer noticeId,String flg);
}
