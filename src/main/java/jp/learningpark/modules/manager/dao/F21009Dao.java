/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21009Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F21009_出席簿指導内容情報画面 Dao</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/11/26 : yang: 新規<br />
 * @version 5.0
 */
@Mapper
public interface F21009Dao {
    /**
     * 指導報告書情報を画面に表示
     * @param attendBookCd
     * @param orgId
     * @return
     */
    List<F21009Dto> getGrdData(@Param("attendBookCd") String attendBookCd,@Param("orgId")String orgId,@Param("absDiv") String absDiv);

    /**
     * 出席簿明細から出席簿情報を取得
     * @param attendBookCd
     * @param orgId
     * @return
     */
    List<F21009Dto> getAbhData(@Param("attendBookCd") String attendBookCd,@Param("orgId")String orgId,@Param("absDiv") String absDiv);
}
