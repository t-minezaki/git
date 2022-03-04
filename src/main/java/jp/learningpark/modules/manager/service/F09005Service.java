package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.CM0005;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.manager.dto.F09005Dto;

import java.util.Date;
import java.util.List;

public interface F09005Service {
    /**
     * @param stuidlist 選択されたstuidの集合
     * @return List<F09005Dto>
     */
    List<F09005Dto> init(List<String> stuidlist,String status);
    MstNoticeEntity save(List<F09005Dto> f09005DtoList, Date time, String status, CM0005.PointVo pointVo);
    R sendMessage(List<F09005Dto> f09005DtoList, MstNoticeEntity mstNoticeEntity, Date time,String status);
}
