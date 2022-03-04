/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30423Dto;
import org.apache.ibatis.annotations.Param;

/**
 * <p>褒めポイント詳細画面 Dao</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/03/05 : wq: 新規<br />
 * @version 1.0
 */
public interface F30423Dao {

    /**
     * @param noticeId お知らせID
     * @param orgId 組織ID
     * @param stuId 生徒ID
     * @return
     */
    F30423Dto selectInitData(@Param("noticeId") Integer noticeId, @Param("orgId") String orgId, @Param("stuId") String stuId);
}
