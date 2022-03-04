/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dto.F00071Dto;
import jp.learningpark.modules.manager.service.F00071Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>F00071_生徒ステータス状況詳細画面</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/03/20 : tan: 新規<br />
 * @version 1.0
 */

@RequestMapping("/manager/F00071")
@RestController
public class F00071Controller {

    /**
     * 生徒ステータス状況詳細画面
     */
    @Autowired
    private F00071Service f00071Service;

    @Autowired
    MstOrgService mstOrgService;
    @Autowired
    CommonService commonService;

    /**
     * <p>初期化</p>
     *
     * @param orgName   画面．検索条件．組織名
     * @param stuId     画面．検索条件．生徒ＩＤ
     * @param stuName   画面．検索条件．生徒姓名
     * @param usrStatus 画面．検索条件．ステータス
     * @param crmSts    画面．検索条件．異動ステータス
     * @param moveYmd   画面．検索条件．異動年月日
     * @param limit     １ページ最大件数
     * @param page      現在のページ数
     * @return
     */
    private R getDetail(String orgName, String stuId, String stuName, String usrStatus, String crmSts, String moveYmd, Integer limit, Integer page) {
        String brandCd = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id",orgName).eq("del_flg",0)).getBrandCd();
        List<OrgAndLowerOrgIdDto> orgAndLowerOrgIdDto = commonService.getThisAndLowerOrgId(brandCd,orgName);
        List<F00071Dto> list = f00071Service.getValue(orgAndLowerOrgIdDto, stuId, stuName, usrStatus, crmSts, moveYmd, limit, (page - 1) * limit);
        //総件数をとる
        Integer total = f00071Service.getTotalCount(orgAndLowerOrgIdDto, stuId, stuName, usrStatus, crmSts, moveYmd);
        if (list.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒"));
        }
        return R.ok().put("page", new PageUtils(list, total, limit, page));
    }

    /**
     * <p>初期化</p>
     *
     * @param limit １ページ最大件数
     * @param page  現在のページ数
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page) {
        // 組織名
        String orgNm = "";
        String sessionOrgId = (String)ShiroUtils.getSessionAttribute(GakkenConstant.ORG_ID);
        String userOrgId = ShiroUtils.getUserEntity().getOrgId();
        //組織ID
        String orgId = StringUtils.isEmpty(sessionOrgId) ? userOrgId : sessionOrgId;
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId)));
        if (org != null) {
            orgNm = org.getOrgNm();
        }
        R r = getDetail(orgId, null, null, null, null, null, limit, page);
        return r.put("sOrgId", orgId).put("orgNm", orgNm);
    }

    /**
     * <P>検索ボタン押下</P>
     *
     * @param orgName   画面．検索条件．組織名
     * @param stuId     画面．検索条件．生徒ＩＤ
     * @param stuName   画面．検索条件．生徒姓名
     * @param usrStatus 画面．検索条件．ステータス
     * @param crmSts    画面．検索条件．異動ステータス
     * @param moveYmd   画面．検索条件．異動年月日
     * @param limit     １ページ最大件数
     * @param page      現在のページ数
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public R search(String orgName, String stuId, String stuName, String usrStatus, String crmSts, String moveYmd, Integer limit, Integer page) {
        return getDetail(orgName, stuId, stuName, usrStatus, crmSts, moveYmd, limit, page);
    }

    /**
     * <p>//初期化の画面．検索条件．組織名</p>
     *
     * @return
     */
    @RequestMapping(value = "/orgNameSearch", method = RequestMethod.GET)
    public R orgNameSearch() {
        String sessionOrgId = (String)ShiroUtils.getSessionAttribute(GakkenConstant.ORG_ID);
        String userOrgId = ShiroUtils.getUserEntity().getOrgId();
        //組織ID
        String orgId = StringUtils.isEmpty(sessionOrgId) ? userOrgId : sessionOrgId;
        List<F00071Dto> orgNameSearchList = f00071Service.getOrganization(ShiroUtils.getBrandcd(), orgId);
        if (orgNameSearchList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "組織マスタ"));
        }
        return R.ok().put("orgNameSearchList", orgNameSearchList);
    }

    /**
     * <p>初期化の画面．検索条件．ステータス</p>
     *
     * @return
     */
    @RequestMapping(value = "/usrStatusSearch", method = RequestMethod.GET)
    public R usrStatusSearch() {
        List<F00071Dto> usrStatusSearchList = f00071Service.getUsrStatus();
        if (usrStatusSearchList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "ステータス"));
        }
        return R.ok().put("usrStatusSearchList", usrStatusSearchList);
    }

    /**
     * <p>初期化の画面．検索条件．異動ステータス</p>
     *
     * @return
     */
    @RequestMapping(value = "/crmStsSearch", method = RequestMethod.GET)
    public R crmStsStsSearch() {
        List<F00071Dto> crmStsSearchList = f00071Service.getDisabilityStatus();
        if (crmStsSearchList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "異動ステータス"));
        }
        return R.ok().put("crmStsSearchList", crmStsSearchList);
    }
}