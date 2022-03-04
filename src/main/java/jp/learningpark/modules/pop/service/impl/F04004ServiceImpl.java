/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.modules.pop.dao.F04004Dao;
import jp.learningpark.modules.pop.dto.F04004Dto;
import jp.learningpark.modules.pop.service.F04004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>配信先設定画面 ServiceImpl</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/06/18: yang: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F04004ServiceImpl implements F04004Service {
    @Autowired
    private F04004Dao f04004Dao;

    @Override
    public List<F04004Dto> getEntity(String schy, List<Integer> group, List<String> orgIdList,String stuId,String stuNm,List<String> stuIds,List<String> rowIds,List<String> stuIdList) {
        return f04004Dao.selectStuAndGuardList(schy, group, orgIdList,stuId,stuNm,stuIds,rowIds,stuIdList);
    }
}
