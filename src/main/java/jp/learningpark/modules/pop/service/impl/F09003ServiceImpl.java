/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.modules.pop.dao.F09003Dao;
import jp.learningpark.modules.pop.dto.F09003Dto;
import jp.learningpark.modules.pop.service.F09003Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>F09003_対象者選択画面 ServiceImpl</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/11/28 : yang: 新規<br />
 * @version 5.0
 */
@Transactional
@Service
public class F09003ServiceImpl implements F09003Service {
    /**
     * F09003 Dao
     */
    @Autowired
    F09003Dao f09003Dao;

    /**
     * 検索
     * @param orgId
     * @param paramsMap
     * @return
     */
    @Override
    public List<F09003Dto> getStuList(String orgId,Map<String, String> paramsMap,String userId,String roleDiv) {
        return f09003Dao.getStuList(orgId,paramsMap,userId,roleDiv);
    }
}
