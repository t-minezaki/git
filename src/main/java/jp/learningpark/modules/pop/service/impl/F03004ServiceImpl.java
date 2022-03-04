/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.modules.manager.dto.F03004Dto;
import jp.learningpark.modules.pop.dao.F03004Dao;
import jp.learningpark.modules.pop.service.F03004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F03002_教科書単元編集画面 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/01/14 : gong: 新規<br />
 * @version 1.0
 */
@Service
public class F03004ServiceImpl implements F03004Service {
    /**
     * F03002_教科書単元編集画面 dao
     */
    @Autowired
    private F03004Dao f03004Dao;

    /**
     * <p>単元マスタへ単元情報を取得して表示する。</p>
     *
     * @param schyDiv  学年区分
     * @param subjtDiv 教科区分
     * @param sectnNm  節名
     * @param chaptNm  章名
     * @param orgId    組織ID
     * @param unitNm   単元名
     * @param limit
     * @param page
     * @return
     */
    @Override
    public List<F03004Dto> getUnitList(String schyDiv, String subjtDiv, String sectnNm, String chaptNm, String orgId, String unitNm, Integer limit, Integer page) {
        return f03004Dao.selectUnitList(schyDiv, subjtDiv, sectnNm, chaptNm,orgId, unitNm, limit, page);
    }

    /**
     * <p>単元マスタへ単元情報の数量を取得</p>
     *
     * @param schyDiv  学年区分
     * @param subjtDiv 教科区分
     * @param sectnNm  節名
     * @param chaptNm  章名
     * @param orgId    組織ID
     * @param unitNm   単元名
     * @return
     */
    @Override
    public Integer getUnitCount(String schyDiv, String subjtDiv, String sectnNm, String chaptNm, String orgId, String unitNm) {
        return f03004Dao.selectUnitCount(schyDiv, subjtDiv, sectnNm, chaptNm, orgId, unitNm);
    }
}
