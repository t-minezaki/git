package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstMessageEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstMessageService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F21065Dto;
import jp.learningpark.modules.manager.service.F21065Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/21 ： NWT)hxl ： 新規作成
 */
@RestController
@RequestMapping("/manager/F21065")
public class F21065Controller {
    @Autowired
    MstMessageService mstMessageService;
    @Autowired
    F21065Service f21065Service;
    @Autowired
    MstOrgService mstOrgService;

    /**
     * 初期表示
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer messageId) {
        // 組織名
        String orgNm = "";
        //組織情報
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", ShiroUtils.getUserEntity().getOrgId())));

        //組織名
        orgNm = org.getOrgNm();
        MstMessageEntity mstMessageEntity = mstMessageService.getOne(
                new QueryWrapper<MstMessageEntity>().eq("id", messageId).eq("message_type_div", "1").eq("del_flg", 0));
        //        開始時間を取得
        String pubPlanStartDt = DateUtils.format(mstMessageEntity.getPubPlanStartDt(), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
        //        終了時間を取得
        String pubPlanEndDt = DateUtils.format(mstMessageEntity.getPubPlanEndDt(), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM);

        //メッセージ
        return R.ok().put("mstMessageEntity", mstMessageEntity).put("orgNm", orgNm).put("sOrgId", ShiroUtils.getUserEntity().getOrgId()).put(
                "pubPlanStartDt", pubPlanStartDt).put("pubPlanEndDt", pubPlanEndDt);
    }

    //    要約データを取得する
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public R select(Integer messageId, Integer limit, Integer page) throws UnsupportedEncodingException {
        String orgNm = "";
        MstMessageEntity mstMessageEntity = mstMessageService.getOne(
                new QueryWrapper<MstMessageEntity>().eq("id", messageId).eq("message_type_div", "1").eq("del_flg", 0));
        if (mstMessageEntity != null) {
            //メッセージヘッダーのデコード
            mstMessageEntity.setMessageTitle(URLDecoder.decode(mstMessageEntity.getMessageTitle(), "utf-8"));
            //組織ID
            String orgId = mstMessageEntity.getOrgId();
            //組織判断
            if (ShiroUtils.getUserEntity().getOrgId().equals(orgId)) {
                List<F21065Dto> f21065DtoList = f21065Service.init01(messageId, limit, page);
                return R.ok().put("page", new PageUtils(f21065DtoList, f21065DtoList.size(), limit, page)).put("mstMessageEntity", mstMessageEntity).put(
                        "orgNm", orgNm).put("sOrgId", ShiroUtils.getUserEntity().getOrgId());
            } else {
                String brandCd = ShiroUtils.getBrandcd();
                //下位組織の情報を入手する
                List<MstOrgEntity> mstOrgEntityList = f21065Service.getOrg(brandCd, orgId);
                StringBuilder orgIds = new StringBuilder();
                for (MstOrgEntity mstOrgEntityList1 : mstOrgEntityList) {
                    orgIds.append("," + mstOrgEntityList1.getOrgId());
                }
                List<F21065Dto> f21065DtoList = f21065Service.init02(orgIds.deleteCharAt(0).toString(), messageId, limit, page);
                return R.ok().put("page", new PageUtils(f21065DtoList, f21065DtoList.size(), limit, page)).put("mstMessageEntity", mstMessageEntity);
            }
        }
        return R.ok();
    }
}
