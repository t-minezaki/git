/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F00062Dao;
import jp.learningpark.modules.manager.dto.F00062Dto;
import jp.learningpark.modules.manager.service.F00062Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>生徒保護者関係設定修正画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/18 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
public class F00062ServiceImpl implements F00062Service {
    /**
     * 生徒保護者関係設定修正画面 Dao
     */
    @Autowired
    private F00062Dao f00062Dao;

    /**
     * 生徒、メンター一覧情報を取得
     *
     * @param id Id
     * @return
     */
    @Override
    public F00062Dto getInitCont(Integer id) {
        return f00062Dao.getInitCont(id);
    }

    /**
     * メンター一覧情報を取得
     *
     * @param mentorId メンターId
     * @return
     */
    @Override
    public F00062Dto selectMentorInfo(String mentorId) {
        return f00062Dao.selectMentorInfo(mentorId);
    }

    /**
     * 生徒一覧情報を取得
     *
     * @param stuId 生徒Id
     * @return
     */
    @Override
    public F00062Dto selectStuInfo(String stuId) {
        return f00062Dao.selectStuInfo(stuId);
    }
}
