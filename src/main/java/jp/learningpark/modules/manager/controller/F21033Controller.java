package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstGrpEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstGrpService;
import jp.learningpark.modules.manager.service.F21033Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F21033ダッシュボードビュー Controller</p >
 *
 * @author NWT : yang <br />
 * @version 7.0
 */
@RestController
@RequestMapping("/manager/F21033")
public class F21033Controller {
    /**
     * グループマスタ service
     */
    @Autowired
    MstGrpService mstGrpService;
    /**
     * コードマスタ_明細 service
     */
    @Autowired
    MstCodDService mstCodDService;
    /**
     * f21033 service
     */
    @Autowired
    F21033Service f21033Service;
    String pattern = "yyyy-MM-dd";
    DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(pattern).parseDefaulting(ChronoField.MONTH_OF_YEAR, 1).parseDefaulting(
            ChronoField.DAY_OF_MONTH, 1).parseDefaulting(ChronoField.HOUR_OF_DAY, 0).toFormatter();
    Double maxValue = 0.0;
    /**
     * 初期表示
     * @param daysStr
     * @param type
     * @param grpId
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.POST)
    public R init(String daysStr, String type, Integer grpId) {
        //セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        List<String> days = new ArrayList<>();
        Map<Object, Object> schoolMap = new HashMap<>();
        Map<Object, Object> groupMap = new HashMap<>();
        if (daysStr != null) {
            days = (List<String>)JSON.parse(daysStr);
        }
        //登録者所属組織全グループを抽出
        List<MstGrpEntity> mstGrpEntityList = mstGrpService.list(
                new QueryWrapper<MstGrpEntity>().select("grp_id", "grp_nm").eq("org_id", orgId).eq("del_flg", 0).orderByAsc("grp_id"));
        //コードマスタ_明細から学年リストの値を抽出して
        List<MstCodDEntity> schyList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "SCHY_DIV").eq("del_flg", 0).orderByAsc("sort"));

        for (int i = 0; i < days.size(); i++) {
            Double schoolday = 0.0;
            Double groupday = 0.0;
            if (i == days.size() - 1) {
                //各対象週終了日
                LocalDateTime lastDate = LocalDateTime.parse(days.get(i), formatter);
                //タブ週
                if (StringUtils.equals("week", type)) {
                    //週対象週終了日を算出する
                    lastDate = lastDate.plusWeeks(1);
                }
                //タブ日
                else if (StringUtils.equals("day", type)) {
                    //日対象週終了日を算出する
                    lastDate = lastDate.plusDays(1);
                }
                //タブ月
                else {
                    //月対象週終了日を算出する
                    lastDate = lastDate.plusMonths(1);
                }
                String lastday = lastDate.format(formatter);
                // 画面の校舎平均の折れ線グラフが表示するため
                schoolday = f21033Service.getSchoolData(days.get(i), lastday, orgId);
                //画面の担当グループ平均の折れ線グラフが表示するため
                groupday = f21033Service.getGroupData(days.get(i), lastday, grpId);
            } else {
                // 画面の校舎平均の折れ線グラフが表示するため
                schoolday = f21033Service.getSchoolData(days.get(i), days.get(i + 1), orgId);
                //画面の担当グループ平均の折れ線グラフが表示するため
                groupday = f21033Service.getGroupData(days.get(i), days.get(i + 1), grpId);
            }
            //各対象日をもとに、組織単位で全て生徒の実績個数と計画個数を集計する
            schoolMap.put(days.get(i), schoolday);
            //各対象日をもとに、指定したグループ単位で全て生徒の実績個数と計画個数を集計する
            groupMap.put(days.get(i), groupday);
        }
        return R.ok().put("mstGrpEntityList", mstGrpEntityList).put("schoolMap", schoolMap).put("groupMap", groupMap).put("schyList",schyList);
    }
    @RequestMapping(value = "/getMap2",method = RequestMethod.POST)
    public R getMap2(String daysStr, String type, Integer grpId,String schyDiv){
        maxValue = 0.0;
        //セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        Map<Object, Object> schoolMap = new HashMap<>();
        Map<Object, Object> avgMap = new HashMap<>();
        Map<Object, Object> groupMap = new HashMap<>();
        List<String> days = new ArrayList<>();
        if (daysStr != null) {
            days = (List<String>)JSON.parse(daysStr);
        }
        for (int i = 0; i < days.size(); i++) {
            Double schoolday = 0.0;
            Double avgday = 0.0;
            Double groupday = 0.0;
            if (i == days.size() - 1) {
                //各対象週終了日
                LocalDateTime lastDate = LocalDateTime.parse(days.get(i), formatter);
                //タブ週
                if (StringUtils.equals("weekSchy", type)) {
                    //週対象週終了日を算出する
                    lastDate = lastDate.plusWeeks(1);
                }
                //タブ日
                else if (StringUtils.equals("daySchy", type)) {
                    //日対象週終了日を算出する
                    lastDate = lastDate.plusDays(1);
                }
                //タブ月
                else {
                    //月対象週終了日を算出する
                    lastDate = lastDate.plusMonths(1);
                }
                String lastday = lastDate.format(formatter);
                // 画面の校舎平均の折れ線グラフが表示するため
                schoolday = f21033Service.getMap2(days.get(i), lastday, orgId,schyDiv,grpId,"notGroup");
                Date startday = DateUtils.parse(days.get(i), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
                Date endday = DateUtils.parse(lastday, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
                //画面の担当グループ平均の折れ線グラフが表示するため
                avgday = f21033Service.getMap2(DateUtils.format(DateUtils.addYears(startday,-1),GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS),
                        DateUtils.format(DateUtils.addYears(endday,-1),GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS),orgId,schyDiv,grpId,"notGroup");
                groupday = f21033Service.getMap2(days.get(i), lastday,orgId,schyDiv,grpId,"group");
            } else {
                // 画面の校舎平均の折れ線グラフが表示するため
                schoolday = f21033Service.getMap2(days.get(i), days.get(i + 1), orgId,schyDiv,grpId,"notGroup");
                Date startday = DateUtils.parse(days.get(i), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
                Date endday = DateUtils.parse(days.get(i + 1), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
                //画面の担当グループ平均の折れ線グラフが表示するため
                avgday = f21033Service.getMap2(DateUtils.format(DateUtils.addYears(startday,-1),GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS),DateUtils.format(DateUtils.addYears(endday,-1),GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS),null,schyDiv, grpId,"notGroup");
                groupday = f21033Service.getMap2(days.get(i), days.get(i + 1), orgId,schyDiv,grpId,"group");
            }
            //各対象日をもとに、組織単位で全て生徒の実績個数と計画個数を集計する
            schoolMap.put(days.get(i), schoolday);
            //各対象日をもとに、指定したグループ単位で全て生徒の実績個数と計画個数を集計する
            avgMap.put(days.get(i), avgday);
            groupMap.put(days.get(i), groupday);
        }
        getDayMaxData(schoolMap,days,type);
        getDayMaxData(avgMap, days,type);
        getDayMaxData(groupMap, days,type);
        return R.ok().put("schoolMap", schoolMap).put("avgMap", avgMap).put("groupMap", groupMap).put("maxValue", maxValue);
    }

    public Double getDayMaxData(Map<Object, Object> map, List<String> days,String type) {
        for (int i = 0; i < days.size(); i++) {
            maxValue =  maxValue < (Double) map.get(days.get(i)) ? (Double) map.get(days.get(i)) : maxValue;
        }
        if (maxValue == 0.0){
            if (StringUtils.equals("daySchy", type)){
                maxValue = 600.0;
            }
            else if (StringUtils.equals("weekSchy", type)){
                maxValue = 4200.0;
            }
            else {
                maxValue = 18000.0;
            }
        }
        return maxValue;
    }

    @RequestMapping(value = "getInformation", method = RequestMethod.GET)
    public R getInformation(Integer limit, Integer page) {
        String userId = ShiroUtils.getUserId();
        return f21033Service.getInformation(userId, limit, page);
    }
}
