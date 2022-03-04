/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F01001Dto;
import jp.learningpark.modules.manager.service.F01001Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F01001_塾時期検索一覧画面 Controller</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/08 : xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager")
@RestController
public class F01001Controller extends AbstractController {
    /**
     * F01001_service
     */
    @Autowired
    private F01001Service f01001Service;

    @Autowired
    private MstOrgService mstOrgService;

    /**
     * 組織一覧情報
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/F01001/init", method = RequestMethod.GET)
    public R init(Integer page, Integer limit){
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 組織名
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id",orgId)));
        String orgNm = org.getOrgNm();
        // ブランドコード
        String brandCd = (String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_BRAND_CD);
        // 総数
        Integer total = f01001Service.getTotalCount(orgId,brandCd);

        if (total == 0){
            return R.error(MessageUtils.getMessage("MSGCOMN0123", "塾時期")).put("orgId",orgId).put("orgNm",orgNm);
        }
        // ページを分ける一覧情報
        List<F01001Dto> list1 = f01001Service.getUpplevInfomation(orgId,brandCd,(page-1)*limit,limit);
        for (F01001Dto list:list1
             ) {
            list.setPlanStartDy(DateUtils.format(list.getPlanPrdStartDy(), Constant.DATE_FORMAT_YYYYMMDD));
            list.setPlanEndDy(DateUtils.format(list.getPlanPrdEndDy(), Constant.DATE_FORMAT_YYYYMMDD));
        }
        return R.ok().put("orgId",orgId).put("orgNm",orgNm).put("page",new PageUtils(list1,total,limit,page));
    }

    /**
     * <p>対応する組織を組織マスタから論理削除処理行う。</p>
     * @param id
     * @return
     */
    @RequestMapping(value = "/F01001/delete", method = RequestMethod.POST)
    public R deleteFn(Integer id){
        return f01001Service.deleteById(id);
    }
}
