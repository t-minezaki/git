/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.manager.dto.F09023Dto;
import jp.learningpark.modules.manager.service.F09023Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>既存利用者ログインID同報設定画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/08/03 : wq: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F09023")
@RestController
public class F09023Controller {

    /**
     * コードマスタ_明細 Service
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * 既存利用者ログインID同報設定画面 Service
     */
    @Autowired
    private F09023Service f09023Service;

    /**
     * <p>初期データを取得</p>
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init() {
        R info = R.ok();
        //該当組織情報を取得する
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String orgNm = ShiroUtils.getUserEntity().getOrgNm();
        info.put("orgId", orgId).put("orgNm", orgNm);
        //ロール区分を取得する
        List<MstCodDEntity> roleList = mstCodDService.list(new QueryWrapper<MstCodDEntity>()
                .select("cod_cd", "cod_value")
                .eq("cod_key", "ROLE_DIV")
                .eq("del_flg", 0)
                // 管理者とメンターのみを表示
                .in("cod_cd", Arrays.asList("1", "2"))
                .orderByAsc("sort"));

        info.put("roleList", roleList);
        return info;
    }

    /**
     * 存在チェック
     *
     * @param afterUsrId 利用者ログインID
     */
    @GetMapping("/check")
    public R isExist(String afterUsrId, String roleDiv) {
        String currentUsrId = ShiroUtils.getUserEntity().getAfterUsrId();
        String orgId = ShiroUtils.getUserEntity().getOrgId();

        // セッションデータ．ログインIDをもとに、組織マスタから該当ログインIDに対するブランドを取得する。
        String brandCode = f09023Service.getBrandCode(currentUsrId, orgId);

        // 画面入力した利用者ログインIDをもとに、下記条件でユーザ基本マスタを取得する。
        List<F09023Dto> roles = f09023Service.getRoleDataList(roleDiv,"checkRole", orgId, afterUsrId);
        if (roles.isEmpty()) {
            return R.error(MessageUtils.getMessage("MSGCOMN0075", "利用者ログインID：" + afterUsrId));
        }

        // 取得したブランドコード１　≠　取得したブランドコード２の場合、 処理中断し、
        for (F09023Dto role : roles) {
            if (!Objects.equals(brandCode, role.getBrandCd())){
                return R.error(MessageUtils.getMessage("MSGCOMN0164"));
            }
        }

        return R.ok();
    }

    /**
     * @param list
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R DBLogin(String list) {
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        List<F09023Dto> objList = JSONArray.parseArray(list, F09023Dto.class);
        List<F09023Dto> submitCheck = new ArrayList<>();
        List<F09023Dto> roleData = new ArrayList<>();
        List<F09023Dto> usrList = null;
        //同報対象データリストをもとに下記条件でユーザ基本マスタを取得する。
        for (F09023Dto dto : objList) {
            submitCheck.addAll(f09023Service.getRoleDataList("", "", orgId, dto.getLoginId()));
        }
        if (submitCheck.size() == 0) {
            //同報対象データリストをもとに、各ロール基本情報を取得する
            for (F09023Dto dto : objList) {
                usrList = f09023Service.getRoleDataList(dto.getRoleDiv(), "", orgId, dto.getLoginId());
                // 複数データありの場合、１件目を取得する。
                if (!usrList.isEmpty()){
                    F09023Dto usr = usrList.get(0);
                    usr.setLoginId(dto.getLoginId());
                    usr.setCommKey(dto.getCommKey());
                    usr.setRoleDiv(dto.getRoleDiv());
                    roleData.add(usr);
                }
            }
        }
        return f09023Service.DBLogin(roleData);
    }
}
