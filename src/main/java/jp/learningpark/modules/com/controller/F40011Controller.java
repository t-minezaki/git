/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.com.dto.F40013Dto;
import jp.learningpark.modules.com.service.F40013Service;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstManagerEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstManagerService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.service.F21017Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>スマホ_管理者共通メニュー画面</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/17 : yang: 新規<br />
 * @version 6.0
 */
@RequestMapping("/common/F40011")
@RestController
public class F40011Controller extends AbstractController {
    @Autowired
    F21017Service f21017Service;

    /**
     * ユーザ基本マスタ Service
     */
    @Autowired
    MstUsrService mstUsrService;

    /**
     * F40013Service
     */
    @Autowired
    F40013Service f40013Service;

    /**
     * ユーザ基本マスタ Service
     */
    @Autowired
    MstManagerService mstManagerService;

    @Autowired
    MstOrgService mstOrgService;

    /**
     * 初期表示
     * @return
     */
    @RequestMapping(value="/init",method = RequestMethod.GET)
    public R init() {
        SysUserEntity sysUserEntity = ShiroUtils.getUserEntity();
        String roleDiv;
        if (sysUserEntity != null) {
            if (StringUtils.isNotBlank(sysUserEntity.getRoleDiv())){
                //ログインユーザー
                roleDiv = sysUserEntity.getRoleDiv().trim();
                //組織ID
                String orgId = ShiroUtils.getUserEntity().getOrgId();
                //ログインユーザーID
                String mentorId = ShiroUtils.getUserEntity().getUsrId();
                boolean disa = true;
                /* 2021/05/25 manamiru1-666 cuikailin modify start */
                String gid = ShiroUtils.getUserEntity().getGidFlg();
                String manaFlag = StringUtils.equals("1",gid)?"0":"1";
                //セッションデータ．ログインID
                String userId = ShiroUtils.getUserEntity().getAfterUsrId();
                String brandCd = "";
                if (StringUtils.equals(manaFlag,"0")){
                    MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id",orgId).eq("del_flg",0));
                    brandCd = mstOrgEntity.getBrandCd();
                }
                MstManagerEntity mstManagerEntity = mstManagerService.getOne(new QueryWrapper<MstManagerEntity>().eq("mgr_id", mentorId).eq("del_flg", 0));
                String tchCd;
                if (mstManagerEntity != null){
                    tchCd = mstManagerEntity.getTchCd();
                }else {
                    tchCd = null;
                }
                String gidPk = ShiroUtils.getUserEntity().getGidpk();
                //userIdで該当する情報を取得する
                List<F40013Dto> f40013DtoList = f40013Service.getF40013DtoList(userId,"","",0,null,manaFlag,brandCd, gid, tchCd, gidPk);
                if (f40013DtoList.size() > 1) {
                    disa = false;
                }
                /* 2021/05/25 manamiru1-666 cuikailin modify end */
                //ログイン当日（YYYYMMDD）
                String date = DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
                //組織名
                String orgName = ShiroUtils.getUserEntity().getOrgNm();
                //未確認連絡数を取得
                Integer unreadCount = f21017Service.getUnreadCount(roleDiv, mentorId, orgId, date);
                return R.ok().put("orgName", orgName).put("unreadCount", unreadCount).put("roleDiv", roleDiv).put("disa", disa);
            }else {
                logger.info("ユーザーテーブルのユーザキャラクターデータを確認してください。");
                return R.error("ユーザーテーブルのユーザキャラクターデータを確認してください。");
            }
        }else {
            logger.info("ログインしてください。");
            return R.error("ログインしてください。");
        }
    }
}
