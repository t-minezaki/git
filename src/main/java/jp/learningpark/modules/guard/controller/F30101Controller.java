/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.EntryExitHstEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.EntryExitHstService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.StuComplimentMstService;
import jp.learningpark.modules.guard.dto.F30101Dto;
import jp.learningpark.modules.guard.dto.F30101stuConplimentDto;
import jp.learningpark.modules.guard.service.F30101Service;
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
 * <p>学習者の進捗一覧画面（デイリー）</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/21 : hujunjie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("guard/F30101")
public class F30101Controller extends AbstractController {
    /**
     * 学習者の進捗一覧画面（デイリー）
     */
    @Autowired
    F30101Service f30101Service;
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
    public R f30101init(String tgtYmdStr, String dataFlg, String url) {
        //生徒ID
        String stuId = ShiroUtils.getSessionAttribute("stuId").toString();
        //対象日
        Date tgtYmd = DateUtils.parse(tgtYmdStr, GakkenConstant.DATE_FORMAT_YYYYMMDD);
        //対象日初期値はシステム日付
        if (tgtYmd == null) {
            //システム日付取得
            tgtYmd = DateUtils.getSysDate();
        }
        Date monthFirstDay = DateUtils.getMonthFirstDay(tgtYmd);
        Date monthEndDay = DateUtils.getMonthEndDay(tgtYmd);
        //生徒ウィークリー計画実績設定リスト取得
        List<F30101Dto> planPerfList = f30101Service.getPlanBlockList(stuId, monthFirstDay);

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
                logger.error(e.getMessage());
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
                logger.error(e.getMessage());
            }
        }
        //生徒固定ブロック情報取得
        List<F30101Dto> schdlBlockList = f30101Service.getSchdlBlockList(stuId, monthFirstDay);
        info.put("schdlBlockList", schdlBlockList);
        // 学生の通塾時間を取得
        List<EntryExitHstEntity> entryExitHstEntityList = entryExitHstService.list(new QueryWrapper<EntryExitHstEntity>().select("entry_dt", "entry_flg").and(
                w->w.eq("stu_id", stuId).gt("entry_dt", monthFirstDay).lt("entry_dt", monthEndDay).eq("del_flg", 0)).orderByAsc("entry_dt"));
        //セッション・生徒組織Id
        String orgId = (String)ShiroUtils.getSessionAttribute("orgId");
        //生徒褒めスタンプと褒めコメントを取得
        List<F30101stuConplimentDto> stuComplimentMstEntityList = f30101Service.getStuComplimentList(stuId, orgId, monthFirstDay);
        info.put("entryExitHstList", entryExitHstEntityList).put("stuComplimentMstEntityList", stuComplimentMstEntityList);
        return info;
    }

    @RequestMapping(value = "/getImg", method = RequestMethod.GET)
    public R getImg(String complimentCont) {
        MstCodDEntity mstCodDEntityList = mstCodDService.getOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "PRAISE_STAMP_LIST").eq("cod_cd", complimentCont).eq("del_flg", 0));
        return R.ok().put("codValue", mstCodDEntityList.getCodValue());
    }
}