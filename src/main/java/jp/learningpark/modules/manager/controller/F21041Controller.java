/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/30
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.AskAboutRecordEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.service.AskAboutRecordService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.manager.dto.F21041Dto;
import jp.learningpark.modules.manager.service.F21041Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/04/30 : lyh: 新規<br />
 * @version 7.0
 */
@RequestMapping(value = "/manager/F21041")
@RestController
public class F21041Controller {

    /**
     * F21041  Service
     */
    @Autowired
    F21041Service f21041Service;

    /**
     * 生徒基本マスタ Service
     */
    @Autowired
    MstStuService mstStuService;

    /**
     * 問い合せ記録 Service
     */
    @Autowired
    AskAboutRecordService askAboutRecordService;

    /**
     *
     * @param stuId
     * @param startDate
     * @param stats
     * @return
     */
    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public R init(String stuId, Date startDate,String stats) {
        List<F21041Dto> degree = new ArrayList<>();
        //判定日と月
        if (StringUtils.equals(stats, "1")) {
            Date endDate = DateUtils.addDays(startDate, +6);
            //記条件で各対象日の当生徒の教科ごとの学習ブロック（※1）の実績時間を集計し画面で表示する
            degree = f21041Service.getDegree(stuId, startDate, endDate);
        } else if (StringUtils.equals(stats, "2")) {
            Date endDate = DateUtils.addWeeks(startDate, -5);
            //記条件で各対象日の当生徒の教科ごとの学習ブロック（※1）の実績時間を集計し画面で表示する
            degree = f21041Service.getDegreeWeek(stuId, startDate, endDate);
        } else {
            Date endDate = DateUtils.addMonths(DateUtils.getMonthFirstDay(startDate), -5);
            //記条件で各対象日の当生徒の教科ごとの学習ブロック（※1）の実績時間を集計し画面で表示する
            degree = f21041Service.getDegreeMonth(stuId, DateUtils.getMonthEndDay(startDate), endDate);
        }
        //学生の名前
        MstStuEntity stuInfo = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0));
        String stuNm = stuInfo.getFlnmNm() + " " + stuInfo.getFlnmLnm();
        return R.ok().put("degree", degree).put("stuNm", stuNm);
    }

    /**
     *
     * @param limit
     * @param page
     * @param nowYear
     * @return
     */
    @RequestMapping(value = "/talk",method = RequestMethod.GET)
    public R init(Integer limit, Integer page,String nowYear,String flg,String stuId){
        ShiroUtils.setSessionAttribute(GakkenConstant.STU_ID, stuId);
        //セッション・生徒組織Id
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        List<F21041Dto> total = new ArrayList<>();
        if (StringUtils.equals(flg,"1")){
            Date time = DateUtils.parse(nowYear,"yyyy");
            //面談記録一覧データを表示するため、下記条件で面談記録を取得する。
            List<F21041Dto> getTalk = f21041Service.getTalk(stuId, time, limit, (page - 1) * limit);

            total = f21041Service.getTalk(stuId, time, null, null);
            //上記取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。
            if (getTalk.size()==0){
                return R.error(MessageUtils.getMessage("MSGCOMN0017","面談記録"));
            }
            return R.ok().put("page",new PageUtils(getTalk, total.size(), limit, page));
        }else {
            List<AskAboutRecordEntity> askAboutRecordEntities = askAboutRecordService.list(new QueryWrapper<AskAboutRecordEntity>().eq("ask_user_id",stuId).eq("org_id",orgId).eq("del_flg",0).orderByAsc("ask_datime").last("LIMIT "+limit+" OFFSET "+(page-1)*limit));
            List<AskAboutRecordEntity> total2 = askAboutRecordService.list(new QueryWrapper<AskAboutRecordEntity>().eq("ask_user_id",stuId).eq("org_id",orgId).eq("del_flg",0).orderByAsc("ask_datime"));
            if (askAboutRecordEntities.size()==0){
                return R.error(MessageUtils.getMessage("MSGCOMN0017","問い合せ"));
            }
            return R.ok().put("page",new PageUtils(askAboutRecordEntities, total2.size(), limit, page));
        }

    }
    /**
     *
     * @param id
     * @return
     */

    @RequestMapping(value = "/getPop",method = RequestMethod.GET)
    public R getPop(String id){
        //セッション・生徒組織Id
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //面談記録
        List<F21041Dto> f21041Dtos = f21041Service.getPop(Integer.parseInt(id),orgId);
//        if (f21041Dtos.size()==0){
//            return R.error(MessageUtils.getMessage("MSGCOMN0017","面談記録"));
//        }
        return R.ok().put("f21041Dtos",f21041Dtos);
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getAbout",method = RequestMethod.GET)
    public R getAbout(String id){
        AskAboutRecordEntity askAboutRecordEntity = askAboutRecordService.getById(Integer.parseInt(id));
        return R.ok().put("askAboutRecordEntity",askAboutRecordEntity);
    }

    /**
     *
     * @param id
     * @param stuId
     * @param stats
     * @param title
     * @param time
     * @param content
     * @return
     */
    @RequestMapping(value = "/addOrUpdate",method = RequestMethod.POST)
    public R addOrUpdate(String id ,String stuId, String stats,String title,Date time,String content){
        //組織ID
        String orgId= ShiroUtils.getUserEntity().getOrgId();
        Timestamp timeDate = DateUtils.toTimestamp(time);
        if (StringUtils.equals(stats,"1")){
            AskAboutRecordEntity askAboutRecordEntity = new AskAboutRecordEntity();
            //セッションデータ．組織ID
            askAboutRecordEntity.setOrgId(orgId);
            //セッションデータ．生徒ID
            askAboutRecordEntity.setAskUserId(stuId);
            //画面．問い合わせ日時
            askAboutRecordEntity.setAskDatime(timeDate);
            //画面．タイトル
            askAboutRecordEntity.setAskTitle(title);
            //画面．問い合わせ内容
            askAboutRecordEntity.setAskCont(content);
            //NULL
            askAboutRecordEntity.setContlMatCont(null);
            //システム日時
            askAboutRecordEntity.setCretUsrId(ShiroUtils.getUserId());
            //ログインユーザＩＤ
            askAboutRecordEntity.setCretDatime(DateUtils.getSysTimestamp());
            //システム日時
            askAboutRecordEntity.setUpdUsrId(ShiroUtils.getUserId());
            //ログインユーザＩＤ
            askAboutRecordEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //「0：有効」
            askAboutRecordEntity.setDelFlg(0);
            //問い合わせ記録登録
            askAboutRecordService.save(askAboutRecordEntity);
        }else if (StringUtils.equals(stats,"2")){
            AskAboutRecordEntity askAboutRecordEntity = askAboutRecordService.getById(Integer.parseInt(id));
            //画面．問い合わせ日時
            askAboutRecordEntity.setAskDatime(timeDate);
            //画面．タイトル
            askAboutRecordEntity.setAskTitle(title);
            //画面．問い合わせ内容
            askAboutRecordEntity.setAskCont(content);
            //NULL
            askAboutRecordEntity.setContlMatCont(null);
            //システム日時
            askAboutRecordEntity.setCretUsrId(ShiroUtils.getUserId());
            //ログインユーザＩＤ
            askAboutRecordEntity.setCretDatime(DateUtils.getSysTimestamp());
            //システム日時
            askAboutRecordEntity.setUpdUsrId(ShiroUtils.getUserId());
            //ログインユーザＩＤ
            askAboutRecordEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //問い合わせ記録更新
            askAboutRecordService.updateById(askAboutRecordEntity);
        }else {
            AskAboutRecordEntity askAboutRecordEntity = askAboutRecordService.getById(Integer.parseInt(id));
            //「1：無効」
            askAboutRecordEntity.setDelFlg(1);
            //問い合わせ記録削除
            askAboutRecordService.updateById(askAboutRecordEntity);
        }
        return R.ok();
    }
}