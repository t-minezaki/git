/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.service.F30421Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>保護者共通メニュー画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/27 : xiong: 新規<br />
 * @version 1.0
 * 2019/09/30 : hujiaxing: 改修<br />
 * @version 4.0
 * 2020/03/16 : zhangpuao: 改修<br/>
 * @version 5.0
 */
@RequestMapping("/common/F40004")
@RestController
public class F40004Controller extends AbstractController {
    @Autowired
    private MstUsrService mstUsrService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private F30421Service f30421Service;
    /**
     * ログインURL
     */
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;
    /**
     * @return
     */
    @RequestMapping(value="/init",method = RequestMethod.GET)
    public R init(HttpServletRequest request) {
        boolean disa = true;
        List<String> orgList = commonService.getOrgsForChoose(ShiroUtils.getUserEntity().getAfterUsrId(), ShiroUtils.getBrandcd());
        if (orgList.size() > 1) {
            disa = false;
        }
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
        String guardId = ShiroUtils.getUserId();
        String stuId = (String) ShiroUtils.getSessionAttribute("stuId");
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //2020/11/24 liyuhuan add start
//        Integer unReadCount = commonService.pushUnreadCount(guardId);
        //お知らせの件数の取得
        Integer noticeCount = f30421Service.getNoticeCount(orgId, guardId, stuId);
        //イベントの件数の取得
        Integer eventCount = f30421Service.getEventCount(guardId, stuId);
        Integer unReadCount = noticeCount + eventCount;
        //2020/11/24 liyuhuan add end
        return R.ok().put("cou", unReadCount).put("brandCd", brandCd).put("disa", disa);
    }
    @RequestMapping(value = "/getOrg",method = RequestMethod.GET)
    public R getOrg(){
        String orgIdAdd = commonService.getOrgIdAdd();
        return R.ok().put("orgIdAdd",orgIdAdd);
    }
}
