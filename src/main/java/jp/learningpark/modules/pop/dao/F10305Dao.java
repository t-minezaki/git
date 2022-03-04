/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.pop.dto.F10305Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>教科書デフォルトターム情報 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/05 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F10305Dao {
    /**
     * <p>当ブロックの単元情報を取得</p>
     *
     * @param wppId        生徒ウィークリー計画実績設定Id
     * @param orgId        塾ID
     * @param blockTypeDiv ブロック種類区分
     * @return
     */
    F10305Dto selectTextbDefByUnitiId(@Param("wppId") Integer wppId, @Param("orgId") String orgId, @Param("blockTypeDiv") String blockTypeDiv);

}
