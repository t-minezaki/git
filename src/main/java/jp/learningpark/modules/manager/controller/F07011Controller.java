/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.StuTestGoalResultDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.StuTestGoalResultDService;
import jp.learningpark.modules.manager.dto.F07011Dto;
import jp.learningpark.modules.manager.service.F07011Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F07011_成績教科メンテナンス一覧画面 Controller</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/04/23 : yang: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F07011")
@RestController
public class F07011Controller extends AbstractController {
    /**
     * 組織マスター　Service
     */
    @Autowired
    MstOrgService mstOrgService;
    /**
     * mstCodDService
     */
    @Autowired
    MstCodDService mstCodDService;
    @Autowired
    /**
     * F07011Service
     */
            F07011Service f07011Service;
    /**
     * StuTestGoalResultDService
     */
    @Autowired
    StuTestGoalResultDService stuTestGoalResultDService;

    /**
     * <p>初期表示</p>
     *
     * @param limit 件数
     * @param page ページ数
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page) {
        String orgNm = "";
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", ShiroUtils.getUserEntity().getOrgId())));
        if (org != null) {
            //組織名
            orgNm = org.getOrgNm();
        }
        List<MstCodDEntity> mstCodDEntityList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().and(w->w.eq("cod_key", "SCHY_DIV").eq("del_flg", 0)).orderByAsc("sort"));
        if (mstCodDEntityList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "学年")).put("orgId", ShiroUtils.getUserEntity().getOrgId()).put("orgNm", orgNm);
        }
        //        return R.ok().put("schyList", mstCodDEntityList);
        List<F07011Dto> list = f07011Service.getMstcodList(null, null, null, limit, (page - 1) * limit);
        Integer total = f07011Service.getMstcodCount(null, null, null);
        if (list.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科")).put("orgId", ShiroUtils.getUserEntity().getOrgId()).put("orgNm", orgNm);
        }
        return R.ok().put("page", new PageUtils(list, total, limit, page)).put("orgId", ShiroUtils.getUserEntity().getOrgId()).put("orgNm", orgNm).put(
                "schyList", mstCodDEntityList);
    }

    /**
     * <p>検索</p>
     *
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public R search(String schy, String codCd, String subName, Integer limit, Integer page) {
        List<F07011Dto> searchList = f07011Service.getMstcodList(schy, codCd, subName, limit, (page - 1) * limit);
        Integer searchTotal = f07011Service.getMstcodCount(schy, codCd, subName);
        if (searchList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科"));
        }
        return R.ok().put("page", new PageUtils(searchList, searchTotal, limit, page));
    }

    /**
     * <p>削除</p>
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delete(String codCd) {
        List<StuTestGoalResultDEntity> stuTestGoalResultDEntityList = stuTestGoalResultDService.list(
                new QueryWrapper<StuTestGoalResultDEntity>().and(w->w.eq("SUBJT_DIV", codCd).eq("del_flg", 0)));
        if (stuTestGoalResultDEntityList.size() != 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0092", "教科"));
        }
        mstCodDService.remove(new QueryWrapper<MstCodDEntity>().and(w->w.eq("cod_cd", codCd).eq("del_flg", 0)));
        return R.ok();
    }
}