/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.manager.service.F20008Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>学習状況分析画面（週、月、年）</p >
 *
 * @author NWT : huangyong <br />
 * 変更履歴 <br />
 * 2018/11/13 : huangyong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager")
public class F20008Controller extends AbstractController {
    @Autowired
    F20008Service f20008Service;

    /**
     * mstCodDService
     */
    @Autowired
    MstCodDDao mstCodDDao;
    /**
     * 学習状況分析
     *
     * @param type    日、週、月
     * @param subjtDiv 教科選択
     * @return 画面情報
     */
    @RequestMapping(value = "/F20008", method = RequestMethod.GET)
    public R index(String type, String subjtDiv,String startDate,String endDate) {
        // 生徒ID
        String stuId = ShiroUtils.getSessionAttribute("stuId").toString();
        //塾Id
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 開始日
        Date startYmd;
        // 終了日
        Date endYmd;
        Date sysDate = DateUtils.getSysDate();

        List<MstCodDEntity> colors = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_value_2", "cod_cd").eq("cod_key", "LEARN_LEV_UNDS").eq("del_flg", 0).orderByDesc("sort"));

        if(startDate!=null&&endDate!=null&& !StringUtils.equals("",startDate)&&!StringUtils.equals("",endDate)){
            startYmd=DateUtils.parse(startDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
            endYmd=DateUtils.parse(endDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        }else{
            switch (type) {
                case "week":
                    startYmd = DateUtils.getMonday(sysDate);
                    endYmd = DateUtils.getSunday(sysDate);
                    break;
                case "month":
                    startYmd = DateUtils.getMonthFirstDay(sysDate);
                    endYmd = DateUtils.getMonthEndDay(sysDate);
                    break;
                case "day":
                    startYmd = DateUtils.parse(DateUtils.format(sysDate, Constant.DATE_FORMAT_YYYY_MM_DD_BARS), Constant.DATE_FORMAT_YYYY_MM_DD_BARS);
                    endYmd = startYmd;
                    break;
                default:
                    startYmd = DateUtils.parse(DateUtils.format(sysDate, Constant.DATE_FORMAT_YYYY_MM_DD_BARS), Constant.DATE_FORMAT_YYYY_MM_DD_BARS);
                    endYmd = startYmd;
            }
        }



        Map<String, Object> condition = new HashMap<>();
        // 生徒ID
        condition.put("stuId", stuId);
        // 開始日
        condition.put("startYmd", startYmd);
        // 終了日
        condition.put("endYmd", endYmd);
        //教科選択
        condition.put("subjtDiv",subjtDiv);
        //塾ＩＤ
        condition.put("orgId",orgId);

        Map<String, Object> condition1 = new HashMap<>();
        // 生徒ID
        condition1.put("stuId", stuId);
        // 開始日
        condition1.put("startYmd", startYmd);
        // 終了日
        condition1.put("endYmd", endYmd);
        //

        //教科リストを取得 and 理解度_棒グラフ
        List<Map<String, Object>> degree = f20008Service.getDegreeTotal(condition1);

        // 学習時間_円グラフ
        Map<String, Object> timeCircle = f20008Service.getLearnTimeCircleTotal(condition);

        // 学習時間_横軸グラフ
        Map<String, Object> timeHorizontal = f20008Service.getLearnTimeHorizontalTotal(condition);

        //メンター名
        String mentorNm = (String) ShiroUtils.getSessionAttribute(GakkenConstant.MENTOR_NM);
        //生徒名
        String stuNm = (String) ShiroUtils.getSessionAttribute(GakkenConstant.STU_NM);

        R r=R.ok();
        r.put("colors", colors);
        r.put("degree", degree).put("timeCircle", timeCircle).put("timeHorizontal", timeHorizontal).put("stuId", stuId);
        if(startDate!=null&&endDate!=null){
            r.put("startDate",startDate).put("endDate",endDate);
        }else {
            switch (type) {
                case "day":
                    endYmd = DateUtils.addDays(startYmd, -1);
                    startYmd = endYmd;
                    break;
                case "week":
                    endYmd = DateUtils.addDays(startYmd, -1);
                    startYmd = DateUtils.getMonday(endYmd);
                    break;
                case "month":
                    endYmd = DateUtils.addDays(startYmd, -1);
                    startYmd = DateUtils.getMonthFirstDay(endYmd);
                    break;
                default:
                    endYmd = DateUtils.addDays(startYmd, -1);
                    startYmd = endYmd;
            }
            // 開始日
            condition.put("startYmd", startYmd);
            // 終了日
            condition.put("endYmd", endYmd);

            Map<String, Object> timeCircle2 = f20008Service.getLearnTimeCircleTotal(condition);
            r.put("timeCircle2", timeCircle2);
        }
        r.put("mentorNm", mentorNm);
        r.put("stuNm", stuNm);
        return r;
    }
}
