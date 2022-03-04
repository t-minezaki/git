/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

import jp.learningpark.modules.common.entity.StuFixdSchdlEntity;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/04/27 : lyh: 新規<br />
 * @version 1.0
 */
public class F11002Dto extends StuFixdSchdlEntity {

    /**
     * ブロック種類区分
     */
    private String blockType;

    /**
     * ブロック種類区分を取得する
     *
     * @return blockType ブロック種類区分
     */
    public String getBlockType() {
        return this.blockType;
    }

    /**
     * ブロック種類区分を設定する
     *
     * @param blockType ブロック種類区分
     */
    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }
}