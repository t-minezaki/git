/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.GuidReprDeliverEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.UserDisplayItemSetEntity;
import jp.learningpark.modules.common.service.GuidReprDeliverService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.UserDisplayItemSetService;
import jp.learningpark.modules.manager.dto.F21008Dto;
import jp.learningpark.modules.manager.dto.F21010Dto;
import jp.learningpark.modules.manager.service.F21008Service;
import jp.learningpark.modules.manager.service.F21010Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2019/11/29 : lyh: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F21010")
@RestController
public class F21010Controller {
    /**
     * F21010Service
     */
    @Autowired
    F21010Service f21010Service;
    /**
     * 出席簿出欠情報入力画面 Service
     */
    @Autowired
    F21008Service f21008Service;
    /**
     * UserDisplayItemSetService
     */
    @Autowired
    UserDisplayItemSetService userDisplayItemSetService;
    /**
     * GuidReprDeliverService
     */
    @Autowired
    GuidReprDeliverService guidReprDeliverService;

    /**
     * コードマスタ_明細 Service
     */
    @Autowired
    MstCodDService mstCodDService;

    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public R init(Integer limit, Integer page,String params){
        //セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        Map<String,String> paramsMap = (Map) JSON.parse(params);
        Integer count = f21010Service.count(orgId,paramsMap);
        Integer offset= (page-1)*limit;
        List<F21010Dto> f21010Dto = f21010Service.select(orgId,paramsMap,limit,offset);
        if (f21010Dto.size() == 0){
            return R.error(MessageUtils.getMessage("MSGCOMN0017","指導報告書の"));
        }


        return R.ok().put("page", new PageUtils(f21010Dto,count, limit, page));
    }

    @RequestMapping(value = "getshowItems",method = RequestMethod.GET)
    public R getshowItems(){
        //画面表示項目
        F21008Dto displayItems = f21008Service.selectDspItems(ShiroUtils.getUserId(),"F21010");
        return R.ok().put("displayItems",displayItems);
    }

    /**
     * POP画面の「確認」押下
     */
    @RequestMapping(value = "saveDspItmes",method = RequestMethod.GET)
    public R saveDspItmes(String dspitems,String menuId){
        List<String> dspitemsList = JSON.parseArray(dspitems, String.class);
        UserDisplayItemSetEntity userDisplayItemSetEntity = new UserDisplayItemSetEntity();
        userDisplayItemSetEntity = userDisplayItemSetService.getOne(new QueryWrapper<UserDisplayItemSetEntity>().eq("user_id",ShiroUtils.getUserId()).eq("menu_id",menuId).eq("del_flg",0));
        //ユーザ表示項目設定を削除する
        if (userDisplayItemSetEntity != null){
            userDisplayItemSetService.removeById(userDisplayItemSetEntity);
        }
        //ユーザID
        userDisplayItemSetEntity.setUserId(ShiroUtils.getUserId());
        //ID
        userDisplayItemSetEntity = new UserDisplayItemSetEntity();
        //画面ID
        userDisplayItemSetEntity.setMenuId(menuId);
        //表示項目
        userDisplayItemSetEntity.setDisplayItems(StringUtils.join(dspitemsList.toArray(), ','));
        //作成日時
        userDisplayItemSetEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        userDisplayItemSetEntity.setCretUsrId(ShiroUtils.getUserId());
        //更新日時
        userDisplayItemSetEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        userDisplayItemSetEntity.setUpdUsrId(ShiroUtils.getUserId());
        //削除フラグ
        userDisplayItemSetEntity.setDelFlg(0);
        userDisplayItemSetService.save(userDisplayItemSetEntity);
        return R.ok();
    }

    /**
     * @param guidReprDeliverCd
     * @return R 画面情報
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public R deleteEvent(String guidReprDeliverCd,String eventStsDiv) {
        //セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        GuidReprDeliverEntity guidReprDeliverEntity  = guidReprDeliverService.getOne(new QueryWrapper<GuidReprDeliverEntity>().eq("org_Id",orgId).eq("guid_repr_deliver_cd",guidReprDeliverCd));
        if (StringUtils.equals(eventStsDiv,"0")){
            return R.error(MessageUtils.getMessage("MSGCOMN0109","指導報告書"));
        }
        GuidReprDeliverEntity guidReprDeliverEntityCheck  = guidReprDeliverService.getOne(new QueryWrapper<GuidReprDeliverEntity>().eq("org_Id",orgId).eq("guid_repr_deliver_cd",guidReprDeliverCd));
        //排他チェックエラーの場合
        if (!StringUtils.equals(guidReprDeliverEntity.getUpdDatime().toString(),guidReprDeliverEntityCheck.getUpdDatime().toString())){
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        guidReprDeliverEntity.setDelFlg(1);
        guidReprDeliverEntity.setUpdDatime(DateUtils.getSysTimestamp());
        guidReprDeliverEntity.setUpdUsrId(ShiroUtils.getUserId());
        guidReprDeliverService.updateById(guidReprDeliverEntity);
        return R.ok();
    }

    @RequestMapping(value = "/getCod",method = RequestMethod.GET)
    public R getCod(){
        List<MstCodDEntity> mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd","cod_value").eq("cod_key","EVENT_STS_DIV").eq("del_flg",0));
        return R.ok().put("mstCodDEntityList",mstCodDEntityList);
    }
    @RequestMapping(value = "/getDetail",method = RequestMethod.GET)
    public R getDetail(String guidReprDeliverCd,String readFlg){
        return f21010Service.getDetail(guidReprDeliverCd,readFlg);
    }
}