/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.manager.dao.F04015Dao;
import jp.learningpark.modules.manager.dto.F04015Dto;
import jp.learningpark.modules.manager.service.F04015Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F04015_配信先既読未読状態確認一覧画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/17 : zpa: 新規<br />
 * @version 1.0
 */
@Service
public class F04015ServiceImpl implements F04015Service {
    @Autowired
    F04015Dao f04015Dao;
    @Override
    public List<F04015Dto> init01(Integer noticeId, Integer limit, Integer page) {
        return f04015Dao.init01(noticeId,limit,page);
    }

    @Override
    public Integer init01Count(Integer noticeId) {
        return f04015Dao.init01Count(noticeId);
    }

    @Override
    public List<F04015Dto> init02(String orgIds,Integer noticeId, Integer limit, Integer page) {
        return f04015Dao.init02(orgIds, noticeId,limit,page);
    }

    @Override
    public Integer init02Count(String orgIds, Integer noticeId) {
        return f04015Dao.init02Count(orgIds, noticeId);
    }

    @Override
    public List<MstOrgEntity> getOrg(String brandCd, String orgId) {
        return f04015Dao.getOrg(brandCd,orgId);
    }
}
