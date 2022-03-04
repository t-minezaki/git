/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/13 : gong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/common/F40005")
@RestController
public class F40005Controller {

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R getInit(String startDate, String endDate) {
        if (startDate == null|| StringUtils.equals(startDate,"undefined")||StringUtils.isEmpty(startDate)||StringUtils.equals("null",startDate)) {
            startDate = DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
            endDate = DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        }
        return R.ok().put("startDate", startDate).put("endDate", endDate);
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R check(String start, String end) {
        Date startDate = DateUtils.parse(start, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        Date endDate = DateUtils.parse(end, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        if (startDate.compareTo(endDate) > 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0024", "期間終了日", "期間開始日"));
        }
        return R.ok();
    }
}
