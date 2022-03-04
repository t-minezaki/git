/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F21022Dao;
import jp.learningpark.modules.manager.dto.F21022Dto;
import jp.learningpark.modules.manager.service.F21022Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>ほめポイント登録画面</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/02/13 : lyh: 新規<br />
 * @version 6.0
 */
@Service
public class F21022ServiceImpl implements F21022Service {
    @Autowired
    F21022Dao f21022Dao;

    /**
     *
     * @param orgId         組織ＩＤ
     * @param complimentDt  システム日付-60
     * @param stuId         生徒ID
     * @param limit
     * @param page
     * @return
     */
    @Override
    public List<F21022Dto> init(String orgId, String complimentDt, String stuId, Integer limit, Integer page){
        return f21022Dao.init(orgId,complimentDt,stuId, limit, page);
    }
    @Override
    public Integer getCount(String orgId, String complimentDt, String stuId){
        return f21022Dao.getCount(orgId,complimentDt,stuId);
    }
}