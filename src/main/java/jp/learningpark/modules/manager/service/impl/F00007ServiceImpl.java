/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F00007Dao;
import jp.learningpark.modules.manager.dto.F00007Dto;
import jp.learningpark.modules.manager.dto.F00007ExportDto;
import jp.learningpark.modules.manager.service.F00007Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>生徒集計画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/01/10 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
public class F00007ServiceImpl implements F00007Service {
    /**
     * 生徒集計画面Dao
     */
    @Autowired
    F00007Dao f00007Dao;

    /**
     * 属する下位組織情報を取得
     *
     * @param brandCd      ブランドコード
     * @param upplevOrgId  条件設定用上位組織
     * @return
     */
    @Override
    public List<F00007Dto> selectLowerLevOrg(String brandCd, String upplevOrgId) {
        return f00007Dao.selectLowerLevOrg(brandCd, upplevOrgId);
    }

    /**
     * 集計人数リスト取得
     *
     * @param list 下層組織ID
     * @return
     */
    @Override
    public Map<String, String> selectUsrCount(List<F00007Dto> list) {
        return f00007Dao.selectUsrCount(list);
    }

    /**
     * エクスポートデータ取得
     *
     * @param list
     * @return
     */
    @Override
    public List<F00007ExportDto> exportFile(List<F00007Dto> list) {
        return f00007Dao.exportFile(list);
    }
}
