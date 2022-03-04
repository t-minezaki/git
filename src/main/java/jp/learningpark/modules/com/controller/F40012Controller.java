/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.com.service.F40012Service;
import jp.learningpark.modules.common.entity.StuPointEntity;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.service.StuPointService;
import jp.learningpark.modules.common.utils.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>生徒共通メニュー画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/23 : zpa: 新規<br />
 * @version 7.0
 */
@RequestMapping("/common/F40012")
@RestController
public class F40012Controller {

    /**
     * ユーザー基本マスタ　Service
     */
    @Autowired
    MstUsrService mstUsrService;

    /**
     * 生徒共通メニュー画面
     */
    @Autowired
    F40012Service f40012Service;

    /**
     * 共通処理 Service
     */
    @Autowired
    CommonService commonService;

    /* 2020/12/17 V9.0 cuikailin add start */
    /**
     *生徒ポイント Service
     */
    @Autowired
    StuPointService stuPointService;
    /* 2020/12/17 V9.0 cuikailin add end */

    @RequestMapping(value="/init",method = RequestMethod.GET)
    public R init(){
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String userId = ShiroUtils.getUserId();
        boolean disa = true;
        List<String> orgList = commonService.getOrgsForChoose(ShiroUtils.getUserEntity().getAfterUsrId(), ShiroUtils.getBrandcd());
        if (orgList.size() > 1) {
            disa = false;
        }
        Integer cou = f40012Service.getNum(orgId,userId);
        /* 2020/12/17 V9.0 cuikailin add start */
        StuPointEntity stuPointEntity = stuPointService.getOne(
                new QueryWrapper<StuPointEntity>().eq("STU_ID", ShiroUtils.getUserEntity().getUsrId())
                        .eq("ORG_ID", ShiroUtils.getUserEntity().getOrgId()).eq("DEL_FLG", 0));
        Integer point = null;
        if (stuPointEntity != null) {
            point = stuPointEntity.getPoint() + stuPointEntity.getDoLoginPoint()
                    + stuPointEntity.getDoTotalPoint() + stuPointEntity.getInRoomPoint() + stuPointEntity.getMovePoint()
                    /* modify at 2021/08/12 for V9.02 by NWT LiGX START */
                    + stuPointEntity.getAttendPoint() + stuPointEntity.getBirthdayPoint();
                    /* modify at 2021/08/12 for V9.02 by NWT LiGX START */
        }
        /* 2020/12/17 V9.0 cuikailin add end */
        return R.ok().put("cou",cou).put("disa", disa).put("point",point);
    }
}