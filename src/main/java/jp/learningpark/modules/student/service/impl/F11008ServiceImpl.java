package jp.learningpark.modules.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.StuIndivSchdlAdjustDao;
import jp.learningpark.modules.common.entity.StuIndivSchdlAdjustEntity;
import jp.learningpark.modules.student.dao.F11008Dao;
import jp.learningpark.modules.student.dto.F11008Dto;
import jp.learningpark.modules.student.service.F11008Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/04/23 ： NWT)hxl ： 新規作成
 * @date 2020/04/23 11:03
 */
@Service
public class F11008ServiceImpl implements F11008Service {

    /**
     * f11008Dao
     */
    @Autowired
    F11008Dao f11008Dao;

    /**
     *
     */
    @Autowired
    StuIndivSchdlAdjustDao stuIndivSchdlAdjustDao;

    /**
     * 計画ブロックリストの取得
     *
     * @param stuId  生徒ID
     * @param tgtYmd 対象日付
     * @return
     */
    @Override
    public List<F11008Dto> getPlanBlockList(String stuId, Date tgtYmd) {
        // 生徒計画ブロック情報取得
        List<F11008Dto> listPlan = f11008Dao.getStuWeeklyPlanPerfInfo(stuId, tgtYmd);
        return listPlan;
    }

    /**
     * 生徒固定ブロック情報取得
     * @param stuId
     * @param tgtYmd
     * @return
     */
    @Override
    public List<F11008Dto> getSchdlBlockList(String stuId, Date tgtYmd, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekFlg = calendar.get(Calendar.DAY_OF_WEEK);
        Map<Integer, List<String>> map = new HashMap<>();
        // 生徒固定ブロック情報取得
        List<F11008Dto> listFixed = f11008Dao.getStuFixdAndAdjustSchdlInfo(stuId, tgtYmd, date, StringUtils.defaultString(weekFlg));
        List<StuIndivSchdlAdjustEntity> abolt = stuIndivSchdlAdjustDao.selectList(new QueryWrapper<StuIndivSchdlAdjustEntity>().select("stu_fixd_schdl_id",
                "plan_ymd").eq("stu_id", ShiroUtils.getUserId()).eq("fixd_block_abolt_flg", "1").between("plan_ymd", date, DateUtils.addMonths(date, 1)).eq(
                        "del_flg", 0));

        for (StuIndivSchdlAdjustEntity stuIndivSchdlAdjustEntity : abolt) {
            Integer stuFixdSchdlId = stuIndivSchdlAdjustEntity.getStuFixdSchdlId();
            if (map.containsKey(stuFixdSchdlId)) {
                List<String> strings = map.get(stuFixdSchdlId);
                strings.add(DateUtils.format(stuIndivSchdlAdjustEntity.getPlanYmd(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
            } else {
                List<String> stringList = new ArrayList<>();
                stringList.add(DateUtils.format(stuIndivSchdlAdjustEntity.getPlanYmd(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
                map.put(stuFixdSchdlId, stringList);
            }
        }
        for (F11008Dto dto : listFixed) {
            Integer fixId = dto.getId();
            if (map.containsKey(fixId)) {
                dto.setIsAbolt(StringUtils.join(map.get(fixId).toArray(), ','));
            } else {
                dto.setIsAbolt("");
            }
        }

        return listFixed;
    }

    /**
     * <p>日付+時間の変換処理</p>
     *
     * @param date 日付
     * @param time 時間
     * @return 日付(yyyy - MM - dd) + 時間(hh:mm)
     */
    public static String setTimeToISO(Date date, Timestamp time) {
        // 時間
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(time);
        // 日付
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.DATE,timeCal.get(Calendar.DATE));
        cal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
        cal.set(Calendar.MILLISECOND, 0);

        return DateUtils.format(cal.getTime(), Constant.DATE_FORMAT_YYYYMMDDHHMM_ISO);
    }
}
