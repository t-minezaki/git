package jp.learningpark.modules.pop.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.pop.dto.F21074Dto;
import jp.learningpark.modules.pop.service.F21074Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>配信先選択画面（POP） Controller</p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/06/04 ： NWT)hxl ： 新規作成
 * 2020/11/09 ： NWT)文 ： 要件変更
 */
@RequestMapping("/pop/F21074")
@RestController
public class F21074Controller {

    /**
     * 組織マスタService
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * 共通 Service
     */
    @Autowired
    private CommonService commonService;

    /**
     * F04004Service
     */
    @Autowired
    private F21074Service f21074Service;

    /**
     * <p>初期表示</p>
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f21074Init() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));
        //本組織及び下層組織リストの取得
        List<OrgAndLowerOrgIdDto> orgIdList = commonService.getThisAndLowerOrgId(brandCd, orgId);
        return R.ok().put("org", org).put("orgIdList", orgIdList);
    }

    /**
     * <p>検索</p>
     *
     * @param type メッセージタイプ
     * @param list 組織IDリスト
     * @param searchName 氏名
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public R f21074Search(String type, String searchName, String loginId, String list) {
        //画面．検索条件．組織IDが一件でも選択していない場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
        List<String> orgIdList = (List<String>)JSON.parse(list);
        if (orgIdList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0028", "組織"));
        }
        if (StringUtils.isEmpty(type)) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "対象ユーザー"));
        }
        return f21074Service.getEntity(type, searchName, loginId, orgIdList);
    }

    /**
     * <p>検索</p>
     *
     * @param type メッセージタイプ
     * @param list 組織IDリスト
     * @param searchName 氏名
     * @return
     */
    @RequestMapping(value = "/getUsers", method = RequestMethod.POST)
    public R getUsers(String type, String searchName, String loginId, String list) {
        //画面．検索条件．組織IDが一件でも選択していない場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
        List<String> orgIdList = JSON.parseArray(list, String.class);
        if (orgIdList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0028", "組織"));
        }
        if (StringUtils.isEmpty(type)) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "対象ユーザー"));
        }
        R info = f21074Service.getEntity(type, searchName, loginId, orgIdList);
        return R.ok().put("personList", info.get("personList"));
    }
}
