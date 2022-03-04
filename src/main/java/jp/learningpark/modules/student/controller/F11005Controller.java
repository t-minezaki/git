/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.StuWeeklyPlanPerfDao;
import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import jp.learningpark.modules.student.dto.F11001Dto;
import jp.learningpark.modules.student.dto.F11005Dto;
import jp.learningpark.modules.student.service.F11005Service;
import jp.learningpark.modules.student.service.F11007Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>レイアウト新規作成。</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/26 : zpa: 新規<br />
 * @version 7.0
 */
@RestController
@RequestMapping("/student/F11005")
public class F11005Controller {
    @Autowired
    F11005Service f11005Service;
    @Autowired
    MstCodDDao mstCodDDao;
    @Autowired
    StuWeeklyPlanPerfDao stuWeeklyPlanPerfDao;
    @Autowired
    F11007Service f11007Service;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer id,String submitType){
        List<MstCodDEntity> mstCodDEntity = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>().select("cod_value","cod_cd").eq("cod_key",
                "LEARN_LEV_UNDS").eq("del_flg", 0).orderByAsc("sort"));
        if ("0".equals(submitType)){
            F11005Dto f11005Dto = f11005Service.init(id);
            return R.ok().put("dto",f11005Dto).put("cods",mstCodDEntity);
        }else {
            // 該当生徒より、コードマスタ_明細から科目を取得する。
            List<F11001Dto> subjtDiv = f11007Service.getSubjt();
            // マスタブロックからカテゴリを取得する。
            List<MstBlockEntity> blockType = f11007Service.getBlockType();
            return R.ok().put("cods",mstCodDEntity).put("subjtDiv", subjtDiv).put("blockType", blockType);
        }
    }

    @RequestMapping(value = "/submit",method = RequestMethod.GET)
    public R submit(Integer id, Date starTime, String startYmd, Integer hours, Integer min, String memo, String llu, String block, String subjt, String subjtNm){
        MstCodDEntity mstCodDEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().select("cod_cd").eq("cod_value",llu).eq("del_flg",0));
        String codCd = mstCodDEntity.getCodCd();
        Date Ymd = DateUtils.parse(startYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        if (hours !=0){
            hours = hours*60;
            min = hours+min;
        }
        /* 2021/01/28 cuikailin MANAMIRU1-393 start */
        StuWeeklyPlanPerfEntity stuWeeklyPlanPerfEntity = new StuWeeklyPlanPerfEntity();
        if (id != 0){
            stuWeeklyPlanPerfEntity = stuWeeklyPlanPerfDao.selectById(id);
        }else {
            //生徒ID
            stuWeeklyPlanPerfEntity.setStuId(ShiroUtils.getUserId());
            //単元ID
            stuWeeklyPlanPerfEntity.setUnitId(0);
            // 生徒タームプラン設定ID
            stuWeeklyPlanPerfEntity.setStuTermPlanId(null);
            //ブロック種類区分
            stuWeeklyPlanPerfEntity.setBlockTypeDiv(block);
            //計画年月日
            stuWeeklyPlanPerfEntity.setPlanYmd(Ymd);
            //教科区分
            stuWeeklyPlanPerfEntity.setSubjtDiv(subjt);
            //教科
            stuWeeklyPlanPerfEntity.setSubjtNm(subjtNm);
            //生徒計画学習時間（分）
            stuWeeklyPlanPerfEntity.setStuPlanLearnTm(min);
            //計画学習時期ID
            stuWeeklyPlanPerfEntity.setPlanLearnSeasnId(null);
            //計画学習開始時間
            stuWeeklyPlanPerfEntity.setPlanLearnStartTime(DateUtils.toTimestamp(starTime));
            //積み残し対象フラグ
            if (StringUtils.equals(codCd, "4") || StringUtils.equals(codCd, "0")) {
                stuWeeklyPlanPerfEntity.setRemainDispFlg("1");
            } else {
                stuWeeklyPlanPerfEntity.setRemainDispFlg("0");
            }
            //生徒削除フラグ
            stuWeeklyPlanPerfEntity.setStuDelFlg("0");
            //表示順番
            stuWeeklyPlanPerfEntity.setDispyOrder(null);
            //作成日時
            stuWeeklyPlanPerfEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            stuWeeklyPlanPerfEntity.setCretUsrId(ShiroUtils.getUserId());
            //削除フラグ
            stuWeeklyPlanPerfEntity.setDelFlg(0);
        }
        //実績年月日
        stuWeeklyPlanPerfEntity.setPerfYmd(Ymd);
        //学習理解度
        stuWeeklyPlanPerfEntity.setLearnLevUnds(codCd);
        if (StringUtils.equals(codCd, "0")){
            //実績学習開始時間
            stuWeeklyPlanPerfEntity.setPerfLearnStartTime(null);
            //実績学習時間（分）
            stuWeeklyPlanPerfEntity.setPerfLearnTm(null);
        }else {
            //実績学習開始時間
            stuWeeklyPlanPerfEntity.setPerfLearnStartTime(DateUtils.toTimestamp(starTime));
            //実績学習時間（分）
            stuWeeklyPlanPerfEntity.setPerfLearnTm(min);
        }
        //ブロック表示名
        stuWeeklyPlanPerfEntity.setBlockDispyNm(memo);
        //タイマー時間（秒）
        stuWeeklyPlanPerfEntity.setTimerTm(null);
        stuWeeklyPlanPerfEntity.setUpdDatime(DateUtils.getSysTimestamp());
        stuWeeklyPlanPerfEntity.setUpdUsrId(ShiroUtils.getUserId());
        try {
            if (id != 0){
                stuWeeklyPlanPerfDao.updateAllColumnById(stuWeeklyPlanPerfEntity);
            }else {
                stuWeeklyPlanPerfDao.insert(stuWeeklyPlanPerfEntity);
            }
        /* 2021/01/28 cuikailin MANAMIRU1-393 end */
        }catch (Exception e){
            e.printStackTrace();
        }
        if (starTime!=null){
            // 2020/12/7 huangxinliang modify start
            //学習実績登録時、実績登録ポイントを取得する。
            CM0005.PointVo pointVo = CM0005.getPointVo(ShiroUtils.getUserEntity().getOrgId());
            CM0005.addPoint(ShiroUtils.getUserId(), ShiroUtils.getUserEntity().getOrgId(), pointVo, 2, ShiroUtils.getUserId(),DateUtils.toTimestamp(starTime));
            //学習実績累計時間によって、累計時間ポイントを取得する。　
            CM0005.addPoint(ShiroUtils.getUserId(), ShiroUtils.getUserEntity().getOrgId(), pointVo, 3, ShiroUtils.getUserId(),DateUtils.getSysTimestamp());
            // 2020/12/7 huangxinliang modify end
        }
        return R.ok();
    }

    /**
     * 削除ボタン押下
     * @param weeklyPlanId
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public R delete(Integer weeklyPlanId){
        try {
            stuWeeklyPlanPerfDao.deleteById(weeklyPlanId);
        }catch (Exception e){
            return R.error("データの削除に失敗しました。");
        }
        return R.ok();
    }
}