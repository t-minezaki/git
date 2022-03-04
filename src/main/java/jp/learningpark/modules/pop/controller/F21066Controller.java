package jp.learningpark.modules.pop.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.pop.service.F21066Service;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/22 ： NWT)hxl ： 新規作成
 * @date 2020/05/22 11:29
 */
@RequestMapping("/pop/F21066")
@RestController
public class F21066Controller {
    /**
     * f04007Service
     */
    @Autowired
    private F21066Service f21066Service;

    /**
     * お知らせ配信先、組織情報、保護者お知らせ閲覧状況より、取得する。
     *
     * @param page      ページ
     * @param limit     '1ページのMAX件数30件
     * @param orgId     保護者お知らせ閲覧状況．組織ID
     * @param messageId お知らせId
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer page, Integer limit, @Param("orgId") String orgId, @Param("messageId") Integer messageId, @Param("flg") String flg) {
        //組織名
        String orgNm = ShiroUtils.getUserEntity().getOrgNm();
        return f21066Service.init(messageId, orgId, page, flg, limit).put("orgNm", orgNm);
    }
}
