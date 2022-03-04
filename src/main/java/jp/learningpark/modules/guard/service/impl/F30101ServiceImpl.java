/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.MstHolidayEntity;
import jp.learningpark.modules.guard.dao.F30101Dao;
import jp.learningpark.modules.guard.dto.F30101Dto;
import jp.learningpark.modules.guard.dto.F30101stuConplimentDto;
import jp.learningpark.modules.guard.service.F30101Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>学習者の進捗一覧画面（デイリー）Service Impl</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/21 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
public class F30101ServiceImpl implements F30101Service {
    @Autowired
    F30101Dao f30101Dao;

    /**
     * <p>対象日より、生徒ウィークリー計画実績設定情報取得</p>
     *
     * @param stuId 生徒ID
     * @param stgtYmd 当月開始日
     * @return 生徒ウィークリー計画実績設定情報
     */
    @Override
    public List<F30101Dto> getPlanBlockList(String stuId, Date stgtYmd) {
        // 生徒計画ブロック情報取得
        return f30101Dao.getStuWeeklyPlanPerf(stuId, stgtYmd);
    }

    /**
     * 生徒固定ブロック情報取得
     *
     * @param stuId
     * @param tgtYmd
     * @return
     */
    @Override
    public List<F30101Dto> getSchdlBlockList(String stuId, Date tgtYmd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tgtYmd);
        int weekFlg = calendar.get(Calendar.DAY_OF_WEEK);
        // 生徒固定ブロック情報取得
        List<F30101Dto> listFixd = f30101Dao.getStuFixdAndAdjustSchdlInfo(stuId, tgtYmd, StringUtils.defaultString(weekFlg));
        return listFixd;
    }

    /**
     * @param stuId 生徒
     * @param orgId 　組織ID
     * @param stgtYmd 　当月開始日
     * @return
     */
    @Override
    public List<F30101stuConplimentDto> getStuComplimentList(String stuId, String orgId, Date stgtYmd) {
        return f30101Dao.getStuComplimentList(stuId, orgId, stgtYmd);
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
        cal.set(Calendar.DATE, timeCal.get(Calendar.DATE));
        cal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
        cal.set(Calendar.MILLISECOND, 0);

        return DateUtils.format(cal.getTime(), Constant.DATE_FORMAT_YYYYMMDDHHMM_ISO);
    }
}
