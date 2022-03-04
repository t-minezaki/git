/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;

import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.guard.dto.F30410Dto;

/**
 * <p>F30410_保護者面談の申込内容キャンセル画面 Service</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/22: yang: 新規<br />
 * @version 1.0
 */
public interface F30410Service {

    /**
     * 初期化
     *
     * @param eventId
     * @param stuId
     * @param guardId
     * @return
     */
    F30410Dto getGuardEventApplySts(Integer eventId, String stuId, String guardId,String refType);

    /**
     * 申込をキャンセルボタン押下時
     *
     * @param eventId
     * @param stuId
     * @param guardId
     * @return
     */
    GuardEventApplyStsEntity getDeleteOne(Integer eventId, String stuId, String guardId);

}
