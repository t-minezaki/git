/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.guard.dto.F30408Dto;
import jp.learningpark.modules.guard.service.F30408Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>保護者面談の結果表示</p >
 */
@RestController
@RequestMapping("guard/F30408/")
public class F30408Controller {

    /**
     * F30408Service
     */
    @Autowired
    F30408Service F30408Service;

    /**
     * 初期化  ページ
     * @param eventId イベントＩＤ
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R init(Integer eventId) {

        // 保護者ＩＤ
        String guardId = ShiroUtils.getUserEntity().getUsrId();

        List<F30408Dto> f30408DtoList = F30408Service.getEventNews(eventId,guardId);
        int size = F30408Service.getEventNews(eventId, guardId).size();
        if (size > 0){
            return R.ok().put("display" ,"block").put("applyId",f30408DtoList.get(0).getApplyId());
        }else {
            return R.ok().put("display" ,"none");
        }
    }
}