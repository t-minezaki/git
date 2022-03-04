/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.NumberUtils;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstBlockDao;
import jp.learningpark.modules.common.dao.MstLearnSeasnDao;
import jp.learningpark.modules.common.dao.StuTermPlanDao;
import jp.learningpark.modules.common.dao.StuWeeklyPlanPerfDao;
import jp.learningpark.modules.common.entity.MstLearnSeasnEntity;
import jp.learningpark.modules.common.entity.StuTermPlanEntity;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import jp.learningpark.modules.student.dao.F10301Dao;
import jp.learningpark.modules.student.dto.F1030101Dto;
import jp.learningpark.modules.student.dto.F1030102Dto;
import jp.learningpark.modules.student.dto.F1030103Dto;
import jp.learningpark.modules.student.dto.F1030105Dto;
import jp.learningpark.modules.student.dto.F1030106Dto;
import jp.learningpark.modules.student.service.F10301Service;
import jp.learningpark.modules.xapi.Activitys;
import jp.learningpark.modules.xapi.Extensions;
import jp.learningpark.modules.xapi.Verbs;
import jp.learningpark.modules.xapi.XApiConstant;
import jp.learningpark.modules.xapi.XApiUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ウィークリープラン計画管理画面 ServiceImpl。
 * </p >
 *
 * @author NWT : yangfan <br />
 * 変更履歴 <br />
 * 2018/10/17 : yangfan: 新規<br />
 * @version 1.0
 */
@Service
public class F10301ServiceImpl implements F10301Service {

    /**
     * 生徒ウィークリー計画実績設定 DAO
     */
    @Autowired
    private StuWeeklyPlanPerfDao weeklyPlanPerfDao;

    /**
     * ウィークリープラン計画管理画面DAO
     */
    @Autowired
    private F10301Dao f10301Dao;

    /**
     * 生徒タームプラン設定 DAO
     */
    @Autowired
    private StuTermPlanDao termPlanDao;

    /**
     * ブロッグマスタ Dao
     */
    @Autowired
    private MstBlockDao mstBlockDao;

    /**
     * 学習時期マスタ Dao
     */
    @Autowired
    private MstLearnSeasnDao mstLearnSeasnDao;


    /**
     * <p>登録処理</p>
     *
     * @param dto 画面情報
     * @return ブロック情報
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public F1030103Dto doUpdate(F1030102Dto dto) {

        // ユーザーID
        String stuId = ShiroUtils.getUserId();

        StuWeeklyPlanPerfEntity upEntity = null;

        if (dto.getPlanPerfId() != null) {
            // 生徒ウィークリー計画実績設定
            upEntity = weeklyPlanPerfDao.selectById(dto.getPlanPerfId());
        }

        // 10.1  画面．学習ブロックエリア．今週の積み残しエリアからドラッグ＆ドロップ場合、
        if (GakkenConstant.FLG_TRUE.equals(dto.getRemainDispFlg())) {
            // 取得できないの場合、エラーとする
            if (upEntity == null) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
            }
            /* 2020/12/24 ITA-028、29 cuikailin modify start */
            if (dto.getStuTermPlanId() != null) {
                // 積み残し対象フラグが「対象」の場合
                // 生徒ウィークリー実績履歴登録処理
                f10301Dao.insertWeeklyPerfHst(stuId, DateUtils.getSysTimestamp(), dto.getPlanPerfId());
            }
            /* 2020/12/24 ITA-028、29 cuikailin modify end */
            // 生徒ウィークリー計画実績設定の編集処理
            this.updateWeeklyPlanPerf(dto, stuId, upEntity);
            // 実績学習時間（分）
            upEntity.setPerfLearnTm(null);
            // 学習理解度
            upEntity.setLearnLevUnds(null);
            // 実績年月日
            upEntity.setPerfYmd(null);
            // 実績学習開始時間
            upEntity.setPerfLearnStartTime(null);

            // 生徒ウィークリー計画実績設定の更新処理
            weeklyPlanPerfDao.updateAllColumnById(upEntity);
        } else {
            // 10.2  画面．学習ブロックエリア．今週の積み残しエリア以外の学習ブロックをドラッグ＆ドロップ場合、
            // 積み残し対象フラグが「対象外」の場合
            // 生徒ウィークリー計画実績設定に登録する
            if (upEntity != null) {
                // 生徒ウィークリー計画実績設定の編集処理
                upEntity.setBlockDispyNm(dto.getBlockDispyNm());
                this.updateWeeklyPlanPerf(dto, stuId, upEntity);

                // 生徒ウィークリー計画実績設定の更新処理
                weeklyPlanPerfDao.updateById(upEntity);
            } else {
                // 生徒タームプラン設定へ更新する。
                StuTermPlanEntity entity = new StuTermPlanEntity();
                // ID
                entity.setId(dto.getStuTermPlanId());
                // 計画登録フラグ
                entity.setPlanRegFlg(GakkenConstant.PLAN_REG_FLG_YES);
                // 更新日時
                entity.setUpdDatime(DateUtils.getSysTimestamp());
                // 更新ユーザＩＤ
                entity.setUpdUsrId(stuId);

                entity.setBlockDispyNm(dto.getBlockDispyNm());

                // 生徒タームプラン設定の更新処理。
                termPlanDao.updateById(entity);
                // 生徒ウィークリー計画実績設定の登録処理
                upEntity = new StuWeeklyPlanPerfEntity();
                upEntity.setDispyOrder(entity.getDispyOrder());
                this.insertWeeklyPlanPerf(dto, stuId, upEntity);

            }
        }
        // ブロック情報を編集する
        F1030103Dto f1030103Dto = new F1030103Dto();
        BeanUtils.copyProperties(upEntity, f1030103Dto);
        // 計画年月日
        f1030103Dto.setPlanYmd(DateUtils.format(upEntity.getPlanYmd(), GakkenConstant.DATE_FORMAT_YYYYMMDD));
        // 生徒ウィークリー計画実績設定ID
        f1030103Dto.setPlanPerfId(upEntity.getId());

        return f1030103Dto;
    }

    /**
     * <p>削除処理</p>
     *
     * @param id 生徒ウィークリー計画実績設定のID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer doDelete(Integer id) {
        StuWeeklyPlanPerfEntity entity = weeklyPlanPerfDao.selectById(id);

        // 生徒ウィークリー計画実績設定の削除処理
        int count = weeklyPlanPerfDao.deleteById(id);

        // ブロック種類区分=学習の場合、生徒タームプラン設定の更新を行う
        /* 2020/11/24 cuikailin V9.0 modify start */
        if (GakkenConstant.BLOCK_TYPE_DIV_S1.equals(entity.getBlockTypeDiv()) && entity.getStuTermPlanId() != null) {
        /* 2020/11/24 cuikailin V9.0 modify end */
            // 生徒タームプラン設定の取得処理
            StuTermPlanEntity termPlan = termPlanDao.selectById(entity.getStuTermPlanId());
            /* 2020/12/02 V9.0 cuikailin add start */
            termPlan.setBlockDispyNm(entity.getBlockDispyNm());
            /* 2020/12/02 V9.0 cuikailin add end */
            // 計画登録フラグ
            termPlan.setPlanRegFlg(GakkenConstant.FLG_FALSE);
            // 生徒タームプラン設定の更新処理
            termPlanDao.updateById(termPlan);
        }

        return count;
    }

    /**
     * <p>生徒ウィークリー計画実績設定に更新するデータ作成</p>
     *
     * @param dto 計画時間図エリアデータ
     * @param stuId 生徒ID
     * @param entity 更新対象
     */
    private void updateWeeklyPlanPerf(F1030102Dto dto, String stuId, StuWeeklyPlanPerfEntity entity) {
        // 計画年月日
        entity.setPlanYmd(DateUtils.parse(StringUtils.substring(dto.getStartTime(), 0, 10), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
        // 生徒計画学習時間（分）
        Timestamp timeStart = DateUtils.toTimestamp(DateUtils.parse(dto.getStartTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM));
        Timestamp timeEnd = DateUtils.toTimestamp(DateUtils.parse(dto.getEndTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM));
        entity.setStuPlanLearnTm(NumberUtils.longToInt((timeEnd.getTime() - timeStart.getTime()) / 60000));
        // 計画学習開始時間
        entity.setPlanLearnStartTime(timeStart);
        // 積み残し対象フラグ
        entity.setRemainDispFlg(null);
        // 計画学習時期ID
        entity.setPlanLearnSeasnId(dto.getPlanLearnSeasnId());
        // 更新日時
        entity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ
        entity.setUpdUsrId(stuId);
    }

    /**
     * <p>生徒ウィークリー計画実績設定に更新するデータ作成</p>
     *
     * @param dto 計画時間図エリアデータ
     * @param stuId 生徒ID
     * @param entity 更新対象
     */
    private void insertWeeklyPlanPerf(F1030102Dto dto, String stuId, StuWeeklyPlanPerfEntity entity) {
        // 生徒ID
        entity.setStuId(stuId);
        // 単元ID
        entity.setUnitId(NumberUtils.toInt(dto.getBlockId()));
        // 生徒タームプラン設定ID
        entity.setStuTermPlanId(dto.getStuTermPlanId());
        // ブロック表示名
        if (!StringUtils.isEmpty(dto.getBlockContext())) {
            entity.setBlockDispyNm(dto.getBlockDispyNm() + " " + dto.getBlockContext());
        } else {
            entity.setBlockDispyNm(dto.getBlockDispyNm() + "");
        }
        // ブロック種類区分
        entity.setBlockTypeDiv(dto.getBlockTypeDiv());
        // 計画年月日
        entity.setPlanYmd(DateUtils.parse(StringUtils.substring(dto.getStartTime(), 0, 10), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
        // 教科区分
        entity.setSubjtDiv(dto.getSubjtDiv());
        // 教科区分名
        entity.setSubjtNm(dto.getSubjtNm());
        // 生徒計画学習時間（分）
        Timestamp timeStart = DateUtils.toTimestamp(DateUtils.parse(dto.getStartTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM));
        Timestamp timeEnd = DateUtils.toTimestamp(DateUtils.parse(dto.getEndTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM));
        entity.setStuPlanLearnTm(NumberUtils.longToInt((timeEnd.getTime() - timeStart.getTime()) / 60000));
        // 計画学習時期ID
        entity.setPlanLearnSeasnId(dto.getPlanLearnSeasnId());
        // 計画学習開始時間
        entity.setPlanLearnStartTime(timeStart);
        // 実績学習時間（分）
        entity.setPerfLearnTm(null);
        // 学習理解度
        entity.setLearnLevUnds(null);
        // 積み残し対象フラグ
        // 障害43 START
        // entity.setRemainDispFlg(GakkenConstant.FLG_FALSE);
        entity.setRemainDispFlg(null);
        // 障害43 END
        // 作成日時
        entity.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ
        entity.setCretUsrId(stuId);
        // 更新日時
        entity.setUpdDatime(entity.getCretDatime());
        // 更新ユーザＩＤ
        entity.setUpdUsrId(stuId);
        // 削除フラグ
        entity.setDelFlg(0);

        // 生徒ウィークリー計画実績設定の更新処理
        weeklyPlanPerfDao.insert(entity);
    }

    public Color convertToColor(String colorStr) {
        colorStr = colorStr.substring(1);
        Color color =  new Color(Integer.parseInt(colorStr, 16)) ;
        return color;
    }

    /**
     * <p>その他ブロックの取得処理</p>
     *
     * @return その他ブロックリスト
     */
    @Override
    public List<F1030101Dto> getOtherBlock() {

        // その他ブロック   no18  0625
        List<F1030106Dto> otherBlockList = f10301Dao.selectOtherBlock(ShiroUtils.getUserId());
        List<F1030101Dto> blockList = new ArrayList<F1030101Dto>();

        for (F1030106Dto entity : otherBlockList) {
            if (StringUtils.isEmpty(entity.getColorId())){
                entity.setColorId("white");
                entity.setFontColor("black");
            }else {
                Color color = convertToColor(entity.getColorId());
                if (color.getRed() + color.getBlue() + color.getGreen() > 650) {
                    entity.setFontColor("black");
                } else {
                    entity.setFontColor("white");
                }
            }
            F1030101Dto dto = new F1030101Dto();
            // ブロックID
            dto.setBlockId(StringUtils.defaultString(entity.getId()));
            // ブロック表示名
            dto.setBlockDispyNm(entity.getBlockDispyNm());
            // ブロック種類区分
            dto.setBlockTypeDiv(entity.getBlockTypeDiv());
            // ブロック画像区分
            dto.setBlockPicDiv(entity.getBlockPicDiv());
            // 上層ブロックID
            dto.setUpplevBlockId(entity.getUpplevBlockId());
            // 理解度
            dto.setLearnLevUnds("0");
            //カラー
            dto.setColorId(entity.getColorId());
            //フォントの色
            dto.setFontColor(entity.getFontColor());
            blockList.add(dto);
        }

        return blockList;
    }

    /**
     * <p>
     * 計画済み学習ブロックの取得処理
     * </p>
     *
     * @return 計画済み学習ブロック情報
     */
    @Override
    public Map<Integer, F1030103Dto> getPlannedBlock() {
        // ブロック種類区分リスト
        List<String> typeDivList = new ArrayList<String>();
        typeDivList.add(GakkenConstant.BLOCK_TYPE_DIV_S1);

        // 生徒ウィークリー計画実績設定を取得する。
        List<F1030103Dto> list = f10301Dao.selectPlannedBlock(ShiroUtils.getUserId(), typeDivList, ShiroUtils.getCrmLearnPrdId());
        Map<Integer, F1030103Dto> result = new HashMap<Integer, F1030103Dto>();
        for (F1030103Dto dto : list) {
            if (StringUtils.isEmpty(dto.getColorId())){
                dto.setFontColor("black");
                dto.setColorId("white");
            }else {
                Color color = convertToColor(dto.getColorId());
                if (color.getRed() + color.getBlue() + color.getGreen() > 650) {
                    dto.setFontColor("black");
                } else {
                    dto.setFontColor("white");
                }
            }
            String blockDispyNm = dto.getBlockDispyNm();
            if (!StringUtils.isEmpty(blockDispyNm) && StringUtils.getStringCnt(blockDispyNm, " ") > 1) {
                int blankIndex = 0;
                blankIndex = StringUtils.indexOf(blockDispyNm, " ");
                blankIndex = StringUtils.indexOf(blockDispyNm, " ", blankIndex + 1);
                dto.setBlockDispyNm(blockDispyNm.substring(0, blankIndex));
                dto.setBlockContext(blockDispyNm.substring(blankIndex + 1));
            }
            result.put(dto.getPlanPerfId(), dto);
        }

        if (ShiroUtils.getSessionAttribute("bigDataSeasnList") != null && ShiroUtils.getSessionAttribute("bigDataLearnBlockInfo") != null) {
            Map<String, Object> bigDataSeasnList = (Map<String, Object>)ShiroUtils.getSessionAttribute("bigDataSeasnList");
            Map<String, Object> bigDataBlockInfo = (Map<String, Object>)ShiroUtils.getSessionAttribute("bigDataLearnBlockInfo");
            //ビッグデータ
            Extensions extensions = new Extensions();
            JsonArray array = new JsonArray();
            JsonObject jsonObject;
            Map<Integer, F1030103Dto> planPerf = null;
            //未計画ブロック個数
            if (bigDataBlockInfo.get("unPlanCnt") != null) {
                extensions.put(XApiConstant.EXT_KEY_UNPLANNED_BLOCK_SIZE, bigDataBlockInfo.get("unPlanCnt"));
            }
            //            if (result.get("planPerf") != null) {
            //                planPerf = (Map<Integer, F1030103Dto>)bigDataBlockInfo.get("planPerf");
            //            }

            //今週開始日
            extensions.put(XApiConstant.EXT_KEY_WEEK_BEGIN_DATE,
                    DateUtils.format(DateUtils.getMonday(DateUtils.getSysDate()), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
            //今週終了日
            extensions.put(XApiConstant.EXT_KEY_WEEK_END_DATE,
                    DateUtils.format(DateUtils.getSunday(DateUtils.getSysDate()), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
            //ビッグデータ  積み残し対象は空の場合  524
            if (result == null) {
                XApiUtils.saveStatement(Verbs.launched(), Activitys.schedule(), extensions);
            } else {
                List<F1030103Dto> f1030103DtoList = new ArrayList<F1030103Dto>(result.values());
                String weekStartDay = null;
                if (bigDataSeasnList.get("weekStartDay") != null) {
                    weekStartDay = StringUtils.defaultString(bigDataSeasnList.get("weekStartDay"));
                }
                for (int i = 0; i < f1030103DtoList.size(); i++) {
                    if (f1030103DtoList.get(i) != null && weekStartDay != null) {
                        // 生徒ウィークリー計画実績設定．積み残し対象フラグ　＝ 「null」「1：対象」  and 生徒ウィークリー計画実績設定．計画年月日 ＜ 画面．学習ブロックエリア．学習時期開始日 または　生徒ウィークリー計画実績設定．積み残し対象フラグ　= 「3：強制対象」
                        if ((f1030103DtoList.get(i).getPlanYmd().compareTo(weekStartDay) < 0 && (StringUtils.equals("",
                                f1030103DtoList.get(i).getRemainDispFlg()) || StringUtils.equals(
                                null, f1030103DtoList.get(i).getRemainDispFlg()) || StringUtils.equals("1",
                                f1030103DtoList.get(i).getRemainDispFlg()))) || StringUtils.equals("3", f1030103DtoList.get(i).getRemainDispFlg())) {
                            jsonObject = new JsonObject();
                            //ブロック教科区分
                            jsonObject.addProperty(
                                    XApiConstant.EXT_KEY_BLOCK_SUBJT_DIV, f1030103DtoList.get(i) == null ? "" : f1030103DtoList.get(i).getSubjtDiv());
                            //ブロック単元ID
                            jsonObject.addProperty(XApiConstant.EXT_KEY_BLOCK_UNIT_ID,
                                    f1030103DtoList.get(i) == null ? "" : StringUtils.defaultString(f1030103DtoList.get(i).getUnitId()));
                            //ブロック表示名
                            jsonObject.addProperty(
                                    XApiConstant.EXT_KEY_BLOCK_DISPLAY_NAME, f1030103DtoList.get(i) == null ? "" : f1030103DtoList.get(i).getBlockDispyNm());
                            //当日日付
                            jsonObject.addProperty(
                                    XApiConstant.EXT_KEY_DATE, DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
                            //積み残し判定フラグ
                            jsonObject.addProperty(
                                    XApiConstant.EXT_KEY_REMAIN_FLG, f1030103DtoList.get(i) == null ? "" : f1030103DtoList.get(i).getRemainDispFlg());
                            array.add(jsonObject);
                        }
                    }
                }
                //array is null の場合
                if (array.size() != 0) {
                    extensions.put(XApiConstant.EXT_KEY_LEAVE_BLOCK_INFO, array);
                }
                XApiUtils.saveStatement(Verbs.launched(), Activitys.schedule(), extensions);
            }
        }
        ShiroUtils.getSession().removeAttribute("bigDataSeasnList");
        ShiroUtils.getSession().removeAttribute("bigDataLearnBlockInfo");
        return result;
    }

    /**
     * <p>復習ブロックの取得処理(F10301)</p>
     *
     * @return 復習ブロック情報リスト
     */
    @Override
    public List<F1030101Dto> getReviewBlock() {
        List<F1030101Dto> blockList = new ArrayList<F1030101Dto>();
        List<String> blockTypeDiv = new ArrayList<>();
        /****2020-11-11 cuikailin V9.0 modify start******/
        blockTypeDiv.add(GakkenConstant.BLOCK_TYPE_DIV_S1);
        /****2020-11-11 cuikailin V9.0 modify end******/
        blockTypeDiv.add(GakkenConstant.BLOCK_TYPE_DIV_R1);
        blockTypeDiv.add(GakkenConstant.BLOCK_TYPE_DIV_W1);
        blockTypeDiv.add(GakkenConstant.BLOCK_TYPE_DIV_V1);
        blockTypeDiv.add(GakkenConstant.BLOCK_TYPE_DIV_P1);
        // 復習ブロックの取得  0624  add塾の宿題

        List<F1030106Dto> reviewBlockList = f10301Dao.getReviewBlockList(blockTypeDiv, ShiroUtils.getUserId());
        for (F1030106Dto entity : reviewBlockList) {
            if (StringUtils.isEmpty(entity.getColorId())){
                entity.setFontColor("black");
                entity.setColorId("white");
            }else {
                Color color = convertToColor(entity.getColorId());
                if (color.getRed() + color.getBlue() + color.getGreen() > 650) {
                    entity.setFontColor("black");
                } else {
                    entity.setFontColor("white");
                }
            }

            F1030101Dto dto = new F1030101Dto();
            // ブロック表示名
            dto.setBlockDispyNm(entity.getBlockDispyNm());
            // ブロック種類区分
            dto.setBlockTypeDiv(entity.getBlockTypeDiv());
            // 理解度
            dto.setLearnLevUnds("0");
            // ブロックID
            dto.setBlockId(StringUtils.defaultString(entity.getId()));
            //ブロックカラー
            dto.setColorId(entity.getColorId());
            dto.setFontColor(entity.getFontColor());

            blockList.add(dto);
        }
        return blockList;
    }

    /**
     * <p>学習週の取得処理</p>
     *
     * @return 学習週リスト
     */
    @Override
    public Map<String, Object> getLearnSeasnInfo() {
        Map<String, Object> resultInfo = new HashMap<String, Object>();

        List<Map<String, Object>> seasnList = new ArrayList<Map<String, Object>>();
        // 当生徒当塾のすべての学習週リストを取得する
        List<MstLearnSeasnEntity> dataList = mstLearnSeasnDao.selectList(
                new QueryWrapper<MstLearnSeasnEntity>().select("id", "learn_seasn_start_dy", "learn_seasn_end_dy").eq("crm_learn_prd_id",
                        ShiroUtils.getCrmLearnPrdId()).eq("del_flg", 0).orderByAsc("plan_learn_seasn", "learn_seasn_start_dy"));
        // システムデータ
        String sysDate = DateUtils.format(DateUtils.getSysDate(), Constant.DATE_FORMAT_YYYYMMDD);

        for (MstLearnSeasnEntity data : dataList) {
            Map<String, Object> result = new HashMap<String, Object>();
            // 塾学習期間ID
            result.put("seasnId", data.getId());
            // 学習時期開始日
            result.put("startDy", DateUtils.format(data.getLearnSeasnStartDy(), GakkenConstant.DATE_FORMAT_YYYYMMDD));

            // 今週の判定
            if (sysDate.compareTo(DateUtils.format(data.getLearnSeasnStartDy(), Constant.DATE_FORMAT_YYYYMMDD)) >= 0 && sysDate.compareTo(
                    DateUtils.format(data.getLearnSeasnEndDy(), Constant.DATE_FORMAT_YYYYMMDD)) <= 0) {
                result.put("label", "今週");
                // 今週フラグ
                result.put("thisWeek", GakkenConstant.FLG_TRUE);
                // 今週開始日
                resultInfo.put("weekStartDay", DateUtils.format(data.getLearnSeasnStartDy(), Constant.DATE_FORMAT_YYYYMMDD));
            } else {
                result.put("label", DateUtils.format(data.getLearnSeasnStartDy(), GakkenConstant.DATE_FORMAT_M_D_SLASH) + "～");
                result.put("thisWeek", GakkenConstant.FLG_FALSE);
            }
            seasnList.add(result);
        }

        resultInfo.put("seasnList", seasnList);
        ShiroUtils.setSessionAttribute("bigDataSeasnList", resultInfo);

        return resultInfo;
    }

    /**
     * <p>学習ブロックの取得処理</p>
     *
     * @param weekStartDay 今週開始日
     * @return 学習ブロックリスト
     */
    @Override
    public Map<String, Object> getLearnBlockInfo(String weekStartDay) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, List<F1030101Dto>> dataMap = new HashMap<String, List<F1030101Dto>>();
        // 学習ブロックの取得処理
        List<F1030101Dto> dataList = f10301Dao.selectLearnBlock(ShiroUtils.getUserId(), ShiroUtils.getCrmLearnPrdId());

        // 未計画ブロック個数
        long count = 0;
        // 遅れている単元の学習週開始日
        String fristDay = "";
        // 進んでいる単元の学習週開始日
        String lastDay = "";
        // 最も古いブロック
        String oldDay = "";
        for (F1030101Dto dto : dataList) {
            if (StringUtils.isEmpty(dto.getColorId())){
                dto.setFontColor("black");
                dto.setColorId("white");
            }else {
                Color color = convertToColor(dto.getColorId());
                if (color.getRed() + color.getBlue() + color.getGreen() > 650) {
                    dto.setFontColor("black");
                } else {
                    dto.setFontColor("white");
                }
            }
            if (!dataMap.containsKey(dto.getLearnSeasnStartDy())) {
                dataMap.put(dto.getLearnSeasnStartDy(), new ArrayList<F1030101Dto>());
            }
            String blockDispyNm = dto.getBlockDispyNm();
            if (!StringUtils.isEmpty(blockDispyNm) && StringUtils.getStringCnt(blockDispyNm, " ") > 1) {
                int blankIndex = 0;
                blankIndex = StringUtils.indexOf(blockDispyNm, " ");
                blankIndex = StringUtils.indexOf(blockDispyNm, " ", blankIndex + 1);
                dto.setBlockDispyNm(blockDispyNm.substring(0, blankIndex));
                dto.setBlockContext(blockDispyNm.substring(blankIndex + 1));
            }
            dataMap.get(dto.getLearnSeasnStartDy()).add(dto);
            // 生徒未計画ブロック
            if (GakkenConstant.PLAN_REG_FLG_NO.equals(dto.getPlanRegFlg())) {

                if (weekStartDay.compareTo(dto.getLearnSeasnStartDy()) > 0) {
                    if (StringUtils.isEmpty(oldDay) || oldDay.compareTo(dto.getLearnSeasnStartDy()) > 0) {
                        oldDay = dto.getLearnSeasnStartDy();
                    }
                    count++;
                }

                if (StringUtils.isEmpty(fristDay) || fristDay.compareTo(dto.getLearnSeasnStartDy()) > 0) {
                    fristDay = dto.getLearnSeasnStartDy();
                }

                if (StringUtils.isEmpty(lastDay) || dto.getLearnSeasnStartDy().compareTo(lastDay) > 0) {
                    lastDay = dto.getLearnSeasnStartDy();
                }
            }
        }
        result.put("termPlan", dataMap);
        //
        result.put("unPlanCnt", count);
        // 最も古いブロック
        result.put("oldDay", oldDay);
        // 遅れている単元の学習週開始日
        result.put("fristDay", fristDay);
        // 進んでいる単元の学習週開始日
        result.put("lastDay", lastDay);
        ShiroUtils.setSessionAttribute("bigDataLearnBlockInfo", result);
        return result;
    }

    /**
     * <p>　予習ブロックの取得処理(F10301)</p>
     *
     * @return 予習ブロックリスト
     */
    @Override
    public List<F1030101Dto> getPreviewBlock() {
        // 予習ブロックを取得する  no7  0625
        return f10301Dao.selectPreviewBlock(ShiroUtils.getUserId());
    }

    /**
     * <p>生徒基本情報取得処理</p>
     *
     * @return 生徒基本情報
     */
    @Override
    public Map<String, String> getStudentInfo() {
        // 生徒基本情報を取得する
        return f10301Dao.selectStudentInfo(ShiroUtils.getUserId());
    }

    /**
     * <p>学習ブロックの取得処理(F10302)</p>
     *
     * @return 積み残しブロック情報リスト
     */
    @Override
    public Map<String, Object> getLearnBlock() {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, List<F1030103Dto>> dataMap = new HashMap<String, List<F1030103Dto>>();
        // ブロック種類区分リスト
        List<String> typeDivList = new ArrayList<String>();
        typeDivList.add(GakkenConstant.BLOCK_TYPE_DIV_S1);
        typeDivList.add(GakkenConstant.BLOCK_TYPE_DIV_R1);
        typeDivList.add(GakkenConstant.BLOCK_TYPE_DIV_P1);
        typeDivList.add(GakkenConstant.BLOCK_TYPE_DIV_W1);
        //TYJ 追加
        typeDivList.add(GakkenConstant.BLOCK_TYPE_DIV_V1);

        // 生徒ID、対象開始日、対象終了日により生徒ウィークリー計画実績設定を取得する。
        List<F1030103Dto> list = f10301Dao.selectPlannedBlock(ShiroUtils.getUserId(), typeDivList, ShiroUtils.getCrmLearnPrdId());
//        List<F1030103Dto> notStartList = new ArrayList<F1030103Dto>();
        String sysDate = DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDD);
        for (F1030103Dto dto : list) {
            if (!dataMap.containsKey(dto.getLearnSeasnStartDy())) {
                dataMap.put(dto.getLearnSeasnStartDy(), new ArrayList<F1030103Dto>());
            }
            String blockDispyNm = dto.getBlockDispyNm();
            if (!StringUtils.isEmpty(blockDispyNm) && StringUtils.getStringCnt(blockDispyNm, " ") > 1) {
                int blankIndex = 0;
                blankIndex = StringUtils.indexOf(blockDispyNm, " ");
                blankIndex = StringUtils.indexOf(blockDispyNm, " ", blankIndex + 1);
                dto.setBlockDispyNm(blockDispyNm.substring(0, blankIndex));
                dto.setBlockContext(blockDispyNm.substring(blankIndex + 1));
            }
            dataMap.get(dto.getLearnSeasnStartDy()).add(dto);
            // 生徒未計画ブロック
//            if (dto.getLearnLevUnds() == null && dto.getPlanYmd().compareTo(sysDate) < 0) {
//                notStartList.add(dto);
//            }
        }

        result.put("planPerf", dataMap);
//        result.put("notStartBlock", notStartList);
        return result;
    }

    /**
     * <p>
     * 印刷用計画済みブロック情報取得処理(R10001)
     * </p>
     *
     * @param startYmd 週開始日
     * @param endYmd 週終了日
     * @return 計画済みブロック情報取得処理
     */
    @Override
    public List<F1030105Dto> getPrintPlannedBlock(Date startYmd, Date endYmd) {
        // 印刷用計画済みブロック情報取得処理
        return f10301Dao.selectPrintPlannedBlock(ShiroUtils.getUserId(), startYmd, endYmd);
    }
}
