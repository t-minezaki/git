/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;

import jp.learningpark.modules.guard.dto.F30420Dto;

/**
 * <p>チャンネル詳細</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/20 : zpa: 新規<br />
 * @version 1.0
 */
public interface F30420Service {
    F30420Dto init(Integer noticeId,String guardId,String stuId);
}
