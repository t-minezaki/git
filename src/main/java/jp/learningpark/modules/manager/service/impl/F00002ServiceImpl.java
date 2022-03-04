/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.dao.MstManagerDao;
import jp.learningpark.modules.common.dao.MstMentorDao;
import jp.learningpark.modules.common.dao.MstUsrDao;
import jp.learningpark.modules.common.entity.MstManagerEntity;
import jp.learningpark.modules.common.entity.MstMentorEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstManagerService;
import jp.learningpark.modules.manager.dao.F00002Dao;
import jp.learningpark.modules.manager.service.F00002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>F00002 管理者基本情報登録画面 ServiceImpl</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/11 : xiong: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F00002ServiceImpl implements F00002Service {

    @Autowired
    private F00002Dao f00002Dao;

    @Autowired
    private  MstManagerDao mstManagerDao;

    @Autowired
    private MstMentorDao mstMentorDao;

    @Autowired
    private MstUsrDao mstUsrDao;

    /**
     * 初期化
     * @return
     */
    @Override
    public R initial() {
        // セッションデータ．ユーザーID
        String userId = ShiroUtils.getUserId();
        //ユーザーロール
        String roleDiv = ShiroUtils.getUserEntity().getRoleDiv();

        //登録アカウントエンティティを取得する
        MstUsrEntity mstUsrEntity= mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().eq("usr_id",userId));

        if (StringUtils.equals("1",roleDiv.trim())){
            // 管理者基本マスタから取得し
            MstManagerEntity mstManagerEntity = f00002Dao.getManager(userId);
            if (mstManagerEntity == null){
                return R.error(MessageUtils.getMessage("MSGCOMN0017","管理者基本マスタ"));
            }
            // 更新日時
            String nowTime = DateUtils.format(mstManagerEntity.getUpdDatime(),Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            return R.ok().put("manager",mstManagerEntity).put("updateTime",nowTime).put("saf_modify_flg",mstUsrEntity.getSafModifyFlg());
        }else {
            // 管理者基本マスタから取得し
            MstMentorEntity mstMentorEntity = mstMentorDao.selectOne(new QueryWrapper<MstMentorEntity>().eq("mentor_id",userId).eq("del_flg",0));
            if (mstMentorEntity == null){
                return R.error(MessageUtils.getMessage("MSGCOMN0017","メンター基本マスタ"));
            }
            // 更新日時
            String nowTime = DateUtils.format(mstMentorEntity.getUpdDatime(),Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            return R.ok().put("manager",mstMentorEntity).put("updateTime",nowTime).put("saf_modify_flg",mstUsrEntity.getSafModifyFlg());
        }
    }

    /**
     * 管理者基本マスタへ更新する
     * @param mstManagerEntity 管理者基本マスタへ
     * @param updateTime 更新日時
     * @return
     */
    @Override
    public R updateManager(MstManagerEntity mstManagerEntity,String updateTime) {
        //ID
        Integer id = mstManagerEntity.getId();
        //ユーザーロール
        String roleDiv = ShiroUtils.getUserEntity().getRoleDiv();
        Map<String, Object> entity = getStringObjectMap(mstManagerEntity);
        if (StringUtils.equals("1",roleDiv.trim())){
            /* 2020/12/1 V9.0 cuikailin add start */
            if (id == null){
                mstManagerEntity.setMgrId(ShiroUtils.getUserId());
                mstManagerEntity.setDelFlg(0);
                mstManagerEntity.setCretUsrId(ShiroUtils.getUserId());
                mstManagerEntity.setCretDatime(DateUtils.getSysTimestamp());
                mstManagerEntity.setUpdUsrId(ShiroUtils.getUserId());
                mstManagerEntity.setUpdDatime(DateUtils.getSysTimestamp());
                mstManagerDao.insert(mstManagerEntity);
            }else {
                /* 2020/12/1 V9.0 cuikailin add end */
                // 排他チェックエラーの場合
                MstManagerEntity mstManagerEntity1 = mstManagerDao.selectById(id);
                String nowTime = DateUtils.format(mstManagerEntity1.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
                if (!updateTime.equals(nowTime)) {
                    return R.error(MessageUtils.getMessage("MSGCOMN0019"));
                }
                f00002Dao.updateManagerInfo(entity);
            }
        }else {
            /* 2020/12/1 V9.0 cuikailin add start */
            if (id == null){
                MstMentorEntity mentorEntity = new MstMentorEntity();
                mentorEntity.setMentorId(ShiroUtils.getUserId());
                mentorEntity.setMailad(mstManagerEntity.getMailad());
                mentorEntity.setFlnmNm(mstManagerEntity.getFlnmNm());
                mentorEntity.setFlnmLnm(mstManagerEntity.getFlnmLnm());
                mentorEntity.setFlnmKnNm(mstManagerEntity.getFlnmKnNm());
                mentorEntity.setFlnmKnLnm(mstManagerEntity.getFlnmKnLnm());
                mentorEntity.setTelnum(mstManagerEntity.getTelnum());
                mentorEntity.setDelFlg(0);
                mentorEntity.setCretUsrId(ShiroUtils.getUserId());
                mentorEntity.setCretDatime(DateUtils.getSysTimestamp());
                mentorEntity.setUpdUsrId(ShiroUtils.getUserId());
                mentorEntity.setUpdDatime(DateUtils.getSysTimestamp());
                mstMentorDao.insert(mentorEntity);
            }else {
            /* 2020/12/1 V9.0 cuikailin add end */
                // 排他チェックエラーの場合
                MstMentorEntity mstMentorEntity = mstMentorDao.selectById(id);
                String nowTime = DateUtils.format(mstMentorEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
                if (!updateTime.equals(nowTime)) {
                    return R.error(MessageUtils.getMessage("MSGCOMN0019"));
                }
                // 更新内容
                /* 2020/12/1 V9.0 cuikailin modify start */
                //Map<String, Object> entity = getStringObjectMap(mstManagerEntity);
                /* 2020/12/1 V9.0 cuikailin modify end */
                f00002Dao.updateMentorInfo(entity);
            }
        }
        return R.ok();
    }

    private Map<String, Object> getStringObjectMap(MstManagerEntity mstManagerEntity) {
        Map<String, Object> entity = new HashMap<>(16);
        // 姓名_姓
        entity.put("flnmNm", mstManagerEntity.getFlnmNm());
        // 姓名_名
        entity.put("flnmLnm", mstManagerEntity.getFlnmLnm());
        // 姓名_カナ姓
        entity.put("flnmKnNm", mstManagerEntity.getFlnmKnNm());
        // 姓名_カナ名
        entity.put("flnmKnLnm", mstManagerEntity.getFlnmKnLnm());
        // 電話番号
        entity.put("telnum", mstManagerEntity.getTelnum());
        // 更新内容
        entity.put("updDatime", DateUtils.getSysTimestamp());
        entity.put("updUsrId", ShiroUtils.getUserId());
        entity.put("afterUsrId", ShiroUtils.getUserEntity().getAfterUsrId());
        return entity;
    }
}
