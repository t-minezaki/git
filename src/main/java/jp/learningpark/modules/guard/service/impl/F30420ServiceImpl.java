/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.guard.dao.F30420Dao;
import jp.learningpark.modules.guard.dto.F30420Dto;
import jp.learningpark.modules.guard.service.F30420Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>チャンネル詳細</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/20 : zpa: 新規<br />
 * @version 1.0
 */
@Service
public class F30420ServiceImpl implements F30420Service {
    @Autowired
    F30420Dao f30420Dao;
    @Override
    public F30420Dto init(Integer noticeId,String guardId,String stuId) {

        return f30420Dao.init(noticeId,guardId,stuId);
    }
}
