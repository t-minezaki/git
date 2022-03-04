/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.PerfStandMstEntity;
import jp.learningpark.modules.common.service.PerfStandMstService;
import jp.learningpark.modules.pop.dto.F21031Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * <p>F21031_生徒学習実績標準値設定画面_.v6.0</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/21 : yang: 新規<br />
 * @version 6.0
 */
@RestController
@RequestMapping(value = "/manager/F21031")
public class F21031Controller {
    @Autowired
    PerfStandMstService perfStandMstService;
    /**
     * @param
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(String orgId,String usrId) {
        F21031Dto f21031Dto = new F21031Dto();
        PerfStandMstEntity perfStandMstEntity = perfStandMstService.getOne(new QueryWrapper<PerfStandMstEntity>().eq("org_id",orgId).eq("usr_id",usrId).eq("del_flg",0));
        if (perfStandMstEntity == null){
            PerfStandMstEntity perfStandMstEntityNew = new PerfStandMstEntity();
            perfStandMstEntityNew.setPerfStandDay1(30);
            perfStandMstEntityNew.setPerfStandDay2(60);
            perfStandMstEntityNew.setPerfStandDay3(90);
            perfStandMstEntityNew.setPerfStandWeek1(210);
            perfStandMstEntityNew.setPerfStandWeek2(420);
            perfStandMstEntityNew.setPerfStandWeek3(630);
            perfStandMstEntityNew.setPerfStandMonth1(900);
            perfStandMstEntityNew.setPerfStandMonth2(1800);
            perfStandMstEntityNew.setPerfStandMonth3(2700);
            perfStandMstEntity = perfStandMstEntityNew;
        }
        return R.ok().put("f21031Dto",getHourAndMin(f21031Dto,perfStandMstEntity));
    }

    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public R submit(PerfStandMstEntity f21031dto) {
        String loginUser = ShiroUtils.getUserId();
        Timestamp time = DateUtils.getSysTimestamp();
        PerfStandMstEntity perfStandMstEntity = new PerfStandMstEntity();
        perfStandMstEntity = perfStandMstService.getOne(new QueryWrapper<PerfStandMstEntity>().eq("org_id",f21031dto.getOrgId()).eq("usr_id",f21031dto.getUsrId()).eq("del_flg",0));
        if (perfStandMstEntity == null){
            f21031dto.setCreatorId(loginUser);
            f21031dto.setCretUsrId(loginUser);
            f21031dto.setCretDatime(time);
            f21031dto.setUpdUsrId(loginUser);
            f21031dto.setUpdDatime(time);
            f21031dto.setDelFlg(0);
            perfStandMstService.save(f21031dto);
        }else {
            perfStandMstEntity.setPerfStandDay1(f21031dto.getPerfStandDay1());
            perfStandMstEntity.setPerfStandDay2(f21031dto.getPerfStandDay2());
            perfStandMstEntity.setPerfStandDay3(f21031dto.getPerfStandDay3());
            perfStandMstEntity.setPerfStandWeek1(f21031dto.getPerfStandWeek1());
            perfStandMstEntity.setPerfStandWeek2(f21031dto.getPerfStandWeek2());
            perfStandMstEntity.setPerfStandWeek3(f21031dto.getPerfStandWeek3());
            perfStandMstEntity.setPerfStandMonth1(f21031dto.getPerfStandMonth1());
            perfStandMstEntity.setPerfStandMonth2(f21031dto.getPerfStandMonth2());
            perfStandMstEntity.setPerfStandMonth3(f21031dto.getPerfStandMonth3());
            perfStandMstEntity.setUpdDatime(time);
            perfStandMstEntity.setUpdUsrId(loginUser);
            perfStandMstService.update(perfStandMstEntity,new QueryWrapper<PerfStandMstEntity>().eq("org_id",f21031dto.getOrgId()).eq("usr_id",f21031dto.getUsrId()).eq("del_flg",0));
        }
        return R.ok();
    }
    private F21031Dto getHourAndMin(F21031Dto dto,PerfStandMstEntity perfStandMstEntity){
        dto.setDayPassH(perfStandMstEntity.getPerfStandDay3()/60);
        dto.setDayPassM(perfStandMstEntity.getPerfStandDay3()%60);
        dto.setDayMidH(perfStandMstEntity.getPerfStandDay2()/60);
        dto.setDayMidM(perfStandMstEntity.getPerfStandDay2()%60);
        dto.setDayUnpassH(perfStandMstEntity.getPerfStandDay1()/60);
        dto.setDayUnpassM(perfStandMstEntity.getPerfStandDay1()%60);


        dto.setWeekPassH(perfStandMstEntity.getPerfStandWeek3()/60);
        dto.setWeekPassM(perfStandMstEntity.getPerfStandWeek3()%60);
        dto.setWeekMidH(perfStandMstEntity.getPerfStandWeek2()/60);
        dto.setWeekMidM(perfStandMstEntity.getPerfStandWeek2()%60);
        dto.setWeekUnpassH(perfStandMstEntity.getPerfStandWeek1()/60);
        dto.setWeekUnpassM(perfStandMstEntity.getPerfStandWeek1()%60);

        dto.setMonthPassH(perfStandMstEntity.getPerfStandMonth3()/60);
        dto.setMonthPassM(perfStandMstEntity.getPerfStandMonth3()%60);
        dto.setMonthMidH(perfStandMstEntity.getPerfStandMonth2()/60);
        dto.setMonthMidM(perfStandMstEntity.getPerfStandMonth2()%60);
        dto.setMonthUnpassH(perfStandMstEntity.getPerfStandMonth1()/60);
        dto.setMonthUnpassM(perfStandMstEntity.getPerfStandMonth1()%60);
        return dto;
    }
}
