package jp.learningpark.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.modules.common.dao.MstUsrDao;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.sys.dao.SysUserDao;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import jp.learningpark.modules.sys.service.ShiroService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private SysUserDao sysUserDao;
    /**
     * 組織マスタService
     */
    @Autowired
    MstOrgService mstOrgService;

    /**
     * commonService
     */
    @Autowired
    CommonService commonService;

    /**
     * mstUsrDao
     */
    @Autowired
    MstUsrDao mstUsrDao;
    /**
     * ユーザー権限を取得する
     *
     * @param id ユーザーID
     * @return
     */
    @Override
    public Set<String> getUserPermissions(long id) {

        List<String> permsList = sysUserDao.selectAllPerms(id);

        // ユーザー権限リスト
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    /**
     * ユーザー情報を取得する
     *
     * @param id ユーザーマスターのID
     * @return
     */
    @Override
    public SysUserEntity getUserById(Integer id) {
        return sysUserDao.selectUserById(id);
    }

    /**
     * ユーザー情報を取得する
     *
     * @param userId ユーザーID
     * @return
     */
    @Override
    public SysUserEntity getUserByUserId(String userId) {
        return sysUserDao.selectUserByUserId(userId);
    }

    /**
     * ユーザー情報を取得する
     *
     * @param userCd  ユーザーID
     * @param brandCd ブランドコード
     * @return
     */
    @Override
    public List<SysUserEntity> getUserByBrandCd(String userCd, String brandCd) {
        return sysUserDao.selectUserByBrandCd(userCd, brandCd);
    }

    /**
     * ユーザー情報を取得する
     *
     * @param userCd  ユーザーID
     * @return
     */
    @Override
    public List<SysUserEntity> getUserByLoginId(String userCd, String manaFlag, String brandCd, String gidFlag, String tchCdFlg) {
        return sysUserDao.selectUserByLoginId(userCd, manaFlag, brandCd, gidFlag, tchCdFlg);
    }

    /**
     * ユーザー役割を取得する
     *
     * @param userId ユーザーID
     * @return
     */
    @Override
    public String getUserRole(long userId) {
        return sysUserDao.selectUserRole(userId);
    }

    /**
     * 子供個数の取得
     *
     * @param guardId 保護者ID
     * @return
     */
    @Override
    public Map<String, Object> getStuCountByGuardId(String guardId) {
        return sysUserDao.selectStuCountByGuardId(guardId);
    }

    /**
     * 塾学習期間IDの取得
     *
     * @param userId ユーザーID
     * @return
     */
    @Override
    public Integer getLearnPrdIdByUserId(String userId, String orgId) {
        //階層
        Integer level = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id",orgId).eq("del_flg",0)).getLevel();
        //塾学習期間IDの取得
        Integer prdId = sysUserDao.selectLearnPrdIdByUserId(userId,orgId);
        if (prdId == null){
            for (int i = level - 1; i > 0 ; i--) {
                orgId = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id",orgId).eq("del_flg",0)).getUpplevOrgId();
                prdId = sysUserDao.selectLearnPrdIdByUserId(userId,orgId);
                if (prdId != null){
                    return prdId;
                }
            }
        }
        return prdId;
    }

    /**
     * ユーザーマスタより、ロックエラー回数を更新する。
     *
     * @param loginId ユーザーID
     * @return
     */
    @Override
    public Integer updateErrorCount(String loginId, int errorCount) {
        return sysUserDao.addErrorCount(loginId, errorCount);
    }

    /**
     * ユーザーマスタより、ロックフラグを更新する。
     *
     * @param loginId ユーザーID
     * @return
     */
    @Override
    public Integer updateLockFlg(String loginId) {
        return sysUserDao.setLockFlg(loginId);
    }

    /**
     * 指導者のGIDとGIDPKを更新する。
     *
     * @param tchCd
     * @param gidRuleFlag
     * @param updatime
     * @param usrNm
     * @param gidpk
     * @param gid
     * @return
     */
    @Override
    public Integer updateGidpkByTchCd(String tchCd, String usrNm, String gidpk, String gid, String gidRuleFlag, Timestamp updatime) {
        return sysUserDao.updateGidpkByTchCd(tchCd, usrNm, gidpk, gid, gidRuleFlag, updatime);
    }

    /**
     * 基本マスタ更新
     *
     * @param afterUsrId ユーザーID
     * @return
     */
    @Override
    public Integer updateMailAddress(String afterUsrId, String roleDiv, String mailad, String usrId) {
        //2020/11/11 huangxinliang modify start
        return sysUserDao.updateMailAddress(afterUsrId, roleDiv, mailad, usrId);
        //2020/11/11 huangxinliang modify end
    }

    /**
     * 各ロールのメールアドレス取得処理
     *
     * @param roleDiv
     * @param brandCd
     * @param afterUsrId
     * @return
     */
    @Override
    public List<String> getMailAddress(String roleDiv, String brandCd, String afterUsrId) {
        return sysUserDao.selectEmailAddress(roleDiv, brandCd, afterUsrId);
    }

    /**
     * 組織IDとログインユーザーIDでユーザーを検索する
     *
     * @param afterUsrId
     * @param orgId
     * @return
     */
    @Override
    public SysUserEntity selectUserByOrgIdAndAfterUsrId(String afterUsrId, String orgId) {

        return sysUserDao.selectUserByOrgIdAndAfterUsrId(afterUsrId, orgId);
    }

    /**
     * 組織IDとログインthcCdでユーザーを検索する
     *
     * @param tchCd
     * @param orgId
     * @return
     */
    @Override
    public SysUserEntity selectUserByOrgIdAndTchCd(String tchCd, String orgId) {

        return sysUserDao.selectUserByOrgIdAndTchCd(tchCd, orgId);
    }
    //NWT　楊　2021/07/01　MANAMIRU1-699 --> 727　Edit　Start
    @Override
    public SysUserEntity getUserByGidpk(String gidpk, String orgId) {
        return sysUserDao.selectUserByGidpk(gidpk,  orgId);
    }
    //NWT　楊　2021/07/01　MANAMIRU1-699 --> 727　Edit　End

    @Override
    public List<MstUsrEntity> gidpkCount(String gidpk) {
        //NWT　楊　2021/07/01　MANAMIRU1-699 --> 727　Edit　End
        return mstUsrDao.selectList(new QueryWrapper<MstUsrEntity>().select("usr_id, role_div","gidPk").eq("gidpk", gidpk).
                eq("del_flg", 0).eq("gid_flg", "1"));
        //NWT　楊　2021/07/01　MANAMIRU1-699 --> 727　Edit　End
    }
    //NWT　楊　2021/07/21　MANAMIRU1-727.④　Edit　Start
    @Override
    public List<MstUsrEntity> tchCdCount(String tchCd,String orgId) {
        return sysUserDao.tchCdCount(tchCd,orgId);
    }
    //NWT　楊　2021/07/21　MANAMIRU1-727.④　Edit　End

    @Override
    public List<MstUsrEntity> selectStudentGidkpList(List<String> guardUsrIdList) {
        return sysUserDao.selectStudentGidpkList(guardUsrIdList);
    }
    /**
     * ユーザー情報を取得する
     *
     * @param userCd  ユーザーID
     * @return
     */
    @Override
    public List<SysUserEntity> getSamlUserByLoginId(String userCd) {
        return sysUserDao.selectSamlUserByLoginId(userCd);
    }
    /**
     * tcd非初回登録件数取得
     * NWT　楊　2021/07/15　MANAMIRU1-727　Edit
     * @param tchCd
     * @return
     */
    @Override
    public Integer selectFirstLoginUsrByTchcd(String tchCd) {
        return sysUserDao.selectFirstLoginUsrByTchcd(tchCd);
    }
}
