/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstNoticeService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F04015Dto;
import jp.learningpark.modules.manager.service.F04015Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>F04015_配信先既読未読状態確認一覧画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/17 : zpa: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F04015")
@RestController
public class F04015Controller {
    @Autowired
    MstNoticeService mstNoticeService;
    @Autowired
    F04015Service f04015Service;
    @Autowired
    MstOrgService mstOrgService;
    /**
     * 初期表示
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer noticeId,Integer limit, Integer page) throws UnsupportedEncodingException {
        // 組織名
        String orgNm = "";
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", ShiroUtils.getUserEntity().getOrgId())));
        if (org != null) {
            orgNm = org.getOrgNm();
        }
        MstNoticeEntity mstNoticeEntity = mstNoticeService.getOne(new QueryWrapper<MstNoticeEntity>().eq("id",noticeId).eq("del_flg", 0));
        if(mstNoticeEntity != null){
            mstNoticeEntity.setNoticeTitle(URLDecoder.decode(mstNoticeEntity.getNoticeTitle(),"utf-8"));
            String orgId = mstNoticeEntity.getOrgId();
            Integer count = 0;
            if(ShiroUtils.getUserEntity().getOrgId().equals(orgId)){
                List<F04015Dto> f04015DtoList = f04015Service.init01(noticeId,limit,page);
                count = f04015Service.init01Count(noticeId);
                return R.ok().put("page", new PageUtils(f04015DtoList, count, limit, page)).put("mstNoticeEntity",mstNoticeEntity).put("orgNm",orgNm).put("sOrgId",ShiroUtils.getUserEntity().getOrgId());
            }
            else{
                String brandCd = ShiroUtils.getBrandcd();
                List<MstOrgEntity> mstOrgEntityList = f04015Service.getOrg(brandCd, orgId);
                StringBuilder orgIds = new StringBuilder();
                for( MstOrgEntity mstOrgEntityList1 : mstOrgEntityList)
                {
                    orgIds.append(","+mstOrgEntityList1.getOrgId());
                }
                List<F04015Dto> f04015DtoList = f04015Service.init02(orgIds.deleteCharAt(0).toString(), noticeId,limit, page);
                count = f04015Service.init02Count(orgIds.deleteCharAt(0).toString(), noticeId);
                return R.ok().put("page", new PageUtils(f04015DtoList, count, limit, page)).put("mstNoticeEntity",mstNoticeEntity).put("orgNm",orgNm).put("sOrgId",ShiroUtils.getUserEntity().getOrgId());
            }
        }
       return R.ok();
    }
}
