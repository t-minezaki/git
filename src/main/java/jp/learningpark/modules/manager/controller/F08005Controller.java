/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F08005ScheduleDto;
import jp.learningpark.modules.manager.service.F08005Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>F08005 イベント管理日程の設定画面 Controller</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/07/31 : wq: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F08005")
@RestController
public class F08005Controller {

    /**
     * コードマスタ　Service
     */
    @Autowired
    F08005Service f08005Service;

    /**
     * @param eventId イベントID
     * @param userName ユーザー名
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer eventId, String userName) {
        return f08005Service.getInitData(eventId, userName);
    }

    /**
     * @param eventId 　イベントID
     * @param tgtYmd 　対象年月日
     * @return
     */
    @RequestMapping(value = "/eventSchedule", method = RequestMethod.GET)
    public R getEventSchedule(Integer eventId, String tgtYmd) {
        return f08005Service.getEventScheduleData(eventId, tgtYmd);
    }

    /**
     * @param entity
     * @return
     */
    @RequestMapping(value = "/updateEventSchedule", method = RequestMethod.POST)
    public R updateEventSchedule(F08005ScheduleDto entity) {
        return f08005Service.updateDB(entity);
    }
}
