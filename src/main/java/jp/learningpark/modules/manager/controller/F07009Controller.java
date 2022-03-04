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
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.StuFixdSchdlEntity;
import jp.learningpark.modules.common.service.MstBlockService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.StuFixdSchdlService;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F70005_その他ブロックメンテナンス一覧画面 Controller</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/04/22 : yang: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F07009")
@RestController
public class F07009Controller extends AbstractController {
    @Autowired
    MstOrgService mstOrgService;
    @Autowired
    MstBlockService mstBlockService;
    @Autowired
    StuFixdSchdlService stuFixdSchdlService;
    /**
     * <p>初期表示</p>
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page) {
        String orgNm = "";
        String path = ".." + GakkenConstant.SERVER_IMG_PREFIX;
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", ShiroUtils.getUserEntity().getOrgId())));
        if (org != null) {
            orgNm = org.getOrgNm();
        }
        R r = new R();
         //mstBlockEntityList
        List<MstBlockEntity> mstBlockEntityList = mstBlockService.list(new QueryWrapper<MstBlockEntity>().select("id","block_dispy_nm","block_type_div","block_pic_div").and
                (w->w.likeRight("block_type_div","C").eq("del_flg",0)));
        //mstBlockEntityListLimit
        List<MstBlockEntity> mstBlockEntityListLimit = mstBlockService.list(new QueryWrapper<MstBlockEntity>().select("id","block_dispy_nm","block_type_div","block_pic_div").and
                (w->w.likeRight("block_type_div","C").eq("del_flg",0)).last("LIMIT "+limit+" OFFSET "+(page-1)*limit));
        for(MstBlockEntity entity:mstBlockEntityListLimit){
            entity.setUpdUsrId(path);
        }
        if (mstBlockEntityList.size() == 0){
            return R.error(MessageUtils.getMessage("MSGCOMN0017","固定ブロック")).put("orgId",ShiroUtils.getUserEntity().getOrgId()).put("orgNm",orgNm);
        }
        return r.put("orgId",ShiroUtils.getUserEntity().getOrgId()).put("orgNm",orgNm).put("page",new PageUtils(mstBlockEntityListLimit,mstBlockEntityListLimit.size(),limit,page));
    }

    /**
     * 検索ボタン押下
     * @param blockDispyNm
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public R search(String blockDispyNm, Integer limit,Integer page) {
        // 検索
        List<MstBlockEntity> mstBlockEntityList = null;
        // 検索改ページ表示
        List<MstBlockEntity> mstBlockEntityListLimit = null;
        String path = ".." + GakkenConstant.SERVER_IMG_PREFIX;
         mstBlockEntityList = mstBlockService.list(new QueryWrapper<MstBlockEntity>().select("id","block_dispy_nm","block_type_div","block_pic_div").and
                    (w->w.likeRight("block_type_div","C").like("block_dispy_nm",blockDispyNm).eq("del_flg",0)));
           mstBlockEntityListLimit = mstBlockService.list(new QueryWrapper<MstBlockEntity>().select("id","block_dispy_nm","block_type_div","block_pic_div").and
                    (w->w.likeRight("block_type_div","C").like("block_dispy_nm",blockDispyNm).eq("del_flg",0)).last("LIMIT "+limit+" OFFSET "+(page-1)*limit));

        for(MstBlockEntity entity:mstBlockEntityListLimit){
            entity.setUpdUsrId(path);
        }

        if (mstBlockEntityList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "固定ブロック名"));
        }

        return R.ok().put("page", new PageUtils(mstBlockEntityListLimit, mstBlockEntityList.size(), limit, page));
    }

    /**
     * 削除リンク押下
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R search(Integer id) {
        StuFixdSchdlEntity stuFixdSchdlEntity = stuFixdSchdlService.getOne(new QueryWrapper<StuFixdSchdlEntity>().and(w->w.eq("block_id",id).eq("del_flg",0)));
//        MstBlockEntity mstBlockEntity = mstBlockService.getOne(new QueryWrapper<MstBlockEntity>().and(w -> w.likeRight("block_type_div", "O").eq("upplev_block_id",id).eq("del_flg", 0)));
        if (stuFixdSchdlEntity != null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0061", "ブロック表示名"));
        }
        mstBlockService.remove(new QueryWrapper<MstBlockEntity>().and(w -> w.eq("id", id)));
        return R.ok();
    }
}
