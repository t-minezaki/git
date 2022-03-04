package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.pop.dao.F21074Dao;
import jp.learningpark.modules.pop.dto.F21074Dto;
import jp.learningpark.modules.pop.service.F21074Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>配信先選択画面（POP） Service</p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/06/04 ： NWT)hxl ： 新規作成
 * 2020/11/09 ： NWT)文 ： 要件変更
 */
@Service
public class F21074ServiceImpl implements F21074Service {

    /**
     * 配信先選択画面（POP）Dao
     */
    @Autowired
    private F21074Dao f21074Dao;

    /**
     * @param type
     * @param searchName
     * @param orgIdList
     * @return
     */
    @Override
    public R getEntity(String type, String searchName, String loginId, List<String> orgIdList) {
        Integer total = f21074Dao.selectUserCount(searchName, loginId, orgIdList);
        if (total == 0) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0017", "対象ユーザー"));
        }
        List<F21074Dto> personList = f21074Dao.selectManagerAndMentorList(searchName, loginId, orgIdList);
        return R.ok().put("personList", personList);
    }

    /**
     * @param searchName search name
     * @param orgIdList 組織IDList
     * @return
     */
    @Override
    public Integer selectUserCount(String searchName, String loginId, List<String> orgIdList) {
        return f21074Dao.selectUserCount(searchName, loginId, orgIdList);
    }
}
