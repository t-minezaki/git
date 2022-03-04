package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstVariousSetEntity;
import jp.learningpark.modules.common.service.EntryExitHstService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstVariousSetService;
import jp.learningpark.modules.manager.dto.F09004Dto;
import jp.learningpark.modules.manager.service.F09004Service;
import jp.learningpark.modules.manager.service.F21026Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/manager/F09004")
public class F09004Controller {

    /**
     * mstOrgService
     */
    @Autowired
    MstOrgService mstOrgService;

    /**
     * f09004Service
     */
    @Autowired
    F09004Service f09004Service;

    /**
     * entryExitHstService
     */
    @Autowired
    EntryExitHstService entryExitHstService;

    /**
     * variousSetService
     */
    @Autowired
    MstVariousSetService mstVariousSetService;
    /**
     * f21026Service
     */
    @Autowired
    F21026Service f21026Service;

    /**
     *
     * @param limit
     * @param page
     * @param params
     * @return R
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page, String params) {

        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();

        //ログインユーザロール
        String roleDiv  =  ShiroUtils.getUserEntity().getRoleDiv().trim();

        //ログインユーザ
        String userId = ShiroUtils.getUserEntity().getUsrId();

        List<MstVariousSetEntity> mstVariousSetEntityList = mstVariousSetService.list(new QueryWrapper<MstVariousSetEntity>().eq("org_id", orgId).eq("del_flg", 0));

        Map<String,Object> paramsMap = (Map) JSON.parse(params);

        if (StringUtils.equals("true", paramsMap.get("isFirst").toString())){
            return R.ok().put("page", new PageUtils(new ArrayList<>(), 0, limit, page));
        }
        List<F09004Dto> f09004DtoList = f09004Service.init(orgId, paramsMap,roleDiv,userId);

        // 上記取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。
        if (f09004DtoList.isEmpty()) {

            return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0017", "利用者")).put("page", new PageUtils(f09004DtoList, f09004DtoList.size(), limit, page));
        }

        return R.ok().put("page", new PageUtils(f09004DtoList, f09004DtoList.size(), limit, page));
    }

    /**
     *
     * @param status
     * @param stuIdListStr
     * @return R
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public R check(Integer status, String stuIdListStr, Date time) {

        List<String> stuIdList = JSON.parseArray(stuIdListStr, String.class);

        if (stuIdList.isEmpty()) {
            // 2020/11/25 huangxinliang modify start
            return  R.error().put("msg", MessageUtils.getMessage("MSGCOMN0175", "対象", "１人"));
            // 2020/11/25 huangxinliang modify end
        }

        if (status == null) {
            // 2020/11/25 huangxinliang modify start
            return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0175", "ステータス", "１つ")).put("check", false);
            // 2020/11/25 huangxinliang modify end
        }

        for (String stuId : stuIdList) {
            switch (status) {
                // 登校が可否チェック
                case 0: {
                    // 画面．ステータス　＝　0：登校
//                    EntryExitHstEntity entryDt = entryExitHstService.getOne(new QueryWrapper<EntryExitHstEntity>().select("entry_dt").eq("stu_id",stuId));
                    if(f21026Service.login(stuId,DateUtils.format(time, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH)) % 2 != 0 ) {

                        // COUNT(入退室履歴．生徒ID　＝　ユーザー基本マスタ．ユーザーID)/2　＜＞０
                        return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0127", "入室", "退室")).put("check", false);
                    }
                    break;
                }
                // 下校が可否チェック
                case 1: {
                    if(f21026Service.login(stuId,DateUtils.format(time, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH)) % 2 == 0) {

                        // COUNT(入退室履歴．生徒ID　＝　ユーザー基本マスタ．ユーザーID)/2　＝　０
                        return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0127", "入室", "退室")).put("check", false);
                    }
                    break;
                }
                default: {

                    return R.error().put("msg", MessageUtils.getMessage("MSGCOMD0001", "ステータス")).put("check", false);
                }
            }
        }

        // 登校・下校が可能の場合
        return R.ok().put("check", true);
    }
}
