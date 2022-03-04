/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.EventSchePlanDelEntity;
import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.common.entity.TalkRecordDEntity;
import jp.learningpark.modules.common.entity.TalkRecordHEntity;
import jp.learningpark.modules.common.service.EventSchePlanDelService;
import jp.learningpark.modules.common.service.GuardEventApplyStsService;
import jp.learningpark.modules.common.service.TalkRecordDService;
import jp.learningpark.modules.common.service.TalkRecordHService;
import jp.learningpark.modules.guard.dto.F30410Dto;
import jp.learningpark.modules.guard.service.F30410Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>F30410_保護者面談の申込内容キャンセル画面 Controller</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/22: yang: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("guard/F30410/")
public class F30410Controller {

    /**
     * f30410Service
     */
    @Autowired
    F30410Service f30410Service;
    /**
     * guardEventApplyStsService
     */
    @Autowired
    GuardEventApplyStsService guardEventApplyStsService;
    /**
     * eventSchePlanDelService
     */
    @Autowired
    EventSchePlanDelService eventSchePlanDelService;
    /**
     * talkRecordHService
     */
    @Autowired
    TalkRecordHService talkRecordHService;
    /**
     * talkRecordDService
     */
    @Autowired
    TalkRecordDService talkRecordDService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初期化
     *
     * @param eventId
     * @param stuId
     * @param guardId
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    /* 2020/12/18 V9.0 cuikailin modify start */
    public R init(Integer eventId, String stuId, String guardId, String refType) {
        R info = new R();
        //保護者申込状況マスタから情報を取得
        F30410Dto guardEventApplySts = f30410Service.getGuardEventApplySts(eventId, stuId, guardId, refType);
    /* 2020/12/18 V9.0 cuikailin modify end */
        //でデータが取得した場合
        if (guardEventApplySts != null) {
            info.put("msg", MessageUtils.getMessage("MSGCOMN0119"));
        }
        //時間書式変換
        Date sgdStartDate = DateUtils.parse(guardEventApplySts.getSgdStartTime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO);
        String sgdStartDateString = DateUtils.format(sgdStartDate, GakkenConstant.DATE_FORMAT_M_D_E_HH_MM);
        /* 2020/12/21 V9.0 cuikailin modify start */
        if("2".equals(refType)){
            guardEventApplySts.setSgdStartTime(sgdStartDateString);
        }else{
            guardEventApplySts.setSgdStartTime(sgdStartDateString + "～");
        }
        /* 2020/12/21 V9.0 cuikailin modify end */
        info.put("guardEventApplySts", guardEventApplySts);

        //保護者の質問事項情報を確認するため、下記条件で面談記録詳細を取得する。
        TalkRecordHEntity talkRecordHEntity = talkRecordHService.getOne(
                new QueryWrapper<TalkRecordHEntity>().eq("event_id", eventId).eq("stu_id", stuId).eq("guard_id", guardId).eq("org_id",
                        ShiroUtils.getUserEntity().getOrgId()).eq("talk_apply_sts_div", "0").eq("del_flg", 0));
        if (talkRecordHEntity != null) {
            F30410Dto f30410Dto = new F30410Dto();
            f30410Dto.setId(talkRecordHEntity.getId());
            f30410Dto.setNoteCont(talkRecordHEntity.getNoteCont());
            // 2020/12/3 huangxinliang modify start
            List<TalkRecordDEntity> talkRecordDEntityList = talkRecordDService.list(
                    new QueryWrapper<TalkRecordDEntity>().select("question_name, answer_relt_cont, answer_type_div, ask_num").eq("talk_id", talkRecordHEntity.getId()).eq("del_flg",
                            0).eq("item_type_div", "0").orderByAsc("ask_num"));
            // 2020/12/3 huangxinliang modify end
            if (talkRecordDEntityList.size() != 0) {
                f30410Dto.setTalkRecordDEntityList(talkRecordDEntityList);
                info.put("answerResult", f30410Dto);
            }
        }
        return info;
    }

    /**
     * 申込をキャンセルボタン押下時
     *
     * @param talkRecordId
     * @param eventId
     * @param stuId
     * @param guardId
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public R delete(Integer talkRecordId, Integer eventId, String stuId, String guardId, String refType) {
        String brandCd = ShiroUtils.getBrandcd();
        //保護者申込状況マスタから該当データを論理削除する
        GuardEventApplyStsEntity guardEventApplyStsEntity = f30410Service.getDeleteOne(eventId, stuId, guardId);
        //保護者イベント申込状況．閲覧回答区分＝「2．キャンセル」
        guardEventApplyStsEntity.setReplyStsDiv("2");
        //更新日時
        guardEventApplyStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        guardEventApplyStsEntity.setUpdUsrId(ShiroUtils.getUserId());
        //データベースの更新
        guardEventApplyStsService.update(guardEventApplyStsEntity, new QueryWrapper<GuardEventApplyStsEntity>().eq("id", guardEventApplyStsEntity.getId()));
        /* 2020/12/21 V9.0 cuikailin modify start */
        if (!"2".equals(refType)) {
            //イベント日程(詳細)から該当データのカラム状態を変更する。
            EventSchePlanDelEntity eventSchePlanDelEntity = eventSchePlanDelService.getOne(
                    new QueryWrapper<EventSchePlanDelEntity>().eq("id", guardEventApplyStsEntity.getEventScheDelId()).eq("del_flg", 0));
            //イベント日程(詳細)．予定済人数 ＝ イベント日程(詳細)．予定済人数 ‐1
            eventSchePlanDelEntity.setPlanedMember(eventSchePlanDelEntity.getPlanedMember() - 1);
            //更新日時
            eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            eventSchePlanDelEntity.setUpdUsrId(ShiroUtils.getUserId());
            //データベースの更新
            eventSchePlanDelService.update(eventSchePlanDelEntity, new QueryWrapper<EventSchePlanDelEntity>().eq("id", eventSchePlanDelEntity.getId()));
        }
        /* 2020/12/21 V9.0 cuikailin modify end */
        if (talkRecordId != null) {
            TalkRecordHEntity talkRecordHEntity = talkRecordHService.getOne(
                    new QueryWrapper<TalkRecordHEntity>().eq("event_id", eventId).eq("stu_id", stuId).eq("guard_id", guardId).eq("org_id",
                            ShiroUtils.getUserEntity().getOrgId()).eq("talk_apply_sts_div", "0").eq("id", talkRecordId).eq("del_flg", 0));
            if (talkRecordHEntity != null) {
                talkRecordHEntity.setUpdDatime(DateUtils.getSysTimestamp());
                talkRecordHEntity.setUpdUsrId(ShiroUtils.getUserId());
                talkRecordHEntity.setTalkApplyStsDiv("1");
                try {
                    talkRecordHService.updateById(talkRecordHEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return R.ok().put("brandCd",brandCd);
    }
}