/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;

import java.util.List;


/**
 * <p>F01001_塾時期検索一覧画面 Service</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/05 : xiong: 新規<br />
 * @version 1.0
 */
public interface F01002Service {

    /**
     * 塾学習期間マスタ
     * @return  塾学習期間一覧情報
     */
    List<MstCodDEntity> getCodValue();

    /**
     * 塾学習期間マスタ
     * @param orgId 組織ID
     * @param id    ID
     * @return      学年区分情報
     */
    MstCrmschLearnPrdEntity getCrmList(String orgId,Integer id);

    /**
     * 塾学習期間マスタへ反映する。
     * @param mstCrmschLearnPrdEntity 塾学習期間マスタ
     * @param prdStartDy              計画期間開始日
     * @param prdEndDy                計画期間終了日
     * @param updateTime              更新日時
     * @return                        塾学習期間更新情報
     */
    R insertOrUpdate(MstCrmschLearnPrdEntity mstCrmschLearnPrdEntity, String prdStartDy, String prdEndDy, String updateTime);
}
