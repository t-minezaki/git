/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.dao.MstGuardDao;
import jp.learningpark.modules.common.dao.MstManagerDao;
import jp.learningpark.modules.common.dao.MstMentorDao;
import jp.learningpark.modules.common.dao.MstNumassDao;
import jp.learningpark.modules.common.dao.MstStuDao;
import jp.learningpark.modules.common.dao.MstUsrDao;
import jp.learningpark.modules.common.dao.MstUsrFirtPwDao;
import jp.learningpark.modules.common.entity.MstGuardEntity;
import jp.learningpark.modules.common.entity.MstManagerEntity;
import jp.learningpark.modules.common.entity.MstMentorEntity;
import jp.learningpark.modules.common.entity.MstNumassEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.entity.MstUsrFirtPwEntity;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dao.F00007Dao;
import jp.learningpark.modules.manager.dao.F00042Dao;
import jp.learningpark.modules.manager.dto.F00042Dto;
import jp.learningpark.modules.manager.service.F00042Service;
import jp.learningpark.modules.sys.dao.SysUserRoleDao;
import jp.learningpark.modules.sys.entity.SysUserRoleEntity;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>ユーザー初期基本情報＆新規発番画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/12: xiong: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F00042ServiceImpl implements F00042Service {

    /**
     * 属する下位組織情報を取得
     */
    @Autowired
    F00007Dao f00007Dao;
    /**
     * ユーザー初期基本情報＆新規発番画面 Dao
     */
    @Autowired
    F00042Dao f00042Dao;

    /**
     * 採番マスタ Dao
     */
    @Autowired
    private MstNumassDao mstNumassDao;

    /**
     * ユーザー基本マスタ　Dao
     */
    @Autowired
    private MstUsrDao mstUsrDao;

    /**
     * 管理者基本マスタ　Dao
     */
    @Autowired
    private MstManagerDao mstManagerDao;

    /**
     * メンター基本マスタ　Dao
     */
    @Autowired
    private MstMentorDao mstMentorDao;

    /**
     * 保護者基本マスタ　Dao
     */
    @Autowired
    private MstGuardDao mstGuardDao;

    /**
     * 生徒基本マスタ　Dao
     */
    @Autowired
    private MstStuDao mstStuDao;

    /**
     * ユーザーキャラ　Dao
     */
    @Autowired
    SysUserRoleDao sysUserRoleDao;

    /**
     * ユーザ初期パスワード管理　Dao
     */
    @Autowired
    MstUsrFirtPwDao mstUsrFirtPwDao;

    @Autowired
    CommonService commonService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @param userId 画面．利用者ログインＩＤ
     * @return
     */
    @Override
    public Integer getAfterUserId(String userId) {
        return f00042Dao.getAfterUserId(userId);
    }

    /**
     * ユーザー初期基本情報
     *
     * @param f00042Dto
     * @return
     */
    @Override
    // 2021/09/22 manamiru1-772 cuikl del
    public R getInformation(F00042Dto f00042Dto) {
        // ロール
        String role = f00042Dto.getRole();
        // nowTime
        Timestamp nowTime = DateUtils.getSysTimestamp();
        // ロール区分
        MstNumassEntity mstNumassEntity = mstNumassDao.selectOne(new QueryWrapper<MstNumassEntity>().eq("role_div", role).eq("del_flg", 0));
        String brandCd = ShiroUtils.getBrandcd();
        //	  新組織list
        List<MstOrgEntity> orgList = f00042Dto.getOrgList();
        //新組織listの下位組織
        Set<MstOrgEntity> lowerOrgId = new HashSet<>();
        //取得組織と下位組織
        for (MstOrgEntity orgEntity : orgList) {
            String id = orgEntity.getOrgId();
            List<OrgAndLowerOrgIdDto> thisAndLowerOrgId = commonService.getThisAndLowerOrgId(brandCd, id);
            lowerOrgId.addAll(thisAndLowerOrgId);
        }
        //削除新組織list,下の組織を取得する
        lowerOrgId.removeAll(orgList);
        // 取得したの一覧listが下記の操作を繰り返し実行する
        for (MstOrgEntity orgEntity : orgList) {
            saveUsrMsg(f00042Dto, orgEntity.getOrgId(), role, nowTime, mstNumassEntity, true);
        }
        //        取得したの組織listの全ての下位組織が下記の操作を繰り返し実行する
        for (MstOrgEntity orgEntity : lowerOrgId) {
            saveUsrMsg(f00042Dto, orgEntity.getOrgId(), role, nowTime, mstNumassEntity, false);
        }
        //        saveUsrMsg(f00042Dto,ShiroUtils.getUserEntity().getOrgId(), role, nowTime, mstNumassEntity,true);
        return R.ok();
    }

    public void saveUsrMsg(F00042Dto f00042Dto, String orgId, String role, Timestamp nowTime, MstNumassEntity mstNumassEntity, boolean isTopOrg) {

        // ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();
        // maxId
        String maxId = "";
        // newMaxId
        String newMaxId = "";
        if (mstNumassEntity != null) {
            maxId = mstNumassEntity.getMaxId();
            newMaxId = maxId.charAt(0) + new BigInteger(maxId.substring(1)).add(new BigInteger("1")).toString();
            // 採番マスタよりＭＡＸユーザーＩＤを更新する
            mstNumassEntity.setMaxId(newMaxId);
            // 更新日時
            mstNumassEntity.setUpdDatime(nowTime);
            // 更新ユーザＩＤ
            mstNumassEntity.setUpdUsrId(userId);
            try {
                mstNumassDao.updateById(mstNumassEntity);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            mstNumassEntity = new MstNumassEntity();
            mstNumassEntity.setBrandCd(ShiroUtils.getBrandcd());
            mstNumassEntity.setRoleDiv(role);
            mstNumassEntity.setMaxId(
                    StringUtils.equals("1", role) ? "a1" : (StringUtils.equals("2", role) ? "m1" : (StringUtils.equals("3", role) ? "p1" : "s1")));
            mstNumassEntity.setCretDatime(DateUtils.getSysTimestamp());
            mstNumassEntity.setCretUsrId(ShiroUtils.getUserId());
            mstNumassEntity.setUpdDatime(DateUtils.getSysTimestamp());
            mstNumassEntity.setUpdUsrId(ShiroUtils.getUserId());
            mstNumassEntity.setDelFlg(0);
            mstNumassDao.insert(mstNumassEntity);
            newMaxId = mstNumassEntity.getMaxId();
        }

        // ユーザー基本マスタへ登録する
        MstUsrEntity mstUsrEntity = new MstUsrEntity();
        //ユーザ初期パスワード管理登録
        MstUsrFirtPwEntity mstUsrFirtPwEntity = new MstUsrFirtPwEntity();
        // ユーザID
        mstUsrEntity.setUsrId(newMaxId);
        // ユーザID
        mstUsrFirtPwEntity.setUsrId(newMaxId);
        // ユーザログインPW
        if (f00042Dto.getPassword().isEmpty()) {
            // PW更新フラグ
            mstUsrEntity.setPwUpFlg("0");
            // パスワード初期化
            String pwd = ShiroUtils.stringToAscii(newMaxId);
            mstUsrEntity.setUsrPassword(ShiroUtils.sha256(pwd, newMaxId));
            mstUsrFirtPwEntity.setUsrFstPassword(ShiroUtils.sha256(pwd, newMaxId));
        } else {
            // PW更新フラグ
            mstUsrEntity.setPwUpFlg("2");
            mstUsrEntity.setUsrPassword(ShiroUtils.sha256(f00042Dto.getPassword(), newMaxId));
            mstUsrFirtPwEntity.setUsrFstPassword(ShiroUtils.sha256(f00042Dto.getPassword(), newMaxId));
        }
        // 初回登録フラグ
        mstUsrEntity.setFstLoginFlg("0");// 2020/04/16 mod by zhangpuao 0->1;20200803 wen 1 -> 0
        // ロール区分
        mstUsrEntity.setRoleDiv(role);
        // ロール区分
        mstUsrFirtPwEntity.setRoleDiv(role);
        mstUsrFirtPwEntity.setSysDiv("0");
        // 組織ID
        mstUsrEntity.setOrgId(orgId);
        // ユーザステータス
        mstUsrEntity.setUsrSts("1");
        // 特殊権限フラグ
        mstUsrEntity.setSpecAuthFlg("0");
        // 変更後ユーザーID
        mstUsrEntity.setAfterUsrId(f00042Dto.getUserId());
        // エラー回数
        mstUsrEntity.setErrorCount(0);
        // ロックフラグ
        mstUsrEntity.setLockFlg("0");
        //        GIDフラグ
        mstUsrEntity.setGidFlg("0");
        //        他システム区分
        mstUsrEntity.setSystemKbn("1");
        //組織共用キー
        mstUsrEntity.setOrgCommKey(orgId + "_key");
        //        GakkenID規約フラグ
        mstUsrEntity.setGidRuleFlg("0");
        //        マナミル規約フラグ
        mstUsrEntity.setManaRuleFlg("0");
        //        個人情報保護規約フラグ
        mstUsrEntity.setPerlInfoRuleFlg("0");
        //        自分修正可否フラグ
        mstUsrEntity.setSafModifyFlg("1");
        //管理者修正可否フラグ
        mstUsrEntity.setMgrModifyFlg("1");
        //所属組織フラグ
        mstUsrEntity.setOwnerOrgFlg(isTopOrg ? "1" : "0");
        // 作成日時
        mstUsrEntity.setCretDatime(nowTime);
        // 作成日時
        mstUsrFirtPwEntity.setCretDatime(nowTime);
        // 作成ユーザＩＤ
        mstUsrEntity.setCretUsrId(userId);
        // 作成ユーザＩＤ
        mstUsrFirtPwEntity.setCretUsrId(userId);
        // 更新日時
        mstUsrEntity.setUpdDatime(nowTime);
        // 更新日時
        mstUsrFirtPwEntity.setUpdDatime(nowTime);
        // 更新ユーザＩＤ
        mstUsrEntity.setUpdUsrId(userId);
        // 更新ユーザＩＤ
        mstUsrFirtPwEntity.setUpdUsrId(userId);
        // 削除フラグ
        mstUsrEntity.setDelFlg(0);
        // 削除フラグ
        mstUsrFirtPwEntity.setDelFlg(0);

        try {
            mstUsrDao.insert(mstUsrEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (!StringUtils.isEmpty(((PSQLException)e.getCause()).getServerErrorMessage().getConstraint())) {
                throw new RRException("該当ユーザーが存在しています。");
            }
        }
        try {
            mstUsrFirtPwDao.insert(mstUsrFirtPwEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (!StringUtils.isEmpty(((PSQLException)e.getCause()).getServerErrorMessage().getRoutine())) {
                throw new RRException("データが長すぎます。");
            }
        }

        //ユーザーキャラへ登録する
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        //ユーザー基本マスタ.ID
        sysUserRoleEntity.setUserId((long)mstUsrEntity.getId());
        switch (role) {
            // 管理者基本マスタ登録
            case "1":
                MstManagerEntity mstManagerEntity = new MstManagerEntity();
                // 管理者ID
                mstManagerEntity.setMgrId(newMaxId);
                // メールアドレス
                mstManagerEntity.setMailad(f00042Dto.getEmail());
                // 姓名_姓
                mstManagerEntity.setFlnmNm(f00042Dto.getFlnmNm());
                // 姓名_名
                mstManagerEntity.setFlnmLnm(f00042Dto.getFlnmLnm());
                // 姓名_カナ姓
                mstManagerEntity.setFlnmKnNm(f00042Dto.getFlnmKnNm());
                // 姓名_カナ名
                mstManagerEntity.setFlnmKnLnm(f00042Dto.getFlnmKnLnm());
                // 電話番号
                mstManagerEntity.setTelnum(f00042Dto.getTelNum());
                // 作成日時
                mstManagerEntity.setCretDatime(nowTime);
                // 作成ユーザＩＤ
                mstManagerEntity.setCretUsrId(userId);
                // 更新日時
                mstManagerEntity.setUpdDatime(nowTime);
                // 更新ユーザＩＤ
                mstManagerEntity.setUpdUsrId(userId);
                // 削除フラグ
                mstManagerEntity.setDelFlg(0);
                try {
                    mstManagerDao.insert(mstManagerEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    if (!StringUtils.isEmpty(((PSQLException)e.getCause()).getServerErrorMessage().getConstraint())) {
                        throw new RRException("該当管理者が存在しています。");
                    }
                    if (!StringUtils.isEmpty(((PSQLException)e.getCause()).getServerErrorMessage().getRoutine())) {
                        throw new RRException("データが長すぎます。");
                    }
                }
                //キャラクターID
                sysUserRoleEntity.setRoleId((long)5);
                try {
                    sysUserRoleDao.insert(sysUserRoleEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                break;
            // メンター基本マスタ登録
            case "2":
                MstMentorEntity mstMentorEntity = new MstMentorEntity();
                // メンターID
                mstMentorEntity.setMentorId(newMaxId);
                // メールアドレス
                mstMentorEntity.setMailad(f00042Dto.getEmail());
                // 姓名_姓
                mstMentorEntity.setFlnmNm(f00042Dto.getFlnmNm());
                // 姓名_名
                mstMentorEntity.setFlnmLnm(f00042Dto.getFlnmLnm());
                // 姓名_カナ姓
                mstMentorEntity.setFlnmKnNm(f00042Dto.getFlnmKnNm());
                // 姓名_カナ名
                mstMentorEntity.setFlnmKnLnm(f00042Dto.getFlnmKnLnm());
                // 電話番号
                mstMentorEntity.setTelnum(f00042Dto.getTelNum());
                // 作成日時
                mstMentorEntity.setCretDatime(nowTime);
                // 作成ユーザＩＤ
                mstMentorEntity.setCretUsrId(userId);
                // 更新日時
                mstMentorEntity.setUpdDatime(nowTime);
                // 更新ユーザＩＤ
                mstMentorEntity.setUpdUsrId(userId);
                // 削除フラグ
                mstMentorEntity.setDelFlg(0);
                try {
                    mstMentorDao.insert(mstMentorEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    if (!StringUtils.isEmpty(((PSQLException)e.getCause()).getServerErrorMessage().getConstraint())) {
                        throw new RRException("該当先生が存在しています。");
                    }
                    if (!StringUtils.isEmpty(((PSQLException)e.getCause()).getServerErrorMessage().getRoutine())) {
                        throw new RRException("データが長すぎます。");
                    }
                }
                //キャラクターID
                sysUserRoleEntity.setRoleId((long)1);
                try {
                    sysUserRoleDao.insert(sysUserRoleEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                break;
            // 保護者基本マスタ登録
            case "3":
                MstGuardEntity mstGuardEntity = new MstGuardEntity();
                // 保護者ID
                mstGuardEntity.setGuardId(newMaxId);
                // メールアドレス
                mstGuardEntity.setMailad(f00042Dto.getEmail());
                // 姓名_姓
                mstGuardEntity.setFlnmNm(f00042Dto.getFlnmNm());
                // 姓名_名
                mstGuardEntity.setFlnmLnm(f00042Dto.getFlnmLnm());
                // 姓名_カナ姓
                mstGuardEntity.setFlnmKnNm(f00042Dto.getFlnmKnNm());
                // 姓名_カナ名
                mstGuardEntity.setFlnmKnLnm(f00042Dto.getFlnmKnLnm());
                // 郵便番号_主番
                mstGuardEntity.setPostcdMnum(f00042Dto.getPostCd().substring(0, 3));
                // 郵便番号_枝番
                mstGuardEntity.setPostcdBnum(f00042Dto.getPostCd().substring(3));
                // 住所１
                mstGuardEntity.setAdr1(f00042Dto.getAddr1());
                // 住所２
                mstGuardEntity.setAdr2(f00042Dto.getAddr2());
                // 電話番号
                mstGuardEntity.setTelnum(f00042Dto.getTelNum());
                // 続柄区分
                mstGuardEntity.setReltnspDiv(f00042Dto.getParent());
                //緊急電話番号
                mstGuardEntity.setUrgTelnum(f00042Dto.getUrgTelNum());
                // 作成日時
                mstGuardEntity.setCretDatime(nowTime);
                // 作成ユーザＩＤ
                mstGuardEntity.setCretUsrId(userId);
                // 更新日時
                mstGuardEntity.setUpdDatime(nowTime);
                // 更新ユーザＩＤ
                mstGuardEntity.setUpdUsrId(userId);
                // 削除フラグ
                mstGuardEntity.setDelFlg(0);
                try {
                    mstGuardDao.insert(mstGuardEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    if (!StringUtils.isEmpty(((PSQLException)e.getCause()).getServerErrorMessage().getConstraint())) {
                        throw new RRException("該当保護者が存在しています。");
                    }
                    if (!StringUtils.isEmpty(((PSQLException)e.getCause()).getServerErrorMessage().getRoutine())) {
                        throw new RRException("データが長すぎます。");
                    }
                }
                //キャラクターID
                sysUserRoleEntity.setRoleId((long)3);
                try {
                    sysUserRoleDao.insert(sysUserRoleEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                break;
            // 生徒基本マスタ登録
            case "4":
                MstStuEntity mstStuEntity = new MstStuEntity();
                // 生徒ID
                mstStuEntity.setStuId(newMaxId);
                // 学校名
                mstStuEntity.setSch(f00042Dto.getSchoolName());
                // 姓名_姓
                mstStuEntity.setFlnmNm(f00042Dto.getFlnmNm());
                // 姓名_名
                mstStuEntity.setFlnmLnm(f00042Dto.getFlnmLnm());
                // 姓名_カナ姓
                mstStuEntity.setFlnmKnNm(f00042Dto.getFlnmKnNm());
                // 姓名_カナ名
                mstStuEntity.setFlnmKnLnm(f00042Dto.getFlnmKnLnm());
                // 性別区分
                mstStuEntity.setGendrDiv(f00042Dto.getGender());
                // 生年月日
                mstStuEntity.setBirthd(DateUtils.parse(f00042Dto.getBirthDay(), Constant.DATE_FORMAT_YYYY_MM_DD_SLASH));
                // 学年区分
                mstStuEntity.setSchyDiv(f00042Dto.getSchoolYear());
                //QRコード
                mstStuEntity.setQrCod(newMaxId);
                //オリジナルCD
                mstStuEntity.setOriaCd(f00042Dto.getOriaCd());
                // 作成日時
                mstStuEntity.setCretDatime(nowTime);
                // 作成ユーザＩＤ
                mstStuEntity.setCretUsrId(userId);
                // 更新日時
                mstStuEntity.setUpdDatime(nowTime);
                // 更新ユーザＩＤ
                mstStuEntity.setUpdUsrId(userId);
                // 削除フラグ
                mstStuEntity.setDelFlg(0);
                try {
                    mstStuDao.insert(mstStuEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    if (!StringUtils.isEmpty(((PSQLException)e.getCause()).getServerErrorMessage().getConstraint())) {
                        throw new RRException("該当生徒が存在しています。");
                    }
                    if (!StringUtils.isEmpty(((PSQLException)e.getCause()).getServerErrorMessage().getRoutine())) {
                        throw new RRException("データが長すぎます。");
                    }
                }
                //キャラクターID
                sysUserRoleEntity.setRoleId((long)2);
                try {
                    sysUserRoleDao.insert(sysUserRoleEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                break;
            default:
                break;
        }
    }
}
