/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.StuPointEntity;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.service.StuPointService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/02/11 : gong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/common/F40002")
@RestController
public class F40002Controller extends AbstractController {

    /**
     * ユーザー基本マスタ　Service
     */
    @Autowired
    MstUsrService mstUsrService;

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

    /**
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init() {
        boolean disa = true;
        List<String> orgList = commonService.getOrgsForChoose(ShiroUtils.getUserEntity().getAfterUsrId(), ShiroUtils.getBrandcd());
        if (orgList.size() > 1) {
            disa = false;
        }
        /* 2020/12/17 V9.0 cuikailin add start */
        StuPointEntity stuPointEntity = stuPointService.getOne(
                new QueryWrapper<StuPointEntity>().eq("STU_ID", ShiroUtils.getUserEntity().getUsrId())
                        .eq("ORG_ID", ShiroUtils.getUserEntity().getOrgId()).eq("DEL_FLG", 0));
        Integer point = null;
        if (stuPointEntity != null) {
            point = stuPointEntity.getPoint() + stuPointEntity.getDoLoginPoint()
                    /* modify at 2021/08/12 for V9.02 by NWT LiGX START */
                    + stuPointEntity.getDoTotalPoint() + stuPointEntity.getInRoomPoint() + stuPointEntity.getMovePoint() + stuPointEntity.getAttendPoint()
                    + stuPointEntity.getBirthdayPoint();
                    /* modify at 2021/08/12 for V9.02 by NWT LiGX START */
        }
        /* 2020/12/17 V9.0 cuikailin add end */
        return R.ok().put("stuNm", ShiroUtils.getSessionAttribute(GakkenConstant.STU_NM)).put("disa", disa).put("point",point);
    }
}
