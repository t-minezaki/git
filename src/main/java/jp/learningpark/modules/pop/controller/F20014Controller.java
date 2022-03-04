/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.StuTermPlanEntity;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import jp.learningpark.modules.common.service.StuTermPlanService;
import jp.learningpark.modules.common.service.StuWeeklyPlanPerfService;
import jp.learningpark.modules.pop.dto.F20014Dto;
import jp.learningpark.modules.pop.service.F20014Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>F20014_積み残し設定画面(POP)</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/10/08 : gong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/pop/F20014")
public class F20014Controller extends AbstractController {

    /**
     * 生徒ウィークリー計画実績設定 Service
     */
    @Autowired
    private StuWeeklyPlanPerfService weeklyPlanPerfService;

    /**
     * 生徒タームプラン設定 Service
     */
    @Autowired
    private StuTermPlanService stuTermPlanService;

    /**
     * F20014_積み残し設定画面 Service
     */
    @Autowired
    private F20014Service f20014Service;

    /**
     * <p>初期表示,del_flg=0</p>
     *
     * @param id           term_planのid,weekly_plan_prefのid
     * @param isTerm       t:(0：未計画、２：削除）f:1：計画済み
     * @param blockTypeDiv ブロック種類区分
     * @return 画面情報
     */
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public R init(Integer id, String isTerm, String blockTypeDiv) {
        F20014Dto f20014Dto = new F20014Dto();
        //生徒タームプラン設定
        if (StringUtils.equals("t", isTerm)) {
            f20014Dto = f20014Service.getInitInfoByTermId(id);
        }
        //生徒ウィークリー計画実績設定
        if (StringUtils.equals("f", isTerm)) {
//            管理者表示の追加 2020/11/25 modify yang start--
            StuWeeklyPlanPerfEntity stuWeeklyPlanPerfEntity = weeklyPlanPerfService.getById(id);
            if (StringUtils.equals("S1", blockTypeDiv) && stuWeeklyPlanPerfEntity.getStuTermPlanId() != null) {
//                管理者表示の追加 2020/11/25 modify yang end--
                f20014Dto = f20014Service.getInitInfoByWeeklyId(id);
            } else {
                f20014Dto = f20014Service.getInitInfoByWeeklyIdNotInS1(id);
            }
        }
        //計画学習開始時間
        String planLearnStartTime = "";
        //計画学習終了時間
        String planLearnEndTime = "";
        //理解度
        String learnLev = "";
        //該当ブロックの計画年月日　M月D日（曜日）で表示
        String weekday = "";
        if (f20014Dto != null) {
            //計画学習開始時間
            if (f20014Dto.getPlanLearnStartTime() != null) {
                planLearnStartTime = DateUtils.format(f20014Dto.getPlanLearnStartTime(), GakkenConstant.DATE_FORMAT_HH_MM);
                if (f20014Dto.getStuPlanLearnTm() != null) {
                    //計画学習終了時間
                    planLearnEndTime = DateUtils.format(DateUtils.addMinutes(f20014Dto.getPlanLearnStartTime(), f20014Dto.getStuPlanLearnTm()), GakkenConstant.DATE_FORMAT_HH_MM);
                }
            }

            if (f20014Dto.getPlanYmd() != null) {
                //該当ブロックの計画年月日　M月D日（曜日）で表示
                weekday = DateUtils.format(f20014Dto.getPlanYmd(), GakkenConstant.DATE_FORMAT_MM_DD_E);
            }
            //計画学習時間（分）
            if (f20014Dto.getPlanLearnTm() == null) {
                f20014Dto.setPlanLearnTm(0);
            }
        } else {
            f20014Dto = new F20014Dto();
        }
        Boolean dateFlg = false;
        if (StringUtils.equals(isTerm, "f")) {
            //計画年月日 ＜ 引渡データ．対象週開始日
            if (f20014Dto.getPlanYmd().compareTo(DateUtils.getMonday(DateUtils.getSysDate())) < 0) {
                dateFlg = true;
            }
        }
        if (StringUtils.equals(isTerm, "t")) {

        }
//        f20014Dto.setTermPlanId(id);
        return R.ok().put("dto", f20014Dto).put("planLearnStartTime", planLearnStartTime).put("dateFlg", dateFlg).put("planLearnEndTime", planLearnEndTime).put("learnLev", learnLev).put("weekday", weekday);
    }

    /**
     * <p>更新の処理</p>
     *
     * @param id ID
     * @param stuDelFlg 区分
     * @return 画面情報
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(Integer id, Integer stuDelFlg) {
        StuWeeklyPlanPerfEntity weeklyPlanPerfEntity = new StuWeeklyPlanPerfEntity();
        String mentorId = getUserCd();
        //Id
        weeklyPlanPerfEntity.setId(id);
        //DBへ該当ブロックを積み残し対象にする。
        weeklyPlanPerfEntity.setRemainDispFlg("3");
        //生徒削除フラグ
        weeklyPlanPerfEntity.setStuDelFlg("0");
        //更新日時
        weeklyPlanPerfEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        weeklyPlanPerfEntity.setUpdUsrId(mentorId);
        //更新
        weeklyPlanPerfService.updateById(weeklyPlanPerfEntity);
        if(stuDelFlg !=null){
            weeklyPlanPerfEntity=weeklyPlanPerfService.getById(id);
        }
        return R.ok().put("remainDispFlg", weeklyPlanPerfEntity.getRemainDispFlg()).put("learnLevUnds",weeklyPlanPerfEntity.getLearnLevUnds());
    }

    /**
     * <p>更新の処理</p>
     *
     * @param id ID
     * @param stuDelFlg 区分
     * @return 画面情報
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delete(Integer id,Integer stuDelFlg) {
        StuWeeklyPlanPerfEntity weeklyPlanPerfEntity = new StuWeeklyPlanPerfEntity();
        String mentorId = getUserCd();
        //Id
        weeklyPlanPerfEntity.setId(id);
        //DBへ該当ブロックを積み残し対象にする。
        //積み残し対象フラグ
        weeklyPlanPerfEntity.setRemainDispFlg("4");
        //更新日時
        weeklyPlanPerfEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        weeklyPlanPerfEntity.setUpdUsrId(mentorId);
        //更新
        weeklyPlanPerfService.updateById(weeklyPlanPerfEntity);
        if(stuDelFlg !=null){
            weeklyPlanPerfEntity=weeklyPlanPerfService.getById(id);
        }
        return R.ok().put("remainDispFlg", weeklyPlanPerfEntity.getRemainDispFlg()).put("learnLevUnds",weeklyPlanPerfEntity.getLearnLevUnds());
    }

    /**
     * <p>更新の処理</p>
     *
     * @param id 生徒タームプラン設定ID
     * @return 画面情報
     */
    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public R reply(Integer id) {
        StuTermPlanEntity stuTermPlanEntity = new StuTermPlanEntity();
        String mentorId = getUserCd();
//        //Id
        stuTermPlanEntity.setId(id);
        //計画登録フラグ
        stuTermPlanEntity.setPlanRegFlg("0");
        //更新日時
        stuTermPlanEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        stuTermPlanEntity.setUpdUsrId(mentorId);
        //更新
        stuTermPlanService.updateById(stuTermPlanEntity);
        return R.ok().put("planRegFlg", stuTermPlanEntity.getPlanRegFlg());
    }

    /**
     * <p>更新の処理</p>
     *
     * @param id 生徒タームプラン設定ID
     * @return 画面情報
     */
    @RequestMapping(value = "/deleteTermPlan", method = RequestMethod.POST)
    public R deleteTermPlan(Integer id) {
        StuTermPlanEntity stuTermPlanEntity = new StuTermPlanEntity();
        String mentorId = getUserCd();
//        //Id
        stuTermPlanEntity.setId(id);
        //計画登録フラグ
        stuTermPlanEntity.setPlanRegFlg("2");
        //更新日時
        stuTermPlanEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        stuTermPlanEntity.setUpdUsrId(mentorId);
        //更新
        stuTermPlanService.updateById(stuTermPlanEntity);
        return R.ok().put("planRegFlg", stuTermPlanEntity.getPlanRegFlg());
    }
}
