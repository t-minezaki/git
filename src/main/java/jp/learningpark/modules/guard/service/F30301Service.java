/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;


/**
 * <p>F30301_トップニュース一覧画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/02/21 : xiong: 新規<br />
 * @version 1.0
 */
public interface F30301Service {

    /**
     * お知らせ未読カウント数
     * @param guardId
     * @param stuId
     * @param orgId
     * @return
     */
    Integer getNewsCount(String guardId,String stuId,String orgId);
}
