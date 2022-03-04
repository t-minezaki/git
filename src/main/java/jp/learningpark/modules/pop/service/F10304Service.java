/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.modules.common.entity.MstBlockEntity;

import java.util.List;

/**
 * <p>そのたブロック登録画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/11 : gong: 新規<br />
 * @version 1.0
 */
public interface F10304Service {
    /**
     * <p>大分類その他ブロックの対応する小分類ブロックリストはブロックマスタをもとに取得し</p>
     *
     * @param blockId      上層ブロックID
     * @param blockTypeDiv 種類区分
     * @return
     */
    List<MstBlockEntity> getBlockListByUpplevBlockIdAndBlockTypeDiv(int blockId, String blockTypeDiv);
}
