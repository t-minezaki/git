/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.service.impl;

import jp.learningpark.modules.com.dao.F40013Dao;
import jp.learningpark.modules.com.dto.F40013Dto;
import jp.learningpark.modules.com.service.F40013Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>教室選択画面</p>
 * <p>ServiceImpl</p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/3 : xie: 新規<br />
 * @version 1.0
 */
@Service
public class F40013ServiceImpl implements F40013Service {

    /**
     * F40013Dao
     */
    @Autowired
    F40013Dao f40013Dao;

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
    @Override
    public List<F40013Dto> getF40013DtoList(String userId, String orgNm, String orgId, Integer limit, Integer offset, String manaFlag, String brandCd,String gid, String tchCd,String gidPk) {
        return f40013Dao.getF40013DtoList(userId, orgNm, orgId, limit, offset, manaFlag, brandCd, gid, tchCd, gidPk);
    }

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
    @Override
    public Integer getOrgCount(String userId, String orgNm, String orgId, Integer limit, Integer offset, String manaFlag, String brandCd,String gid, String tchCd,String gidPk) {
        return f40013Dao.selectOrgCount(userId, orgNm, orgId, limit, offset, manaFlag, brandCd, gid, tchCd, gidPk);
    }
}
