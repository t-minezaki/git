/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.StuTermPlanEntity;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import jp.learningpark.modules.common.entity.TextbDefTimeInfoEntity;
import jp.learningpark.modules.common.service.StuTermPlanService;
import jp.learningpark.modules.common.service.StuWeeklyPlanPerfService;
import jp.learningpark.modules.common.service.TextbDefTimeInfoService;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * <p>F10306_学習単元確認画面(POP)</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/10/08 : gong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/pop/F10306")
public class F10306Controller extends AbstractController {

    /**
     * 生徒ウィークリー計画実績設定 Service
     */
    @Autowired
    private StuWeeklyPlanPerfService weeklyPlanPerfService;

    /**
     * 教科書デフォルトターム情報 Service
     */
    @Autowired
    private TextbDefTimeInfoService textbDefTimeInfoService;

    /**
     * 生徒タームプラン設定 Service
     */
    @Autowired
    private StuTermPlanService termPlanService;

    /**
     * <p>F10306 初期画面</p>
     *
     * @param termId 生徒タームプラン設定．ID
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer termId) {
        //生徒タームプランの情報
        StuTermPlanEntity termPlanEntity = termPlanService.getById(termId);
        //生徒ウィークリー計画実績設定の情報
        StuWeeklyPlanPerfEntity weeklyPlanPerfEntity = new StuWeeklyPlanPerfEntity();
        //教科書デフォルトターム情報
        TextbDefTimeInfoEntity TextbDefTimeInfoEntity=new TextbDefTimeInfoEntity();
        //更新日時
        String updateTime = "";
        //学習時期開始日
        Date learnPrdStartDate = DateUtils.getMonday(DateUtils.getSysDate());
        //積み残し対象フラグの判断する
        boolean dataFlg = false;
        if (termPlanEntity != null) {
            //教科書デフォルトターム情報の情報が取得する
            TextbDefTimeInfoEntity=textbDefTimeInfoService.getById(termPlanEntity.getTextbDefUnitId());

            //生徒ウィークリー計画実績設定の情報が取得する
            weeklyPlanPerfEntity = weeklyPlanPerfService.getOne(new QueryWrapper<StuWeeklyPlanPerfEntity>().and(wrapper->wrapper.eq("stu_term_plan_id",termId).eq("del_flg",0)));
            if (weeklyPlanPerfEntity != null) {
                //更新日時
                updateTime = DateUtils.format(weeklyPlanPerfEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
                //積み残し対象フラグの判断する
//                if (StringUtils.equals(weeklyPlanPerfEntity.getRemainDispFlg(), "1") && weeklyPlanPerfEntity.getPlanYmd().compareTo(learnPrdStartDate) < 0 && weeklyPlanPerfEntity.getLearnLevUnds()!=null && Integer.parseInt(weeklyPlanPerfEntity.getLearnLevUnds())>0) {
//                    remainDispFlg = true;
//                }
//                boolean flg = weeklyPlanPerfEntity.getLearnLevUnds() == null || StringUtils.equals(weeklyPlanPerfEntity.getLearnLevUnds(), "0");
//                if (StringUtils.equals(weeklyPlanPerfEntity.getRemainDispFlg(), "0") && flg && weeklyPlanPerfEntity.getPlanYmd().compareTo(learnPrdStartDate) < 0) {
//                    remainDispFlg = true;
//                }
                if(weeklyPlanPerfEntity.getPlanYmd().compareTo(learnPrdStartDate)<0){
                    dataFlg=true;
                }
            }
        }
        return R.ok()
                .put("term", termPlanEntity)
                .put("wpp", weeklyPlanPerfEntity)
                .put("textDff",TextbDefTimeInfoEntity)
                .put("dataFlg",dataFlg)
                .put("updateTime", updateTime);
//                .put("remainDispFlg", remainDispFlg);
    }

    /**
     * <p>削除ボタンの処理(論理削除)</p>
     *
     * @param id 生徒ウィークリー計画実績設定TableID
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public R f10306del(Integer id, String updateTime) {
        StuWeeklyPlanPerfEntity weeklyPlanPerfEntity = new StuWeeklyPlanPerfEntity();
        //生徒ID
        String stuId = getUserCd();
        //排他チェック
        StuWeeklyPlanPerfEntity old = weeklyPlanPerfService.getById(id);
        //もう削除されました
        if (old == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        //もう更新しました
        /* 2021/09/16 manamiru1-772 cuikl del start */
        /* 2021/09/16 manamiru1-772 cuikl del end */
        if (!StringUtils.equals(DateUtils.format(old.getUpdDatime(),GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS),updateTime)) {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        //id
        weeklyPlanPerfEntity.setId(id);
        //生徒削除フラグ
        weeklyPlanPerfEntity.setStuDelFlg("1");
//        if(old.getLearnLevUnds()==null){
//            //積み残し対象フラグ
//            weeklyPlanPerfEntity.setRemainDispFlg("2");
//        }else{
//            int i=Integer.parseInt(old.getLearnLevUnds());
//            if(i==0){
//                //積み残し対象フラグ
//                weeklyPlanPerfEntity.setRemainDispFlg("2");
//            }else{
//                //積み残し対象フラグ
//                weeklyPlanPerfEntity.setRemainDispFlg("0");
//            }
//        }
        //積み残し対象フラグ
        weeklyPlanPerfEntity.setRemainDispFlg("4");
        //更新日時
        weeklyPlanPerfEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        weeklyPlanPerfEntity.setUpdUsrId(stuId);
        weeklyPlanPerfService.updateById(weeklyPlanPerfEntity);
        return R.ok().put("stuDelFlg", "1").put("remainDispFlg", weeklyPlanPerfEntity.getRemainDispFlg());
    }
}
