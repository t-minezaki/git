/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.MstOrgFunListService;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.pop.dao.F00003Dao;
import jp.learningpark.modules.pop.service.F00003Service;
import jp.learningpark.modules.sys.dao.SysRoleDao;
import jp.learningpark.modules.sys.dao.SysUserRoleDao;
import jp.learningpark.modules.sys.entity.SysRoleEntity;
import jp.learningpark.modules.sys.entity.SysUserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>F00003_組織設定・修正画面 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/24 : gong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F00003ServiceImpl implements F00003Service {

    /**
     * 組織Master　DAO
     */
    @Autowired
    private MstOrgDao mstOrgDao;

    /**
     * ユーザ基本マスタ Dao
     */
    @Autowired
    private MstUsrDao mstUsrDao;

    /**
     * 管理者基本マスタ
     */
    @Autowired
    private MstManagerDao mstManagerDao;

    /**
     * 角色 dao
     */
    @Autowired
    SysRoleDao sysRoleDao;

    /**
     * ユーザ角色 Dao
     */
    @Autowired
    SysUserRoleDao sysUserRoleDao;

    /**
     * mstOrgFunListService
     */
    @Autowired
    MstOrgFunListService mstOrgFunListService;

    /**
     * mstOrgFunListDao
     */
    @Autowired
    MstOrgFunListDao mstOrgFunListDao;

    /**
     * commonService
     */
    @Autowired
    CommonService commonService;

    /**
     * f00003Dao
     */
    @Autowired
    F00003Dao f00003Dao;

    /**
     * mstMentorDao
     */
    @Autowired
    MstMentorDao mstMentorDao;

    /**
     * mstGuardDao
     */
    @Autowired
    MstGuardDao mstGuardDao;

    /**
     * mstStuDao
     */
    @Autowired
    MstStuDao mstStuDao;

    /**
     * mstNumassDao
     */
    @Autowired
    MstNumassDao mstNumassDao;

    // 2021/1/4 huangxinliang modify start
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    // 2021/1/4 huangxinliang modify end

    /**
     * @param org       組織ID
     * @param upLevel   前画面から受け取った階層
     * @return  R
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public R submitFn(MstOrgEntity org, Integer upLevel,String upTime) {
        //管理者ID
        String manageId = ShiroUtils.getUserId();
        String brandCd = ShiroUtils.getBrandcd();
        String upLvOrg = org.getUpplevOrgId();
        //編集の場合
        if (org.getId() != null) {
            MstOrgEntity mstOrgEntity = mstOrgDao.selectById(org.getId());
            String nowUpdatime = mstOrgEntity.getUpdDatime().toString();
            if (!nowUpdatime.equals(upTime)){
                return R.error(MessageUtils.getMessage("MSGCOMN0019"));
            }
            //更新日時
            org.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            org.setUpdUsrId(manageId);
            if (org.getUpplevOrgId().equals(mstOrgEntity.getUpplevOrgId())){
                org.setLevel(++upLevel);
                mstOrgDao.updateById(org);
            }else {
                //階層
                org.setLevel(++upLevel);
                mstOrgDao.updateById(org);

                //引渡データ．組織IDより、編集中の組織ID及び編集中組織の下層組織を取得する
                List<OrgAndLowerOrgIdDto> orgList = commonService.getThisAndLowerOrgId(ShiroUtils.getBrandcd(),org.getOrgId());
                //引渡データ．組織IDより、編集中組織に対するアカウントを取得する
                //after_usr_ids
                List<String> mstUsrEntities =  f00003Dao.getUsrs(org.getOrgId());
                List<String> orgIdList = new ArrayList<>();
                for (OrgAndLowerOrgIdDto orgAndLowerOrgIdDto : orgList){
                    orgIdList.add(orgAndLowerOrgIdDto.getOrgId());
                }
                if (mstUsrEntities.size() != 0){
                    List<MstUsrEntity> usrEntityList = f00003Dao.getAllUsrs(mstUsrEntities,orgIdList);
                    //取得した所属組織フラグ＝「0：所属ではない」のアカウントを削除する。　　※論理削除
                    for (MstUsrEntity mstUsrEntity : usrEntityList){
                        String role = mstUsrEntity.getRoleDiv();
                        String usrId = mstUsrEntity.getUsrId();
                        switch (role){
                            case "1 " :
                                MstManagerEntity mstManagerEntity = new MstManagerEntity();
                                mstManagerEntity.setDelFlg(1);
                                mstManagerDao.update(mstManagerEntity,new QueryWrapper<MstManagerEntity>().eq("mgr_id",usrId));break;
                            case "2 " :
                                MstMentorEntity mstMentorEntity = new MstMentorEntity();
                                mstMentorEntity.setDelFlg(1);
                                mstMentorDao.update(mstMentorEntity,new QueryWrapper<MstMentorEntity>().eq("mentor_id",usrId));break;
                            case "3 " :
                                MstGuardEntity mstGuardEntity = new MstGuardEntity();
                                mstGuardEntity.setDelFlg(1);
                                mstGuardDao.update(mstGuardEntity,new QueryWrapper<MstGuardEntity>().eq("guard_id",usrId));break;
                            case "4 " :
                                MstStuEntity mstStuEntity = new MstStuEntity();
                                mstStuEntity.setDelFlg(1);
                                mstStuDao.update(mstStuEntity,new QueryWrapper<MstStuEntity>().eq("stu_id",usrId));break;
                            default:break;
                        }
                        sysUserRoleDao.delete(new QueryWrapper<SysUserRoleEntity>().eq("user_id",mstUsrEntity.getId()));
                        f00003Dao.updateAcount(mstUsrEntity.getUsrId());
                    }
                }
                // 2020/12/9 huangxinliang modify start
                threadPoolTaskExecutor.execute(() -> {
                    for (MstOrgEntity mstOrgEntity1 : orgList){
                        createAccount(mstOrgEntity1);
                    }
                });
                // 2020/12/9 huangxinliang modify end
            }
            return R.ok(MessageUtils.getMessage("MSGCOMN0036", "組織"));
        }
        //新規の場合
        else {
            //該当組織の権限データを取得する
            List<MstOrgFunListEntity> mstOrgFunListEntityList = mstOrgFunListService.list(new QueryWrapper<MstOrgFunListEntity>().select("fun_id", "mgr_flg", "ment_flg").eq("org_id", upLvOrg).eq("del_flg", 0));

            //組織IDの一意チェック行う 2020/11/23 modify yang start--
            int orgCount = mstOrgDao.selectList(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", org.getOrgId()).eq("del_flg", 0))).size();
            int userCount = mstUsrDao.selectList(new QueryWrapper<MstUsrEntity>().and(w -> w.eq("after_usr_id", org.getOrgId()).eq("del_flg", 0))).size();
            //2020/11/23 modify yang end--
            if (orgCount > 0 || userCount > 0) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0062", org.getOrgId(), "別の組織ID"));
            }

            //brandCD
            org.setBrandCd(brandCd);
            //階層
            org.setLevel(++upLevel);
            //作成日時
            org.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            org.setCretUsrId(manageId);
            //更新日時
            org.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            org.setUpdUsrId(manageId);
            //del_flg
            org.setDelFlg(0);
            mstOrgDao.insert(org);
            //ユーザ基本マスタ
            MstUsrEntity mstUsrEntity = new MstUsrEntity();
            //ユーザID
            mstUsrEntity.setUsrId(org.getOrgId() + brandCd);
            //ユーザログインPW
            mstUsrEntity.setUsrPassword(ShiroUtils.sha256(org.getOrgId(), org.getOrgId() + brandCd));
            //初回登録フラグ
            mstUsrEntity.setFstLoginFlg("1");
            //ユーザ名
            mstUsrEntity.setUsrNm(org.getOrgId());
            //ロール区分
            mstUsrEntity.setRoleDiv("1");
            //組織ID
            mstUsrEntity.setOrgId(org.getOrgId());
            //PW更新フラグ
            mstUsrEntity.setPwUpFlg("2");
            //ユーザステータス
            mstUsrEntity.setUsrSts("1");
            //特殊権限フラグ
            mstUsrEntity.setSpecAuthFlg("1");
            //変更後ユーザーID
            mstUsrEntity.setAfterUsrId(org.getOrgId());
            //エラー回数
            mstUsrEntity.setErrorCount(0);
            //hxl modify 2020/11/05
            //GakkenID規約フラグ
            mstUsrEntity.setGidRuleFlg("1");
            //マナミル規約フラグ
            mstUsrEntity.setManaRuleFlg("1");
            //個人情報保護規約フラグ
            mstUsrEntity.setPerlInfoRuleFlg("1");
            //自分修正可否フラグ
            mstUsrEntity.setSafModifyFlg("1");
            //管理者修正可否フラグ
            mstUsrEntity.setMgrModifyFlg("1");
            //所属組織フラグ
            mstUsrEntity.setOwnerOrgFlg("1");
            //ロックフラグ
            mstUsrEntity.setLockFlg("0");
            //作成日時
            mstUsrEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            mstUsrEntity.setCretUsrId(manageId);
            //更新日時
            mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstUsrEntity.setUpdUsrId(manageId);
            //削除フラグ
            mstUsrEntity.setDelFlg(0);
            mstUsrEntity.setOrgCommKey(org.getOrgId() + "_key");
            mstUsrDao.insert(mstUsrEntity);

            SysRoleEntity sysRoleEntity = sysRoleDao.selectOne(new QueryWrapper<SysRoleEntity>().and(w -> w.eq("role_name", "manager")));
            insertSysUserRole(mstUsrEntity, sysRoleEntity);

            //管理者基本マスタ
            MstManagerEntity mstManagerEntity = new MstManagerEntity();
            //管理者ID
            mstManagerEntity.setMgrId(org.getOrgId() + brandCd);
            //メールアドレス
            mstManagerEntity.setMailad("");
            //姓名_姓
            mstManagerEntity.setFlnmNm("");
            //姓名_名
            mstManagerEntity.setFlnmLnm("");
            //姓名_カナ姓
            mstManagerEntity.setFlnmKnNm("");
            //姓名_カナ名
            mstManagerEntity.setFlnmKnLnm("");
            //電話番号
            mstManagerEntity.setTelnum("");
            //作成日時
            mstManagerEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            mstManagerEntity.setCretUsrId(manageId);
            //更新日時
            mstManagerEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstManagerEntity.setUpdUsrId(manageId);
            //削除フラグ
            mstManagerEntity.setDelFlg(0);

            MstOrgFunListEntity mstOrgFunListEntity1 = new MstOrgFunListEntity();

            for (MstOrgFunListEntity mstOrgFunListEntity : mstOrgFunListEntityList) {
                //組織ID
                mstOrgFunListEntity1.setOrgId(org.getOrgId());
                //機能ID
                mstOrgFunListEntity1.setFunId(mstOrgFunListEntity.getFunId());
                //管理者フラグ
                mstOrgFunListEntity1.setMentFlg(mstOrgFunListEntity.getMentFlg());
                //メンターフラグ
                mstOrgFunListEntity1.setMgrFlg(mstOrgFunListEntity.getMgrFlg());
                //作成日時
                mstOrgFunListEntity1.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                mstOrgFunListEntity1.setCretUsrId(manageId);
                //更新日時
                mstOrgFunListEntity1.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                mstOrgFunListEntity1.setUpdUsrId(manageId);
                //削除フラグ
                mstOrgFunListEntity1.setDelFlg(0);
                mstOrgFunListDao.insert(mstOrgFunListEntity1);
            }

            mstManagerDao.insert(mstManagerEntity);
            // 2021/1/4 huangxinliang modify start
            threadPoolTaskExecutor.execute(() -> {
                createAccount(org);
            });
            // 2021/1/4 huangxinliang modify end
            return R.ok(MessageUtils.getMessage("MSGCOMN0022", "組織の追加"));
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createAccount(MstOrgEntity org){
        //画面．上位組織をもとに、下記条件でユーザ基本マスタを取得する
        List<MstUsrEntity> upUsrList = f00003Dao.getUpUsr(org.getUpplevOrgId());
        for (MstUsrEntity mstUsrEntity1 : upUsrList){
            String usrId = mstUsrEntity1.getUsrId();
            String roleDiv = mstUsrEntity1.getRoleDiv();
            Timestamp nowTime = DateUtils.getSysTimestamp();
            String nowUsrId = ShiroUtils.getUserId();
            //ユーザ基本マスタ登録
            MstUsrEntity mstUsrEntity2 = new MstUsrEntity();
            //ユーザID
            MstNumassEntity mstNumassEntity =  mstNumassDao.selectOne(new QueryWrapper<MstNumassEntity>().eq("role_div",roleDiv));
            String maxId = mstNumassEntity.getMaxId();
            String newMaxId = maxId.charAt(0) + new BigInteger(maxId.substring(1)).add(new BigInteger("1")).toString();
            MstNumassEntity m = new MstNumassEntity();
            m.setMaxId(newMaxId);
            mstNumassDao.update(m,new QueryWrapper<MstNumassEntity>().eq("role_div",roleDiv));

            mstUsrEntity2.setUsrId(newMaxId);
            //ユーザログインPW
            mstUsrEntity2.setUsrPassword(null);
            //初回登録フラグ
            mstUsrEntity2.setFstLoginFlg("0");
            //ユーザ名
            mstUsrEntity2.setUsrNm(mstUsrEntity1.getUsrNm());
            //取得したロール区分
            mstUsrEntity2.setRoleDiv(roleDiv);
            //組織ID
            mstUsrEntity2.setOrgId(org.getOrgId());
            //PW更新フラグ
            mstUsrEntity2.setPwUpFlg(mstUsrEntity1.getPwUpFlg());
            //ユーザステータス
            mstUsrEntity2.setUsrSts(mstUsrEntity1.getUsrSts());
            //特殊権限フラグ
            mstUsrEntity2.setSpecAuthFlg("0");
            //変更後ユーザーID
            mstUsrEntity2.setAfterUsrId(mstUsrEntity1.getAfterUsrId());
            //エラー回数
            mstUsrEntity2.setErrorCount(0);
            //ロックフラグ
            mstUsrEntity2.setLockFlg("0");
            //学研IDプライマリキー
            mstUsrEntity2.setGidpk(mstUsrEntity1.getGidpk());
            //GIDフラグ
            mstUsrEntity2.setGidFlg(mstUsrEntity1.getGidFlg());
            //他システム区分
            mstUsrEntity2.setSystemKbn(mstUsrEntity1.getSystemKbn());
            //組織共用キー
            mstUsrEntity2.setOrgCommKey(org.getOrgId()+"_key");
            //GakkenID規約フラグ
            mstUsrEntity2.setGidRuleFlg("0");
            //マナミル規約フラグ
            mstUsrEntity2.setManaRuleFlg("0");
            //個人情報保護規約フラグ
            mstUsrEntity2.setPerlInfoRuleFlg("0");
            //自分修正可否フラグ
            mstUsrEntity2.setSafModifyFlg("0");
            //管理者修正可否フラグ
            mstUsrEntity2.setMgrModifyFlg("0");
            //所属組織フラグ
            mstUsrEntity2.setOwnerOrgFlg("0");
            //作成日時
            mstUsrEntity2.setCretDatime(nowTime);
            //作成ユーザＩＤ
            mstUsrEntity2.setCretUsrId(nowUsrId);
            //更新日時
            mstUsrEntity2.setUpdDatime(nowTime);
            //更新ユーザＩＤ
            mstUsrEntity2.setUpdUsrId(nowUsrId);
            //削除フラグ
            mstUsrEntity2.setDelFlg(0);
            mstUsrDao.insert(mstUsrEntity2);
            //ユーザーキャラ登録
            // 2020/12/9 huangxinliang modify start
            String roleName = insertRoleBaseDate(usrId, roleDiv, nowTime, nowUsrId, mstUsrEntity2, newMaxId);
            //ロール登録 --2020/11/18 modify yang start
            SysRoleEntity sysRoleEntity = sysRoleDao.selectOne(new QueryWrapper<SysRoleEntity>().eq("role_name", roleName));
            insertSysUserRole(mstUsrEntity2, sysRoleEntity);
            //ロール登録 --2020/11/18 modify yang end
            // 2020/12/9 huangxinliang modify end
        }
    }

    private String insertRoleBaseDate(String usrId, String roleDiv, Timestamp nowTime, String nowUsrId, MstUsrEntity mstUsrEntity2, String newMaxId) {
        String roleName = "";
        switch (roleDiv) {
            case "1 ":
                roleName = "manager";
                //管理者基本マスタ登録
                MstManagerEntity mstManagerEntity1 = f00003Dao.getMgr(usrId);
                //空論ではない 2020/11/04 Modify yang
                if (mstManagerEntity1 != null){
                    //管理者ID
                    mstManagerEntity1.setMgrId(mstUsrEntity2.getUsrId());
                    //作成日時
                    mstManagerEntity1.setCretDatime(nowTime);
                    //作成ユーザＩＤ
                    mstManagerEntity1.setCretUsrId(nowUsrId);
                    //更新日時
                    mstManagerEntity1.setUpdDatime(nowTime);
                    //更新ユーザＩＤ
                    mstManagerEntity1.setUpdUsrId(nowUsrId);
                    //削除フラグ
                    mstManagerEntity1.setDelFlg(0);
                    mstManagerDao.insert(mstManagerEntity1);
                    break;
                }
            case "2 ":
                roleName = "mentor";
                //メンター基本マスタ登録
                MstMentorEntity mstMentorEntity = f00003Dao.getMentor(usrId);
                //空論ではない 2020/11/04 Modify yang
                if (mstMentorEntity != null){
                    //メンターID
                    mstMentorEntity.setMentorId(mstUsrEntity2.getUsrId());
                    //作成日時
                    mstMentorEntity.setCretDatime(nowTime);
                    //作成ユーザＩＤ
                    mstMentorEntity.setCretUsrId(nowUsrId);
                    //更新日時
                    mstMentorEntity.setUpdDatime(nowTime);
                    //更新ユーザＩＤ
                    mstMentorEntity.setUpdUsrId(nowUsrId);
                    //削除フラグ
                    mstMentorEntity.setDelFlg(0);
                    mstMentorDao.insert(mstMentorEntity);
                    break;
                }
            case "3 ":
                roleName = "guard";
                //保護者基本マスタ登録
                MstGuardEntity mstGuardEntity = f00003Dao.getGuard(usrId);
                //空論ではない 2020/11/04 Modify yang
                if (mstGuardEntity != null){
                    //保護者ID
                    mstGuardEntity.setGuardId(mstUsrEntity2.getUsrId());
                    //作成日時
                    mstGuardEntity.setCretDatime(nowTime);
                    //作成ユーザＩＤ
                    mstGuardEntity.setCretUsrId(nowUsrId);
                    //更新日時
                    mstGuardEntity.setUpdDatime(nowTime);
                    //更新ユーザＩＤ
                    mstGuardEntity.setUpdUsrId(nowUsrId);
                    //削除フラグ
                    mstGuardEntity.setDelFlg(0);
                    mstGuardDao.insert(mstGuardEntity);
                    break;
                }
            case "4 ":
                roleName = "student";
                //生徒基本マスタ登録
                MstStuEntity mstStuEntity = f00003Dao.getStu(usrId);
                //空論ではない 2020/11/04 Modify yang
                if (mstStuEntity != null){
                    //生徒ID
                    mstStuEntity.setStuId(newMaxId);
                    // 2020/12/8 huangxinliang modify start
                    mstStuEntity.setQrCod(newMaxId);
                    // 2020/12/8 huangxinliang modify end
                    //作成日時
                    mstStuEntity.setCretDatime(nowTime);
                    //作成ユーザＩＤ
                    mstStuEntity.setCretUsrId(nowUsrId);
                    //更新日時
                    mstStuEntity.setUpdDatime(nowTime);
                    //更新ユーザＩＤ
                    mstStuEntity.setUpdUsrId(nowUsrId);
                    //削除フラグ
                    mstStuEntity.setDelFlg(0);
                    mstStuDao.insert(mstStuEntity);
                }
                break;
            default:
                break;
        }
        return roleName;
    }

    private void insertSysUserRole(MstUsrEntity mstUsrEntity2, SysRoleEntity sysRoleEntity) {
        Long roleId;
        if (sysRoleEntity != null) {
            roleId = sysRoleEntity.getRoleId();
        } else {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0017", "システムの角色テーブルのデータ"));
        }
        //角色の追加
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        sysUserRoleEntity.setRoleId(roleId);
        sysUserRoleEntity.setUserId(mstUsrEntity2.getId().longValue());
        sysUserRoleDao.insert(sysUserRoleEntity);
    }
}