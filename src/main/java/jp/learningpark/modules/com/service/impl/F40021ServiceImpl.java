/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.gakken.id2.V2GakkenIDPrivilegeSvcStub;
import jp.learningpark.framework.gakkenID.GakkenIdAPI;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.F40021Dao;
import jp.learningpark.modules.com.service.F40021Service;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstManagerDao;
import jp.learningpark.modules.common.entity.MstGuardEntity;
import jp.learningpark.modules.common.entity.MstManagerEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstGuardService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.guard.service.F30002Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>利用規約同意画面</p>
 * <p>ServiceImpl</p>
 * <p></p>
 *
 * @author NWT : wang <br />
 * 変更履歴 <br />
 * 2020/8/7 : wang: 新規<br />
 * @version 8.0
 */

@Transactional
@Service
public class F40021ServiceImpl implements F40021Service {

    /**
     * mstUsrService
     */
    @Autowired
    MstUsrService mstUsrService;

    @Autowired
    MstManagerDao mstManagerDao;

    /**
     * mstGuardService
     */
    @Autowired
    MstGuardService mstGuardService;

    @Autowired
    GakkenIdAPI gakkenIdAPI;

    @Autowired
    F40021Dao f40021Dao;

    @Autowired
    F30002Service f30002Service;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(F40021ServiceImpl.class);

    /**
     * ・規約フラグを更新する。
     * upDateid()
     */
    @Override
    public R upDateId(Boolean gidFlg, Boolean manaFlg) {
        Logger logger = LoggerFactory.getLogger(getClass());
        //        ユーザＩＤ ＝ セッションデータ．ユーザＩＤ
        String afterUsrId = ShiroUtils.getUserEntity().getAfterUsrId();
        String tchCd = null;
        if (StringUtils.equals("1",ShiroUtils.getUserEntity().getRoleDiv().trim())){
            //指導者管理コード取得
            tchCd = mstManagerDao.selectOne(new QueryWrapper<MstManagerEntity>().eq("mgr_id",ShiroUtils.getUserEntity().getUsrId()).eq("del_flg",0)).getTchCd();
        }
        List<MstUsrEntity> mstUsrEntities = new ArrayList<>();
        //NWT　楊　2021/07/01　MANAMIRU1-699 --> 727　Edit　Start
        //指導者管理コード
        if (StringUtils.equals("1",ShiroUtils.getUserEntity().getGidFlg())){
            if (StringUtils.isNotBlank(ShiroUtils.getUserEntity().getGidpk())){
                mstUsrEntities = mstUsrService.list(new QueryWrapper<MstUsrEntity>().eq("gidpk",ShiroUtils.getUserEntity().getGidpk()).eq("del_flg",0));
            }if (StringUtils.isNotBlank(tchCd) && mstUsrEntities.size() == 0){
                mstUsrEntities = f40021Dao.getUsrListByTchCd(tchCd);
            }
        }else {
            //NWT　楊　2021/07/15　MANAMIRU1-727　Edit　Start
            // ユーザ基本マスタ情報取得
            mstUsrEntities = mstUsrService.list(new QueryWrapper<MstUsrEntity>().eq("after_usr_id", afterUsrId).eq("del_flg", 0));
            //NWT　楊　2021/07/15　MANAMIRU1-727　Edit　Start
        }
        //NWT　楊　2021/07/01　MANAMIRU1-699 --> 727　Edit　End
        List<String> guardIds = new ArrayList<>();
        //	・	ユーザ基本マスタ．初回登録フラグ ＝　1：初回以外
        for (MstUsrEntity mstUsrEntity : mstUsrEntities) {
            //  ・	ユーザ基本マスタ．GakkenID規約フラグ ＝　1：確認済
            if (gidFlg != null && gidFlg) {
                mstUsrEntity.setGidRuleFlg("1");
            }
            //  ・	ユーザ基本マスタ．マナミル規約フラグ ＝　1：確認済
            if (manaFlg != null && manaFlg) {
                mstUsrEntity.setManaRuleFlg("1");
            }
            //NWT　楊　2021/06/25　MANAMIRU1-709.5　Edit　Start
            if (StringUtils.equals("1", mstUsrEntity.getRoleDiv().trim()) || StringUtils.equals("2", mstUsrEntity.getRoleDiv().trim())){
                mstUsrEntity.setFstLoginFlg("1");
            }
            //NWT　楊　2021/06/25　MANAMIRU1-709.5　Edit　End
            //	・	ユーザ基本マスタ．個人情報保護規約フラグ ＝　1：確認済
            //            mstUsrEntity.setPerlInfoRuleFlg("1");
            //	・	ユーザ基本マスタ．更新日時 ＝　システム日時
            mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //	・	ユーザ基本マスタ．メールアドレス ＝　ログインユーザＩＤ
            mstUsrEntity.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
            mstUsrService.updateById(mstUsrEntity);
            if (StringUtils.equals("3", mstUsrEntity.getRoleDiv().trim())){
                guardIds.add(mstUsrEntity.getUsrId());
            }
        }
        String roleDiv = ShiroUtils.getUserEntity().getRoleDiv().trim();
        R r = R.ok();
        if (StringUtils.equals("3", roleDiv)){
            MstGuardEntity guardEntity = mstGuardService.getOne(new QueryWrapper<MstGuardEntity>().eq("guard_id", ShiroUtils.getUserEntity().getUsrId()));
            MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", ShiroUtils.getUserEntity().getUsrId()));
            String guards = String.join(",", guardIds);
            Integer count = f30002Service.getStudents(guards).size();
            String pwUpFlg = mstUsrEntity.getPwUpFlg();
            r.put("emptyMail", StringUtils.isEmpty(guardEntity.getMailad())).put("count",count).put("pwUpFlg",pwUpFlg);
        }
        /* 2020/01/06 V8.0(ph2) cuikailin add start */
        do {
            if (StringUtils.equals("0", ShiroUtils.getUserEntity().getGidFlg())
                    || StringUtils.isEmpty(ShiroUtils.getUserEntity().getGidpk())){
                break;
            }
            //NWT　楊　2021/06/28　MANAMIRU1-709.6 --> 1-718　Delete　Start
            //NWT　楊　2021/06/28　MANAMIRU1-709.6 --> 1-718　Delete　End
            try {
                String gidPK = ShiroUtils.getUserEntity().getGidpk();
                // NWT　楊　2021/07/06　MANAMIRU1-719　Edit Start
                log.info("---------------------------push gidpk: " + gidPK + "---------------------------");
                if (StringUtils.isNotBlank(gidPK)){
                    V2GakkenIDPrivilegeSvcStub.GakkenID[] gakkenIDs = new V2GakkenIDPrivilegeSvcStub.GakkenID[1];
                    gakkenIDs[0] = new V2GakkenIDPrivilegeSvcStub.GakkenID();
                    //GIDPK
                    gakkenIDs[0].setGidpk(gidPK);
                    //データ利用許諾
                    short agreeFlg = 1;
                    gakkenIDs[0].setAgree_flg(agreeFlg);
                    log.info("---------------------------push agree_flg: " + agreeFlg + "---------------------------");
                    V2GakkenIDPrivilegeSvcStub.GakkenIDselect gakkenIdSelect = new V2GakkenIDPrivilegeSvcStub.GakkenIDselect();
                    //GIDPK更新する
                    gakkenIdSelect.setGidpk(true);
                    //データ利用許諾更新する
                    gakkenIdSelect.setAgree_flg(true);
                    //更新する
                    gakkenIdAPI.updateGakkenIDBulk(gakkenIDs, gakkenIdSelect);
                }else {
                    logger.error("gidpkは空です");
                }
                // NWT　楊　2021/07/06　MANAMIRU1-719　Edit End
            } catch (Exception e) {
                log.error(e.getMessage());
                return R.error();
            }
        }while (false);
        /* 2020/01/06 V8.0(ph2) cuikailin add start */

        return r;
    }

    /**
     * データを取得する
     * @return
     */
    @Override
    public R getDate() {
        String usrId = ShiroUtils.getUserId();
        //ユーザ基本マスタを元に、GIDフラグ、GakkenID規約フラグ、マナミル規約フラグとロール区分を取得する。
        MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", usrId).eq("del_flg", 0));
        // ユーザ基本マスタ情報取得
        mstUsrEntity.getUpdDatime();
//        更新
        String updDatime = DateUtils.format(mstUsrEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);

        String gidFlg = mstUsrEntity.getGidFlg();
        String gidRuleFlg = mstUsrEntity.getGidRuleFlg();
        String manaRuleFlg = mstUsrEntity.getManaRuleFlg();
        String roleDiv = mstUsrEntity.getRoleDiv().trim();
        return R.ok().put("updDatime", updDatime).put("gidFlg", gidFlg).put("gidRuleFlg", gidRuleFlg).put("manaRuleFlg", manaRuleFlg).put("roleDiv", roleDiv);
    }
}
