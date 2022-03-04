/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.service.impl;

import jp.learningpark.modules.com.dao.F40012Dao;
import jp.learningpark.modules.com.service.F40012Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>生徒共通メニュー画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/23 : zpa: 新規<br />
 * @version 7.0
 */
@Service
public class F40012ServiceImpl implements F40012Service {
@Autowired
F40012Dao f40012Dao;
    @Override
    public Integer getNum(String orgId, String userId) {
        return f40012Dao.getNum(orgId, userId);
    }
}