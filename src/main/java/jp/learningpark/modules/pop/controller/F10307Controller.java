/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.StuTermPlanEntity;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import jp.learningpark.modules.common.entity.TextbDefTimeInfoEntity;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.pop.service.F10303Service;
import jp.learningpark.modules.pop.service.F10307Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import jp.learningpark.modules.xapi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>F10307 学習単元実績登録画面</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/10/08 : gong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/pop/F10307")
public class F10307Controller extends AbstractController {

    /**
     * 生徒ウィークリー計画実績設定 Service
     */
    @Autowired
    private StuWeeklyPlanPerfService weeklyPlanPerfService;

    /**
     * 生徒タームプラン設定 Service
     */
    @Autowired
    private StuTermPlanService termPlanService;

    /**
     * 教科書デフォルトターム情報 Service
     */
    @Autowired
    private TextbDefTimeInfoService textbDefTimeInfoService;

    @Autowired
    private F10307Service f10307Service;

    @Autowired
    private F10303Service f10303Service;

    /**
     * コードマスタ_明細 Service
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * stuGetPointHstService
     */
    @Autowired
    private StuGetPointHstService stuGetPointHstService;

    /**
     * <p>F10307 当学習ブロックの教科書単元情報、計画学習時間などを表示する</p>
     *
     * @param id 当学習ブロックのid
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer id,Integer flag) {
        StuWeeklyPlanPerfEntity weeklyPlanPerfEntity;
        //学習理解度List
        List<MstCodDEntity> list = null;
        //曜日
        String s = "";
        //計画学習開始時間
        String planLearnStartTime = "";
        //計画学習終了時間
        String planLearnEndTime = "";
        //生徒タームプラン設定の情報
        StuTermPlanEntity termPlanEntity;
        //教科書デフォルトターム情報
        TextbDefTimeInfoEntity textbDefTimeInfoEntity = new TextbDefTimeInfoEntity();
        //学習理解度List
        list = f10307Service.getListByCodKey("LEARN_LEV_UNDS");
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        /* 2020/11/13 V9.0 cuikailin add start */
        if(flag == 1) {
            //生徒ウィークリー計画実績設定から単元情報を取得し
            weeklyPlanPerfEntity = f10307Service.getWithCodeMstBystuId(id);
            if (weeklyPlanPerfEntity == null) {
                return R.error("当学習ブロックの情報は存在しない");
            }
            if (!"NP".equals(weeklyPlanPerfEntity.getBlockTypeDiv())){
                switch (weeklyPlanPerfEntity.getBlockTypeDiv()){
                    case "R1":
                        weeklyPlanPerfEntity.setBlockDispyNm("復－");
                        break;
                    case "P1":
                        weeklyPlanPerfEntity.setBlockDispyNm("予－");
                        break;
                    case "W1":
                        weeklyPlanPerfEntity.setBlockDispyNm("学宿－");
                        break;
                    case "V1":
                        weeklyPlanPerfEntity.setBlockDispyNm("塾宿－");
                        break;
                    /* 2020/11/24 V9.0 cuikailin modify start */
                    case "S1":
                        if (weeklyPlanPerfEntity.getStuTermPlanId() == null){
                            weeklyPlanPerfEntity.setBlockDispyNm("学－");
                        }
                        break;
                    /* 2020/11/24 V9.0 cuikailin modify end */
                }
            }
            /* 2020/11/13 V9.0 cuikailin add end */
            //生徒タームプラン設定の情報
            termPlanEntity = termPlanService.getById(weeklyPlanPerfEntity.getStuTermPlanId());
            //教科書デフォルトターム情報
            if (termPlanEntity != null) {
                textbDefTimeInfoEntity = textbDefTimeInfoService.getById(termPlanEntity.getTextbDefUnitId());
            }

            //曜日
            s = DateUtils.format(weeklyPlanPerfEntity.getPlanYmd(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
            //実績年月日
            String perfLearnYmd = "";
            if (weeklyPlanPerfEntity.getPerfYmd() == null) {
                perfLearnYmd = DateUtils.format(weeklyPlanPerfEntity.getPlanYmd(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
            } else {
                perfLearnYmd = DateUtils.format(weeklyPlanPerfEntity.getPerfYmd(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
            }
            //実績開始時間
            String perfLearnTime = "";
            String perfLearnEndTime = "";
            if (weeklyPlanPerfEntity.getPerfLearnStartTime() == null) {
                perfLearnTime = DateUtils.format(weeklyPlanPerfEntity.getPlanLearnStartTime(), GakkenConstant.DATE_FORMAT_HH_MM);
                perfLearnEndTime = DateUtils.format(DateUtils.addMinutes(weeklyPlanPerfEntity.getPlanLearnStartTime(), weeklyPlanPerfEntity.getStuPlanLearnTm()),
                        GakkenConstant.DATE_FORMAT_HH_MM);
            } else {
                perfLearnTime = DateUtils.format(weeklyPlanPerfEntity.getPerfLearnStartTime(), GakkenConstant.DATE_FORMAT_HH_MM);
                if (weeklyPlanPerfEntity.getPerfLearnTm() != null) {
                    perfLearnEndTime = DateUtils.format(DateUtils.addMinutes(weeklyPlanPerfEntity.getPerfLearnStartTime(), weeklyPlanPerfEntity.getPerfLearnTm()),
                            GakkenConstant.DATE_FORMAT_HH_MM);
                } else {
                    perfLearnEndTime = DateUtils.format(
                            DateUtils.addMinutes(weeklyPlanPerfEntity.getPlanLearnStartTime(), weeklyPlanPerfEntity.getStuPlanLearnTm()),
                            GakkenConstant.DATE_FORMAT_HH_MM);
                }
            }

            //計画学習開始時間
            planLearnStartTime = DateUtils.format(weeklyPlanPerfEntity.getPlanLearnStartTime(), GakkenConstant.DATE_FORMAT_HH_MM);
            //計画学習終了時間
            planLearnEndTime = DateUtils.format(
                    DateUtils.addMinutes(weeklyPlanPerfEntity.getPlanLearnStartTime(), weeklyPlanPerfEntity.getStuPlanLearnTm()), GakkenConstant.DATE_FORMAT_HH_MM);
            String updateTime = "";
            //更新日時
            updateTime = DateUtils.format(weeklyPlanPerfEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);

            List<MstCodDEntity> subjtList = f10303Service.getSubjtDivsByStuId(ShiroUtils.getUserId(),orgId);
            return R.ok().put("wpp", weeklyPlanPerfEntity).put("list", list).put("texDff", textbDefTimeInfoEntity).put("weekday", s)
                    .put("planLearnStartTime", planLearnStartTime).put("planLearnEndTime", planLearnEndTime).put("updateTime", updateTime)
                    .put("perfLearnYmd", perfLearnYmd).put("perfLearnTime", perfLearnTime).put("perfLearnEndTime", perfLearnEndTime)
                    .put("flag",flag).put("subjtList", subjtList);
            //                .put("preEndTimeCheck", preEndTimeCheck);
        /* 2020/11/13 V9.0 cuikailin add start */
        }else {
            //ブロックList
            List<MstCodDEntity> selectList = f10307Service.getListByCodKey("BLOCK_TYPE_DIV");
            List<MstCodDEntity> blockList = new ArrayList<>();
            for (MstCodDEntity mstCodDEntity:selectList){
                if("S1,R1,V1,W1,P1".contains(mstCodDEntity.getCodCd())){
                    blockList.add(mstCodDEntity);
                }
            }
            //教科List
            List<MstCodDEntity> eduList = f10303Service.getSubjtDivsByStuId(ShiroUtils.getUserId(),orgId);
            return R.ok().put("list", list).put("blockList",blockList).put("eduList",eduList).put("flag",flag);
        }
        /* 2020/11/13 V9.0 cuikailin add end */
    }

    /**
     * <p>登録ボタンの処理</p>
     *
     * @param model 画面モード(学習理解度,実績学習時間（分))
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R f10307Submit(StuWeeklyPlanPerfEntity model, String updateTime, String perfLearnYmd, String perfLearnTime, String perfLearnEndTime, Integer flag) {
        // 2020/12/4 huangxinliang modify start
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        String stuId = ShiroUtils.getUserId();
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 2020/12/4 huangxinliang modify end
        perfLearnYmd = perfLearnYmd.replace("/", "-");
        Date perfLearnYmdDB = DateUtils.parse(perfLearnYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);

        Date startDate = DateUtils.parse(perfLearnYmd + " " + perfLearnTime + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO);
        Date endDate = DateUtils.parse(perfLearnYmd + " " + perfLearnEndTime + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO);

        //実績開始時間
        Timestamp perfLearnTimeDB = new Timestamp(
                DateUtils.parse(perfLearnYmd + " " + perfLearnTime + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
        //実績学習終了時間
        Timestamp perfLearnEndTimeDB = new Timestamp(
                DateUtils.parse(perfLearnYmd + " " + perfLearnEndTime + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());

        //時間の比較
        Timestamp perfLearnStartTime = null;
        Integer perfLearnTm = null;
        if (!StringUtils.equals(model.getLearnLevUnds(), "0")) {
            //実績学習開始時間
            /*weeklyPlanPerfEntity.setPerfLearnStartTime(perfLearnTimeDB);*/
            perfLearnStartTime = perfLearnTimeDB;
            if (endDate.compareTo(startDate) >= 0) {
                //実績学習時間(分)
                /*weeklyPlanPerfEntity.setPerfLearnTm((int)((perfLearnEndTimeDB.getTime() - perfLearnTimeDB.getTime()) / (1000 * 60)));*/
                perfLearnTm = (int) ((perfLearnEndTimeDB.getTime() - perfLearnTimeDB.getTime()) / (1000 * 60));
            } else {
                //実績学習時間(分)
                /*weeklyPlanPerfEntity.setPerfLearnTm((int)((perfLearnEndTimeDB.getTime() - perfLearnTimeDB.getTime()) / (1000 * 60)) + 60 * 24);*/
                perfLearnTm = (int) ((perfLearnEndTimeDB.getTime() - perfLearnTimeDB.getTime()) / (1000 * 60)) + 60 * 24;
            }
        }/*else {
            weeklyPlanPerfEntity.setPerfLearnTm(null);
        }*/
        String remainDispFlg = null;
        //積み残し対象フラグ
        if (StringUtils.equals(model.getLearnLevUnds(), "4") || StringUtils.equals(model.getLearnLevUnds(), "0")) {
            remainDispFlg = "1";
        } else {
            remainDispFlg = "0";
        }
        // 2020/12/4 huangxinliang modify start
        R r = R.ok();
        // 2020/12/4 huangxinliang modify end
        if(flag == 1) {
            //排他チェックエラーの場合、処理を中断し
            StuWeeklyPlanPerfEntity weeklyPlanPerfEntity = weeklyPlanPerfService.getById(model.getId());
            if (weeklyPlanPerfEntity == null || !updateTime.equals(
                    DateUtils.format(weeklyPlanPerfEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS))) {
                return R.error(MessageUtils.getMessage("MSGCOMN0019"));
            }
            weeklyPlanPerfEntity.setPerfYmd(perfLearnYmdDB);

            weeklyPlanPerfEntity.setSubjtDiv(model.getSubjtDiv());
            weeklyPlanPerfEntity.setSubjtNm(model.getSubjtNm());
            //学習理解度
            weeklyPlanPerfEntity.setLearnLevUnds(model.getLearnLevUnds());
            //積み残し対象フラグ
//            if (StringUtils.equals(model.getLearnLevUnds(), "4") || StringUtils.equals(model.getLearnLevUnds(), "0")) {
            weeklyPlanPerfEntity.setRemainDispFlg(remainDispFlg);
//            } else {
//                weeklyPlanPerfEntity.setRemainDispFlg("0");
//            }
            //更新日時
            weeklyPlanPerfEntity.setUpdDatime(sysTimestamp);
            //更新ユーザＩＤ
            weeklyPlanPerfEntity.setUpdUsrId(model.getStuId());

            weeklyPlanPerfEntity.setPerfLearnStartTime(null);
            weeklyPlanPerfEntity.setPerfLearnTm(null);
            //update
            weeklyPlanPerfService.update(weeklyPlanPerfEntity, new UpdateWrapper<StuWeeklyPlanPerfEntity>()
                    .set("perf_learn_start_time", perfLearnStartTime)
                    .set("perf_learn_tm", perfLearnTm)
                    .eq("id", weeklyPlanPerfEntity.getId()));
            weeklyPlanPerfService.updateById(weeklyPlanPerfEntity);

            //ビッグデータ
            Extensions exts = new Extensions();
            //ブロック教科区分
            exts.put(XApiConstant.EXT_KEY_SUBJT_DIV, weeklyPlanPerfEntity.getSubjtDiv());
            //ブロック単元ID
            exts.put(XApiConstant.EXT_KEY_UNIT_ID, weeklyPlanPerfEntity.getUnitId());
            //ブロック表示名
            exts.put(XApiConstant.EXT_KEY_BLOCK_DISPLAY_NM, weeklyPlanPerfEntity.getBlockDispyNm());
            //ブロック種類区分
            MstCodDEntity mstCodDEntity = mstCodDService.getOne(
                    new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "BLOCK_TYPE_DIV").eq("cod_cd", weeklyPlanPerfEntity.getBlockTypeDiv()).eq(
                            "del_flg", 0));
            String blockType = "";
            if (mstCodDEntity != null) {
                blockType = mstCodDEntity.getCodValue();
            }
            exts.put(XApiConstant.EXT_KEY_BLOCK_TYPE_DIV, weeklyPlanPerfEntity.getBlockTypeDiv() + ":" + blockType);
            //計画学習年月日
            exts.put(
                    XApiConstant.EXT_KEY_PLAN_YMD, weeklyPlanPerfEntity.getPlanYmd() != null ? DateUtils.format(weeklyPlanPerfEntity.getPlanYmd(),
                            GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH) : "");
            //計画学習開始時間
            exts.put(XApiConstant.EXT_KEY_PLAN_LEARN_START_TIME,
                    weeklyPlanPerfEntity.getPlanLearnStartTime() != null ? DateUtils.format(weeklyPlanPerfEntity.getPlanLearnStartTime(),
                            GakkenConstant.DATE_FORMAT_HH_MM_SS) : "");
            //実績学習年月日
            exts.put(
                    XApiConstant.EXT_KEY_PREF_YMD, weeklyPlanPerfEntity.getPerfYmd() != null ? DateUtils.format(weeklyPlanPerfEntity.getPerfYmd(),
                            GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH) : "");
            //実績学習開始時間
            exts.put(XApiConstant.EXT_KEY_PREF_LEARN_START_TIME,
                    weeklyPlanPerfEntity.getPerfLearnStartTime() != null ? DateUtils.format(weeklyPlanPerfEntity.getPerfLearnStartTime(),
                            GakkenConstant.DATE_FORMAT_HH_MM_SS) : "");
            //生徒計画学習時間（分）
            exts.put(XApiConstant.EXT_KEY_STU_PLAN_LEARN_TM, weeklyPlanPerfEntity.getStuPlanLearnTm());
            //実績学習時間（分）
            exts.put(XApiConstant.EXT_KEY_PREF_LEARN_TM, weeklyPlanPerfEntity.getPerfLearnTm());
            //理解度
            mstCodDEntity = mstCodDService.getOne(
                    new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "LEARN_LEV_UNDS").eq("cod_cd", weeklyPlanPerfEntity.getLearnLevUnds()).eq(
                            "del_flg", 0));
            String learnLevUnds = "";
            if (mstCodDEntity != null) {
                learnLevUnds = mstCodDEntity.getCodValue();
            }
            exts.put(XApiConstant.EXT_KEY_LEARN_LEV_UNDS, weeklyPlanPerfEntity.getLearnLevUnds() + ":" + learnLevUnds);
            try {
                XApiUtils.saveStatement(Verbs.terminated(), Activitys.schedule(), exts);
            } catch (Exception e) {
                e.printStackTrace();
            }
        /* 2020/11/13 V9.0 cuikailin add start */
        }else {
            //生徒ID
            model.setStuId(ShiroUtils.getUserEntity().getUsrId());
            //単元ID
            model.setUnitId(0);
            //生徒タームプラン設定ID
            model.setStuTermPlanId(null);
            //ブロック種類区分
//            model.setBlockTypeDiv("NP");
            //計画年月日
            model.setPlanYmd(perfLearnYmdDB);
            int planLearnTm = 0;
            if (endDate.compareTo(startDate) >= 0) {
                //実績学習時間(分)
                /*weeklyPlanPerfEntity.setPerfLearnTm((int)((perfLearnEndTimeDB.getTime() - perfLearnTimeDB.getTime()) / (1000 * 60)));*/
                planLearnTm = (int) ((perfLearnEndTimeDB.getTime() - perfLearnTimeDB.getTime()) / (1000 * 60));
            } else {
                //実績学習時間(分)
                /*weeklyPlanPerfEntity.setPerfLearnTm((int)((perfLearnEndTimeDB.getTime() - perfLearnTimeDB.getTime()) / (1000 * 60)) + 60 * 24);*/
                planLearnTm = (int) ((perfLearnEndTimeDB.getTime() - perfLearnTimeDB.getTime()) / (1000 * 60)) + 60 * 24;
            }
            //生徒計画学習時間（分）
            model.setStuPlanLearnTm(planLearnTm);
            //計画学習時期ID
            model.setPlanLearnSeasnId(null);
            //計画学習開始時間
            model.setPlanLearnStartTime(perfLearnTimeDB);
            //実績年月日
            model.setPerfYmd(perfLearnYmdDB);
            if (!StringUtils.equals(model.getLearnLevUnds(), "0")) {
                //実績学習開始時間
                model.setPerfLearnStartTime(perfLearnStartTime);
                //実績学習時間（分）
                model.setPerfLearnTm(perfLearnTm);
            }
            //タイマー時間（秒）
            model.setTimerTm(null);
            //積み残し対象フラグ
            model.setRemainDispFlg(remainDispFlg);
            //表示順番
            model.setDispyOrder(null);
            //作成日時
            model.setCretDatime(sysTimestamp);
            //作成ユーザＩＤ
            model.setCretUsrId(stuId);
            //更新日時
            model.setUpdDatime(sysTimestamp);
            //更新ユーザＩＤ
            model.setUpdUsrId(stuId);
            //削除フラグ
            model.setDelFlg(0);
            weeklyPlanPerfService.save(model);
            // 2020/12/4 huangxinliang modify start
            r.put("id", model.getId());
            // 2020/12/4 huangxinliang modify end
        }
        /* 2020/11/13 V9.0 cuikailin add end */
        if (perfLearnTimeDB!=null){
            // 2020/12/7 huangxinliang modify start
            //学習実績登録時、実績登録ポイントを取得する。
            CM0005.PointVo pointVo = CM0005.getPointVo(orgId);
            CM0005.addPoint(stuId, orgId, pointVo, 2, stuId,perfLearnTimeDB);
            //学習実績累計時間によって、累計時間ポイントを取得する。　
            CM0005.addPoint(stuId, orgId, pointVo, 3, stuId,sysTimestamp);
            // 2020/12/7 huangxinliang modify end
        }
        return r;
    }

    /* 2020/11/13 V9.0 cuikailin add start */
    /**
     * @param id ID
     * @param updateTime 更新日時
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public R del(Integer id,String updateTime) {
        //排他チェックエラーの場合、処理を中断し
        StuWeeklyPlanPerfEntity weeklyPlanPerfEntity = weeklyPlanPerfService.getById(id);
        if (weeklyPlanPerfEntity == null || !updateTime.equals(
                DateUtils.format(weeklyPlanPerfEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS))) {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        // 実績年月日
        weeklyPlanPerfEntity.setPerfYmd(null);
        // 実績学習開始時間
        weeklyPlanPerfEntity.setPerfLearnStartTime(null);
        // 実績学習時間（分）
        weeklyPlanPerfEntity.setPerfLearnTm(null);
        // タイマー時間（秒）
        weeklyPlanPerfEntity.setTimerTm(null);
        // 学習理解度
        weeklyPlanPerfEntity.setLearnLevUnds(null);
        // 積み残し対象フラグ
        weeklyPlanPerfEntity.setRemainDispFlg(null);
        // 更新日時
        weeklyPlanPerfEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ
        weeklyPlanPerfEntity.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
        // update
        weeklyPlanPerfService.update(weeklyPlanPerfEntity, new UpdateWrapper<StuWeeklyPlanPerfEntity>()
            .set("PERF_YMD",null)
            .set("PERF_LEARN_START_TIME",null)
            .set("PERF_LEARN_TM",null)
            .set("TIMER_TM",null)
            .set("LEARN_LEV_UNDS",null)
            .set("REMAIN_DISP_FLG",null)
            .eq("id",id)
        );
        return R.ok().put("wpp",weeklyPlanPerfEntity);
    }
    /* 2020/11/13 V9.0 cuikailin add end */
}
