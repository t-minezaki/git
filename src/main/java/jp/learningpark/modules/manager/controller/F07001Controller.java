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
import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.MstUnitEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstCrmschLearnPrdService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.MstUnitService;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F70001_学年メンテナンス一覧画面 Controller</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/04/03 : yang: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F07001")
@RestController
public class F07001Controller extends AbstractController {

    /**
     * 組織マスター　Service
     */
    @Autowired
    MstOrgService mstOrgService;
    /**
     * mstCrmschLearnPrdService
     */
    @Autowired
    MstCrmschLearnPrdService mstCrmschLearnPrdService;
    /**
     * mstStuService
     */
    @Autowired
    MstStuService mstStuService;
    /**
     * mstUnitService
     */
    @Autowired
    MstUnitService mstUnitService;
    /**
     * mstCodDService
     */
    @Autowired
    MstCodDService mstCodDService;


    /**
     * <p>初期表示</p>
     *
     * @param limit 当ページ数
     * @param page  各ページの最大記録数
     * @return R 画面情報
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page) {
        String orgNm = "";
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", ShiroUtils.getUserEntity().getOrgId())));
        if (org != null) {
            orgNm = org.getOrgNm();
        }
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        List<MstCodDEntity> mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value", "sort")
                .eq("cod_key", "SCHY_DIV").eq("del_flg", 0).orderBy(true, true, "sort"));
        //mstCodDEntityListLimit改ページ表示
        List<MstCodDEntity> mstCodDEntityListLimit = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value", "sort")
                .eq("cod_key", "SCHY_DIV").eq("del_flg", 0).orderBy(true, true, "sort").last("LIMIT " + limit + " OFFSET " + (page - 1) * limit));
        if (mstCodDEntityList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "学年")).put("orgId", orgId).put("orgNm", orgNm);
        }
        R info = new R();
        info.put("orgId", orgId).put("orgNm", orgNm).put("page", new PageUtils(mstCodDEntityListLimit, mstCodDEntityList.size(), limit, page));
        return info;
    }

    /**
     * <p>検索ボタン押下</p>
     *
     * @param codValue 学年名
     * @param limit    当ページ数
     * @param page     各ページの最大記録数
     * @return 検索画面情報
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public R search(String codValue, Integer limit, Integer page) {
        List<MstCodDEntity> mstCodDEntityList;
        // 検索改ページ表示
        List<MstCodDEntity> mstCodDEntityListLimit;
            mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value", "sort")
                    .eq("cod_key", "SCHY_DIV").like("cod_value", codValue).eq("del_flg", 0).orderBy(true, true, "sort"));
            mstCodDEntityListLimit = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value", "sort")
                    .eq("cod_key", "SCHY_DIV").like("cod_value", codValue).eq("del_flg", 0).orderBy(true, true, "sort").last("LIMIT " + limit + " OFFSET " + (page - 1) * limit));
        if (mstCodDEntityList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "学年"));
        }
        R r = new R();
        return r.put("page", new PageUtils(mstCodDEntityListLimit, mstCodDEntityList.size(), limit, page));
    }

    /**
     * 削除ボタン押下
     *
     * @param codCd
     * @return 削除画面情報
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delete(String codCd) {
        //塾学習期間マスタ．学年区分　＝削除行の　「画面．学年CD」
        MstCrmschLearnPrdEntity mstCrmschLearnPrdEntity = mstCrmschLearnPrdService.getOne(new QueryWrapper<MstCrmschLearnPrdEntity>().and(w -> w.eq("schy_div", codCd).eq("del_flg", 0)));
        //生徒基本マスタ．学年区分　＝削除行の　「画面．学年CD」
        MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().and(w -> w.eq("schy_div", codCd).eq("del_flg", 0)));
        //単元マスタ．学年区分　＝削除行の　「画面．学年CD」
        MstUnitEntity mstUnitEntity = mstUnitService.getOne(new QueryWrapper<MstUnitEntity>().and(w -> w.eq("schy_div", codCd).eq("del_flg", 0)));
        //エラーの場合
        if (mstCrmschLearnPrdEntity != null || mstStuEntity != null || mstUnitEntity != null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0092", "学年"));
        }
        //コードマスタの物理削除
        mstCodDService.remove(new QueryWrapper<MstCodDEntity>().and(w -> w.eq("cod_cd", codCd).eq("cod_key", "SCHY_DIV")));
        return R.ok();
    }
}
