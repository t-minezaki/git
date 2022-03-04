/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F04005Dto;

import java.util.List;

/**
 * <p>F04005 塾ニュース照会画面 Service</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/14 : wen: 新規<br />
 * @version 1.0
 */
public interface F04005Service {

    /**
     * @param id    セッションデータ.ID
     * @return
     */
    List<F04005Dto> getStuList(Integer id,String orgId);
}
