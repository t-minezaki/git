/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.manager.dao.F21010Dao;
import jp.learningpark.modules.manager.dto.F21010Dto;
import jp.learningpark.modules.manager.service.F21010Service;
import jp.learningpark.modules.pop.dto.F04007Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2019/11/29 : lyh: 新規<br />
 * @version 1.0
 */
@Service
public class F21010ServiceImpl implements F21010Service {
    @Autowired
    F21010Dao f21010Dao;

    @Override
    public List<F21010Dto> select(String orgId, Map<String,String> paramsMap,Integer limit, Integer offset) {
        return f21010Dao.select(orgId,paramsMap,limit,offset);
    }
    @Override
    public Integer count(String orgId, Map<String,String> paramsMap) {
        return f21010Dao.count(orgId,paramsMap);
    }

    @Override
    public R getDetail(String guidReprDeliverCd, String readFlg) {
        R r = new R();
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        List<F04007Dto> detail = f21010Dao.getDetail(guidReprDeliverCd, readFlg,orgId);
        r.put("detail",detail);
        return r;
    }


}