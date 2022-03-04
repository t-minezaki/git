/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service;

import jp.learningpark.modules.student.dto.F11018Dto;

import java.util.List;

/**
 * <p>生徒面談の申込内容キャンセル画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/14 : wq: 新規<br />
 * @version 1.0
 */
public interface F11018Service {

    /**
     * @param applyId 生徒イベント申込状況.ID
     * @param stuId 生徒ID
     * @param refType 関連タイプ
     * @return 面談記録情報
     */
    F11018Dto selectInitTalkData(Integer applyId, String stuId, String refType);

    /**
     * @param talkId 面談記録.ID
     * @return 面談記録詳細情報
     */
    List<F11018Dto> selectInitTalkDelData(Integer talkId);
}
