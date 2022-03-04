package jp.learningpark.modules.job.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.HalfConversionUtils;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.job.entity.*;
import jp.learningpark.modules.job.task.dao.BTGKA1004Dao;
import jp.learningpark.modules.manager.dao.F00004Dao;
import jp.learningpark.modules.manager.dto.F00004Dto;
import jp.learningpark.modules.sys.dao.SysUserRoleDao;
import jp.learningpark.modules.sys.entity.SysUserRoleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/8/31 ： NWT)hxl ： 新規作成
 * @date 2020/8/31 16:15
 */
@Component
public class BTGKA1004TransactionBean {

    /**
     * ユーザ基本マスタDao
     */
    @Autowired
    MstUsrDao mstUsrDao;

    /**
     * f00004Dao
     */
    @Autowired
    F00004Dao f00004Dao;

    /**
     * 管理者基本マスタDao
     */
    @Autowired
    MstManagerDao mstManagerDao;

    /**
     * メンター基本マスタDao
     */
    @Autowired
    MstMentorDao mstMentorDao;

    /**
     * 保護者基本マスタDao
     */
    @Autowired
    MstGuardDao mstGuardDao;

    /**
     * 生徒基本マスタDao
     */
    @Autowired
    MstStuDao mstStuDao;

    /**
     * 組織マスタDao
     */
    @Autowired
    MstOrgDao mstOrgDao;

    /**
     * 教室コース関連管理dao
     */
    @Autowired
    MstClassCourseDao mstClassCourseDao;

    /**
     * mstNumassDao
     */
    @Autowired
    MstNumassDao mstNumassDao;

    /**
     * マスタ_明細Dao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * btgka1004Dao
     */
    @Autowired
    private BTGKA1004Dao btgka1004Dao;

    /**
     * sysUserRoleDao
     */
    @Autowired
    private SysUserRoleDao sysUserRoleDao;


    /**
     * 都道府県名
     */
    private static final AtomicReference<Map<String, String>> mapOfPrefectures = new AtomicReference<>();

    /**
     * 学年区分対応関係データ
     */
    private static final AtomicReference<Map<String, String>> mapOfSchyDiv = new AtomicReference<>();

    /**
     * 性別区分対応関係データ
     */
    private static final AtomicReference<Map<String, String>> mapOfGenderDiv = new AtomicReference<>();

    int maxIdOfManager;
    int maxIdOfMentor;
    int maxIdOfGuard;
    int maxIdOfStu;

    MstUsrEntity mstUsrEntity = new MstUsrEntity();
    MstStuEntity mstStuEntity = new MstStuEntity();
    MstGuardEntity mstGuardEntity = new MstGuardEntity();

    /**
     * BTGKA1004MemberDto
     */
    BTGKA1004MemberDto btgka1004MemberDto = null;

    public BTGKA1004MemberDto getBtgka1004MemberDto() {
        return btgka1004MemberDto;
    }

    public void setBtgka1004MemberDto(BTGKA1004MemberDto btgka1004MemberDto) {
        this.btgka1004MemberDto = btgka1004MemberDto;
    }

    private static final Logger log = LoggerFactory.getLogger(BTGKA1004TransactionBean.class);

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public void saveOrUpdateMemberData(String orgPrefix) {
        loadMaps();
        //MaxId
        Map<String, Integer> maxId = getMaxId();

        //採番マスタ
        MstNumassEntity mstNumassEntity = new MstNumassEntity();
        //ブランドコード
        mstNumassEntity.setBrandCd("");
        //作成日時
        mstNumassEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        mstNumassEntity.setCretUsrId("system");
        //更新日時
        mstNumassEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        mstNumassEntity.setUpdUsrId("system");
        //del_flg
        mstNumassEntity.setDelFlg(0);

        getManagerMaxId(maxId, mstNumassEntity);

        getMentorMaxId(maxId, mstNumassEntity);

        getGuardMaxId(maxId, mstNumassEntity);

        getStuMaxId(maxId, mstNumassEntity);


        // 2020/12/8 huangxinliang modify start
        //2.2.2.1　ユーザ基本マスタのデータ存在チェック
        //1）　生徒データ取得
        List<BTGKA1004StudentDto> studentDtos = btgka1004Dao.selectStudentByGidpkAndOrgId(btgka1004MemberDto.getStuGidpk(),
                orgPrefix + btgka1004MemberDto.getOrgId());
        // 2020/12/24 huangxinliang modify start
        if (studentDtos.size() != 0 && !StringUtils.equals(studentDtos.get(0).getMemberCd(), btgka1004MemberDto.getMemberCd())){
            return;
        }
        // 2020/12/24 huangxinliang modify end
        //2）　保護者データ取得
        MstUsrEntity guardMstUsrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().eq("gidpk", btgka1004MemberDto.getGuardGidpk())
                .eq("org_id", orgPrefix + btgka1004MemberDto.getOrgId()).eq("system_kbn", "2"));
        String guardId = null;
        List<String> memberCodeList = btgka1004Dao.getMemberCodeList(btgka1004MemberDto.getGuardGidpk(), orgPrefix + btgka1004MemberDto.getOrgId());
        int guardDelFlg;
        int stuDelFlg;
        if (StringUtils.equals("1", btgka1004MemberDto.getReplyCancelFlag()) &&
                (StringUtils.equals("20", btgka1004MemberDto.getReplyContentDiv()) ||
                StringUtils.equals("21", btgka1004MemberDto.getReplyContentDiv()))) {
            //会員情報．申請取消フラグが「1：取消」の場合　且つ 会員情報．申込内容区分＝入会「20　または　21」の場合
            stuDelFlg = 1;
            guardDelFlg = 1;
            if (memberCodeList.size() >= 2){
                guardDelFlg = 0;
            }else if (memberCodeList.size() == 1 && !StringUtils.equals(memberCodeList.get(0), btgka1004MemberDto.getMemberCd())){
                guardDelFlg = 0;
            }
        }else {
            stuDelFlg = 0;
            guardDelFlg = 0;
        }
        // 2020/12/8 huangxinliang modify end
        if (guardMstUsrEntity == null) {
            guardId = "p" + (maxIdOfGuard + 1);
            //「保護者」取得できない場合、
            //保護者データを作成する。
            //[DBセット]シートのDBセット名[ユーザ基本マスタ登録]
            mstUsrEntity.setId(null);
            mstUsrEntity.setUsrId(guardId);
            // 2021/3/15 huangxinliang modify start
            mstUsrEntity.setUsrPassword(null);
            // 2021/3/15 huangxinliang modify end
            mstUsrEntity.setFstLoginFlg("0");
            mstUsrEntity.setUsrNm(Optional.ofNullable(btgka1004MemberDto.getGuardFlnmNm()).orElse("") + " " + Optional.ofNullable(btgka1004MemberDto.getGuardFlnmLnm()).orElse(""));
            mstUsrEntity.setRoleDiv("3");
            mstUsrEntity.setOrgId(orgPrefix + btgka1004MemberDto.getOrgId());
            mstUsrEntity.setPwUpFlg("0");
            mstUsrEntity.setUsrSts("1");
            mstUsrEntity.setSpecAuthFlg("0");
            mstUsrEntity.setAfterUsrId(" ");
            mstUsrEntity.setErrorCount(0);
            mstUsrEntity.setLockFlg("0");
            mstUsrEntity.setGidpk(btgka1004MemberDto.getGuardGidpk());
            mstUsrEntity.setGidFlg("1");
            mstUsrEntity.setSystemKbn("2");
            mstUsrEntity.setOrgCommKey(null);
            mstUsrEntity.setGidRuleFlg("0");
            mstUsrEntity.setManaRuleFlg("0");
            mstUsrEntity.setPerlInfoRuleFlg("0");
            mstUsrEntity.setSafModifyFlg("0");
            mstUsrEntity.setMgrModifyFlg("0");
            //所属組織フラグ
            mstUsrEntity.setOwnerOrgFlg("1");
            mstUsrEntity.setCretDatime(DateUtils.getSysTimestamp());
            mstUsrEntity.setCretUsrId("system");
            mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
            mstUsrEntity.setUpdUsrId("system");
            mstUsrEntity.setDelFlg(guardDelFlg);
            try {
                mstUsrDao.insert(mstUsrEntity);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("保護者データを作成する/[DBセット]シートのDBセット名[ユーザ基本マスタ登録]");
                throw new RRException("保護者データを作成する/[DBセット]シートのDBセット名[ユーザ基本マスタ登録]");
            }
            // 2020/12/8 huangxinliang modify start
            insertMstGuard(guardId, guardDelFlg);
            // 2020/12/8 huangxinliang modify end
            insertSysUsrRole(mstUsrEntity.getUsrId(), 3);
            maxIdOfGuard++;
        } else {
            guardId = guardMstUsrEntity.getUsrId();
            //「保護者」取得できる場合、
            //保護者データを更新する。
            //[DBセット]シートのDBセット名[ユーザ基本マスタ更新（生徒と保護者）]
            guardMstUsrEntity.setUsrNm(Optional.ofNullable(btgka1004MemberDto.getGuardFlnmNm()).orElse("") + " " + Optional.ofNullable(btgka1004MemberDto.getGuardFlnmLnm()).orElse(""));
            guardMstUsrEntity.setOrgId(orgPrefix + btgka1004MemberDto.getOrgId());
            guardMstUsrEntity.setGidpk(btgka1004MemberDto.getGuardGidpk());
            guardMstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
            guardMstUsrEntity.setUpdUsrId("system");
            // 2021/08/16 manamiru1-689 cuikl add start
            Set<String> set = new HashSet<>();
            set.add("35");
            set.add("36");
            boolean updtDelFlg = true;
            if (StringUtils.equals("31",btgka1004MemberDto.getReplyContentDiv()) || (!StringUtils.equals("1",btgka1004MemberDto.getReplyCancelFlag()) && set.contains(btgka1004MemberDto.getReplyContentDiv()))){
                /* 2021/08/27 manamiru1-689 cuikl edit start */
                if (studentDtos.size() == 0){
                    guardDelFlg = 0;
                }else {
                    updtDelFlg = false;
                }
                /* 2021/08/27 manamiru1-689 cuikl edit end */
            }
            if(updtDelFlg) {
                // 2020/12/8 huangxinliang modify start
                guardMstUsrEntity.setDelFlg(guardDelFlg);
                // 2020/12/8 huangxinliang modify end
            }
            // 2021/08/16 manamiru1-689 cuikl add end
            try {
                mstUsrDao.updateById(guardMstUsrEntity);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("保護者データを更新する/[DBセット]シートのDBセット名[ユーザ基本マスタ更新（生徒と保護者）]");
                throw new RRException("保護者データを更新する/[DBセット]シートのDBセット名[ユーザ基本マスタ更新（生徒と保護者）]");
            }
            //[DBセット]シートのDBセット名[保護者基本マスタ更新]
            MstGuardEntity mstGuardEntityOld = mstGuardDao.selectOne(new QueryWrapper<MstGuardEntity>().eq("guard_id", guardMstUsrEntity.getUsrId()));
            if (mstGuardEntityOld != null) {
                //[DBセット]シートのDBセット名[保護者基本マスタ更新]
                mstGuardEntityOld.setGuardId(guardMstUsrEntity.getUsrId());
                mstGuardEntityOld.setMailad(btgka1004MemberDto.getMailad());
                mstGuardEntityOld.setFlnmNm(Optional.ofNullable(btgka1004MemberDto.getGuardFlnmNm()).orElse(" "));
                mstGuardEntityOld.setFlnmLnm(Optional.ofNullable(btgka1004MemberDto.getGuardFlnmLnm()).orElse(" "));
                mstGuardEntityOld.setFlnmKnNm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Optional.ofNullable(btgka1004MemberDto.getGuardFlnmKnNm()).orElse(" ")));
                mstGuardEntityOld.setFlnmKnLnm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Optional.ofNullable(btgka1004MemberDto.getGuardFlnmKnLnm()).orElse(" ")));
                if (StringUtils.isNotBlank(btgka1004MemberDto.getPostCd())){
                    mstGuardEntityOld.setPostcdMnum(btgka1004MemberDto.getPostCd().substring(0, 3));
                    mstGuardEntityOld.setPostcdBnum(btgka1004MemberDto.getPostCd().substring(3));
                }else {
                    mstGuardEntityOld.setPostcdMnum(null);
                    mstGuardEntityOld.setPostcdBnum(null);
                }
                String prefecture = mapOfPrefectures.get().get(btgka1004MemberDto.getPrefecturesCd());
                // 2021/3/15 huangxinliang modify start
                mstGuardEntityOld.setAdr1(Optional.ofNullable(prefecture).orElse("") + Optional.ofNullable(btgka1004MemberDto.getCity()).orElse(""));
                mstGuardEntityOld.setAdr2(btgka1004MemberDto.getAddress());
                mstGuardEntityOld.setTelnum(btgka1004MemberDto.getTelnum());
                mstGuardEntityOld.setUrgTelnum(Optional.ofNullable(btgka1004MemberDto.getUrgTelnum()).orElse(""));
                // 2021/3/15 huangxinliang modify end
                // 2020/12/7 huangxinliang modify start
                mstGuardEntityOld.setReltnspDiv("5");
                // 2020/12/7 huangxinliang modify end
                mstGuardEntityOld.setUpdDatime(DateUtils.getSysTimestamp());
                mstGuardEntityOld.setUpdUsrId("system");
                // 2020/12/8 huangxinliang modify start
                mstGuardEntityOld.setDelFlg(guardDelFlg);
                // 2020/12/8 huangxinliang modify end
                try {
                    mstGuardDao.update(new MstGuardEntity(), new UpdateWrapper<MstGuardEntity>()
                            .set("guard_id", mstGuardEntityOld.getGuardId())
                            .set("mailad", mstGuardEntityOld.getMailad())
                            .set("flnm_nm", mstGuardEntityOld.getFlnmNm())
                            .set("flnm_lnm", mstGuardEntityOld.getFlnmLnm())
                            .set("flnm_kn_nm", mstGuardEntityOld.getFlnmKnNm())
                            .set("flnm_kn_lnm", mstGuardEntityOld.getFlnmKnLnm())
                            .set("postcd_mnum", mstGuardEntityOld.getPostcdMnum())
                            .set("postcd_bnum", mstGuardEntityOld.getPostcdBnum())
                            .set("adr1", mstGuardEntityOld.getAdr1())
                            .set("adr2", mstGuardEntityOld.getAdr2())
                            .set("telnum", mstGuardEntityOld.getTelnum())
                            .set("urg_telnum", mstGuardEntityOld.getUrgTelnum())
                            .set("reltnsp_div", mstGuardEntityOld.getReltnspDiv())
                            .set("upd_datime", mstGuardEntityOld.getUpdDatime())
                            .set("upd_usr_id", mstGuardEntityOld.getUpdUsrId())
                            .eq("id", mstGuardEntityOld.getId()));
                    /* 2021/08/16 manamiru1-689 cuikl add start */
                    if(updtDelFlg) {
                        mstGuardDao.update(new MstGuardEntity(), new UpdateWrapper<MstGuardEntity>()
                        .set("del_flg", mstGuardEntityOld.getDelFlg())
                        .eq("id", mstGuardEntityOld.getId()));
                    }
                    /* 2021/08/16 manamiru1-689 cuikl add end */
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.error("保護者データを更新する/[DBセット]シートのDBセット名[保護者基本マスタ更新]");
                    throw new RRException("保護者データを更新する/[DBセット]シートのDBセット名[保護者基本マスタ更新]");
                }
            } else {
                // 2020/12/8 huangxinliang modify start
                insertMstGuard(guardMstUsrEntity.getUsrId(), guardDelFlg);
                // 2020/12/8 huangxinliang modify end
            }
        }
        if (studentDtos.size() == 0) {
            //上記「生徒」取得できない場合
            //生徒データを作成する、
            //[DBセット]シートのDBセット名[ユーザ基本マスタ登録]
            mstUsrEntity.setId(null);
            mstUsrEntity.setUsrId("s" + (maxIdOfStu + 1));
            // 2021/3/15 huangxinliang modify start
            mstUsrEntity.setUsrPassword(null);
            // 2021/3/15 huangxinliang modify end
            mstUsrEntity.setFstLoginFlg("0");
            mstUsrEntity.setUsrNm(btgka1004MemberDto.getStuFlnmNm() + " " + Optional.ofNullable(btgka1004MemberDto.getStuFlnmLnm()).orElse(""));
            mstUsrEntity.setRoleDiv("4");
            mstUsrEntity.setOrgId(orgPrefix + btgka1004MemberDto.getOrgId());
            mstUsrEntity.setPwUpFlg("0");
            mstUsrEntity.setUsrSts("1");
            mstUsrEntity.setSpecAuthFlg("0");
            mstUsrEntity.setAfterUsrId(" ");
            mstUsrEntity.setErrorCount(0);
            mstUsrEntity.setLockFlg("0");
            mstUsrEntity.setGidpk(btgka1004MemberDto.getStuGidpk());
            mstUsrEntity.setGidFlg("1");
            mstUsrEntity.setSystemKbn("2");
            mstUsrEntity.setOrgCommKey(null);
            mstUsrEntity.setGidRuleFlg("0");
            mstUsrEntity.setManaRuleFlg("0");
            mstUsrEntity.setPerlInfoRuleFlg("0");
            mstUsrEntity.setSafModifyFlg("0");
            mstUsrEntity.setMgrModifyFlg("0");
            //所属組織フラグ
            mstUsrEntity.setOwnerOrgFlg("1");
            mstUsrEntity.setCretDatime(DateUtils.getSysTimestamp());
            mstUsrEntity.setCretUsrId("system");
            mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
            mstUsrEntity.setUpdUsrId("system");
            mstUsrEntity.setDelFlg(stuDelFlg);
            try {
                mstUsrDao.insert(mstUsrEntity);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("生徒データを作成する/[DBセット]シートのDBセット名[ユーザ基本マスタ登録]");
                if (guardMstUsrEntity != null) {
                    maxIdOfGuard--;
                }
                throw new RRException("生徒データを作成する/[DBセット]シートのDBセット名[ユーザ基本マスタ登録]");
            }
            try {
                insertMstStu(guardId, stuDelFlg);
                insertSysUsrRole(mstUsrEntity.getUsrId(), 4);
            } catch (Exception e) {
                if (guardMstUsrEntity != null) {
                    maxIdOfGuard--;
                }
                throw e;
            }
            maxIdOfStu++;
        } else if (StringUtils.equals(studentDtos.get(0).getMemberCd(), btgka1004MemberDto.getMemberCd())){
            MstUsrEntity stuMstUsrEntity = studentDtos.get(0);
            //上記「生徒」取得できる場合
            //生徒データを更新する
            //[DBセット]シートのDBセット名[ユーザ基本マスタ更新（生徒と保護者）]
            stuMstUsrEntity.setUsrNm(btgka1004MemberDto.getStuFlnmNm() + " " + Optional.ofNullable(btgka1004MemberDto.getStuFlnmLnm()).orElse(""));
            stuMstUsrEntity.setOrgId(orgPrefix + btgka1004MemberDto.getOrgId());
            stuMstUsrEntity.setGidpk(btgka1004MemberDto.getStuGidpk());
            stuMstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
            stuMstUsrEntity.setUpdUsrId("system");
            //NWT　楊　2021/06/18　MANAMIRU1-689　Edit　Start
            Set<String> set = new HashSet<>();
            set.add("35");
            set.add("36");
            boolean updtDelFlg = true;
            if (StringUtils.equals("31",btgka1004MemberDto.getReplyContentDiv()) || (!StringUtils.equals("1",btgka1004MemberDto.getReplyCancelFlag()) && set.contains(btgka1004MemberDto.getReplyContentDiv()))){
                updtDelFlg = false;
            }
            if (updtDelFlg){
                stuMstUsrEntity.setDelFlg(stuDelFlg);
            }
            //NWT　楊　2021/06/18　MANAMIRU1-689　Edit　End
            try {
                mstUsrDao.updateById(stuMstUsrEntity);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("生徒データを更新する/[DBセット]シートのDBセット名[ユーザ基本マスタ更新（生徒と保護者）]");
                if (guardMstUsrEntity != null) {
                    maxIdOfGuard--;
                }
                throw new RRException("生徒データを更新する/[DBセット]シートのDBセット名[ユーザ基本マスタ更新（生徒と保護者）]");
            }
            //[DBセット]シートのDBセット名[生徒基本マスタ更新]
            MstStuEntity mstStuEntityOld = mstStuDao.selectOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuMstUsrEntity.getUsrId()));
            if (mstStuEntityOld != null) {
                mstStuEntityOld.setStuId(stuMstUsrEntity.getUsrId());
                mstStuEntityOld.setSch(btgka1004MemberDto.getSch());
                mstStuEntityOld.setGuardId(guardId);
                mstStuEntityOld.setFlnmNm(btgka1004MemberDto.getStuFlnmNm());
                mstStuEntityOld.setFlnmLnm(Optional.ofNullable(btgka1004MemberDto.getStuFlnmLnm()).orElse(" "));
                mstStuEntityOld.setFlnmKnNm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Optional.ofNullable(btgka1004MemberDto.getStuFlnmKnNm()).orElse(" ")));
                mstStuEntityOld.setFlnmKnLnm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Optional.ofNullable(btgka1004MemberDto.getStuFlnmKnLnm()).orElse(" ")));
                String gender = mapOfGenderDiv.get().get(btgka1004MemberDto.getGendrDiv());
                if (gender != null) {
                    mstStuEntityOld.setGendrDiv(gender);
                } else {
                    mstStuEntityOld.setGendrDiv(mapOfGenderDiv.get().get(null));
                }
                mstStuEntityOld.setBirthd(btgka1004MemberDto.getBirthd());
                String schyDiv = mapOfSchyDiv.get().get(btgka1004MemberDto.getSchyDiv());
                if (schyDiv != null) {
                    mstStuEntityOld.setSchyDiv(schyDiv);
                } else {
                    mstStuEntityOld.setSchyDiv(btgka1004MemberDto.getSchyDiv());
                }
                mstStuEntityOld.setMemberCd(btgka1004MemberDto.getMemberCd());
                mstStuEntityOld.setCourseId(btgka1004MemberDto.getCourse());
                mstStuEntityOld.setAdmissionDate(btgka1004MemberDto.getRegisterDate() == null ? null : DateUtils.toTimestamp(btgka1004MemberDto.getRegisterDate()));
                mstStuEntityOld.setUpdDatime(DateUtils.getSysTimestamp());
                mstStuEntityOld.setUpdUsrId("system");
                mstStuEntityOld.setDelFlg(stuDelFlg);
                try {
                    mstStuDao.update(new MstStuEntity(), new UpdateWrapper<MstStuEntity>()
                            .set("stu_id", mstStuEntityOld.getStuId())
                            .set("sch", mstStuEntityOld.getSch())
                            .set("guard_id", mstStuEntityOld.getGuardId())
                            .set("flnm_nm", mstStuEntityOld.getFlnmNm())
                            .set("flnm_lnm", mstStuEntityOld.getFlnmLnm())
                            .set("flnm_kn_nm", mstStuEntityOld.getFlnmKnNm())
                            .set("flnm_kn_lnm", mstStuEntityOld.getFlnmKnLnm())
                            .set("gendr_div", mstStuEntityOld.getGendrDiv())
                            .set("birthd", mstStuEntityOld.getBirthd())
                            .set("schy_div", mstStuEntityOld.getSchyDiv())
                            .set("member_cd", mstStuEntityOld.getMemberCd())
                            .set("course_id", mstStuEntityOld.getCourseId())
                            .set("admission_date", mstStuEntityOld.getAdmissionDate())
                            .set("upd_datime", mstStuEntityOld.getUpdDatime())
                            .set("upd_usr_id", mstStuEntityOld.getUpdUsrId())
                            .eq("id", mstStuEntityOld.getId()));
                    //NWT　楊　2021/06/18　MANAMIRU1-689　Edit　Start
                    if (updtDelFlg){
                        mstStuDao.update(new MstStuEntity(), new UpdateWrapper<MstStuEntity>()
                                .set("del_flg", mstStuEntityOld.getDelFlg())
                                .eq("id", mstStuEntityOld.getId()));
                    }
                    //NWT　楊　2021/06/18　MANAMIRU1-689　Edit　End
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.error("生徒データを更新する/[DBセット]シートのDBセット名[生徒基本マスタ更新]");
                    if (guardMstUsrEntity != null) {
                        maxIdOfGuard--;
                    }
                    throw new RRException("生徒データを更新する/[DBセット]シートのDBセット名[生徒基本マスタ更新]");
                }
            } else {
                try {
                    insertMstStu(guardId, stuDelFlg);
                } catch (Exception e) {
                    if (guardMstUsrEntity != null) {
                        maxIdOfGuard--;
                    }
                    throw e;
                }
            }
        }

        //全ファイルのデータLOOP処理完了後、採番マスタに最大MAXIDを更新する
        MstNumassEntity updateMst = new MstNumassEntity();
        //更新日時
        updateMst.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        updateMst.setUpdUsrId("system");
        //maxId
        setManagerMaxId(updateMst, maxIdOfManager);
        //maxId
        setMentorMaxId(updateMst, maxIdOfMentor);
        //maxId
        setGuardMaxId(updateMst, maxIdOfGuard);
        //maxId
        setStuMaxId(updateMst, maxIdOfStu);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public void saveOrUpdateManaTeacherData(BTGKA1004ManaTeacherDto btgka1004ManaTeacherDto, String loginId) {
        //MaxId
        Map<String, Integer> maxId = getMaxId();

        //採番マスタ
        MstNumassEntity mstNumassEntity = new MstNumassEntity();
        //ブランドコード
        mstNumassEntity.setBrandCd("");
        //作成日時
        mstNumassEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        mstNumassEntity.setCretUsrId("system");
        //更新日時
        mstNumassEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        mstNumassEntity.setUpdUsrId("system");
        //del_flg
        mstNumassEntity.setDelFlg(0);

        Integer managerMaxId = getManagerMaxId(maxId, mstNumassEntity);

        //管理者基本マスタのデータ存在チェック
        List<String> mgrIds = btgka1004Dao.teacherCheck(btgka1004ManaTeacherDto.getMgrCd(),
                btgka1004ManaTeacherDto.getManagerGidPk());

        if (mgrIds.size() == 0){
            //上記取得できない場合、新規作成を行う。

            managerMaxId++;

            String usrId = "a" + managerMaxId;
            //ユーザ基本マスタ登録
            MstUsrEntity mstUsrEntity = new MstUsrEntity();
            //ユーザID
            mstUsrEntity.setUsrId(usrId);
            //ユーザログインPW
            mstUsrEntity.setUsrPassword(null);
            //初回登録フラグ
            mstUsrEntity.setFstLoginFlg("0");
            //ユーザ名
            mstUsrEntity.setUsrNm(btgka1004ManaTeacherDto.getMgrFlnmNm() + " " + Optional.ofNullable(btgka1004ManaTeacherDto.getMgrFlnmLnm()).orElse(""));
            //ロール区分
            mstUsrEntity.setRoleDiv("1");
            //PW更新フラグ
            mstUsrEntity.setPwUpFlg("0");
            //ユーザステータス
            mstUsrEntity.setUsrSts("1");
            //特殊権限フラグ
            mstUsrEntity.setSpecAuthFlg("0");
            //変更後ユーザーID
            mstUsrEntity.setAfterUsrId(" ");
            //エラー回数
            mstUsrEntity.setErrorCount(0);
            //ロックフラグ
            mstUsrEntity.setLockFlg("0");
            //学研IDプライマリキー
            mstUsrEntity.setGidpk(btgka1004ManaTeacherDto.getManagerGidPk());
            //GIDフラグ
            mstUsrEntity.setGidFlg("1");
            //他システム区分
            mstUsrEntity.setSystemKbn("2");
            //GakkenID規約フラグ
            mstUsrEntity.setGidRuleFlg("0");
            //マナミル規約フラグ
            mstUsrEntity.setManaRuleFlg("0");
            //個人情報保護規約フラグ
            mstUsrEntity.setPerlInfoRuleFlg("0");
            //自分修正可否フラグ
            mstUsrEntity.setSafModifyFlg("0");
            //管理者修正可否フラグ
            mstUsrEntity.setMgrModifyFlg("0");
            //所属組織フラグ
            mstUsrEntity.setOwnerOrgFlg("1");
            //作成日時
            mstUsrEntity.setCretDatime(DateUtils.getSysTimestamp());
            // 作成ユーザＩＤ
            mstUsrEntity.setCretUsrId(loginId);
            // 更新日時
            mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
            // 更新ユーザＩＤ
            mstUsrEntity.setUpdUsrId(loginId);
            // 削除フラグ
            mstUsrEntity.setDelFlg(0);
            try {
                mstUsrDao.insert(mstUsrEntity);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("ユーザ基本マスタ登録に失敗する");
                throw new RRException("ユーザ基本マスタ登録に失敗する");
            }
            //管理者基本マスタ登録
            MstManagerEntity mstManagerEntity = new MstManagerEntity();
            //管理者ID
            mstManagerEntity.setMgrId(usrId);
            // 姓名_姓
            mstManagerEntity.setFlnmNm(btgka1004ManaTeacherDto.getMgrFlnmNm());
            // 姓名_名
            mstManagerEntity.setFlnmLnm(Optional.ofNullable(btgka1004ManaTeacherDto.getMgrFlnmLnm()).orElse(" "));
            // 姓名_カナ姓
            mstManagerEntity.setFlnmKnNm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Optional.ofNullable(btgka1004ManaTeacherDto.getMgrFlnmKnNm()).orElse(" ")));
            // 姓名_カナ名
            mstManagerEntity.setFlnmKnLnm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Optional.ofNullable(btgka1004ManaTeacherDto.getMgrFlnmKnLnm()).orElse(" ")));
            // 指導者管理コード
            mstManagerEntity.setTchCd(btgka1004ManaTeacherDto.getMgrCd());
            // 作成日時
            mstManagerEntity.setCretDatime(DateUtils.getSysTimestamp());
            // 作成ユーザＩＤ
            mstManagerEntity.setCretUsrId(loginId);
            // 更新日時
            mstManagerEntity.setUpdDatime(DateUtils.getSysTimestamp());
            // 更新ユーザＩＤ
            mstManagerEntity.setUpdUsrId(loginId);
            // 削除フラグ
            mstManagerEntity.setDelFlg(0);
            try {
                mstManagerDao.insert(mstManagerEntity);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("管理者基本マスタ登録に失敗する");
                throw new RRException("管理者基本マスタ登録に失敗する");
            }
            //[DBセット]シートのDBセット名[ユーザーキャラ登録]
            insertSysUsrRole(mstUsrEntity.getUsrId(), 1);
        }else {
            for (String mgrId : mgrIds) {
                //管理者基本マスタ更新
                MstManagerEntity mstManagerEntity = new MstManagerEntity();
                // 姓名_姓
                mstManagerEntity.setFlnmNm(btgka1004ManaTeacherDto.getMgrFlnmNm());
                // 姓名_名
                mstManagerEntity.setFlnmLnm(Optional.ofNullable(btgka1004ManaTeacherDto.getMgrFlnmLnm()).orElse(" "));
                // 姓名_カナ姓
                mstManagerEntity.setFlnmKnNm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Optional.ofNullable(btgka1004ManaTeacherDto.getMgrFlnmKnNm()).orElse(" ")));
                // 姓名_カナ名
                mstManagerEntity.setFlnmKnLnm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Optional.ofNullable(btgka1004ManaTeacherDto.getMgrFlnmKnLnm()).orElse(" ")));
                // 電話番号
                mstManagerEntity.setTelnum(null);
                // 指導者管理コード
                mstManagerEntity.setTchCd(btgka1004ManaTeacherDto.getMgrCd());
                // 更新日時
                mstManagerEntity.setUpdDatime(DateUtils.getSysTimestamp());
                // 更新ユーザＩＤ
                mstManagerEntity.setUpdUsrId(loginId);
                // 削除フラグ
                mstManagerEntity.setDelFlg(0);
                try {
                    mstManagerDao.update(mstManagerEntity, new QueryWrapper<MstManagerEntity>().eq("mgr_id", mgrId));
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.error("管理者基本マスタ更新に失敗する");
                    throw new RRException("管理者基本マスタ更新に失敗する");
                }
                //ユーザ基本マスタ更新（指導者）
                MstUsrEntity mstUsrEntity = new MstUsrEntity();
                //ユーザ名
                mstUsrEntity.setUsrNm(btgka1004ManaTeacherDto.getMgrFlnmNm() + " " + Optional.ofNullable(btgka1004ManaTeacherDto.getMgrFlnmLnm()).orElse(""));
                // 更新日時
                mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
                // 更新ユーザＩＤ
                mstUsrEntity.setUpdUsrId(loginId);
                // 削除フラグ
                mstUsrEntity.setDelFlg(0);
                try {
                    mstUsrDao.update(mstUsrEntity, new QueryWrapper<MstUsrEntity>().eq("usr_id", mgrId));
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.error("ユーザ基本マスタ更新（指導者）に失敗する");
                    throw new RRException("ユーザ基本マスタ更新（指導者）に失敗する");
                }
            }
        }
        //全ファイルのデータLOOP処理完了後、採番マスタに最大MAXIDを更新する
        MstNumassEntity updateMst = new MstNumassEntity();
        //更新日時
        updateMst.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        updateMst.setUpdUsrId("system");
        //maxId
        setManagerMaxId(updateMst, managerMaxId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public void updateManaTidOrgData(BTGKA1004ManaTidOrgDto btgka1004ManaTidOrgDto, MstUsrEntity oldUsrEntity, String loginId, String orgPrefix) {
        //ユーザ基本マスタ更新（指導者と教室関係更新）
        // 組織ID
        oldUsrEntity.setOrgId(orgPrefix + btgka1004ManaTidOrgDto.getOrgCd());
        // 更新日時
        oldUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ
        oldUsrEntity.setUpdUsrId(loginId);
        try {
            mstUsrDao.updateById(oldUsrEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("ユーザ基本マスタ更新（指導者と教室関係更新）に失敗する");
            throw new RRException("ユーザ基本マスタ更新（指導者と教室関係更新）に失敗する");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public void deleteManaTidOrgData(MstUsrEntity oldUsrEntity, String loginId) {
        //ユーザ基本マスタ更新（指導者と教室関係更新）
        oldUsrEntity.setDelFlg(1);
        // 更新日時
        oldUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ
        oldUsrEntity.setUpdUsrId(loginId);
        try {
            mstUsrDao.updateById(oldUsrEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("ユーザ基本マスタ更新（指導者と教室関係削除）に失敗する");
            throw new RRException("ユーザ基本マスタ更新（指導者と教室関係削除）に失敗する");
        }
        MstManagerEntity mstManagerEntity = new MstManagerEntity();
        mstManagerEntity.setDelFlg(1);
        // 更新日時
        mstManagerEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ
        mstManagerEntity.setUpdUsrId(loginId);
        try {
            mstManagerDao.update(mstManagerEntity, new UpdateWrapper<MstManagerEntity>().eq("mgr_id", oldUsrEntity.getUsrId()));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("ユーザ基本マスタ更新（指導者と教室関係削除）に失敗する");
            throw new RRException("ユーザ基本マスタ更新（指導者と教室関係削除）に失敗する");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public void saveManaTidOrgData(BTGKA1004ManaTidOrgDto btgka1004ManaTidOrgDto, BTGKA1004UsrDto btgka1004UsrDto, String loginId, String orgPrefix) {
        //MaxId
        Map<String, Integer> maxId = getMaxId();

        //採番マスタ
        MstNumassEntity mstNumassEntity = new MstNumassEntity();
        //ブランドコード
        mstNumassEntity.setBrandCd("");
        //作成日時
        mstNumassEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        mstNumassEntity.setCretUsrId("system");
        //更新日時
        mstNumassEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        mstNumassEntity.setUpdUsrId("system");
        //del_flg
        mstNumassEntity.setDelFlg(0);

        Integer managerMaxId = getManagerMaxId(maxId, mstNumassEntity);

        managerMaxId++;

        String mgrId = "a" + managerMaxId;

        do {
            //ユーザ基本マスタ登録（指導者と教室関係登録）
            MstUsrEntity mstUsrEntity = new MstUsrEntity();
            //ユーザID
            mstUsrEntity.setUsrId(mgrId);
            // ユーザログインPW
            mstUsrEntity.setUsrPassword(null);
            // 初回登録フラグ
            mstUsrEntity.setFstLoginFlg(btgka1004UsrDto.getFstLoginFlg());
            // ユーザ名
            mstUsrEntity.setUsrNm(btgka1004UsrDto.getUsrNm());
            // ロール区分
            mstUsrEntity.setRoleDiv(btgka1004UsrDto.getRoleDiv());
            // 組織ID
            mstUsrEntity.setOrgId(orgPrefix + btgka1004ManaTidOrgDto.getOrgCd());
            // PW更新フラグ
            mstUsrEntity.setPwUpFlg(btgka1004UsrDto.getPwUpFlg());
            // ユーザステータス
            mstUsrEntity.setUsrSts(btgka1004UsrDto.getUsrSts());
            // 特殊権限フラグ
            mstUsrEntity.setSpecAuthFlg(btgka1004UsrDto.getSpecAuthFlg());
            // 変更後ユーザーID
            mstUsrEntity.setAfterUsrId(btgka1004UsrDto.getAfterUsrId());
            // エラー回数
            mstUsrEntity.setErrorCount(btgka1004UsrDto.getErrorCount());
            // ロックフラグ
            mstUsrEntity.setLockFlg(btgka1004UsrDto.getLockFlg());
            // 学研IDプライマリキー
            mstUsrEntity.setGidpk(btgka1004UsrDto.getGidpk());
            // GIDフラグ
            mstUsrEntity.setGidFlg(btgka1004UsrDto.getGidFlg());
            // 他システム区分
            mstUsrEntity.setSystemKbn(btgka1004UsrDto.getSystemKbn());
            // GakkenID規約フラグ
            mstUsrEntity.setGidRuleFlg(btgka1004UsrDto.getGidRuleFlg());
            // マナミル規約フラグ
            mstUsrEntity.setManaRuleFlg(btgka1004UsrDto.getManaRuleFlg());
            // 個人情報保護規約フラグ
            mstUsrEntity.setPerlInfoRuleFlg(btgka1004UsrDto.getPerlInfoRuleFlg());
            //所属組織フラグ
            mstUsrEntity.setOwnerOrgFlg(btgka1004UsrDto.getOwnerOrgFlg());
            //自分修正可否フラグ
            mstUsrEntity.setSafModifyFlg(btgka1004UsrDto.getSafModifyFlg());
            //管理者修正可否フラグ
            mstUsrEntity.setMgrModifyFlg(btgka1004UsrDto.getMgrModifyFlg());
            // 作成日時
            mstUsrEntity.setCretDatime(DateUtils.getSysTimestamp());
            // 作成ユーザＩＤ
            mstUsrEntity.setCretUsrId(loginId);
            // 更新日時
            mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
            // 更新ユーザＩＤ
            mstUsrEntity.setUpdUsrId(loginId);
            // 削除フラグ
            mstUsrEntity.setDelFlg(0);
            try {
                mstUsrDao.insert(mstUsrEntity);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("ユーザ基本マスタ登録（指導者と教室関係登録）に失敗する");
                throw new RRException("ユーザ基本マスタ登録（指導者と教室関係登録）に失敗する");
            }
        }while (false);

        do {
            //管理者基本マスタ登録
            MstManagerEntity mstManagerEntity = new MstManagerEntity();
            //管理者ID
            mstManagerEntity.setMgrId(mgrId);
            // メールアドレス
            mstManagerEntity.setMailad(btgka1004UsrDto.getMailad());
            // 姓名_姓
            mstManagerEntity.setFlnmNm(btgka1004UsrDto.getFlnmNm());
            // 姓名_名
            mstManagerEntity.setFlnmLnm(btgka1004UsrDto.getFlnmLnm());
            // 姓名_カナ姓
            mstManagerEntity.setFlnmKnNm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(btgka1004UsrDto.getFlnmKnNm()));
            // 姓名_カナ名
            mstManagerEntity.setFlnmKnLnm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(btgka1004UsrDto.getFlnmKnLnm()));
            // 電話番号
            mstManagerEntity.setTelnum(btgka1004UsrDto.getTelnum());
            // 指導者管理コード
            mstManagerEntity.setTchCd(btgka1004UsrDto.getTchCd());
            // 作成日時
            mstManagerEntity.setCretDatime(DateUtils.getSysTimestamp());
            // 作成ユーザＩＤ
            mstManagerEntity.setCretUsrId(loginId);
            // 更新日時
            mstManagerEntity.setUpdDatime(DateUtils.getSysTimestamp());
            // 更新ユーザＩＤ
            mstManagerEntity.setUpdUsrId(loginId);
            // 削除フラグ
            mstManagerEntity.setDelFlg(0);
            try {
                mstManagerDao.insert(mstManagerEntity);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("管理者基本マスタ登録に失敗する");
                throw new RRException("管理者基本マスタ登録に失敗する");
            }
        }while (false);

        //[DBセット]シートのDBセット名[ユーザーキャラ登録]
        insertSysUsrRole( mgrId, 1);
        //全ファイルのデータLOOP処理完了後、採番マスタに最大MAXIDを更新する
        MstNumassEntity updateMst = new MstNumassEntity();
        //更新日時
        updateMst.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        updateMst.setUpdUsrId("system");
        //maxId
        setManagerMaxId(updateMst, managerMaxId);
    }

    //組織マスタなどにブランドデータを登録する
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public void saveBrandData(String loginId, String orgPrefix, String upOrgId) {
        //MaxId
        Map<String, Integer> maxId = getMaxId();

        //採番マスタ
        MstNumassEntity mstNumassEntity = new MstNumassEntity();
        //ブランドコード
        mstNumassEntity.setBrandCd("");
        //作成日時
        mstNumassEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        mstNumassEntity.setCretUsrId(loginId);
        //更新日時
        mstNumassEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        mstNumassEntity.setUpdUsrId(loginId);
        //del_flg
        mstNumassEntity.setDelFlg(0);

        getManagerMaxId(maxId, mstNumassEntity);

        maxIdOfManager++;
        //[DBセット]シートのDBセット名[組織マスタ登録（ブランド）]
        MstOrgEntity mstOrgEntity = new MstOrgEntity();
        // 組織ID
        mstOrgEntity.setOrgId(orgPrefix);
        // 上層組織ID
        mstOrgEntity.setUpplevOrgId(upOrgId);
        // 階層
        mstOrgEntity.setLevel(1);
        // 組織名
        mstOrgEntity.setOrgNm(orgPrefix);
        // ブランドコード
        mstOrgEntity.setBrandCd(orgPrefix);
        // 管理フラグ
        mstOrgEntity.setMgrFlg("1");
        // 他システム区分
        mstOrgEntity.setSystemKbn("2");
        //Web申込利用フラグ
        mstOrgEntity.setWebUseFlg("0");
        //所属区分
        mstOrgEntity.setOwnerKbn(null);
        // 作成日時
        mstOrgEntity.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ
        mstOrgEntity.setCretUsrId(loginId);
        // 更新日時
        mstOrgEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ
        mstOrgEntity.setUpdUsrId(loginId);
        // 削除フラグ
        mstOrgEntity.setDelFlg(0);
        try {
            mstOrgDao.insert(mstOrgEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("組織マスタなどにブランドデータを登録する/[DBセット]シートのDBセット名[組織マスタ登録（ブランド）]");
            throw new RRException("組織マスタなどにブランドデータを登録する/[DBセット]シートのDBセット名[組織マスタ登録（ブランド）]");
        }
        //[DBセット]シートのDBセット名[ユーザ基本マスタ登録]
        MstUsrEntity mstUsrEntity = new MstUsrEntity();
        //ユーザID
        mstUsrEntity.setUsrId("a" + maxIdOfManager);
        // ユーザログインPW
        mstUsrEntity.setUsrPassword(ShiroUtils.sha256("Gakken001", mstUsrEntity.getUsrId()));
        // 初回登録フラグ
        mstUsrEntity.setFstLoginFlg("0");
        // ユーザ名
        mstUsrEntity.setUsrNm(orgPrefix);
        // ロール区分
        mstUsrEntity.setRoleDiv("1");
        // 組織ID
        mstUsrEntity.setOrgId(orgPrefix);
        // PW更新フラグ
        mstUsrEntity.setPwUpFlg("0");
        // ユーザステータス
        mstUsrEntity.setUsrSts("1");
        // 特殊権限フラグ
        mstUsrEntity.setSpecAuthFlg("1");
        // 変更後ユーザーID
        mstUsrEntity.setAfterUsrId("GKGCSystemAdmin");
        // エラー回数
        mstUsrEntity.setErrorCount(0);
        // ロックフラグ
        mstUsrEntity.setLockFlg("0");
        // 学研IDプライマリキー
        mstUsrEntity.setGidpk("GKGCSystemAdmin");
        // GIDフラグ
        mstUsrEntity.setGidFlg("1");
        // 他システム区分
        mstUsrEntity.setSystemKbn("2");
        // 組織共用キー
        mstUsrEntity.setOrgCommKey(mstUsrEntity.getOrgId() + "_key");
        // GakkenID規約フラグ
        mstUsrEntity.setGidRuleFlg("0");
        // マナミル規約フラグ
        mstUsrEntity.setManaRuleFlg("0");
        // 個人情報保護規約フラグ
        mstUsrEntity.setPerlInfoRuleFlg("0");
        // 自分修正可否フラグ
        mstUsrEntity.setSafModifyFlg("0");
        // 管理者修正可否フラグ
        mstUsrEntity.setMgrModifyFlg("0");
        //所属組織フラグ
        mstUsrEntity.setOwnerOrgFlg("1");
        // 作成日時
        mstUsrEntity.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ
        mstUsrEntity.setCretUsrId(loginId);
        // 更新日時
        mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ
        mstUsrEntity.setUpdUsrId(loginId);
        // 削除フラグ
        mstUsrEntity.setDelFlg(0);
        try {
            mstUsrDao.insert(mstUsrEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("組織マスタなどにブランドデータを登録する/[DBセット]シートのDBセット名[ユーザ基本マスタ登録]");
            throw new RRException("組織マスタなどにブランドデータを登録する/[DBセット]シートのDBセット名[ユーザ基本マスタ登録]");
        }
        //[DBセット]シートのDBセット名[管理者基本マスタ登録（ブランド）]
        MstManagerEntity mstManagerEntity = new MstManagerEntity();
        //管理者ID
        mstManagerEntity.setMgrId("a" + maxIdOfManager);
        // メールアドレス
        mstManagerEntity.setMailad(null);
        // 姓名_姓
        mstManagerEntity.setFlnmNm(orgPrefix);
        // 姓名_名
        mstManagerEntity.setFlnmLnm(orgPrefix);
        // 姓名_カナ姓
        mstManagerEntity.setFlnmKnNm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(orgPrefix));
        // 姓名_カナ名
        mstManagerEntity.setFlnmKnLnm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(orgPrefix));
        // 電話番号
        mstManagerEntity.setTelnum(null);
        // 指導者管理コード
        mstManagerEntity.setTchCd(orgPrefix);
        // 作成日時
        mstManagerEntity.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ
        mstManagerEntity.setCretUsrId(loginId);
        // 更新日時
        mstManagerEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ
        mstManagerEntity.setUpdUsrId(loginId);
        // 削除フラグ
        mstManagerEntity.setDelFlg(0);
        try {
            mstManagerDao.insert(mstManagerEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("組織マスタなどにブランドデータを登録する/[DBセット]シートのDBセット名[管理者基本マスタ登録（ブランド）]");
            throw new RRException("組織マスタなどにブランドデータを登録する/[DBセット]シートのDBセット名[管理者基本マスタ登録（ブランド）]");
        }
        //[DBセット]シートのDBセット名[ユーザーキャラ登録]
        insertSysUsrRole(mstUsrEntity.getUsrId(), 1);

        //全ファイルのデータLOOP処理完了後、採番マスタに最大MAXIDを更新する
        MstNumassEntity updateMst = new MstNumassEntity();
        //更新日時
        updateMst.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        updateMst.setUpdUsrId(loginId);
        //maxId
        setManagerMaxId(updateMst, maxIdOfManager);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public void saveOrUpadteOrgData(BTGKA1004ManaOrgDto btgka1004ManaOrgDto, MstOrgEntity mstOrgEntity3, String loginId, String orgPrefix, boolean classRoomFlag) {
        MstOrgEntity mstOrgEntity = new MstOrgEntity();
        // 組織名
        mstOrgEntity.setOrgNm(btgka1004ManaOrgDto.getOrgNm());
        if (classRoomFlag){
            // 上層組織ID
            mstOrgEntity.setUpplevOrgId(orgPrefix + btgka1004ManaOrgDto.getUpplevOrgId() +
                    (StringUtils.equals("1", btgka1004ManaOrgDto.getDiv()) ? "01" : "02"));
            // 階層(組織情報．階層コード＋2)
            mstOrgEntity.setLevel(btgka1004ManaOrgDto.getLevel() + 2);
        }else {
            // 上層組織ID
            mstOrgEntity.setUpplevOrgId(orgPrefix + btgka1004ManaOrgDto.getUpplevOrgId());
            // 階層(組織情報．階層コード＋1)
            mstOrgEntity.setLevel(btgka1004ManaOrgDto.getLevel() + 1);
        }
        //所属区分
        mstOrgEntity.setOwnerKbn(btgka1004ManaOrgDto.getDiv());
        //Web申込利用フラグ
        if ("0".equals(btgka1004ManaOrgDto.getWebUseFlg())) {
            mstOrgEntity.setWebUseFlg("1");
        } else {
            mstOrgEntity.setWebUseFlg("0");
        }
        //更新日時
        mstOrgEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ
        mstOrgEntity.setUpdUsrId(loginId);
        // 削除フラグ
        mstOrgEntity.setDelFlg(0);
        if (mstOrgEntity3 == null) {
            //[DBセット]シートのDBセット名[組織マスタ登録]
            //組織ID
            mstOrgEntity.setOrgId(orgPrefix + btgka1004ManaOrgDto.getOrgCd());
            // ブランドコード
            mstOrgEntity.setBrandCd(orgPrefix);
            // 管理フラグ
            mstOrgEntity.setMgrFlg("0");
            // 他システム区分
            mstOrgEntity.setSystemKbn("2");
            // 作成日時
            mstOrgEntity.setCretDatime(DateUtils.getSysTimestamp());
            // 作成ユーザＩＤ
            mstOrgEntity.setCretUsrId(loginId);
            try {
                mstOrgDao.insert(mstOrgEntity);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("[DBセット]シートのDBセット名[組織マスタ登録]");
                throw new RRException("[DBセット]シートのDBセット名[組織マスタ登録]");
            }
        } else {
            //[DBセット]シートのDBセット名[組織マスタ更新]
            try {
                mstOrgDao.update(mstOrgEntity, new QueryWrapper<MstOrgEntity>().eq("org_id", orgPrefix + btgka1004ManaOrgDto.getOrgCd()).eq("system_kbn", "2"));
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("[DBセット]シートのDBセット名[組織マスタ更新]は失敗している");
                throw new RRException("[DBセット]シートのDBセット名[組織マスタ更新]は失敗している");
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public void saveFcOrUpOrgData(BTGKA1004ManaOrgDto btgka1004ManaOrgDto, String loginId, String orgPrefix) {
        // 2020/12/28 huangxinliang modify start
        MstOrgEntity upLevelOrgEntity = mstOrgDao.selectOne(new QueryWrapper<MstOrgEntity>().eq("org_id", orgPrefix + btgka1004ManaOrgDto.getOrgCd()).
                eq("system_kbn", "2").eq("del_flg", 0));
        //[DBセット]シートのDBセット名[組織マスタ登録（ＦＣと直営）]
        MstOrgEntity orgEntity = new MstOrgEntity();
        if (upLevelOrgEntity == null){
            //組織マスタから教室の直近上層組織を取得できない場合
            //組織名
            orgEntity.setOrgNm(" ");
            // 上層組織ID
            orgEntity.setUpplevOrgId(null);
        }else {
            //組織マスタから教室の直近上層組織を取得できる場合
            //組織名
            orgEntity.setOrgNm(upLevelOrgEntity.getOrgNm() + "FC");
            // 上層組織ID
            orgEntity.setUpplevOrgId(upLevelOrgEntity.getOrgId());
        }
        // 2020/12/28 huangxinliang modify end
        // 組織ID
        orgEntity.setOrgId(orgPrefix + btgka1004ManaOrgDto.getOrgCd() + "01");
        // 階層(Web申込教室階層コード＋1)
        orgEntity.setLevel(btgka1004ManaOrgDto.getLevel() + 2);
        // ブランドコード
        orgEntity.setBrandCd(orgPrefix);
        //Web申込利用フラグ
        if ("0".equals(btgka1004ManaOrgDto.getWebUseFlg())) {
            orgEntity.setWebUseFlg("1");
        } else {
            orgEntity.setWebUseFlg("0");
        }
        // 管理フラグ
        orgEntity.setMgrFlg("1");
        // 他システム区分
        orgEntity.setSystemKbn("2");
        //所属区分
        orgEntity.setOwnerKbn(null);
        // 作成日時
        orgEntity.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ
        orgEntity.setCretUsrId(loginId);
        // 更新日時
        orgEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ
        orgEntity.setUpdUsrId(loginId);
        // 削除フラグ
        orgEntity.setDelFlg(0);
        try {
            mstOrgDao.insert(orgEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("[DBセット]シートのDBセット名[組織マスタ登録（ＦＣ）]は失敗している");
            throw new RRException("[DBセット]シートのDBセット名[組織マスタ登録（ＦＣ）]は失敗している");
        }
        // 組織ID
        orgEntity.setOrgId(orgPrefix + btgka1004ManaOrgDto.getOrgCd() + "02");
        // 2020/12/28 huangxinliang modify start
        if (upLevelOrgEntity == null){
            //組織マスタから教室の直近上層組織を取得できない場合
            //組織名
            orgEntity.setOrgNm(" ");
        }else {
            //組織マスタから教室の直近上層組織を取得できる場合
            //組織名
            orgEntity.setOrgNm(upLevelOrgEntity.getOrgNm() + "直営");
        }
        // 2020/12/28 huangxinliang modify end
        try {
            mstOrgDao.insert(orgEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("[DBセット]シートのDBセット名[組織マスタ登録（直営）]は失敗している");
            throw new RRException("[DBセット]シートのDBセット名[組織マスタ登録（直営）]は失敗している");
        }
    }

    /**
     * [DBセット]シートのDBセット名[生徒基本マスタ登録]
     *
     * @param guardId
     */
    private void insertMstStu(String guardId, int stuDelFlg) {
        //[DBセット]シートのDBセット名[生徒基本マスタ登録]
        mstStuEntity.setId(null);
        mstStuEntity.setStuId("s" + (maxIdOfStu + 1));
        mstStuEntity.setSch(btgka1004MemberDto.getSch());
        mstStuEntity.setGuardId(guardId);
        mstStuEntity.setGuard1Id(null);
        mstStuEntity.setGuard2Id(null);
        mstStuEntity.setGuard3Id(null);
        mstStuEntity.setGuard4Id(null);
        mstStuEntity.setFlnmNm(btgka1004MemberDto.getStuFlnmNm());
        mstStuEntity.setFlnmLnm(Optional.ofNullable(btgka1004MemberDto.getStuFlnmLnm()).orElse(" "));
        mstStuEntity.setFlnmKnNm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Optional.ofNullable(btgka1004MemberDto.getStuFlnmKnNm()).orElse(" ")));
        mstStuEntity.setFlnmKnLnm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Optional.ofNullable(btgka1004MemberDto.getStuFlnmKnLnm()).orElse(" ")));
        String gender = mapOfGenderDiv.get().get(btgka1004MemberDto.getGendrDiv());
        if (gender != null) {
            mstStuEntity.setGendrDiv(gender);
        } else {
            mstStuEntity.setGendrDiv(mapOfGenderDiv.get().get(null));
        }
        mstStuEntity.setBirthd(btgka1004MemberDto.getBirthd());
        mstStuEntity.setPhotPath(null);
        String schyDiv = mapOfSchyDiv.get().get(btgka1004MemberDto.getSchyDiv());
        if (schyDiv != null) {
            mstStuEntity.setSchyDiv(schyDiv);
        } else {
            mstStuEntity.setSchyDiv(btgka1004MemberDto.getSchyDiv());
        }
        String key = btgka1004MemberDto.getOrgId() + "-" + btgka1004MemberDto.getMemberCd();
        mstStuEntity.setQrCod(key);
        mstStuEntity.setOriaCd(key);
        mstStuEntity.setEnglishNm(null);
        mstStuEntity.setDayweekDiv(null);
        mstStuEntity.setMemoCont(null);
        mstStuEntity.setResptyNm(null);
        mstStuEntity.setStudyCont(null);
        mstStuEntity.setGoodSubjtDiv(null);
        mstStuEntity.setNogoodSubjtDiv(null);
        mstStuEntity.setHopeJobNm(null);
        mstStuEntity.setHopeUnityNm(null);
        mstStuEntity.setHopeLearnSub(null);
        mstStuEntity.setSpecCont(null);
        mstStuEntity.setMemberCd(btgka1004MemberDto.getMemberCd());
        mstStuEntity.setCourseId(btgka1004MemberDto.getCourse());
        mstStuEntity.setAdmissionDate(btgka1004MemberDto.getRegisterDate() == null ? null : DateUtils.toTimestamp(btgka1004MemberDto.getRegisterDate()));
        mstStuEntity.setCretDatime(DateUtils.getSysTimestamp());
        mstStuEntity.setCretUsrId("system");
        mstStuEntity.setUpdDatime(DateUtils.getSysTimestamp());
        mstStuEntity.setUpdUsrId("system");
        mstStuEntity.setDelFlg(stuDelFlg);
        try {
            mstStuDao.insert(mstStuEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("生徒データを作成する/[DBセット]シートのDBセット名[生徒基本マスタ登録]");
            throw new RRException("生徒データを作成する/[DBセット]シートのDBセット名[生徒基本マスタ登録]");
        }
    }

    /**
     * [DBセット]シートのDBセット名[保護者基本マスタ登録]
     */
    private void insertMstGuard(String usrId, int guardDelFlg) throws RRException {
        //[DBセット]シートのDBセット名[保護者基本マスタ登録]
        mstGuardEntity.setId(null);
        mstGuardEntity.setGuardId(usrId);
        mstGuardEntity.setMailad(btgka1004MemberDto.getMailad());
        mstGuardEntity.setFlnmNm(Optional.ofNullable(btgka1004MemberDto.getGuardFlnmNm()).orElse(" "));
        mstGuardEntity.setFlnmLnm(Optional.ofNullable(btgka1004MemberDto.getGuardFlnmLnm()).orElse(" "));
        mstGuardEntity.setFlnmKnNm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Optional.ofNullable(btgka1004MemberDto.getGuardFlnmKnNm()).orElse(" ")));
        mstGuardEntity.setFlnmKnLnm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Optional.ofNullable(btgka1004MemberDto.getGuardFlnmKnLnm()).orElse(" ")));
        if (StringUtils.isNotBlank(btgka1004MemberDto.getPostCd())){
            mstGuardEntity.setPostcdMnum(btgka1004MemberDto.getPostCd().substring(0, 3));
            mstGuardEntity.setPostcdBnum(btgka1004MemberDto.getPostCd().substring(3));
        }
        String prefecture = mapOfPrefectures.get().get(btgka1004MemberDto.getPrefecturesCd());
        // 2021/3/15 huangxinliang modify start
        mstGuardEntity.setAdr1(Optional.ofNullable(prefecture).orElse("") + Optional.ofNullable(btgka1004MemberDto.getCity()).orElse(""));
        mstGuardEntity.setAdr2(btgka1004MemberDto.getAddress());
        mstGuardEntity.setTelnum(btgka1004MemberDto.getTelnum());
        mstGuardEntity.setUrgTelnum(Optional.ofNullable(btgka1004MemberDto.getUrgTelnum()).orElse(""));
        // 2021/3/15 huangxinliang modify end
        // 2020/12/7 huangxinliang modify start
        mstGuardEntity.setReltnspDiv("5");
        // 2020/12/7 huangxinliang modify end
        mstGuardEntity.setCretDatime(DateUtils.getSysTimestamp());
        mstGuardEntity.setCretUsrId("system");
        mstGuardEntity.setUpdDatime(DateUtils.getSysTimestamp());
        mstGuardEntity.setUpdUsrId("system");
        // 2020/12/8 huangxinliang modify start
        mstGuardEntity.setDelFlg(guardDelFlg);
        // 2020/12/8 huangxinliang modify end
        try {
            mstGuardDao.insert(mstGuardEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("保護者データを作成する/[DBセット]シートのDBセット名[保護者基本マスタ登録]");
            throw new RRException("保護者データを作成する/[DBセット]シートのDBセット名[保護者基本マスタ登録]");
        }
    }

    private void insertSysUsrRole(String usrId, Integer roleDiv) {
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        MstUsrEntity mstUsrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().select("id").eq("usr_id", usrId));
        sysUserRoleEntity.setUserId(Long.valueOf(mstUsrEntity.getId()));
        switch (roleDiv){
            case 1:
                sysUserRoleEntity.setRoleId(5L);
                break;
            case 2:
                sysUserRoleEntity.setRoleId(1L);
                break;
            case 3:
                sysUserRoleEntity.setRoleId(3L);
                break;
            case 4:
                sysUserRoleEntity.setRoleId(2L);
                break;
            default:
                break;
        }
        try {
            sysUserRoleDao.insert(sysUserRoleEntity);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new RRException("ユーザーキャラクター登録に失敗");
        }

    }

    private void loadMaps() {
        //1.3　保護者の住所を編集するために、下記条件でコードマスタ_明細から都道府県名を取得する。
        do {
            if (mapOfPrefectures.get() != null) {
                break;
            } else {
                List<MstCodDEntity> prefecturesList = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>().eq("cod_key", "PREFECTURE_ID").eq("del_flg", 0));
                //上記のファイルパスを取得できない場合、下記メッセージをログに出力する。
//                if (prefecturesList.size() == 0){
//                    return R.error(MessageUtils.getMessage("MSGCOMN0035", "ファイルパス"));
//                }
                Map<String, String> map = new HashMap<>();
                prefecturesList.forEach(prefecturesCodDEntity ->
                        map.put(prefecturesCodDEntity.getCodCd(), prefecturesCodDEntity.getCodValue())
                );
                mapOfPrefectures.set(map);
            }
        } while (false);
        //1.4　学年区分を変換するために、下記条件でコードマスタ_明細から両方の学年区分対応関係データを取得する。
        do {
            if (mapOfSchyDiv.get() != null) {
                break;
            } else {
                List<MstCodDEntity> schyDivList = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>().eq("cod_key", "SCHY_DIV").eq("del_flg", 0));
                //上記のファイルパスを取得できない場合、下記メッセージをログに出力する。
//                if (schyDivList.size() == 0){
//                    return R.error(MessageUtils.getMessage("MSGCOMN0035", "ファイルパス"));
//                }
                Map<String, String> map = new HashMap<>();
                schyDivList.forEach(schyDivCodDEntity ->
                        map.put(schyDivCodDEntity.getCodValue2(), schyDivCodDEntity.getCodCd())
                );
                mapOfSchyDiv.set(map);
            }
        } while (false);
        //1.5　性別区分を変換するために、下記条件でコードマスタ_明細から両方の性別区分対応関係データを取得する。
        do {
            if (mapOfGenderDiv.get() != null) {
                break;
            } else {
                List<MstCodDEntity> genderDivList = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>().eq("cod_key", "GENDR_DIV").eq("del_flg", 0));
                //上記のファイルパスを取得できない場合、下記メッセージをログに出力する。
//                if (genderDivList.size() == 0){
//                    return R.error(MessageUtils.getMessage("MSGCOMN0035", "ファイルパス"));
//                }
                Map<String, String> map = new HashMap<>();
                genderDivList.forEach(genderDivCodDEntity -> {
                            map.put(genderDivCodDEntity.getCodValue2(), genderDivCodDEntity.getCodCd());
                            if (genderDivCodDEntity.getCodValue2() == null){
                                map.put("", genderDivCodDEntity.getCodCd());
                            }
                        });
                mapOfGenderDiv.set(map);
            }
        } while (false);
    }

    private Map<String, Integer> getMaxId() {
        //MaxId
        List<F00004Dto> f00004Dtos = f00004Dao.selectMax(null);
        Map<String, Integer> maxIdMap = new HashMap<>();
        for (F00004Dto dto : f00004Dtos) {
            if (dto.getMax() != null) {
                maxIdMap.put(dto.getRoleDiv().trim(), dto.getMax());
            }
        }
        return maxIdMap;
    }

    private Integer getManagerMaxId(Map<String, Integer> maxIdMap, MstNumassEntity mstNumassEntity) {
        if (maxIdMap.get("1") == null) {
            //ロール区分
            mstNumassEntity.setRoleDiv("1");
            //MAXID
            mstNumassEntity.setMaxId("a1");
            mstNumassDao.insert(mstNumassEntity);
            maxIdOfManager = 0;
        } else {
            maxIdOfManager = maxIdMap.get("1");
        }
        return maxIdOfManager;
    }

    private Integer getMentorMaxId(Map<String, Integer> maxIdMap, MstNumassEntity mstNumassEntity) {
        if (maxIdMap.get("2") == null) {
            //ロール区分
            mstNumassEntity.setRoleDiv("2");
            //MAXID
            mstNumassEntity.setMaxId("m1");
            mstNumassDao.insert(mstNumassEntity);
            maxIdOfMentor = 0;
        } else {
            maxIdOfMentor = maxIdMap.get("2");
        }
        return maxIdOfMentor;
    }

    private Integer getGuardMaxId(Map<String, Integer> maxIdMap, MstNumassEntity mstNumassEntity) {
        if (maxIdMap.get("3") == null) {
            //ロール区分
            mstNumassEntity.setRoleDiv("3");
            //MAXID
            mstNumassEntity.setMaxId("p1");
            mstNumassDao.insert(mstNumassEntity);
            maxIdOfGuard = 0;
        } else {
            maxIdOfGuard = maxIdMap.get("3");
        }
        return maxIdOfGuard;
    }

    private Integer getStuMaxId(Map<String, Integer> maxIdMap, MstNumassEntity mstNumassEntity) {
        if (maxIdMap.get("4") == null) {
            //ロール区分
            mstNumassEntity.setRoleDiv("4");
            //MAXID
            mstNumassEntity.setMaxId("s1");
            mstNumassDao.insert(mstNumassEntity);
            maxIdOfStu = 0;
        } else {
            maxIdOfStu = maxIdMap.get("4");
        }
        return maxIdOfStu;
    }

    //maxId
    private void setManagerMaxId(MstNumassEntity updateMst, Integer maxIdOfManager) {
        updateMst.setMaxId("a" + maxIdOfManager);
        mstNumassDao.update(updateMst, new QueryWrapper<MstNumassEntity>().and(w -> w.eq("role_div", "1").eq("del_flg", 0)));
    }

    //maxId
    private void setMentorMaxId(MstNumassEntity updateMst, Integer maxIdOfMentor) {
        updateMst.setMaxId("m" + maxIdOfMentor);
        mstNumassDao.update(updateMst, new QueryWrapper<MstNumassEntity>().and(w -> w.eq("role_div", "2").eq("del_flg", 0)));
    }

    //maxId
    private void setGuardMaxId(MstNumassEntity updateMst, Integer maxIdOfGuard) {
        updateMst.setMaxId("p" + maxIdOfGuard);
        mstNumassDao.update(updateMst, new QueryWrapper<MstNumassEntity>().and(w -> w.eq("role_div", "3").eq("del_flg", 0)));
    }

    //maxId
    private void setStuMaxId(MstNumassEntity updateMst, Integer maxIdOfStu) {
        updateMst.setMaxId("s" + maxIdOfStu);
        mstNumassDao.update(updateMst, new QueryWrapper<MstNumassEntity>().and(w -> w.eq("role_div", "4").eq("del_flg", 0)));
    }
}
