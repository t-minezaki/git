/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */
package jp.learningpark.modules.com.controller;

import com.alibaba.fastjson.JSON;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.com.service.NoticeApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通知アプリ連携API
 *
 * @author NWT 楊
 */
@RestController
public class NoticeApiController {
    /**
     * 通知アプリ連携APIService
     */
    @Autowired
    NoticeApiService noticeApiService;
    Logger logger = LoggerFactory.getLogger(getClass());
    /* 2021/09/15 manamiru1-772 cuikl edit start */

    @PostMapping("/PUSHAPI")
    public R NoticePushApi(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        Map<String, Object> paramsMap = (Map)JSON.parse(URLDecoder.decode(params, "UTF-8"));
    /* 2021/09/15 manamiru1-772 cuikl edit end */
        switch (paramsMap.get("type").toString()) {
         // 2021/09/22 manamiru1-772 cuikl edit start
            case Constant.getBrandCdApi:
                logger.info("=====type:"+paramsMap.get("type")+"=====");
                return noticeApiService.getBrandCd(paramsMap.get("type").toString(), request);
            case Constant.appLoginCheck:
                logger.info("=====type:"+paramsMap.get("type")
                        +"||loginId:"+paramsMap.get("loginId")
                        +"||phoneType:"+paramsMap.get("phoneType")
                        + "||deviceToken:"+paramsMap.get("deviceToken")
                        +"=====");
                return noticeApiService.appLoginCheck(
                        paramsMap.get("loginId").toString(), paramsMap.get("password").toString(), paramsMap.get("deviceToken").toString(),
                        paramsMap.get("phoneType").toString(), request, response);
            case Constant.unReadGetApi:
                logger.info("=====type:"+paramsMap.get("type") + "||token:"+paramsMap.get("token") +"=====");
                return noticeApiService.unReadGet(request, paramsMap.get("token").toString(), 1);
            case Constant.readStsUpdateApi:
                logger.info("=====type:"+paramsMap.get("type") + "||token:"+paramsMap.get("token") +"=====");
                return noticeApiService.unReadGet(request, paramsMap.get("token").toString(), 2);
            case Constant.sendMessage:
                logger.info("=====type:"+paramsMap.get("type") + "||data:"+paramsMap.get("data") +"=====");
                return noticeApiService.sendMessage(paramsMap.get("data").toString());
            case Constant.getErrorData:
                logger.info("=====type:"+paramsMap.get("type")
                        +"||startDateTime:"+paramsMap.get("startDateTime")
                        + "||endDateTime:"+paramsMap.get("endDateTime")
                        +"=====");
                return noticeApiService.getErrorData(
                        Long.valueOf(paramsMap.get("startDateTime").toString()), Long.valueOf(paramsMap.get("endDateTime").toString()));
            case Constant.insert:
                logger.info("=====type:"+paramsMap.get("type") + "||result:"+paramsMap.get("result") + "=====");
                return noticeApiService.insert(paramsMap.get("result").toString());
            case Constant.logout:
                logger.info("=====type:"+paramsMap.get("type") + "||loginId:"+paramsMap.get("loginId") + "=====");
                //ログアウトAPIを改修 2020/11/11 modify yang
                return noticeApiService.logout(paramsMap.get("loginId").toString(), request);
            default:
                logger.error("=====type:"+paramsMap.get("type")+"=====");
        // 2021/09/22 manamiru1-772 cuikl edit end
                return R.error().put("message", "「タイプ」エラー").put("token", "");
        }
    }
}
