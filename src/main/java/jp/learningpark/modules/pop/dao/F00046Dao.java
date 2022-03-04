/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.pop.dto.F00046Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>組織検索追加画面 Dao</p >
 *
 * @author NWT : zmh <br />
 * 変更履歴 <br />
 * 2020/10/14: zmh: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00046Dao {

    /**
     * 組織IDと組織名に応じたファジークエリ組織情報
     *
     * @param orgId             照会する組織ID
     * @param orgName           照会する組織名
     * @param brandCd           ブランドコード
     * @param orgIds
     * @return ファジークエリで見つかった組織情報を返す
     */
    List<F00046Dto> findOrgListByIdOrName(@Param("orgId") String orgId,
                                          @Param("orgName") String orgName,
                                          @Param("brandCd") String brandCd,
                                          @Param("orgIds") List<String> orgIds);
}
