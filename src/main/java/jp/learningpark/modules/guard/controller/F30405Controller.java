/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.guard.dto.F30405Dto;
import jp.learningpark.modules.guard.service.F30405Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>保護者面談の日程設定画面 Controller</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/16 : wq: 新規<br />
 * @version 1.0
 */
@RequestMapping("/guard/F30405")
@RestController
public class F30405Controller {

    /**
     * 保護者面談の日程設定画面 Service
     */
    @Autowired
    private F30405Service f30405Service;

    /**
     * @param eventId イベントID
     * @param applyId 保護者イベント申込ID
     * @param tgtYmd 画面選択時間
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f30405Init(Integer eventId, Integer applyId, String tgtYmd, Boolean firstFlag) {
        return f30405Service.getInitData(eventId, applyId, tgtYmd, firstFlag);
    }

    /**
     * @param eventId
     * @return
     */
    @RequestMapping(value = "/getMin", method = RequestMethod.GET)
    public R getMinDate(Integer eventId) {
        return f30405Service.getMinDate(eventId);
    }

    /**
     * @param eventId イベントID
     * @param tgtYmd 画面選択時間
     * @return
     */
    @RequestMapping(value = "/timeselect", method = RequestMethod.GET)
    public R timeSelect(Integer eventId, String tgtYmd, String timeStr) {
        return f30405Service.timeSelect(eventId, tgtYmd, timeStr);
    }

    /**
     * @param f30405Dto
     * @param resultList
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(F30405Dto f30405Dto, String resultList) {
        return f30405Service.updateDB(f30405Dto, resultList);
    }

}
