package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.manager.dto.F09024Dto;
import jp.learningpark.modules.manager.service.F09024Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>入退室履歴画面</p >
 *
 * @author NWT : wq <br />
 * <p>
 * 2021/08/18
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F09024")
public class F09024Controller {

    /**
     * 入退室履歴画面 Service
     */
    private final F09024Service service;

    public F09024Controller(F09024Service service) {
        this.service = service;
    }

    /**
     * @param map パラメータ
     * @return 入退室履歴一覧情報
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(@RequestParam Map<String, Object> map) {
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String role = ShiroUtils.getUserEntity().getRoleDiv();
        String userId = ShiroUtils.getUserEntity().getUsrId();
        map.put("orgId", orgId);
        map.put("role", role);
        map.put("userId", userId);
        String msg = MessageUtils.getMessage("MSGCOMN0017", "入退室履歴");
        Object limitation = map.get("limit");
        Object curPage = map.get("page");
        if (limitation == null || curPage == null) {
            return R.error(msg);
        }
        Integer count = service.getCount(map);
        if (count == 0) {
            return R.error(msg);
        }
        int limit = Integer.parseInt((String)map.get("limit"));
        int page = Integer.parseInt((String)map.get("page"));
        map.put("limit", limit);
        map.put("offset", page * limit - limit);
        List<F09024Dto> f09024Dtos = service.getHstList(map);
        return R.ok().put("page", new PageUtils(f09024Dtos, count, limit, page));
    }
}
