/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.service.EventSchePlanDelService;
import jp.learningpark.modules.common.service.EventScheduleService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstEventService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F08013Dto;
import jp.learningpark.modules.manager.dto.F08013GuardApplyDto;
import jp.learningpark.modules.manager.service.F08013Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>配信設定状況確認カレンダー表示画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/12 : wq: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F08013")
@RestController
public class F08013Controller {

    /**
     * コードマスタ　Service
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     * コードマスタ　Service
     */
    @Autowired
    F08013Service f08013Service;

    /**
     * コードマスタ　Service
     */
    @Autowired
    MstOrgService mstOrgService;

    /**
     * イベントマスタ　Service
     */
    @Autowired
    MstEventService mstEventService;

    /**
     * イベント日程　Service
     */
    @Autowired
    EventScheduleService eventScheduleService;

    /**
     * イベント日程（詳細）　Service
     */
    @Autowired
    EventSchePlanDelService eventSchePlanDelService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(String userName, String eventTitle) {

        //カラーリスト
        List<MstCodDEntity> colorList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "COLOR_BASE").eq("del_flg", 0).orderByAsc("sort"));
        //ユーザーチェックリスト
        List<F08013Dto> userCheckList = f08013Service.getUserCheckList(ShiroUtils.getUserEntity().getOrgId(), userName);
        //イベントチェックリスト
        List<MstEventEntity> mstEventEntityList = f08013Service.getEventInfo(ShiroUtils.getUserEntity().getOrgId(), eventTitle);

        return R.ok().put("colorList", colorList).put("userCheckList", userCheckList).put("mstEventEntityList", mstEventEntityList);
    }

    /**
     * @param tgtYmd
     * @return
     */
    @RequestMapping(value = "/eventSchedule", method = RequestMethod.GET)
    public R getEventSchedule(String tgtYmd) {

        //今週の月曜日
        Date thisMonday = DateUtils.getMonday(DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYYMMDD));
        //今週の日曜日
        Date thisSunday = DateUtils.getSunday(DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYYMMDD));
        //保護者予定情報
        List<F08013GuardApplyDto> f08013GuardApplyDtoList = f08013Service.getEventScheDelInfo(ShiroUtils.getUserEntity().getOrgId(), thisMonday, thisSunday);
        for (F08013GuardApplyDto f08013GuardApplyDto : f08013GuardApplyDtoList) {
            if (StringUtils.equals(f08013GuardApplyDto.getRefTypeDiv(), "1")) {
                if (f08013GuardApplyDto.getPersonsLimt().intValue() == f08013GuardApplyDto.getPlanedMember().intValue()) {
                    F08013GuardApplyDto mstGuardOrStudentEntity;
                    if (f08013GuardApplyDto.getUserFlag()) {
                        mstGuardOrStudentEntity = f08013Service.getGuardInfo(f08013GuardApplyDto.getDetailId());
                    } else {
                        mstGuardOrStudentEntity = f08013Service.getStudentInfo(f08013GuardApplyDto.getDetailId());
                    }
                    if (mstGuardOrStudentEntity != null) {
                        f08013GuardApplyDto.setDisplayNm(mstGuardOrStudentEntity.getDisplayNm());
                    }
                }
            }
        }
        //保護者予定情報が未取得の場合
        if (f08013GuardApplyDtoList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "予定情報"));
        }
        //ユーザー対応の色を表示される
        //カラーリスト
        List<MstCodDEntity> colorList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "COLOR_BASE").eq("del_flg", 0).orderByAsc("sort"));
        if (colorList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "カラー"));
        }
        //ユーザーチェックリスト
        List<F08013Dto> userCheckList = f08013Service.getUserCheckList(ShiroUtils.getUserEntity().getOrgId(), null);
        Map<String, String> map = new HashMap<String, String>();
        if (userCheckList.size() > colorList.size()) {
            for (int i = 0; i < userCheckList.size(); i++) {
                if (i > colorList.size() - 1) {
                    map.put(userCheckList.get(i).getRefId(), colorList.get(i % colorList.size()).getCodValue());
                } else {
                    map.put(userCheckList.get(i).getRefId(), colorList.get(i).getCodValue());
                }
            }
        } else {
            for (int i = 0; i < userCheckList.size(); i++) {
                map.put(userCheckList.get(i).getRefId(), colorList.get(i).getCodValue());
            }
        }

        return R.ok().put("eventScheduleEntityList", f08013GuardApplyDtoList).put("map", map);
    }
}
