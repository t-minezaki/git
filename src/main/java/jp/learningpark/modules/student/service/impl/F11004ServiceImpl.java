/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import jp.learningpark.modules.student.dao.F11004Dao;
import jp.learningpark.modules.student.dto.F11004Dto;
import jp.learningpark.modules.student.service.F11004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>学習情報登録一覧</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/24 : zpa: 新規<br />
 * @version 7.0
 */
@Service
public class F11004ServiceImpl implements F11004Service {
    @Autowired
    F11004Dao f11004Dao;
    @Override
    public List<F11004Dto> init(String userId) {
        return f11004Dao.init(userId);
    }
}