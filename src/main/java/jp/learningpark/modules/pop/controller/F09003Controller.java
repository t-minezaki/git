/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstGrpEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstGrpService;
import jp.learningpark.modules.pop.dto.F09003Dto;
import jp.learningpark.modules.pop.service.F09003Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * <p>F09003_対象者選択画面 Controller</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/11/28 : yang: 新規<br />
 * @version 5.0
 */

@RequestMapping("/pop/F09003")
@RestController
public class F09003Controller {
    /**
     * コードマスタ Service
     */
    @Autowired
    MstCodDService mstCodDService;
    /**
     * グループマスタ Service
     */
    @Autowired
    MstGrpService mstGrpService;
    /**
     * F09003 Service
     */
    @Autowired
    F09003Service f09003Service;

    /**
     * 初期表示
     * @return
     */
    @RequestMapping(value = "init",method = RequestMethod.GET)
    public R init(){
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 2020/11/9 zhangminghao modify start
        //学年情報を取得
        List<MstCodDEntity> schyList = mstCodDService.list(new QueryWrapper<MstCodDEntity>()
                .eq("cod_key","SCHY_DIV").eq("del_flg",0).orderByAsc("sort"));
        // 出欠情報を取得
        List<MstCodDEntity> absList = mstCodDService.list(new QueryWrapper<MstCodDEntity>()
                .eq("cod_key","ABS_DIV").eq("del_flg",0).orderByAsc("sort"));
        //グループ情報を取得
        List<MstGrpEntity> mstGrpEntityList = mstGrpService.list(new QueryWrapper<MstGrpEntity>()
                .eq("org_id",orgId).eq("del_flg",0).orderByAsc("grp_id"));

        return R.ok().put("schyList",schyList).put("absList", absList).put("mstGrpEntityList",mstGrpEntityList);
        // 2020/11/9 zhangminghao modify end
    }

    /**
     *「検索」ボタン押下
     * @param params
     * @return
     */
    @RequestMapping(value = "search",method = RequestMethod.GET)
    public R search(String params){
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ログインユーザ
        String userId = ShiroUtils.getUserEntity().getUsrId();
        //ログインユーザロール
        String roleDiv  =  ShiroUtils.getUserEntity().getRoleDiv().trim();
        //データ処理
        Map<String, String> paramsMap = (Map) JSON.parse(params);
        //選択可能対象を取得
        List<F09003Dto> stuList = f09003Service.getStuList(orgId,paramsMap,userId,roleDiv);
        return R.ok().put("stuList",stuList);
    }
}