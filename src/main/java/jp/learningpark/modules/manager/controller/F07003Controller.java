/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F07003Dto;
import jp.learningpark.modules.manager.service.F07003Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F70003_教科メンテナンス一覧画面 Controller</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/04/02 : wen: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F07003")
@RestController
public class F07003Controller extends AbstractController {

    /**
     * F00051 Service
     */
    @Autowired
    F07003Service f07003Service;

    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * <p>初期表示</p>
     *
     * @param limit 当ページ数
     * @param page 各ページの最大記録数
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f07003Init(Integer limit, Integer page, String subjtCd, String subjtNm, String commonType, String reviewType) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));
        //教科一覧情報を取得
        List<F07003Dto> f07003DtoList = f07003Service.getMstCodDList(subjtCd, subjtNm, commonType, reviewType, (page - 1) * limit);
        for (F07003Dto f07003Dto : f07003DtoList) {
            f07003Dto.setUpdateTmForCheck(DateUtils.format(f07003Dto.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        }
        if (f07003DtoList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科")).put("mstOrgEntity", mstOrgEntity);
        }
        //教科一覧情報記録数取得
        Integer totalSize = f07003Service.gettMstCodDListCount(subjtCd, subjtNm, commonType, reviewType);
        return R.ok().put("mstOrgEntity", mstOrgEntity).put("page", new PageUtils(f07003DtoList, totalSize, limit, page));
    }

    /**
     * <p>削除ボタン</p>
     *
     * @param subjtCd 教科CD
     * @param updateTm 更新日時
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R deleteBtn(String subjtCd, String updateTm) {
        return f07003Service.delete(subjtCd, updateTm);
    }
}
