/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.MstMessageEntity;
import jp.learningpark.modules.common.service.MstMessageService;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dto.F21071Dto;
import jp.learningpark.modules.manager.service.F21071Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>F21071_配信先既読未読状態確認一覧画面（インフォメーション）Controller</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/06/01 : yang: 新規<br />
 * @version 7.0
 */
@RequestMapping("/manager/F21071")
@RestController
public class F21071Controller {
    @Autowired
    MstMessageService mstMessageService;
    @Autowired
    F21071Service f21071Service;
    @Autowired
    CommonService commonService;
    /**
     * 初期表示
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer msgId,Integer limit, Integer page) {
        //組織ＩＤ
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //組織名
        String orgNm = ShiroUtils.getUserEntity().getOrgNm();
        // 引渡データ．IDを元に、下記条件、メッセージマスタを元に取得し、画面で表示される。
        MstMessageEntity mstMessageEntity = mstMessageService.getOne(new QueryWrapper<MstMessageEntity>().eq("id",msgId).eq("message_type_div","2").eq("del_flg",0));
        List<F21071Dto> f21071DtoList = new ArrayList<>();
        List<F21071Dto> f21071DtoListSize = new ArrayList<>();
        //上記で取得した「組織ID」＝セッションデータ．組織IDの場合
        if (StringUtils.equals(mstMessageEntity.getOrgId(), ShiroUtils.getUserEntity().getOrgId())){
            //本組織指定された配信先組織の既読未読集計一覧を取得する。
            f21071DtoList = f21071Service.getGridList(msgId,null,limit,(page - 1) * limit,true);
            f21071DtoListSize = f21071Service.getGridList(msgId,null,null,null,true);
        }else {
            //本組織と本組織の下層組織を取得し
            List<OrgAndLowerOrgIdDto> orgList = commonService.getThisAndLowerOrgId(ShiroUtils.getBrandcd(),ShiroUtils.getUserEntity().getOrgId());
            List<String> orgIdList = new ArrayList<>();
            for(OrgAndLowerOrgIdDto dto:orgList){
                orgIdList.add(dto.getOrgId());
            }
            //上層組織から指定された配信先組織のうち、本組織と、本組織の下層組織対応する配信先組織の既読未読集計一覧を取得する。
            f21071DtoList = f21071Service.getGridList(msgId,orgIdList,limit,(page - 1) * limit,false);
            f21071DtoListSize = f21071Service.getGridList(msgId,orgIdList,null,null,false);
        }
       return R.ok().put("mstMessageEntity",mstMessageEntity).put("page", new PageUtils(f21071DtoList, f21071DtoListSize.size(), limit, page)).put("orgId",orgId).put("orgNm",orgNm);
    }
}
