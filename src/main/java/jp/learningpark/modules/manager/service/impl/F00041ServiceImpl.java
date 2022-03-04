/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.utils.dao.CommonDao;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.manager.dao.F00041Dao;
import jp.learningpark.modules.manager.dto.F00041Dto;
import jp.learningpark.modules.manager.service.F00041Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>ユーザー基本情報一覧画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/13 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
public class F00041ServiceImpl implements F00041Service {

    /**
     * ユーザー基本情報一覧画面Dao
     */
    @Autowired
    F00041Dao f00041Dao;

    /**
     * ユーザー基本マスタ　Dao
     */
    @Autowired
    MstUsrDao mstUsrDao;

    /**
     * ユーザ初期パスワード管理 Dao
     */
    @Autowired
    MstUsrFirtPwDao mstUsrFirtPwDao;

    /**
     * 組織基本マスタ　Dao
     */
    @Autowired
    MstOrgDao mstOrgDao;

    /**
     * コードマスタ明細　Dao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * 共通
     */
    @Autowired
    CommonDao commonDao;

    /**
     * 生徒基本マスタ　Dao
     */
    @Autowired
    MstStuDao mstStuDao;

    /**
     * @param afterUsrId 変更後ユーザID
     * @param userId ユーザーID
     * @return
     */
    @Override
    @Transactional
    public R changeStatus(String afterUsrId, String userId) {
        // ユーザ存在チェック
        if (StringUtils.equals(userId, ShiroUtils.getUserId())) {
            return R.error(MessageUtils.getMessage("MSGCOMN0092", "ユーザー"));
        }
        // ユーザー基本マスタから　削除　または　回復 20210220 wen ↓
        MstUsrEntity mstUsrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", userId));
        mstUsrEntity.setDelFlg(mstUsrEntity.getDelFlg() == 0 ? 1 : 0);
        mstUsrDao.updateById(mstUsrEntity);
        if (StringUtils.equals("3", StringUtils.trim(mstUsrEntity.getRoleDiv()))) {
            List<MstStuEntity> stuEntities = mstStuDao.selectList(
                    new QueryWrapper<MstStuEntity>().eq("guard_id", mstUsrEntity.getUsrId()).or().eq("guard1_id", mstUsrEntity.getUsrId()).or().eq("guard2_id",
                            mstUsrEntity.getUsrId()).or().eq("guard3_id", mstUsrEntity.getUsrId()).or().eq("guard4_id", mstUsrEntity.getUsrId()));
            for (MstStuEntity stuEntity : stuEntities) {
                stuEntity.setGuardId(StringUtils.equals(mstUsrEntity.getUsrId(), stuEntity.getGuardId()) ? null : stuEntity.getGuardId());
                stuEntity.setGuard1Id(StringUtils.equals(mstUsrEntity.getUsrId(), stuEntity.getGuard1Id()) ? null : stuEntity.getGuard1Id());
                stuEntity.setGuard2Id(StringUtils.equals(mstUsrEntity.getUsrId(), stuEntity.getGuard2Id()) ? null : stuEntity.getGuard2Id());
                stuEntity.setGuard3Id(StringUtils.equals(mstUsrEntity.getUsrId(), stuEntity.getGuard3Id()) ? null : stuEntity.getGuard3Id());
                stuEntity.setGuard4Id(StringUtils.equals(mstUsrEntity.getUsrId(), stuEntity.getGuard4Id()) ? null : stuEntity.getGuard4Id());
            }
            if (stuEntities.size()!=0){
                f00041Dao.updateStudent(stuEntities);
            }
        }
        //ユーザ初期パスワード管理から　削除　または　回復
        MstUsrFirtPwEntity mstUsrFirtPwEntity = mstUsrFirtPwDao.selectOne(new QueryWrapper<MstUsrFirtPwEntity>().eq("usr_id", userId));
        if (mstUsrFirtPwEntity != null) {
            mstUsrFirtPwEntity.setDelFlg(mstUsrFirtPwEntity.getDelFlg() == 0 ? 1 : 0);
            mstUsrFirtPwDao.updateById(mstUsrFirtPwEntity);
        }
        // 20210220 wen ↑
        return R.ok();
    }

    /**
     * @param usrRole 　ロール区分
     * @param specAuthFlg 　特殊権限フラグ
     * @param name 　画面入力した姓名
     * @param knName 　画面入力したカナ姓名
     * @param afterUsrId 　利用者ログインＩＤ
     * @param usrSts 　ステータス
     * @param schy 　学年
     * @param orgId 　組織
     * @param orgIdList 　組織リスト
     * @param limit
     * @param page
     * @return
     */
    @Override
    public R retrieval(String usrRole, String specAuthFlg, String name, String knName, String afterUsrId, String usrSts, String schy, String orgId, String orgIdList, Integer limit, Integer page) {
        //セッション・組織ID
        if (orgId == null || "".equals(orgId)) {
            orgId = ShiroUtils.getUserEntity().getOrgId();
        }
        List<String> orgIds = (List<String>)JSON.parse(orgIdList);
        List<F00041Dto> showList = new ArrayList<>();
        Integer total = null;
        //ユーザ―カウントを取得
        total = f00041Dao.selectUserCount(usrRole, name, knName, schy, orgIds, orgId, afterUsrId, usrSts);
        //データ取得できないの場合
        if (total == null || total == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "利用者"));
        }
        //当ページユーザ―一覧を取得
        showList = f00041Dao.selectUserList(usrRole, name, knName, schy, orgIds, orgId, afterUsrId, usrSts, limit, (page - 1) * limit);
        for (F00041Dto dto : showList) {
            if (StringUtils.equals("0", dto.getPwUpFlg()) && StringUtils.equals("0", dto.getSpecAuthFlg())) {
                dto.setPassword(ShiroUtils.stringToAscii(dto.getUserId()));
            } else {
                dto.setPassword("＊＊＊＊＊＊＊＊");
            }
        }
        Timestamp birth;
        if (StringUtils.equals("4", usrRole)) {
            for (F00041Dto dto : showList) {
                birth = dto.getBirthd();
                if (birth != null && !birth.toString().isEmpty()) {
                    dto.setBirth(DateUtils.format(birth, Constant.DATE_FORMAT_YYYYMD));
                }
            }
        }
        return R.ok().put("page", new PageUtils(showList, total, limit, page));
    }

    /**
     * @param orgId 組織ID
     * @param limit
     * @param page
     * @return
     */
    @Override
    public R initUserList(String orgId, Integer limit, Integer page) {
        //ユーザ―カウントを取得
        Integer total = f00041Dao.selectMgrCount(!StringUtils.isEmpty(orgId) ? orgId : ShiroUtils.getUserEntity().getOrgId());
        //データ取得できないの場合
        if (total == null || total == 0) {
            // 2020/12/8 huangxinliang modify start
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "ユーザー")).put(
                    "orgId", !StringUtils.isEmpty(orgId) ? orgId : ShiroUtils.getUserEntity().getOrgId());
            // 2020/12/8 huangxinliang modify end
        }
        //当ページユーザ―一覧を取得
        List<F00041Dto> showList = f00041Dao.selectMgrList(
                !StringUtils.isEmpty(orgId) ? orgId : ShiroUtils.getUserEntity().getOrgId(), limit, (page - 1) * limit);
        for (F00041Dto dto : showList) {
            if (StringUtils.equals("0", dto.getPwUpFlg()) && StringUtils.equals("0", dto.getSpecAuthFlg())) {
                dto.setPassword(ShiroUtils.stringToAscii(dto.getUserId()));
            } else {
                dto.setPassword("＊＊＊＊＊＊＊＊");
            }
        }
        return R.ok().put("page", new PageUtils(showList, total, limit, page)).put(
                "orgId", !StringUtils.isEmpty(orgId) ? orgId : ShiroUtils.getUserEntity().getOrgId());
    }

    /**
     * @return
     */
    @Override
    public R orgInfo() {
        MstUsrEntity mstUsrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", ShiroUtils.getUserId()).eq("del_flg", 0));
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();

        MstOrgEntity org = mstOrgDao.selectOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));
        List<MstCodDEntity> entityList = mstCodDDao.selectList(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "SCHY_DIV").eq("del_flg", 0).orderByAsc("sort"));
        List<MstCodDEntity> usrSts = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>().eq("cod_key", "USR_STS").eq("del_flg", 0).orderByAsc("sort"));
        //本組織及び下層組織リストの取得
        List<OrgAndLowerOrgIdDto> orgList = commonDao.selectThisAndLowerOrgId(brandCd, orgId);
        return R.ok().put("org", org).put("schyList", entityList).put("usrSts", usrSts).put("mstUsrEntity", mstUsrEntity).put("orgList", orgList);
    }

}
