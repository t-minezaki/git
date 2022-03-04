/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.EventSchePlanDelEntity;
import jp.learningpark.modules.common.entity.StuEventApplyStsEntity;
import jp.learningpark.modules.common.entity.TalkRecordHEntity;
import jp.learningpark.modules.common.service.EventSchePlanDelService;
import jp.learningpark.modules.common.service.StuEventApplyStsService;
import jp.learningpark.modules.common.service.TalkRecordHService;
import jp.learningpark.modules.student.dto.F11018Dto;
import jp.learningpark.modules.student.service.F11018Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>生徒面談の申込内容キャンセル画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/14 : wq: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/student/F11018")
public class F11018Controller {

    /**
     * 生徒面談の申込内容キャンセル画面 Service
     */
    @Autowired
    F11018Service f11018Service;

    /**
     * 生徒イベント申込状況　Service
     */
    @Autowired
    StuEventApplyStsService stuEventApplyStsService;

    /**
     * 面談記録　Service
     */
    @Autowired
    TalkRecordHService talkRecordHService;

    /**
     * イベント日程(詳細)　Service
     */
    @Autowired
    EventSchePlanDelService eventSchePlanDelService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @param applyId 生徒イベント申込状況.ID
     * @param refType 関連タイプ
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer applyId, String refType) {

        R info = new R();
        //面談記録情報
        F11018Dto f11018Dto = f11018Service.selectInitTalkData(applyId, ShiroUtils.getUserId(),refType);
        List<F11018Dto> f11018Dtos = new ArrayList<>();
        //でデータが取得した場合
        if (f11018Dto != null) {
            info.put("msg", MessageUtils.getMessage("MSGCOMN0119"));
            //時間書式変換
            Date startTime = new Date(f11018Dto.getSgdStartDatime().getTime());
            String sgdStartDateString = DateUtils.format(startTime, GakkenConstant.DATE_FORMAT_M_D_E_HH_MM);
            /* 2020/12/30 V9.0 cuikailin modify start */
            if ("2".equals(refType)){
                f11018Dto.setDisplayTime(sgdStartDateString);
            }else {
                f11018Dto.setDisplayTime(sgdStartDateString + "～");
            }
            /* 2020/12/30 V9.0 cuikailin modify end */
            info.put("f11018Dto", f11018Dto);
            //面談記録詳細情報
            f11018Dtos = f11018Service.selectInitTalkDelData(f11018Dto.getTrhId());
            info.put("f11018Dtos", f11018Dtos);
        }
        return info;
    }

    /**
     * 申込をキャンセルボタン押下時
     *
     * @param talkId
     * @param applyId
     * @param refType 関連タイプ
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public R delete(Integer talkId, Integer applyId, String refType) {

        //閲覧回答区分は '2' に変更されました。
        StuEventApplyStsEntity stuEventApplyStsEntity = stuEventApplyStsService.getById(applyId);
        stuEventApplyStsEntity.setReplyStsDiv("2");
        stuEventApplyStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
        stuEventApplyStsEntity.setUpdUsrId(ShiroUtils.getUserId());
        stuEventApplyStsEntity.setReplyCnt("");
        //面談申込状態区分 '1' に変更されました。
        TalkRecordHEntity talkRecordHEntity = talkRecordHService.getById(talkId);
        talkRecordHEntity.setTalkApplyStsDiv("1");
        talkRecordHEntity.setUpdDatime(DateUtils.getSysTimestamp());
        talkRecordHEntity.setUpdUsrId(ShiroUtils.getUserId());
        EventSchePlanDelEntity eventSchePlanDelEntity = new EventSchePlanDelEntity();
        /* 2020/12/30 V9.0 cuikailin modify start */
        if (!"2".equals(refType)) {
            //予定済人数 - 1
            eventSchePlanDelEntity = eventSchePlanDelService.getById(stuEventApplyStsEntity.getEventScheDelId());
            eventSchePlanDelEntity.setPlanedMember(eventSchePlanDelEntity.getPlanedMember() - 1);
            eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
            eventSchePlanDelEntity.setUpdUsrId(ShiroUtils.getUserId());
        }
        try {
            stuEventApplyStsService.updateById(stuEventApplyStsEntity);
            talkRecordHService.updateById(talkRecordHEntity);
            if (!"2".equals(refType)) {
                eventSchePlanDelService.updateById(eventSchePlanDelEntity);
            }
        /* 2020/12/30 V9.0 cuikailin modify end */
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return R.ok();
    }
}
