/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstGrpService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F00051Dto;
import jp.learningpark.modules.manager.service.F00051Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F00051 グループ検索一覧画面 Controller</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/18 : wen: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F00051")
@RestController
public class F00051Controller extends AbstractController {

    /**
     * グループマスタ Service
     */
    @Autowired
    MstGrpService mstGrpService;

    /**
     * F00051 Service
     */
    @Autowired
    F00051Service f00051Service;

    /**
     * 組織マスター　Service
     */
    @Autowired
    MstOrgService mstOrgService;

    /**
     * <p>初期表示</p>
     *
     * @param limit 当ページ数
     * @param page  各ページの最大記録数
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f00051Init(Integer limit, Integer page, String grpNm, String orgId, String orgIdListStr) {
        List<String> orgIdList = JSON.parseArray(orgIdListStr, String.class);

        if (orgIdList.isEmpty()) {

            orgIdList.add(!StringUtils.isEmpty(orgId)?orgId:ShiroUtils.getUserEntity().getOrgId());
        }

        MstOrgEntity mstOrgEntity = mstOrgService.getOne(
                new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", ShiroUtils.getUserEntity().getOrgId()).eq("del_flg", 0)));
        //グループマスタを元に一覧情報を取得
        List<F00051Dto> f00051DtoList = f00051Service.getGrpList(orgIdList, grpNm, (page - 1) * limit);

        f00051DtoList.forEach((e)->{
            e.setOrgId(ShiroUtils.getUserEntity().getOrgId());
            e.setOrgNm(mstOrgEntity.getOrgNm());
        });

        //取得できない場合
        if (f00051DtoList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "グループ")).put("mstOrgEntity", mstOrgEntity);
        }
        for (F00051Dto f00051Dto : f00051DtoList) {
            f00051Dto.setUpdateTimeForCheck(DateUtils.format(f00051Dto.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        }
        //生徒テスト目標結果Listの長さを取得する
        Integer totalSize = f00051Service.getGrpList(orgIdList, grpNm, null).size();
        return R.ok().put("mstOrgEntity", mstOrgEntity).put("page", new PageUtils(f00051DtoList, totalSize, limit, page));
    }

    /**
     * 削除ボタン
     *
     * @param grpId    グループID
     * @param updateTm 更新日時
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R deleteBtn(Integer grpId, String updateTm) {
        return f00051Service.delete(grpId, updateTm);
    }
}
