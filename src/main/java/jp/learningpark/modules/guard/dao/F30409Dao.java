/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30409Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>F30409_保護者面談の申込内容変更・キャンセル画面 Dao</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/16: yang: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F30409Dao {
    /**
     * 保護者が各子供のイベント申込情報を確認するため、保護者申込状況マスタから情報を取得
     * @param guardId 保護者ＩＤ
     * @param systemTime システム日時
     * @param flg 判断マーク
     * @param stuId 生徒ID
     * @return
     */
    List<F30409Dto> getStuList(@Param("guardId")String guardId, @Param("systemTime") Timestamp systemTime,@Param("flg")Integer flg,@Param("stuId")String stuId);
}