/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service;

import jp.learningpark.modules.student.dto.F10002Dto;

/**
 * <p>F10002 生徒Myページ画面Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/04 : gong: 新規<br />
 * @version 1.0
 */
public interface F10002Service {
    /**
     * <p>当生徒の基本情報を取得</p>
     *
     * @param stuId 生徒ID
     * @param orgId 組織ID
     * @return 生徒情報
     */
    F10002Dto getStuInfo(String stuId, String orgId);
}
