/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.guard.service.F30104Service;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>学習状況分析画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/27 : hujunjie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/guard")
public class F30104Controller extends AbstractController {
    @Autowired
    F30104Service f30104Service;

    /**
     * mstCodDService
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * 学習状況分析
     *
     * @param type 週、月、年
     * @param subjtDiv 教科区分
     * @param dataFlg 状態フラグ
     * @param url 当画面URL
     * @return 画面情報
     */
    @RequestMapping(value = "/F30104", method = RequestMethod.GET)
    public R index(String type, String subjtDiv, String startDate, String endDate, String dataFlg, String url) {
        String stuId;
        try{
        // 生徒ID
        stuId = ShiroUtils.getSessionAttribute("stuId").toString();
        }catch (Exception e){
            logger.error(e.getMessage());
            stuId = "";
        }
        //塾Id
        String orgId = (String)ShiroUtils.getSessionAttribute("orgId");
        // 開始日
        Date startYmd;
        // 終了日
        Date endYmd;
        Date sysDate = DateUtils.getSysDate();

        List<MstCodDEntity> colors = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_value_2", "cod_cd").eq("cod_key", "LEARN_LEV_UNDS").eq("del_flg", 0).orderByDesc("sort"));

        if (startDate != null && endDate != null) {
            startYmd = DateUtils.parse(startDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
            endYmd = DateUtils.parse(endDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        } else {
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
        condition.put("subjtDiv", subjtDiv);
        //塾ＩＤ
        condition.put("orgId", orgId);

        Map<String, Object> condition1 = new HashMap<>();
        // 生徒ID
        condition1.put("stuId", stuId);
        // 開始日
        condition1.put("startYmd", startYmd);
        // 終了日
        condition1.put("endYmd", endYmd);

        //教科リストを取得 and 理解度_棒グラフ
        List<Map<String, Object>> degree = f30104Service.getDegreeTotal(condition1);

        // 学習時間_円グラフ
        Map<String, Object> timeCircle = f30104Service.getLearnTimeCircleTotal(condition);

        // 学習時間_横軸グラフ
        Map<String, Object> timeHorizontal = f30104Service.getLearnTimeHorizontalTotal(condition);

        R r = R.ok();
        r.put("colors", colors);
        r.put("degree", degree).put("timeCircle", timeCircle).put("timeHorizontal", timeHorizontal);
        if (startDate != null && endDate != null) {
            r.put("startDate", startDate).put("endDate", endDate);
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

            Map<String, Object> timeCircle2 = f30104Service.getLearnTimeCircleTotal(condition);
            r.put("timeCircle2", timeCircle2);
        }
        if (StringUtils.equals("0", dataFlg)) {
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
        return r;
    }
}
