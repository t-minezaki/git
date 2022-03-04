/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.manager.dto.F09019Dto;
import jp.learningpark.modules.manager.service.F09019Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>F09019_一斉お知らせ配信（一覧）（スマホ） Controller</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/02/19 : wq: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/manager/F09019")
public class F09019Controller {

    /**
     * F09019_一斉お知らせ配信（一覧）Service
     */
    @Autowired
    F09019Service f09019Service;

    /**
     * @param limit
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R getInitData(Integer limit) throws UnsupportedEncodingException {
        Integer noticesCount = f09019Service.getNoticesCount(ShiroUtils.getUserEntity().getOrgId());
        if (noticesCount == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "お知らせ"));
        }
        List<F09019Dto> f09019Dtos = f09019Service.getNotices(ShiroUtils.getUserEntity().getOrgId(), (limit - GakkenConstant.NOTICE_PAGE_SIZE), GakkenConstant.NOTICE_PAGE_SIZE);
        return R.ok().put("f09019Dtos", f09019Dtos).put("noticesCount", noticesCount);
    }
}
