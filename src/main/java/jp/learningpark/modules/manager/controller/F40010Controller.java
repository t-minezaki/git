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
     * 画面初期化
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init() {
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //本組織の上層組織を取得する
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id",orgId).eq("del_flg",0));
        // 組織名
        String orgNm = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id", orgId)).getOrgNm();
        List<String> funIdList = new ArrayList<>();
        if (mstOrgEntity.getLevel()==1){
            List<MstFunEntity> mstFunEntityList = mstFunService.list(new QueryWrapper<MstFunEntity>().orderByAsc("junban_num").eq("del_flg",0));
            for (MstFunEntity mstFunEntity : mstFunEntityList) {
                funIdList.add(mstFunEntity.getFunId());
            }
        }else {
            // 組織別の機能一覧を画面に表示するため、組織別機能一覧マスタと機能マスタから機能名などを取得する。
            List<MstOrgFunListEntity> mstOrgFunListEntityList = mstOrgFunListService.list(new QueryWrapper<MstOrgFunListEntity>().eq("org_id", mstOrgEntity.getLevel()==1?orgId:mstOrgEntity.getUpplevOrgId()).eq("del_flg", 0).last("and (mgr_flg = '1' or ment_flg = '1')"));
            for (MstOrgFunListEntity mstFunEntityList : mstOrgFunListEntityList) {
                funIdList.add(mstFunEntityList.getFunId());
            }
        }


        List<MstFunEntity> mstFunEntityList = mstFunService.list(new QueryWrapper<MstFunEntity>().in("fun_id", funIdList).orderByAsc("junban_num").eq("del_flg", 0));
        // 木構造を取得する
        List<F40010ManagerTypeDto> f40010ManagerTypeDtoList = f40010Service.getDiv(mstFunEntityList);

        if (funIdList.size() <= 0) {
            // 取得０件の場合、機能マスタから全機能一覧情報を取得する。
            mstFunEntityList = mstFunService.list(new QueryWrapper<MstFunEntity>().orderByAsc("junban_num").eq("del_flg", 0));
            f40010ManagerTypeDtoList = f40010Service.getDiv(mstFunEntityList);

            if (f40010ManagerTypeDtoList.size() <= 0) {
                // 取得０件の場合、処理を中断し、画面上部のエラーメッセージ領域でエラーメッセージを表示する。
                return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0035", "'機能名")).put("orgId", orgId).put("orgNm", orgNm);
            }
        }

        return R.ok().put("managerTypeList", f40010ManagerTypeDtoList).put("orgId", orgId).put("orgNm", orgNm);
    }

    /**
     * 下部組織を獲得する
     * @return
     */
    @RequestMapping(value = {"/getOrgList"}, method = RequestMethod.GET)
    public R getOrgList() {

        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String brandCd = ShiroUtils.getBrandcd();
        // 組織リステ
        List<OrgAndLowerOrgIdDto> orgIdList = commonService.getThisAndLowerOrgId(brandCd, orgId);

        return R.ok().put("mstOrgEntityList", orgIdList);
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
     * データ保存
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

        // 組織ID
        String nowOrgId = ShiroUtils.getUserEntity().getOrgId();

        //　ユーザID
        String userId = ShiroUtils.getUserEntity().getUsrId();

        // 機能IDリスト
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
            // ロール別機能一覧権限付与処理。

            // 組織IDリスト
            List<String> orgIdList = JSON.parseArray(orgIdListStr, String.class);
            if (orgIdList.size() <= 0){
                orgIdList.add(nowOrgId);
            }

            for (String orgId : orgIdList) {

                // 組織の機能をリセットする
                f40010Service.updateMstOrgFunList(orgId, managerOrMentor, userId, DateUtils.getSysTimestamp());
                for (String funId : funcIdStrList) {

                    // 権限付与機能IDの存在チェックを行うため、組織別機能一覧マスタから機能IDを取得する。
                    MstOrgFunListEntity mstOrgFunListEntity = mstOrgFunListService.getOne(new QueryWrapper<MstOrgFunListEntity>().eq("org_id", orgId).eq("fun_id", funId).eq("del_flg", 0));
                    if (mstOrgFunListEntity == null) {

                        // 件数＜1の場合新規
                        mstOrgFunListEntity = new MstOrgFunListEntity();
                        // 組織ID
                        mstOrgFunListEntity.setOrgId(orgId);
                        // 機能ID
                        mstOrgFunListEntity.setFunId(funId);
                        // 管理者フラグ
                        mstOrgFunListEntity.setMgrFlg("0");
                        // メンターフラグ
                        mstOrgFunListEntity.setMentFlg("0");
                        // 作成日時
                        mstOrgFunListEntity.setCretDatime(DateUtils.getSysTimestamp());
                        // 作成ユーザＩＤ
                        mstOrgFunListEntity.setCretUsrId(userId);
                        // 削除フラグ
                        mstOrgFunListEntity.setDelFlg(0);
                    }

                    if (managerOrMentor == 0) {
                        // 管理者フラグ
                        mstOrgFunListEntity.setMgrFlg("1");
                    }
                    if (managerOrMentor == 1) {
                        // メンターフラグ
                        mstOrgFunListEntity.setMentFlg("1");
                    }

                    // 更新日時
                    mstOrgFunListEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    // 更新ユーザＩＤ
                    mstOrgFunListEntity.setUpdUsrId(userId);

                    mstOrgFunListService.saveOrUpdate(mstOrgFunListEntity);

                }
            }
        }else if (pageDiv == 2) {
            // 個人別機能権限設定の場合、画面に設定状況より、個人の権限付与を行う。

            // ユーザIDリステ
            List<F40010DspDto> userIdList = JSON.parseArray(userIdListStr, F40010DspDto.class);
            for (F40010DspDto user : userIdList) {

                // 指定した個人権限付与機能一覧を更新するため、ユーザ別機能一覧マスタ を削除する
                mstUserNmFunListService.remove(new QueryWrapper<MstUserNmFunListEntity>().eq("user_id", user.getUserId()).eq("del_flg", 0));
                for (String funId : funcIdStrList) {
                    MstUserNmFunListEntity mstUserNmFunListEntity = new MstUserNmFunListEntity();
                    // ユーザID
                    mstUserNmFunListEntity.setUserId(user.getUserId());
                    // 機能ID
                    mstUserNmFunListEntity.setFunId(funId);
                    // 作成日時
                    mstUserNmFunListEntity.setCretDatime(DateUtils.getSysTimestamp());
                    // 作成ユーザID
                    mstUserNmFunListEntity.setCretUsrId(userId);
                    // 更新日時
                    mstUserNmFunListEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    // 更新ユーザID
                    mstUserNmFunListEntity.setUpdUsrId(userId);
                    // 削除フラゲ
                    mstUserNmFunListEntity.setDelFlg(0);

                    mstUserNmFunListService.save(mstUserNmFunListEntity);
                }
            }
        }

        return R.ok();
    }

    /**
     * 指定者検索
     * @param id
     * @param orgId
     * @param roleDiv
     * @return
     */
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public  R search(Integer id , String orgId , String roleDiv){
        List<F40010DspDto> f40010DspDtoList = f40010Service.selectMst(orgId,roleDiv);
        // ユーザId
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
