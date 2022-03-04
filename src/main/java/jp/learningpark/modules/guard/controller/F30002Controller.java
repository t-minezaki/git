/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstCrmschLearnPrdService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dto.F30002Dto;
import jp.learningpark.modules.guard.service.F30002Service;
import jp.learningpark.modules.guard.service.F30419Service;
import jp.learningpark.modules.guard.service.F30421Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import jp.learningpark.modules.sys.service.ShiroService;
import jp.learningpark.modules.sys.shiro.token.UserAuthToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>F30002_子供選択画面</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/28 : gong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/guard/F30002")
@RestController
public class F30002Controller extends AbstractController {

    /**
     * F30002_子供選択画面 Service
     */
    @Autowired
    private F30002Service f30002Service;

    /**
     * 塾学習期間 Service
     */
    @Autowired
    private MstCrmschLearnPrdService mstCrmschLearnPrdService;

    /**
     *　ユーザー基本マスタ　Service
     */
    @Autowired
    private MstUsrService mstUsrService;
    /**
     * f30421Service
     */
    @Autowired
    F30421Service f30421Service;
    /**
     * f30419Service
     */
    @Autowired
    F30419Service f30419Service;

    /**
     * shiroService
     */
    @Autowired
    ShiroService shiroService;
    /**
     * commonService
     */
    @Autowired
    CommonService commonService;

    /**
     * 1． 初期表示
     *
     * @return イメージの情報
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f30002Init() {
        List<F30002Dto> stuMstEntities = new ArrayList<>();
        String username;
        String tchCdFlg = "0";
        if (StringUtils.isEmpty((String) ShiroUtils.getSessionAttribute(GakkenConstant.TCH_CD))){
            username = StringUtils.equals("0",ShiroUtils.getUserEntity().getGidFlg())?ShiroUtils.getUserEntity().getAfterUsrId():ShiroUtils.getUserEntity().getGidpk();
        }else {
            username = (String) ShiroUtils.getSessionAttribute(GakkenConstant.TCH_CD);
            tchCdFlg = "1";
        }
        String manaFlag = (String)ShiroUtils.getSessionAttribute(GakkenConstant.MANA_FLAG);
        String brandCd = ShiroUtils.getBrandcd();
        List<SysUserEntity> userEntityList = shiroService.getUserByLoginId(username, manaFlag, brandCd, ShiroUtils.getUserEntity().getGidFlg(), tchCdFlg);
        List<String> guardIds = new ArrayList<>();
        //複数教室
        for (MstUsrEntity user : userEntityList) {
            guardIds.add(user.getUsrId());
        }
        String guards = String.join(",", guardIds);
        stuMstEntities = f30002Service.getStudents(guards);
        return R.ok().put("stuList", stuMstEntities);
    }

    /**
     * 2． 生徒idの保存する
     *
     * @param stuId 生徒id
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public R f30002save(String stuId, String orgId, HttpServletResponse response) {
        ShiroUtils.setSessionAttribute(GakkenConstant.STU_ID, stuId);
        ShiroUtils.setSessionAttribute("orgId", orgId);
//        //共通処理「 塾学習期間IDの取得」を呼び出し、
        Integer crmLearnPrdId = shiroService.getLearnPrdIdByUserId(stuId,orgId);
        logger.info("crmLearnPrdId",crmLearnPrdId);
        if (crmLearnPrdId == null) {//取得できない場合
            /* 2021/09/16 manamiru1-772 cuikl del start */
            /* 2021/09/16 manamiru1-772 cuikl del end */
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "塾学習期間"));
        } else {
            //塾学習期間IDより塾情報を取得する
            MstCrmschLearnPrdEntity mstCrmschLearnPrdEntity = mstCrmschLearnPrdService.getById(crmLearnPrdId);
            //or 計画期間終了日 < システム日付　or 計画期間開始日 > システム日付
            if (mstCrmschLearnPrdEntity.getPlanPrdStartDy().compareTo(new Date()) > 0 || mstCrmschLearnPrdEntity.getPlanPrdEndDy().compareTo(
                    new Date()) < 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "塾学習期間"));
            }
            // 2020/11/11 huangxinliang modify start
            //「Subject」を取得
            /* 2021/09/16 manamiru1-772 cuikl del start */
            /* 2021/09/16 manamiru1-772 cuikl del end */
            Subject subject = ShiroUtils.getSubject();
            //ユーザー名/パスワード認証トークンを作成する
            UserAuthToken token = null;
            //セッションでパスワードを取得する
            String password = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_PASSWORD);
            /* 2021/09/16 manamiru1-772 cuikl del start */
            /* 2021/09/16 manamiru1-772 cuikl del end */
            ShiroUtils.setSessionAttribute("crmLearnPrdId", crmLearnPrdId);
            List<F30002Dto> guardList = f30002Service.selectGuardInfo(stuId);
            /* 2021/09/16 manamiru1-772 cuikl del start */
            /* 2021/09/16 manamiru1-772 cuikl del end */
            MstUsrEntity mstUsrEntity = new MstUsrEntity();
            for (F30002Dto dto : guardList ) {
                if (StringUtils.equals(dto.getAfterUsrId(),ShiroUtils.getUserEntity().getAfterUsrId())){
                    mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id",dto.getGuardId()).eq("del_flg",0));
                }
            }
            /* 2021/09/16 manamiru1-772 cuikl del start */
            /* 2021/09/16 manamiru1-772 cuikl del end */
            if (ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_LOGIN_APP) != null) {
                HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
                commonService.updateDeviceToken(null,mstUsrEntity,null,request);
                // add at 2021/9/30 for V9.02 by NWT HuangXL START
                // add at 2021/11/18 for V9.02 by NWT HuangXL START
//                commonService.deleteRedundantDeviceToken(mstUsrEntity.getUsrId());
                // add at 2021/11/18 for V9.02 by NWT HuangXL END
                // add at 2021/9/30 for V9.02 by NWT HuangXL END
            }
            token = new UserAuthToken(StringUtils.defaultString(mstUsrEntity.getId()), password, Constant.LOGIN_TYPE_GAKKEN, false, null);
            subject.login(token);
            // 2020/11/11 huangxinliang modify end
            // 2021/12/14 manamiru1-785 cuikl add start
            String brandCd = f30002Service.getBrandcdByStu(stuId);
            ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_BRAND_CD, brandCd);
            // ブランドコードをセッションに保存
            Cookie cookie = new Cookie("brandcd", brandCd);
            cookie.setPath("/");
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
            // 2021/12/14 manamiru1-785 cuikl add end
        }
        return R.ok();
    }
}
