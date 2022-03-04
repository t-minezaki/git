package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.manager.dto.F21065Dto;

import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/21 ： NWT)hxl ： 新規作成
 * @date 2020/05/21 16:08
 */
public interface F21065Service {
    List<F21065Dto> init01(Integer messageId, Integer limit, Integer page);
    List<F21065Dto> init02(String orgIds,Integer messageId, Integer limit, Integer page);
    List<MstOrgEntity> getOrg(String brandCd, String orgId);
}
