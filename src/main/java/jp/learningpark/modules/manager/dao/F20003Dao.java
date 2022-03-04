/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F20003Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F20003_タームプラン作成画面 Dao</p >
 *
 * @author NWT : huangyong <br />
 * 変更履歴 <br />
 * 2018/10/23 : huangyong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F20003Dao {

    /**
     * <p>生徒基本情報を取得し</p>
     *
     * @param stuId 生徒id
     * @param orgId 塾id
     * @return
     */
    F20003Dto selectStuInfoByStuId(@Param("stuId") String stuId, @Param("orgId") String orgId);

    /**
     * <p>当生徒の教科と教科書情報を取得し</p>
     *
     * @param stuId         生徒id
     * @param crmLearnPrdId 塾学習期間ID
     * @return
     */
    List<F20003Dto> selectStuTextchocList(@Param("stuId") String stuId, @Param("crmLearnPrdId") Integer crmLearnPrdId);

    /**
     * <p>当生徒の教科書情報を取得し</p>
     *
     * @param stuId         生徒id
     * @param crmLearnPrdId 塾学習期間ID
     * @return
     */
    List<String> selectTextbIdList(@Param("stuId") String stuId, @Param("crmLearnPrdId") Integer crmLearnPrdId);

    /**
     * <p>生徒タームプラン設定、教科書デフォルトターム情報から取得し、画面で表示する</p>
     *
     * @param crmLearnPrdId 塾学習期間ID
     * @param stuId         生徒id
     * @param textbId       教科書Id
     * @return
     */
    List<F20003Dto> selectDefaultAndTermPlan(@Param("crmLearnPrdId") Integer crmLearnPrdId, @Param("stuId") String stuId, @Param("textbId") Integer textbId);

    /**
     * (1) 生徒未計画の生徒タームプラン設定データをすべで物理削除する。
     *
     * @param stuId         生徒id
     * @param crmLearnPrdId 塾学習期間ID
     * @param subjtDiv      教科
     */
    void deleteByStuIdAndTextbIdsAndSubjtDiv(@Param("stuId") String stuId, @Param("crmLearnPrdId") Integer crmLearnPrdId, @Param("subjtDiv") String subjtDiv);

    /**
     * <p>登録レコード数（※）を取得する。</p>
     *
     * @param stuId         生徒id
     * @param crmLearnPrdId 塾学習期間ID
     * @param subjtDiv      教科
     * @return
     */
    List<F20003Dto> selectListOfPlanReged(@Param("stuId") String stuId, @Param("crmLearnPrdId") Integer crmLearnPrdId, @Param("subjtDiv") String subjtDiv);

}
