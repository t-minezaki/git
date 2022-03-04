/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.dao;

import jp.learningpark.modules.com.dto.F40013Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p></p >
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/3 : xie: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F40013Dao {

    /**
     * @param userId 　登録ID
     * @param orgNm 　組織名
     * @param orgId 　組織ID
     * @param limit
     * @param offset
     * @param manaFlag 　マナミルフラグ
     * @param brandCd 　ブランドコード
     * @return
     */
    List<F40013Dto> getF40013DtoList(
            @Param("userId") String userId,
            @Param("orgNm") String orgNm,
            @Param("orgId") String orgId,
            @Param("limit") Integer limit, @Param("offset") Integer offset, @Param("manaFlag") String manaFlag, @Param("brandCd") String brandCd, @Param("gid") String gid, @Param("tchCd") String tchCd, @Param("gidPk") String gidPk);

    /**
     * @param userId 　登録ID
     * @param orgNm 　組織名
     * @param orgId 　組織ID
     * @param limit
     * @param offset
     * @param manaFlag 　マナミルフラグ
     * @param brandCd 　ブランドコード
     * @return
     */
    Integer selectOrgCount(
            @Param("userId") String userId,
            @Param("orgNm") String orgNm,
            @Param("orgId") String orgId,
            @Param("limit") Integer limit, @Param("offset") Integer offset, @Param("manaFlag") String manaFlag, @Param("brandCd") String brandCd, @Param("gid") String gid, @Param("tchCd") String tchCd, @Param("gidPk") String gidPk);
}
