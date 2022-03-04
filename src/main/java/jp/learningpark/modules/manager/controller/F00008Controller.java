/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.service.F00008Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>F00008_パスワード初期化 Controller</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2018/12/27 : xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager")
@RestController
public class F00008Controller {

    /**
     * 組織マスタService
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * F00008_パスワード初期化 service
     */
    @Autowired
    private F00008Service f00008Service;

    /**
     * <p>初期表示と検索</p>
     *
     * @return
     */
    @RequestMapping(value = "/f00008init", method = RequestMethod.GET)
    public R f00008Init() {

        // 管理者ID
        String managerId=ShiroUtils.getUserId().toString();
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 組織
        MstOrgEntity mstOrgEntity =
                mstOrgService.getOne(new QueryWrapper<MstOrgEntity>()
                        .and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        return  R.ok().put("upOrg", mstOrgEntity).put("managerId",managerId);
    }

    /**
     * <p>対応する組織を組織中心から論理化して処理する。</p>
     * @param userId
     * @return
     */
    @RequestMapping(value = "/f00008upd", method = RequestMethod.POST)
    public R updateById(String userId){
        return f00008Service.initialPwd(userId);
    }
}
