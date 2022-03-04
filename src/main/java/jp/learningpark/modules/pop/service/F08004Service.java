/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.pop.dto.F08004Dto;

import java.util.List;

/**
 * <p>F08004 </p>
 *
 * @author NWT : zhaoxiaoqin <br />
 * 2019/09/23 : zhaoxiaoqin: 新規<br />
 * @version 1.0
 */
public interface F08004Service {

    /**
     *
     * @param eventId イベントID
     * @return F08004Dto
     */
    F08004Dto getEventEntity(Integer eventId);

    /**
     *
     * @param eventId イベントID
     * @return List<MstOrgEntity>
     */
    List<MstOrgEntity> getOrgList(Integer eventId);
}
