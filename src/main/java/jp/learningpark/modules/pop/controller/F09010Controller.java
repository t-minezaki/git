/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>F09010_組織複数選択画面（POP）Controller</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/12/03 : yang: 新規<br />
 * @version 5.0
 */

@RequestMapping("/pop/F09010")
@RestController
public class F09010Controller {
    /**
     * 共通 Service
     */
    @Autowired
    CommonService commonService;

    /**
     * 初期表示
     *
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R init() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        List<String> orgIds = new ArrayList<>();
        //本組織及び下層組織リストの取得
        List<OrgAndLowerOrgIdDto> orgIdList = commonService.getThisAndLowerOrgId(brandCd, orgId);
        for (OrgAndLowerOrgIdDto dto:orgIdList) {
            orgIds.add(dto.getOrgId());
        }

        return R.ok().put("orgIds",orgIds).put("orgIdList",orgIdList);
    }

}