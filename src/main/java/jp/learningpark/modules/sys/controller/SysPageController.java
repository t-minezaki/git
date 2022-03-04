/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */
package jp.learningpark.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.HttpContextUtils;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import jp.learningpark.modules.xapi.Activitys;
import jp.learningpark.modules.xapi.Extensions;
import jp.learningpark.modules.xapi.Verbs;
import jp.learningpark.modules.xapi.XApiConstant;
import jp.learningpark.modules.xapi.XApiUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * システムページコントローラ
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月24日 下午11:05:27
 */
@Controller
public class SysPageController {

    /**
     * mstCodDDao
     */
    @Autowired
    MstCodDDao mstCodDDao;
    /**
     * <p>共通処理 Serviceです。</p>
     */
    @Autowired
    CommonService commonService;
    /**
     * <p>画面の初期化</p>
     * 
     * @param module モジュール
     * @param url 画面URL
     * @return 画面パス
     */
    @RequestMapping("modules/{module}/{url}.html")
    @RequiresPermissions("manager:*")
    public String module(@PathVariable("module") String module, @PathVariable("url") String url) {
        String resultUrl = module + "/" + url;
        postAccessLog(resultUrl);
        return resultUrl;
    }

    /**
     * <p>生徒画面の初期化</p>
     * 
     * @param module モジュール
     * @param url 画面URL
     * @return 画面パス
     */
    @RequestMapping("student/{pageId}.html")
    @RequiresPermissions("student:*")
    public String student(@PathVariable("pageId") String pageId) {
        String resultUrl = "student/" + pageId;
        postAccessLog(resultUrl);
        return resultUrl;
    }

    /**
     * <p>メンター画面の初期化</p>
     * 
     * @param module モジュール
     * @param url 画面URL
     * @return 画面パス
     */
    @RequestMapping("mentor/{pageId}.html")
    @RequiresPermissions("mentor:*")
    public String mentor(@PathVariable("pageId") String pageId) {
        String resultUrl = "mentor/" + pageId;
        postAccessLog(resultUrl);
        return resultUrl;
    }

    /**
     * <p>管理者画面の初期化</p>
     * 
     * @param module モジュール
     * @param url 画面URL
     * @return 画面パス
     */
    @RequestMapping("manager/{pageId}.html")
    @RequiresPermissions("manager:*")
    public String manager(@PathVariable("pageId") String pageId) {
        String resultUrl = "manager/" + pageId;
        postAccessLog(resultUrl);
        return resultUrl;
    }

    /**
     * <p>保護者画面の初期化</p>
     * 
     * @param module モジュール
     * @param url 画面URL
     * @return 画面パス
     */
    @RequestMapping("guard/{pageId}.html")
    @RequiresPermissions("guard:*")
    public String guard(@PathVariable("pageId") String pageId) {
        String resultUrl = "guard/" + pageId;
        //postAccessLog(resultUrl);
        return resultUrl;
    }

    /**
     * <p>共通画面の初期化</p>
     * 
     * @param module モジュール
     * @param url 画面URL
     * @return 画面パス
     */
    @RequestMapping("common/{pageId}.html")
    public String common(@PathVariable("pageId") String pageId) {
        return "common/" + pageId;
    }

    /**
     * <p>共通エラー画面の初期化</p>
     * 
     * @param module モジュール
     * @param url 画面URL
     * @return 画面パス
     */
    @RequestMapping("error/{pageId}.html")
    public String error(@PathVariable("pageId") String pageId) {
        return "error/" + pageId;
    }

    /**
     * <p>ポップアップ画面の初期化</p>
     * 
     * @param module モジュール
     * @param url 画面URL
     * @return 画面パス
     */
    @RequestMapping("pop/{pageId}.html")
    public String pop(@PathVariable("pageId") String pageId) {
        String resultUrl = "pop/" + pageId;
        postAccessLog(resultUrl);
        return resultUrl;
    }

    /**
     * <p>ログイン画面の初期化</p>
     *
     * @param brandcd ブランドコード
     * @return パス
     */
    @RequestMapping(value = { "/", "/login" })
    public String login(Model model, HttpServletRequest request, @ModelAttribute("errorMsg") String errorMsg, @ModelAttribute("lockFlg") String lockFlg) {
        Map<String, String> map = new HashMap<>();
        String brandcd = StringUtils.defaultString(request.getParameter("brandcd"));
        String redirectUrl = "login";
        map.put("brandcd", brandcd);
        map.put("errorMsg", errorMsg);
        //TODO
        map.put("lockFlg", lockFlg);
        model.addAllAttributes(map);
        if(StringUtils.isEmpty(brandcd)){
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            Cookie[] cookies = httpServletRequest.getCookies();
            if (cookies != null) {
                String manaFlag = null;
                for (Cookie cookie : cookies) {
                    if ("brandcd".equals(cookie.getName())) {
                        brandcd = cookie.getValue();
                        continue;
                    }
                    if (StringUtils.equals(cookie.getName(), GakkenConstant.MANA_FLAG)){
                        manaFlag = cookie.getValue();
                    }
                }
                if (StringUtils.equals(manaFlag, "1")){
                    brandcd = getManaUrl();
                }
                brandcd = StringUtils.isEmpty(brandcd) ? commonService.getOrgIdAdd() : brandcd;
                redirectUrl = redirectUrl + "/" + brandcd;
                return "redirect:" + redirectUrl;
            }
            return redirectUrl;
        }else {
            return redirectUrl;
        }
    }

    private String getManaUrl() {
        String brandCd;
        MstCodDEntity mstCodDEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "URL_ADD_KEY").eq("del_flg", 0));
        brandCd = mstCodDEntity.getCodValue();
        return brandCd;
    }

    /**
     * <p>ログイン画面の初期化</p>
     *
     * @param brandcd ブランドコード
     * @return パス
     */
    @RequestMapping(value = { "/login/{brandcd}" })
    public String login(Model model, @PathVariable("brandcd") String brandcd) {
        Map<String, String> map = new HashMap<>();
        map.put("brandcd", brandcd);
        model.addAllAttributes(map);
        return "login";
    }
    
    /**
     * <p>システム管理画面の初期化</p>
     *
     * @return パス
     */
    @RequestMapping(value = {"/sys/index.html"})
    public String index() {
        return "sys/index";
    }

    @RequestMapping("sys/main.html")
    public String main() {
        return "sys/main";
    }

    @RequestMapping("/error/403")
    public String forbidden() {
        return "/error/403";
    }

    @RequestMapping("/error/404")
    public String notFound() {
        return "/error/404";
    }

    @RequestMapping("/error/500")
    public String serverErr() {
        return "/error/500";
    }

//    @RequestMapping("test")
//    public String test() {
//        return "test";
//    }

    /**
     * <p>システムログイン画面の初期化</p>
     *
     * @param brandcd ブランドコード
     * @return パス
     */
    private void postAccessLog(String url) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        if (user == null) {
            return;
        }
        // ユーザログイン
        Extensions exts = new Extensions();
        // 利用者のシステムID
        exts.put(XApiConstant.EXT_KEY_USER_ID, user.getUsrId());
        // 開始時間
        exts.put(XApiConstant.EXT_KEY_VISIT_TIME, DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS));
        // 画面URL
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        exts.put(XApiConstant.EXT_KEY_URL, request.getRequestURL());
        try {
            XApiUtils.saveStatement(Verbs.launched(), Activitys.link(), exts);
        } catch (Exception e) {
            
        }
    }
}
