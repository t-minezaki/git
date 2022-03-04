/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.service.GuardReadingStsService;
import jp.learningpark.modules.guard.dto.F30420Dto;
import jp.learningpark.modules.guard.service.F30420Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>チャンネル詳細</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/20 : zpa: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/guard/F30420")
public class F30420Controller {
    @Autowired
    F30420Service f30420Service;
    @Autowired
    GuardReadingStsService guardReadingStsService;
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer noticeId){

        String guardId = ShiroUtils.getUserEntity().getUsrId();
        //初期表示
        F30420Dto f30420Dto = f30420Service.init(noticeId,guardId,ShiroUtils.getSessionAttribute("stuId").toString());
        if(f30420Dto != null)
        {
            Date time = f30420Dto.getUpdTime();
            String t = DateUtils.format(time, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
            //保護者お知らせ閲覧状況更新する
            if(f30420Dto.getReadingStsDiv().equals("0"))
            {
                GuardReadingStsEntity guardReadingStsEntity = guardReadingStsService.getOne(new QueryWrapper<GuardReadingStsEntity>().eq("id", f30420Dto.getId()));
                guardReadingStsEntity.setReadingStsDiv("1");
                guardReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
                guardReadingStsEntity.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
                guardReadingStsService.saveOrUpdate(guardReadingStsEntity);
            }
            return R.ok().put("f30420Dto",f30420Dto).put("time",t);
        }
        return R.ok();
    }
    /* 2020/11/12 V9.0 cuikailin add start */
    /**
     * @param id 保護者お知らせ閲覧状況．ＩＤ
     * @return
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public R confirm(Integer id) {
        GuardReadingStsEntity guardReadingStsEntity = guardReadingStsService.getById(id);
        guardReadingStsEntity.setOpenedFlg("1");
        guardReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
        guardReadingStsEntity.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
        boolean update = guardReadingStsService.updateById(guardReadingStsEntity);
        return R.ok().put("updateFlag",update);
    }
    /* 2020/11/12 V9.0 cuikailin add end */
}
