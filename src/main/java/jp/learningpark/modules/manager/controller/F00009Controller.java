/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.manager.dto.F00009Dto;
import jp.learningpark.modules.manager.service.F00009Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parse;

/**
 * <p>F00009_組織一覧画面 Controller</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/20 : gong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F00009")
@RestController
public class F00009Controller extends AbstractController {

    /**
     * 組織マスタService
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * F00009_組織一覧画面 service
     */
    @Autowired
    private F00009Service f00009Service;
    /**
     * ユーザ基本マスタService
     */
    @Autowired
    private MstUsrService mstUsrService;

    /**
     * <p>初期表示と検索</p>
     *
     * @param currentOrgId　入力組織ID
     * @param orgNm　入力組織名
     * @param upLevOrgId　入力上層組織ID
     * @param mgrFlg　管理フラグ
     * @param level
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f00009Init(String currentOrgId, String orgNm, String upLevOrgId, Integer mgrFlg, Integer level, Integer limit, Integer page) {
        String sessionOrgId = (String)ShiroUtils.getSessionAttribute(GakkenConstant.ORG_ID);
        String userOrgId = ShiroUtils.getUserEntity().getOrgId();
        //組織ID
        String orgId = StringUtils.isEmpty(sessionOrgId) ? userOrgId : sessionOrgId;
        //組織
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));
        //ブランドコード
        String brandCd = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_BRAND_CD);

        List<F00009Dto> mstOrgEntityList1 = null;
        if (StringUtils.isEmpty(upLevOrgId)) {
            mstOrgEntityList1 = f00009Service.getLowerOrgList(orgId, brandCd);
        } else {
            mstOrgEntityList1 = f00009Service.getLowerOrgList(upLevOrgId, brandCd);
        }
        List<String> lowerOrgList = new ArrayList<>();
        for (F00009Dto dto : mstOrgEntityList1) {
            lowerOrgList.add(dto.getOrgId());
        }

        //下位組織一覧情報の数量
        Integer total = f00009Service.lowerOrgCount(orgId, brandCd, lowerOrgList, currentOrgId, orgNm, upLevOrgId, mgrFlg, level);
        if (total == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "組織")).put("upOrg", mstOrgEntity).put("upLevOrg", mstOrgEntityList1);
        }
        //下位組織一覧情報
        List<F00009Dto> showList = f00009Service.lowerOrgList(
                orgId, brandCd, lowerOrgList, currentOrgId, orgNm, upLevOrgId, mgrFlg, level, limit, (page - 1) * limit);
        return R.ok().put("page", new PageUtils(showList, total, limit, page)).put("upOrg", mstOrgEntity).put(
                "managerId", ShiroUtils.getUserEntity().getAfterUsrId()).put("upLevOrg", mstOrgEntityList1);
    }

    /**
     * <p>対応する組織を組織マスタから論理削除処理行う。</p>
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R deleteFn(Integer id) {
        return f00009Service.updateById(id);
    }

    /**
     * ＱＲログインデータを取得
     */
    @RequestMapping(value = "/qrLogin", method = RequestMethod.GET)
    public R qrLogin(String orgListStr) {
        List<String> orgList = (List<String>)parse(orgListStr);
        //ＱＲログインデータを取得
        List<F00009Dto> qrDatas = f00009Service.getQrUser(orgList);
        //ＱＲログインデータを取得(変更後ユーザーIDの重複データを削除する)
        List<F00009Dto> aftUsrIds = f00009Service.getAftUsrIds(orgList);
        //上記取得できない場合
        if (qrDatas.size() == 0) {
            //画面上部のエラーメッセージ領域でワーニングメッセージを表示する
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "ＱＲユーザ"));
        }
        for (F00009Dto dto : aftUsrIds) {
            List<MstUsrEntity> mstUsrEntityList = mstUsrService.list(new QueryWrapper<MstUsrEntity>().eq("after_usr_id", dto.getAfterUsrId()).eq("del_flg", 0));
            if (mstUsrEntityList.size() <= 1) {
                //※非統合ユーザーのみは”Ｍｇ”を付ける。
                dto.setAfterUsrId(dto.getAfterUsrId() + "Mg");
            }
        }
        return R.ok().put("qrDatas", qrDatas).put("aftUsrIds", aftUsrIds);
    }

    /**
     * 　「登録」押下時、ＱＲ統合処理を行う。
     */
    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public R submit(String qrUsersStr, String afterUsrId, boolean checkFlg) {
        if (checkFlg) {
            List<MstUsrEntity> mstUsrEntityList = mstUsrService.list(
                    new QueryWrapper<MstUsrEntity>().eq("after_usr_id", afterUsrId).eq("spec_auth_flg", "2").eq("del_flg", 0));
            //取得したUSERCNT＞0の場合
            if (mstUsrEntityList.size() > 0) {
                //画面上部のエラーメッセージ領域でワーニングメッセージを表示する。
                return R.error(MessageUtils.getMessage("MSGCOMN0010", "ＱＲ統合ユーザ", "ユーザ基本マスタ"));
            }
        }
        List<F00009Dto> qrUsrs = JSON.parseArray(qrUsersStr, F00009Dto.class);
        for (F00009Dto dto : qrUsrs) {
            //取得した組織ID
            String orgId = dto.getOrgId();
            //取得したユーザID
            String usrId = dto.getUsrId();
            MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("org_id", orgId).eq("usr_id", usrId).eq("del_flg", 0));
            //更新日時
            String nowUpdTime = DateUtils.format(mstUsrEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            // 排他チェック
            if (nowUpdTime.equals(DateUtils.format(dto.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS))) {
                //変更後ユーザーID
                mstUsrEntity.setAfterUsrId(afterUsrId);
                //更新日時
                mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                mstUsrEntity.setUpdUsrId(ShiroUtils.getUserId());
                mstUsrService.update(mstUsrEntity, new QueryWrapper<MstUsrEntity>().eq("org_id", orgId).eq("usr_id", usrId).eq("del_flg", 0));
            } else {
                //排他チェックエラーの場合、処理を中断し、エラー内容を画面の上部に表示する
                return R.error(MessageUtils.getMessage("MSGCOMN0019"));
            }
        }
        return R.ok();
    }
}
