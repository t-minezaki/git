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
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.StuWeeklyPlanPerfService;
import jp.learningpark.modules.manager.dto.F20009Dto;
import jp.learningpark.modules.manager.service.F20009Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>F20009 理解度詳細画面（PC）</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/11/22 : wen: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F20009/")
public class F20009Controller extends AbstractController {

    /**
     * 生徒ウィークリー計画実績設定　Serivice
     */
    @Autowired
    StuWeeklyPlanPerfService weeklyPlanPerfService;
    /**
     * コードマスタ_明細　Serivice
     */
    @Autowired
    MstCodDService codMstDService;

    /**
     * F20009 service
     */
    @Autowired
    F20009Service f20009Service;

    /**
     * @param subjtDiv     教科区分
     * @param flag         週/月/年
     * @param getStartDate 指定開始日
     * @param getEndDate   指定終了日
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R init(String subjtDiv, String flag, String getStartDate, String getEndDate) throws UnsupportedEncodingException {
        Calendar calendar = Calendar.getInstance();
        Date sysDate = DateUtils.getSysDate();
        //教科 障害24
        String decodeSubjtNm = URLDecoder.decode(subjtDiv,"UTF-8");

        //週開始日
        Date startWeekDay = DateUtils.getMonday(sysDate);
        //週終了日
        Date endWeekDay = DateUtils.getSunday(sysDate);

        //月開始日
        Date monthStartDay = DateUtils.getMonthFirstDay(sysDate);
        //月終了日
        Date monthEndDay = DateUtils.getMonthEndDay(sysDate);

        //年開始日
        Date yearStartDay = DateUtils.parse(calendar.get(Calendar.YEAR) + "-" + "01-01", GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        //年終了日
        Date yearEndDay = DateUtils.parse(calendar.get(Calendar.YEAR) + "-" + "12-31", GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);

        //セッションデータ．生徒IDを取得する
        String stuId = ShiroUtils.getSessionAttribute("stuId").toString();
        //メンター名
        String mentorNm = (String) ShiroUtils.getSessionAttribute(GakkenConstant.MENTOR_NM);
        //生徒名
        String stuNm = (String) ShiroUtils.getSessionAttribute(GakkenConstant.STU_NM);
        List<F20009Dto> f20009DtoList = new ArrayList<>();

        //教科を取得する
        String subjtNm = decodeSubjtNm;

        Date setStartDate = DateUtils.parse(getStartDate, GakkenConstant.DATE_FORMAT_YYYYMD);
        Date setEndDate = DateUtils.parse(getEndDate, GakkenConstant.DATE_FORMAT_YYYYMD);
        if (getStartDate != null && getEndDate != null&& !StringUtils.equals("null",getStartDate)&&!StringUtils.equals("null",getEndDate)&&!StringUtils.equals("undefined",getStartDate)) {
            f20009DtoList = f20009Service.getValueByCodcd(stuId, decodeSubjtNm, setStartDate, setEndDate);
        } else {
            if (GakkenConstant.DATE_TYPE.WEEK.getLable().equals(flag)) {
                //週
                f20009DtoList = f20009Service.getValueByCodcd(stuId, decodeSubjtNm, startWeekDay, endWeekDay);
            }

            if (GakkenConstant.DATE_TYPE.MONTH.getLable().equals(flag)) {
                //月
                f20009DtoList = f20009Service.getValueByCodcd(stuId, decodeSubjtNm, monthStartDay, monthEndDay);
            }

            if (GakkenConstant.DATE_TYPE.YEAR.getLable().equals(flag)) {
                //年
                f20009DtoList = f20009Service.getValueByCodcd(stuId, decodeSubjtNm, yearStartDay, yearEndDay);
            }
        }
        MstCodDEntity mcd=codMstDService.getOne(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(wrapper -> wrapper.eq("cod_key", "SUBJT_DIV").eq("del_flg", 0).eq("cod_cd",decodeSubjtNm)).orderByAsc("sort"));
        subjtNm=mcd.getCodValue();
        return R.ok().put("subjtNm", subjtNm).put("f20009DtoList", f20009DtoList).put("mentorNm", mentorNm).put("stuNm", stuNm);
    }
}
