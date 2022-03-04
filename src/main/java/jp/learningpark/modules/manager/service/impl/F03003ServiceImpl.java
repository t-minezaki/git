/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F03003Dao;
import jp.learningpark.modules.manager.dto.F03003Dto;
import jp.learningpark.modules.manager.service.F03003Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F03003_教科書単元照会画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/01/08 : gong: 新規<br />
 * @version 1.0
 */
@Service
public class F03003ServiceImpl implements F03003Service {
    /**
     * F03003_教科書単元照会画面 Dao
     */
    @Autowired
    private F03003Dao f03003Dao;

    /**
     * <p>教科書情報と教科書単元情報を表示するため、教科書マスタ、教科書デフォルトターム情報を元に、前画面引渡の教科書IDを取得し、画面で表示する。</p>
     *
     * @param textbId       教科書Id
     * @param crmLearnPrdId 塾学習期間ID
     * @param page          ページ
     * @param limit
     * @return
     */
    @Override
    public List<F03003Dto> getTexdiff(Integer textbId, Integer crmLearnPrdId, Integer page, Integer limit) {
        return f03003Dao.selectTexdiff(textbId, crmLearnPrdId, page, limit);
    }

    /**
     * <p>教科書情報と教科書単元情報の数量取得し、画面で表示する。</p>
     *
     * @param textbId       教科書Id
     * @param crmLearnPrdId 塾学習期間ID
     * @return
     */
    @Override
    public Integer getTexdiffCount(Integer textbId, Integer crmLearnPrdId) {
        return f03003Dao.selectTexdiffCount(textbId, crmLearnPrdId);
    }
}
