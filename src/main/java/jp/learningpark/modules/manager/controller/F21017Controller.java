/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.manager.dto.F21017Dto;
import jp.learningpark.modules.manager.service.F21017Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>F21017_マスホ_生徒一覧画面_V6.0</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/21 : yang: 新規<br />
 * @version 6.0
 */
@RestController
@RequestMapping(value = "/manager/F21017")
public class F21017Controller {
    @Autowired
    F21017Service f21017Service;

    /**
     * <p>入室状況を取得する。</p>
     */
    @Autowired
    MstCodDService mstCodDService;

    private static Logger logger = LoggerFactory.getLogger(F21017Controller.class);

    // delete at 2021/08/16 for V9.02 by NWT wen START
    //    /**
    //     * @param
    //     * @return
    //     */
    //    @RequestMapping(value = "/init", method = RequestMethod.GET)
    //    public R init(boolean flg, Integer limit) {
    //        //ログインユーザー
    //        String roleDiv = ShiroUtils.getUserEntity().getRoleDiv().trim();
    //        //組織ID
    //        String orgId = ShiroUtils.getUserEntity().getOrgId();
    //        //ログインユーザーID
    //        String mentorId = ShiroUtils.getUserEntity().getUsrId();
    //        //ログイン当日（YYYYMMDD）
    //        String date = DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
    //        //今月
    //        String thisMonth = date.substring(5, 7);
    //        //当日
    //        String today = date.substring(5, 10);
    //        //今週
    //        Map<String, Integer> thisWeek = getWeekAndYear(date);
    //        //現在のページの生徒基本情報データを取得する
    //        List<F21017Dto> stuList = f21017Service.selectStuInfo(
    //                roleDiv, orgId, date, mentorId, (limit - GakkenConstant.NOTICE_PAGE_SIZE), GakkenConstant.NOTICE_PAGE_SIZE);
    //        //現在のページの生徒基本情報データを取得する
    //        List<F21017Dto> allStu = f21017Service.selectStuInfo(roleDiv, orgId, date, mentorId, null, null);
    //        //「本日入室済」
    //        List<F21017Dto> todayDatas = new ArrayList<>();
    //        Integer count = 0;
    //        if (StringUtils.equals("1", roleDiv)) {
    //            count = f21017Service.orgDatasCount(orgId);
    //        } else if (StringUtils.equals("2", roleDiv)) {
    //            count = f21017Service.mentorDatasCount(mentorId, orgId);
    //        }
    //        //上記取得できない場合
    //        if (stuList.size() == 0) {
    //            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒"));
    //        }
    //        //「本日入室済」ボタン押下
    //        if (flg) {
    //            for (F21017Dto stu : allStu) {
    //                if (stu.getEntryDt() != null) {
    //                    //入退室履歴データ・登校日時（ＹＹＹＹＭＭＤＤ）　＝　ログイン当日（ＹＹＹＹＭＭＤＤ）
    //                    if (StringUtils.equals(stu.getEntryDt().substring(0, 10), date)) {
    //                        todayDatas.add(stu);
    //                    }
    //                }
    //            }
    //            count = todayDatas.size();
    //            stuList = todayDatas;
    //            //上記取得できない場合
    //            if (stuList.size() == 0) {
    //                return R.error(MessageUtils.getMessage("MSGCOMN0017", "本日入室済"));
    //            }
    //            if (limit <= count) {
    //                stuList = stuList.subList(0, limit);
    //            } else {
    //                stuList = stuList.subList(0, count);
    //            }
    //        }
    //        for (F21017Dto stu : stuList) {
    //            stu.setPhotPath(StringUtils.isEmpty(stu.getPhotPath()) ? "../img/logo2.png" : stu.getPhotPath());
    //            //誕生日説明
    //            if (!StringUtils.isEmpty(stu.getBirthd())) {
    //                if (StringUtils.equals(today, stu.getBirthd().substring(5, 10))) {
    //                    stu.setBirthDayTxt("当日お誕生日です！");
    //                } else if (thisWeek.get("week").equals(getWeekAndYear(stu.getBirthd()).get("week"))) {
    //                    stu.setBirthDayTxt("今週お誕生日です！");
    //                } else if (StringUtils.equals(thisMonth, stu.getBirthd().substring(5, 7))) {
    //                    stu.setBirthDayTxt("今月お誕生日です！");
    //                } else {
    //                    stu.setBirthDayTxt("");
    //                }
    //            }
    //        }
    //        return R.ok().put("stuList", stuList).put("count", count);
    //    }
    // delete at 2021/08/16 for V9.02 by NWT wen END

    /**
     * <p>初期化</p>
     * <p>
     * add at 2021/08/10 for V9.02 by NWT wen
     *
     * @param map パラメータ
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public R init(@RequestBody Map<String, Object> map) {
        Integer limit = (Integer)map.get("limit");
        //ログインユーザー
        String roleDiv = ShiroUtils.getUserEntity().getRoleDiv().trim();
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ログインユーザーID
        String mentorId = ShiroUtils.getUserEntity().getUsrId();
        //ログイン当日（YYYYMMDD）
        String date = DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        //今月
        String thisMonth = date.substring(5, 7);
        //当日
        String today = date.substring(5, 10);
        //今週
        Map<String, Integer> thisWeek = getWeekAndYear(date);
        //現在のページの生徒基本情報データを取得する
        // add for NWT9.02 Lgx 2021/11/30 start
        Date startDay = DateUtils.parse(date, Constant.DATE_FORMAT_YYYY_MM_DD_BARS);
        Timestamp start = DateUtils.toTimestamp(startDay);
        Timestamp end = DateUtils.toTimestamp(DateUtils.addDays(startDay, 1));
        map.put("startTime",start);
        map.put("endTime",end);
        // add for NWT9.02 Lgx 2021/11/30 end
        map.put("roleDiv", roleDiv);
        map.put("orgId", orgId);
        map.put("date", date);
        map.put("mentorId", mentorId);
        map.put("offset", null);
        map.put("limit", null);
        Integer count = f21017Service.selectStudentCount(map);
        //上記取得できない場合
        if (count == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒"));
        }
        map.put("offset", limit - GakkenConstant.NOTICE_PAGE_SIZE);
        map.put("limit", GakkenConstant.NOTICE_PAGE_SIZE);
        List<F21017Dto> stuList = f21017Service.selectStuInfo(map);
        for (F21017Dto stu : stuList) {
            stu.setPhotPath(StringUtils.isEmpty(stu.getPhotPath()) ? "../img/logo2.png" : stu.getPhotPath());
            //誕生日説明
            if (!StringUtils.isEmpty(stu.getBirthd())) {
                if (StringUtils.equals(today, stu.getBirthd().substring(5, 10))) {
                    stu.setBirthDayTxt("当日お誕生日です！");
                    //2021/12/30　MANAMIRU1-871 huangxinliang　Edit　Start
                } else if (thisWeek.get("week").equals(getWeekAndYear(date.substring(0, 4) + stu.getBirthd().substring(4, 10)).get("week"))) {
                    //2021/12/30　MANAMIRU1-871 huangxinliang　Edit　End
                    stu.setBirthDayTxt("今週お誕生日です！");
                } else if (StringUtils.equals(thisMonth, stu.getBirthd().substring(5, 7))) {
                    stu.setBirthDayTxt("今月お誕生日です！");
                } else {
                    stu.setBirthDayTxt("");
                }
            }
        }
        // 入室状況リストを取得する。
        List<MstCodDEntity> statusList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "IN_ROOM_DIV").orderByAsc("sort"));
        //　学年リストを取得する。
        List<MstCodDEntity> schoolYearList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "SCHY_DIV").orderByAsc("sort"));
        //　曜日リストを取得する。
        List<MstCodDEntity> weekDay = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "DAYWEEK_DIV").orderByAsc("sort"));

        return R.ok().put("stuList", stuList).put("count", count).put("status", statusList).put("schoolYear", schoolYearList).put("weekDay", weekDay);
    }

    /**
     * 今周の取得方法
     *
     * @param date
     * @return
     */
    public static Map<String, Integer> getWeekAndYear(String date) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        Calendar cal = Calendar.getInstance();

        //设置一周的开始,默认是周日,这里设置成星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatMon = new SimpleDateFormat("MM");
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        Date d = null;
        try {
            d = format.parse(date);
            cal.setTime(d);
            int month = Integer.valueOf(formatMon.format(d));
            int year = Integer.valueOf(formatYear.format(d));

            int week = cal.get(Calendar.WEEK_OF_YEAR);
            result.put("week", week);
            if (week == 1 && month == 12) {
                result.put("year", year + 1);
            } else {

                result.put("year", year);
            }
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * 登校日時（降順）
     *
     * @param list
     * @return
     */
    public List<F21017Dto> order(List<F21017Dto> list) {
        List<F21017Dto> copyList1 = new ArrayList<>();
        List<F21017Dto> copyList2 = new ArrayList<>();
        List<F21017Dto> newList = new ArrayList<>();
        for (F21017Dto dto : list) {
            if (!StringUtils.isEmpty(dto.getEntryDt())) {
                copyList1.add(dto);
                copyList1 = copyList1.stream().sorted(Comparator.comparing(F21017Dto::getEntryDt)).collect(Collectors.toList());
            } else {
                copyList2.add(dto);
            }
        }
        Collections.reverse(copyList1);
        newList.addAll(copyList1);
        newList.addAll(copyList2);
        return newList;
    }
}
