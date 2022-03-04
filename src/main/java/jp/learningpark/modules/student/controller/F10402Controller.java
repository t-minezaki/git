/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.StuWeeklyPlanPerfService;
import jp.learningpark.modules.student.dto.F10402Dto;
import jp.learningpark.modules.student.service.F10402Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import jp.learningpark.modules.xapi.Activitys;
import jp.learningpark.modules.xapi.Extensions;
import jp.learningpark.modules.xapi.Verbs;
import jp.learningpark.modules.xapi.XApiConstant;
import jp.learningpark.modules.xapi.XApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>F10402 理解度詳細画面（IPAD）Controller。</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/11/16 : wen: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/student/F10402")
public class F10402Controller extends AbstractController {

    /**
     * 生徒ウィークリー計画実績設定　Serivice
     */
    @Autowired
    StuWeeklyPlanPerfService stuWeeklyPlanPerfService;

    /**
     * コードマスタ_明細　Serivice
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     * F10402 service
     */
    @Autowired
    private F10402Service f10402Service;

    /**
     * 理解度詳細画面（IPAD）
     *
     * @param init         0:非初期,1:初期
     * @param name     教科区分
     * @param flag         週/月/年
     * @param getStartDate 指定開始日
     * @param getEndDate   指定終了日
     * @param url          当画面URL
     * @return
     */

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer init,String subjtDiv, String flag, String getStartDate, String getEndDate,String url) throws UnsupportedEncodingException {
        //生徒ID
        String stuId = getUserCd();

        Date sysDate = DateUtils.parse(DateUtils.format(DateUtils.getSysDate(), Constant.DATE_FORMAT_YYYY_MM_DD_BARS), Constant.DATE_FORMAT_YYYY_MM_DD_BARS);

        //週開始日
        Date startWeekDay = DateUtils.getMonday(sysDate);
        //週終了日
        Date endWeekDay = DateUtils.getSunday(sysDate);

        //月開始日
        //String monthStart = calendar.get(Calendar.MONTH) + 1 + "-" + "01";
        Date monthStartDay = DateUtils.getMonthFirstDay(sysDate);
        //月終了日
        Date monthEndDay = DateUtils.getMonthEndDay(sysDate);

        //年開始日
//        Date yearStartDay = DateUtils.getYearFirstDay(sysDate);
        //年終了日
//        Date yearEndDay = DateUtils.getYearEndDay(sysDate);
        //Date yearEndDay = DateUtils.parse(calendar.get(Calendar.YEAR) + "-" + "12-31", GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);

        //セッションデータ．生徒IDを取得する
        List<F10402Dto> f10402DtoList = new ArrayList<>();

        //教科を取得する
        MstCodDEntity codMstDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().select("cod_value").and(wrapper -> wrapper.eq("cod_cd", subjtDiv)).eq("del_flg", 0));
        String subjtNm = codMstDEntity.getCodValue();

        Date setStartDate = DateUtils.parse(getStartDate, GakkenConstant.DATE_FORMAT_YYYYMD);
        Date setEndDate = DateUtils.parse(getEndDate, GakkenConstant.DATE_FORMAT_YYYYMD);
        if (getStartDate != null && getEndDate != null && !StringUtils.equals("null", getStartDate) && !StringUtils.equals("null", getEndDate) && !StringUtils.equals("undefined", getStartDate) && !StringUtils.equals("undefined", getEndDate)) {
            f10402DtoList = f10402Service.getValueByCodcd(stuId, subjtDiv, setStartDate, setEndDate);
        } else {
            if (GakkenConstant.DATE_TYPE.WEEK.getLable().equals(flag)) {

                //週
                f10402DtoList = f10402Service.getValueByCodcd(stuId, subjtDiv, startWeekDay, endWeekDay);
            }

            if (GakkenConstant.DATE_TYPE.MONTH.getLable().equals(flag)) {

                //月
                f10402DtoList = f10402Service.getValueByCodcd(stuId, subjtDiv, monthStartDay, monthEndDay);
            }

            if ("day".equals(flag)) {

                //年
                f10402DtoList = f10402Service.getValueByCodcd(stuId, subjtDiv, sysDate, sysDate);
            }
        }

        //ビッグデータ
        if(init==0){
            Extensions extensions = new Extensions();
            //利用者のシステムID
            extensions.put(XApiConstant.EXT_KEY_USER_ID,ShiroUtils.getUserId());
            //当画面URL
            extensions.put(XApiConstant.EXT_KEY_URL,url);
            //当画面訪問時間
            extensions.put(XApiConstant.EXT_KEY_VISIT_TIME,DateUtils.format(DateUtils.getSysTimestamp(),GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
            try{
                XApiUtils.saveStatement(Verbs.launched(), Activitys.schedule(),extensions);
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
        return R.ok().put("subjtNm", subjtNm).put("f10402DtoList", f10402DtoList).put("stuNm", ShiroUtils.getSessionAttribute(GakkenConstant.STU_NM));
    }

}
