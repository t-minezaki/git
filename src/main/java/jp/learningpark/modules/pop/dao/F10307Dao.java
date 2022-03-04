/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>学習単元実績登録画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/05 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F10307Dao {
    /**
     * <p>生徒ウィークリー計画実績設定を取得する。</p>
     *
     * @param id ブロックId
     * @return ブロック情報
     */
    StuWeeklyPlanPerfEntity selectWithCodeMstBystuId(Integer id);

    /**
     * <p>コードマスタ_明細リスト</p>
     *
     * @param codKey
     * @return
     */
    List<MstCodDEntity> selectListByCodKey(String codKey);

}
