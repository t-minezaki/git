/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.gakken.id2.V2GakkenIDPrivilegeSvcStub;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.gakkenID.GakkenIdAPI;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.dao.MstManagerDao;
import jp.learningpark.modules.common.dao.MstUsrDao;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.manager.dao.F00008Dao;
import jp.learningpark.modules.manager.service.F00008Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>F00008_パスワード初期化 ServiceImpl</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2018/12/27 : xiong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F00008ServiceImpl implements F00008Service {

    /**
     * F00008 Dao
     */
    @Autowired
    private F00008Dao f00008Dao;

    /**
     * ユーザ基本マスタ Dao
     */
    @Autowired
    private MstUsrDao mstUsrDao;

    @Autowired
    private MstManagerDao mstManagerDao;

    /**
     * gakkenIdAPI
     */
    @Autowired
    GakkenIdAPI gakkenIdAPI;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * パスワード初期化
     *
     * @param afterUserId
     * @return
     */
    @Override
    public R initialPwd(String afterUserId) {

        // 管理者ID
        String managerId = ShiroUtils.getUserId().toString();
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();

        MstUsrEntity mstUsrEntity = f00008Dao.selectByUserId(afterUserId, orgId);

        if (mstUsrEntity == null) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0017", "利用者"));
        }
        else {
            // パスワード初期化
            String pwd = ShiroUtils.stringToAscii(mstUsrEntity.getUsrId());
            //updateTime
            mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //updateUserId
            mstUsrEntity.setUpdUsrId(managerId);
            //userPassword
            mstUsrEntity.setUsrPassword(ShiroUtils.sha256(pwd, mstUsrEntity.getUsrId()));
            //pwUpFlg
            mstUsrEntity.setPwUpFlg("0");
            //ロック解除フラグ
            mstUsrEntity.setLockFlg("0");
            mstUsrEntity.setErrorCount(0);

            mstUsrDao.updateById(mstUsrEntity);

            if (StringUtils.equals(mstUsrEntity.getGidFlg(), "0")){
                //処理を継続する
            }else if (StringUtils.equals(mstUsrEntity.getGidFlg(), "1")){
                try {
                    do {
                        String gidPK = mstUsrEntity.getGidpk();
                        // NWT　楊　2021/07/06　MANAMIRU1-719　Edit Start
                        logger.info("---------------------------push gidpk: " + gidPK + "---------------------------");
//                        if (StringUtils.isEmpty(gidPK)){
//                            MstManagerEntity mstManagerEntity = mstManagerDao.selectOne(new QueryWrapper<MstManagerEntity>().eq("mgr_id", mstUsrEntity.getUsrId()));
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
                            V2GakkenIDPrivilegeSvcStub.GakkenID[] gakkenIDS = new V2GakkenIDPrivilegeSvcStub.GakkenID[1];
                            gakkenIDS[0] = new V2GakkenIDPrivilegeSvcStub.GakkenID();
                            gakkenIDS[0].setGidpk(gidPK);
                            gakkenIDS[0].setPass(ShiroUtils.stringToAscii(mstUsrEntity.getUsrId()));
                            gakkenIDselect.setGidpk(true);
                            gakkenIDselect.setPass(true);
                            gakkenIdAPI.updateGakkenIDBulk( gakkenIDS, gakkenIDselect);
                        }else {
                            logger.error("gidpkは空です");
                        }
                        // NWT　楊　2021/07/06　MANAMIRU1-719　Edit End
                    }
                    while (false);

                }catch (Exception e){
                    logger.error(e.getMessage());
                    throw new RRException(MessageUtils.getMessage("MSGCOMD0160"));
                }
            }

            return R.ok().put("success", true).put("newPwd", pwd);
        }
    }
}
