/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.service;

import jp.learningpark.modules.com.dto.F40013Dto;

import java.util.List;

/**
 * <p>教室選択画面</p >
 * <p>Service</p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/3 : xie: 新規<br />
 * @version 1.0
 */
public interface F40013Service {

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
    List<F40013Dto> getF40013DtoList(String userId, String orgNm, String orgId, Integer limit, Integer offset, String manaFlag, String brandCd,String gid,String tchCd,String gidPk);

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
    Integer getOrgCount(String userId, String orgNm, String orgId, Integer limit, Integer offset, String manaFlag, String brandCd,String gid,String tchCd,String gidPk);
}
