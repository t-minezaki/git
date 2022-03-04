/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import jp.learningpark.modules.student.dao.F10002Dao;
import jp.learningpark.modules.student.dto.F10002Dto;
import jp.learningpark.modules.student.service.F10002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>F10002 生徒Myページ画面ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/04 : gong: 新規<br />
 * @version 1.0
 */
@Service
public class F10002ServiceImpl implements F10002Service {

    /**
     * 生徒Myページ画面 Dao
     */
    @Autowired
    private F10002Dao f10002Dao;

    /**
     * <p>当生徒の基本情報を取得</p>
     *
     * @param stuId 生徒ID
     * @param orgId 組織ID
     * @return 生徒情報
     */
    @Override
    public F10002Dto getStuInfo(String stuId, String orgId) {
        return f10002Dao.selectStuInfo(stuId,orgId);
    }
}
