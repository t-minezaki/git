package jp.learningpark.modules.com.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dto.OnLineApiDto;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.sys.dao.SysUserRoleDao;
import jp.learningpark.modules.sys.entity.SysUserRoleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * マナミルSAMLMAPPING TransactionBean
 * </p>
 *
 * @author NWT)lyx
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/03/22 ： NWT)lyx ： 新規作成
 * @date 2021/03/22 10:27
 */
@Component
public class OnLineApiTransactionBean {
    /**
     * マスタ_明細Dao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * ユーザー基本マスタ　Dao
     */
    @Autowired
    private MstUsrDao mstUsrDao;

    /**
     * ユーザ初期パスワード管理　Dao
     */
    @Autowired
    MstUsrFirtPwDao mstUsrFirtPwDao;

    /**
     * 採番マスタ Dao
     */
    @Autowired
    private MstNumassDao mstNumassDao;

    /**
     * 生徒基本マスタ　Dao
     */
    @Autowired
    private MstStuDao mstStuDao;

    /**
     * 保護者基本マスタ
     */
    @Autowired
    private MstGuardDao mstGuardDao;

    /**
     * ユーザーキャラ
     */
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    /**
     * グループマスタ Dao
     */
    @Autowired
    private MstGrpDao mstGrpDao;

    /**
     * 会員-グループマスタ dao
     */
    @Autowired
    private StuGrpDao stuGrpDao;

    /**
     * マナミルSAMLMAPPING Dao
     */
    @Autowired
    private OnLineApiDao onLineApiDao;

    /**
     * マナミルSAMLMAPPING データ登録者
     */
    private  static final String LOGIN_ID = "SamlDataSync";

    /**
     * ログ
     */
    private static final Logger logger = LoggerFactory.getLogger(OnLineApiTransactionBean.class);

    /**
     * msg:生徒の学研ID：
     */
    private static final String MSG_STU_ID = "生徒の学研ID：";

    /**
     * msg:保護者のGIDPK：
     */
    private static final String MSG_GUARD_GIDPK = "保護者のGIDPK：";

    /**
     * msg:教室コースマスタ：
     */
    private static final String MSG_GRP = "教室コースマスタ：";

    /**
     * 組織ID:onair30001 2021/4/26変更——> mnONAIR2
     */
    private static final String STR_ORG_ID = "mnONAIR2";

    /**
     * attributesのtypeが「Contact」データ とactionがinsertの場合、新規操作を行う。
     * @param dto
     * @param genderDivList
     * @param reltnspList
     * @param schyDivList
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String contactInsert(OnLineApiDto dto, List<MstCodDEntity> genderDivList, List<MstCodDEntity> reltnspList, List<MstCodDEntity> schyDivList){
        try{
            //保護者データを作成する
            String getGroudNewId = handleGuardData(dto, reltnspList);
            // 生徒情報のDB処理エラー時：
            //　・生徒の学研ID　Contact.SeitoBango2__c
            String errorMsg = MSG_STU_ID + dto.SeitoBango2__c ;
            // 生徒データを作成する
            // 採番マスタ更新
            String getStuNewId = setNewId(GakkenConstant.STUDENT_ROLE_DIV, errorMsg);
            // ユーザログインPW
            String getStuPwd = ShiroUtils.stringToAscii(getStuNewId);
            // ユーザ基本マスタ登録（生徒）、ユーザ初期パスワード管理登録
            mstUsrSave(dto, GakkenConstant.STUDENT_ROLE_DIV, getStuNewId, getStuPwd,errorMsg);
            // 生徒基本マスタ登録
            mstStuSave(dto,getStuNewId, getGroudNewId, schyDivList, genderDivList, errorMsg);
            // ユーザ初期パスワード管理登録
            mstUsrFirtPwSave(GakkenConstant.STUDENT_ROLE_DIV, getStuNewId, getStuPwd, errorMsg);
        }catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    /**
     * actionがupdateの場合 DBに当該レコード既存しないの場合,更新操作を行う。
     * @param dto
     * @param genderDivList
     * @param reltnspList
     * @param schyDivList
     * @param stu
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String contactUpdate(OnLineApiDto dto, List<MstCodDEntity> genderDivList, List<MstCodDEntity> reltnspList, List<MstCodDEntity> schyDivList, MstStuEntity stu){
        try{
            // 保護者データを更新する。
            String guardId = handleGuardData(dto, reltnspList);
            //生徒データを更新する
            // 生徒情報のDB処理エラー時：
            //　・生徒の学研ID　Contact.SeitoBango2__c
            String errorMsg = MSG_STU_ID + dto.SeitoBango2__c ;
            // ユーザ基本マスタ更新（生徒）
            mstUsrdUp(dto, GakkenConstant.STUDENT_ROLE_DIV,"", errorMsg);
            //生徒基本マスタ更新
            mstStudUp(dto, schyDivList, genderDivList, stu.getStuId(), guardId, errorMsg);
        }catch (Exception e) {
            return e.getMessage() ;
        }
        return null;
    }


    /**
     * グループ既存の場合、グループデータを更新する
     * @param dto
     * @param dayweekDivList
     * @param mstGrpList
     * @param stuId
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String jukoJohoUpdate(OnLineApiDto dto, List<MstCodDEntity> dayweekDivList, List<MstGrpEntity> mstGrpList, String stuId){
        try{
            //グループマスタ更新
            mstGrpUp(dto, dayweekDivList, mstGrpList.get(0));
            // 生徒グループ管理に生徒とグループ関連関係設定します。
            //生徒グループ管理データ取得
            List<StuGrpEntity> stuGrpEntityList = stuGrpDao.selectList(new QueryWrapper<StuGrpEntity>().and(wrapper -> wrapper.eq("grp_Id", mstGrpList.get(0).getGrpId())
                    .eq("stu_id", stuId).eq("del_flg", 0)));
            // ③で生徒とグループ関連関係既存の場合、何もしない。
            //③で生徒とグループ関連関係既存しない場合、生徒とグループ関連関係新規追加します。
            if(stuGrpEntityList.size() == 0){
                //生徒グループ管理登録
                insertStuGrp(stuId, mstGrpList.get(0).getGrpId(), dto.KyoshitsuCourceMaster__r);
            }
        }catch (Exception e) {
            return e.getMessage() ;
        }
        return null;
    }

    /**
     * 生徒グループ管理に生徒とグループ関連関係設定します。
     * @param stuId
     * @param grpId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String jukoJohoStuGrpSave(String stuId, Integer grpId, String grpCod){
        try{
            //生徒グループ管理データ取得
            List<StuGrpEntity> stuGrpEntityList = stuGrpDao.selectList(new QueryWrapper<StuGrpEntity>().and(wrapper -> wrapper.eq("grp_Id", grpId)
                    .eq("stu_id", stuId).eq("del_flg", 0)));
            // ③で生徒とグループ関連関係既存の場合、何もしない。
            //③で生徒とグループ関連関係既存しない場合、生徒とグループ関連関係新規追加します。
            if(stuGrpEntityList.size() == 0){
                //生徒グループ管理登録
                insertStuGrp(stuId, grpId, grpCod);
            }
        }catch (Exception e) {
            return e.getMessage() ;
        }
        return null;
    }

    /**
     * グループマスタ登録
     * @param JukoJoho__c
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String mstGrpSave(OnLineApiDto JukoJoho__c, List<MstCodDEntity> dayweekDivList, String stuId){
        MstGrpEntity mstGrpEntity = new MstGrpEntity();
        // グループ情報のDB処理エラー時：
        //　・教室コースマスタ　JukoJoho__c.KyoshitsuCourceMaster__r
        String errorMsg = MSG_GRP + JukoJoho__c.KyoshitsuCourceMaster__r;
        try{
            //組織ID
            mstGrpEntity.setOrgId(STR_ORG_ID);
            String[] kyoshitsuCourceMaster = JukoJoho__c.KyoshitsuCourceMaster__r.split("：");
            String dayweekDiv = null;
            String grpNm = null;
            if(kyoshitsuCourceMaster.length >= 3){
                dayweekDiv = kyoshitsuCourceMaster[2].substring(0,1);
                for(MstCodDEntity MstCodD : dayweekDivList){
                    if(MstCodD.getCodValue().equals(dayweekDiv)){
                        //曜日区分:JukoJoho__c.KyoshitsuCourceMaster__r
                        mstGrpEntity.setDayweekDiv(MstCodD.getCodCd());
                        break;
                    }
                }
                grpNm = kyoshitsuCourceMaster[0] + kyoshitsuCourceMaster[1];
            }
            //グループ名:・コロン（：）で区切られた、１つ目（教科）＆２つ目（レベル）を繋げてデータをセット
            mstGrpEntity.setGrpNm(grpNm);
            // グループコード：KyoshitsuCourceMaster__c
            mstGrpEntity.setGrpCod(JukoJoho__c.KyoshitsuCourceMaster__c);
            //作成日時：システム日時
            mstGrpEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ：ログインユーザＩＤ
            mstGrpEntity.setCretUsrId(LOGIN_ID);
            //更新日時：システム日時
            mstGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ：ログインユーザＩＤ
            mstGrpEntity.setUpdUsrId(LOGIN_ID);
            //削除フラグ
            mstGrpEntity.setDelFlg(GakkenConstant.DEL_FLG.NO.getValue());
            try{
                mstGrpDao.insert(mstGrpEntity);
            }catch (Exception e){
                logger.error(e.getMessage());
                logger.error("[グループマスタ登録操作失敗]");
                throw new RRException(errorMsg + " [グループマスタ登録操作失敗]" + e.getMessage());
            }
            //生徒グループ管理データ取得
            List<StuGrpEntity> stuGrpEntityList = stuGrpDao.selectList(new QueryWrapper<StuGrpEntity>().and(wrapper -> wrapper.eq("grp_Id", mstGrpEntity.getGrpId())
                    .eq("stu_id", stuId).eq("del_flg", 0)));
            // ③で生徒とグループ関連関係既存の場合、何もしない。
            //③で生徒とグループ関連関係既存しない場合、生徒とグループ関連関係新規追加します。
            if(stuGrpEntityList.size() == 0){
                //生徒グループ管理登録
                insertStuGrp(stuId,mstGrpEntity.getGrpId(),JukoJoho__c.KyoshitsuCourceMaster__r);
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;

    }

    /**
     * 保護者データを作成する
     * @param dto
     * @param reltnspList
     * @return GroudId
     */
    private String handleGuardData(OnLineApiDto dto, List<MstCodDEntity> reltnspList){
        // Contact.KazokuCode__cを基づいて、データの既存チェックを行う
        // 保護者データ取得
        List<MstUsrEntity> mstUsrList = mstUsrDao.selectList(new QueryWrapper<MstUsrEntity>()
                .eq("gidpk", dto.KazokuCode__c)
                .eq("org_id", STR_ORG_ID)
                .eq("del_flg", 0));
        //　・生徒の学研ID　Contact.SeitoBango2__c
        //　・保護者のGIDPK　Contact.KazokuCode__c
        String errorMsg = MSG_STU_ID + dto.SeitoBango2__c + " " + MSG_GUARD_GIDPK + dto.KazokuCode__c;
        // ①検索結果レコード数＞０の場合、既存と判定します。
        // 保護者データを更新する。
        if(mstUsrList.size() > 0){
            // 保護者データを更新する。
            // 保護者情報のDB処理エラー時：
            //	[DBセット]シートのDBセット名[保護者基本マスタ更新]
            mstGuardUp(dto, reltnspList, mstUsrList.get(0).getUsrId(), errorMsg);
            return mstUsrList.get(0).getUsrId();
        }// ②検索結果レコード数＝０の場合、既存しないと判定します。
        else {
            // 保護者データを作成する
            // 採番マスタ更新
            String getGroudNewId = setNewId(GakkenConstant.GUARD_ROLE_DIV, errorMsg);
            //ユーザ基本マスタ登録（保護者）、ユーザーキャラ登録
            mstUsrSave(dto, GakkenConstant.GUARD_ROLE_DIV, getGroudNewId,null, errorMsg);
            // 保護者基本マスタ登録
            mstGuardSave(dto, getGroudNewId, reltnspList, errorMsg);
            // ユーザ初期パスワード管理登録
            mstUsrFirtPwSave(GakkenConstant.GUARD_ROLE_DIV, getGroudNewId," ", errorMsg);
            return getGroudNewId;
        }

    }

    /**
     * グループマスタ更新
     * @param stuId
     * @param grpId
     */
    private void insertStuGrp(String stuId,Integer grpId,String grpCod){
        // 2021/5/18 huangxinliang modify start
//            try{
//                 //生徒とグループ関連関係を削除する
//                onLineApiDao.deleteStuGrpByStuId(stuId);
//
//            }catch (Exception e) {
//
//                // グループ情報のDB処理エラー時：
//                //　・教室コースマスタ　JukoJoho__c.KyoshitsuCourceMaster__r
//                String errorMsg = MSG_GRP + grpCod;
//                logger.error(e.getMessage());
//                logger.error("[生徒とグループ関連関係を物理削除操作失敗]");
//                throw new RRException(errorMsg + " [生徒とグループ関連関係を物理削除操作失敗]" + e.getMessage());
//            }
        // 2021/5/18 huangxinliang modify end
        //生徒グループ管理登録
        StuGrpEntity stuGrpEntity = new StuGrpEntity();
        // ID:連番採番したID
        // 生徒ID:取得ユーザー基本マスタ・ユーザID
        stuGrpEntity.setStuId(stuId);
        // グループID:取得したグループマスタ・グループID
        stuGrpEntity.setGrpId(grpId);
        //作成日時:システム日時
        stuGrpEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ:	ログインユーザＩＤ
        stuGrpEntity.setCretUsrId(LOGIN_ID);
        //更新日時:システム日時
        stuGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ:	ログインユーザＩＤ
        stuGrpEntity.setUpdUsrId(LOGIN_ID);
        //削除フラグ:「0：有効」
        stuGrpEntity.setDelFlg(GakkenConstant.DEL_FLG.NO.getValue());
        try{
            stuGrpDao.insert(stuGrpEntity);
        } catch (Exception e) {
            // グループ情報のDB処理エラー時：
            //　・教室コースマスタ　JukoJoho__c.KyoshitsuCourceMaster__r
            String errorMsg = MSG_GRP + grpCod;
            logger.error(e.getMessage());
            logger.error("[生徒グループ管理登録操作失敗]");
            throw new RRException(errorMsg + " [生徒グループ管理登録操作失敗]" + e.getMessage());
        }
    }

    /**
     * グループマスタ更新
     * @param JukoJoho__c
     * @param dayweekDivList
     * @param MstGrp
     * @return
     */
    private void mstGrpUp(OnLineApiDto JukoJoho__c, List<MstCodDEntity> dayweekDivList, MstGrpEntity MstGrp){
        Map<String, Object> entity = new HashMap<>(16);
        try{
            String[] kyoshitsuCourceMaster = JukoJoho__c.KyoshitsuCourceMaster__r.split("：");
            // グループ名
            String grpNm = null;
            // 曜日区分
            String newDayweekDiv = "";
            if(kyoshitsuCourceMaster.length >= 3){
                String dayweekDiv = null;
                String dayweekDate = kyoshitsuCourceMaster[2].substring(0,1);
                for(MstCodDEntity MstCodD : dayweekDivList){
                    if(MstCodD.getCodValue().equals(dayweekDate)){
                        dayweekDiv = MstCodD.getCodCd();
                        break;
                    }
                }
                //※曜日数複数を選択した場合、カンマ区切する。
                //例：月、火同時選択した場合、1，2
                //以前の曜日区分が「JukoJoho__c.KyoshitsuCourceMaster__r」含めないの場合、今回の値を追加して、更新する
                if(!MstGrp.getDayweekDiv().contains(dayweekDiv)){
                    String div[] = MstGrp.getDayweekDiv().trim().split(",");
                    int index=Arrays.binarySearch(div, dayweekDiv);
                    int newIndex=-index-1;
                    String newDiv[] = new String[div.length+1];
                    System.arraycopy(div, 0, newDiv, 0, newIndex);
                    newDiv[newIndex]=dayweekDiv;
                    System.arraycopy(div,newIndex,newDiv, newIndex+1,div.length-newIndex);
                    // 曜日区分
                    for(String str : newDiv){
                        if(StringUtils.isEmpty(newDayweekDiv)){
                            newDayweekDiv = str;
                        }else{
                            newDayweekDiv = newDayweekDiv + "," + str;
                        }
                    }
                }
                //以前の曜日区分が「JukoJoho__c.KyoshitsuCourceMaster__r」含めるの場合、更新なし
                else{
                    newDayweekDiv = MstGrp.getDayweekDiv();
                }
                //グループ名:・コロン（：）で区切られた、１つ目（教科）＆２つ目（レベル）を繋げてデータをセット
                grpNm = kyoshitsuCourceMaster[0] + kyoshitsuCourceMaster[1];
            }
            entity.put("dayweekDiv", newDayweekDiv);
            entity.put("grpNm", grpNm);
            // グループコード：KyoshitsuCourceMaster__c
            entity.put("grpCod", JukoJoho__c.KyoshitsuCourceMaster__c);
            // 更新日時	：システム日時
            entity.put("updDatime", DateUtils.getSysTimestamp());
            // 更新ユーザＩＤ：ログインユーザＩＤ
            entity.put("updUsrId", LOGIN_ID);
            onLineApiDao.updateMstGrp(entity);
        } catch (Exception e) {
            // グループ情報のDB処理エラー時：
            //　・教室コースマスタ　JukoJoho__c.KyoshitsuCourceMaster__r
            String errorMsg = MSG_GRP + JukoJoho__c.KyoshitsuCourceMaster__r;
            logger.error(e.getMessage());
            logger.error("[グループマスタ更新操作失敗]");
            throw new RRException(errorMsg + " [グループマスタ更新操作失敗]" + e.getMessage());
        }
    }

    /**
     * 採番
     * @param role
     * @return
     */
    private String setNewId(String role,String errorMsg){
        // ロール区分
        MstNumassEntity mstNumassEntity = mstNumassDao.selectOne(new QueryWrapper<MstNumassEntity>()
                .eq("role_div", role)
                .eq("del_flg", 0));
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
            mstNumassEntity.setUpdDatime(DateUtils.getSysTimestamp());
            // 更新ユーザＩＤ
            mstNumassEntity.setUpdUsrId(LOGIN_ID);
            try {
                // 採番マスタ更新
                mstNumassDao.updateById(mstNumassEntity);
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.error("[採番マスタ更新操作失敗]");
                throw new RRException(errorMsg + " [採番マスタ更新操作失敗]" + e.getMessage());
            }
        } else {
            mstNumassEntity = new MstNumassEntity();
            mstNumassEntity.setBrandCd(ShiroUtils.getBrandcd());
            mstNumassEntity.setRoleDiv(role);
            mstNumassEntity.setMaxId(
                    StringUtils.equals("1", role) ? "a1" : (StringUtils.equals("2", role) ? "m1" : (StringUtils.equals("3", role) ? "p1" : "s1")));
            mstNumassEntity.setCretDatime(DateUtils.getSysTimestamp());
            mstNumassEntity.setCretUsrId(LOGIN_ID);
            mstNumassEntity.setUpdDatime(DateUtils.getSysTimestamp());
            mstNumassEntity.setUpdUsrId(LOGIN_ID);
            mstNumassEntity.setDelFlg(GakkenConstant.DEL_FLG.NO.getValue());
            try{
                mstNumassDao.insert(mstNumassEntity);
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.error("[採番マスタ登録操作失敗]");
                throw new RRException(errorMsg + " [採番マスタ登録操作失敗]" + e.getMessage());
            }
            newMaxId = mstNumassEntity.getMaxId();
        }
        return newMaxId;
    }

    /**
     * ユーザ初期パスワード管理登録
     * @param role
     * @param newUsrId
     * @param pwd
     * @return
     */
    private void mstUsrFirtPwSave(String role,String newUsrId,String pwd,String errorMsg){
        //ユーザ初期パスワード管理登録
        MstUsrFirtPwEntity mstUsrFirtPwEntity = new MstUsrFirtPwEntity();
        // ユーザID
        mstUsrFirtPwEntity.setUsrId(newUsrId);
        // 取得したユーザー基本マスタ・ロール区分
        mstUsrFirtPwEntity.setRoleDiv(role);
        // roleが生徒の場合
        if(GakkenConstant.STUDENT_ROLE_DIV.equals(role)){
            //取得したユーザー基本マスタ・ユーザログインPW
            mstUsrFirtPwEntity.setUsrFstPassword(ShiroUtils.sha256(pwd, newUsrId));
        }else{
            //保護者の場合、
            //　　半角スペース
            mstUsrFirtPwEntity.setUsrFstPassword(" ");
        }
        // システム区分 「0：マラミル」
        mstUsrFirtPwEntity.setSysDiv("0");
        // 作成日時
        mstUsrFirtPwEntity.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ：ログインユーザＩＤ
        mstUsrFirtPwEntity.setCretUsrId(LOGIN_ID);
        // 更新日時
        mstUsrFirtPwEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ：ログインユーザＩＤ
        mstUsrFirtPwEntity.setUpdUsrId(LOGIN_ID);
        // 削除フラグ"「0：有効」
        mstUsrFirtPwEntity.setDelFlg(GakkenConstant.DEL_FLG.NO.getValue());
        try {
            mstUsrFirtPwDao.insert(mstUsrFirtPwEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("[ユーザ初期パスワード管理登録操作失敗]");
            throw new RRException(errorMsg + " [ユーザ初期パスワード管理登録操作失敗]" + e.getMessage());
        }
    }

    /**
     * ユーザ基本マスタ登録
     * @param onLineApiDto
     * @param role
     * @param newUsrId
     * @param pwd
     * @return
     */
    private void mstUsrSave(OnLineApiDto onLineApiDto, String role, String newUsrId, String pwd,String errorMsg){
        // ユーザー基本マスタへ登録する
        MstUsrEntity mstUsrEntity = new MstUsrEntity();
        // ユーザID
        mstUsrEntity.setUsrId(newUsrId);
        // PW更新フラグ:"「0：未更新」
        mstUsrEntity.setPwUpFlg("0");
        //初回登録フラグ:「1：初回以外」
        mstUsrEntity.setFstLoginFlg("1");
        //ユーザ名
        mstUsrEntity.setUsrNm(null);
        //ロール区分
        mstUsrEntity.setRoleDiv(role);
        // 組織ID
        mstUsrEntity.setOrgId(STR_ORG_ID);
        //特殊権限フラグ:「0：無」
        mstUsrEntity.setSpecAuthFlg("0");
        // roleが生徒の場合
        if(GakkenConstant.STUDENT_ROLE_DIV.equals(role)){
            //変更後ユーザーID:"Contact.SeitoBango2__c
            mstUsrEntity.setAfterUsrId(onLineApiDto.SeitoBango2__c);
            //学研IDプライマリキー:null
            mstUsrEntity.setGidpk(null);
            //ユーザステータス	:"Contact.TaijukuDate__cがNuLLなら、１をセット,Contact.TaijukuDate__cがNull以外なら、０をセット"
            mstUsrEntity.setUsrSts(onLineApiDto.TaijukuDate__c == null?"1":"0");
            //ユーザログインPW:マナミルルールで生成する（生徒）
            //ユーザログインPW:null(保護者)
            mstUsrEntity.setUsrPassword(ShiroUtils.sha256(pwd, newUsrId));
        }// roleが保護者の場合
        else{
            //変更後ユーザーID:"半角スペース
            mstUsrEntity.setAfterUsrId(" ");
            //学研IDプライマリキー:Contact.KazokuCode__c
            mstUsrEntity.setGidpk(onLineApiDto.KazokuCode__c);
            //ユーザステータス	:「1：在塾」
            mstUsrEntity.setUsrSts("1");
        }
        //GIDフラグ:「1：GID」
        mstUsrEntity.setGidFlg("1");
        //エラー回数:0
        mstUsrEntity.setErrorCount(0);
        //ロックフラグ：0：未ロック」
        mstUsrEntity.setLockFlg("0");
        //他システム区分:「「3：その他」」
        mstUsrEntity.setSystemKbn("3");
        //組織共用キー:null
        mstUsrEntity.setOrgCommKey(null);
        //GakkenID規約フラグ：「0：未確認」
        mstUsrEntity.setGidRuleFlg("0");
        //マナミル規約フラグ「0：未確認」
        mstUsrEntity.setManaRuleFlg("0");
        //個人情報保護規約フラグ：「0：未確認」
        mstUsrEntity.setPerlInfoRuleFlg("0");
        //自分修正可否フラグ：「0：否」
        mstUsrEntity.setSafModifyFlg("0");
        //管理者修正可否フラグ：0：否」
        mstUsrEntity.setMgrModifyFlg("0");
        //所属組織フラグ:「1：所属」
        mstUsrEntity.setOwnerOrgFlg("1");
        // 作成日時
        mstUsrEntity.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ：ログインユーザＩＤ
        mstUsrEntity.setCretUsrId(LOGIN_ID);
        // 更新日時
        mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ：ログインユーザＩＤ
        mstUsrEntity.setUpdUsrId(LOGIN_ID);
        // 削除フラグ"「0：有効」
        mstUsrEntity.setDelFlg(GakkenConstant.DEL_FLG.NO.getValue());
        try{
            mstUsrDao.insert(mstUsrEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("[ユーザ基本マスタ登録操作失敗]");
            throw new RRException(errorMsg + " [ユーザ基本マスタ登録操作失敗]" + e.getMessage());
        }
        //ユーザーキャラへ登録する
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        // ユーザID:ＤＢセット「ユーザー基本マスタ登録」登録した「ＩＤ」
        //ユーザー基本マスタ.ID
        sysUserRoleEntity.setUserId((long) mstUsrEntity.getId());
        //キャラクターID :"直前に設定したユーザ基本マスタ．ロール区分が「4:生徒」の場合、2
        // 直前に設定したユーザ基本マスタ．ロール区分が「3:保護者」の場合、3
        //キャラクターID
        if(GakkenConstant.STUDENT_ROLE_DIV.equals(role)){
            sysUserRoleEntity.setRoleId(2L);
        }// 保護者
        else{
            sysUserRoleEntity.setRoleId(3L);
        }
        try {
            sysUserRoleDao.insert(sysUserRoleEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("[ユーザーキャラ登録操作失敗]");
            throw new RRException(errorMsg + " [ユーザーキャラ登録操作失敗]" + e.getMessage());
        }
    }

    /**
     * 生徒基本マスタ登録
     * @param Contact
     * @param newUsrId
     * @param newGuardId
     * @return
     */
    private void mstStuSave(OnLineApiDto Contact, String newUsrId, String newGuardId, List<MstCodDEntity> schyDivList, List<MstCodDEntity> genderDivList,String errorMsg){
        MstStuEntity mstStuEntity = new MstStuEntity();
        try {
            // 生徒ID：取得したユーザー基本マスタ．ユーザID
            mstStuEntity.setStuId(newUsrId);
            // 学校名：Contact.GakkoMei__c	     誠之中学校
            mstStuEntity.setSch(Contact.GakkoMei__c);
            // 保護者ID:取得したユーザー基本マスタ・ユーザID（保護者）
            mstStuEntity.setGuardId(newGuardId);
            // 保護者1ID:NULL
            mstStuEntity.setGuard1Id(null);
            // 保護者2ID:NULL
            mstStuEntity.setGuard2Id(null);
            // 保護者3ID:NULL
            mstStuEntity.setGuard3Id(null);
            // 保護者4ID:NULL
            mstStuEntity.setGuard4Id(null);
            // 姓名_姓：Contact.LastName
            mstStuEntity.setFlnmNm(Contact.LastName);
            // 姓名_名：Contact.FirstName
            mstStuEntity.setFlnmLnm(Contact.FirstName);
            // 姓名_カナ姓：	Contact.SeiKana__cの全角カタカナへの変換
            //上記がNULLの場合、
            //　　「カナ」
            if(StringUtils.isEmpty(Contact.SeiKana__c)){
                mstStuEntity.setFlnmKnNm("カナ");
            }else{
                mstStuEntity.setFlnmKnNm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Contact.SeiKana__c));
            }
            // 姓名_カナ名：Contact.MeiKana__cの全角カタカナへの変換
            //上記がNULLの場合、
            //　　「シメイ」
            if(StringUtils.isEmpty(Contact.MeiKana__c)){
                mstStuEntity.setFlnmKnLnm( "シメイ");
            }else{
                mstStuEntity.setFlnmKnLnm( HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Contact.MeiKana__c));
            }
            // 性別区分：Contact.Seibetsu__c"男" の場合 1 "女" の場合 2 未設定または上記以外を 9（その他） としてください
            mstStuEntity.setGendrDiv("9");
            for(MstCodDEntity MstCodD : genderDivList){
                if(MstCodD.getCodValue().equals(Contact.Seibetsu__c)){
                    mstStuEntity.setGendrDiv(MstCodD.getCodCd());
                    break;
                }
            }
            // 生年月日：Contact.Seinengappi__c
            mstStuEntity.setBirthd(DateUtils.parse(Contact.Seinengappi__c, Constant.DATE_FORMAT_YYYY_MM_DD_BARS));
            // 写真パス	：NULL
            mstStuEntity.setPhotPath(null);
            // 学年区分	："Contact.GakuseiKubun__r  " （GakuseiKubun__r）.学校区分コードから、学年を取得（小学校=１、中学校=２、高校生=3）
            //          Contact.GakunenKubun__r"    （Contact.GakunenKubun__r）．学年コードから、学年コードを取得（４年生の場合は「４」、全学年の場合は「９」）"
            String gakuseiKubun = Contact.GakuseiKubun__r == null ? "":Contact.GakuseiKubun__r;
            String gakunenKubun = Contact.GakunenKubun__r == null ? "":Contact.GakunenKubun__r;
            String schyDiv1 = "";
            String schyDiv2 = gakunenKubun.length()<=2?gakunenKubun:gakunenKubun.substring(0, 2);
            //Contact.GakuseiKubun__r の１文字目が "小" の場合は小学、"中" の場合は中学、"高" の場合は高校、
            switch(gakuseiKubun.length()<=1?gakuseiKubun:gakuseiKubun.substring(0, 1)){
                case "小" :
                    schyDiv1 = "小学";
                    break;
                case "中" :
                    schyDiv1 = "中学";
                    break;
                case "高" :
                    schyDiv1 = "高校";
                    break;
            }
            // Contact.GakuseiKubun__r それ以外は年長(55:年長)固定
            mstStuEntity.setSchyDiv("55");
            for(MstCodDEntity MstCodD : schyDivList){
                if(MstCodD.getCodValue().equals(schyDiv1+schyDiv2)){
                    mstStuEntity.setSchyDiv(MstCodD.getCodCd());
                    break;
                }
            }
            //QRコード:null
            mstStuEntity.setQrCod(null);
            //オリジナルCD:null
            mstStuEntity.setOriaCd(null);
            // 英語氏名:null
            mstStuEntity.setEnglishNm(null);
            // 通塾曜日区分:null
            mstStuEntity.setDayweekDiv(null);
            // メモ:null
            mstStuEntity.setMemoCont(null);
            // 担当者氏名:null
            mstStuEntity.setResptyNm(null);
            // 習い事:NULL
            mstStuEntity.setStudyCont(null);
            // 得意科目区分:NULL
            mstStuEntity.setGoodSubjtDiv(null);
            // 不得意科目区分:NULL
            mstStuEntity.setNogoodSubjtDiv(null);
            // 希望職種:NULL
            mstStuEntity.setHopeJobNm(null);
            // 希望大学:NULL
            mstStuEntity.setHopeUnityNm(null);
            // 希望学部学科:NULL
            mstStuEntity.setHopeLearnSub(null);
            // 特記事項:NULL
            mstStuEntity.setSpecCont(null);
            // 会員コード:NULL
            mstStuEntity.setMemberCd(null);
            // コース	:NULL
            mstStuEntity.setCourseId(null);
            // 入会年月日:NULL
            mstStuEntity.setAdmissionDate(null);
            // 生涯番号	:NULL
            mstStuEntity.setCareeresNum(null);
            // 作成日時:システム日時
            mstStuEntity.setCretDatime(DateUtils.getSysTimestamp());
            // 作成ユーザＩＤ:ログインユーザＩＤ
            mstStuEntity.setCretUsrId(LOGIN_ID);
            // 更新日時:システム日時
            mstStuEntity.setUpdDatime(DateUtils.getSysTimestamp());
            // 更新ユーザＩＤ:ログインユーザＩＤ
            mstStuEntity.setUpdUsrId(LOGIN_ID);
            // 削除フラグ:"「0：有効」
            mstStuEntity.setDelFlg(GakkenConstant.DEL_FLG.NO.getValue());
            mstStuDao.insert(mstStuEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("[生徒基本マスタ登録操作失敗]");
            throw new RRException(errorMsg + " [生徒基本マスタ登録操作失敗]" + e.getMessage());
        }
    }

    /**
     * 保護者基本マスタ登録
     * @param Contact
     * @param newUsrId
     * @return
     */
    private void mstGuardSave(OnLineApiDto Contact, String newUsrId, List<MstCodDEntity> reltnspList,String errorMsg){
        MstGuardEntity mstGuardEntity = new MstGuardEntity();
        // 保護者ID：取得したユーザー基本マスタ．ユーザID
        mstGuardEntity.setGuardId(newUsrId);
        // メールアドレス：Contact.HogoshaMail__c
        mstGuardEntity.setMailad(Contact.HogoshaMail__c);
        // 姓名_姓：Contact.HogoshaSei__c
        mstGuardEntity.setFlnmNm(Contact.HogoshaSei__c);
        // 姓名_名：Contact.HogoshaMei__c
        mstGuardEntity.setFlnmLnm(Contact.HogoshaMei__c);
        // 姓名_カナ姓：Contact.HogoshaSeiKana__c
        // 上記がNULLの場合、
        //　「カナ」
        if(StringUtils.isEmpty(Contact.HogoshaSeiKana__c)){
            mstGuardEntity.setFlnmKnNm("カナ");
        }else {
            //Contact.HogoshaSeiKana__cの全角カタカナへの変換
            mstGuardEntity.setFlnmKnNm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Contact.HogoshaSeiKana__c));
        }
        // 姓名_カナ名：Contact.HogoshaMeiKana__c
        // 上記がNULLの場合、
        //「シメイ」
        if(StringUtils.isEmpty(Contact.HogoshaMeiKana__c)){
            mstGuardEntity.setFlnmKnLnm("シメイ");
        }else{
            // Contact.HogoshaMeiKana__cの全角カタカナへの変換
            mstGuardEntity.setFlnmKnLnm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Contact.HogoshaMeiKana__c));
        }
        if(Contact.mailingAddress != null){
            String postalCode = StringUtils.isEmpty(Contact.mailingAddress.postalCode)?"":Contact.mailingAddress.postalCode;
            // 郵便番号_主番：Contact.MailingAddress.postalCodeの１～３桁
            // 郵便番号_枝番：Contact.MailingAddress.postalCodeの４～７桁
            if(postalCode.length()<3){
                // Contact.MailingAddress.postalCodeの１～３桁がNULLの場合、「000」
                mstGuardEntity.setPostcdMnum(postalCode.length() == 0? "000" : postalCode);
                // Contact.MailingAddress.postalCodeの１～３桁がNULLの場合、「0000」
                mstGuardEntity.setPostcdBnum("0000");
            }else{
                mstGuardEntity.setPostcdMnum(postalCode.substring(0, 3));
                mstGuardEntity.setPostcdBnum(postalCode.substring(3));
            }

            // 住所１：Contact.MailingAddress
//            String contry = StringUtils.isEmpty(Contact.mailingAddress.contry)?"":Contact.mailingAddress.contry;
            String state = StringUtils.isEmpty(Contact.mailingAddress.state)?"":Contact.mailingAddress.state;
            String city = StringUtils.isEmpty(Contact.mailingAddress.city)?"":Contact.mailingAddress.city;
            String street = StringUtils.isEmpty(Contact.mailingAddress.street)?"":Contact.mailingAddress.street;
            // 2021/5/18 huangxinliang modify start
            String adr1 = state + city;
            String adr2 = street;
            // Contact.MailingAddress.state + Contact.MailingAddress.city
            if(StringUtils.isEmpty(adr1)){
                mstGuardEntity.setAdr1("住所");
            }else{
                mstGuardEntity.setAdr1(adr1);
            }
            // Contact.MailingAddress.streetがNULLの場合、「住所」
            if(StringUtils.isEmpty(adr2)){
                mstGuardEntity.setAdr1("住所");
            }else{
                mstGuardEntity.setAdr2(adr2);
            }
        }else{
            mstGuardEntity.setPostcdMnum("000");
            mstGuardEntity.setPostcdBnum("0000");
            mstGuardEntity.setAdr1("住所");
            mstGuardEntity.setAdr2("住所");
        }
        // 2021/5/18 huangxinliang modify end
        // 電話番号：Contact.Phone("-" なし)
        mstGuardEntity.setTelnum(StringUtils.isEmpty(Contact.Phone)?Contact.Phone:Contact.Phone.replace("-",""));
        // 緊急電話番号：Contact.KinkyuRenrakusaki__c("-" なし)
        mstGuardEntity.setUrgTelnum(StringUtils.isEmpty(Contact.KinkyuRenrakusaki__c)?Contact.KinkyuRenrakusaki__c:Contact.KinkyuRenrakusaki__c.replace("-",""));
        // 続柄区分：Contact.HogoshaZokugara__c  母=1、父=2、祖父=3、祖母=4、その他=5
        //上記がNULLの場合、
        //　　「5:その他」
        mstGuardEntity.setReltnspDiv("5");
        for(MstCodDEntity MstCodD : reltnspList){
            if(MstCodD.getCodValue().equals(Contact.HogoshaZokugara__c)){
                mstGuardEntity.setReltnspDiv(MstCodD.getCodCd());
                break;
            }
        }
        // 作成日時：システム日時
        mstGuardEntity.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ：ログインユーザＩＤ
        mstGuardEntity.setCretUsrId(LOGIN_ID);
        // 更新日時：システム日時
        mstGuardEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ：ログインユーザＩＤ
        mstGuardEntity.setUpdUsrId(LOGIN_ID);
        // 削除フラグ
        mstGuardEntity.setDelFlg(GakkenConstant.DEL_FLG.NO.getValue());
        try {
            mstGuardDao.insert(mstGuardEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("[保護者基本マスタ登録操作失敗]");
            throw new RRException(errorMsg + " [保護者基本マスタ登録操作失敗]" + e.getMessage());
        }
    }

    /**
     * 保護者基本マスタ更新
     * @param Contact
     * @param reltnspList
     * @return
     */
    private void mstGuardUp (OnLineApiDto Contact, List<MstCodDEntity> reltnspList, String guardId,String errorMsg){
        Map<String, Object> entity = new HashMap<>(16);
        try {
            // 取得したユーザー基本マスタ．ユーザID
            entity.put("guardId", guardId);
            // 保護者ID	:取得したユーザー基本マスタ．ユーザID
            // メールアドレス:Contact.HogoshaMail__c
            entity.put("mailad", Contact.HogoshaMail__c);
            // 姓名_姓:Contact.HogoshaSei__c
            entity.put("flnmNm", Contact.HogoshaSei__c);
            // 姓名_名:Contact.HogoshaMei__c
            entity.put("flnmLnm", Contact.HogoshaMei__c);
            // 姓名_カナ姓：Contact.HogoshaSeiKana__c
            // 上記がNULLの場合、
            //　「カナ」
            if(StringUtils.isEmpty(Contact.HogoshaSeiKana__c)){
                entity.put("flnmKnNm", "カナ");
            }else {
                //Contact.HogoshaSeiKana__cの全角カタカナへの変換
                entity.put("flnmKnNm", HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Contact.HogoshaSeiKana__c));
            }
            // 姓名_カナ名：Contact.HogoshaMeiKana__c
            // 上記がNULLの場合、
            //「シメイ」
            if(StringUtils.isEmpty(Contact.HogoshaMeiKana__c)){
                entity.put("flnmKnLnm", "シメイ");
            }else{
                // Contact.HogoshaMeiKana__cの全角カタカナへの変換
                entity.put("flnmKnLnm", HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Contact.HogoshaMeiKana__c));
            }
            if(Contact.mailingAddress != null){
                String postalCode = StringUtils.isEmpty(Contact.mailingAddress.postalCode)?"":Contact.mailingAddress.postalCode;
                // 郵便番号_主番：Contact.MailingAddress.postalCodeの１～３桁
                // 郵便番号_枝番：Contact.MailingAddress.postalCodeの４～７桁
                if(postalCode.length()<3){
                    // Contact.MailingAddress.postalCodeの１～３桁がNULLの場合、「000」
                    entity.put("postcdMnum", postalCode.length() == 0? "000" : postalCode);
                    entity.put("postcdBnum", "0000");
                }else{
                    entity.put("postcdMnum",postalCode.substring(0, 3));
                    entity.put("postcdBnum",postalCode.substring(3));
                }
                // 住所１:Contact.MailingAddress
//                String contry = StringUtils.isEmpty(Contact.mailingAddress.contry)?"":Contact.mailingAddress.contry;
                String state = StringUtils.isEmpty(Contact.mailingAddress.state)?"":Contact.mailingAddress.state;
                String city = StringUtils.isEmpty(Contact.mailingAddress.city)?"":Contact.mailingAddress.city;
                String street = StringUtils.isEmpty(Contact.mailingAddress.street)?"":Contact.mailingAddress.street;
                // 2021/5/18 huangxinliang modify start
                String adr1 = state + city;
                String adr2 = street;
                // Contact.MailingAddress.state + Contact.MailingAddress.city
                if(StringUtils.isEmpty(adr1)){
                    entity.put("adr1","住所");
                }else{
                    entity.put("adr1",adr1);
                }
                // Contact.MailingAddress.streetがNULLの場合、「住所」
                if(StringUtils.isEmpty(adr2)){
                    entity.put("adr2","住所");
                }else{
                    entity.put("adr2",adr2);
                }
            }else{
                entity.put("postcdMnum","000");
                entity.put("postcdBnum", "0000");
                entity.put("adr1","住所");
                entity.put("adr2","住所");
            }
            // 2021/5/18 huangxinliang modify end

            // 電話番号:Contact.Phone("-" なし)
            entity.put("telnum", StringUtils.isEmpty(Contact.Phone)?Contact.Phone:Contact.Phone.replace("-",""));
            // 緊急電話番号:Contact.KinkyuRenrakusaki__c("-" なし)
            entity.put("urgTelnum", StringUtils.isEmpty(Contact.KinkyuRenrakusaki__c)?Contact.KinkyuRenrakusaki__c:Contact.KinkyuRenrakusaki__c.replace("-",""));
            // 続柄区分:Contact.HogoshaZokugara__c
            //上記がNULLの場合、
            //　　「5:その他」
            String reltnspDiv = "5";
            for(MstCodDEntity MstCodD : reltnspList){
                if(MstCodD.getCodValue().equals(Contact.HogoshaZokugara__c)){
                    reltnspDiv = MstCodD.getCodCd();
                    break;
                }
            }
            entity.put("reltnspDiv", reltnspDiv);
            // 更新日時:システム日時
            entity.put("updDatime", DateUtils.getSysTimestamp());
            // 更新ユーザＩＤ:ログインユーザＩＤ
            entity.put("updUsrId", LOGIN_ID);
            onLineApiDao.updateGuardInfo(entity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("[保護者基本マスタ更新操作失敗]");
            throw new RRException(errorMsg + " [保護者基本マスタ更新操作失敗]" + e.getMessage());
        }
    }

    /**
     * 生徒基本マスタ更新
     * @param Contact
     * @param schyDivList
     * @param genderDivList
     * @return
     */
    private void mstStudUp (OnLineApiDto Contact, List<MstCodDEntity> schyDivList, List<MstCodDEntity> genderDivList, String stuId,String guardId,String errorMsg){
        Map<String, Object> entity = new HashMap<>(16);
        try{
            // 生徒ID：取得したユーザー基本マスタ．ユーザID
            entity.put("stuId", stuId);
            // 姓名_姓：Contact.GakkoMei__c
            entity.put("flnmNm", Contact.LastName);
            // 姓名_名：Contact.LastName
            entity.put("flnmLnm", Contact.FirstName);
            // 姓名_カナ姓 ：Contact.SeiKana__cの全角カタカナへの変換
            //上記がNULLの場合、
            //　　「カナ」
            if(StringUtils.isEmpty(Contact.SeiKana__c)){
                entity.put("flnmKnNm", "カナ");
            }else{
                entity.put("flnmKnNm", HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Contact.SeiKana__c));
            }
            // 姓名_カナ名：Contact.MeiKana__cの全角カタカナへの変換
            //上記がNULLの場合、
            //　　「シメイ」
            if(StringUtils.isEmpty(Contact.MeiKana__c)){
                entity.put("flnmKnLnm", "シメイ");
            }else{
                entity.put("flnmKnLnm", HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(Contact.MeiKana__c));
            }
            // 学年区分："Contact.GakuseiKubun__r
            //        ：Contact.GakunenKubun__r"
            String gakuseiKubun = Contact.GakuseiKubun__r == null ? "":Contact.GakuseiKubun__r;
            String gakunenKubun = Contact.GakunenKubun__r == null ? "":Contact.GakunenKubun__r;
            String schyDivContact1 = "";
            String schyDivContact2 = gakunenKubun.length()<=2?gakunenKubun:gakunenKubun.substring(0, 2);
            //Contact.GakuseiKubun__r の１文字目が "小" の場合は小学、"中" の場合は中学、"高" の場合は高校、
            switch(gakuseiKubun.length()<=1?gakuseiKubun:gakuseiKubun.substring(0, 1)){
                case "小" :
                    schyDivContact1 = "小学";
                    break;
                case "中" :
                    schyDivContact1 = "中学";
                    break;
                case "高" :
                    schyDivContact1 = "高校";
                    break;
            }
            // Contact.GakuseiKubun__r それ以外は年長(55:年長)固定
            String schyDiv = "55";
            for(MstCodDEntity MstCodD : schyDivList){
                if(MstCodD.getCodValue().equals(schyDivContact1+schyDivContact2)){
                    schyDiv = MstCodD.getCodCd();
                    break;
                }
            }
            entity.put("schyDiv", schyDiv);
            // 学校名：Contact.GakkoMei__c
            entity.put("sch", Contact.GakkoMei__c);
            // 保護者ID：取得したユーザー基本マスタ・ユーザID（保護者）
            entity.put("guardId", guardId);
            // 性別区分：Contact.Seibetsu__c"男" の場合 1 "女" の場合 2 未設定または上記以外を 9（その他） としてください
            String gendrDiv = "9";
            for(MstCodDEntity MstCodD : genderDivList){
                if(MstCodD.getCodValue().equals(Contact.Seibetsu__c)){
                    gendrDiv = MstCodD.getCodCd();
                    break;
                }
            }
            entity.put("gendrDiv", gendrDiv);
            // 生年月日：Contact.Seinengappi__c
            entity.put("birthd", DateUtils.parse(Contact.Seinengappi__c, Constant.DATE_FORMAT_YYYY_MM_DD_BARS));
            // 更新日時	：システム日時
            entity.put("updDatime", DateUtils.getSysTimestamp());
            // 更新ユーザＩＤ：ログインユーザＩＤ
            entity.put("updUsrId", LOGIN_ID);
            onLineApiDao.updateStudentInfo(entity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("[生徒基本マスタ更新操作失敗]");
            throw new RRException(errorMsg + " [生徒基本マスタ更新操作失敗]" + e.getMessage());
        }
    }

    /**
     * ユーザ基本マスタ更新
     * @param Contact
     * @param role
     * @return
     */
    private void mstUsrdUp (OnLineApiDto Contact, String role, String groudId,String errorMsg){
        Map<String, Object> entity = new HashMap<>(16);
        // ユーザステータス:Contact.TaijukuDate__cがNuLLなら、１をセット
        //        Contact.TaijukuDate__cがNull以外なら、０をセット
        entity.put("usrSts", Contact.TaijukuDate__c == null?"1":"0");
        // 更新日時	：システム日時
        entity.put("updDatime", DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ：ログインユーザＩＤ
        entity.put("updUsrId", LOGIN_ID);
        // 変更後ユーザーID:Contact.SeitoBango2__c
        entity.put("afterUsrid", Contact.SeitoBango2__c);
        // 生徒基本マスタ.保護者ID
        entity.put("usrId", groudId);
        entity.put("role", role);
        try{
            onLineApiDao.updateUsrInfo(entity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("[ユーザ基本マスタ更新操作失敗]");
            throw new RRException(errorMsg + " [ユーザ基本マスタ更新操作失敗]" + e.getMessage());
        }
    }

}
