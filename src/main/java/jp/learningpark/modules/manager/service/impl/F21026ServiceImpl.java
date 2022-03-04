/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.modules.manager.dao.F21026Dao;
import jp.learningpark.modules.manager.dto.F21026Dto;
import jp.learningpark.modules.manager.service.F21026Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>F21017_マスホ_生徒一覧画面_V6.0</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/21 : yang: 新規<br />
 * @version 6.0
 */
@Service
@Transactional
public class F21026ServiceImpl implements F21026Service {

    @Autowired
    F21026Dao f21026Dao;

    @Override
    public F21026Dto getStuInfo(String orgId, String stuId,String date) {
        //2021/10/13　MANAMIRU1-809 huangxinliang　Edit　Start
        Date startDay = DateUtils.parse(date, Constant.DATE_FORMAT_YYYY_MM_DD_BARS);
        Timestamp start = DateUtils.toTimestamp(startDay);
        Timestamp end = DateUtils.toTimestamp(DateUtils.addDays(startDay, 1));
        return f21026Dao.getStu(orgId, stuId,date, start, end);
        // 2021/10/13　MANAMIRU1-809 huangxinliang　Edit　End
    }

    @Override
    public Integer login(String stuId, String date) {
        //2021/10/13　MANAMIRU1-809 huangxinliang　Edit　Start
        Date startDay = DateUtils.parse(date, Constant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        Timestamp start = DateUtils.toTimestamp(startDay);
        Timestamp end = DateUtils.toTimestamp(DateUtils.addDays(startDay, 1));
        return f21026Dao.login(stuId,date, start, end);
        // 2021/10/13　MANAMIRU1-809 huangxinliang　Edit　End
    }
}
