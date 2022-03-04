/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.MstMessageEntity;
import jp.learningpark.modules.common.entity.TalkReadingStsEntity;
import jp.learningpark.modules.common.entity.TalkRecordHEntity;
import jp.learningpark.modules.common.service.MstMessageService;
import jp.learningpark.modules.common.service.TalkReadingStsService;
import jp.learningpark.modules.common.service.TalkRecordHService;
import jp.learningpark.modules.manager.dto.F21076Dto;
import jp.learningpark.modules.manager.service.F21076Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>面談記録結果配信設定画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/18 : wq: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/manager/F21076")
public class F21076Controller {

    /**
     * 面談記録 Service
     */
    @Autowired
    TalkRecordHService talkRecordHService;

    /**
     * メッセージマスタ　Service
     */
    @Autowired
    MstMessageService mstMessageService;

    /**
     * 　メッセージ閲覧状況　Service
     */
    @Autowired
    TalkReadingStsService talkReadingStsService;

    /**
     * 　面談記録結果配信設定画面　Service
     */
    @Autowired
    F21076Service f21076Service;

    /**
     * @param userList ユーザーリスト
     * @return
     */
    @RequestMapping(value = "/deliver", method = RequestMethod.POST)
    public R deliver(String userList, Integer eventId, String flg, String questionFlg, String interviewFlg) {
        List<F21076Dto> users = JSON.parseObject(StringUtils.defaultString(userList), new TypeReference<List<F21076Dto>>() {
        });
        if (users.size() == 0) {
            return R.ok();
        }
        List<Integer> talkIds = new ArrayList<>();
        for (F21076Dto dto : users) {
            TalkRecordHEntity talkRecordHEntity = talkRecordHService.getOne(
                    new QueryWrapper<TalkRecordHEntity>().select("id").eq("event_id", eventId).eq("org_id", ShiroUtils.getUserEntity().getOrgId()).eq("stu_id",
                            dto.getStuId()).eq("guard_id", dto.getGuardId()).eq("talk_apply_sts_div", "2").eq("del_flg", 0));
            if (talkRecordHEntity != null) {
                talkIds.add(talkRecordHEntity.getId());
            } else {
                return R.error(MessageUtils.getMessage("MSGCOMN0017","面談記録"));
            }
        }
        List<MstMessageEntity> mstMessageEntities = new ArrayList<>();
        if (talkIds.size() != 0) {
            //event_id -> talk_id
            mstMessageEntities = mstMessageService.list(
                    new QueryWrapper<MstMessageEntity>().select("id", "talk_id").in("talk_id", talkIds).eq("org_id", ShiroUtils.getUserEntity().getOrgId()).eq(
                            "del_flg", 0));
            for (MstMessageEntity message : mstMessageEntities) {
                talkReadingStsService.remove(
                        new QueryWrapper<TalkReadingStsEntity>().eq("org_id", ShiroUtils.getUserEntity().getOrgId()).eq("message_id", message.getId()));
                mstMessageService.remove(
                        new QueryWrapper<MstMessageEntity>().eq("org_id", ShiroUtils.getUserEntity().getOrgId()).eq("talk_id", message.getTalkId()));
            }
        }

        return f21076Service.insert(talkIds, users, flg, questionFlg, interviewFlg);
    }
}
