/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstGuardEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstGuardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>保護者マイページ画面</p >
 */
@RestController
@RequestMapping("guard/F30406/")
public class F30406Controller {

    /**
     * MstGuardService
     */
    @Autowired
    private MstGuardService mstGuardService;
    /**
     * コードマスタ_明細
     */
    @Autowired
    MstCodDService mstCodDService;
    /**
     * ログインURL
     */
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;
    /**
     * 初期化  ページ
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R init(HttpServletRequest request) {

        String userId = ShiroUtils.getUserEntity().getUsrId();
        MstGuardEntity mstGuardEntity = mstGuardService.getOne(new QueryWrapper<MstGuardEntity>().select("flnm_nm", "flnm_lnm").eq("guard_id", userId));
        //brandCd
        String brandCd = ShiroUtils.getBrandcd();
        if (StringUtils.isEmpty(brandCd)){
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                if ("brandcd".equals(cookies[i].getName())) {
                    brandCd = cookies[i].getValue();
                    break;
                }
            }
        }
        if (StringUtils.isEmpty(brandCd)) {
            return R.error(307, loginUrl);
        }
        String orgIdAdd = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "ORG_ID_ADD").eq("cod_cd", "1")).getCodValue4();
        return R.ok().put("guardNm", mstGuardEntity.getFlnmNm() + " " + mstGuardEntity.getFlnmLnm()).put("brandCd", brandCd).put("orgIdAdd",orgIdAdd);
    }
}