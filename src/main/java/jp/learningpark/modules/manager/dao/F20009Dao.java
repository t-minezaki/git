/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F20009Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>F30105 理解度詳細画面 Service</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/29 : hujunjie: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F20009Dao {
    /**
     * 理解度詳細リストを取得する
     *
     * @param stuId     　生徒ID
     * @param subjtDiv  　教科区分
     * @param startDate 　週/月/年開始日
     * @param endDate   　週/月/年終了日
     */
    List<F20009Dto> selectValueByCodcd(@Param("stuId") String stuId, @Param("subjtDiv") String subjtDiv, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
