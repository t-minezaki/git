package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstFunEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstOrgFunListEntity;
import jp.learningpark.modules.common.entity.MstUserNmFunListEntity;
import jp.learningpark.modules.common.service.MstFunService;
import jp.learningpark.modules.common.service.MstOrgFunListService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstUserNmFunListService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dto.F40010DspDto;
import jp.learningpark.modules.manager.dto.F40010ManagerTypeDto;
import jp.learningpark.modules.manager.service.F40010Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping(value = {"/manager/F40010"})
public class F40010Controller {

    /**
     * mstUsrService
     */
    @Autowired
    MstUsrService mstUsrService;

    /**
     * mstFunService
     */
    @Autowired
    MstFunService mstFunService;

    /**
     * mstOrgFunListService
     */
    @Autowired
    MstOrgFunListService mstOrgFunListService;

    /**
     * mstOrgService
     */
    @Autowired
    MstOrgService mstOrgService;

    /**
     * f40010Service
     */
    @Autowired
    F40010Service f40010Service;

    /**
     * mstUserNmFunListService
     */
    @Autowired
    MstUserNmFunListService mstUserNmFunListService;

    /**
     *
     */
    @Autowired
    CommonService commonService;

    /**
     * ???????????????
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init() {
        // ??????ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //???????????????????????????????????????
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id",orgId).eq("del_flg",0));
        // ?????????
        String orgNm = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id", orgId)).getOrgNm();
        List<String> funIdList = new ArrayList<>();
        if (mstOrgEntity.getLevel()==1){
            List<MstFunEntity> mstFunEntityList = mstFunService.list(new QueryWrapper<MstFunEntity>().orderByAsc("junban_num").eq("del_flg",0));
            for (MstFunEntity mstFunEntity : mstFunEntityList) {
                funIdList.add(mstFunEntity.getFunId());
            }
        }else {
            // ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            List<MstOrgFunListEntity> mstOrgFunListEntityList = mstOrgFunListService.list(new QueryWrapper<MstOrgFunListEntity>().eq("org_id", mstOrgEntity.getLevel()==1?orgId:mstOrgEntity.getUpplevOrgId()).eq("del_flg", 0).last("and (mgr_flg = '1' or ment_flg = '1')"));
            for (MstOrgFunListEntity mstFunEntityList : mstOrgFunListEntityList) {
                funIdList.add(mstFunEntityList.getFunId());
            }
        }


        List<MstFunEntity> mstFunEntityList = mstFunService.list(new QueryWrapper<MstFunEntity>().in("fun_id", funIdList).orderByAsc("junban_num").eq("del_flg", 0));
        // ????????????????????????
        List<F40010ManagerTypeDto> f40010ManagerTypeDtoList = f40010Service.getDiv(mstFunEntityList);

        if (funIdList.size() <= 0) {
            // ????????????????????????????????????????????????????????????????????????????????????
            mstFunEntityList = mstFunService.list(new QueryWrapper<MstFunEntity>().orderByAsc("junban_num").eq("del_flg", 0));
            f40010ManagerTypeDtoList = f40010Service.getDiv(mstFunEntityList);

            if (f40010ManagerTypeDtoList.size() <= 0) {
                // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0035", "'?????????")).put("orgId", orgId).put("orgNm", orgNm);
            }
        }

        return R.ok().put("managerTypeList", f40010ManagerTypeDtoList).put("orgId", orgId).put("orgNm", orgNm);
    }

    /**
     * ???????????????????????????
     * @return
     */
    @RequestMapping(value = {"/getOrgList"}, method = RequestMethod.GET)
    public R getOrgList() {

        // ??????ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String brandCd = ShiroUtils.getBrandcd();
        // ???????????????
        List<OrgAndLowerOrgIdDto> orgIdList = commonService.getThisAndLowerOrgId(brandCd, orgId);

        return R.ok().put("mstOrgEntityList", orgIdList);
    }

    /**
     * ???????????????????????????????????????
     * @param mstOrgEntityList
     * @return
     */
    private List<MstOrgEntity> getOrgUpplevList(List<MstOrgEntity> mstOrgEntityList) {

        // ??????????????????
        List<MstOrgEntity> retmstOrgEntityList = new ArrayList<>();
        retmstOrgEntityList.addAll(mstOrgEntityList);
        for (MstOrgEntity mstOrgEntity : mstOrgEntityList) {

            // ???????????????
            List<MstOrgEntity> childMstOrgEntityList = mstOrgService.list(new QueryWrapper<MstOrgEntity>().eq("upplev_org_id", mstOrgEntity.getOrgId()).eq("del_flg", 0));
            retmstOrgEntityList.addAll(getOrgUpplevList(childMstOrgEntityList));
        }

        return retmstOrgEntityList;
    }

    /**
     * ???????????????
     * @param funcIdListStr
     * @param orgIdListStr
     * @param userIdListStr
     * @param managerOrMentor
     * @param pageDiv
     * @return
     */
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    public R save(String funcIdListStr, String orgIdListStr, String userIdListStr, Integer managerOrMentor, Integer pageDiv) {

        // ??????ID
        String nowOrgId = ShiroUtils.getUserEntity().getOrgId();

        //????????????ID
        String userId = ShiroUtils.getUserEntity().getUsrId();

        // ??????ID?????????
        List<Integer> funcIdList = JSON.parseArray(funcIdListStr, Integer.class);

        List<MstFunEntity> mstFunEntityList = new ArrayList<>();
        if (funcIdList.size() > 0) {
            mstFunEntityList = mstFunService.list(new QueryWrapper<MstFunEntity>().in("id", funcIdList).eq("del_flg", 0));
        }else {
            return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0126"));
        }

        List<String> funcIdStrList = new ArrayList<>();

        mstFunEntityList.forEach((e) -> funcIdStrList.add(e.getFunId()));

//        List<MstOrgFunListEntity> mstOrgFunListEntityList = mstOrgFunListService.list(new QueryWrapper<MstOrgFunListEntity>().eq("org_id", nowOrgId).eq("mgr_flg", "1").or().eq("ment_flg", "1").eq("del_flg", 0));

//        List<String> nowFunIdList = new ArrayList<>();

//        if (mstOrgFunListEntityList.size() > 0) {
//
//            mstOrgFunListEntityList.forEach((e)->nowFunIdList.add(e.getFunId()));
//        }else {
//
//            mstFunService.list(new QueryWrapper<MstFunEntity>().eq("del_flg", 0)).forEach((e)->nowFunIdList.add(e.getFunId()));
//        }
//
//        for (String funcIdStr : funcIdStrList) {
//
//            if (nowFunIdList.indexOf(funcIdStr) < 0) {
//
//                return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0019"));
//            }
//        }

        if (pageDiv == 1) {
            // ?????????????????????????????????????????????

            // ??????ID?????????
            List<String> orgIdList = JSON.parseArray(orgIdListStr, String.class);
            if (orgIdList.size() <= 0){
                orgIdList.add(nowOrgId);
            }

            for (String orgId : orgIdList) {

                // ????????????????????????????????????
                f40010Service.updateMstOrgFunList(orgId, managerOrMentor, userId, DateUtils.getSysTimestamp());
                for (String funId : funcIdStrList) {

                    // ??????????????????ID?????????????????????????????????????????????????????????????????????????????????ID??????????????????
                    MstOrgFunListEntity mstOrgFunListEntity = mstOrgFunListService.getOne(new QueryWrapper<MstOrgFunListEntity>().eq("org_id", orgId).eq("fun_id", funId).eq("del_flg", 0));
                    if (mstOrgFunListEntity == null) {

                        // ?????????1???????????????
                        mstOrgFunListEntity = new MstOrgFunListEntity();
                        // ??????ID
                        mstOrgFunListEntity.setOrgId(orgId);
                        // ??????ID
                        mstOrgFunListEntity.setFunId(funId);
                        // ??????????????????
                        mstOrgFunListEntity.setMgrFlg("0");
                        // ?????????????????????
                        mstOrgFunListEntity.setMentFlg("0");
                        // ????????????
                        mstOrgFunListEntity.setCretDatime(DateUtils.getSysTimestamp());
                        // ?????????????????????
                        mstOrgFunListEntity.setCretUsrId(userId);
                        // ???????????????
                        mstOrgFunListEntity.setDelFlg(0);
                    }

                    if (managerOrMentor == 0) {
                        // ??????????????????
                        mstOrgFunListEntity.setMgrFlg("1");
                    }
                    if (managerOrMentor == 1) {
                        // ?????????????????????
                        mstOrgFunListEntity.setMentFlg("1");
                    }

                    // ????????????
                    mstOrgFunListEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    // ?????????????????????
                    mstOrgFunListEntity.setUpdUsrId(userId);

                    mstOrgFunListService.saveOrUpdate(mstOrgFunListEntity);

                }
            }
        }else if (pageDiv == 2) {
            // ??????????????????????????????????????????????????????????????????????????????????????????????????????

            // ?????????ID?????????
            List<F40010DspDto> userIdList = JSON.parseArray(userIdListStr, F40010DspDto.class);
            for (F40010DspDto user : userIdList) {

                // ??????????????????????????????????????????????????????????????????????????????????????????????????? ???????????????
                mstUserNmFunListService.remove(new QueryWrapper<MstUserNmFunListEntity>().eq("user_id", user.getUserId()).eq("del_flg", 0));
                for (String funId : funcIdStrList) {
                    MstUserNmFunListEntity mstUserNmFunListEntity = new MstUserNmFunListEntity();
                    // ?????????ID
                    mstUserNmFunListEntity.setUserId(user.getUserId());
                    // ??????ID
                    mstUserNmFunListEntity.setFunId(funId);
                    // ????????????
                    mstUserNmFunListEntity.setCretDatime(DateUtils.getSysTimestamp());
                    // ???????????????ID
                    mstUserNmFunListEntity.setCretUsrId(userId);
                    // ????????????
                    mstUserNmFunListEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    // ???????????????ID
                    mstUserNmFunListEntity.setUpdUsrId(userId);
                    // ???????????????
                    mstUserNmFunListEntity.setDelFlg(0);

                    mstUserNmFunListService.save(mstUserNmFunListEntity);
                }
            }
        }

        return R.ok();
    }

    /**
     * ???????????????
     * @param id
     * @param orgId
     * @param roleDiv
     * @return
     */
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public  R search(Integer id , String orgId , String roleDiv){
        List<F40010DspDto> f40010DspDtoList = f40010Service.selectMst(orgId,roleDiv);
        // ?????????Id
        String userId = ShiroUtils.getUserEntity().getUsrId();
        List<F40010DspDto> retList = new ArrayList<>();
        f40010DspDtoList.forEach((e)->{
            if (!e.getUserId().equals(userId)) {
                retList.add(e);
            }
        });
        return R.ok().put("f40010DspDtoList",retList);
    }
}
