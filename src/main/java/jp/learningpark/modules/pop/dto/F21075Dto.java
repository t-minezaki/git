/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dto;

import jp.learningpark.modules.common.entity.MstAskTalkEventEntity;

/**
 * <p>面談記録画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/25 : wq: 新規<br />
 * @version 1.0
 */
public class F21075Dto extends MstAskTalkEventEntity {

    /**
     * 回答結果内容
     */
    private String content;

    /**
     * 回答結果内容を取得する
     *
     * @return content 回答結果内容
     */
    public String getContent() {
        return this.content;
    }

    /**
     * 回答結果内容を設定する
     *
     * @param content 回答結果内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}
