/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.student.dto.F11004Dto;
import jp.learningpark.modules.student.service.F11004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * <p>学習情報登録一覧</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/24 : zpa: 新規<br />
 * @version 7.0
 */
@RestController
@RequestMapping("/student/F11004")
public class F11004Controller {
    @Autowired
    F11004Service f11004Service;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init() {
        String userId = ShiroUtils.getUserId();
        List<F11004Dto> dto = f11004Service.init(userId);
        if(dto != null && dto.size() != 0){
            for(F11004Dto f11004Dto : dto){

                Calendar now=Calendar.getInstance();
                //時間の書式を設定
                SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");

                now.setTime(f11004Dto.getPlanYmd());

                f11004Dto.setPlanYmd1(sdf1.format(now.getTimeInMillis()));

                int w=now.get(Calendar.DAY_OF_WEEK)-1;

                f11004Dto.setWeekDay(getweek(w));

                now.setTime(f11004Dto.getPlanLearnStartTime());

                String startTm = sdf.format(now.getTimeInMillis());
                //stuPlanStartTime
                f11004Dto.setStartPlanTm(startTm);
                //stuPlanStartTime+stuPlanLearnTime
                now.add(Calendar.MINUTE,f11004Dto.getStuPlanLearnTm());

                String dateStr=sdf.format(now.getTimeInMillis());
                //stuPlanEndTime
                f11004Dto.setEndPlanTm(dateStr);
            }
        }
        return R.ok().put("dto", dto);
    }

    public String getweek(int w){
        String day = "";
        if (w == 1) {
            day = "(月)";
        } else if (w == 2) {
            day = "(火)";
        } else if (w == 3) {
            day = "(水)";
        } else if (w == 4) {
            day = "(木)";
        } else if (w == 5) {
            day = "(金)";
        } else if (w == 6) {
            day = "(土)";
        } else if (w == 0) {
            day = "(日)";
        };
        return day;
    }
}