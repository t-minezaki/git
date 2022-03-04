/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.StuGrpEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.StuGrpService;
import jp.learningpark.modules.manager.dto.F00053Dto;
import jp.learningpark.modules.manager.service.F00053Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>F00053_生徒グループ関係検索一覧画面 Controller</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/3/18 : tan: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("manager/F00053/")
public class F00053Controller extends AbstractController {
    /**
     * F00053_生徒グループ関係検索一覧画面 Service
     */
    @Autowired
    private F00053Service f00053Service;

    @Autowired
    MstOrgService mstOrgService;
    @Autowired
    StuGrpService stuGrpService;

    /**
     * 初期化
     *
     * @param limit １ページ最大件数
     * @param page  現在のページ数
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page) {
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 組織リステ
        List<MstOrgEntity> mstOrgEntityList = mstOrgService.list(new QueryWrapper<MstOrgEntity>().eq("upplev_org_id", orgId).eq("del_flg", 0));
        // 下位組織は再帰的に獲得する
        mstOrgEntityList = getOrgUpplevList(mstOrgEntityList);
        mstOrgEntityList.add(mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id", orgId).eq("del_flg", 0)));
        // 組織名
        String orgNm = "";
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", ShiroUtils.getUserEntity().getOrgId())));
        if (org != null) {
            orgNm = org.getOrgNm();
        }
        List<F00053Dto> list = f00053Service.getGroupList(ShiroUtils.getUserEntity().getOrgId(),null,limit, (page - 1) * limit);
        //総件数をとる
        Integer total = f00053Service.getTotalCount(ShiroUtils.getUserEntity().getOrgId(),null);
        if (list.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒グループ")).put("orgNm", orgNm);
        }
        return R.ok().put("page", new PageUtils(list, total, limit, page)).put("sOrgId", ShiroUtils.getUserEntity().getOrgId()).put("orgNm", orgNm).put("mstOrgEntityList", mstOrgEntityList);
    }

    /**
     * 下部組織を再帰的に獲得する
     * @param mstOrgEntityList
     * @return
     */
    private List<MstOrgEntity> getOrgUpplevList(List<MstOrgEntity> mstOrgEntityList) {

        // データを返す
        List<MstOrgEntity> retmstOrgEntityList = new ArrayList<>();
        retmstOrgEntityList.addAll(mstOrgEntityList);
        for (MstOrgEntity mstOrgEntity : mstOrgEntityList) {

            // 再帰的獲得
            List<MstOrgEntity> childMstOrgEntityList = mstOrgService.list(new QueryWrapper<MstOrgEntity>().eq("upplev_org_id", mstOrgEntity.getOrgId()).eq("del_flg", 0));
            retmstOrgEntityList.addAll(getOrgUpplevList(childMstOrgEntityList));
        }

        return retmstOrgEntityList;
    }

    /**
     * 検索ボタン押下
     *
     * @param groupName 画面．検索条件．グループ名
     * @param limit     １ページ最大件数
     * @param page      現在のページ数
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public R searchFn( String selectOrgId,String groupName, Integer limit, Integer page) {

        if (selectOrgId.isEmpty()) {
            selectOrgId = ShiroUtils.getUserEntity().getOrgId();
        }

        List<F00053Dto> list = f00053Service.getGroupList(selectOrgId,groupName,limit, (page - 1) * limit);
        //総件数をとる
        Integer total = f00053Service.getTotalCount(selectOrgId,groupName);
        if (list.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒グループ"));
        }
        return R.ok().put("page", new PageUtils(list, total, limit, page));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R deleteBtn(Integer groupId) {
        List<StuGrpEntity> stuGrpEntity = stuGrpService.list(new QueryWrapper<StuGrpEntity>().eq("grp_id", groupId).eq("del_flg", 0));
        for (StuGrpEntity stuGrp : stuGrpEntity ){
            stuGrp.setUpdDatime(DateUtils.getSysTimestamp());
            stuGrp.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
            stuGrp.setDelFlg(1);
            stuGrpService.update(stuGrp,new QueryWrapper<StuGrpEntity>().eq("grp_id",stuGrp.getGrpId()));
        }
        return R.ok();
    }
}
