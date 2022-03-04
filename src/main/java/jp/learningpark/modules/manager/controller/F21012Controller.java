package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.manager.dto.F21012Dto;
import jp.learningpark.modules.manager.service.F21012Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 学習連絡確認
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/29 ： NWT)hxl ： 新規作成
 * @date 2019/11/29 18:01
 */
@RestController
@RequestMapping("/manager/F21012")
public class F21012Controller {
    @Autowired
    F21012Service f21012Service;

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
    public R saveOrUpdate(String list, String date, String startDate, String endDate, String statusCD, String guidReprDeliverCd,String noticeLevelDiv){
        List<F21012Dto> stuList = JSON.parseArray(list, F21012Dto.class);
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        return f21012Service.saveOrUpdate(stuList, orgId, guidReprDeliverCd, DateUtils.parse(date, Constant.DATE_FORMAT_YYYY_MM_DD_SLASH),
                DateUtils.parse(startDate, Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM), DateUtils.parse(endDate, Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM), statusCD,noticeLevelDiv);
    }
}
