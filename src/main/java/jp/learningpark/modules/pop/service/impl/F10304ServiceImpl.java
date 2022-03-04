/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.pop.dao.F10304Dao;
import jp.learningpark.modules.pop.service.F10304Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>そのたブロック登録画面 ServiceIpml</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/11 : gong: 新規<br />
 * @version 1.0
 */
@Service
public class F10304ServiceImpl implements F10304Service {

    /**
     * そのたブロック登録画面 Dao
     */
    @Autowired
    private F10304Dao f10304Dao;

    /**
     * <p>大分類その他ブロックの対応する小分類ブロックリストはブロックマスタをもとに取得し</p>
     *
     * @param blockId      上層ブロックID
     * @param blockTypeDiv 種類区分
     * @return
     */
    @Override
    public List<MstBlockEntity> getBlockListByUpplevBlockIdAndBlockTypeDiv(int blockId, String blockTypeDiv) {
        return f10304Dao.selectBlockListByUpplevBlockIdAndBlockTypeDiv(blockId,blockTypeDiv);
    }
}
