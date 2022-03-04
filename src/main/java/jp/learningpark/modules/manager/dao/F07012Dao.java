/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F07012Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>F07012 成績教科追加・編集画面 Dao</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/04/26 : yang: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F07012Dao {
    /**
     * 取得するmstcodEntity
     *
     * @param codCd 教科CD
     * @return
     */
    F07012Dto mstcodEntity(@Param("codCd") String codCd);
}
