package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.manager.service.F09021Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * F09021Controller
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/28 ： NWT)hxl ： 新規作成
 * @date 2020/02/28 11:22
 */
@RestController
@RequestMapping("/manager/F09021")
public class F09021Controller {

    /**
     * f09021Service
     */
    @Autowired
    F09021Service f09021Service;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初期化
     *
     * @param orgIds 組織IDリスト
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(String orgIds) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ブランドコード
        String brandcd = ShiroUtils.getBrandcd();
        //組織IDリスト
        List<String> orgIdList = getOrgIdList(orgIds, orgId);
        return f09021Service.init(brandcd, orgId, orgIdList);
    }

    /**
     * 生徒情報のクエリ
     *
     * @param orgIds    編集した組織ID
     * @param grpId     指定したグループID
     * @param schyDiv   指定した学年区分
     * @return
     */
    @RequestMapping(value = "/getStuList", method = RequestMethod.GET)
    public R getStuList(String orgIds, Integer grpId, String schyDiv) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //組織IDリスト
        List<String> orgIdList = getOrgIdList(orgIds, orgId);
        return f09021Service.getStuList(orgIdList, grpId, schyDiv);
    }

    /**
     * 組織IDリスト
     *
     * @param orgIds    組織IDリスト
     * @param orgId     組織ID
     * @return
     */
    private List<String> getOrgIdList(String orgIds, String orgId) {
        List<String> orgIdList;
        try {
            //JSON文字列を解析する
            orgIdList = JSON.parseArray(orgIds, String.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
            //デフォルトはセッションデータ・組織ID
            orgIdList = new ArrayList<>();
            orgIdList.add(orgId);
        }
        if (orgIdList == null || orgIdList.size() == 0) {
            //デフォルトはセッションデータ・組織ID
            orgIdList = new ArrayList<>();
            orgIdList.add(orgId);
        }
        return orgIdList;
    }
}
