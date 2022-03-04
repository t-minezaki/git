/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dto.F40013Dto;
import jp.learningpark.modules.com.service.F40013Service;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstFunService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dto.F00001Dto;
import jp.learningpark.modules.manager.dto.F00001ManagerTypeDto;
import jp.learningpark.modules.manager.service.F00001Service;
import jp.learningpark.modules.manager.service.F21003Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>F04001_塾ニュース一覧画面</p>
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/03/12 : tan: 新規<br />
 * @version 1.0
 */

@RequestMapping("/manager/F00001")
@RestController
public class F00001Controller {
    /**
     * ユーザ基本マスタ Service
     */
    @Autowired
    MstUsrService mstUsrService;
    /**
     * F00001 Service
     */
    @Autowired
    F00001Service f00001Service;
    /**
     * 機能マスタ Service
     */
    @Autowired
    MstFunService mstFunService;
    /**
     * 共通 Service
     */
    @Autowired
    CommonService commonService;

    /**
     *
     */
    @Autowired
    F21003Service f21003Service;

    /**
     * mstOrgService
     */
    @Autowired
    MstOrgService mstOrgService;

    /**
     * 初期表示
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(HttpServletRequest request, String orgIdNew) {
        boolean disa = true;
        if (!StringUtils.isEmpty(orgIdNew)) {
            ShiroUtils.getUserEntity().setOrgId(orgIdNew);
        }
        //セッションデータ．ユーザID
        String userId = ShiroUtils.getUserId();
        List<String> orgList = commonService.getOrgsForChoose(ShiroUtils.getUserEntity().getAfterUsrId(), ShiroUtils.getBrandcd());
        if (orgList.size() > 1) {
            disa = false;
        }
        //セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //セッションデータ．ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        //特殊権限フラグ
        String specAuthFlg = "";
        //ロール区分
        String roleDiv = "";
        boolean stuMenuShowFlg = false;
        //ユーザ基本マスタ・特殊権限フラグ
        specAuthFlg = ShiroUtils.getUserEntity().getSpecAuthFlg();
        //取得したロール区分
        roleDiv = ShiroUtils.getUserEntity().getRoleDiv().trim();
        //ユーザ別機能一覧取得処理
        List<F00001Dto> funList = f00001Service.getUserFunList(userId);
        //取得０件の場合、
        if (funList.size() == 0) {
            //組織別機能一覧取得処理
            funList = f00001Service.getOrgFunList(orgId, roleDiv);
            if (funList.size() == 0) {
                //機能マスタから全機能一覧を取得する。
                funList = f00001Service.getAllFunList(roleDiv);
                //取得０件の場合、
                if (funList.size() == 0) {
                    //処理を中断し、画面上部のエラーメッセージ領域でエラーメッセージを表示する。
                    return R.error(MessageUtils.getMessage("MSGCOMN0035", "機能名"));
                }
            }
        }
        for (F00001Dto fun : funList) {
            if (StringUtils.equals(fun.getFunId(), "F20001")) {
                if (StringUtils.equals("1", roleDiv)) {
                    if (StringUtils.equals(fun.getMgrFlg(), "1")) {
                        stuMenuShowFlg = true;
                    }
                } else if (StringUtils.equals("2", roleDiv)) {
                    if (StringUtils.equals(fun.getMentFlg(), "1")) {
                        stuMenuShowFlg = true;
                    }
                }
            }
        }
        //メニューの取得
        List<F00001ManagerTypeDto> menuList = f00001Service.getDiv(funList);
        for (F00001ManagerTypeDto dto : menuList) {
            switch (dto.getName()) {
                case "運用管理":
                    dto.setImgStr(request.getContextPath() + "/img/home.png");
                    break;
                case "教科書管理":
                    dto.setImgStr(request.getContextPath() + "/img/textbook.png");
                    break;
                case "イベント管理":
                    dto.setImgStr(request.getContextPath() + "/img/event.png");
                    break;
                case "ニュース管理":
                    dto.setImgStr(request.getContextPath() + "/img/news.png");
                    break;
                case "入退室管理":
                    dto.setImgStr(request.getContextPath() + "/img/news.png");
                    break;
                case "出席簿管理":
                    dto.setImgStr(request.getContextPath() + "/img/news.png");
                    break;
                case "生徒情報管理":
                    dto.setImgStr(request.getContextPath() + "/img/news.png");
                    break;
                case "褒め管理":
                    dto.setImgStr(request.getContextPath() + "/img/news.png");
                    break;
            }
        }
        return R.ok().put("userFunList",menuList).put("specAuthFlg",specAuthFlg).put("roleDiv",roleDiv).put("stuMenuShowFlg",stuMenuShowFlg).put("brandCd", brandCd).put("disa", disa).put("funList",funList).put("userOrgId", orgId);
    }

    /**
     * 未対応の連絡数を取得する。
     *
     * @return
     */
    @RequestMapping(value = "/getCount", method = RequestMethod.GET)
    public R getF21003Un() {
        String userId = ShiroUtils.getUserId();
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String roleDiv = ShiroUtils.getUserEntity().getRoleDiv();
        //ログイン当日（YYYYMMDD）
        String date = DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        Integer count = f21003Service.count(userId, orgId, "0", date, roleDiv);
        return R.ok().put("count", count);
    }

    @PostMapping(value = "/changeOrg")
    public R changeOrg(String orgId){
        if (StringUtils.isEmpty(orgId)){
            return R.error("組織を空にすることはできません。");
        }
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id", orgId).eq("del_flg", 0));
        if (mstOrgEntity == null){
            return R.error("組織が存在しません");
        }
        ShiroUtils.setSessionAttribute(GakkenConstant.ORG_ID, orgId);
        ShiroUtils.getUserEntity().setOrgId(orgId);
        return R.ok();
    }

    @RequestMapping(value = "/loadOrgList")
    public R loadOrgList(){
        // 2020/12/7 huangxinliang modify start
        //セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //セッションデータ．ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        String afterUsrId= ShiroUtils.getUserEntity().getAfterUsrId();
        String manaFlag = (String)ShiroUtils.getSessionAttribute(GakkenConstant.MANA_FLAG);
        String gid = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GID_FLG);
        String tchCd = (String)ShiroUtils.getSessionAttribute(GakkenConstant.TCH_CD);
        String gidPk = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GIDPK);
        //本組織及び下層組織リストの取得
        List<F00001Dto> orgIdList = f00001Service.getThisOrgId(brandCd, afterUsrId, manaFlag, gid, tchCd, gidPk);
        String sessionOrgId = (String) ShiroUtils.getSessionAttribute(GakkenConstant.ORG_ID);
        if (StringUtils.isEmpty(sessionOrgId)){
            sessionOrgId = orgId;
        }
        R r = R.ok();
        r.put("orgIdList", orgIdList);
        r.put("sessionOrgId", sessionOrgId);
        return r;
        // 2020/12/7 huangxinliang modify end
    }
}