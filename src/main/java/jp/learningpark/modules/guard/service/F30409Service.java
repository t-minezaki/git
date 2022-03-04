/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;


import jp.learningpark.modules.guard.dto.F30409Dto;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>F30409_保護者面談の申込内容変更・キャンセル画面 Service</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/16: yang: 新規<br />
 * @version 1.0
 */
public interface F30409Service {

    /**
     * 初期化
     *
     * @param guardId 保護者ＩＤ
     * @param systemTime システム日時
     * @param flg 判断マーク
     * @param stuId 生徒ID
     * @return
     */
    List<F30409Dto> getStuList(String guardId, Timestamp systemTime,Integer flg, String stuId);

}
