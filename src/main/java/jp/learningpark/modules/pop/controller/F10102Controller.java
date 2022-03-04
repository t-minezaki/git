/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.common.entity.StuFixdSchdlEntity;
import jp.learningpark.modules.common.entity.StuIndivSchdlAdjustEntity;
import jp.learningpark.modules.common.service.MstBlockService;
import jp.learningpark.modules.common.service.StuFixdSchdlService;
import jp.learningpark.modules.common.service.StuIndivSchdlAdjustService;
import jp.learningpark.modules.common.service.StuWeeklyPlanPerfService;
import jp.learningpark.modules.pop.form.F10102Form;
import jp.learningpark.modules.pop.service.F10102Service;
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

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p>F10102 固定活動登録画面(POP) Controller。</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/11/13 : wen: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/pop/F10102")
public class F10102Controller extends AbstractController {

    /**
     * 生徒固定スケジュール設定
     */
    @Autowired
    private StuFixdSchdlService stuFixdSchdlService;

    /**
     * 生徒個別スケジュール調整
     */
    @Autowired
    private StuIndivSchdlAdjustService stuIndivSchdlAdjustService;

    /**
     * ブロックマスタ　Service
     */
    @Autowired
    private MstBlockService mstBlockService;

    /**
     * 生徒ウィークリー計画実績設定 Service
     */
    @Autowired
    private StuWeeklyPlanPerfService stuWeeklyPlanPerfService;

    /**
     * F10102Service Service
     */
    @Autowired
    private F10102Service f10102Service;

    /**
     * 　初期表示
     *
     * @param key 操作キー（1：登録/2：更新）
     * @param id ブロックＩＤ/生徒固定スケジュール設定のＩＤ
     * @param singleOrAll 個別全体選択フラグ
     * @param start 計画年月日
     * @return
     */
    @RequestMapping(value = "/Init", method = RequestMethod.GET)
    public R init(String key, Integer id, String singleOrAll, String start) {

        //singleOrAll = "1";
        //ブロックマスタ情報
        MstBlockEntity blockMstEntity;

        //スケジュール情報
        StuFixdSchdlEntity fixdSchdlEntity;

        //個別スケジュールデータ
        StuIndivSchdlAdjustEntity stuIndivSchdlAdjustEntityGet;

        //時間を取得する
        String startDateTime;
        String endDateTime;
        String shortStartDateTime;
        String shortEndDateTime;

        //更新の時間を取得する
        String updDatime;

        //日付を取得する
        String startDateString;
        String endDateString;

        String stuId = ShiroUtils.getUserId();

        //最後計画日翌日を取得する
        Date maxWeeklyPlanYmdAfterAll = DateUtils.parse(start, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS);
        String maxWeeklyPlanYmdAfter = DateUtils.format(maxWeeklyPlanYmdAfterAll, GakkenConstant.DATE_FORMAT_YYYYMD);

        //キーが登録の場合
        if (StringUtils.equals(key, "1")) {
            blockMstEntity = mstBlockService.getById(id);
            return R.ok().put("f10102Form", blockMstEntity).put("maxWeeklyPlanYmdAfter", maxWeeklyPlanYmdAfter);
        } else {
            //キーが更新の場合
            fixdSchdlEntity = stuFixdSchdlService.getById(id);
            blockMstEntity = mstBlockService.getById(fixdSchdlEntity.getBlockId());
            stuIndivSchdlAdjustEntityGet = stuIndivSchdlAdjustService.getOne(new QueryWrapper<StuIndivSchdlAdjustEntity>().and(
                    wrapper->wrapper.eq("block_id", fixdSchdlEntity.getBlockId()).eq("stu_id", stuId).eq("plan_ymd",
                            DateUtils.parse(start, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS)).eq("del_flg", 0).eq("stu_fixd_schdl_id", id)));

            //日付を取得する
            startDateString = DateUtils.format(fixdSchdlEntity.getBlockStartDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
            endDateString = DateUtils.format(fixdSchdlEntity.getBlockEndDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);

            //計画年月日
            String planDate2 = "";
            if (StringUtils.equals(singleOrAll, "0")) {
                planDate2 = DateUtils.format(start, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
            }

            //更新の時間を取得する
            updDatime = DateUtils.format(fixdSchdlEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            F10102Form f10102Form = new F10102Form();
            if (StringUtils.equals(singleOrAll, "1")) {//「全体」の場合、
                startDateTime = DateUtils.format(fixdSchdlEntity.getBlockStartTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
                endDateTime = DateUtils.format(fixdSchdlEntity.getBlockEndTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
                shortStartDateTime = DateUtils.format(fixdSchdlEntity.getBlockStartTime(), GakkenConstant.DATE_FORMAT_HH_MM);
                shortEndDateTime = DateUtils.format(fixdSchdlEntity.getBlockEndTime(), GakkenConstant.DATE_FORMAT_HH_MM);
                //プロパティを設定する
                f10102Form.setBlockId(blockMstEntity.getId());
                f10102Form.setBlockPicDiv(blockMstEntity.getBlockPicDiv());
                f10102Form.setBlockDispyNm(blockMstEntity.getBlockDispyNm());
                f10102Form.setBlockStartDate(startDateString);
                f10102Form.setBlockEndDate(endDateString);
                f10102Form.setBlockStartTime(startDateTime);
                f10102Form.setBlockEndTime(endDateTime);
                f10102Form.setMoDwChocFlg(fixdSchdlEntity.getMoDwChocFlg());
                f10102Form.setTuDwChocFlg(fixdSchdlEntity.getTuDwChocFlg());
                f10102Form.setWeDwChocFlg(fixdSchdlEntity.getWeDwChocFlg());
                f10102Form.setThDwChocFlg(fixdSchdlEntity.getThDwChocFlg());
                f10102Form.setFrDwChocFlg(fixdSchdlEntity.getFrDwChocFlg());
                f10102Form.setSaDwChocFlg(fixdSchdlEntity.getSaDwChocFlg());
                f10102Form.setSuDwChocFlg(fixdSchdlEntity.getSuDwChocFlg());
                f10102Form.setUpdateTime(updDatime);
            } else {//「個別」の場合、
                if (stuIndivSchdlAdjustEntityGet == null) {
                    startDateTime = DateUtils.format(fixdSchdlEntity.getBlockStartTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
                    endDateTime = DateUtils.format(fixdSchdlEntity.getBlockEndTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
                    shortStartDateTime = DateUtils.format(fixdSchdlEntity.getBlockStartTime(), GakkenConstant.DATE_FORMAT_HH_MM);
                    shortEndDateTime = DateUtils.format(fixdSchdlEntity.getBlockEndTime(), GakkenConstant.DATE_FORMAT_HH_MM);
                    //プロパティを設定する
                    f10102Form.setBlockId(blockMstEntity.getId());
                    f10102Form.setBlockPicDiv(blockMstEntity.getBlockPicDiv());
                    f10102Form.setBlockDispyNm(blockMstEntity.getBlockDispyNm());
                    f10102Form.setBlockStartDate(planDate2);
                    f10102Form.setBlockEndDate(planDate2);
                    f10102Form.setBlockStartTime(startDateTime);
                    f10102Form.setBlockEndTime(endDateTime);
                    f10102Form.setMoDwChocFlg(fixdSchdlEntity.getMoDwChocFlg());
                    f10102Form.setTuDwChocFlg(fixdSchdlEntity.getTuDwChocFlg());
                    f10102Form.setWeDwChocFlg(fixdSchdlEntity.getWeDwChocFlg());
                    f10102Form.setThDwChocFlg(fixdSchdlEntity.getThDwChocFlg());
                    f10102Form.setFrDwChocFlg(fixdSchdlEntity.getFrDwChocFlg());
                    f10102Form.setSaDwChocFlg(fixdSchdlEntity.getSaDwChocFlg());
                    f10102Form.setSuDwChocFlg(fixdSchdlEntity.getSuDwChocFlg());
                    f10102Form.setUpdateTime(updDatime);
                } else {
                    startDateTime = DateUtils.format(stuIndivSchdlAdjustEntityGet.getBlockStartTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
                    endDateTime = DateUtils.format(stuIndivSchdlAdjustEntityGet.getBlockEndTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
                    shortStartDateTime = DateUtils.format(stuIndivSchdlAdjustEntityGet.getBlockStartTime(), GakkenConstant.DATE_FORMAT_HH_MM);
                    shortEndDateTime = DateUtils.format(stuIndivSchdlAdjustEntityGet.getBlockEndTime(), GakkenConstant.DATE_FORMAT_HH_MM);
                    //プロパティを設定する
                    f10102Form.setBlockId(blockMstEntity.getId());
                    f10102Form.setBlockPicDiv(blockMstEntity.getBlockPicDiv());
                    f10102Form.setBlockDispyNm(blockMstEntity.getBlockDispyNm());
                    f10102Form.setBlockStartDate(planDate2);
                    f10102Form.setBlockEndDate(planDate2);
                    f10102Form.setBlockStartTime(startDateTime);
                    f10102Form.setBlockEndTime(endDateTime);
                    f10102Form.setMoDwChocFlg(fixdSchdlEntity.getMoDwChocFlg());
                    f10102Form.setTuDwChocFlg(fixdSchdlEntity.getTuDwChocFlg());
                    f10102Form.setWeDwChocFlg(fixdSchdlEntity.getWeDwChocFlg());
                    f10102Form.setThDwChocFlg(fixdSchdlEntity.getThDwChocFlg());
                    f10102Form.setFrDwChocFlg(fixdSchdlEntity.getFrDwChocFlg());
                    f10102Form.setSaDwChocFlg(fixdSchdlEntity.getSaDwChocFlg());
                    f10102Form.setSuDwChocFlg(fixdSchdlEntity.getSuDwChocFlg());
                    f10102Form.setUpdateTime(updDatime);
                }
            }

            return R.ok().put("f10102Form", f10102Form).put("startDateTime", startDateTime).put("endDateTime", endDateTime).put(
                    "shortStartDateTime", shortStartDateTime).put("shortEndDateTime", shortEndDateTime).put(
                    "startDateTime", fixdSchdlEntity.getBlockStartTime()).put(
                    "endDateTime", fixdSchdlEntity.getBlockEndTime()).put("maxWeeklyPlanYmdAfter", maxWeeklyPlanYmdAfter);
        }
    }

    /**
     * 登録ボタン押下の場合、
     *
     * @param key 操作キー（1：登録/2：更新）
     * @param id ブロックＩＤ/生徒固定スケジュール設定のＩＤ
     * @param singleOrAll 個別全体選択フラグ
     * @param start 計画年月日
     * @param stuFixdSchdlEntity FixdSchdlEntityオブジェクト
     * @param updDatimeString 変更の時間
     * @param start1 開始日
     * @param end 終了日
     * @param startTime 開始時間
     * @param endTime 終了時間
     * @return
     */
    @RequestMapping(value = "/SubmitBtn", method = RequestMethod.POST)
    public R submitBtn(String key, Integer id, String singleOrAll, String start, StuFixdSchdlEntity stuFixdSchdlEntity, String updDatimeString, String start1, String end, String startTime, String endTime) {//,boolean start_check,boolean end_check
        //画面のユーザーID取得する
        String stuId = getUserCd();

        //新しい オブジェクトを作成する
        StuFixdSchdlEntity newStuFixdSchdlEntity = new StuFixdSchdlEntity();
        //"-"を"/"に変換する
        start1 = start1.replace("/", "-");
        end = end.replace("/", "-");

        //新しいフォマードの日付
        Date startDate = DateUtils.parse(start1, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        Date endDate = DateUtils.parse(end, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);

        //新しいフォマードの時間
        Timestamp startDateTime = new Timestamp(DateUtils.parse(start1 + " " + startTime + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
        Timestamp endDateTime = new Timestamp(DateUtils.parse(end + " " + endTime + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
        List<StuFixdSchdlEntity> fixdSchdlEntityList = null;
        if (StringUtils.equals("key", "1")) {
            //キーが登録の場合、当生徒の生徒固定スケジュール設定を取得する
            fixdSchdlEntityList = stuFixdSchdlService.list(
                    new QueryWrapper<StuFixdSchdlEntity>().select().and(wrapper->wrapper.eq("stu_id", stuId).eq("del_flg", 0)));
        } else {
            //キーが更新の場合、当生徒のその他の生徒固定スケジュール設定を取得する
            fixdSchdlEntityList = stuFixdSchdlService.list(
                    new QueryWrapper<StuFixdSchdlEntity>().select().and(wrapper->wrapper.eq("stu_id", stuId).eq("del_flg", 0).notIn("id", id)));
        }

        //画面関連チェック
        for (int i = 0; i < fixdSchdlEntityList.size(); i++) {

        }
        if (startDate.compareTo(endDate) > 0) {
            //画面．ブロック終了日を画面．ブロック開始日以降の日付で入力すること
            return R.error(MessageUtils.getMessage("MSGCOMN0024", "ブロック終了日", "ブロック開始日"));
        }
        if (StringUtils.equals(endTime, "00:00")) {
            //0時の場合
            int startHour = Integer.parseInt(startTime.split(":")[0]);
            if (startHour > 24) {
                return R.error(MessageUtils.getMessage("MSGCOMN0048", "ブロック終了時間", "ブロック開始時間"));
            }
        } else {
            if (startTime.compareTo(endTime) >= 0) {
                //画面．ブロック開始時間 を画面．ブロック開始時間以降の日付で入力すること
                return R.error(MessageUtils.getMessage("MSGCOMN0048", "ブロック終了時間", "ブロック開始時間"));
            }
        }

        //　キーが登録の場合
        if (StringUtils.equals(key, "1")) {

            //生徒のID
            newStuFixdSchdlEntity.setStuId(stuId);
            //ブロークID
            newStuFixdSchdlEntity.setBlockId(id);
            //ブロック表示名
            newStuFixdSchdlEntity.setBlockDispyNm(stuFixdSchdlEntity.getBlockDispyNm());
            //ブロック開始日
            newStuFixdSchdlEntity.setBlockStartDate(startDate);
            //ブロック終了日
            newStuFixdSchdlEntity.setBlockEndDate(endDate);
            //月曜日選択フラグ
            newStuFixdSchdlEntity.setMoDwChocFlg(stuFixdSchdlEntity.getMoDwChocFlg());
            //火曜日選択フラグ
            newStuFixdSchdlEntity.setTuDwChocFlg(stuFixdSchdlEntity.getTuDwChocFlg());
            //水曜日選択フラグ
            newStuFixdSchdlEntity.setWeDwChocFlg(stuFixdSchdlEntity.getWeDwChocFlg());
            //木曜日選択フラグ
            newStuFixdSchdlEntity.setThDwChocFlg(stuFixdSchdlEntity.getThDwChocFlg());
            //金曜日選択フラグ
            newStuFixdSchdlEntity.setFrDwChocFlg(stuFixdSchdlEntity.getFrDwChocFlg());
            //土曜日選択フラグ
            newStuFixdSchdlEntity.setSaDwChocFlg(stuFixdSchdlEntity.getSaDwChocFlg());
            //日曜日選択フラグ
            newStuFixdSchdlEntity.setSuDwChocFlg(stuFixdSchdlEntity.getSuDwChocFlg());
            //ブロック開始時間
            newStuFixdSchdlEntity.setBlockStartTime(startDateTime);
            //ブロック終了時間
            newStuFixdSchdlEntity.setBlockEndTime(endDateTime);
            //作成日時
            newStuFixdSchdlEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            newStuFixdSchdlEntity.setCretUsrId(stuId);
            //更新日時
            newStuFixdSchdlEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            newStuFixdSchdlEntity.setUpdUsrId(stuId);
            //削除フラグ
            newStuFixdSchdlEntity.setDelFlg(0);
            //この新しいオブジェクトを保存する
            stuFixdSchdlService.save(newStuFixdSchdlEntity);

            //ビッグデータ
            Extensions extensions = new Extensions();
            //ブロック開始日
            extensions.put(XApiConstant.EXT_KEY_BLOCK_START_DATE, DateUtils.format(startDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
            //ブロック終了日
            extensions.put(XApiConstant.EXT_KEY_BLOCK_END_DATE, DateUtils.format(endDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
            //ブロック開始時間
            extensions.put(XApiConstant.EXT_KEY_BLOCK_START_TIME, DateUtils.format(startDateTime, GakkenConstant.DATE_FORMAT_HH_MM_SS));
            //ブロック終了時間
            extensions.put(XApiConstant.EXT_KEY_BLOCK_END_TIME, DateUtils.format(endDateTime, GakkenConstant.DATE_FORMAT_HH_MM_SS));
            //ブロックID
            extensions.put(XApiConstant.EXT_KEY_BLOCK_ID, id);
            //ブロック表示名
            extensions.put(XApiConstant.EXT_KEY_BLOCK_DISPLAY_NM, stuFixdSchdlEntity.getBlockDispyNm());
            //個別又は全体
            extensions.put(XApiConstant.EXT_KEY_SINGLE_OR_ALL, "1:全体");
            //曜日選択リスト
            JsonArray jsonArray = new JsonArray();
            JsonObject obj = new JsonObject();
            //月曜日フラグ
            obj.addProperty(XApiConstant.EXT_KEY_MO_DW_CHOC_FLG, dwFormat(stuFixdSchdlEntity.getMoDwChocFlg()));
            //火曜日フラグ
            obj.addProperty(XApiConstant.EXT_KEY_TU_DW_CHOC_FLG, dwFormat(stuFixdSchdlEntity.getTuDwChocFlg()));
            //水曜日フラグ
            obj.addProperty(XApiConstant.EXT_KEY_WE_DW_CHOC_FLG, dwFormat(stuFixdSchdlEntity.getWeDwChocFlg()));
            //木曜日フラグ
            obj.addProperty(XApiConstant.EXT_KEY_TH_DW_CHOC_FLG, dwFormat(stuFixdSchdlEntity.getThDwChocFlg()));
            //金曜日フラグ
            obj.addProperty(XApiConstant.EXT_KEY_FR_DW_CHOC_FLG, dwFormat(stuFixdSchdlEntity.getFrDwChocFlg()));
            //土曜日フラグ
            obj.addProperty(XApiConstant.EXT_KEY_SA_DW_CHOC_FLG, dwFormat(stuFixdSchdlEntity.getSaDwChocFlg()));
            //日曜日フラグ
            obj.addProperty(XApiConstant.EXT_KEY_SU_DW_CHOC_FLG, dwFormat(stuFixdSchdlEntity.getSuDwChocFlg()));
            jsonArray.add(obj);
            extensions.put(XApiConstant.EXT_KEY_DW_CHOC_LIST, jsonArray);
            try {
                XApiUtils.saveStatement(Verbs.scheduled(), Activitys.schedule(), extensions);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            return R.ok().put("newFixdSchdlEntity", newStuFixdSchdlEntity).put("stuId", stuId);
        } else {
            //　キーが更新の場合
            StuFixdSchdlEntity stuFixdSchdlEntity1 = stuFixdSchdlService.getById(id);
            Integer blockId = stuFixdSchdlEntity1.getBlockId();
            //個別全体選択フラグが「全体」の場合、
            if (StringUtils.equals(singleOrAll, "1")) {
                StuFixdSchdlEntity stuFixdSchdlEntityResource = stuFixdSchdlService.getById(id);
                newStuFixdSchdlEntity.setId(id);
                if (stuFixdSchdlEntityResource != null && StringUtils.equals(
                        updDatimeString, DateUtils.format(stuFixdSchdlEntityResource.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS))) {
                    //ブロック開始日
                    newStuFixdSchdlEntity.setBlockStartDate(startDate);
                    //ブロック終了日
                    newStuFixdSchdlEntity.setBlockEndDate(endDate);
                    //月曜日選択フラグ
                    newStuFixdSchdlEntity.setMoDwChocFlg(stuFixdSchdlEntity.getMoDwChocFlg());
                    //火曜日選択フラグ
                    newStuFixdSchdlEntity.setTuDwChocFlg(stuFixdSchdlEntity.getTuDwChocFlg());
                    //水曜日選択フラグ
                    newStuFixdSchdlEntity.setWeDwChocFlg(stuFixdSchdlEntity.getWeDwChocFlg());
                    //木曜日選択フラグ
                    newStuFixdSchdlEntity.setThDwChocFlg(stuFixdSchdlEntity.getThDwChocFlg());
                    //金曜日選択フラグ
                    newStuFixdSchdlEntity.setFrDwChocFlg(stuFixdSchdlEntity.getFrDwChocFlg());
                    //土曜日選択フラグ
                    newStuFixdSchdlEntity.setSaDwChocFlg(stuFixdSchdlEntity.getSaDwChocFlg());
                    //日曜日選択フラグ
                    newStuFixdSchdlEntity.setSuDwChocFlg(stuFixdSchdlEntity.getSuDwChocFlg());
                    //ブロック開始時間
                    newStuFixdSchdlEntity.setBlockStartTime(startDateTime);
                    //ブロック終了時間
                    newStuFixdSchdlEntity.setBlockEndTime(endDateTime);
                    //更新日時
                    newStuFixdSchdlEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    newStuFixdSchdlEntity.setUpdUsrId(ShiroUtils.getUserId());

                    stuFixdSchdlService.updateById(newStuFixdSchdlEntity);
                } else {
                    return R.error(MessageUtils.getMessage("MSGCOMN0019"));
                }

                //ビッグデータ
                Extensions extensions = new Extensions();
                //ブロック開始日
                extensions.put(XApiConstant.EXT_KEY_BLOCK_START_DATE, DateUtils.format(startDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
                //ブロック終了日
                extensions.put(XApiConstant.EXT_KEY_BLOCK_END_DATE, DateUtils.format(endDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
                //ブロック開始時間
                extensions.put(XApiConstant.EXT_KEY_BLOCK_START_TIME, DateUtils.format(startDateTime, GakkenConstant.DATE_FORMAT_HH_MM_SS));
                //ブロック終了時間
                extensions.put(XApiConstant.EXT_KEY_BLOCK_END_TIME, DateUtils.format(endDateTime, GakkenConstant.DATE_FORMAT_HH_MM_SS));
                //ブロックID
                extensions.put(XApiConstant.EXT_KEY_BLOCK_ID, id);
                //ブロック表示名
                extensions.put(XApiConstant.EXT_KEY_BLOCK_DISPLAY_NM, stuFixdSchdlEntity.getBlockDispyNm());
                //個別又は全体
                extensions.put(XApiConstant.EXT_KEY_SINGLE_OR_ALL, "1:全体");
                //曜日選択リスト
                JsonArray jsonArray = new JsonArray();
                JsonObject obj = new JsonObject();
                //月曜日フラグ
                obj.addProperty(XApiConstant.EXT_KEY_MO_DW_CHOC_FLG, dwFormat(stuFixdSchdlEntity.getMoDwChocFlg()));
                //火曜日フラグ
                obj.addProperty(XApiConstant.EXT_KEY_TU_DW_CHOC_FLG, dwFormat(stuFixdSchdlEntity.getTuDwChocFlg()));
                //水曜日フラグ
                obj.addProperty(XApiConstant.EXT_KEY_WE_DW_CHOC_FLG, dwFormat(stuFixdSchdlEntity.getWeDwChocFlg()));
                //木曜日フラグ
                obj.addProperty(XApiConstant.EXT_KEY_TH_DW_CHOC_FLG, dwFormat(stuFixdSchdlEntity.getThDwChocFlg()));
                //金曜日フラグ
                obj.addProperty(XApiConstant.EXT_KEY_FR_DW_CHOC_FLG, dwFormat(stuFixdSchdlEntity.getFrDwChocFlg()));
                //土曜日フラグ
                obj.addProperty(XApiConstant.EXT_KEY_SA_DW_CHOC_FLG, dwFormat(stuFixdSchdlEntity.getSaDwChocFlg()));
                //日曜日フラグ
                obj.addProperty(XApiConstant.EXT_KEY_SU_DW_CHOC_FLG, dwFormat(stuFixdSchdlEntity.getSuDwChocFlg()));
                jsonArray.add(obj);
                extensions.put(XApiConstant.EXT_KEY_DW_CHOC_LIST, jsonArray);
                try {
                    XApiUtils.saveStatement(Verbs.scheduled(), Activitys.schedule(), extensions);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }

                return R.ok();
            } else {
                //個別全体選択フラグが「個別」の場合、
                StuIndivSchdlAdjustEntity stuIndivSchdlAdjustEntity = stuIndivSchdlAdjustService.getOne(new QueryWrapper<StuIndivSchdlAdjustEntity>().and(
                        wrapper->wrapper.eq("stu_id", stuId).eq("block_id", blockId).eq("plan_ymd",
                                DateUtils.parse(start, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS)).eq("del_flg", 0)));
                Timestamp planStartTime = new Timestamp(
                        DateUtils.parse(start1 + " " + startTime + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
                Timestamp planEndTime = new Timestamp(DateUtils.parse(start1 + " " + endTime + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
                if (stuIndivSchdlAdjustEntity == null) {
                    stuIndivSchdlAdjustEntity = new StuIndivSchdlAdjustEntity();
                    //生徒ID
                    stuIndivSchdlAdjustEntity.setStuId(stuId);
                    //ブロックID
                    stuIndivSchdlAdjustEntity.setBlockId(blockId);
                    //計画年月日
                    stuIndivSchdlAdjustEntity.setPlanYmd(DateUtils.parse(start, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS));
                    //ブロック表示名
                    stuIndivSchdlAdjustEntity.setBlockDispyNm(stuFixdSchdlEntity.getBlockDispyNm());
                    //ブロック開始時間
                    stuIndivSchdlAdjustEntity.setBlockStartTime(planStartTime);
                    //ブロック終了時間
                    stuIndivSchdlAdjustEntity.setBlockEndTime(planEndTime);
                    //固定ブロック廃止フラグ
                    stuIndivSchdlAdjustEntity.setFixdBlockAboltFlg("0");
                    //作成日時
                    stuIndivSchdlAdjustEntity.setCretDatime(DateUtils.getSysTimestamp());
                    //作成ユーザＩＤ
                    stuIndivSchdlAdjustEntity.setCretUsrId(ShiroUtils.getUserId());
                    //更新日時
                    stuIndivSchdlAdjustEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    stuIndivSchdlAdjustEntity.setUpdUsrId(ShiroUtils.getUserId());
                    //削除フラグ
                    stuIndivSchdlAdjustEntity.setDelFlg(0);
                    //固定ブロックID
                    stuIndivSchdlAdjustEntity.setStuFixdSchdlId(id);

                    stuIndivSchdlAdjustService.save(stuIndivSchdlAdjustEntity);

                    //ビッグデータ
                    Extensions extensions = new Extensions();
                    //ブロック開始日
                    extensions.put(
                            XApiConstant.EXT_KEY_BLOCK_START_DATE,
                            DateUtils.format(start, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
                    //ブロック終了日
                    extensions.put(
                            XApiConstant.EXT_KEY_BLOCK_END_DATE,
                            DateUtils.format(start, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
                    //ブロック開始時間
                    extensions.put(XApiConstant.EXT_KEY_BLOCK_START_TIME, DateUtils.format(planStartTime, GakkenConstant.DATE_FORMAT_HH_MM_SS));
                    //ブロック終了時間
                    extensions.put(XApiConstant.EXT_KEY_BLOCK_END_TIME, DateUtils.format(planEndTime, GakkenConstant.DATE_FORMAT_HH_MM_SS));
                    //ブロックID
                    extensions.put(XApiConstant.EXT_KEY_BLOCK_ID, blockId);
                    //ブロック表示名
                    extensions.put(XApiConstant.EXT_KEY_BLOCK_DISPLAY_NM, stuFixdSchdlEntity.getBlockDispyNm());
                    //個別又は全体
                    extensions.put(XApiConstant.EXT_KEY_SINGLE_OR_ALL, "0:個別");
                    //曜日選択リスト
                    JsonArray jsonArray = new JsonArray();
                    JsonObject obj = new JsonObject();
                    StuFixdSchdlEntity entity = getDwFlgEntity(DateUtils.parse(start, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS));
                    //月曜日フラグ
                    obj.addProperty(XApiConstant.EXT_KEY_MO_DW_CHOC_FLG, dwFormat(entity.getMoDwChocFlg()));
                    //火曜日フラグ
                    obj.addProperty(XApiConstant.EXT_KEY_TU_DW_CHOC_FLG, dwFormat(entity.getTuDwChocFlg()));
                    //水曜日フラグ
                    obj.addProperty(XApiConstant.EXT_KEY_WE_DW_CHOC_FLG, dwFormat(entity.getWeDwChocFlg()));
                    //木曜日フラグ
                    obj.addProperty(XApiConstant.EXT_KEY_TH_DW_CHOC_FLG, dwFormat(entity.getThDwChocFlg()));
                    //金曜日フラグ
                    obj.addProperty(XApiConstant.EXT_KEY_FR_DW_CHOC_FLG, dwFormat(entity.getFrDwChocFlg()));
                    //土曜日フラグ
                    obj.addProperty(XApiConstant.EXT_KEY_SA_DW_CHOC_FLG, dwFormat(entity.getSaDwChocFlg()));
                    //日曜日フラグ
                    obj.addProperty(XApiConstant.EXT_KEY_SU_DW_CHOC_FLG, dwFormat(entity.getSuDwChocFlg()));
                    jsonArray.add(obj);
                    extensions.put(XApiConstant.EXT_KEY_DW_CHOC_LIST, jsonArray);
                    try {
                        XApiUtils.saveStatement(Verbs.scheduled(), Activitys.schedule(), extensions);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }

                } else {//更新
                    //ブロック開始時間
                    stuIndivSchdlAdjustEntity.setBlockStartTime(planStartTime);
                    //ブロック終了時間
                    stuIndivSchdlAdjustEntity.setBlockEndTime(planEndTime);
                    //固定ブロック廃止フラグ
                    stuIndivSchdlAdjustEntity.setFixdBlockAboltFlg("0");
                    //更新日時
                    stuIndivSchdlAdjustEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    stuIndivSchdlAdjustEntity.setUpdUsrId(ShiroUtils.getUserId());
                    stuIndivSchdlAdjustService.updateById(stuIndivSchdlAdjustEntity);

                    //ビッグデータ
                    Extensions extensions = new Extensions();
                    //ブロック開始日
                    extensions.put(
                            XApiConstant.EXT_KEY_BLOCK_START_DATE,
                            DateUtils.format(stuIndivSchdlAdjustEntity.getPlanYmd(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
                    //ブロック終了日
                    extensions.put(
                            XApiConstant.EXT_KEY_BLOCK_END_DATE,
                            DateUtils.format(stuIndivSchdlAdjustEntity.getPlanYmd(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
                    //ブロック開始時間
                    extensions.put(XApiConstant.EXT_KEY_BLOCK_START_TIME, DateUtils.format(planStartTime, GakkenConstant.DATE_FORMAT_HH_MM_SS));
                    //ブロック終了時間
                    extensions.put(XApiConstant.EXT_KEY_BLOCK_END_TIME, DateUtils.format(planEndTime, GakkenConstant.DATE_FORMAT_HH_MM_SS));
                    //ブロックID
                    extensions.put(XApiConstant.EXT_KEY_BLOCK_ID, stuIndivSchdlAdjustEntity.getBlockId());
                    //ブロック表示名
                    extensions.put(XApiConstant.EXT_KEY_BLOCK_DISPLAY_NM, stuFixdSchdlEntity.getBlockDispyNm());
                    //個別又は全体
                    extensions.put(XApiConstant.EXT_KEY_SINGLE_OR_ALL, "0:個別");
                    //曜日選択リスト
                    JsonArray jsonArray = new JsonArray();
                    JsonObject obj = new JsonObject();
                    StuFixdSchdlEntity entity = getDwFlgEntity(stuIndivSchdlAdjustEntity.getPlanYmd());
                    //月曜日フラグ
                    obj.addProperty(XApiConstant.EXT_KEY_MO_DW_CHOC_FLG, dwFormat(entity.getMoDwChocFlg()));
                    //火曜日フラグ
                    obj.addProperty(XApiConstant.EXT_KEY_TU_DW_CHOC_FLG, dwFormat(entity.getTuDwChocFlg()));
                    //水曜日フラグ
                    obj.addProperty(XApiConstant.EXT_KEY_WE_DW_CHOC_FLG, dwFormat(entity.getWeDwChocFlg()));
                    //木曜日フラグ
                    obj.addProperty(XApiConstant.EXT_KEY_TH_DW_CHOC_FLG, dwFormat(entity.getThDwChocFlg()));
                    //金曜日フラグ
                    obj.addProperty(XApiConstant.EXT_KEY_FR_DW_CHOC_FLG, dwFormat(entity.getFrDwChocFlg()));
                    //土曜日フラグ
                    obj.addProperty(XApiConstant.EXT_KEY_SA_DW_CHOC_FLG, dwFormat(entity.getSaDwChocFlg()));
                    //日曜日フラグ
                    obj.addProperty(XApiConstant.EXT_KEY_SU_DW_CHOC_FLG, dwFormat(entity.getSuDwChocFlg()));
                    jsonArray.add(obj);
                    extensions.put(XApiConstant.EXT_KEY_DW_CHOC_LIST, jsonArray);
                    try {
                        XApiUtils.saveStatement(Verbs.scheduled(), Activitys.schedule(), extensions);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }

                }
                return R.ok();
            }
        }
    }

    /**
     * 削除ボタン押下、
     *
     * @param id ブロックＩＤ/生徒固定スケジュール設定のＩＤ
     * @param singleOrAll 個別全体選択フラグ
     * @param start 計画年月日
     * @param start1 開始日
     * @param startTime 開始時間
     * @param endTime 終了時間
     * @return
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public R deleteBtn(Integer id, String singleOrAll, String start, String start1, String startTime, String endTime) {

        StuIndivSchdlAdjustEntity stuIndivSchdlAdjustEntity;

        //生徒のID
        String stuId = getUserCd();
        //ブロックID
        int blockId = stuFixdSchdlService.getById(id).getBlockId();
        //計画年月日
        Date planYmd = DateUtils.parse(start, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS);
        //ブロック表示名
        String blockDispyNm = stuFixdSchdlService.getById(id).getBlockDispyNm();

        String planStart = start1.replace("/", "-");
        //開始時間
        Timestamp planStartTime = new Timestamp(DateUtils.parse(planStart + " " + startTime + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
        //終了時間
        Timestamp planEndTime = new Timestamp(DateUtils.parse(planStart + " " + endTime + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());

        if (StringUtils.equals(singleOrAll, "1")) {//全体
            //f10102Service.fixdschdlDel(id);
            f10102Service.delBoth(id);
        } else {//個別
            //個別全体選択フラグが「個別」の場合、
            stuIndivSchdlAdjustEntity = stuIndivSchdlAdjustService.getOne(new QueryWrapper<StuIndivSchdlAdjustEntity>().and(
                    wrapper->wrapper.eq("stu_id", stuId).eq("block_id", blockId).eq("plan_ymd",
                            DateUtils.parse(start, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS)).eq("del_flg", 0).eq("stu_fixd_schdl_id", id)));
            if (stuIndivSchdlAdjustEntity != null) {
                //固定ブロック廃止フラグ
                stuIndivSchdlAdjustEntity.setFixdBlockAboltFlg("1");
                //更新日時
                stuIndivSchdlAdjustEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                stuIndivSchdlAdjustEntity.setUpdUsrId(stuId);
                stuIndivSchdlAdjustService.updateById(stuIndivSchdlAdjustEntity);
            } else {
                stuIndivSchdlAdjustEntity = new StuIndivSchdlAdjustEntity();
                //生徒ID
                stuIndivSchdlAdjustEntity.setStuId(stuId);
                //ブロックID
                stuIndivSchdlAdjustEntity.setBlockId(blockId);
                //計画年月日
                stuIndivSchdlAdjustEntity.setPlanYmd(planYmd);
                //ブロック表示名
                stuIndivSchdlAdjustEntity.setBlockDispyNm(blockDispyNm);
                //ブロック開始時間
                stuIndivSchdlAdjustEntity.setBlockStartTime(planStartTime);
                //ブロック終了時間
                stuIndivSchdlAdjustEntity.setBlockEndTime(planEndTime);
                //固定ブロック廃止フラグ
                stuIndivSchdlAdjustEntity.setFixdBlockAboltFlg("1");
                //作成日時
                stuIndivSchdlAdjustEntity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                stuIndivSchdlAdjustEntity.setCretUsrId(stuId);
                //更新日時
                stuIndivSchdlAdjustEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                stuIndivSchdlAdjustEntity.setUpdUsrId(stuId);
                //削除フラグ
                stuIndivSchdlAdjustEntity.setDelFlg(0);
                //固定ブロックID
                stuIndivSchdlAdjustEntity.setStuFixdSchdlId(id);

                stuIndivSchdlAdjustService.save(stuIndivSchdlAdjustEntity);
            }
        }
        return R.ok();
    }

    /**
     * 曜日選択フォマード
     *
     * @param dwChocFlg
     */
    public String dwFormat(String dwChocFlg) {
        if (dwChocFlg == null || StringUtils.equals("0", dwChocFlg)) {
            return "0:未選択";
        } else {
            return "1:選択";
        }
    }

    /**
     * 曜日選択フォマード
     *
     * @param date
     */
    public StuFixdSchdlEntity getDwFlgEntity(Date date) {
        String yobi = DateUtils.format(date, GakkenConstant.DATE_FORMAT_E);
        StuFixdSchdlEntity stuFixdSchdlEntity = new StuFixdSchdlEntity();
        switch (yobi) {
            case "月":
                stuFixdSchdlEntity.setMoDwChocFlg("1");
                break;
            case "火":
                stuFixdSchdlEntity.setTuDwChocFlg("1");
                break;
            case "水":
                stuFixdSchdlEntity.setWeDwChocFlg("1");
                break;
            case "木":
                stuFixdSchdlEntity.setThDwChocFlg("1");
                break;
            case "金":
                stuFixdSchdlEntity.setFrDwChocFlg("1");
                break;
            case "土":
                stuFixdSchdlEntity.setSaDwChocFlg("1");
                break;
            case "日":
                stuFixdSchdlEntity.setSuDwChocFlg("1");
                break;
            default:
                break;
        }
        return stuFixdSchdlEntity;
    }
}
