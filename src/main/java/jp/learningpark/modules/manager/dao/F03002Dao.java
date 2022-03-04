/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F0300202Dto;
import jp.learningpark.modules.manager.dto.F03002Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F03002_教科書単元編集画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/01/08 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F03002Dao {
    /**
     * <p>教科書情報と教科書単元情報を表示するため、教科書マスタ、教科書デフォルトターム情報を元に、前画面引渡の教科書IDを取得し、画面で表示する。</p>
     *
     * @param textbId       教科書Id
     * @param crmLearnPrdId 塾学習期間ID
     * @return              教科書情報
     */
    List<F03002Dto> selectTexdiff(@Param("textbId") Integer textbId, @Param("crmLearnPrdId") Integer crmLearnPrdId);

    /**
     * 生徒タームプラン設定に計画済み件数を取得
     *
     * @tdtiId 教科書デフォルトターム情報．ID
     * @return 計画済み件数
     */
    Integer selectPlanReg(@Param("tdtiId") Integer tdtiId);

    /**
     * 生徒タームプラン設定に生徒ごとの計画済み件数を取得する。
     *
     * @param textbDefaultId 教科書デフォルトId
     * @return               生徒ごとの計画済み件数
     */
    List<F0300202Dto> selectStuOfPlanedCountList(Integer textbDefaultId);
}
