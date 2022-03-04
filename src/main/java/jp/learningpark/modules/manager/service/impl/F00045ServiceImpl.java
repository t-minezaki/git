/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F00045Dao;
import jp.learningpark.modules.manager.dto.F00045Dto;
import jp.learningpark.modules.manager.service.F00045Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>生徒保護者関係設定修正画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/18 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
public class F00045ServiceImpl implements F00045Service {
    /**
     * 生徒保護者関係設定修正画面 Dao
     */
    @Autowired
    private F00045Dao f00045Dao;

    /**
     * 生徒、保護者一覧情報を取得
     *
     * @param stuId 生徒Id
     * @return
     */
    @Override
    public List<F00045Dto> getInitCont(String stuId) {
        return f00045Dao.getInitCont(stuId);
    }

    /**
     * 保護者一覧情報を取得
     *
     * @param guardId 保護者Id
     * @return
     */
    @Override
    public F00045Dto selectGuardInfo(String guardId) {
        return f00045Dao.selectGuardInfo(guardId);
    }
}
