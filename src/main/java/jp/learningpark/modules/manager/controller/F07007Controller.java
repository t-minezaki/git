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
import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstBlockService;
import jp.learningpark.modules.common.service.MstOrgService;
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
 * 2019/04/16 : yang: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F07007")
@RestController
public class F07007Controller extends AbstractController {

    /**
     * 組織マスター　Service
     */
    @Autowired
    MstOrgService mstOrgService;
    /**
     * MstBlockService
     */
    @Autowired
    MstBlockService mstBlockService;

    /**
     * <p>初期表示</p>
     * @param id
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init( Integer id,Integer limit, Integer page) {
        R info = new R();
        String orgNm = "";
        String blockDispyNm ="";
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", ShiroUtils.getUserEntity().getOrgId())));
        if (org != null) {
            orgNm = org.getOrgNm();
        }
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        info.put("orgId", orgId).put("orgNm", orgNm);
        if (id == null){
            info.put("blockNull",MessageUtils.getMessage("MSGCOMN0017", "その他大分類ブロック'")).put("blockDispyNm",blockDispyNm);
            return info;
        }
        MstBlockEntity mstBlockEntity = mstBlockService.getOne(new QueryWrapper<MstBlockEntity>().eq("id",id).eq("del_flg",0));
        blockDispyNm = mstBlockEntity.getBlockDispyNm();
        List<MstBlockEntity> mstBlockEntityList = mstBlockService.list(new QueryWrapper<MstBlockEntity>().select("id","block_dispy_nm","block_type_div")
                .and(w->w.eq("upplev_block_id",id).isNull("stu_id").likeRight("block_type_div","O")).eq("del_flg",0).orderBy(true,true,"block_type_div"));
        List<MstBlockEntity> mstBlockEntityListLimit = mstBlockService.list(new QueryWrapper<MstBlockEntity>().select("id","block_dispy_nm","block_type_div")
                .and(w->w.eq("upplev_block_id",id).isNull("stu_id")
                        .likeRight("block_type_div","O"))
                        .eq("del_flg",0)
                        .orderBy(true,true,"block_type_div")
                        .last("LIMIT "+limit+" OFFSET "+(page-1)*limit));
        if (mstBlockEntityList.size() == 0){
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "その他小分類ブロック'")).put("blockDispyNm",blockDispyNm);
        }
        info.put("page",new PageUtils(mstBlockEntityListLimit,mstBlockEntityList.size(),limit,page)).put("blockDispyNm",blockDispyNm);
        return info;
    }
    //削除ボタン押下
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delete(Integer deleteId) {
        MstBlockEntity mstBlockEntity = mstBlockService.getOne(new QueryWrapper<MstBlockEntity>().and(w->w.eq("id",deleteId).isNotNull("stu_id").eq("del_flg",0)));
        if(mstBlockEntity != null){

            return R.error(MessageUtils.getMessage("MSGCOMN0081", "その他大分類ブロック'"));
        }
        //コードマスタの物理削除
        mstBlockService.remove(new QueryWrapper<MstBlockEntity>().and(w->w.eq("id",deleteId)));
        return R.ok();
    }
}
