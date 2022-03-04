/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dto.F00044Dto;
import jp.learningpark.modules.manager.service.F00044Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>F00044_生徒保護者関係検索一覧画面</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/03/15 : tan: 新規<br />
 * @version 1.0
 */

@RequestMapping("/manager/F00044")
@RestController
public class F00044Controller {
    @Autowired
    private F00044Service f00044Service;

    @Autowired
    MstOrgService mstOrgService;
    /**
     * 共通 Service
     */
    @Autowired
    private CommonService commonService;
    /**
     * 生徒基本マスタ Service
     */
    @Autowired
    private MstStuService mstStuService;
    /**
     * 初期化
     *
     * @param limit １ページ最大件数
     * @param page  現在のページ数
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page, String orgIdList, String orgId) {
        // 組織名
        String orgNm = "";
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", ShiroUtils.getUserEntity().getOrgId())));
        if (org != null) {
            orgNm = org.getOrgNm();
        }
        orgId = orgId.isEmpty()?ShiroUtils.getUserEntity().getOrgId():orgId;
        R r = getDetail(orgId, null, null, null, null, null, limit, page, orgIdList);
        return r.put("sOrgId", ShiroUtils.getUserEntity().getOrgId()).put("orgNm", orgNm);
    }


    /**
     * 検索ボタン押下
     *
     * @param stuId     画面．検索条件．生徒ID
     * @param guardId   画面．検索条件．保護者ID
     * @param stuName   画面．検索条件．生徒姓名
     * @param guardName 画面．検索条件．保護者姓名
     * @param schy      画面．検索条件．学年
     * @param limit     １ページ最大件数
     * @param page      現在のページ数
     * @param orgIdList     画面  检索条件  組織ID
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public R search(String orgId, String stuId, String guardId, String stuName, String guardName, String schy, Integer limit, Integer page, String orgIdList) {
        return getDetail(orgId, stuId, guardId, stuName, guardName, schy, limit, page, orgIdList);
    }

    @RequestMapping(value = "/del",method = RequestMethod.GET)
    public R del(String id){
        String guardId = id.split(",")[0];
        String stuId= id.split(",")[1];
        MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id",stuId));
        if (guardId.equals(mstStuEntity.getGuardId())){
            mstStuEntity.setGuardId("");
        }else if (guardId.equals(mstStuEntity.getGuard1Id())){
            mstStuEntity.setGuard1Id("");
        }else if (guardId.equals(mstStuEntity.getGuard2Id())){
            mstStuEntity.setGuard2Id("");
        }else if (guardId.equals(mstStuEntity.getGuard3Id())){
            mstStuEntity.setGuard3Id("");
        }else if (guardId.equals(mstStuEntity.getGuard4Id())){
            mstStuEntity.setGuard4Id("");
        }
        mstStuService.updateById(mstStuEntity);
    return R.ok();
    }


    /**
     * 初期化学年区分を取得
     *
     * @return
     */
    @RequestMapping(value = "/schySearch", method = RequestMethod.GET)
    public R schySearch() {
        List<F00044Dto> schySearchList = f00044Service.getSchySearch();

        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        //本組織及び下層組織リストの取得
        List<OrgAndLowerOrgIdDto> orgIdList = commonService.getThisAndLowerOrgId(brandCd, orgId);
        return R.ok().put("schySearchList", schySearchList).put("orgIdList",orgIdList);
    }

    /**
     * 初期化
     *
     * @param stuId     画面．検索条件．生徒ID
     * @param guardId   画面．検索条件．保護者ID
     * @param stuName   画面．検索条件．生徒姓名
     * @param guardName 画面．検索条件．保護者姓名
     * @param schy      画面．検索条件．学年
     * @param limit     １ページ最大件数
     * @param page      現在のページ数
     * @param orgIdList     画面  检索条件  組織ID
     * @return
     */
    private R getDetail(String orgId, String stuId, String guardId, String stuName, String guardName, String schy, Integer limit, Integer page, String orgIdList) {
        List<String> orgIds = (List<String>)JSON.parse(orgIdList);
        if (orgIds.size() == 0) {
            orgIds.add(orgId);
        }
        List<F00044Dto> list = f00044Service.getSearch(stuId, guardId, stuName, guardName, schy, limit, (page - 1) * limit, orgIds);
//        for (F00044Dto dto:list) {
//            //オリジナルプロテクター
//            MstStuEntity firstGuard = mstStuService.getOne(new QueryWrapper<MstStuEntity>().select("guard_id").eq("stu_id",dto.getStuUi()).eq("del_flg",0));
//            if (firstGuard != null){
//                dto.setFirstGuard(firstGuard.getGuardId());
//            }
//
//        }
        //総件数をとる
        Integer total = f00044Service.getTotalCount(stuId, guardId, stuName, guardName, schy, orgIds);
        if (list.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒"));
        }
        return R.ok().put("page", new PageUtils(list, total, limit, page));
    }
}

