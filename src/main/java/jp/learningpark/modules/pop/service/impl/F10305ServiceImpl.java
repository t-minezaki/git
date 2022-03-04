/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.StuTermPlanDao;
import jp.learningpark.modules.common.dao.StuWeeklyPlanPerfDao;
import jp.learningpark.modules.common.entity.StuTermPlanEntity;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import jp.learningpark.modules.pop.dao.F10305Dao;
import jp.learningpark.modules.pop.dto.F10305Dto;
import jp.learningpark.modules.pop.service.F10305Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * <p>教科書デフォルトターム情報 Extend Service Impl</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/10/19 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F10305ServiceImpl implements F10305Service {
    /**
     * 生徒ウィークリー計画実績設定
     */
    @Autowired
    private StuWeeklyPlanPerfDao stuWeeklyPlanPerfDao;

    /**
     * 生徒タームプラン設定 Dao
     */
    @Autowired
    private StuTermPlanDao stuTermPlanDao;

    /**
     * 計画時間調整画面(F10305) Dao
     */
    @Autowired
    private F10305Dao f10305Dao;

    /**
     * <p>当ブロックの単元情報を取得</p>
     *
     * @param wppId        生徒ウィークリー計画実績設定Id
     * @param orgId        塾ID
     * @param blockTypeDiv ブロック種類区分
     * @return
     */
    @Override
    public F10305Dto getTextbDefByUnitiId(Integer wppId, String orgId,String blockTypeDiv) {
        return f10305Dao.selectTextbDefByUnitiId(wppId, orgId,blockTypeDiv);
    }

    /**
     * 登録
     *
     * @param stuId
     * @param type  更新フラグ
     * @param dto   画面情報
     * @return
     */
    @Override
    public R submitFn(String stuId, String type, F10305Dto dto) {
        //更新フラグ「0」場合
        StuWeeklyPlanPerfEntity stuWeeklyPlanPerfEntity = new StuWeeklyPlanPerfEntity();
        if (StringUtils.equals("0", type)) {
            //生徒ID
            stuWeeklyPlanPerfEntity.setStuId(stuId);
            //単元ID
            stuWeeklyPlanPerfEntity.setUnitId(dto.getUnitId());
            //生徒タームプラン設定ID
            stuWeeklyPlanPerfEntity.setStuTermPlanId(null);
            //ブロック表示名
            stuWeeklyPlanPerfEntity.setBlockDispyNm(dto.getBlockDispyNm());
            //ブロック種類区分
            stuWeeklyPlanPerfEntity.setBlockTypeDiv(dto.getBlockTypeDiv());
            //計画年月日
            stuWeeklyPlanPerfEntity.setPlanYmd(DateUtils.parse(dto.getPlanYmdStr(), GakkenConstant.DATE_FORMAT_YYYYMMDD));
            //教科区分
            stuWeeklyPlanPerfEntity.setSubjtDiv(null);
            //教科
            stuWeeklyPlanPerfEntity.setSubjtNm(null);
            //生徒計画学習時間（分）
            stuWeeklyPlanPerfEntity.setStuPlanLearnTm(dto.getStuPlanLearnTm());
            //計画学習時期ID
            stuWeeklyPlanPerfEntity.setPlanLearnSeasnId(null);
            //計画学習開始時間
            stuWeeklyPlanPerfEntity.setPlanLearnStartTime(new Timestamp(DateUtils.parse(dto.getPlanLearnStartTime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime()));
            //実績学習時間（分）
            stuWeeklyPlanPerfEntity.setPerfLearnTm(null);
            //学習理解度
            stuWeeklyPlanPerfEntity.setLearnLevUnds(null);
            //積み残し対象フラグ
            stuWeeklyPlanPerfEntity.setRemainDispFlg("0");
            //生徒削除フラグ
            stuWeeklyPlanPerfEntity.setStuDelFlg("0");
            //表示順番
            stuWeeklyPlanPerfEntity.setDispyOrder(null);
            //作成日時
            stuWeeklyPlanPerfEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            stuWeeklyPlanPerfEntity.setCretUsrId(stuId);
            //更新日時
            stuWeeklyPlanPerfEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            stuWeeklyPlanPerfEntity.setUpdUsrId(stuId);
            //del_flg
            stuWeeklyPlanPerfEntity.setDelFlg(0);
            stuWeeklyPlanPerfDao.insert(stuWeeklyPlanPerfEntity);
        }
        //更新フラグ「1」場合
        else {
            //Id
            stuWeeklyPlanPerfEntity.setId(dto.getId());
            //生徒計画学習時間（分）
            stuWeeklyPlanPerfEntity.setStuPlanLearnTm(dto.getStuPlanLearnTm());
            //更新日時
            stuWeeklyPlanPerfEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            stuWeeklyPlanPerfEntity.setUpdUsrId(stuId);
            //del_flg
            stuWeeklyPlanPerfEntity.setDelFlg(0);
            stuWeeklyPlanPerfDao.updateById(stuWeeklyPlanPerfEntity);
        }
        return R.ok();
    }

    /**
     * 削除
     *
     * @param stuId
     * @param dto
     * @return
     */
    @Override
    public R delete(String stuId, F10305Dto dto) {
        StuWeeklyPlanPerfEntity stuWeeklyPlanPerfEntity = new StuWeeklyPlanPerfEntity();
        //id
        stuWeeklyPlanPerfEntity.setId(dto.getId());
        //del_flg
        stuWeeklyPlanPerfEntity.setDelFlg(1);
        //更新日時
        stuWeeklyPlanPerfEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        stuWeeklyPlanPerfEntity.setUpdUsrId(stuId);
        stuWeeklyPlanPerfDao.updateById(stuWeeklyPlanPerfEntity);
        stuWeeklyPlanPerfEntity = stuWeeklyPlanPerfDao.selectById(dto.getId());
        StuTermPlanEntity stuTermPlanEntity = new StuTermPlanEntity();
        //Id
        stuTermPlanEntity.setId(stuWeeklyPlanPerfEntity.getStuTermPlanId());
        //計画登録フラグ
        stuTermPlanEntity.setPlanRegFlg("0");
        //更新日時
        stuTermPlanEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        stuTermPlanEntity.setUpdUsrId(stuId);
        stuTermPlanDao.updateById(stuTermPlanEntity);
        return R.ok();
    }
}
