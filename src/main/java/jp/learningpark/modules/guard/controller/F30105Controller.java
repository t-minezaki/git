/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.StuWeeklyPlanPerfService;
import jp.learningpark.modules.guard.dto.F30105Dto;
import jp.learningpark.modules.guard.service.F30105Service;
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
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>F30105 理解度詳細画面</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/11/23 : wen: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/guard/F30105")
public class F30105Controller extends AbstractController {

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
     * F30105 service
     */
    @Autowired
    F30105Service f30105Service;

    /**
     * @param subjtDiv 教科区分
     * @param flag 週/月/年
     * @param getStartDate 指定開始日
     * @param getEndDate 指定終了日
     * @param dataFlg 状態フラグ
     * @param url 当画面URL
     * @return
     */

    @RequestMapping(value = "/Init", method = RequestMethod.GET)
    public R init(String subjtDiv,String subjtNm, String flag, String getStartDate, String getEndDate, String dataFlg,String url) throws UnsupportedEncodingException {
        //現在の時間を取得する
        Calendar calendar = Calendar.getInstance();
        Date sysDate = DateUtils.getSysDate();
        Date tgtYmd = DateUtils.parse(DateUtils.format(sysDate, Constant.DATE_FORMAT_YYYY_MM_DD_BARS), Constant.DATE_FORMAT_YYYY_MM_DD_BARS);
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
//        Date yearStartDay = DateUtils.parse(calendar.get(Calendar.YEAR) + "-" + "01-01", GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        //年終了日
//        Date yearEndDay = DateUtils.parse(calendar.get(Calendar.YEAR) + "-" + "12-31", GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);

        //セッションデータ．生徒IDを取得する
        String stuId = ShiroUtils.getSessionAttribute("stuId").toString();
        List<F30105Dto> f30105DtoList = new ArrayList<>();

        //教科を取得する
//        MstCodDEntity codMstDEntity = codMstDService.getOne(new QueryWrapper<MstCodDEntity>().select("cod_value").and(wrapper -> wrapper.eq("cod_cd", subjtDiv)).eq("del_flg", 0));
//        String subjtNm = codMstDEntity.getCodValue();
        subjtDiv = URLDecoder.decode(subjtDiv,"UTF-8");
        subjtNm = URLDecoder.decode(subjtNm,"UTF-8");
        Date setStartDate = DateUtils.parse(getStartDate, GakkenConstant.DATE_FORMAT_YYYYMD);
        Date setEndDate = DateUtils.parse(getEndDate, GakkenConstant.DATE_FORMAT_YYYYMD);
        if (getStartDate != null && getEndDate != null && !StringUtils.equals("null", getStartDate) && !StringUtils.equals("null", getEndDate) && !StringUtils.equals("", getStartDate) && !StringUtils.equals("", getEndDate)) {
            f30105DtoList = f30105Service.getValueByCodcd(stuId, subjtDiv, setStartDate, setEndDate);
        } else {
            if (GakkenConstant.DATE_TYPE.WEEK.getLable().equals(flag)) {
                //週
                f30105DtoList = f30105Service.getValueByCodcd(stuId, subjtDiv, startWeekDay, endWeekDay);
            }

            if (GakkenConstant.DATE_TYPE.MONTH.getLable().equals(flag)) {
                //月
                f30105DtoList = f30105Service.getValueByCodcd(stuId, subjtDiv, monthStartDay, monthEndDay);
            }

            if (StringUtils.equals("day",flag)) {
                //年
                f30105DtoList = f30105Service.getValueByCodcd(stuId, subjtDiv, tgtYmd, tgtYmd);
            }
        }
        if (StringUtils.equals("0",dataFlg)){
            //ビッグデータ
            Extensions exts = new Extensions();
            //利用者のシステムID
            exts.put(XApiConstant.EXT_KEY_USER_ID, ShiroUtils.getUserId());
            //当画面URL
            exts.put(XApiConstant.EXT_KEY_URL, url);
            //当画面訪問時間
            exts.put(XApiConstant.EXT_KEY_VISIT_TIME, DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
            //生徒ＩＤ
            exts.put(XApiConstant.EXT_KEY_STU_ID, stuId);
            try {
                XApiUtils.saveStatement(Verbs.launched(), Activitys.schedule(), exts);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return R.ok().put("subjtNm", subjtNm).put("f30105DtoList", f30105DtoList);
    }
}
