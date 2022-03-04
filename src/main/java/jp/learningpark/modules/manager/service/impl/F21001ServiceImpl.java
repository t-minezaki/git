/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstMentorDao;
import jp.learningpark.modules.common.entity.MstMentorEntity;
import jp.learningpark.modules.manager.dao.F21001Dao;
import jp.learningpark.modules.manager.service.F21001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>F21001 メンター基本情報登録画面 ServiceImpl</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/11 : xiong: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F21001ServiceImpl implements F21001Service {

    @Autowired
    private F21001Dao f21001Dao;

    @Autowired
    private MstMentorDao mstMentorDao;

    /**
     * 初期化
     *
     * @return
     */
    @Override
    public R initial() {
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        // セッションデータ．ユーザーID
        String mentorId = ShiroUtils.getUserId();
        // メンター基本マスタから取得し
        MstMentorEntity mstMentorEntity = f21001Dao.getMentor(mentorId);
        if (mstMentorEntity == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "先生基本マスタ"));
        }
        // 更新日時
        String nowTime = DateUtils.format(mstMentorEntity.getUpdDatime(),Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
        return R.ok().put("mentor", mstMentorEntity).put("updateTime",nowTime).put("brandCd",brandCd);
    }

    /**
     * メンター基本マスタへ更新する
     * @param mstMentorEntity メンター基本マスタ
     * @param updateTime 更新日時
     * @return
     */
    @Override
    public R updateMentor(MstMentorEntity mstMentorEntity,String updateTime) {
        //ID
        Integer id = mstMentorEntity.getId();
        // 排他チェックエラーの場合
        MstMentorEntity mstMentorEntity1 = mstMentorDao.selectById(id);
        String nowTime = DateUtils.format(mstMentorEntity1.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
        if (!updateTime.equals(nowTime)) {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        // 更新内容
        mstMentorEntity.setUpdDatime(DateUtils.getSysTimestamp());
        mstMentorEntity.setUpdUsrId(ShiroUtils.getUserId());
        mstMentorDao.updateById(mstMentorEntity);
        String mentorNm = mstMentorEntity.getFlnmNm() + " " + mstMentorEntity.getFlnmLnm();
        ShiroUtils.setSessionAttribute(GakkenConstant.MENTOR_NM, mentorNm);
        return R.ok();
    }
}
