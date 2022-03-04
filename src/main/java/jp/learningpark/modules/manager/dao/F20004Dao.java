/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F20004Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F20004_週別タームプラン確認画面 Dao</p >
 *
 * @author NWT : huangyong <br />
 * 変更履歴 <br />
 * 2018/10/23 : huangyong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F20004Dao {
    /**
     * <p>生徒の週毎の学習時間取得(F20004)</p>
     *
     * @param crmLearnPrdId 塾学習時間Id
     * @param stuId 生徒ID
     * @return 生徒の週毎の学習時間情報
     */
    List<F20004Dto> selectStuWeeklyLearnTmInfo(@Param("crmLearnPrdId") Integer crmLearnPrdId, @Param("stuId") String stuId);

    /**
     * <p>生徒基本情報</p>
     * @param stuId 生徒ID
     * @param orgId 塾ID
     * @return
     */
    F20004Dto selectStuInfo(@Param("stuId") String stuId,@Param("orgId") String orgId);
}
