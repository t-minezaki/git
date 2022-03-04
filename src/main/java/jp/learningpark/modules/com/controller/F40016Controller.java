package jp.learningpark.modules.com.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.com.dao.F40016Dao;
import jp.learningpark.modules.com.dto.F40013Dto;
import jp.learningpark.modules.com.service.F40013Service;
import jp.learningpark.modules.com.service.F40016Service;
import jp.learningpark.modules.common.GakkenConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>教室選択画面(生徒スマホ画面)</p >
 * @author NWT : lpp <br />
 * 2020/08/05 : lpp: 新規<br />
 * @version 8.0
 */
@RestController
@RequestMapping("/common/F40016")
public class F40016Controller {

    /**
     * f40016Service
     */
    @Autowired
    F40016Service f40016Service;

    /**
     *
     */
    @Autowired
    F40016Dao f40016Dao;

    /**
     * f40013Service
     */
    @Autowired
    F40013Service f40013Service;

    /**
     * 初期表示
     *
     * @return
     */
    @RequestMapping("/init")
    public R init(Integer pageSize,String orgNm,String orgId) {
        String manaFlag = (String) ShiroUtils.getSessionAttribute(GakkenConstant.MANA_FLAG);
        //セッションデータ．ログインID
        String userId = (String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_AFTER_USR_ID);
        String brandCd = "";
        if (StringUtils.equals(manaFlag,"0")){
            brandCd= (String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_BRAND_CD);
        }
        String gid = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GID_FLG);
        String tchCd = (String)ShiroUtils.getSessionAttribute(GakkenConstant.TCH_CD);
        String gidPk = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GIDPK);
        //userIdで該当する情報を取得する
        List<F40013Dto> f40013DtoList = f40013Service.getF40013DtoList(userId,orgNm,orgId,pageSize,0,manaFlag,brandCd, gid, tchCd, gidPk);
        List<F40013Dto> firstCount = f40013Service.getF40013DtoList(userId,orgNm,orgId,null,null,manaFlag,brandCd, gid, tchCd, gidPk);
        String orgNow = ShiroUtils.getUserEntity() == null ? "" : ShiroUtils.getUserEntity().getOrgId();
//        if(limit != null){
//            return R.ok().put("page", new PageUtils(f40013DtoList, f40013DtoList.size(), pageSize, 1)).put("orgNow",orgNow);
//        }
        return R.ok().put("orgNow",orgNow).put("count",firstCount).put("f40013DtoList",f40013DtoList);
    }
}
