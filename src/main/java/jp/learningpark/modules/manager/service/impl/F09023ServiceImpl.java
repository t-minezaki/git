/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
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
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.manager.dao.F09023Dao;
import jp.learningpark.modules.manager.dto.F09023Dto;
import jp.learningpark.modules.manager.service.F09023Service;
import jp.learningpark.modules.sys.dao.SysUserRoleDao;
import jp.learningpark.modules.sys.entity.SysUserRoleEntity;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>既存利用者ログインID同報設定画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/08/03 : wq: 新規<br />
 * @version 1.0
 */

@Service
@Transactional
public class F09023ServiceImpl implements F09023Service {

    /**
     * 既存利用者ログインID同報設定画面 Dao
     */
    @Autowired
    F09023Dao f09023Dao;

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

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @param roleDiv    　ロール区分
     * @param orgId      　組織ID
     * @param afterUsrId 　変更後ユーザーID
     */
    @Override
    public List<F09023Dto> getRoleDataList(String roleDiv, String checkRole, String orgId, String afterUsrId) {
        return f09023Dao.getRoleDataList(roleDiv, checkRole, orgId, afterUsrId);
    }

    /**
     *
     * @param roleData
     * @return
     */
    @Override
    public R DBLogin(List<F09023Dto> roleData) {
        //セッション組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // ロール区分
        MstNumassEntity mstNumassEntity = null;
        for (F09023Dto dto : roleData) {
            mstNumassEntity = mstNumassDao.selectOne(new QueryWrapper<MstNumassEntity>().eq("role_div", dto.getRoleDiv()).eq("del_flg", 0));
            if (mstNumassEntity != null) {
                // maxId
                String maxId = mstNumassEntity.getMaxId();
                // newMaxId
                String newMaxId = maxId.substring(0, 1) + new BigInteger(maxId.substring(1)).add(new BigInteger("1")).toString();
                // 採番マスタよりＭＡＸユーザーＩＤを更新する
                mstNumassEntity.setMaxId(newMaxId);
                // 更新日時
                mstNumassEntity.setUpdDatime(DateUtils.getSysTimestamp());
                // 更新ユーザＩＤ
                mstNumassEntity.setUpdUsrId(ShiroUtils.getUserId());
                try {
                    mstNumassDao.updateById(mstNumassEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }

                // ユーザー基本マスタへ登録する
                MstUsrEntity mstUsrEntity = new MstUsrEntity();
                //ユーザ初期パスワード管理登録
//                MstUsrFirtPwEntity mstUsrFirtPwEntity = new MstUsrFirtPwEntity();
                // ユーザID
                mstUsrEntity.setUsrId(newMaxId);
                // ユーザID
//                mstUsrFirtPwEntity.setUsrId(newMaxId);
                // ユーザログインPW
                // PW更新フラグ
                mstUsrEntity.setPwUpFlg("0");
//                // パスワード初期化
//                String pwd = ShiroUtils.stringToAscii(newMaxId);
                mstUsrEntity.setUsrPassword(null);
//                mstUsrFirtPwEntity.setUsrFstPassword(null);
                // 初回登録フラグ
                mstUsrEntity.setFstLoginFlg("0");
                // ユーザ名
                mstUsrEntity.setUsrNm("同報設定ユーザー");
                // ロール区分
                mstUsrEntity.setRoleDiv(dto.getRoleDiv());
                // ロール区分
//                mstUsrFirtPwEntity.setRoleDiv(dto.getRoleDiv());
//                mstUsrFirtPwEntity.setSysDiv("0");
                // 組織ID
                mstUsrEntity.setOrgId(orgId);
                // 組織共用キー
                mstUsrEntity.setOrgCommKey(orgId + "_key");
                // ユーザステータス
                mstUsrEntity.setUsrSts("1");
                // 特殊権限フラグ
                mstUsrEntity.setSpecAuthFlg("0");
                // 変更後ユーザーID
                mstUsrEntity.setAfterUsrId(dto.getLoginId());
                // エラー回数
                mstUsrEntity.setErrorCount(0);
                // ロックフラグ
                mstUsrEntity.setLockFlg("0");
                // 学研IDプライマリキー
                mstUsrEntity.setGidpk(dto.getGidpk());
                // GIDフラグ
                mstUsrEntity.setGidFlg(dto.getGidFlg());
                // 他システム区分
                mstUsrEntity.setSystemKbn(dto.getSystemKbn());
                // GakkenID規約フラグ
                mstUsrEntity.setGidRuleFlg(dto.getGidRuleFlg());
                // マナミル規約フラグ
                mstUsrEntity.setManaRuleFlg(dto.getManaRuleFlg());
                // 個人情報保護規約フラグ
                mstUsrEntity.setPerlInfoRuleFlg(dto.getPerlInfoRuleFlg());
                // 自分修正可否フラグ
                mstUsrEntity.setSafModifyFlg("1");
                // 管理者修正可否フラグ
                mstUsrEntity.setMgrModifyFlg("1");
                // 作成日時
                mstUsrEntity.setCretDatime(DateUtils.getSysTimestamp());
                // 作成日時
//                mstUsrFirtPwEntity.setCretDatime(DateUtils.getSysTimestamp());
                // 作成ユーザＩＤ
                mstUsrEntity.setCretUsrId(ShiroUtils.getUserId());
                // 作成ユーザＩＤ
//                mstUsrFirtPwEntity.setCretUsrId(ShiroUtils.getUserId());
                // 更新日時
                mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
                // 更新日時
//                mstUsrFirtPwEntity.setUpdDatime(DateUtils.getSysTimestamp());
                // 更新ユーザＩＤ
                mstUsrEntity.setUpdUsrId(ShiroUtils.getUserId());
                //2020/12/29 liyuhuan add start
                //所属組織フラグを設定する
                mstUsrEntity.setOwnerOrgFlg("1");
                //2020/12/29 liyuhuan add end
                // 更新ユーザＩＤ
//                mstUsrFirtPwEntity.setUpdUsrId(ShiroUtils.getUserId());
                // 削除フラグ
                mstUsrEntity.setDelFlg(0);
                // 削除フラグ
//                mstUsrFirtPwEntity.setDelFlg(0);

                try {
                    mstUsrDao.insert(mstUsrEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    if (!StringUtils.isEmpty(((PSQLException)e.getCause()).getServerErrorMessage().getConstraint())) {
                        throw new RRException("該当ユーザーが存在しています。");
                    }
                }
//                try {
//                    mstUsrFirtPwDao.insert(mstUsrFirtPwEntity);
//                } catch (Exception e) {
//                    logger.error(e.getMessage());
//                    if (!StringUtils.isEmpty(((PSQLException)e.getCause()).getServerErrorMessage().getRoutine())) {
//                        throw new RRException("データが長すぎます。");
//                    }
//                }

                //ユーザーキャラへ登録する
                SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
                //ユーザー基本マスタ.ID
                sysUserRoleEntity.setUserId((long)mstUsrEntity.getId());
                switch (dto.getRoleDiv()) {
                    // 管理者基本マスタ登録
                    case "1":
                        MstManagerEntity mstManagerEntity = new MstManagerEntity();
                        // 管理者ID
                        mstManagerEntity.setMgrId(newMaxId);
                        // メールアドレス
                        mstManagerEntity.setMailad(dto.getMailad());
                        // 姓名_姓
                        mstManagerEntity.setFlnmNm(dto.getFlnmNm());
                        // 姓名_名
                        mstManagerEntity.setFlnmLnm(dto.getFlnmLnm());
                        // 姓名_カナ姓
                        mstManagerEntity.setFlnmKnNm(dto.getFlnmKnNm());
                        // 姓名_カナ名
                        mstManagerEntity.setFlnmKnLnm(dto.getFlnmKnLnm());
                        // 電話番号
                        mstManagerEntity.setTelnum(dto.getTelNum());
                        // 作成日時
                        mstManagerEntity.setCretDatime(DateUtils.getSysTimestamp());
                        // 作成ユーザＩＤ
                        mstManagerEntity.setCretUsrId(ShiroUtils.getUserId());
                        // 更新日時
                        mstManagerEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        // 更新ユーザＩＤ
                        mstManagerEntity.setUpdUsrId(ShiroUtils.getUserId());
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
                        //メールアドレス
                        mstMentorEntity.setMailad(dto.getMailad());
                        // 姓名_姓
                        mstMentorEntity.setFlnmNm(dto.getFlnmNm());
                        // 姓名_名
                        mstMentorEntity.setFlnmLnm(dto.getFlnmLnm());
                        // 姓名_カナ姓
                        mstMentorEntity.setFlnmKnNm(dto.getFlnmKnNm());
                        // 姓名_カナ名
                        mstMentorEntity.setFlnmKnLnm(dto.getFlnmKnLnm());
                        // 電話番号
                        mstMentorEntity.setTelnum(dto.getTelNum());
                        // 指導者管理コード
                        mstMentorEntity.setTchCd(dto.getTchCd());
                        // 作成日時
                        mstMentorEntity.setCretDatime(DateUtils.getSysTimestamp());
                        // 作成ユーザＩＤ
                        mstMentorEntity.setCretUsrId(ShiroUtils.getUserId());
                        // 更新日時
                        mstMentorEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        // 更新ユーザＩＤ
                        mstMentorEntity.setUpdUsrId(ShiroUtils.getUserId());
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
                        mstGuardEntity.setMailad(dto.getMailad());
                        // 姓名_姓
                        mstGuardEntity.setFlnmNm(dto.getFlnmNm());
                        // 姓名_名
                        mstGuardEntity.setFlnmLnm(dto.getFlnmLnm());
                        // 姓名_カナ姓
                        mstGuardEntity.setFlnmKnNm(dto.getFlnmKnNm());
                        // 姓名_カナ名
                        mstGuardEntity.setFlnmKnLnm(dto.getFlnmKnLnm());
                        // 子供の続柄
                        mstGuardEntity.setReltnspDiv(dto.getReltnspDiv());
                        // 郵便番号_主番
                        mstGuardEntity.setPostcdMnum(dto.getPostcdMnum());
                        // 郵便番号_枝番
                        mstGuardEntity.setPostcdBnum(dto.getPostcdBnum());
                        // 住所１
                        mstGuardEntity.setAdr1(dto.getAddr1());
                        // 住所２
                        mstGuardEntity.setAdr2(dto.getAddr2());
                        // 電話番号
                        mstGuardEntity.setTelnum(dto.getTelNum());
                        // 緊急電話番号
                        mstGuardEntity.setUrgTelnum(dto.getUrgTelnum());
                        // 作成日時
                        mstGuardEntity.setCretDatime(DateUtils.getSysTimestamp());
                        // 作成ユーザＩＤ
                        mstGuardEntity.setCretUsrId(ShiroUtils.getUserId());
                        // 更新日時
                        mstGuardEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        // 更新ユーザＩＤ
                        mstGuardEntity.setUpdUsrId(ShiroUtils.getUserId());
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
                        mstStuEntity.setSch(dto.getSch());
                        // 保護者ID
                        mstStuEntity.setGuardId(dto.getGuardId());
                        // 保護者1ID
                        mstStuEntity.setGuard1Id(dto.getGuard1Id());
                        // 保護者2ID
                        mstStuEntity.setGuard2Id(dto.getGuard2Id());
                        // 保護者3ID
                        mstStuEntity.setGuard3Id(dto.getGuard3Id());
                        // 保護者4ID
                        mstStuEntity.setGuard4Id(dto.getGuard4Id());
                        // 姓名_姓
                        mstStuEntity.setFlnmNm(dto.getFlnmNm());
                        // 姓名_名
                        mstStuEntity.setFlnmLnm(dto.getFlnmLnm());
                        // 姓名_カナ姓
                        mstStuEntity.setFlnmKnNm(dto.getFlnmKnNm());
                        // 姓名_カナ名
                        mstStuEntity.setFlnmKnLnm(dto.getFlnmKnLnm());
                        // 性別区分
                        mstStuEntity.setGendrDiv(dto.getGendrDiv());
                        // 生年月日
                        mstStuEntity.setBirthd(dto.getBirthd());
                        // 学年区分
                        mstStuEntity.setSchyDiv(dto.getSchyDiv());
                        //QRコード
                        mstStuEntity.setQrCod(newMaxId);
                        //オリジナルCD
                        mstStuEntity.setOriaCd(dto.getOriaCd());
                        // 英語氏名
                        mstStuEntity.setEnglishNm(dto.getEnglishNm());
                        // 通塾曜日区分
                        mstStuEntity.setDayweekDiv(dto.getDayweekDiv());
                        // メモ
                        mstStuEntity.setMemoCont(dto.getMemoCont());
                        // 担当者氏名
                        mstStuEntity.setResptyNm(dto.getResptyNm());
                        // 習い事
                        mstStuEntity.setStudyCont(dto.getStudyCont());
                        // 得意科目区分
                        mstStuEntity.setGoodSubjtDiv(dto.getGoodSubjtDiv());
                        // 不得意科目区分
                        mstStuEntity.setNogoodSubjtDiv(dto.getNogoodSubjtDiv());
                        // 希望職種
                        mstStuEntity.setHopeJobNm(dto.getHopeJobNm());
                        // 希望大学
                        mstStuEntity.setHopeUnityNm(dto.getHopeUnityNm());
                        // 希望学部学科
                        mstStuEntity.setHopeLearnSub(dto.getHopeLearnSub());
                        // 特記事項
                        mstStuEntity.setSpecCont(dto.getSpecCont());
                        // 会員コード
                        mstStuEntity.setMemberCd(dto.getMemberCd());
                        // 作成日時
                        mstStuEntity.setCretDatime(DateUtils.getSysTimestamp());
                        // 作成ユーザＩＤ
                        mstStuEntity.setCretUsrId(ShiroUtils.getUserId());
                        // 更新日時
                        mstStuEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        // 更新ユーザＩＤ
                        mstStuEntity.setUpdUsrId(ShiroUtils.getUserId());
                        // 削除フラグ
                        mstStuEntity.setDelFlg(0);
                        try {
                            mstStuDao.insert(mstStuEntity);
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
            } else {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "採番マスタ"));
            }
        }
        return R.ok();
    }

    @Override
    public String getBrandCode(String userId, String brandCd) {
        return f09023Dao.getBrandCode(userId, brandCd);
    }
}
