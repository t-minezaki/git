/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.service.GuardReadingStsService;
import jp.learningpark.modules.guard.dto.F30423Dto;
import jp.learningpark.modules.guard.service.F30423Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>褒めポイント詳細画面 Controller</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/03/05 : wq: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/guard/F30423")
public class F30423Controller {

    /**
     * 褒めポイント詳細画面 Service
     */
    @Autowired
    F30423Service f30423Service;
    /**
     * 保護者お知らせ閲覧状況 Service
     */
    @Autowired
    GuardReadingStsService guardReadingStsService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer noticeId) {
        //セッション・生徒Id
        String stuId = (String)ShiroUtils.getSessionAttribute("stuId");
        //セッション・組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        F30423Dto f30423Dto = f30423Service.getInitData(noticeId, orgId, stuId);
        if (f30423Dto == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017","褒めポイント"));
        }
        GuardReadingStsEntity guardReadingStsEntity = guardReadingStsService.getOne(new QueryWrapper<GuardReadingStsEntity>().eq("notice_id",f30423Dto.getId()));
        guardReadingStsEntity.setReadingStsDiv("1");
        try{
            guardReadingStsService.update(guardReadingStsEntity,new QueryWrapper<GuardReadingStsEntity>().eq("notice_id",f30423Dto.getId()));
        }catch (Exception e){
            return R.error("システムエラー");
        }
        return R.ok().put("f30423Dto", f30423Dto);
    }
}
