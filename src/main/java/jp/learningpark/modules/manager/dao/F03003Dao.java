/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F03003Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F03003_教科書単元照会画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/01/08 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F03003Dao {
    /**
     * <p>教科書情報と教科書単元情報を表示するため、教科書マスタ、教科書デフォルトターム情報を元に、前画面引渡の教科書IDを取得し、画面で表示する。</p>
     *
     * @param textbId       教科書Id
     * @param crmLearnPrdId 塾学習期間ID
     * @param page          ページ
     * @param limit
     * @return
     */
    List<F03003Dto> selectTexdiff(@Param("textbId") Integer textbId, @Param("crmLearnPrdId") Integer crmLearnPrdId, @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * <p>教科書情報と教科書単元情報の数量取得し、画面で表示する。</p>
     *
     * @param textbId       教科書Id
     * @param crmLearnPrdId 塾学習期間ID
     * @return
     */
    Integer selectTexdiffCount(@Param("textbId") Integer textbId, @Param("crmLearnPrdId") Integer crmLearnPrdId);
}
