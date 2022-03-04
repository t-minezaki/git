/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;

import java.util.List;

/**
 * <p>学習単元実績登録画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/11 : gong: 新規<br />
 * @version 1.0
 */
public interface F10307Service {
    /**
     * <p>生徒ウィークリー計画実績設定を取得する。</p>
     *
     * @param id ブロックId
     * @return ブロック情報
     */
    StuWeeklyPlanPerfEntity getWithCodeMstBystuId(Integer id);

    /**
     * <p>コードマスタ_明細リスト</p>
     *
     * @param codKey
     * @return
     */
    List<MstCodDEntity> getListByCodKey(String codKey);
}
