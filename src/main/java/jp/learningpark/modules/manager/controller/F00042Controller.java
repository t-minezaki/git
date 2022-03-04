/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dao.F30001Dao;
import jp.learningpark.modules.guard.service.F30001Service;
import jp.learningpark.modules.manager.dto.F00042Dto;
import jp.learningpark.modules.manager.service.F00042Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>ユーザー初期基本情報＆新規発番画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/12: xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/f00042")
@RestController
public class F00042Controller {

    @Autowired
    private F30001Dao f30001Dao;

    /**
     * 組織マスタService
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * コードマスタService
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * F00042Service
     */
    @Autowired
    F00042Service f00042Service;

    /**
     * F30001Service
     */
    @Autowired
    F30001Service f30001Service;

    @Autowired
    CommonService commonService;

    /**
     * <p>初期表示と検索</p>
     *
     * @param role ロール
     * @return R
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f00042Init(String role) {
        R info = new R();
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 組織
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        info.put("mstOrgEntity", mstOrgEntity);
        MstCodDEntity role_div = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_value_2","cod_cd").
                eq("cod_key", "ROLE_DIV").eq("cod_cd", role).eq("del_flg", 0).orderBy(true, true, "sort"));
        info.put("role_div", role_div);
        // ロール区分
        if ("4".equals(role)) {
            // 学年区分
            List<MstCodDEntity> schy_div = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").
                    eq("cod_key", "SCHY_DIV").eq("del_flg", 0).orderBy(true, true, "sort"));
            // 性別を取得
            List<MstCodDEntity> gender_div = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").
                    eq("cod_key", "GENDR_DIV").eq("del_flg", 0).orderBy(true, true, "sort"));
            info.put("schy_div", schy_div);
            info.put("gender_div", gender_div);
        }
        if ("3".equals(role)) {
            // 続柄を取得
            List<MstCodDEntity> reltnsp_div = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").
                    eq("cod_key", "RELTNSP_DIV").eq("del_flg", 0).orderBy(true, true, "sort"));
            info.put("reltnsp_div", reltnsp_div);
        }
        return info;
    }

    /**
     * IDチェック
     *
     * @param userId
     * @return userId
     */
    @RequestMapping(value = "/checkAfterUserId", method = RequestMethod.POST)
    public R checkAfterUserId(String userId) {
        // 利用者ID重複チェック
        if (f00042Service.getAfterUserId(userId) != 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0122"));
        }
        return R.ok();
    }

    /**
     * ユーザー初期基本情報
     *
     * @param f00042Dto
     * @return user情報
     */
    @RequestMapping(value = "/getInformation", method = RequestMethod.POST)
    public R getInformation(@RequestBody F00042Dto f00042Dto) {
        if (f00042Dto.getPostCd() != null) {
            // 住所マスタより、郵便番号を元に、住所情報を取得し
            List adr1 = f30001Dao.searchAddr(f00042Dto.getPostCd());
            if (adr1.isEmpty()) {
                return R.error(MessageUtils.getMessage("MSGCOMN0070", "入力した郵便番号"));
            }
        }
        return f00042Service.getInformation(f00042Dto);
    }

    /**
     * アドレス情報
     *
     * @param postcd
     * @return アドレス
     */
    @RequestMapping(value = "/searchPostCd", method = RequestMethod.POST)
    public R searchPostCd(String postcd) {
        return f30001Service.searchAddr(postcd);
    }

    /**
     * <p>取得したの最上位組織保留する</p>
     *
     * @param selectedOrg ページで選択した組織
     * @return 選ばれたトップ組織
     */
    @PostMapping("/retainTopOrg")
    public R retainTopOrg(@RequestBody(required = false) LinkedList<MstOrgEntity> selectedOrg) {
        List<MstOrgEntity> topOrg = commonService.retainTopOrgList(selectedOrg);
        return R.ok().put("orgList", topOrg);
    }

}
