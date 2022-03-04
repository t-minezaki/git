/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F11017Dto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>生徒面談の申込内容変更・キャンセル画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/14 : wq: 新規<br />
 * @version 1.0
 */
public interface F11017Dao {

    /**
     * <p>生徒イベント申込状況情報を取得</p>
     *
     * @param stuId 生徒ID
     * @param flg 判断マーク
     * @return
     */
    List<F11017Dto> getInitApplyData(@Param("stuId") String stuId, @Param("flg") Integer flg);
}
