/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.modules.manager.dto.F03004Dto;

import java.util.List;

/**
 * <p>F03002_教科書単元編集画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/01/14 : gong: 新規<br />
 * @version 1.0
 */
public interface F03004Service {
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
    List<F03004Dto> getUnitList(String schyDiv, String subjtDiv, String sectnNm, String chaptNm, String orgId, String unitNm, Integer limit, Integer page);

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
    Integer getUnitCount(String schyDiv, String subjtDiv, String sectnNm, String chaptNm, String orgId, String unitNm);
}
