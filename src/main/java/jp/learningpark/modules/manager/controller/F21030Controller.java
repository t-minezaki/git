package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.manager.service.F21030Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * F21030Controller
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/17 ： NWT)hxl ： 新規作成
 * @date 2020/02/17 11:55
 */
@RestController
@RequestMapping("/manager/F21030")
public class F21030Controller {

    /**
     * f21030Service
     */
    @Autowired
    F21030Service f21030Service;

    /**
     *
     * @param isFirst       ページめくりと初期化
     * @param pageTurning   ページめくりサイン
     * @param date          最低日
     * @param grpId         グループID
     * @param stuNm         学徒名
     * @param schyDiv       学年区分
     * @param stuIds        学徒IDリスト
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public R getData(Boolean isFirst, Boolean pageTurning, String date, Integer grpId, String stuNm, String schyDiv, String stuIds){
        Map<String, Object> params = new HashMap<>(16);
        //組織ID
        params.put("orgId", ShiroUtils.getUserEntity().getOrgId());
        //ログインユーザーID
        params.put("usrId", ShiroUtils.getUserId());
        //初期化かどうか
        params.put("isFirst", isFirst);
        //ページめくりサイン
        params.put("pageTurning", pageTurning);
        //minDate
        params.put("date", DateUtils.parse(date, Constant.DATE_FORMAT_YYYY_MM_DD_BARS));
        //ロール区分
        params.put("roleDiv", StringUtils.trim(ShiroUtils.getUserEntity().getRoleDiv()));
        //グループ選択
        if (grpId != null){
            params.put("grpId", grpId);
        }
        //生徒名
        if (!StringUtils.isEmpty(stuNm)){
            params.put("stuNm", stuNm);
        }
        //学年選択
        if (!StringUtils.isEmpty(schyDiv)){
            params.put("schyDiv", schyDiv);
        }
        //前画面に転送されたの生徒名IDリスト
        if (!StringUtils.isEmpty(stuIds)){
            //String -> list
            List<String> stuIdList = JSON.parseObject(StringUtils.defaultString(stuIds), new TypeReference<List<String>>() {
            });
            params.put("stuIdList", stuIdList);
        }
        return f21030Service.getLineDate(params);
    }
}
