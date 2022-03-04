package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import jp.learningpark.framework.utils.CM0005;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.service.MstVariousSetService;
import jp.learningpark.modules.manager.dto.F09005Dto;
import jp.learningpark.modules.manager.service.F09005Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p> 代理入退登録確認画面 Controller</p>
 * 変更履歴 <br />
 * 2019-11-29: zpa: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/manager/F09005")
public class F09005Controller {

    @Autowired
    F09005Service f09005Service;

    @Autowired
    MstVariousSetService mstVariousSetService;
    /**
     *
     * @param limit
     * @param page
     * @param params
     * @param stuidlistStr
     * @return R
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page, String params, String stuidlistStr,String status) {
        //選択されたstuidの集合
        List<String> stuidlist= (List<String>) JSON.parse(stuidlistStr);
        List<F09005Dto> f09005DtoList = f09005Service.init(stuidlist,status);
        return R.ok().put("page", new PageUtils(f09005DtoList, f09005DtoList.size(), limit, page));


    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public R save(String stuidlistStr,Date time, String status){
        List<String> stuidlist= (List<String>) JSON.parse(stuidlistStr);
        List<F09005Dto> f09005DtoList = f09005Service.init(stuidlist,status);
        // 2020/12/7 huangxinliang modify start
        CM0005.PointVo pointVo = CM0005.getPointVo(ShiroUtils.getUserEntity().getOrgId());
        MstNoticeEntity mstNoticeEntity = f09005Service.save(f09005DtoList, time, status, pointVo);
        return f09005Service.sendMessage(f09005DtoList,mstNoticeEntity,time,status);
    }
}

