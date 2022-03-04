/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.com.dto.F40013Dto;
import jp.learningpark.modules.com.service.F40013Service;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstManagerEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstManagerService;
import jp.learningpark.modules.common.service.MstUsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>教室選択画面</p>
 * <p>controller</p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/3 : xie: 新規<br />
 * @version 1.0
 */
@RequestMapping("/common/F40014")
@RestController
public class F40014Controller {
    /**\
     * F40013Service
     */
    @Autowired
    F40013Service f40013Service;

    /**
     * mstUsrService
     * NWT　楊　2021/07/01　MANAMIRU1-699 --> 727　Edit
     */
    @Autowired
    MstUsrService mstUsrService;
    /**
     * mstManagerService
     * NWT　楊　2021/07/01　MANAMIRU1-699 --> 727　Edit
     */
    @Autowired
    MstManagerService mstManagerService;

    @RequestMapping(value="/init",method = RequestMethod.GET)
    public R init(Integer limit, Integer page,String orgNm,String orgId){
        String manaFlag = (String) ShiroUtils.getSessionAttribute(GakkenConstant.MANA_FLAG);
        //セッションデータ．ログインID
        String userId = (String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_AFTER_USR_ID);
        String brandCd = "";
        if (StringUtils.equals(manaFlag,"0")){
            brandCd= (String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_BRAND_CD);
        }
        //userIdで該当する情報を取得する
        String orgNow = "";
        if (ShiroUtils.getUserEntity() != null){
            orgNow = ShiroUtils.getUserEntity().getOrgId();
        }
        Integer offset = 0;
        String gid = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GID_FLG);
        String tchCd = (String)ShiroUtils.getSessionAttribute(GakkenConstant.TCH_CD);
        String gidPk = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GIDPK);
        //NWT　楊　2021/07/20　MANAMIRU1-727-①　Edit　Start
        Integer userCount = 0;
        if (StringUtils.equals("1",gid)){
            if (StringUtils.isNotBlank(tchCd)){
                userCount = mstManagerService.count(new QueryWrapper<MstManagerEntity>().eq("tch_cd",tchCd).eq("del_flg",0));
                tchCd = userCount == 0 ? null:tchCd;
            }
            if (StringUtils.isNotBlank(gidPk)  && userCount == 0){
                userCount = mstUsrService.count(new QueryWrapper<MstUsrEntity>().eq("gidpk",gidPk).eq("del_flg",0));
                gidPk = userCount == 0 ? null:gidPk;
            }
        }
        //NWT　楊　2021/07/20　MANAMIRU1-727-①　Edit　End
        if (limit!=null){
            offset= (page-1)*limit;
            List<F40013Dto> f40013DtoList = f40013Service.getF40013DtoList( userId,orgNm,orgId,limit,offset,manaFlag,brandCd, gid, tchCd, gidPk);
            List<F40013Dto> count = f40013Service.getF40013DtoList( userId,orgNm,orgId,0,null,manaFlag,brandCd, gid, tchCd, gidPk);
            return R.ok().put("page", new PageUtils(f40013DtoList, count.size(), limit, page)).put("orgNow",orgNow).put("f40013DtoList",count.size());
        }
        List<F40013Dto> f40013DtoList = f40013Service.getF40013DtoList( userId,orgNm,orgId,limit,offset,manaFlag,brandCd, gid, tchCd, gidPk);
        return R.ok().put("f40013DtoList", f40013DtoList).put("orgNow",orgNow);
    }
}
