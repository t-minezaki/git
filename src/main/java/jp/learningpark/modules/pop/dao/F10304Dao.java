/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.common.entity.MstBlockEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>そのたブロック登録画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/05 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F10304Dao {
    /**
     * <p>大分類その他ブロックの対応する小分類ブロックリストはブロックマスタをもとに取得し</p>
     *
     * @param blockId      上層ブロックID
     * @param blockTypeDiv 種類区分
     * @return
     */
    List<MstBlockEntity> selectBlockListByUpplevBlockIdAndBlockTypeDiv(@Param("blockId") int blockId,@Param("blockTypeDiv") String blockTypeDiv);

}
