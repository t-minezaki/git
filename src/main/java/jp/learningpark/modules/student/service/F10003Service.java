/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.common.entity.MstTextbEntity;
import jp.learningpark.modules.student.dto.F10003Dto;

import java.util.List;
import java.util.Map;

/**
 * <p>生徒教科書選択画 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/05 : gong: 新規<br />
 * @version 1.0
 */
public interface F10003Service {

    /**
     * <p>当生徒当学年の教科ごとの塾の教科書情報を取得</p>
     *
     * @param stuId 生徒Id
     * @param orgId 塾Id
     * @return
     */
    List<F10003Dto> getTextbDtoListOfSchByStuId(String stuId, String orgId);

    /**
     * <p>1.2 塾学習期間IDの取得</p>
     *
     * @param orgId 塾Id
     * @return
     */
    MstCrmschLearnPrdEntity getCrmschLearnPrdId(String orgId);

    /**
     * <p>生徒選択した教科書リストを取得</p>
     * @param map
     * @return
     */
    List<MstTextbEntity> getTextbListOfStuByStuIdAndOrgIdAndSchyDiv(Map<String, Object> map);

    /**
     * 登録ボタンの処理
     *
     * @param bookDto
     * @return
     */
    R updated(F10003Dto bookDto);
}
