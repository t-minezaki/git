/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F20004Dao;
import jp.learningpark.modules.manager.dto.F20004Dto;
import jp.learningpark.modules.manager.service.F20004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>週別タームプラン確認画面ServiceImpl。</p >
 *
 * @author NWT : yangfan <br />
 * 変更履歴 <br />
 * 2018/10/26 : yangfan: 新規<br />
 * @version 1.0
 */
@Service
public class F20004ServiceImpl implements F20004Service {

    /**
     * F20004_週別タームプラン確認画面 Dao
     */
    @Autowired
    private F20004Dao f20004Dao;

    /**
     * <p>生徒の週毎の学習時間取得(F20004)</p>
     *
     * @param crmLearnPrdId 塾学習時間Id
     * @param stuId         生徒ID
     * @return 生徒の週毎の学習時間情報
     */
    @Override
    public List<F20004Dto> selectStuWeeklyLearnTmInfo(Integer crmLearnPrdId, String stuId) {
        return f20004Dao.selectStuWeeklyLearnTmInfo(crmLearnPrdId, stuId);
    }

    /**
     * <p>生徒基本情報</p>
     *
     * @param stuId 生徒ID
     * @param orgId 塾ID
     * @return
     */
    @Override
    public F20004Dto getStuInfo(String stuId, String orgId) {
        return f20004Dao.selectStuInfo(stuId, orgId);
    }
}
