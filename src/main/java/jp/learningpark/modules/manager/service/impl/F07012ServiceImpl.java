/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F07012Dao;
import jp.learningpark.modules.manager.dto.F07012Dto;
import jp.learningpark.modules.manager.service.F07012Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>F07012 成績教科追加・編集画面 ServiceImpl</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/04/26 : yang: 新規<br />
 * @version 1.0
 */
@Service
public class F07012ServiceImpl implements F07012Service {
    /**
     * F07012Dao
     */
    @Autowired
    private F07012Dao f07012Dao;

    /**
     *
     * @param codCd 教科CD
     * @return
     */
    @Override
    public F07012Dto getF07012Entity(String codCd) {
        return f07012Dao.mstcodEntity(codCd);
    }
}
