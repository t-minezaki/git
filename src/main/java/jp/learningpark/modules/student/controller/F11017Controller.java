/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.student.dto.F11017Dto;
import jp.learningpark.modules.student.service.F11017Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>生徒面談の申込内容変更・キャンセル画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/14 : wq: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/student/F11017")
public class F11017Controller {

    /**
     * 生徒面談の申込内容変更・キャンセル画面 Service
     */
    @Autowired
    F11017Service f11017Service;

    /**
     * <p>生徒イベント申込状況情報を取得</p>
     *
     * @param flg 判断マーク
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer flg) {
        List<F11017Dto> list = f11017Service.selectInitApplyData(ShiroUtils.getUserId(), flg);
        /* 2020/11/18 V9.0 cuikailin add start */
        List<F11017Dto> f11017Dtos = new ArrayList<>();
        List<Integer> talkIdS = new ArrayList<>();
        //面談記録IDを取得
        for (F11017Dto dto : list) {
            boolean falg = true;
            if (talkIdS.size() > 0) {
                for (int i = 0; i < talkIdS.size(); i++) {
                    if (talkIdS.get(i).equals(dto.getTalkId())) {
                        falg = false;
                        break;
                    }
                }
            }
            if (falg) {
                talkIdS.add(dto.getTalkId());
            }
        }
        // 面談記録詳細を取得
        for (F11017Dto dto : list) {
            if (talkIdS.size() > 0) {
                for (int i = 0; i < talkIdS.size(); i++) {
                    if (talkIdS.get(i).equals(dto.getTalkId())) {
                        f11017Dtos.add(dto);
                        talkIdS.remove(i);
                        i--;
                    }
                }
            }
        }
        //面談記録詳細データを追加
        for (int j = 0; j < f11017Dtos.size(); j++) {
            List<F11017Dto> qNList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (f11017Dtos.get(j).getTalkId().equals(list.get(i).getTalkId()) && StringUtils.isNotBlank(list.get(i).getQuestionName())) {
                    F11017Dto dto = new F11017Dto();
                    // 設問名
                    dto.setQuestionName(list.get(i).getQuestionName());
                    // 回答形式区分
                    dto.setAnswerReltCont(list.get(i).getAnswerReltCont());
                    // 事項類型区分
                    dto.setAnswerTypeDiv(list.get(i).getAnswerTypeDiv());
                    qNList.add(dto);
                }
            }
            f11017Dtos.get(j).setQNList(qNList);
        }
        /* 2020/11/18 V9.0 cuikailin add end */
        for (F11017Dto f11017Dto : f11017Dtos) {
            f11017Dto.setFlg(flg);
            if (DateUtils.getSysDate().compareTo(
                    DateUtils.addDays(f11017Dto.getSgdPlanDate(), f11017Dto.getChgLimtDays())) >= 0 || DateUtils.getSysTimestamp().compareTo(
                    f11017Dto.getApplyEndDt()) > 0) {
                f11017Dto.setErrorMsg(MessageUtils.getMessage("MSGCOMN0117"));
            }
            Date startTime = new Date(f11017Dto.getSgdStartDatime().getTime());
            String sgdStartDateString = DateUtils.format(startTime, GakkenConstant.DATE_FORMAT_M_D_E_HH_MM);
            /* 2020/12/29 V9.0 cuikailin add start */
            if("2".equals(f11017Dto.getRefType())){
                f11017Dto.setDisplayTime(sgdStartDateString);
            }else{
                f11017Dto.setDisplayTime(sgdStartDateString + "～");
            }
            /* 2020/12/29 V9.0 cuikailin add end */
        }
        return R.ok().put("f11017Dtos", f11017Dtos).put("flg", flg);
    }
}
