/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import jp.learningpark.modules.student.dao.F11005Dao;
import jp.learningpark.modules.student.dto.F11005Dto;
import jp.learningpark.modules.student.service.F11005Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>レイアウト新規作成。</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/26 : zpa: 新規<br />
 * @version 7.0
 */
@Service
public class F11005ServiceImpl implements F11005Service {
    @Autowired
    F11005Dao f11005Dao;
    @Override
    public F11005Dto init(Integer id) {
        return f11005Dao.init(id);
    }
}