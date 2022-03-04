/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.student.dto.F11013Dto;
import jp.learningpark.modules.student.service.F11010Service;
import jp.learningpark.modules.student.service.F11013Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>スマホ_メッセージ詳細</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/04/29 : lyh: 新規<br />
 * @version 7.0
 */
@RestController
@RequestMapping("/student/F11013")
public class F11013Controller {
    @Autowired
    F11013Service f11013Service;

    @Autowired
    F11010Service f11010Service;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer eventId) {
        F11013Dto f11013Dto = f11013Service.init(eventId);
        Date now = DateUtils.getSysTimestamp();
        String meg = "";
        //生徒はイベント申込可能か否かの判定
        if (now.before(f11013Dto.getApplyStartDt())) {
            //システム日時　＜　１．１取得したイベント．申込開始日時
            //時間の書式を設定
            String format = DateUtils.format(f11013Dto.getApplyStartDt(), GakkenConstant.DATE_FORMAT_M_D_H_M);
            meg = MessageUtils.getMessage("MSGCOMN0112", format);
        } else if (!now.before(f11013Dto.getApplyEndDt())) {
            //システム日時　＞　1.1取得したイベント．申込終了日時
            meg = MessageUtils.getMessage("MSGCOMN0108");
        } else if (StringUtils.equals(f11013Dto.getReplyStsDiv(), "1")) {
            meg = MessageUtils.getMessage("MSGCOMN013");
        }
        //申込期間内の2重申込避ける処理
        return R.ok().put("dto", f11013Dto).put("meg", meg);
    }
}