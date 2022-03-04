/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30113Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

/**
 * <p>塾からの連絡通知詳細画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/11: xiong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F30113Dao {
    /**
     * @param id      お知らせID
     * @param guardId 保護者ＩＤ
     * @param stuId   生徒ＩＤ
     * @return
     */
    // 2020/11/11 zhangminghao modify start
    F30113Dto getNoticeNews(@Param("id") Integer id,
                            @Param("guardId") String guardId,
                            @Param("stuId") String stuId);

    /**
     * 保護者お知らせ閲覧状況を更新する。
     *
     * @param guardReadingId    保護者お知らせ閲覧状況ＩＤ
     * @param userId     ログインユーザＩＤ
     * @param latestTime システム日時
     */
    void updateOpenedFlg(@Param("guardReadingId") Integer guardReadingId,
                         @Param("userId") String userId,
                         @Param("latestTime") Timestamp latestTime);
    // 2020/11/11 zhangminghao modify end
}

