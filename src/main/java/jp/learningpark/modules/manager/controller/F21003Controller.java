package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.LateAbsHstEntity;
import jp.learningpark.modules.common.service.LateAbsHstService;
import jp.learningpark.modules.common.service.MentorStuService;
import jp.learningpark.modules.common.service.MstGuardService;
import jp.learningpark.modules.manager.dto.F21003Dto;
import jp.learningpark.modules.manager.service.F21003Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p></p >
 *
 * @author NWT : LiYuHuan <br />
 * @version 1.0
 */
@RequestMapping("/manager/F21003")
@RestController
public class F21003Controller {
    /**
     * メンター生徒管理 Service
     */
    @Autowired
    MentorStuService mentorStuService;
    /**
     * 遅刻欠席連絡履歴 Service
     */
    @Autowired
    LateAbsHstService lateAbsHstService;
    /**
     * 保護者基本マスタ Service
     */
    @Autowired
    MstGuardService mstGuardService;
    /**
     * f21003 Service
     */
    @Autowired
    F21003Service f21003Service;

    /**
     * 初期化
     *
     * @return
     */
    @RequestMapping(value = "/init" , method = RequestMethod.GET)
    public R init(Integer limit, Integer page, String corrspdSts){
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();
        //組織IDの取得
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ログインユーザのロール
        String roleDiv = ShiroUtils.getUserEntity().getRoleDiv().trim();
        //ログイン当日（YYYYMMDD）
        String date = DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        Integer offset= (page-1)*limit;
        if (StringUtils.equals(corrspdSts,"1")){
            Integer count = f21003Service.count(userId,orgId,corrspdSts,date,roleDiv);
            // クエリーデータセットを取得する
            List<F21003Dto> f21003DtoList = f21003Service.select(userId,orgId,corrspdSts,date,roleDiv,limit,offset);
//            if (f21003DtoList.size() == 0){
//                return R.error(MessageUtils.getMessage("MSGCOMN0017","遅刻欠席連絡"));
//            }
            return R.ok().put("page", new PageUtils(f21003DtoList, count, limit, page));
        }else {
            Integer count = f21003Service.count(userId,orgId,corrspdSts,date,roleDiv);
        List<F21003Dto> f21003DtoList = f21003Service.select(userId,orgId,corrspdSts,date,roleDiv,limit,offset);
            // プロジェクトを取得するとページに戻る
//            if (f21003DtoList.size() == 0){
//                return R.error(MessageUtils.getMessage("MSGCOMN0017","遅刻欠席連絡"));
//            }
        return R.ok().put("page", new PageUtils(f21003DtoList, count, limit, page));
        }
    }

    @RequestMapping(value = "/change",method = RequestMethod.GET)
    public R change(Integer id,String corrspdSts){
        //対応するフィールド判定
        LateAbsHstEntity lateAbsHstEntityList = lateAbsHstService.getById(id);
        if (StringUtils.equals(corrspdSts,"0")) {
            lateAbsHstEntityList.setCorrspdSts("2");
            lateAbsHstService.updateById(lateAbsHstEntityList);
        }
        if(StringUtils.equals(corrspdSts,"3")){
            lateAbsHstEntityList.setCorrspdSts("1");
            lateAbsHstService.updateById(lateAbsHstEntityList);
        }
        if (StringUtils.equals(corrspdSts,"1")) {
            lateAbsHstEntityList.setCorrspdSts("3");
            lateAbsHstService.updateById(lateAbsHstEntityList);
        }
        if(StringUtils.equals(corrspdSts,"2")){
            lateAbsHstEntityList.setCorrspdSts("0");
            lateAbsHstService.updateById(lateAbsHstEntityList);
        }
        return  R.ok();
    }
}
