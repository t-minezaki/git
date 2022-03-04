/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import jp.learningpark.modules.pop.dao.F10307Dao;
import jp.learningpark.modules.pop.service.F10307Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>学習単元実績登録画面 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/11 : gong: 新規<br />
 * @version 1.0
 */
@Service
public class F10307ServiceImpl implements F10307Service {

    /**
     * 学習単元実績登録画面 dao
     */
    @Autowired
    private F10307Dao f10307Dao;

    /**
     * <p>生徒ウィークリー計画実績設定を取得する。</p>
     *
     * @param id ブロックId
     * @return
     */
    @Override
    public StuWeeklyPlanPerfEntity getWithCodeMstBystuId(Integer id) {
        return f10307Dao.selectWithCodeMstBystuId(id);
    }

    /**
     * <p>コードマスタ_明細リスト</p>
     *
     * @param codKey
     * @return
     */
    @Override
    public List<MstCodDEntity> getListByCodKey(String codKey) {
        return f10307Dao.selectListByCodKey(codKey);
    }
}
