/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstHolidayEntity;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>画面名</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2021/01/06 : wq: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/com")
public class HolidayController extends AbstractController {

    /**
     *
     */
    @Autowired
    CommonService commonService;

    /**
     * @return
     */
    @RequestMapping(value = "/getHoliday")
    public R getHoliday(String year) {
        // 祝日判定
        List<MstHolidayEntity> holidayName = commonService.selectHolidayByTgtYmd(year);
        return R.ok().put("holiday", holidayName);
    }
}
