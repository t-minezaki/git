package jp.learningpark.framework.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jp.gakken.id2.V2GakkenIDPrivilegeSvcStub;
import jp.gakken.id2.V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDshort;
import jp.gakken.id2.V2GakkenIDSvcStub;
import jp.gakken.id2.V2GakkenTransactionIDPrivilegeSvcStub;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.gakkenID.GakkenIdAPI;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import jp.learningpark.modules.sys.service.ShiroService;
import org.apache.axis2.databinding.ADBBean;
import org.apache.shiro.authc.LockedAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/9/25 ： NWT)hxl ： 新規作成
 * @date 2020/9/25 17:39
 */
public class CM0003 {

    private static GakkenIdAPI gakkenIdAPI;

    private static final int ACCOUNT_LOGIN_ERROR_LIMIT = 100;

    private static final AtomicReference<ShiroService> shiroServiceAtomicReference = new AtomicReference<>();

    private static final AtomicReference<MstUsrService> mstUsrServiceAtomicReference = new AtomicReference<>();

    private static final AtomicReference<MstManagerService> mstManagerServiceAtomicReference = new AtomicReference<>();

    private static final AtomicReference<MstMentorService> mstMentorServiceAtomicReference = new AtomicReference<>();

    private static final AtomicReference<MstGuardService> mstGuardServiceAtomicReference = new AtomicReference<>();

    private static final AtomicReference<MstStuService> mstStudentServiceAtomicReference = new AtomicReference<>();

    private static final Logger log = LoggerFactory.getLogger(CM0003.class);

    public static LoginCheckResultDto loginCheck(String loginId, String password) {
        if (StringUtils.isEmpty(loginId) || StringUtils.isEmpty(password)) {
            throw new RRException("アカウントとパスワードを入力してください。");
        }
        log.info("---------------------loginId: " + loginId + "--------------------");
        if (gakkenIdAPI == null) {
            gakkenIdAPI = SpringContextUtils.getBean(GakkenIdAPI.class);
        }
        setUpShiroService();
        LoginCheckResultDto loginCheckResultDto = new LoginCheckResultDto();
        try {
            //2.1.1　GIDアカウント不正チェック
//            boolean isGid = gakkenIdAPI.checkGID(loginId);
            //パスワード不正チェックを行う。
            V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDshort gakkenIDshort = gakkenIdAPI.loginWithGakkenID(loginId, password, null);
            boolean gidLoginCheckFlag = gakkenIDshort.getSuccess();
            //ログイン認証成功の場合、
            if (gidLoginCheckFlag) {
                log.info("---------------gid password check succeed----------------");
                log.info("---------------gidpk: " + gakkenIDshort.getGakkenID().getGidpk() + "----------------");
                //API返却値が「true：OK」の場合、入力したパスワードは正しい
                //PWチェックフラグ＝「1：OK」
                loginCheckResultDto.setPasswordCheckFlag(GakkenConstant.OK_FLAG);
                //GIDアカウントフラグ＝「1：GID」　且つ　PWチェックフラグ＝「1：OK」の場合、
                loginCheckResultDto.setGidCheckFlag(GakkenConstant.OK_FLAG);
                //認証成功後の結構体＝loginWithGakkenID返却した結構体
                loginCheckResultDto.setGakkenIDshort(gakkenIDshort);
                //学研IDプライマリキー
                loginCheckResultDto.setGidpk(gakkenIDshort.getGakkenID().getGidpk());
                //メールアドレス
                String mailad = gakkenIDshort.getGakkenID().getMailaddress_1();
                //トランザクションから電子メールアドレスを検索する必要がありますか？
                boolean searchFromTransaction = true;
                if (StringUtils.isNotBlank(mailad)) {
                    //メールアドレス
                    loginCheckResultDto.setMailad(mailad);
                    //トランザクションから電子メールアドレスを検索する必要はありません
                    searchFromTransaction = false;
                }
                //GIDPKをキーとして、GID側API（getGakkenTransactionIDsearch）を利用して、メールアドレスを取得する。
                V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionIDselect select = new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionIDselect();
                select.setEvent_key_1(true);
                select.setUpdate_ts(true);

                V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID gakkenTransactionIDForSearch = new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID();
                gakkenTransactionIDForSearch.setGidpk(loginCheckResultDto.getGidpk());
                gakkenTransactionIDForSearch.setSite_cd(gakkenIdAPI.getServiceCd2());
                log.info("---------------------------getGakkenTransactionIDsearch Site_cd: " + gakkenIdAPI.getServiceCd2() + "---------------------------");
                if (searchFromTransaction){
                    select.setMailaddress_1(true);
                    gakkenTransactionIDForSearch.setEvent_key_1(gakkenIdAPI.getGakkenMailKey());
                    V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs gakkenTransactionIDsearchMail = gakkenIdAPI.getGakkenTransactionIDsearch(gakkenTransactionIDForSearch, select);
                    if (gakkenTransactionIDsearchMail != null && gakkenTransactionIDsearchMail.getRecordCounts() > 0){
                        int indexForMailad = -1;
                        long lastTimeForMailad = -1L;
                        V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID[] gakkenTransactionID = gakkenTransactionIDsearchMail.getGakkenTransactionID();
                        for (int i = 0; i < gakkenTransactionID.length; i++) {
                            if (gakkenTransactionID[i] == null){
                                continue;
                            }
                            /* 2021/09/15 manamiru1-772 cuikl edit start */
                            log.debug("---------------------------transaction key: " + gakkenTransactionID[i].getEvent_key_1() + ",value: " +
                                    gakkenTransactionID[i].getMailaddress_1() + ",update_ts: " + gakkenTransactionID[i].getUpdate_ts() + "---------------------------");
                            /* 2021/09/15 manamiru1-772 cuikl edit end */
                            //メールアドレス
                            if (StringUtils.equals(gakkenTransactionID[i].getEvent_key_1(), gakkenIdAPI.getGakkenMailKey())) {
                                if (gakkenTransactionID[i].getUpdate_ts().getTime() > lastTimeForMailad) {
                                    lastTimeForMailad = gakkenTransactionID[i].getUpdate_ts().getTime();
                                    indexForMailad = i;
                                }
                            }
                            if (indexForMailad >= 0) {
                                /* 2021/09/15 manamiru1-772 cuikl edit start */
                                log.debug("---------------mailad get : " + gakkenTransactionID[indexForMailad].getMailaddress_1() + "----------------");
                                /* 2021/09/15 manamiru1-772 cuikl edit end */
                                loginCheckResultDto.setMailad(gakkenTransactionID[indexForMailad].getMailaddress_1());
                            }
                        }
                    }
                }
                gakkenTransactionIDForSearch.setEvent_key_1(gakkenIdAPI.getTeacherCodeKey());
                select.setEvent_key_2(true);
                select.setMailaddress_1(false);
                V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs gakkenTransactionIDsearchTeacherCd = gakkenIdAPI.getGakkenTransactionIDsearch(gakkenTransactionIDForSearch, select);
                if (gakkenTransactionIDsearchTeacherCd != null && gakkenTransactionIDsearchTeacherCd.getRecordCounts() > 0){
                    int indexForTchCd = -1;
                    long lastTimeForTchCd = -1L;
                    V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID[] gakkenTransactionID = gakkenTransactionIDsearchTeacherCd.getGakkenTransactionID();
                    for (int i = 0; i < gakkenTransactionID.length; i++) {
                        if (gakkenTransactionID[i] == null){
                            continue;
                        }
                        /* 2021/09/15 manamiru1-772 cuikl edit start */
                        log.debug("---------------------------transactionID key: " + gakkenTransactionID[i].getEvent_key_1() + ",value: " +
                                gakkenTransactionID[i].getEvent_key_2() + ",update_ts: " + gakkenTransactionID[i].getUpdate_ts() + "---------------------------");
                        /* 2021/09/15 manamiru1-772 cuikl edit end */
                        //指導者管理コード
                        if (StringUtils.equals(gakkenTransactionID[i].getEvent_key_1(), gakkenIdAPI.getTeacherCodeKey())) {
                            if (gakkenTransactionID[i].getUpdate_ts().getTime() > lastTimeForTchCd) {
                                lastTimeForTchCd = gakkenTransactionID[i].getUpdate_ts().getTime();
                                indexForTchCd = i;
                            }
                        }
                    }
                    if (indexForTchCd >= 0) {
                        log.info("---------------tchCd get : " + gakkenTransactionID[indexForTchCd].getEvent_key_2() + "----------------");
                        loginCheckResultDto.setTchCd(gakkenTransactionID[indexForTchCd].getEvent_key_2());
                    }
                }
            }
        } catch (Exception e) {
            log.error("An exception occurred during GID inspection: " + e.getMessage());
        }
        //ユーザ基本マスタ．ロール区分
        List<MstUsrEntity> mstUsrEntityList = null;
        //NWT　楊　2021/07/15　MANAMIRU1-727　Edit　Start
        boolean tchCdLoginFlg = false;
        //NWT　楊　2021/07/15　MANAMIRU1-727　Edit　End
        if (StringUtils.equals(GakkenConstant.OK_FLAG, loginCheckResultDto.getGidCheckFlag())) {
            do {
                //NWT　楊　2021/06/28　MANAMIRU1-698.2 -->1-720　Edit　Start
                //NWT　楊　2021/07/01　MANAMIRU1-703　Edit　Start
                if (!StringUtils.isEmpty(loginCheckResultDto.getGidpk())) {
                    //GIDアカウントとパスワードの認証が成功しました
                    mstUsrEntityList = shiroServiceAtomicReference.get().gidpkCount(loginCheckResultDto.getGidpk());
                    if (mstUsrEntityList.size() > 0) {
                        log.info("---------------gid exist check by gidpk succeed----------------");
                        loginCheckResultDto.setUserExistsFlag(GakkenConstant.OK_FLAG);
                        loginCheckResultDto.setLoginCheckFlag(GakkenConstant.OK_FLAG);
                        break;
                    }
                }
                if (!StringUtils.isEmpty(loginCheckResultDto.getTchCd())) {
                    //NWT　楊　2021/07/21　MANAMIRU1-727.④　Delete　Start
                    mstUsrEntityList = shiroServiceAtomicReference.get().tchCdCount(loginCheckResultDto.getTchCd(),null);
                    //NWT　楊　2021/07/21　MANAMIRU1-727.④　Delete　End
                    if (mstUsrEntityList.size() > 0) {
                        //NWT　楊　2021/07/15　MANAMIRU1-727　Edit　Start
                        tchCdLoginFlg = true;
                        //NWT　楊　2021/07/15　MANAMIRU1-727　Edit　End
                        log.info("---------------gid exist check by tchCd succeed----------------");
                        loginCheckResultDto.setUserExistsFlag(GakkenConstant.OK_FLAG);
                        loginCheckResultDto.setLoginCheckFlag(GakkenConstant.OK_FLAG);
                    }
                }
                //NWT　楊　2021/07/01　MANAMIRU1-703　Edit　End
            } while (false);
        } else {
            manaloginCheck(loginId, password, loginCheckResultDto);
        }
        if (StringUtils.equals(GakkenConstant.OK_FLAG, loginCheckResultDto.getGidCheckFlag()) &&
                StringUtils.equals(GakkenConstant.OK_FLAG, loginCheckResultDto.getLoginCheckFlag())) {
            //4.1.1　ユーザ基本マスタへの情報同期処理行う。
            updateGidUserData(loginId, loginCheckResultDto);
            if (mstUsrEntityList != null && mstUsrEntityList.size() > 0) {
                List<String> usrIdList = mstUsrEntityList.stream()
                        .map(MstUsrEntity::getUsrId)
                        .collect(Collectors.toList());
                //NWT　楊　2021/07/15　MANAMIRU1-727　Edit　Start
                if (StringUtils.isNotBlank(mstUsrEntityList.get(0).getGidpk()) && !tchCdLoginFlg) {
                    //NWT　楊　2021/07/15　MANAMIRU1-727　Edit　End
                    //NWT　楊　2021/06/28　MANAMIRU1-698.2 -->1-720　Edit　End
                    switch (StringUtils.trim(mstUsrEntityList.get(0).getRoleDiv())) {
                        case GakkenConstant.MANAGER_ROLE_DIV:
                            //4.1.4　管理者ログインの場合、API返却したGID情報をもとに、マナミルの管理者情報を更新する。
                            updateMstManager(loginCheckResultDto, usrIdList, loginId);
                            break;
                        case GakkenConstant.MENTOR_ROLE_DIV:
                            //4.1.5　メンターログインの場合、API返却したGID情報をもとに、マナミルのメンター情報を更新する。
                            updateMstMentor(loginCheckResultDto, usrIdList, loginId);
                            break;
                        case GakkenConstant.GUARD_ROLE_DIV:
                            //4.1.3　保護者ログインの場合、GIDのAPI返却した基本情報をもとに、マナミル側の保護者基本情報を同期する。
                            updateMstGuard(loginCheckResultDto, usrIdList, loginId);
                            List<MstUsrEntity> studentGidkpList = shiroServiceAtomicReference.get().selectStudentGidkpList(usrIdList);
                            updateMstStuByStudentGidpkList(loginId, studentGidkpList);
                            break;
                        case GakkenConstant.STUDENT_ROLE_DIV:
                            //4.1.2　生徒ログインの場合、API返却したGID情報をもとに、マナミルの生徒情報を更新する。
                            updateMstStu(loginCheckResultDto.getGakkenIDshort().getGakkenID(), usrIdList, loginId);
                            break;
                        default:
                    }
                }
            }
            //4.1.6　管理者、メンターと保護者のメールアドレスはGID側へ同期するか否か判断処理を行う。
            if (StringUtils.isEmpty(loginCheckResultDto.getMailad())) {
                assert mstUsrEntityList != null;
                List<String> usrIdList = mstUsrEntityList.stream()
                        .map(MstUsrEntity::getUsrId)
                        .collect(Collectors.toList());
                //4.1.6.1　マナミルのメールアドレスを取得する。
                String dbMailAd = null;
                switch (StringUtils.trim(mstUsrEntityList.get(0).getRoleDiv())) {
                    case GakkenConstant.MANAGER_ROLE_DIV:
                        setUpMstManagerService();
                        List<MstManagerEntity> managerEntityList = mstManagerServiceAtomicReference.get().list(new QueryWrapper<MstManagerEntity>()
                                .select("distinct mailad")
                                .in("mgr_id", usrIdList)
                                .isNotNull("mailad")
                                .ne("trim(mailad)", "")
                                .last("limit 1"));
                        if (managerEntityList.size() > 0) {
                            dbMailAd = managerEntityList.get(0).getMailad();
                        }
                        break;
                    case GakkenConstant.MENTOR_ROLE_DIV:
                        setUpMstMentorService();
                        List<MstMentorEntity> mentorEntityList = mstMentorServiceAtomicReference.get().list(new QueryWrapper<MstMentorEntity>()
                                .select("distinct mailad")
                                .in("mentor_id", usrIdList)
                                .isNotNull("mailad")
                                .ne("trim(mailad)", "")
                                .last("limit 1"));
                        if (mentorEntityList.size() > 0) {
                            dbMailAd = mentorEntityList.get(0).getMailad();
                        }
                        break;
                    case GakkenConstant.GUARD_ROLE_DIV:
                        setUpMstGuardService();
                        List<MstGuardEntity> guardEntityList = mstGuardServiceAtomicReference.get().list(new QueryWrapper<MstGuardEntity>()
                                .select("distinct mailad")
                                .in("guard_id", usrIdList)
                                .isNotNull("mailad")
                                .ne("trim(mailad)", "")
                                .last("limit 1"));
                        if (guardEntityList.size() > 0) {
                            dbMailAd = guardEntityList.get(0).getMailad();
                        }
                        break;
                    default:
                }
                //（1）　ログイン時、API返却したメールアドレスが存在する場合、　且つ　マナミル側のメールアドレスが存在する場合、
                if (StringUtils.isNotBlank(dbMailAd)) {
                    pushMailToGID(loginCheckResultDto.getGakkenIDshort().getGakkenID().getGidpk(), dbMailAd);
                }
            }
        }
        return loginCheckResultDto;
    }

    /**
     * gidユーザーデータを更新する
     *
     * @param loginCheckResultDto ユーザーデータ
     */
    private static void updateGidUserData(String loginId, LoginCheckResultDto loginCheckResultDto) {
        log.info("---------------update mst_usr----------------");
        setUpMstUsrService();
        V2GakkenIDPrivilegeSvcStub.GakkenID gakkenID = loginCheckResultDto.getGakkenIDshort().getGakkenID();
        //システム日時
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        //API返却したデータ利用許諾（agree_flg）
        short agreeFlg = gakkenID.getAgree_flg();
        log.info("---------------------------received agree_flg: " + agreeFlg + "---------------------------");
        //API返却した姓＋半角スペース＋API返却した名
        String usrNm = (Optional.ofNullable(gakkenID.getLastname()).orElse("")
                + " "
                + Optional.ofNullable(gakkenID.getFirstname()).orElse(""));
        //hxl modify 2020/11/03
        MstUsrEntity mstUsrEntity = new MstUsrEntity();
        //ユーザ名
        mstUsrEntity.setUsrNm(usrNm);
        //変更後ユーザーID
        mstUsrEntity.setAfterUsrId(loginId);
        // 2020/12/8 huangxinliang modify start
        //GakkenID規約フラグ
        mstUsrEntity.setGidRuleFlg(Integer.toString(agreeFlg));
        // 2020/12/8 huangxinliang modify end
        //更新日時
        mstUsrEntity.setUpdDatime(sysTimestamp);
        //更新ユーザＩＤ
        mstUsrEntity.setUpdUsrId(loginId);
        try {
            mstUsrServiceAtomicReference.get().update(mstUsrEntity, new UpdateWrapper<MstUsrEntity>().eq("gidpk", gakkenID.getGidpk()));
        }catch (Exception e){
            log.error(MessageUtils.getMessage("MSGCOMN0181", "GIDのデータ利用許諾"));
        }
        log.info("---------------update gid by gidpk succeed----------------");
//        if (!StringUtils.isEmpty(loginCheckResultDto.getTchCd())) {
//            shiroServiceAtomicReference.get().updateGidpkByTchCd(loginCheckResultDto.getTchCd(), usrNm, gakkenID.getGidpk(), loginId,
//                    Integer.toString(agreeFlg), sysTimestamp);
//            log.info("---------------update gid by tchCd succeed----------------");
//        }
    }

    private static void updateMstManager(LoginCheckResultDto loginCheckResultDto, List<String> usrIdList, String loginId) {
        log.info("---------------update mst_manager----------------");
        setUpMstManagerService();
        V2GakkenIDPrivilegeSvcStub.GakkenID gakkenID = loginCheckResultDto.getGakkenIDshort().getGakkenID();
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        MstManagerEntity mstManagerEntity = new MstManagerEntity();
        //メールアドレス
        //NWT　楊　2021/06/23　MANAMIRU1-698.6 --> 1-720　Edit　Start
        if(StringUtils.isNotBlank(loginCheckResultDto.getMailad())){
            mstManagerEntity.setMailad(loginCheckResultDto.getMailad());
        }
        //NWT　楊　2021/06/23　MANAMIRU1-698.6 --> 1-720　Edit　End
        //NWT　楊　2021/07/21　MANAMIRU1-718　Delete　Start
        //NWT　楊　2021/07/21　MANAMIRU1-718　Delete　End
        //姓名_姓
        mstManagerEntity.setFlnmNm(Optional.ofNullable(gakkenID.getLastname()).orElse(""));
        //姓名_名
        mstManagerEntity.setFlnmLnm(Optional.ofNullable(gakkenID.getFirstname()).orElse(""));
        //姓名_カナ姓
        mstManagerEntity.setFlnmKnNm(Optional.ofNullable(gakkenID.getKana_lastname()).orElse(""));
        //姓名_カナ名
        mstManagerEntity.setFlnmKnLnm(Optional.ofNullable(gakkenID.getKana_firstname()).orElse(""));
        //電話番号
        mstManagerEntity.setTelnum(gakkenID.getTel());
        //更新日時
        mstManagerEntity.setUpdDatime(sysTimestamp);
        //更新ユーザＩＤ
        mstManagerEntity.setUpdUsrId(loginId);
        //管理者基本マスタ更新
        mstManagerServiceAtomicReference.get().update(mstManagerEntity, new UpdateWrapper<MstManagerEntity>()
                .in("mgr_id", usrIdList));
        log.info("---------------update mst_manager success----------------");
    }

    private static void updateMstMentor(LoginCheckResultDto loginCheckResultDto, List<String> usrIdList, String loginId) {
        log.info("---------------update mst_mentor----------------");
        setUpMstMentorService();
        V2GakkenIDPrivilegeSvcStub.GakkenID gakkenID = loginCheckResultDto.getGakkenIDshort().getGakkenID();
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        MstMentorEntity mstMentorEntity = new MstMentorEntity();
        //メールアドレス
        //NWT　楊　2021/06/23　MANAMIRU1-698.6 --> 1-720　Edit　Start
        if(StringUtils.isNotBlank(loginCheckResultDto.getMailad())){
            mstMentorEntity.setMailad(loginCheckResultDto.getMailad());
        }
        //NWT　楊　2021/06/23　MANAMIRU1-698.6 --> 1-720　Edit　End
        //姓名_姓
        mstMentorEntity.setFlnmNm(Optional.ofNullable(gakkenID.getLastname()).orElse(""));
        //姓名_名
        mstMentorEntity.setFlnmLnm(Optional.ofNullable(gakkenID.getFirstname()).orElse(""));
        //姓名_カナ姓
        mstMentorEntity.setFlnmKnNm(Optional.ofNullable(gakkenID.getKana_lastname()).orElse(""));
        //姓名_カナ名
        mstMentorEntity.setFlnmKnLnm(Optional.ofNullable(gakkenID.getKana_firstname()).orElse(""));
        //電話番号
        mstMentorEntity.setTelnum(gakkenID.getTel());
        //更新日時
        mstMentorEntity.setUpdDatime(sysTimestamp);
        //更新ユーザＩＤ
        mstMentorEntity.setUpdUsrId(loginId);
        //メンター基本マスタ更新
        mstMentorServiceAtomicReference.get().update(mstMentorEntity, new UpdateWrapper<MstMentorEntity>()
                .in("mentor_id", usrIdList));
        log.info("---------------update mst_mentor success----------------");
    }

    private static void updateMstGuard(LoginCheckResultDto loginCheckResultDto, List<String> usrIdList, String loginId) {
        log.info("---------------update mst_guard----------------");
        setUpMstGuardService();
        V2GakkenIDPrivilegeSvcStub.GakkenID gakkenID = loginCheckResultDto.getGakkenIDshort().getGakkenID();
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        MstGuardEntity mstGuardEntity = new MstGuardEntity();
        //メールアドレス
        //NWT　楊　2021/06/23　MANAMIRU1-698.6 --> 1-720　Edit　Start
        if(StringUtils.isNotBlank(loginCheckResultDto.getMailad())){
            mstGuardEntity.setMailad( loginCheckResultDto.getMailad());
        }
        //NWT　楊　2021/06/23　MANAMIRU1-698.6 --> 1-720　Edit　End
        //姓名_姓
        mstGuardEntity.setFlnmNm(Optional.ofNullable(gakkenID.getLastname()).orElse(""));
        //姓名_名
        mstGuardEntity.setFlnmLnm(Optional.ofNullable(gakkenID.getFirstname()).orElse(""));
        //姓名_カナ姓
        mstGuardEntity.setFlnmKnNm(Optional.ofNullable(gakkenID.getKana_lastname()).orElse(""));
        //姓名_カナ名
        mstGuardEntity.setFlnmKnLnm(Optional.ofNullable(gakkenID.getKana_firstname()).orElse(""));
        //郵便番号_主番
        mstGuardEntity.setPostcdMnum(StringUtils.isEmpty(gakkenID.getZip()) ? "" : gakkenID.getZip().substring(0, 3));
        //郵便番号_枝番
        mstGuardEntity.setPostcdBnum(StringUtils.isEmpty(gakkenID.getZip()) ? "" : gakkenID.getZip().substring(3));
        //住所１
        mstGuardEntity.setAdr1(Optional.ofNullable(gakkenID.getAddress_1()).orElse("")
                + Optional.ofNullable(gakkenID.getAddress_2()).orElse(""));
        //住所2
        mstGuardEntity.setAdr2(Optional.ofNullable(gakkenID.getAddress_3()).orElse("")
                + Optional.ofNullable(gakkenID.getAddress_4()).orElse(""));
        //電話番号
        mstGuardEntity.setTelnum(gakkenID.getTel());
        //電話番号2
        mstGuardEntity.setUrgTelnum(gakkenID.getTel_2());
        //更新日時
        mstGuardEntity.setUpdDatime(sysTimestamp);
        //更新ユーザＩＤ
        mstGuardEntity.setUpdUsrId(loginId);
        //保護者基本マスタ更新
        mstGuardServiceAtomicReference.get().update(mstGuardEntity, new UpdateWrapper<MstGuardEntity>()
                .in("guard_id", usrIdList));
        log.info("---------------update mst_guard success----------------");
    }

    private static void updateMstStu(ADBBean gakkenID, List<String> usrIdList, String loginId) {
        log.info("---------------update mst_stu----------------");
        setUpMstStudentService();
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        MstStuEntity mstStuEntity = new MstStuEntity();
        if (gakkenID instanceof V2GakkenIDPrivilegeSvcStub.GakkenID || gakkenID instanceof V2GakkenIDSvcStub.GakkenID) {
            Class<? extends ADBBean> gakkenIDClass = gakkenID.getClass();
            try {
                //姓名_名
                Field localFirstname = gakkenIDClass.getDeclaredField("localFirstname");
                localFirstname.setAccessible(true);
                mstStuEntity.setFlnmLnm(Optional.ofNullable((String) localFirstname.get(gakkenID)).orElse(""));
                //姓名_姓
                Field localLastname = gakkenIDClass.getDeclaredField("localLastname");
                localLastname.setAccessible(true);
                mstStuEntity.setFlnmNm(Optional.ofNullable((String) localLastname.get(gakkenID)).orElse(""));
                //姓名_カナ名
                Field localKana_firstname = gakkenIDClass.getDeclaredField("localKana_firstname");
                localKana_firstname.setAccessible(true);
                mstStuEntity.setFlnmKnLnm(Optional.ofNullable((String) localKana_firstname.get(gakkenID)).orElse(""));
                //姓名_カナ姓
                Field localKana_lastname = gakkenIDClass.getDeclaredField("localKana_lastname");
                localKana_lastname.setAccessible(true);
                mstStuEntity.setFlnmKnNm(Optional.ofNullable((String) localKana_lastname.get(gakkenID)).orElse(""));
                //性別区分
                Field localSex = gakkenIDClass.getDeclaredField("localSex");
                localSex.setAccessible(true);
                switch ((short) localSex.get(gakkenID)) {
                    case 0:
                        mstStuEntity.setGendrDiv("1");
                        break;
                    case 1:
                        mstStuEntity.setGendrDiv("2");
                        break;
                    default:
                        mstStuEntity.setGendrDiv("9");
                }
                //生年月日
                Field localBirth = gakkenIDClass.getDeclaredField("localBirth");
                localBirth.setAccessible(true);
                mstStuEntity.setBirthd((Date) localBirth.get(gakkenID));
                //更新日時
                mstStuEntity.setUpdDatime(sysTimestamp);
                //更新ユーザＩＤ
                mstStuEntity.setUpdUsrId(loginId);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error(e.getMessage());
                return;
            }
            //生徒基本マスタ更新
            mstStudentServiceAtomicReference.get().update(mstStuEntity, new UpdateWrapper<MstStuEntity>()
                    .in("stu_id", usrIdList));
        }
        log.info("---------------update mst_stu success----------------");
    }

    public static void updateMstStuByStudentGidpkList(String loginId, List<MstUsrEntity> studentGidpkList) {
        log.info("---------------update mst_stu by stu gidpk List----------------");
        //4.1.3　保護者ログインの場合、GIDのAPI返却した基本情報をもとに、マナミル側の保護者と選択した生徒の基本情報を同期する。
        if (gakkenIdAPI == null) {
            gakkenIdAPI = SpringContextUtils.getBean(GakkenIdAPI.class);
        }
        Map<String, List<String>> mapOfGidpkAndUsrIdList = studentGidpkList.stream().collect(Collectors.groupingBy(
                MstUsrEntity::getGidpk, Collectors.mapping(
                        MstUsrEntity::getUsrId, Collectors.toList()
                )
        ));
        Set<String> stuGidpkSet = mapOfGidpkAndUsrIdList.keySet();
        String[] stuGidpkArray = stuGidpkSet.toArray(new String[0]);
        try {
            V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDs gakkenIDBulk = gakkenIdAPI.getGakkenIDBulk(stuGidpkArray, null);
            if (gakkenIDBulk.getGakkenID() != null && gakkenIDBulk.getGakkenID().length > 0) {
                V2GakkenIDPrivilegeSvcStub.GakkenID[] gakkenIDS = gakkenIDBulk.getGakkenID();
                Arrays.stream(gakkenIDS).forEach(
                        gakkenID -> {
                            if (gakkenID != null){
                                List<String> stuIdList = mapOfGidpkAndUsrIdList.get(gakkenID.getGidpk());
                                //[DBセット]シートのDBセット名[生徒基本マスタ更新]
                                updateMstStu(gakkenID, stuIdList, loginId);
                                //ユーザ基本マスタには生徒のユーザ名を更新する。
                                MstUsrEntity mstUsrEntity = new MstUsrEntity();
                                mstUsrEntity.setUsrNm((Optional.ofNullable(gakkenID.getLastname()).orElse(""))
                                        + " "
                                        + (Optional.ofNullable(gakkenID.getFirstname()).orElse("")));
                                mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
                                mstUsrEntity.setUpdUsrId(loginId);
                                mstUsrServiceAtomicReference.get().update(mstUsrEntity, new UpdateWrapper<MstUsrEntity>()
                                        .in("usr_id", stuIdList));
                            }else {
                                log.info("---------------getGakkenID failure----------------");
                            }
                        }
                );
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("---------------update mst_stu by stu gidpk List success----------------");
    }

    public static void pushMailToGID(String gidpk, String mailad) {
        log.info("---------------push mailad to gid----------------");
        if (gakkenIdAPI == null) {
            gakkenIdAPI = SpringContextUtils.getBean(GakkenIdAPI.class);
        }
        boolean isSave;
        try {
            isSave = gakkenIdAPI.checkMailaddress(mailad);
            if (isSave) {
                log.info("---------------mailad check result is true----------------");
                //返却した結果が「TRUE：重複」の場合
                V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID[] gakkenIDs = new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID[1];
                gakkenIDs[0] = new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID();
                //gidpk
                gakkenIDs[0].setGidpk(gidpk);
                //get_mail_address
                gakkenIDs[0].setEvent_key_1(gakkenIdAPI.getGakkenMailKey());
                //メールアドレス
                gakkenIDs[0].setMailaddress_1(mailad);
                //サービスサイトコード
                gakkenIDs[0].setSite_cd(gakkenIdAPI.getServiceCd2());
                log.info("---------------------------transaction Site_cd: " + gakkenIdAPI.getServiceCd2() + "---------------------------");
                /* 2021/09/15 manamiru1-772 cuikl edit start */
                log.debug("---------------------------mail: " + mailad + "---------------------------");
                /* 2021/09/15 manamiru1-772 cuikl edit end */
                //GTID情報を作成する
                gakkenIdAPI.registGakkenTransactionIDBulk(gakkenIDs);
            } else {
                log.info("---------------mailad check result is false----------------");
                //返却した結果が「FALSE：重複しない」の場合
                V2GakkenIDPrivilegeSvcStub.GakkenID[] gakkenIDs = new V2GakkenIDPrivilegeSvcStub.GakkenID[1];
                gakkenIDs[0] = new V2GakkenIDPrivilegeSvcStub.GakkenID();
                //GIDPK
                gakkenIDs[0].setGidpk(gidpk);
                //メールアドレス
                gakkenIDs[0].setMailaddress_1(mailad);
                /* 2021/09/15 manamiru1-772 cuikl edit start */
                log.debug("---------------------------mail: " + mailad + "---------------------------");
                /* 2021/09/15 manamiru1-772 cuikl edit end */
                V2GakkenIDPrivilegeSvcStub.GakkenIDselect gakkenIdSelect = new V2GakkenIDPrivilegeSvcStub.GakkenIDselect();
                //GIDPK更新する
                gakkenIdSelect.setGidpk(true);
                //メールアドレス更新する
                gakkenIdSelect.setMailaddress_1(true);
                //GID情報のメールアドレスを更新する
                gakkenIdAPI.updateGakkenIDBulk(gakkenIDs, gakkenIdSelect);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(MessageUtils.getMessage("MSGCOMN0181", "GIDのメールアドレス"));
        }
        log.info("---------------push mailad to gid success----------------");
    }

    private static void manaloginCheck(String loginId, String password, LoginCheckResultDto loginCheckResultDto) {
        log.info("---------------mana account----------------");
        List<SysUserEntity> sysUserEntityList = shiroServiceAtomicReference.get().getUserByLoginId(loginId, "1", null, "0", "0");
        if (sysUserEntityList.size() > 0) {
            loginCheckResultDto.setUserExistsFlag(GakkenConstant.OK_FLAG);
            SysUserEntity sysUser = sysUserEntityList.get(0);
            if (sysUser.getErrorCount() == ACCOUNT_LOGIN_ERROR_LIMIT) {
                throw new LockedAccountException();
            }
            boolean passwordCheck = false;
            for (SysUserEntity sysUserEntity : sysUserEntityList) {
                if (StringUtils.equals(ShiroUtils.sha256(password + "", sysUserEntity.getUsrId()), sysUserEntity.getUsrPassword())) {
                    passwordCheck = true;
                    break;
                }
            }
            if (passwordCheck) {
                loginCheckResultDto.setLoginCheckFlag(GakkenConstant.OK_FLAG);
                loginCheckResultDto.setPasswordCheckFlag(GakkenConstant.OK_FLAG);
                log.info("---------------mana check succeed----------------");
            } else {
                log.info("---------------mana pass check failure----------------");
            }
        } else {
            log.info("---------------mana account number is zero----------------");
        }
    }

    public static class LoginCheckResultDto {
        /**
         * ユーザー存在フラグ：「1：存在」「0：存在しません」
         */
        private String userExistsFlag;
        /**
         * PWチェックフラグ：「1：OK」「0：NG」
         */
        private String passwordCheckFlag;
        /**
         * GID認証フラグ：「1：成功」「0：失敗」
         */
        private String gidCheckFlag;
        /**
         * 認証成功後の結構体
         */
        private APIResultGakkenIDshort gakkenIDshort;
        /**
         * ログイン認証フラグ：「1：OK」「0：NG」
         */
        private String loginCheckFlag;
        /**
         * メールアドレス
         */
        private String mailad;
        /**
         * 指導者管理コード
         */
        private String tchCd;
        /**
         * 学研IDプライマリキー
         */
        private String gidpk;
        /**
         * ユーザーデータリスト
         */
        List<SysUserEntity> userEntityList;


        //出力パラメータの初期値を設定する。
        public LoginCheckResultDto() {
            //ユーザー存在フラグ＝「0：存在しません」
            this.userExistsFlag = GakkenConstant.NG_FLAG;
            //PWチェックフラグ＝「0：NG」
            this.passwordCheckFlag = GakkenConstant.NG_FLAG;
            //GID認証フラグ＝「0：失敗」
            this.gidCheckFlag = GakkenConstant.NG_FLAG;
            //認証成功後の結構体＝NULL
            this.gakkenIDshort = null;
            //ログイン認証フラグ＝「0：NG」
            this.loginCheckFlag = GakkenConstant.NG_FLAG;
        }

        /**
         * GIDアカウントフラグ：「1：GID」「0：非GID」を取得する
         *
         * @return gidAccountFlag GIDアカウントフラグ：「1：GID」「0：非GID」
         */
//        public String getGidAccountFlag() {
//            return this.gidAccountFlag;
//        }

        /**
         * GIDアカウントフラグ：「1：GID」「0：非GID」を設定する
         *
         * @param gidAccountFlag GIDアカウントフラグ：「1：GID」「0：非GID」
         */
//        public void setGidAccountFlag(String gidAccountFlag) {
//            this.gidAccountFlag = gidAccountFlag;
//        }

        /**
         * PWチェックフラグ：「1：OK」「0：NG」を取得する
         *
         * @return passwordCheckFlag PWチェックフラグ：「1：OK」「0：NG」
         */
        public String getPasswordCheckFlag() {
            return this.passwordCheckFlag;
        }

        /**
         * PWチェックフラグ：「1：OK」「0：NG」を設定する
         *
         * @param passwordCheckFlag PWチェックフラグ：「1：OK」「0：NG」
         */
        public void setPasswordCheckFlag(String passwordCheckFlag) {
            this.passwordCheckFlag = passwordCheckFlag;
        }

        /**
         * GID認証フラグ：「1：成功」「0：失敗」を取得する
         *
         * @return gidCheckFlag GID認証フラグ：「1：成功」「0：失敗」
         */
        public String getGidCheckFlag() {
            return this.gidCheckFlag;
        }

        /**
         * GID認証フラグ：「1：成功」「0：失敗」を設定する
         *
         * @param gidCheckFlag GID認証フラグ：「1：成功」「0：失敗」
         */
        public void setGidCheckFlag(String gidCheckFlag) {
            this.gidCheckFlag = gidCheckFlag;
        }

        /**
         * 認証成功後の結構体を取得する
         *
         * @return gakkenIDshort 認証成功後の結構体
         */
        public APIResultGakkenIDshort getGakkenIDshort() {
            return this.gakkenIDshort;
        }

        /**
         * 認証成功後の結構体を設定する
         *
         * @param gakkenIDshort 認証成功後の結構体
         */
        public void setGakkenIDshort(APIResultGakkenIDshort gakkenIDshort) {
            this.gakkenIDshort = gakkenIDshort;
        }

        /**
         * ログイン認証フラグ：「1：OK」「0：NG」を取得する
         *
         * @return loginCheckFlag ログイン認証フラグ：「1：OK」「0：NG」
         */
        public String getLoginCheckFlag() {
            return this.loginCheckFlag;
        }

        /**
         * ログイン認証フラグ：「1：OK」「0：NG」を設定する
         *
         * @param loginCheckFlag ログイン認証フラグ：「1：OK」「0：NG」
         */
        public void setLoginCheckFlag(String loginCheckFlag) {
            this.loginCheckFlag = loginCheckFlag;
        }

        /**
         * メールアドレスを取得する
         *
         * @return mailad メールアドレス
         */
        public String getMailad() {
            return this.mailad;
        }

        /**
         * メールアドレスを設定する
         *
         * @param mailad メールアドレス
         */
        public void setMailad(String mailad) {
            this.mailad = mailad;
        }

        /**
         * 指導者管理コードを取得する
         *
         * @return tchCd 指導者管理コード
         */
        public String getTchCd() {
            return this.tchCd;
        }

        /**
         * 指導者管理コードを設定する
         *
         * @param tchCd 指導者管理コード
         */
        public void setTchCd(String tchCd) {
            this.tchCd = tchCd;
        }

        /**
         * ユーザーデータリストを取得する
         *
         * @return userEntityList ユーザーデータリスト
         */
        public List<SysUserEntity> getUserEntityList() {
            return this.userEntityList;
        }

        /**
         * ユーザーデータリストを設定する
         *
         * @param userEntityList ユーザーデータリスト
         */
        public void setUserEntityList(List<SysUserEntity> userEntityList) {
            this.userEntityList = userEntityList;
        }

        /**
         * 学研IDプライマリキーを取得する
         *
         * @return gidpk 学研IDプライマリキー
         */
        public String getGidpk() {
            return this.gidpk;
        }

        /**
         * 学研IDプライマリキーを設定する
         *
         * @param gidpk 学研IDプライマリキー
         */
        public void setGidpk(String gidpk) {
            this.gidpk = gidpk;
        }

        /**
         * ユーザー存在フラグ：「1：存在」「0：存在しません」を取得する
         *
         * @return userExistsFlag ユーザー存在フラグ：「1：存在」「0：存在しません」
         */
        public String getUserExistsFlag() {
            return this.userExistsFlag;
        }

        /**
         * ユーザー存在フラグ：「1：存在」「0：存在しません」を設定する
         *
         * @param userExistsFlag ユーザー存在フラグ：「1：存在」「0：存在しません」
         */
        public void setUserExistsFlag(String userExistsFlag) {
            this.userExistsFlag = userExistsFlag;
        }
    }

    private static void setUpShiroService() {
        if (shiroServiceAtomicReference.get() == null) {
            synchronized (shiroServiceAtomicReference) {
                if (shiroServiceAtomicReference.get() == null) {
                    shiroServiceAtomicReference.set(SpringContextUtils.getBean(ShiroService.class));
                }
            }
        }
    }

    private static void setUpMstUsrService() {
        if (mstUsrServiceAtomicReference.get() == null) {
            synchronized (mstUsrServiceAtomicReference) {
                if (mstUsrServiceAtomicReference.get() == null) {
                    mstUsrServiceAtomicReference.set(SpringContextUtils.getBean(MstUsrService.class));
                }
            }
        }
    }

    private static void setUpMstManagerService() {
        if (mstManagerServiceAtomicReference.get() == null) {
            synchronized (mstManagerServiceAtomicReference) {
                if (mstManagerServiceAtomicReference.get() == null) {
                    mstManagerServiceAtomicReference.set(SpringContextUtils.getBean(MstManagerService.class));
                }
            }
        }
    }

    private static void setUpMstMentorService() {
        if (mstMentorServiceAtomicReference.get() == null) {
            synchronized (mstMentorServiceAtomicReference) {
                if (mstMentorServiceAtomicReference.get() == null) {
                    mstMentorServiceAtomicReference.set(SpringContextUtils.getBean(MstMentorService.class));
                }
            }
        }
    }

    private static void setUpMstGuardService() {
        if (mstGuardServiceAtomicReference.get() == null) {
            synchronized (mstGuardServiceAtomicReference) {
                if (mstGuardServiceAtomicReference.get() == null) {
                    mstGuardServiceAtomicReference.set(SpringContextUtils.getBean(MstGuardService.class));
                }
            }
        }
    }

    private static void setUpMstStudentService() {
        if (mstStudentServiceAtomicReference.get() == null) {
            synchronized (mstStudentServiceAtomicReference) {
                if (mstStudentServiceAtomicReference.get() == null) {
                    mstStudentServiceAtomicReference.set(SpringContextUtils.getBean(MstStuService.class));
                }
            }
        }
    }
}
