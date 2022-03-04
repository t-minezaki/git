/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/26
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.common.entity.StuFixdSchdlEntity;
import jp.learningpark.modules.common.entity.StuIndivSchdlAdjustEntity;
import jp.learningpark.modules.common.service.MstBlockService;
import jp.learningpark.modules.common.service.StuFixdSchdlService;
import jp.learningpark.modules.common.service.StuIndivSchdlAdjustService;
import jp.learningpark.modules.student.dto.F11002Dto;
import jp.learningpark.modules.student.service.F11002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;

/**
 * <p>F11002_スマホ_学習情報登録(繰り返しプラン)_V7.0</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/04/26 : lyh: 新規<br />
 * @version 7.0
 */
@RequestMapping("/student/F11002")
@RestController
public class F11002Controller {

    /**
     * ブロックマスタ Service
     */
    @Autowired
    MstBlockService mstBlockService;

    /**
     * 生徒固定スケジュール設定
     */
    @Autowired
    StuFixdSchdlService stuFixdSchdlService;

    /**
     * 生徒個別スケジュール調整
     */
    @Autowired
    StuIndivSchdlAdjustService stuIndivSchdlAdjustService;

    /**
     * F11002  Service
     */
    @Autowired
    F11002Service f11002Service;

    private static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
            .toFormatter();

    /**
     * 初期表示
     *
     * @param stuFixedScheduleId
     * @param typeDiv
     * @param ymd
     * @return
     */
    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public R init(Integer stuFixedScheduleId, String typeDiv, Date ymd){
        R r = R.ok();
        //生徒ID
//        String stuId = ShiroUtils.getUserId();
        //マスタブロックから項目
        List<MstBlockEntity> mstBlock = mstBlockService.list(new QueryWrapper<MstBlockEntity>().eq("block_type_div","C1").eq("del_flg",0).orderByAsc("block_type_div"));
        r.put("mstBlock",mstBlock);
        //生徒ウィークリー計画実績設定を元に、当生徒の最も遅い計画日の翌日を取得する。
//        String planYmd = f11002Service.getMax(stuId);
        if (stuFixedScheduleId == -1){
            return r;
        }else{
            StuFixdSchdlEntity stuFixdSchdlEntity = stuFixdSchdlService.getById(stuFixedScheduleId);
            F11002Dto f11002Dto = new F11002Dto();
            Integer mstBlockEntityId;
            StuIndivSchdlAdjustEntity stuIndivSchdlAdjustEntity = stuIndivSchdlAdjustService.getOne(new QueryWrapper<StuIndivSchdlAdjustEntity>()
                    .eq("stu_fixd_schdl_id", stuFixedScheduleId).eq("plan_ymd", ymd).eq("fixd_block_abolt_flg", "0")
                    .eq("del_flg", 0).last("limit 1"));
            //個別全体選択フラグが「個別」
            if (StringUtils.equals(typeDiv, "1") && stuIndivSchdlAdjustEntity != null){
                //ブロックID
                f11002Dto.setBlockId(stuIndivSchdlAdjustEntity.getBlockId());
                //ブロック開始日
                f11002Dto.setBlockStartDate(stuIndivSchdlAdjustEntity.getPlanYmd());
                //ブロック終了日
                f11002Dto.setBlockEndDate(stuIndivSchdlAdjustEntity.getPlanYmd());
                //ブロック開始時間
                f11002Dto.setBlockStartTime(stuIndivSchdlAdjustEntity.getBlockStartTime());
                //ブロック終了時間
                f11002Dto.setBlockEndTime(stuIndivSchdlAdjustEntity.getBlockEndTime());
                r.put("updStr", DateUtils.format(stuIndivSchdlAdjustEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
            }else{//個別全体選択フラグが「全体」
                //ブロックID
                f11002Dto.setBlockId(stuFixdSchdlEntity.getBlockId());
                if (StringUtils.equals(typeDiv, "0")){
                    //ブロック開始日
                    f11002Dto.setBlockStartDate(stuFixdSchdlEntity.getBlockStartDate());
                    //ブロック終了日
                    f11002Dto.setBlockEndDate(stuFixdSchdlEntity.getBlockEndDate());
                }else {
                    //ブロック開始日
                    f11002Dto.setBlockStartDate(ymd);
                    //ブロック終了日
                    f11002Dto.setBlockEndDate(ymd);
                }
                //ブロック開始時間
                f11002Dto.setBlockStartTime(stuFixdSchdlEntity.getBlockStartTime());
                //ブロック終了時間
                f11002Dto.setBlockEndTime(stuFixdSchdlEntity.getBlockEndTime());
                r.put("updStr", DateUtils.format(stuFixdSchdlEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
            }
            //月曜日選択フラグ
            f11002Dto.setMoDwChocFlg(stuFixdSchdlEntity.getMoDwChocFlg());
            //火曜日選択フラグ
            f11002Dto.setTuDwChocFlg(stuFixdSchdlEntity.getTuDwChocFlg());
            //水曜日選択フラグ
            f11002Dto.setWeDwChocFlg(stuFixdSchdlEntity.getWeDwChocFlg());
            //木曜日選択フラグ
            f11002Dto.setThDwChocFlg(stuFixdSchdlEntity.getThDwChocFlg());
            //金曜日選択フラグ
            f11002Dto.setFrDwChocFlg(stuFixdSchdlEntity.getFrDwChocFlg());
            //土曜日選択フラグ
            f11002Dto.setSaDwChocFlg(stuFixdSchdlEntity.getSaDwChocFlg());
            //日曜日選択フラグ
            f11002Dto.setSuDwChocFlg(stuFixdSchdlEntity.getSuDwChocFlg());
            return r.put("scheduledDto",f11002Dto);
        }
    }

    //登録を押下
    @RequestMapping(value = "/SubmitBtn",method = RequestMethod.POST)
        public R submitBtn(Integer stuFixedScheduleId, String typeDiv, Date ymd, StuFixdSchdlEntity stuFixdSchdlEntity,Integer blockId, Date startDate
            ,String blockDispyNm, Date endDate, Date startTime, Date endTime, String updStr){
        //生徒ID
        String stuId = ShiroUtils.getUserId();
        if (stuFixedScheduleId == -1) {
            stuFixdSchdlEntity.setStuId(stuId);
            //ブロックID
            stuFixdSchdlEntity.setBlockId(blockId);
            //ブロック表示名
            stuFixdSchdlEntity.setBlockDispyNm(blockDispyNm);
            //ブロック開始日
            stuFixdSchdlEntity.setBlockStartDate(startDate);
            //ブロック終了日
            stuFixdSchdlEntity.setBlockEndDate(endDate);
            //ブロック開始時間
            stuFixdSchdlEntity.setBlockStartTime(DateUtils.toTimestamp(startTime));
            //ブロック終了時間
            stuFixdSchdlEntity.setBlockEndTime(DateUtils.toTimestamp(endTime));
            //作成日時
            stuFixdSchdlEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            stuFixdSchdlEntity.setCretUsrId(ShiroUtils.getUserEntity().getUsrId());
            //更新日時
            stuFixdSchdlEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            stuFixdSchdlEntity.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
            //削除フラグ
            stuFixdSchdlEntity.setDelFlg(0);
            //生徒固定スケジュール設定登録
            stuFixdSchdlService.save(stuFixdSchdlEntity);
        }else {
            if (StringUtils.equals(typeDiv, "0")){
                StuFixdSchdlEntity stuFixdSchdlEntityOld = stuFixdSchdlService.getById(stuFixedScheduleId);
                if (!StringUtils.equals(DateUtils.format(stuFixdSchdlEntityOld.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS), updStr)){
                    return R.error(MessageUtils.getMessage("MSGCOMN0019"));
                }
                //ブロックID
                stuFixdSchdlEntityOld.setBlockId(blockId);
                //ブロック表示名
                stuFixdSchdlEntityOld.setBlockDispyNm(blockDispyNm);
                //ブロック開始時間
                stuFixdSchdlEntityOld.setBlockStartDate(startDate);
                //ブロック終了時間
                stuFixdSchdlEntityOld.setBlockEndDate(endDate);
                //ブロック開始時間
                stuFixdSchdlEntityOld.setBlockStartTime(DateUtils.toTimestamp(startTime));
                //ブロック終了時間
                stuFixdSchdlEntityOld.setBlockEndTime(DateUtils.toTimestamp(endTime));
                //月曜日選択フラグ
                stuFixdSchdlEntityOld.setMoDwChocFlg(stuFixdSchdlEntity.getMoDwChocFlg());
                //火曜日選択フラグ
                stuFixdSchdlEntityOld.setTuDwChocFlg(stuFixdSchdlEntity.getTuDwChocFlg());
                //水曜日選択フラグ
                stuFixdSchdlEntityOld.setWeDwChocFlg(stuFixdSchdlEntity.getWeDwChocFlg());
                //木曜日選択フラグ
                stuFixdSchdlEntityOld.setThDwChocFlg(stuFixdSchdlEntity.getThDwChocFlg());
                //金曜日選択フラグ
                stuFixdSchdlEntityOld.setFrDwChocFlg(stuFixdSchdlEntity.getFrDwChocFlg());
                //土曜日選択フラグ
                stuFixdSchdlEntityOld.setSaDwChocFlg(stuFixdSchdlEntity.getSaDwChocFlg());
                //日曜日選択フラグ
                stuFixdSchdlEntityOld.setSuDwChocFlg(stuFixdSchdlEntity.getSuDwChocFlg());
                //更新日時
                stuFixdSchdlEntityOld.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                stuFixdSchdlEntityOld.setUpdUsrId(stuId);
                stuFixdSchdlService.updateById(stuFixdSchdlEntityOld);
            }else {
                //前画面引渡データ．個別全体選択フラグが「個別」の場合
                StuIndivSchdlAdjustEntity stuIndivSchdlAdjustEntity = stuIndivSchdlAdjustService.getOne(new QueryWrapper<StuIndivSchdlAdjustEntity>().and(
                        wrapper->wrapper.eq("stu_id", stuId).eq("plan_ymd", ymd).eq("del_flg", 0)));
                if (stuIndivSchdlAdjustEntity == null){
                    stuIndivSchdlAdjustEntity = new StuIndivSchdlAdjustEntity();
                    //生徒ID
                    stuIndivSchdlAdjustEntity.setStuId(stuId);
                    //固定ブロックID
                    stuIndivSchdlAdjustEntity.setStuFixdSchdlId(stuFixedScheduleId);
                    //ブロックID
                    stuIndivSchdlAdjustEntity.setBlockId(blockId);
                    //計画年月日
                    stuIndivSchdlAdjustEntity.setPlanYmd(ymd);
                    //ブロック表示名
                    stuIndivSchdlAdjustEntity.setBlockDispyNm(blockDispyNm);
                    //ブロック開始時間
                    stuIndivSchdlAdjustEntity.setBlockStartTime(DateUtils.toTimestamp(startTime));
                    //ブロック終了時間
                    stuIndivSchdlAdjustEntity.setBlockEndTime(DateUtils.toTimestamp(endTime));
                    //固定ブロック廃止フラグ
                    stuIndivSchdlAdjustEntity.setFixdBlockAboltFlg("0");
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
                    stuIndivSchdlAdjustService.save(stuIndivSchdlAdjustEntity);
                }else {
                    if (!StringUtils.equals(DateUtils.format(stuIndivSchdlAdjustEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS), updStr)){
                        return R.error(MessageUtils.getMessage("MSGCOMN0019"));
                    }
                    //ブロックID
                    stuIndivSchdlAdjustEntity.setBlockId(blockId);
                    //ブロック表示名
                    stuIndivSchdlAdjustEntity.setBlockDispyNm(blockDispyNm);
                    //ブロック開始時間
                    stuIndivSchdlAdjustEntity.setBlockStartTime(DateUtils.toTimestamp(startTime));
                    //ブロック終了時間
                    stuIndivSchdlAdjustEntity.setBlockEndTime(DateUtils.toTimestamp(endTime));
                    //更新日時
                    stuIndivSchdlAdjustEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    stuIndivSchdlAdjustEntity.setUpdUsrId(stuId);
                    stuIndivSchdlAdjustService.updateById(stuIndivSchdlAdjustEntity);
                }
            }
        }
        return R.ok();
    }

    /**
     *
     * @param stuFixedScheduleId    固定ブロックID
     * @param typeDiv               個別全体選択フラグ 0:全体/1:個別
     * @param ymd                   生徒個別スケジュール調整．計画年月日
     * @param blockId               ブロックＩＤ
     * @param blockDispyNm          ブロック表示名
     * @param startTime             ブロック開始時間
     * @param endTime               ブロック終了時間
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public R delete(Integer stuFixedScheduleId, String typeDiv, Date ymd, Integer blockId, String blockDispyNm, Date startTime, Date endTime){
        //生徒のID
        String stuId = ShiroUtils.getUserId();
        if (StringUtils.equals(typeDiv, "0")){
            //個別全体選択フラグが「全体」の場合、
            stuFixdSchdlService.removeById(stuFixedScheduleId);
            stuIndivSchdlAdjustService.remove(new QueryWrapper<StuIndivSchdlAdjustEntity>()
                    .eq("stu_fixd_schdl_id", stuFixedScheduleId));
        }else {
            //個別全体選択フラグが「個別」の場合、
            StuIndivSchdlAdjustEntity stuIndivSchdlAdjustEntity = stuIndivSchdlAdjustService.getOne(new QueryWrapper<StuIndivSchdlAdjustEntity>()
                    .eq("stu_fixd_schdl_id", stuFixedScheduleId).eq("plan_ymd", ymd).eq("del_flg", 0));
            if (stuIndivSchdlAdjustEntity != null){
                //固定ブロック廃止フラグ
                stuIndivSchdlAdjustEntity.setFixdBlockAboltFlg("1");
                //更新日時
                stuIndivSchdlAdjustEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                stuIndivSchdlAdjustEntity.setUpdUsrId(stuId);
                stuIndivSchdlAdjustService.updateById(stuIndivSchdlAdjustEntity);
            }else {
                stuIndivSchdlAdjustEntity = new StuIndivSchdlAdjustEntity();
                //生徒ID
                stuIndivSchdlAdjustEntity.setStuId(stuId);
                //ブロックID
                stuIndivSchdlAdjustEntity.setBlockId(blockId);
                //計画年月日
                stuIndivSchdlAdjustEntity.setPlanYmd(ymd);
                //ブロック表示名
                stuIndivSchdlAdjustEntity.setBlockDispyNm(blockDispyNm);
                //ブロック開始時間
                stuIndivSchdlAdjustEntity.setBlockStartTime(DateUtils.toTimestamp(startTime));
                //ブロック終了時間
                stuIndivSchdlAdjustEntity.setBlockEndTime(DateUtils.toTimestamp(endTime));
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
                stuIndivSchdlAdjustEntity.setStuFixdSchdlId(stuFixedScheduleId);

                stuIndivSchdlAdjustService.save(stuIndivSchdlAdjustEntity);
            }
        }
        return R.ok();
    }
}