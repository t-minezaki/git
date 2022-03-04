/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.guard.dto.F30409Dto;
import jp.learningpark.modules.guard.service.F30409Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>F30409_保護者面談の申込内容変更・キャンセル画面 Controller</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/16: yang: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("guard/F30409/")
public class F30409Controller {

    /**
     * f30409Service
     */
    @Autowired
    F30409Service f30409Service;

    /**
     * 初期化
     *
     *                flg 判断マーク
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R init( Integer flg) {
        String guardId = ShiroUtils.getUserId();
        //システム日時
        Date systemTime = DateUtils.parse(DateUtils.getSysTimestamp().toString(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS);
        //保護者が各子供のイベント申込情報を確認するため、保護者申込状況マスタから情報を取得
        /* 2020/11/17 V9.0 cuikailin add start */
        List<F30409Dto> list = f30409Service.getStuList(guardId, DateUtils.getSysTimestamp(), flg, ShiroUtils.getSessionAttribute("stuId").toString());
        List<F30409Dto> stuList = new ArrayList<>();
        List<Integer> talkIdS = new ArrayList<>();
        //面談記録IDを取得
        for(F30409Dto dto:list){
            boolean falg = true;
            if (talkIdS.size()>0){
                for (int i=0;i<talkIdS.size();i++){
                    if (talkIdS.get(i).equals(dto.getTalkId())){
                        falg = false;
                        break;
                    }
                }
            }
            if (falg){
                talkIdS.add(dto.getTalkId());
            }
        }
        // 面談記録詳細を取得
        for(F30409Dto dto:list){
            if (talkIdS.size()>0){
                for (int i=0;i<talkIdS.size();i++){
                    if (talkIdS.get(i).equals(dto.getTalkId())){
                        stuList.add(dto);
                        talkIdS.remove(i);
                        i--;
                    }
                }
            }
        }
        //面談記録詳細データを追加
        for (int j=0;j<stuList.size();j++){
            List<F30409Dto> qNList = new ArrayList<>();
            for (int i=0;i<list.size();i++){
                if (stuList.get(j).getTalkId().equals(list.get(i).getTalkId()) && StringUtils.isNotBlank(list.get(i).getQuestionName())){
                    F30409Dto dto = new F30409Dto();
                    // 設問名
                    dto.setQuestionName(list.get(i).getQuestionName());
                    // 回答形式区分
                    dto.setAnswerReltCont(list.get(i).getAnswerReltCont());
                    // 事項類型区分
                    dto.setAnswerTypeDiv(list.get(i).getAnswerTypeDiv());
                    qNList.add(dto);
                }
            }
            stuList.get(j).setQNList(qNList);
        }
        /* 2020/11/17 V9.0 cuikailin add end */
        //時間書式変換
        for (F30409Dto stu : stuList) {
            stu.setFlg(flg);
            if (DateUtils.addDays(stu.getSgdPlanDate(), stu.getChgLimtDays()).getTime() <= systemTime.getTime() || DateUtils.addDays(stu.getApplyEndDate(), 0).getTime() < systemTime.getTime()) {
                stu.setErrorMsg(MessageUtils.getMessage("MSGCOMN0117"));
            }
            Date sgdStartDate = DateUtils.parse(stu.getSgdStartDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO);
            String sgdStartDateString = DateUtils.format(sgdStartDate, GakkenConstant.DATE_FORMAT_M_D_E_HH_MM);
            /* 2020/12/18 V9.0 cuikailin add start */
            if("0".equals(stu.getRefType()) || "1".equals(stu.getRefType())){
                stu.setSgdStartDatime(sgdStartDateString + "～");
            }else{
                stu.setSgdStartDatime(sgdStartDateString);
            }
            /* 2020/12/18 V9.0 cuikailin add end */
        }
        return R.ok().put("stuList", stuList);
    }
}