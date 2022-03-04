/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/27
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import jp.learningpark.modules.common.service.MstBlockService;
import jp.learningpark.modules.common.service.StuWeeklyPlanPerfService;
import jp.learningpark.modules.student.service.F11003Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p>F11003_スマホ_学習情報登録(生活のブロック)_V7.0</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/04/27 : lyh: 新規<br />
 * @version 7.0
 */
@RequestMapping("/student/F11003")
@RestController
public class F11003Controller {

    /**
     *ブロックマスタ Service
     */
    @Autowired
    MstBlockService mstBlockService;

    /**
     * F11003  Service
     */
    @Autowired
    F11003Service f11003Service;

    /**
     *生徒ウィークリー計画実績設定 Service
     */
    @Autowired
    StuWeeklyPlanPerfService stuWeeklyPlanPerfService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初期表示
     * @param id                blockId
     * @param initFlag          初期化flag
     * @param weeklyPlanId      weekly plan id
     * @return
     */
    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public R init(Integer id, Boolean initFlag, Integer weeklyPlanId){
        R r = R.ok();
        if (initFlag){
            //マスタブロックから項目を取得する。
            List<MstBlockEntity> mstBlockList = mstBlockService.list(new QueryWrapper<MstBlockEntity>().in("block_type_div","O1","O2","O3").eq("del_flg",0)
                    .isNull("stu_id").isNull("upplev_block_id").orderByAsc("block_type_div"));
            r.put("mstBlockList",mstBlockList);
        }

        if (initFlag && weeklyPlanId != -1){
            StuWeeklyPlanPerfEntity stuWeeklyPlanPerfEntity = stuWeeklyPlanPerfService.getOne(new QueryWrapper<StuWeeklyPlanPerfEntity>()
                    .select("block_dispy_nm, block_type_div, unit_id, plan_ymd, stu_plan_learn_tm, plan_learn_start_time, block_dispy_nm, upd_datime")
                    .eq("id", weeklyPlanId).eq("del_flg", 0));
            if (stuWeeklyPlanPerfEntity != null){
                r.put("weeklyPlan", stuWeeklyPlanPerfEntity)
                        .put("updDatime", DateUtils.format(stuWeeklyPlanPerfEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
                MstBlockEntity mstBlockEntity = mstBlockService.getById(stuWeeklyPlanPerfEntity.getUnitId());
                if (mstBlockEntity != null){
                    id = mstBlockEntity.getUpplevBlockId();
                }
            }
        }
        if (id==null){
            id=1;
        }
        //画面選択したブロック（大分類）より、マスタブロック（小分類）取得する。
        List<MstBlockEntity> mstBlock = mstBlockService.list(new QueryWrapper<MstBlockEntity>().eq("upplev_block_id",id).isNull("stu_id").eq("del_flg",0));
        r.put("mstBlock",mstBlock);
        return r;
    }

    /**
     * 登録を押下
     *
     * @param weeklyPlanId
     * @param blockCd
     * @param starTime
     * @param startYmd
     * @param hours
     * @param min
     * @param memo
     * @param blockNm
     * @param updDatime
     * @param upplevBlockId
     * @param blockPicDiv
     * @return
     */
    @RequestMapping(value = "/submit",method =RequestMethod.POST )
    @Transactional(rollbackFor = Exception.class)
    public R submit(Integer weeklyPlanId, String blockCd, Date starTime, String startYmd, Integer hours, Integer min, String memo, String blockNm, String updDatime, Integer upplevBlockId, String blockPicDiv){
        //システム日時
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        //生徒ID
        String stuId = ShiroUtils.getUserId();
        Date Ymd = DateUtils.parse(startYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        if (hours !=0){
            hours = hours*60;
            min = hours+min;
        }
        if ("".equals(memo) || memo==null){
            //・画面．メモが空の場合
            memo=blockNm;
        }else {
            //・画面．メモが空でない場合
            memo=blockNm + " " + memo;
        }
        StuWeeklyPlanPerfEntity stuWeeklyPlanPerfEntity;
        if (weeklyPlanId == -1){
            stuWeeklyPlanPerfEntity = new StuWeeklyPlanPerfEntity();
        }else {
            stuWeeklyPlanPerfEntity = stuWeeklyPlanPerfService.getById(weeklyPlanId);
            if (stuWeeklyPlanPerfEntity == null){
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒ウィークリー計画実績設定"));
            }
            if (!StringUtils.equals(updDatime, DateUtils.format(stuWeeklyPlanPerfEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS))){
                return R.error(MessageUtils.getMessage("MSGCOMN0019"));
            }
        }
        if (weeklyPlanId == -1){
            //生徒ID
            stuWeeklyPlanPerfEntity.setStuId(stuId);
            //生徒タームプラン設定ID
            stuWeeklyPlanPerfEntity.setStuTermPlanId(null);
            //教科区分
            stuWeeklyPlanPerfEntity.setSubjtDiv(null);
            //教科
            stuWeeklyPlanPerfEntity.setSubjtNm(null);
            //計画学習時期ID
            stuWeeklyPlanPerfEntity.setPlanLearnSeasnId(null);
            //実績年月日
            stuWeeklyPlanPerfEntity.setPerfYmd(null);
            //実績学習開始時間
            stuWeeklyPlanPerfEntity.setPerfLearnStartTime(null);
            //実績学習時間（分）
            stuWeeklyPlanPerfEntity.setPerfLearnTm(null);
            //学習理解度
            stuWeeklyPlanPerfEntity.setLearnLevUnds(null);
            //積み残し対象フラグ
            stuWeeklyPlanPerfEntity.setRemainDispFlg(null);
            //生徒削除フラグ
            stuWeeklyPlanPerfEntity.setStuDelFlg("0");
            //表示順番
            stuWeeklyPlanPerfEntity.setDispyOrder(null);
            //作成日時
            stuWeeklyPlanPerfEntity.setCretDatime(sysTimestamp);
            //作成ユーザＩＤ
            stuWeeklyPlanPerfEntity.setCretUsrId(stuId);
            //削除フラグ
            stuWeeklyPlanPerfEntity.setDelFlg(0);
        }
        MstBlockEntity mstBlockEntity = mstBlockService.getOne(new QueryWrapper<MstBlockEntity>().eq("block_dispy_nm", blockNm).eq("del_flg", 0).eq("stu_id", stuId).last("limit 1"));
        if (mstBlockEntity == null){
            mstBlockEntity = new MstBlockEntity();
            //ブロック表示名
            mstBlockEntity.setBlockDispyNm(blockNm);
            //上層ブロックID
            mstBlockEntity.setUpplevBlockId(upplevBlockId);
            //生徒ID
            mstBlockEntity.setStuId(stuId);
            //ブロック種類区分
            mstBlockEntity.setBlockTypeDiv(blockCd.split(",")[0]);
            //ブロック画像区分
            mstBlockEntity.setBlockPicDiv(blockPicDiv.replace("_grey", ""));
            //作成日時
            mstBlockEntity.setCretDatime(sysTimestamp);
            //作成ユーザＩＤ
            mstBlockEntity.setCretUsrId(stuId);
            //更新日時
            mstBlockEntity.setUpdDatime(sysTimestamp);
            //更新ユーザＩＤ
            mstBlockEntity.setUpdUsrId(stuId);
            //削除フラグ
            mstBlockEntity.setDelFlg(0);
            mstBlockService.save(mstBlockEntity);
        }
        //単元ID
        stuWeeklyPlanPerfEntity.setUnitId(mstBlockEntity.getId());
        //ブロック表示名
        stuWeeklyPlanPerfEntity.setBlockDispyNm(memo);
        //ブロック種類区分
        stuWeeklyPlanPerfEntity.setBlockTypeDiv(blockCd.split(",")[0]);
        //計画年月日
        stuWeeklyPlanPerfEntity.setPlanYmd(Ymd);
        //生徒計画学習時間（分）
        stuWeeklyPlanPerfEntity.setStuPlanLearnTm(min);
        //計画学習開始時間
        stuWeeklyPlanPerfEntity.setPlanLearnStartTime(DateUtils.toTimestamp(starTime));
        //更新日時
        stuWeeklyPlanPerfEntity.setUpdDatime(sysTimestamp);
        //更新ユーザＩＤ
        stuWeeklyPlanPerfEntity.setUpdUsrId(stuId);
        try {
            if (weeklyPlanId == -1) {
                //生徒ウィークリー計画実績設定登録
                stuWeeklyPlanPerfService.save(stuWeeklyPlanPerfEntity);
            }else {
                //生徒ウィークリー計画実績設定を更新する
                stuWeeklyPlanPerfService.updateById(stuWeeklyPlanPerfEntity);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return R.error();
        }
        return  R.ok();
    }

    /**
     * 削除ボタン押下
     * @param weeklyPlanId
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public R delete(Integer weeklyPlanId){
        try {
            stuWeeklyPlanPerfService.removeById(weeklyPlanId);
        }catch (Exception e){
            return R.error("データの削除に失敗しました。");
        }
        return R.ok();
    }
}