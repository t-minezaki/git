/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.StuFixdSchdlEntity;
import jp.learningpark.modules.common.service.MstBlockService;
import jp.learningpark.modules.common.service.StuFixdSchdlService;
import jp.learningpark.modules.common.utils.dto.SchdlDto;
import jp.learningpark.modules.student.dto.F10101Dto;
import jp.learningpark.modules.student.service.F10101Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F10101 固定スケジュール表示・編集画面 Controller</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/12 : gong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/student/F10101")
public class F10101Controller extends AbstractController {

    /**
     * <p>F10101固定スケジュール表示・編集画面 Service</p>
     */
    @Autowired
    private F10101Service f10101Service;

    /**
     * <p>ブロッグマスタ Service</p>
     */
    @Autowired
    private MstBlockService blockMstService;
    /**
     * <p>ブロッグマスタ Service</p>
     */
    @Autowired
    private StuFixdSchdlService stuFixdSchdlService;

    /**
     * <p>初期表示</p>
     *
     * @param currenDate 現在の日付
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R getInfo(String currenDate) {
        //生徒ID
        String stuId = getUserCd();
        //対象週開始日
        Date startDate;
        //対象週終了日
        Date endDate;
        //基準日
        Date date;
        Map<Integer,String> map = new HashMap<>();
        //初期表示

        // 画面情報
        List<F10101Dto> blockMstEntityList = f10101Service.getBlock();
        for (F10101Dto block:blockMstEntityList){
            if (StringUtils.isEmpty(block.getColorId())){
                block.setColorId("white");
                block.setFontColor("black");
            }else {
                Color color = convertToColor(block.getColorId());
                if (color.getRed() + color.getBlue() + color.getGreen() > 650) {
                    block.setFontColor("black");
                } else {
                    block.setFontColor("white");
                }
            }
            map.put(block.getId(),block.getColorId());
        }
        if (currenDate == null || StringUtils.equals("", currenDate)) {
            //システム日付対応週間の月曜日
            startDate = DateUtils.getMonday(DateUtils.getSysDate());
            //システム日付対応週間の日曜日
            endDate = DateUtils.getSunday(DateUtils.getSysDate());
            currenDate = DateUtils.getSysDate().toString();
        }
        //対象週
        else {
            date = DateUtils.parse(currenDate, GakkenConstant.DATE_FORMAT_YYYYMMDD);
            //対象週開始日
            //            startDate = DateUtils.getMonday(date);
            startDate = DateUtils.getMonday(date);

            //対象週終了日
            endDate = DateUtils.getSunday(date);
        }
        List<StuFixdSchdlEntity> stuFixdSchdlEntityList = stuFixdSchdlService.list(
                new QueryWrapper<StuFixdSchdlEntity>().eq("stu_id", ShiroUtils.getUserId()).le("block_start_date",endDate).ge("block_end_date",startDate).eq("del_flg", 0));
        List<SchdlDto> schdlDtoList = new ArrayList<SchdlDto>();
        for (int i = 0; i < stuFixdSchdlEntityList.size(); i++) {
            boolean checkStart = false;
            boolean checkEnd = false;
            String one = stuFixdSchdlEntityList.get(i).getBlockStartTime() + "";
            String two = stuFixdSchdlEntityList.get(i).getBlockEndTime() + "";
            one = one.split(" ")[0];
            two = two.split(" ")[0];
            if (DateUtils.parse(one, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS).compareTo(stuFixdSchdlEntityList.get(i).getBlockStartDate()) > 0) {
                checkStart = true;
            }
            if (DateUtils.parse(two, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS).compareTo(stuFixdSchdlEntityList.get(i).getBlockEndDate()) > 0) {
                checkEnd = true;
            }
            List<SchdlDto> schdlDtos = f10101Service.getSchdlListOfF10101(stuFixdSchdlEntityList.get(i).getId(), stuId, startDate, checkStart, checkEnd);

            schdlDtoList.addAll(schdlDtos);
        }
        return R.ok().put("schdlList", schdlDtoList).put("currentDate", currenDate).put("stuNm", ShiroUtils.getSessionAttribute(GakkenConstant.STU_NM)).put("block", blockMstEntityList).put("map",map);
    }
    public Color convertToColor(String colorStr) {
        colorStr = colorStr.substring(1);
        Color color =  new Color(Integer.parseInt(colorStr, 16)) ;
        return color;
    }
//    /**
//     * <p>固定ブロックエリア情報の取得処理</p>
//     *
//     * @return ブロックエリア情報
//     */
//    @RequestMapping(value = "/getBlockInfo", method = RequestMethod.GET)
//    private R getBlockInfo() {
//        // 画面情報
//        List<F10101Dto> blockMstEntityList = f10101Service.getBlock();
//        for (F10101Dto block:blockMstEntityList){
//            if (StringUtils.isEmpty(block.getColorId())){
//                block.setColorId("white");
//                block.setFontColor("black");
//            }else {
//                block.setFontColor("white");
//            }
//        }
//        return R.ok().put("block", blockMstEntityList);
//    }
}
