/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.gakken.id2.V2GakkenIDPrivilegeSvcStub;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.gakkenID.GakkenIdAPI;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.F40008Dao;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dao.F00042Dao;
import jp.learningpark.modules.manager.dao.F00043Dao;
import jp.learningpark.modules.manager.dto.F00042Dto;
import jp.learningpark.modules.manager.dto.F00043Dto;
import jp.learningpark.modules.manager.service.F00043Service;
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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>ユーザー基本情報修正画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/15 : xiong: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F00043ServiceImpl implements F00043Service {

    /**
     * ユーザー基本情報修正画面
     */
    @Autowired
    F00043Dao f00043Dao;

    /**
     * ユーザー初期基本情報＆新規発番画面
     */
    @Autowired
    F00042Dao f00042Dao;

    /**
     * ユーザー基本マスタ
     */
    @Autowired
    private MstUsrDao mstUsrDao;

    /**
     * 管理者基本マスタ
     */
    @Autowired
    private MstManagerDao mstManagerDao;

    /**
     * メンター基本マスタ
     */
    @Autowired
    private MstMentorDao mstMentorDao;

    /**
     * 保護者基本マスタ
     */
    @Autowired
    private MstGuardDao mstGuardDao;

    /**
     * 生徒基本マスタ
     */
    @Autowired
    private MstStuDao mstStuDao;

    /**
     *
     */
    @Autowired
    GakkenIdAPI gakkenIdAPI;

    @Autowired
    private CommonService commonService;

    @Autowired
    private MstNumassDao mstNumassDao;

    @Autowired
    private MstUsrFirtPwDao mstUsrFirtPwDao;

    @Autowired
    private F40008Dao f40008Dao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    private final ThreadLocal<Object> cacheRole = new ThreadLocal<>();

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * ユーザー基本情報
     *
     * @param usrId 引渡データ．ユーザＩＤ
     * @return
     */
    @Override
    public F00043Dto getAfterUserId(String usrId) {
        return f00043Dao.getAfterUserId(usrId, false);
    }

    /**
     * [AfterUserId]情報を取得し、削除フラグを無視します
     */
    private F00043Dto getAfterUserIdAndIgnoreDelete(String usrId) {
        return f00043Dao.getAfterUserId(usrId, true);
    }

    /**
     * ユーザー画面基本情報
     *
     * @param f00042Dto ユーザー画面基本情報
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    // 2021/09/22 manamiru1-772 cuikl del
    public R setInformation(F00042Dto f00042Dto) {
        try {
            // 排他チェックエラーの場合
            // 前のスレッドがデータを変更した場合、このミューテックスは失敗します
            tryCheckExclusiveLock(f00042Dto.getUsrId(), f00042Dto.getUpdateTime(), f00042Dto.getUpdDatime());
            // 現在のブランドユーザーが取得
            List<MstUsrEntity> mstUsrEntityList = f40008Dao.selectUsr(ShiroUtils.getBrandcd(),f00042Dto.getOldLoginId());

            // 現在のユーザ情報とキャラクター情報を削除します。
            for (MstUsrEntity usrEntity : mstUsrEntityList) {
                deleteUsrInfoAndRole(usrEntity);
            }

            // データの更新と追加の準備
            // nowTime
            Timestamp nowTime = DateUtils.getSysTimestamp();
            // ユーザー基本情報
            F00043Dto f00043Dto = getAfterUserIdAndIgnoreDelete(f00042Dto.getUsrId());

            // ユーザーrole
            String role = f00043Dto.getCodCd();
            // pop画面から選択した組織
            List<MstOrgEntity> selectedOrg = f00042Dto.getOrgList();
            // 該当組織及び下位組織を取得して、本組織及び下位組織listに設定する
            List<String> orgIdList = getOrgAndLowerOrgIds(selectedOrg);

            // ロール区分
            MstNumassEntity mstNumassEntity = mstNumassDao.selectOne(new QueryWrapper<MstNumassEntity>()
                    .eq("role_div", role).eq("del_flg", 0));

            // トップレベルの組織IDを取得する, フィールド[所属組織フラグ]を更新するために使用されます
            List<String> selectedOrgId = selectedOrg.stream()
                    .map(MstOrgEntity::getOrgId).collect(Collectors.toList());

            // ユーザー情報コレクションをkey = orgId value = elements
            Map<String, MstUsrEntity> usrOrgMap = mstUsrEntityList.stream()
                    .collect(Collectors.toMap(MstUsrEntity::getOrgId, d -> d, (k1, k2) -> k2));

            for (String orgId : orgIdList) {
                boolean isTopOrg = selectedOrgId.contains(orgId);

                // [usrOrgMap]にユーザー情報が見つからない場合は、追加または更新する必要があります
                MstUsrEntity user = usrOrgMap.get(orgId);
                if (user == null) {
                    saveUsrMsg(f00042Dto, mstUsrEntityList.get(0), orgId, role, nowTime, mstNumassEntity, isTopOrg);
                } else {
                    basicInfoAndRoleUpdate(f00042Dto, nowTime, role, user, isTopOrg);
                }
            }

            // basicInfoAndRoleUpdate(f00042Dto, nowTime, role, mstUsrEntityList.get(0), true);
            if (StringUtils.equals("1", f00042Dto.getGidFlg())
                    && (!StringUtils.equals(f00042Dto.getOldLoginId(), f00042Dto.getUserId())
                    || !StringUtils.isEmpty(f00042Dto.getPassword()))) {
                try {
                    do {
                        String gidPK = f00042Dto.getGidpk();
                        // NWT　楊　2021/07/06　MANAMIRU1-719　Edit Start
                        logger.info("---------------------------push gidpk: " + gidPK + "---------------------------");
//                        if (StringUtils.isEmpty(gidPK)){
//                            MstManagerEntity mstManagerEntity = mstManagerDao.selectOne(new QueryWrapper<MstManagerEntity>().eq("mgr_id", f00042Dto.getUsrId()));
//                            if (mstManagerEntity == null || StringUtils.isEmpty(mstManagerEntity.getTchCd())) {
//                                break;
//                            }
//                            V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionIDselect select = new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionIDselect();
//                            select.setGidpk(true);
//                            select.setUpdate_ts(true);
//                            V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID gakkenTransactionIDForSearch = new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID();
//                            gakkenTransactionIDForSearch.setEvent_key_1(gakkenIdAPI.getTeacherCodeKey());
//                            gakkenTransactionIDForSearch.setEvent_key_2(mstManagerEntity.getTchCd());
//                            gakkenTransactionIDForSearch.setSite_cd(gakkenIdAPI.getServiceCd2());
//                            V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs gakkenTransactionIDsearchTeacherCd = gakkenIdAPI.getGakkenTransactionIDsearch(gakkenTransactionIDForSearch, select);
//                            if (gakkenTransactionIDsearchTeacherCd != null && gakkenTransactionIDsearchTeacherCd.getRecordCounts() > 0){
//                                int indexForTchCd = -1;
//                                long lastTimeForTchCd = -1L;
//                                V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID[] gakkenTransactionID = gakkenTransactionIDsearchTeacherCd.getGakkenTransactionID();
//                                for (int i = 0; i < gakkenTransactionID.length; i++) {
//                                    if (gakkenTransactionID[i] == null){
//                                        continue;
//                                    }
//                                    //指導者管理コード
//                                    if (gakkenTransactionID[i].getUpdate_ts().getTime() > lastTimeForTchCd) {
//                                        lastTimeForTchCd = gakkenTransactionID[i].getUpdate_ts().getTime();
//                                        indexForTchCd = i;
//                                    }
//                                }
//                                if (indexForTchCd >= 0) {
//                                    gidPK = gakkenTransactionID[indexForTchCd].getGidpk();
//                                }else {
//                                    break;
//                                }
//                            }else {
//                                break;
//                            }
//                        }
                        if (StringUtils.isNotBlank(gidPK)){
                            V2GakkenIDPrivilegeSvcStub.GakkenIDselect gakkenIDselect = new V2GakkenIDPrivilegeSvcStub.GakkenIDselect();
                            V2GakkenIDPrivilegeSvcStub.GakkenID gakkenIDS[] = new V2GakkenIDPrivilegeSvcStub.GakkenID[1];
                            gakkenIDS[0] = new V2GakkenIDPrivilegeSvcStub.GakkenID();
                            gakkenIDS[0].setGidpk(gidPK);
                            gakkenIDS[0].setPass(f00042Dto.getPassword());
                            gakkenIDS[0].setGid(f00042Dto.getUserId());
                            gakkenIDselect.setGid(true);
                            gakkenIDselect.setPass(true);
                            gakkenIdAPI.updateGakkenIDBulk(gakkenIDS, gakkenIDselect);
                        }else {
                            logger.error("gidpkは空です");
                        }
                        // NWT　楊　2021/07/06　MANAMIRU1-719　Edit End
                    }while (false);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                    throw new RRException(MessageUtils.getMessage("MSGCOMD0160"));
                }
            }
        }finally {
            cacheRole.remove();
        }
        return R.ok();
    }

    @Override
    public List<MstOrgEntity> getUserOrgListByLoginUserOrgLevel(String usrId) {
        List<String> thisAndLowerOrgId = getCurrentUserOrgAndLowerOrgIds();

        return f00043Dao.getUserOrgList(usrId, thisAndLowerOrgId);
    }

    /**
     * ユーザー情報とキャラクター情報を更新します。
     *
     * @param isTopOrg トップレベルの組織の更新されたデータですか
     */
    private void basicInfoAndRoleUpdate(F00042Dto f00042Dto, Timestamp nowTime,
                                        String role, MstUsrEntity user, boolean isTopOrg) {
        String userId = ShiroUtils.getUserId();

        if (!f00042Dto.getPassword().isEmpty()) {
            // ユーザログインPW
            user.setUsrPassword(ShiroUtils.sha256(f00042Dto.getPassword(), user.getUsrId()));
            // PW更新フラグ
            user.setPwUpFlg("2");
        }
        //所属組織フラグ
        user.setOwnerOrgFlg(isTopOrg ? "1" : "0");
        // 変更後ユーザーID
        user.setAfterUsrId(f00042Dto.getUserId());
        // 更新日時
        user.setUpdDatime(nowTime);
        // 更新ユーザＩＤ
        user.setUpdUsrId(userId);
        // 削除フラグ 0：有効
        user.setDelFlg(0);
        mstUsrDao.updateById(user);
        switch (role) {
            // 管理者基本マスタ登録
            case "1":
                MstManagerEntity mstManagerEntity = mstManagerDao.selectOne(new QueryWrapper<MstManagerEntity>()
                        .eq("mgr_id", user.getUsrId())
                        .orderByDesc("id").last("limit 1"));
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
                // 更新日時
                mstManagerEntity.setUpdDatime(nowTime);
                // 更新ユーザＩＤ
                mstManagerEntity.setUpdUsrId(userId);
                // 削除フラグ 0：有効
                mstManagerEntity.setDelFlg(0);
                mstManagerDao.updateById(mstManagerEntity);
                break;
            // メンター基本マスタ登録
            case "2":
                MstMentorEntity mstMentorEntity = mstMentorDao.selectOne(new QueryWrapper<MstMentorEntity>()
                        .eq("mentor_id", user.getUsrId())
                        .orderByDesc("id").last("limit 1"));
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
                // 更新日時
                mstMentorEntity.setUpdDatime(nowTime);
                // 更新ユーザＩＤ
                mstMentorEntity.setUpdUsrId(userId);
                // 削除フラグ 0：有効
                mstMentorEntity.setDelFlg(0);
                mstMentorDao.updateById(mstMentorEntity);
                break;
            // 保護者基本マスタ登録
            case "3":
                MstGuardEntity mstGuardEntity = mstGuardDao.selectOne(new QueryWrapper<MstGuardEntity>()
                        .eq("guard_id", user.getUsrId())
                        .orderByDesc("id").last("limit 1"));
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
                //緊急電話番号
                mstGuardEntity.setUrgTelnum(f00042Dto.getUrgTelNum());
                // 続柄区分
                mstGuardEntity.setReltnspDiv(f00042Dto.getParent());
                // 更新日時
                mstGuardEntity.setUpdDatime(nowTime);
                // 更新ユーザＩＤ
                mstGuardEntity.setUpdUsrId(userId);
                // 削除フラグ 0：有効
                mstGuardEntity.setDelFlg(0);
                mstGuardDao.updateById(mstGuardEntity);
                break;
            // 生徒基本マスタ登録
            case "4":
                MstStuEntity mstStuEntity = mstStuDao.selectOne(new QueryWrapper<MstStuEntity>()
                        .eq("stu_id", user.getUsrId())
                        .orderByDesc("id").last("limit 1"));
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
                // 更新日時
                mstStuEntity.setUpdDatime(nowTime);
                // 更新ユーザＩＤ
                mstStuEntity.setUpdUsrId(userId);
                // 削除フラグ 0：有効
                mstStuEntity.setDelFlg(0);
                mstStuDao.updateById(mstStuEntity);
                break;
            default:
                break;
        }
    }

    /**
     * ユーザ情報を保存
     *
     * @param oldUser 元のユーザー情報は、一部の初期値を置き換えるために使用されます
     */
    public void saveUsrMsg(F00042Dto f00042Dto, MstUsrEntity oldUser, String orgId, String role, Timestamp nowTime,
                           MstNumassEntity mstNumassEntity, boolean isTopOrg) {

        // ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();
        // maxId
        String maxId = mstNumassEntity.getMaxId();
        // newMaxId
        String newMaxId = maxId.charAt(0) + new BigInteger(maxId.substring(1)).add(new BigInteger("1")).toString();
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
            e.printStackTrace();
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
        mstUsrEntity.setFstLoginFlg(oldUser.getFstLoginFlg());// 2020/04/16 mod by zhangpuao 0->1;20200803 wen 1 -> 0
        // ユーザ名
        mstUsrEntity.setUsrNm(oldUser.getUsrNm());
        // ロール区分
        mstUsrEntity.setRoleDiv(role);
        // ロール区分
        mstUsrFirtPwEntity.setRoleDiv(role);
        mstUsrFirtPwEntity.setSysDiv("0");
        // 組織ID
        mstUsrEntity.setOrgId(orgId);
        // ユーザステータス
        mstUsrEntity.setUsrSts(oldUser.getUsrSts());
        // 特殊権限フラグ
        mstUsrEntity.setSpecAuthFlg(oldUser.getSpecAuthFlg());
        // 変更後ユーザーID
        mstUsrEntity.setAfterUsrId(f00042Dto.getUserId());
        // エラー回数
        mstUsrEntity.setErrorCount(oldUser.getErrorCount());
        // ロックフラグ
        mstUsrEntity.setLockFlg(oldUser.getLockFlg());
        // GIDフラグ
        mstUsrEntity.setGidFlg(oldUser.getGidFlg());
        // 他システム区分
        mstUsrEntity.setSystemKbn(oldUser.getSystemKbn());
        //組織共用キー
        mstUsrEntity.setOrgCommKey(orgId + "_key");
        // GakkenID規約フラグ
        mstUsrEntity.setGidRuleFlg(oldUser.getGidRuleFlg());
        // マナミル規約フラグ
        mstUsrEntity.setManaRuleFlg(oldUser.getManaRuleFlg());
        //        個人情報保護規約フラグ
        mstUsrEntity.setPerlInfoRuleFlg(oldUser.getPerlInfoRuleFlg());
        // 自分修正可否フラグ
        mstUsrEntity.setSafModifyFlg(oldUser.getSafModifyFlg());
        // 管理者修正可否フラグ
        mstUsrEntity.setMgrModifyFlg(oldUser.getMgrModifyFlg());
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
            if (!StringUtils.isEmpty(((PSQLException) e.getCause()).getServerErrorMessage().getConstraint())) {
                throw new RRException("該当ユーザーが存在しています。");
            }
        }
        try {
            mstUsrFirtPwDao.insert(mstUsrFirtPwEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (!StringUtils.isEmpty(((PSQLException) e.getCause()).getServerErrorMessage().getRoutine())) {
                throw new RRException("データが長すぎます。");
            }
        }

        //ユーザーキャラへ登録する
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        //ユーザー基本マスタ.ID
        sysUserRoleEntity.setUserId((long) mstUsrEntity.getId());
        switch (role) {
            // 管理者基本マスタ登録
            case "1":
                MstManagerEntity mstManager = (MstManagerEntity) cacheRole.get();
                MstManagerEntity mstManagerEntity = new MstManagerEntity();
                // 管理者ID
                mstManagerEntity.setMgrId(newMaxId);
                // メールアドレス
                mstManagerEntity.setMailad(mstManager.getMailad());
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
                    if (!StringUtils.isEmpty(((PSQLException) e.getCause()).getServerErrorMessage().getConstraint())) {
                        throw new RRException("該当管理者が存在しています。");
                    }
                    if (!StringUtils.isEmpty(((PSQLException) e.getCause()).getServerErrorMessage().getRoutine())) {
                        throw new RRException("データが長すぎます。");
                    }
                }
                //キャラクターID
                sysUserRoleEntity.setRoleId(5L);
                try {
                    sysUserRoleDao.insert(sysUserRoleEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                break;
            // メンター基本マスタ登録
            case "2":
                MstMentorEntity mstMentor = (MstMentorEntity) cacheRole.get();
                MstMentorEntity mstMentorEntity = new MstMentorEntity();
                // メンターID
                mstMentorEntity.setMentorId(newMaxId);
                // メールアドレス
                mstMentorEntity.setMailad(mstMentor.getMailad());
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
                    if (!StringUtils.isEmpty(((PSQLException) e.getCause()).getServerErrorMessage().getConstraint())) {
                        throw new RRException("該当先生が存在しています。");
                    }
                    if (!StringUtils.isEmpty(((PSQLException) e.getCause()).getServerErrorMessage().getRoutine())) {
                        throw new RRException("データが長すぎます。");
                    }
                }
                //キャラクターID
                sysUserRoleEntity.setRoleId(1L);
                try {
                    sysUserRoleDao.insert(sysUserRoleEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                break;
            // 保護者基本マスタ登録
            case "3":
                MstGuardEntity mstGuard = (MstGuardEntity) cacheRole.get();
                MstGuardEntity mstGuardEntity = new MstGuardEntity();
                // 保護者ID
                mstGuardEntity.setGuardId(newMaxId);
                // メールアドレス
                mstGuardEntity.setMailad(mstGuard.getMailad());
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
                mstGuardEntity.setAdr2(mstGuard.getAdr2());
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
                    if (!StringUtils.isEmpty(((PSQLException) e.getCause()).getServerErrorMessage().getConstraint())) {
                        throw new RRException("該当保護者が存在しています。");
                    }
                    if (!StringUtils.isEmpty(((PSQLException) e.getCause()).getServerErrorMessage().getRoutine())) {
                        throw new RRException("データが長すぎます。");
                    }
                }
                //キャラクターID
                sysUserRoleEntity.setRoleId(3L);
                try {
                    sysUserRoleDao.insert(sysUserRoleEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                break;
            // 生徒基本マスタ登録
            case "4":
                MstStuEntity mstStu = (MstStuEntity) cacheRole.get();
                MstStuEntity mstStuEntity = new MstStuEntity();
                // 生徒ID
                mstStuEntity.setStuId(newMaxId);
                // 学校名
                mstStuEntity.setSch(f00042Dto.getSchoolName());
                // 保護者ID
                mstStuEntity.setGuardId(mstStu.getGuardId());
                // 姓名_姓
                mstStuEntity.setFlnmNm(f00042Dto.getFlnmNm());
                // 姓名_名
                mstStuEntity.setFlnmLnm(f00042Dto.getFlnmLnm());
                // 姓名_カナ姓
                mstStuEntity.setFlnmKnNm(f00042Dto.getFlnmKnNm());
                // 姓名_カナ名
                mstStuEntity.setFlnmKnLnm(f00042Dto.getFlnmKnLnm());
                // 性別区分
                mstStuEntity.setGendrDiv(mstStu.getGendrDiv());
                // 生年月日
                mstStuEntity.setBirthd(mstStu.getBirthd());
                // 写真パス
                mstStuEntity.setPhotPath(mstStu.getPhotPath());
                // 学年区分
                mstStuEntity.setSchyDiv(f00042Dto.getSchoolYear());
                //QRコード
                mstStuEntity.setQrCod(newMaxId);
                //オリジナルCD
                mstStuEntity.setOriaCd(mstStu.getOriaCd());
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
                    if (!StringUtils.isEmpty(((PSQLException) e.getCause()).getServerErrorMessage().getConstraint())) {
                        throw new RRException("該当保護者が存在しています。");
                    }
                    if (!StringUtils.isEmpty(((PSQLException) e.getCause()).getServerErrorMessage().getRoutine())) {
                        throw new RRException("データが長すぎます。");
                    }
                }
                //キャラクターID
                sysUserRoleEntity.setRoleId(2L);
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

    /**
     * 現在のユーザー組織と下位組織のIDセットを取得します
     */
    private List<String> getCurrentUserOrgAndLowerOrgIds(){
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        return getOrgAndLowerOrgIds(orgId).stream()
                .map(MstOrgEntity::getOrgId)
                .collect(Collectors.toList());
    }

    /**
     * 現在および下位の組織を取得するための組織リストを指定します
     */
    private List<String> getOrgAndLowerOrgIds(List<MstOrgEntity> selectedOrg) {
        Set<MstOrgEntity> allOrgList = new HashSet<>();
        for (MstOrgEntity orgEntity : selectedOrg) {
            List<OrgAndLowerOrgIdDto> thisAndLowerOrg = getOrgAndLowerOrgIds(orgEntity.getOrgId());
            allOrgList.addAll(thisAndLowerOrg);
        }

        return allOrgList.stream()
                .map(MstOrgEntity::getOrgId)
                .collect(Collectors.toList());
    }

    /**
     * 本組織と下組織のIDセットを取得します。
     */
    private List<OrgAndLowerOrgIdDto> getOrgAndLowerOrgIds(String orgId) {
        String brandCd = ShiroUtils.getBrandcd();
        return commonService.getThisAndLowerOrgId(brandCd, orgId);
    }

    /**
     * てユーザー情報を削除します
     *
     * @param mstUsrEntity 削除するユーザー情報
     */
    private void deleteUsrInfoAndRole(MstUsrEntity mstUsrEntity) {
        mstUsrEntity.setDelFlg(1);
        mstUsrDao.updateById(mstUsrEntity);

        switch (StringUtils.trim(mstUsrEntity.getRoleDiv())) {
            case "1":
                MstManagerEntity mstManagerEntity = mstManagerDao.selectOne(new QueryWrapper<MstManagerEntity>()
                        .eq("del_flg", 0)
                        .eq("mgr_id", mstUsrEntity.getUsrId()));

                mstManagerEntity.setDelFlg(1);
                mstManagerDao.updateById(mstManagerEntity);
                break;
            case "2":
                MstMentorEntity mstMentorEntity = mstMentorDao.selectOne(new QueryWrapper<MstMentorEntity>()
                        .eq("del_flg", 0)
                        .eq("mentor_id", mstUsrEntity.getUsrId()));

                mstMentorEntity.setDelFlg(1);
                mstMentorDao.updateById(mstMentorEntity);
                break;
            case "3":
                MstGuardEntity mstGuardEntity = mstGuardDao.selectOne(new QueryWrapper<MstGuardEntity>()
                        .eq("del_flg", 0)
                        .eq("guard_id", mstUsrEntity.getUsrId()));

                mstGuardEntity.setDelFlg(1);
                mstGuardDao.updateById(mstGuardEntity);
                break;
            case "4":
                MstStuEntity mstStuEntity = mstStuDao.selectOne(new QueryWrapper<MstStuEntity>()
                        .eq("del_flg", 0)
                        .eq("stu_id", mstUsrEntity.getUsrId()));

                mstStuEntity.setDelFlg(1);
                mstStuDao.updateById(mstStuEntity);
                break;
            default:
                throw new IllegalArgumentException("Wrong user role" + mstUsrEntity.getRoleDiv());
        }
    }

    /**
     * 排他ロックを確認してみてください
     *
     * @throws RRException 排他ロックエラー
     */
    private void tryCheckExclusiveLock(String usrId, String updateTime, String updDatime) {
        String nowUpdateTime;

        // ユーザ基本マスタ
        MstUsrEntity mstUsrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>()
                .eq("usr_id", usrId)
                .eq("del_flg", 0));

        // 排他チェックエラーの場合
        nowUpdateTime = DateUtils.format(mstUsrEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
        if (!updDatime.equals(nowUpdateTime)) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
        }

        switch (StringUtils.trim(mstUsrEntity.getRoleDiv())) {
            case "1":
                MstManagerEntity mstManagerEntity = mstManagerDao.selectOne(new QueryWrapper<MstManagerEntity>()
                        .eq("del_flg", 0).eq("mgr_id", mstUsrEntity.getUsrId()));

                cacheRole.set(mstManagerEntity);
                // 排他チェックエラーの場合
                nowUpdateTime = DateUtils.format(mstManagerEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
                if (!updateTime.equals(nowUpdateTime)) {
                    throw new RRException((MessageUtils.getMessage("MSGCOMN0019")));
                }
                break;
            case "2":
                MstMentorEntity mstMentorEntity = mstMentorDao.selectOne(new QueryWrapper<MstMentorEntity>()
                        .eq("del_flg", 0).eq("mentor_id", mstUsrEntity.getUsrId()));

                nowUpdateTime = DateUtils.format(mstMentorEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
                if (!updateTime.equals(nowUpdateTime)) {
                    throw new RRException((MessageUtils.getMessage("MSGCOMN0019")));
                }
                cacheRole.set(mstMentorEntity);
                break;
            case "3":
                MstGuardEntity mstGuardEntity = mstGuardDao.selectOne(new QueryWrapper<MstGuardEntity>()
                        .eq("del_flg", 0).eq("guard_id", mstUsrEntity.getUsrId()));

                nowUpdateTime = DateUtils.format(mstGuardEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
                if (!updateTime.equals(nowUpdateTime)) {
                    throw new RRException((MessageUtils.getMessage("MSGCOMN0019")));
                }
                cacheRole.set(mstGuardEntity);
                break;
            case "4":
                MstStuEntity mstStuEntity = mstStuDao.selectOne(new QueryWrapper<MstStuEntity>()
                        .eq("del_flg", 0).eq("stu_id", mstUsrEntity.getUsrId()));

                nowUpdateTime = DateUtils.format(mstStuEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
                if (!updateTime.equals(nowUpdateTime)) {
                    throw new RRException((MessageUtils.getMessage("MSGCOMN0019")));
                }
                cacheRole.set(mstStuEntity);
                break;
            default:
                throw new IllegalArgumentException("Wrong user role" + mstUsrEntity.getRoleDiv());
        }
    }
}
