package jp.learningpark.modules.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.gakken.id2.V2GakkenIDPrivilegeSvcStub;
import jp.learningpark.framework.gakkenID.GakkenIdAPI;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.F40007Dao;
import jp.learningpark.modules.com.service.F40007Service;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstUsrDao;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>F40007_パスワード再設定画面 ServiceImpl</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2018/12/29 : xiong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F40007ServiceImpl implements F40007Service {

    /**
     * F40007_パスワード再設定画面 Dao
     */
    @Autowired
    private F40007Dao f40007Dao;

    /**
     * ユーザ基本マスタ Dao
     */
    @Autowired
    private MstUsrDao mstUsrDao;

    /**
     * gakkenIdAPI
     */
    @Autowired
    GakkenIdAPI gakkenIdAPI;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public R resetPwd(Map<String,String> userInfo) {
        //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　Start
        // 2021/10/11　MANAMIRU1-776 cuikl　Edit   Start
        String afterUserId = (String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_AFTER_USR_ID_FOR_RESET_PASSWORD);
        String password = userInfo.get("password");
        // 2021/10/25 manamiru1-776 cuikl edit start
        String gidFlag = (String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GID_FLG_FOR_RESET_PASSWORD);
        // 2021/10/25 manamiru1-776 cuikl edit end
        if (StringUtils.equals(gidFlag, "0")){
            /* 2021/10/27 manamiru1-776 cuikl edit start */
            // クエリＩＤ
            List<MstUsrEntity> mstUsrEntityList = f40007Dao.selectById(afterUserId, gidFlag);
            for (MstUsrEntity mstUsrEntity : mstUsrEntityList) {
                //を暗号化する
                String pwd = ShiroUtils.sha256(password, mstUsrEntity.getUsrId());
                // 更新項目
                mstUsrEntity.setUsrPassword(pwd);
                mstUsrEntity.setPwUpFlg("1");
                if ("1".equals(mstUsrEntity.getLockFlg())) {
                    mstUsrEntity.setLockFlg("0");
                    mstUsrEntity.setErrorCount(0);
                }
                mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
                mstUsrEntity.setUpdUsrId(afterUserId);
                mstUsrDao.updateById(mstUsrEntity);
            }
            /* 2021/10/27 manamiru1-776 cuikl edit end */
            //処理を継続する
        }else if (StringUtils.equals(gidFlag, "1")){
            /* 2021/10/27 manamiru1-776 cuikl edit start */
            String gidPK = (String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GIDPK_FOR_RESET_PASSWORD);
            if (StringUtils.isEmpty(gidPK)) {
                logger.info("gidPKが空のためパスワード更新をスキップします[after_usr_id=" + afterUserId + "]");
            } else {
                // クエリＩＤ
                List<MstUsrEntity> mstUsrEntityList = mstUsrDao.selectList(new QueryWrapper<MstUsrEntity>()
                                .and(wrapper -> wrapper.eq("gidpk", gidPK)
                                        .eq("gid_flg", gidFlag).eq("del_flg", 0)));
                // 2021/10/25 manamiru1-776 cuikl edit end
                for (MstUsrEntity mstUsrEntity : mstUsrEntityList) {
                    // 更新項目
                    mstUsrEntity.setUsrPassword(null);
                    mstUsrEntity.setPwUpFlg("1");
                    if ("1".equals(mstUsrEntity.getLockFlg())) {
                        mstUsrEntity.setLockFlg("0");
                        mstUsrEntity.setErrorCount(0);
                    }
                    mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    mstUsrEntity.setUpdUsrId(afterUserId);
                    mstUsrDao.updateById(mstUsrEntity);
                }
            }
            try {
            /* 2021/10/27 manamiru1-776 cuikl edit end */
        // 2021/10/11　MANAMIRU1-776 cuikl　Edit   End
                // NWT　楊　2021/07/06　MANAMIRU1-719　Edit Start
                logger.info("---------------------------push gidpk: " + gidPK + "---------------------------");
                if (StringUtils.isNotBlank(gidPK)){
                    V2GakkenIDPrivilegeSvcStub.GakkenIDselect gakkenIDselect = new V2GakkenIDPrivilegeSvcStub.GakkenIDselect();
                    V2GakkenIDPrivilegeSvcStub.GakkenID[] gakkenIDS = new V2GakkenIDPrivilegeSvcStub.GakkenID[1];
                    gakkenIDS[0] = new V2GakkenIDPrivilegeSvcStub.GakkenID();
                    gakkenIDS[0].setGidpk(gidPK);
                    gakkenIDS[0].setPass(password);
                    gakkenIDselect.setGidpk(true);
                    gakkenIDselect.setPass(true);
                    gakkenIdAPI.updateGakkenIDBulk(gakkenIDS, gakkenIDselect);
                }else {
                    logger.error("gidpkは空です");
                }
                // NWT　楊　2021/07/06　MANAMIRU1-719　Edit Start
            }catch (Exception e){
                logger.error(e.getMessage());
//                GakkenConstant.EQUIPMENT_DIV equipment_div  = (GakkenConstant.EQUIPMENT_DIV) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV);
                return R.error(MessageUtils.getMessage("MSGCOMD0160"));
            }
        }
        // 除去するセッションデータ．ユーザーＩＤ
        ShiroUtils.getSession().removeAttribute("nowUserId");
        // 除去するセッションデータ．変更後ユーザＩＤ
        ShiroUtils.getSession().removeAttribute(GakkenConstant.SESSION_AFTER_USR_ID_FOR_RESET_PASSWORD);
        // 除去するセッションデータ．学研IDプライマリキー
        ShiroUtils.getSession().removeAttribute(GakkenConstant.SESSION_GIDPK_FOR_RESET_PASSWORD);
        // 除去するセッションデータ．GIDフラグ
        ShiroUtils.getSession().removeAttribute(GakkenConstant.SESSION_GID_FLG_FOR_RESET_PASSWORD);
        //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　End
        return R.ok().put("success", true);
    }
}
