/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30105Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>F30105 理解度詳細画面 Dao</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/11/27 : wen: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F30105Dao {

    /**
     * 理解度詳細リストを取得する
     *
     * @param stuId     　生徒ID
     * @param subjtDiv  　教科区分
     * @param startDate 　週/月/年開始日
     * @param endDate   　週/月/年終了日
     */
    List<F30105Dto> selectValueByCodcd(@Param("stuId") String stuId, @Param("subjtDiv") String subjtDiv, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
