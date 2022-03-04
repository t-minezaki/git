/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.service;

import jp.learningpark.modules.com.dto.F40018Dto;

/**
 * <p>ワンタイムパスワード入力画面</p >
 * <p>Service</p>
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/3 : xie: 新規<br />
 * @version 1.0
 */
public interface F40018Service {
    F40018Dto authenticationPassword(String userId);

    void update(String userId, String mailad, String roleDiv);
}
