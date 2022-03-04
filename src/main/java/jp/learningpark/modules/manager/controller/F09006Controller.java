package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.manager.dto.F09006ResetPointDto;
import jp.learningpark.modules.manager.service.F09006Service;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/manager/F09006")
public class F09006Controller {
    private final F09006Service f09006Service;

    public F09006Controller(F09006Service f09006Service) {
        this.f09006Service = f09006Service;
    }

    /**
     * 初期化
     *
     * @param limit １ページ最大件数
     * @param page  現在のページ数
     * * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page) {
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        String orgId = userEntity.getOrgId();
        String roleDiv = StringUtils.trim(userEntity.getRoleDiv());
        String usrId = userEntity.getUsrId();

        Map<String, Object> paramsMap = new HashMap<>(8);
        paramsMap.put("roleDiv", roleDiv);
        paramsMap.put("usrId", usrId);
        paramsMap.put("orgId", orgId);
        return f09006Service.init(paramsMap, limit, page);
    }
    /**
     * 検索後
     *
     * @param limit １ページ最大件数
     * @param page  現在のページ数
     * @param stuList 検索後のstuList
     * * @return
     */
    @RequestMapping(value = "/check_after", method = RequestMethod.GET)
    public R checkAfter(Integer limit, Integer page, String stuList) {
        List<String> stuIdList = JSON.parseArray(stuList, String.class);

        return f09006Service.checkAfter(stuIdList, limit, page);
    }

    @PostMapping("/resetPoint")
    public R resetStuPoint(@RequestBody F09006ResetPointDto dto){
        return f09006Service.resetStuPoint(dto);
    }
}
