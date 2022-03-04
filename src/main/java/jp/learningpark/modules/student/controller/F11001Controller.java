/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/21
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
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.StuWeeklyPlanPerfService;
import jp.learningpark.modules.student.dto.F11001Dto;
import jp.learningpark.modules.student.service.F11001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>スマホ_学習情報登録</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/04/23 : lyh: 新規<br />
 * @version 7.0
 */
@RequestMapping(value = "/student/F11001")
@RestController
public class F11001Controller {

    /**
     *コードマスタ  Service
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     *ブロックマスタ Service
     */
    @Autowired
    MstBlockService mstBlockService;

    /**
     *F11001S  Service
     */
    @Autowired
    F11001Service f11001Service;

    /**
     *生徒ウィークリー計画実績設定  Service
     */
    @Autowired
    StuWeeklyPlanPerfService stuWeeklyPlanPerfService;

    /**
     * 初期表示
     * @param weeklyPlanId    ウィークリー計画実績設定のＩＤ
     * @return
     */
    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public R init(Integer weeklyPlanId){
        //生徒ID
        String stuId = ShiroUtils.getUserId();
        //マスタブロックからカテゴリを取得する。
        List<MstBlockEntity> blockTypeDiv = mstBlockService.list(new QueryWrapper<MstBlockEntity>()
                .in("block_type_div","P1","R1","V1","W1", "S1")
                .isNull("upplev_block_id")
                .isNull("stu_id")
                .orderByAsc("block_type_div")
                .eq("del_flg",0));
        //該当生徒より、コードマスタ_明細から科目を取得する。
        List<F11001Dto> subjtDiv = f11001Service.getSubjt(stuId);
        R r = R.ok();
        if (weeklyPlanId != -1){
            StuWeeklyPlanPerfEntity stuWeeklyPlanPerfEntity = stuWeeklyPlanPerfService.getOne(new QueryWrapper<StuWeeklyPlanPerfEntity>()
                    .select("block_dispy_nm, block_type_div, subjt_nm, subjt_div, plan_ymd, stu_plan_learn_tm, plan_learn_start_time, block_dispy_nm, upd_datime")
                    .eq("id", weeklyPlanId).eq("del_flg", 0));
            if (stuWeeklyPlanPerfEntity != null){
                r.put("weeklyPlan", stuWeeklyPlanPerfEntity)
                        .put("updDatime", DateUtils.format(stuWeeklyPlanPerfEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
            }
        }
        return r.put("blockTypeDiv",blockTypeDiv).put("subjtDiv",subjtDiv);
    }

    //登録を押下||更新ボタン押下
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public R submit(Integer weeklyPlanId, String blockCd,String subjCd,Date starTime,String startYmd,Integer hours,Integer min,String memo,String schy,String block, String updateStr){
        //生徒ID
        String stuId = ShiroUtils.getUserId();
        //ブロックマスタ．ID
        String id=blockCd.split(",")[1];
        Date Ymd = DateUtils.parse(startYmd,GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        if (hours !=0){
            hours = hours*60;
            min = hours+min;
        }
        if (StringUtils.isEmpty(block)){
            //・画面．メモが空の場合
            memo=block;
        }else {
            //・画面．メモが空でない場合
            memo=block +" "+ memo;
        }
        StuWeeklyPlanPerfEntity stuWeeklyPlanPerfEntity = null;
        if (weeklyPlanId == -1) {
            stuWeeklyPlanPerfEntity = new StuWeeklyPlanPerfEntity();
        }else {
            stuWeeklyPlanPerfEntity = stuWeeklyPlanPerfService.getById(weeklyPlanId);
            if (stuWeeklyPlanPerfEntity == null){
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒ウィークリー計画実績設定"));
            }
            if (!StringUtils.equals(updateStr, DateUtils.format(stuWeeklyPlanPerfEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS))){
                return R.error(MessageUtils.getMessage("MSGCOMN0019"));
            }
        }
        if (weeklyPlanId == -1) {
            //生徒ID
            stuWeeklyPlanPerfEntity.setStuId(stuId);
            //単元ID
            stuWeeklyPlanPerfEntity.setUnitId(Integer.parseInt(id));
            //生徒タームプラン設定ID
            stuWeeklyPlanPerfEntity.setStuTermPlanId(null);
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
            stuWeeklyPlanPerfEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            stuWeeklyPlanPerfEntity.setCretUsrId(ShiroUtils.getUserEntity().getUsrId());
            //削除フラグ
            stuWeeklyPlanPerfEntity.setDelFlg(0);
        }
        //ブロック種類区分
        stuWeeklyPlanPerfEntity.setBlockTypeDiv(blockCd.split(",")[0]);
        //教科
        stuWeeklyPlanPerfEntity.setSubjtNm(schy);
        //教科区分
        stuWeeklyPlanPerfEntity.setSubjtDiv(subjCd);
        //計画年月日
        stuWeeklyPlanPerfEntity.setPlanYmd(Ymd);
        //計画学習開始時間
        stuWeeklyPlanPerfEntity.setPlanLearnStartTime(DateUtils.toTimestamp(starTime));
        //生徒計画学習時間（分）
        stuWeeklyPlanPerfEntity.setStuPlanLearnTm(min);
        //ブロック表示名
        stuWeeklyPlanPerfEntity.setBlockDispyNm(memo);
        //更新日時
        stuWeeklyPlanPerfEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        stuWeeklyPlanPerfEntity.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());

        if (weeklyPlanId == -1) {
            //生徒ウィークリー計画実績設定登録
            stuWeeklyPlanPerfService.save(stuWeeklyPlanPerfEntity);
        }else {
            stuWeeklyPlanPerfService.updateById(stuWeeklyPlanPerfEntity);
        }
        return R.ok();
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

