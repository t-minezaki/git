/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.pop.service.F00003Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>F00003_組織設定・修正画面 Controller</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/24 : gong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/pop/F00003")
@RestController
public class F00003Controller extends AbstractController {
    /**
     * 組織Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * 組織設定・修正画面 Service
     */
    @Autowired
    private F00003Service f00003Service;

    /**
     *
     */
    @Autowired
    private CommonService commonService;

    /**
     * <p>初期表示</p>
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f00003Init(Integer id) {
        MstOrgEntity mstOrgEntity=null;
        //セッションデータ．組織ID
        String currentOrgId = ShiroUtils.getUserEntity().getOrgId();
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        //本組織、下層組織IDリストを取得し
        List<OrgAndLowerOrgIdDto> orgList = commonService.getThisAndLowerOrgId(brandCd, currentOrgId);
        if(id!=null){
            //編集の場合
            mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("id", id).eq("del_flg", 0)));
            // 2020/12/10 huangxinliang modify start
            List<OrgAndLowerOrgIdDto> lowerOrgId = commonService.getThisAndLowerOrgId(brandCd, mstOrgEntity.getOrgId());
            Set<String> stringOrgIdSet = lowerOrgId.stream().map(OrgAndLowerOrgIdDto::getOrgId).collect(Collectors.toSet());
            orgList.removeIf(orgAndLowerOrgIdDto -> stringOrgIdSet.contains(orgAndLowerOrgIdDto.getOrgId()));
            // 2020/12/10 huangxinliang modify end
            return R.ok().put("org", mstOrgEntity).put("orgList", orgList).put("orgId",currentOrgId).put("upDatime",mstOrgEntity.getUpdDatime().toString());
        }
        return R.ok().put("org", mstOrgEntity).put("orgList", orgList).put("orgId",currentOrgId);
    }

    /**
     * <p>登録</p>
     *
     * @param org      画面情報
     * @param upLevel  前画面から受け取った階層
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R f00003Submit(MstOrgEntity org, Integer upLevel,String upTime) {
        return f00003Service.submitFn(org, upLevel,upTime);
    }
}
