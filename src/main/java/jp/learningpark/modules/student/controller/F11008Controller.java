package jp.learningpark.modules.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.EntryExitHstEntity;
import jp.learningpark.modules.common.service.EntryExitHstService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.StuComplimentMstService;
import jp.learningpark.modules.student.dto.F11008Dto;
import jp.learningpark.modules.student.service.F11008Service;
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
import java.util.List;

/**
 * <p>スマホ_スケジュール Controller</p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/04/23 ： NWT)hxl ： 新規作成
 */
@RestController
@RequestMapping("/student/F11008")
public class F11008Controller extends AbstractController {
    /**
     * 学習者の進捗一覧画面（デイリー）
     */
    @Autowired
    F11008Service f11008Service;
    /**
     *
     */
    @Autowired
    EntryExitHstService entryExitHstService;
    /**
     *
     */
    @Autowired
    MstCodDService mstCodDService;
    /**
     * 褒めポイント管理
     */
    @Autowired
    StuComplimentMstService stuComplimentMstService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f11008init(String tgtYmdStr, String dataFlg, String url) {
        //生徒ID
        String stuId = getUserCd();
        //対象日
        Date tgtYmd = DateUtils.parse(tgtYmdStr, GakkenConstant.DATE_FORMAT_YYYYMMDD);

        //対象日初期値はシステム日付
        if (tgtYmd == null) {
            //システム日付取得
            tgtYmd = DateUtils.getSysDate();
        }

        // 2020/12/08 NWT文　対象日付より、一ヶ月のデータを取得する。↓↓↓ start
        Date monthFirstDay = DateUtils.getMonthFirstDay(tgtYmd);
        Date monthEndDay = DateUtils.getMonthEndDay(tgtYmd);
        //生徒ウィークリー計画実績設定リスト取得
        List<F11008Dto> planPerfList = f11008Service.getPlanBlockList(stuId, monthFirstDay);
        // end ↑↑↑

        R info = new R();
        info.put("planPerfList", planPerfList);
        info.put("tgtYmd", tgtYmd);
        //ビッグデータ
        if (dataFlg != null && StringUtils.equals("0", dataFlg)) {
            //初期化
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
                e.printStackTrace();
            }
        } else {
            //「日」の切替時
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
                XApiUtils.saveStatement(Verbs.started(), Activitys.schedule(), exts);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //生徒固定ブロック情報取得  2020/12/08 NWT文　対象日付より、一ヶ月のデータを取得する。↓↓↓ start
        List<F11008Dto> schdlBlockList = f11008Service.getSchdlBlockList(stuId, tgtYmd, monthFirstDay);
        info.put("schdlBlockList", schdlBlockList);
        // 学生の通塾時間を取得
        List<EntryExitHstEntity> entryExitHstEntityList = entryExitHstService.list(new QueryWrapper<EntryExitHstEntity>().select("entry_dt", "entry_flg").and(
                w->w.eq("stu_id", stuId).gt("entry_dt", monthFirstDay).lt("entry_dt", monthEndDay).eq("del_flg", 0)).orderByAsc("entry_dt"));
        // end ↑↑↑

        info.put("entryExitHstList", entryExitHstEntityList);
        return info;
    }
}
