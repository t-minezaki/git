/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.guard.dto.F30410Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>F30409_保護者面談の申込内容変更・キャンセル画面 Dao</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/16: yang: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F30410Dao {
    /**
     * 初期化
     *
     * @param eventId
     * @param stuId
     * @param guardId
     * @return
     */
    F30410Dto getGuardEventApplySts(@Param("eventId") Integer eventId, @Param("stuId") String stuId, @Param("guardId") String guardId,@Param("refType") String refType);

    /**
     * 申込をキャンセルボタン押下時
     *
     * @param eventId
     * @param stuId
     * @param guardId
     * @return
     */
    GuardEventApplyStsEntity getDeleteOne(@Param("eventId") Integer eventId, @Param("stuId") String stuId, @Param("guardId") String guardId);

}

