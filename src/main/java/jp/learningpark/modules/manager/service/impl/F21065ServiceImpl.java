package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.manager.dao.F21065Dao;
import jp.learningpark.modules.manager.dto.F21065Dto;
import jp.learningpark.modules.manager.service.F21065Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class F21065ServiceImpl implements F21065Service {
    @Autowired
    F21065Dao f21065Dao;
    @Override
    public List<F21065Dto> init01(Integer noticeId, Integer limit, Integer page) {
        return f21065Dao.init01(noticeId, limit, (page - 1) * limit);
    }

    @Override
    public List<F21065Dto> init02(String orgIds,Integer noticeId, Integer limit, Integer page) {
        return f21065Dao.init02(orgIds, noticeId, limit, (page - 1) * limit);
    }

    @Override
    public List<MstOrgEntity> getOrg(String brandCd, String orgId) {
        return f21065Dao.getOrg(brandCd,orgId);
    }
}
