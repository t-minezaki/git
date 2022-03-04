package jp.learningpark.modules.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.gakken.id2.V2GakkenIDPrivilegeSvcStub;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.gakkenID.GakkenIdAPI;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.F40008Dao;
import jp.learningpark.modules.com.service.F40008Service;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstUsrDao;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.guard.dao.F30001Dao;
import jp.learningpark.modules.guard.dto.F30001Dto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>F40008_パスワード変更画面 ServiceImpl</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/03 : xiong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F40008ServiceImpl implements F40008Service {

    /**
     * F40008_パスワード再設定画面 Dao
     */
    @Autowired
    private F40008Dao f40008Dao;

    @Autowired
    GakkenIdAPI gakkenIdAPI;
    /**
     * ユーザ基本マスタ Dao
     */
    @Autowired
    private MstUsrDao mstUsrDao;

    @Autowired
    private F30001Dao f30001Dao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     * @param newPwd 新しいパスワード
     * @param oldPwd 古いパスワード
     * @param imageId 変更後ユーザＩＤ
     * @param updDatime 更新日時
     * @param gidFlg
     * @return
     */
    @Override
    // 2021/09/22 manamiru1-772 cuikl del
    public R resetPwd(String newPwd, String oldPwd, String imageId, String updDatime, String gidFlg) {
        // セッションデータ．ユーザーＩＤ
        String nowID = (String) ShiroUtils.getUserId();
        // クエリＩＤ
        MstUsrEntity mstUsrEntity = f40008Dao.selectById(nowID);
        if (mstUsrEntity == null){
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        // 取得条件
        List<MstUsrEntity> mstUsrEntities = f40008Dao.selectUsr(ShiroUtils.getBrandcd(),mstUsrEntity.getAfterUsrId());
        if (!mstUsrEntity.getFstLoginFlg().equals("0")) {
            //旧パスワードの確認
            boolean pwdCheck = false;
            for (MstUsrEntity usrEntity : mstUsrEntities) {
                if (StringUtils.equals(usrEntity.getUsrPassword(), ShiroUtils.sha256(oldPwd, usrEntity.getUsrId()))) {
                    pwdCheck = true;
                    break;
                }
            }
            if (!pwdCheck) {
                return R.error(MessageUtils.getMessage("MSGCOMN0058"));
            }
        }

        // nowUpdTime
        String nowUpdTime = DateUtils.format(mstUsrDao.selectById(mstUsrEntity.getId()).getUpdDatime(),Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
        if (!StringUtils.equals(nowUpdTime, updDatime)) {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        // IDは空白の場合
        if(imageId.isEmpty()){
                imageId = mstUsrEntity.getAfterUsrId();
        }
        // 変更後ユーザＩＤの入力チェック
        if (!imageId.equals(mstUsrEntity.getAfterUsrId())){
            // 変更後ユーザＩＤの重複チェック
            if (f40008Dao.selectCountById(imageId) >= 1){
                return R.error(MessageUtils.getMessage("MSGCOMN0184","入力したユーザーIDは既に利用されています。別のユーザーIDを"));
            }
        }
        ShiroUtils.getUserEntity().setFstLoginFlg("1");
        if (StringUtils.isNotBlank(newPwd)) {
            ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_PASSWORD, newPwd);
            ShiroUtils.getUserEntity().setUsrPassword(ShiroUtils.sha256(newPwd, mstUsrEntity.getUsrId()));
        }
        for (MstUsrEntity usrEntity : mstUsrEntities) {
            // 更新項目
            if (StringUtils.isNotBlank(newPwd)) {
                // を暗号化する
                String nPwd = ShiroUtils.sha256(newPwd, usrEntity.getUsrId());
                usrEntity.setUsrPassword(nPwd);
                usrEntity.setPwUpFlg("1");
            }
            usrEntity.setFstLoginFlg("1");
            usrEntity.setAfterUsrId(imageId);
            ShiroUtils.getUserEntity().setAfterUsrId(imageId);
            usrEntity.setUpdDatime(DateUtils.getSysTimestamp());
            usrEntity.setUpdUsrId(imageId);
            mstUsrDao.updateById(usrEntity);
        }
        if (StringUtils.equals(gidFlg, "0")){
            //処理を継続する
        }else if (StringUtils.equals(gidFlg, "1")){
            try {
                //NWT　楊　2021/06/21　MANAMIRU1-698　Edit　Start
                String gidPK = ShiroUtils.getUserEntity().getGidpk();
                // NWT　楊　2021/07/06　MANAMIRU1-719　Edit Start
                logger.info("----------------gidPK:" + gidPK + "----------------");
                if (StringUtils.isNotBlank(gidPK)){
                    V2GakkenIDPrivilegeSvcStub.GakkenIDselect gakkenIDselect = new V2GakkenIDPrivilegeSvcStub.GakkenIDselect();
                    V2GakkenIDPrivilegeSvcStub.GakkenID[] gakkenIDS = new V2GakkenIDPrivilegeSvcStub.GakkenID[1];
                    gakkenIDS[0] = new V2GakkenIDPrivilegeSvcStub.GakkenID();
                    gakkenIDS[0].setGidpk(gidPK);
                    gakkenIDS[0].setPass(newPwd);
                    gakkenIDselect.setGidpk(true);
                    gakkenIDselect.setPass(true);
                    gakkenIdAPI.updateGakkenIDBulk(gakkenIDS, gakkenIDselect);
                }else {
                    logger.error("gidpkは空です");
                }
                // NWT　楊　2021/07/06　MANAMIRU1-719　Edit End
                //NWT　楊　2021/06/21　MANAMIRU1-698　Edit　End
            }catch (Exception e){
                logger.error(e.getMessage());
                throw new RRException(MessageUtils.getMessage("MSGCOMD0160"));
            }
        }
        return R.ok();
    }

    /**
     *
     * @return
     */
    @Override
    public R getInit() {
        // セッションデータ．ユーザーＩＤ
        String nowID = (String) ShiroUtils.getUserId();
        // クエリＩＤ
        MstUsrEntity mstUsrEntity = f40008Dao.selectById(nowID);
        // ロール区分
        if (mstUsrEntity != null){
            // 初回登録フラグ
            String firstLogin = mstUsrEntity.getFstLoginFlg();
            // ロール区分
            String role = mstUsrEntity.getRoleDiv().trim();
            //更新日時
            String updateTime = DateUtils.format(mstUsrEntity.getUpdDatime(),Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            /* 2021/03/09 manamiru4-33 add start */
            R r = R.ok();
            if (StringUtils.equals(role,"1")){
                // 指導者管理コードを取得
                Integer tchCod = f40008Dao.selectTchCod(mstUsrEntity.getUsrId());
                r.put("tchCod",tchCod);
            }
            /* 2021/03/09 manamiru4-33 add start */
            // 初回登録フラグ.生徒の場合
            if("0".equals(firstLogin) && StringUtils.equals(role,"4")){
                return r.put("first",true).put("display",true).put("afterId",mstUsrEntity.getAfterUsrId()).put("updDatime",updateTime).put("mstUsrEntity",mstUsrEntity);
            }
            if ("0".equals(firstLogin)){
                return r.put("first",true).put("afterId",mstUsrEntity.getAfterUsrId()).put("updDatime",updateTime).put("mstUsrEntity",mstUsrEntity);
            }
            if (StringUtils.equals(role,"4")){
                return r.put("display",true).put("afterId",mstUsrEntity.getAfterUsrId()).put("updDatime",updateTime).put("mstUsrEntity",mstUsrEntity);
            }
            return r.put("afterId",mstUsrEntity.getAfterUsrId()).put("updDatime",updateTime).put("mstUsrEntity",mstUsrEntity);
        }else{
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
    }

    /**
     *
     * @param afterId
     * @return
     */
    @Override
    public R updateStatus(String afterId, String updDatime){
        // セッションデータ．ユーザーＩＤ
        String nowID = (String) ShiroUtils.getUserId();
        // クエリＩＤ
        MstUsrEntity mstUsrEntity = f40008Dao.selectById(nowID);
        List<MstUsrEntity> mstUsrEntities = mstUsrDao.selectList(new QueryWrapper<MstUsrEntity>().eq("after_usr_id",afterId).eq("del_flg",0));
        // nowUpdTime
        String nowUpdTime = DateUtils.format(mstUsrDao.selectById(mstUsrEntity.getId()).getUpdDatime(),Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS) ;
        if (nowUpdTime.equals(updDatime)){
            for (MstUsrEntity dto: mstUsrEntities) {
                dto.setFstLoginFlg("1");
                mstUsrDao.updateById(dto);
            }
        }else {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        return R.ok();
    }

    /**
     *
     * @param afterId
     * @return
     */
    @Override
    public R getStuNumber(String afterId){
        F30001Dto f30001Dto = f30001Dao.stuNumber(afterId);
        String brandCd = ShiroUtils.getBrandcd();
        if (f30001Dto.getCount() == 1){
            String stuId = f30001Dto.getStuId();
            ShiroUtils.setSessionAttribute(GakkenConstant.STU_ID, stuId);
            Integer crmschId = f30001Dao.getCrmschId(stuId);
            //NWT　楊　2021/06/25　MANAMIRU1-709.1　Edit　Start
            return R.ok().put("f30001Dto",f30001Dto).put("crmschId",crmschId).put("brandCd",brandCd.toUpperCase());
            //NWT　楊　2021/06/25　MANAMIRU1-709.1　Edit　End
        }
        return R.ok().put("f30001Dto",f30001Dto).put("brandCd",brandCd.toUpperCase());
    }
//    /**
//     *
//     * @param id
//     * @return
//     */
//    @Override
//    public R judgeId(String id) {
//        String brandCd = ShiroUtils.getBrandcd();
//        Integer count = f40008Dao.selectCountById(id,brandCd);
//        if (count!=0){
//            return R.error(MessageUtils.getMessage("MSGCOMN0046","ユーザＩＤ","ユーザ基本マスタ"));
//        }
//        return R.ok();
//    }
}
