/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.service.GuardReadingStsService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstNoticeDeliverService;
import jp.learningpark.modules.common.service.MstNoticeService;
import jp.learningpark.modules.manager.dto.F09020Dto;
import jp.learningpark.modules.manager.service.F09020Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F09020_一斉お知らせ配信(新規作成)（スマホ） Controller</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/03/03 : zpa: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/manager/F09020")
public class F09020Controller {
    @Autowired
    F09020Service f09020Service;
    @Autowired
    MstCodDService mstCodDService;
    @Autowired
    MstNoticeService mstNoticeService;
    @Autowired
    MstNoticeDeliverService mstNoticeDeliverService;
    @Autowired
    GuardReadingStsService guardReadingStsService;
    String s;
    String e;

    private Logger logger = LoggerFactory.getLogger(getClass());

//    MultipartFile[] files;
    @RequestMapping(value = "/init1", method = RequestMethod.GET)
    public R init1(){
        String dayS = DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 60);
        Date date = calendar.getTime();
        Date addDate = date;
        String dayE = DateUtils.format(addDate,GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        List<F09020Dto> cvl = f09020Service.init1();
        return R.ok().put("cvl",cvl).put("dayS",dayS).put("dayE",dayE);
    }
    @RequestMapping(value = "/getfile", method = RequestMethod.POST)
    public R getfile(MultipartFile[] file){
        Map<String, Object> params = new HashMap<>(8);
        params.put("files", file);
//        files = (MultipartFile[]) params.get("files");
        return R.ok();
    }
    @RequestMapping(value = "/init2", method = RequestMethod.GET)
    public R init2(Integer noticeId){
        Date date = null;
        Date date1 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        F09020Dto dtos = f09020Service.init2(noticeId);
        String ps = dtos.getPubPlanStartDt();
        String pe = dtos.getPubPlanEndDt();
        List<F09020Dto> cvl = f09020Service.init1();
        try {
            date = sdf.parse(ps);
            date1 = sdf.parse(pe);
            s = DateUtils.format(date, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
            e = DateUtils.format(date1, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        int count = guardReadingStsService.count(new QueryWrapper<GuardReadingStsEntity>().eq("notice_id", noticeId).eq("del_flg", 0));
        return R.ok().put("dtos", dtos).put("pubPlanStartDt", s).put("pubPlanEndDt", e).put("cvl", cvl).put("count", count);
    }
    @RequestMapping(value = "/go", method = RequestMethod.POST)
    public R go(String f09020dto,MultipartFile file) throws Exception {
        F09020Dto dto = JSON.parseObject(StringUtils.defaultString(f09020dto),new TypeReference<F09020Dto>(){});
        List<String> stuIdlist = (List<String>) JSON.parse(dto.getStuIds());
        dto.setStuList(stuIdlist);
        List<String> orgIdlist = (List<String>) JSON.parse(dto.getOrgIds());
        dto.setStuList(orgIdlist);
        List<String> stulist = (List<String>) JSON.parse(dto.getStu());
        dto.setStuList(stulist);
        if(dto.getNoticeId()!= null){
            MstNoticeEntity mne = mstNoticeService.getOne(new QueryWrapper<MstNoticeEntity>().eq("id",dto.getNoticeId()).eq("del_flg",0));
            dto.setUpdStr(DateUtils.format(mne.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        }
        return f09020Service.go(dto,file);
    }
}
