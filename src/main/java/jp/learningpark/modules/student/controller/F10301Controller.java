/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.common.utils.dto.SchdlDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.student.dto.F10101Dto;
import jp.learningpark.modules.student.dto.F1030101Dto;
import jp.learningpark.modules.student.dto.F1030102Dto;
import jp.learningpark.modules.student.dto.F1030103Dto;
import jp.learningpark.modules.student.service.F10003Service;
import jp.learningpark.modules.student.service.F10101Service;
import jp.learningpark.modules.student.service.F10301Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import jp.learningpark.modules.xapi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * <p>
 * F10301_ウィークリープラン計画管理画面 Controllerです。
 * </p>
 *
 * @author NWT : gaoli <br />
 * 変更履歴 <br />
 * 2018/10/09 : gaoli: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/student")
public class F10301Controller extends AbstractController {

    /**
     * 共通処理 Service
     */
    @Autowired
    private CommonService commonService;

    /**
     * ブロッグマスタ Service
     */
    @Autowired
    private F10301Service f10301Service;

    /**
     * コードマスタ_明細 Service
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * 生徒ウィークリー計画実績設定 Service
     */
    @Autowired
    private StuWeeklyPlanPerfService stuWeeklyPlanPerfService;

    /**
     * 学習時期マスタ Service
     */
    @Autowired
    private MstLearnSeasnService mstLearnSeasnService;

    /**
     * 生徒タームプラン設定 Service
     */
    @Autowired
    private StuTermPlanService stuTermPlanService;

    @Autowired
    private F10101Service f10101Service;

    /**
     * 生徒固定スケジュール設定 Service
     */
    @Autowired
    private StuFixdSchdlService stuFixdSchdlService;
    /**
     * f10003Service
     */
    @Autowired
    private F10003Service f10003Service;

    /**
     *
     */
    @Autowired
    private MstStuService mstStuService;

    /**
     * 前週へボタン、次週へボタン押下、スケージュールの取得処理を行う
     *
     * @param tgtYmd 対象年月
     * @return スケージュール情報
     */
    @RequestMapping(value = "/F10301/schdlList", method = RequestMethod.GET)
    public R getWeekSchdlList(String tgtYmd) {
        if (StringUtils.isEmpty(tgtYmd)) {
            return R.error(500, MessageUtils.getMessage("MSGCOMN0017"));
        }
        Map<Integer,String> map = new HashMap<>();
        // 画面情報
        List<F10101Dto> blockMstEntityList = f10101Service.getBlock();
        for (F10101Dto block:blockMstEntityList){
            if (StringUtils.isEmpty(block.getColorId())){
                block.setColorId("white");
                block.setFontColor("black");
            }else {
                Color color = convertToColor(block.getColorId());
                if (color.getRed() + color.getBlue() + color.getGreen() > 650) {
                    block.setFontColor("black");
                } else {
                    block.setFontColor("white");
                }
            }
            map.put(block.getId(),block.getColorId());
        }

        // 計画図エリア情報の取得処理
        return R.ok().put("schdlList", this.getSchdlList(DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYYMMDD))).put("map",map);
    }

    /**
     * 学習ブロックの取得処理を行う
     *
//     * @param tgtYmd 対象年月
     * @return 学習ブロック情報
     */
    @RequestMapping(value = "/F10301/blockInfo", method = RequestMethod.GET)
    public R getNextMonthBlock() {
        Map<String, Object> blockInfo = this.getBlockInfo();
        //生徒教科書データ有無の取得  no8
//        List<StuTextbChocEntity> stuTextbChocEntityList = stuTextbChocService.list(
//                new QueryWrapper<StuTextbChocEntity>().eq("stu_id", ShiroUtils.getUserId()).eq("del_flg", 0));
        Integer crmLearnPrdId = (Integer) ShiroUtils.getSessionAttribute(GakkenConstant.CRM_LEARN_PRD_ID);
        MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().and(w->w.eq("stu_id", ShiroUtils.getUserId()).eq("del_flg", 0)));
        Map<String, Object> map = new HashMap<>();
        map.put("stuId", ShiroUtils.getUserId());
        map.put("schyDiv", mstStuEntity.getSchyDiv());
        map.put("orgId", ShiroUtils.getUserEntity().getOrgId());
        //塾学習期間ID
        map.put("crmLearnPrdId", crmLearnPrdId);
        List<MstTextbEntity> stuTextbChocEntityList = f10003Service.getTextbListOfStuByStuIdAndOrgIdAndSchyDiv(map);
        // 学習のブロックエリア情報の取得処理
        return R.ok().put("blockInfo", blockInfo).put("stuNm", ShiroUtils.getSessionAttribute(GakkenConstant.STU_NM)).put("stuTextbChocEntityList",
                stuTextbChocEntityList);
    }

    /**
     * 登録ボタン押下、更新処理を行う
     *
     * @param dto 画面情報
     * @return ブロック情報
     */
    @RequestMapping(value = "/F10301/update", method = RequestMethod.POST)
    public R update(F1030102Dto dto) {
        if (dto == null) {
            return R.ok();
        }
        StuTermPlanEntity stuTermPlanEntity = stuTermPlanService.getById(dto.getStuTermPlanId());
        MstLearnSeasnEntity mstLearnSeasnEntity = mstLearnSeasnService.getById(dto.getPlanLearnSeasnId());

        // 17.1 画面の計画時間図エリアの計画ブロックを最新化にするため、当生徒の基準日の当週の生徒ウィークリー計画実績設定のデータを抽出し物理削除する。
        // 学習のブロックエリア情報の取得処理
        F1030103Dto result = f10301Service.doUpdate(dto);

        //ビッグデータ
        Extensions exts = new Extensions();
        MstCodDEntity mstCodDEntity;
        //ブロック教科区分
        if (!StringUtils.equals(dto.getBlockTypeDiv(), "O1") && !StringUtils.equals(dto.getBlockTypeDiv(), "O2") && !StringUtils.equals(dto.getBlockTypeDiv(),
                "O3")) {
            if (StringUtils.equals("", dto.getSubjtNm())) {
                String subjtNm = "";
                mstCodDEntity = mstCodDService.getOne(
                        new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "SUBJT_DIV").eq("cod_cd", dto.getBlockTypeDiv()).eq("del_flg", 0));
                if (mstCodDEntity != null) {
                    subjtNm = mstCodDEntity.getCodValue();
                }
                exts.put(XApiConstant.EXT_KEY_SUBJT_DIV, dto.getSubjtDiv() + ":" + subjtNm);
            } else {
                exts.put(XApiConstant.EXT_KEY_SUBJT_DIV, dto.getSubjtDiv() + ":" + dto.getSubjtNm());
            }
        }
        //ブロック単元ID
        exts.put(XApiConstant.EXT_KEY_UNIT_ID, result.getUnitId());
        //生徒計画時間（分）
        exts.put(XApiConstant.EXT_KEY_STU_PLAN_LEARN_TM, result.getStuPlanLearnTm());
        //メンター計画時間（分）
        if (stuTermPlanEntity != null) {
            exts.put(XApiConstant.EXT_KEY_MENTOR_PLAN_TIME, stuTermPlanEntity.getPlanLearnTm());
        }
        //計画学習開始時間
        exts.put(XApiConstant.EXT_KEY_PLAN_LEARN_START_TIME,
                DateUtils.format(dto.getStartTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM, GakkenConstant.DATE_FORMAT_HH_MM_SS));

        //ブロック種類区分
        String blockType = "";
        if (!StringUtils.equals(dto.getBlockTypeDiv(), "O1") && !StringUtils.equals(dto.getBlockTypeDiv(), "O2") && !StringUtils.equals(dto.getBlockTypeDiv(),
                "O3")) {
            mstCodDEntity = mstCodDService.getOne(
                    new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "BLOCK_TYPE_DIV").eq("cod_cd", dto.getBlockTypeDiv()).eq("del_flg", 0));
            if (mstCodDEntity != null) {
                blockType = mstCodDEntity.getCodValue();
            }
        } else {
            blockType = dto.getBlockDispyNm();
        }

        exts.put(XApiConstant.EXT_KEY_BLOCK_TYPE_DIV, dto.getBlockTypeDiv() + ":" + blockType);
        //計画年月日
        exts.put(XApiConstant.EXT_KEY_PLAN_YMD,
                DateUtils.format(dto.getStartTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
        if (mstLearnSeasnEntity != null) {
            //計画学習時期開始日
            exts.put(XApiConstant.EXT_KEY_PLAN_LEARN_SEASN_START_DATE,
                    DateUtils.format(mstLearnSeasnEntity.getLearnSeasnStartDy(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
            //計画学習時期終了日
            exts.put(XApiConstant.EXT_KEY_PLAN_LEARN_SEASN_END_DATE,
                    DateUtils.format(mstLearnSeasnEntity.getLearnSeasnEndDy(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
        }
        //ブロックID
        exts.put(XApiConstant.EXT_KEY_BLOCK_ID, dto.getBlockId());
        //ブロック表示名
        exts.put(XApiConstant.EXT_KEY_BLOCK_DISPLAY_NAME, dto.getBlockDispyNm());
        try {
            XApiUtils.saveStatement(Verbs.scheduled(), Activitys.schedule(), exts);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return R.ok().put("result", result);
    }

    /**
     * 削除ボタン押下、削除処理を行う
     *
     * @param id 生徒ウィークリー計画実績設定ID
     * @return なし
     */
    @RequestMapping(value = "/F10301/delete", method = RequestMethod.POST)
    public R delete(Integer id) {
        if (id == null) {
            return R.error(MessageUtils.getMessage("MSGCOMD0001", "ID"));
        }
        StuWeeklyPlanPerfEntity stuWeeklyPlanPerfEntity = stuWeeklyPlanPerfService.getById(id);

        // 17.1 画面の計画時間図エリアの計画ブロックを最新化にするため、当生徒の基準日の当週の生徒ウィークリー計画実績設定のデータを抽出し物理削除する。
        // 学習のブロックエリア情報の取得処理
        f10301Service.doDelete(id);

        //ビッグデータ
        Extensions extensions = new Extensions();
        if (stuWeeklyPlanPerfEntity != null) {
            //ブロック単元ID
            extensions.put(XApiConstant.EXT_KEY_UNIT_ID, stuWeeklyPlanPerfEntity.getUnitId());
            //ブロック教科区分
            MstCodDEntity mstCodDEntity = mstCodDService.getOne(
                    new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "SUBJT_DIV").eq("cod_cd", stuWeeklyPlanPerfEntity.getSubjtDiv()).eq(
                            "del_flg", 0));
            String codValue = "";
            if (mstCodDEntity != null) {
                codValue = mstCodDEntity.getCodValue();
            }
            extensions.put(XApiConstant.EXT_KEY_SUBJT_DIV, stuWeeklyPlanPerfEntity.getSubjtDiv() + ":" + codValue);
            //ブロック種類区分
            String blockType = "";
            mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "BLOCK_TYPE_DIV").eq("cod_cd",
                    stuWeeklyPlanPerfEntity.getBlockTypeDiv()).eq("del_flg", 0));
            if (mstCodDEntity != null) {
                blockType = mstCodDEntity.getCodValue();
            }
            extensions.put(XApiConstant.EXT_KEY_BLOCK_TYPE_DIV, stuWeeklyPlanPerfEntity.getBlockTypeDiv() + ":" + blockType);
        }
        try {
            XApiUtils.saveStatement(Verbs.canceled(), Activitys.schedule(), extensions);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return R.ok();
    }

    /**
     * スケージュール情報の取得処理を行う
     *
     * @param tgtYmd 対象日
     * @return 計画図エリア情報
     */
    private List<SchdlDto> getSchdlList(Date tgtYmd) {
        // 1.1 対象週開始・終了日の算出
        // 週開始日
        Date monday = DateUtils.getMonday(tgtYmd);
        // 週終了日
        Date sunday = DateUtils.getSunday(tgtYmd);
        //0702  障害票3.5 no7
        monday = DateUtils.addDays(monday,-1);
        // ユーザーID
        String stuId = getUserCd();
        //生徒固定スケジュールと個別スケジュール調整情報の個数の取得
        List<StuFixdSchdlEntity> stuFixdSchdlEntityList = stuFixdSchdlService.list(
                new QueryWrapper<StuFixdSchdlEntity>().eq("stu_id", ShiroUtils.getUserId()).eq("del_flg", 0));
        List<SchdlDto> getSecondDayList = new ArrayList<>();
        if (stuFixdSchdlEntityList.size() != 0) {
            //固定スケジュール設定する場合
            for (StuFixdSchdlEntity stuFixdSchdlEntity : stuFixdSchdlEntityList) {
                //開始時間チェック
                boolean checkStart = false;
                //終了時間チェック
                boolean checkEnd = false;
                //開始時間
                String one = stuFixdSchdlEntity.getBlockStartTime() + "";
                one = one.split(" ")[0];
                //終了時間
                String two = stuFixdSchdlEntity.getBlockEndTime() + "";
                two = two.split(" ")[0];
                //開始時間は天をまたいでチェックしますか
                if (Objects.requireNonNull(DateUtils.parse(one, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS)).compareTo(stuFixdSchdlEntity.getBlockStartDate()) > 0) {
                    checkStart = true;
                }
                //終了時間は天をまたいでチェックしますか
                if (Objects.requireNonNull(DateUtils.parse(two, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS)).compareTo(stuFixdSchdlEntity.getBlockEndDate()) > 0) {
                    checkEnd = true;
                }
                //生徒固定スケジュールと個別スケジュール調整情報の取得
                List<SchdlDto> getSecondDay = commonService.selectFixedBlockList(stuFixdSchdlEntity.getId(), stuId, monday, checkStart, checkEnd);
                getSecondDayList.addAll(getSecondDay);
            }
            //計画ブロックの取得
            List<SchdlDto> getSecondDay = commonService.selectPlanBlockList(stuId, monday, sunday);
            getSecondDayList.addAll(getSecondDay);
        } else {
            //固定スケジュール設定存在しません場合
            List<SchdlDto> selectPlanBlock = commonService.getSchdlList(stuId, monday, sunday);
            getSecondDayList.addAll(selectPlanBlock);
        }
        // 1.1 計画図エリア情報の取得
        return getSecondDayList;
    }

    /**
     * 学習のブロックエリア情報の取得処理
     *
     * @return 学習のブロックエリア情報
     */
    private Map<String, Object> getBlockInfo() {

        // 学習週の取得処理<
        Map<String, Object> seasnMap = f10301Service.getLearnSeasnInfo();
        Map<String, Object> pageInfo = new HashMap<>(seasnMap);

        // 計画学習ブロックの取得  積み残しブロック情報リスト
        Map<Integer, F1030103Dto> plannedBlock = f10301Service.getPlannedBlock();
        if (!plannedBlock.isEmpty()) {
            pageInfo.put("planPerf", plannedBlock);
        }

        // その他ブロックを取得する
        List<F1030101Dto> otherBlockList = f10301Service.getOtherBlock();
        pageInfo.put("otherBlock", otherBlockList);

        // 復習ブロックの取得
        List<F1030101Dto> reviewBlockList = f10301Service.getReviewBlock();

        Map<String, Object> blockInfo;

        // 予習ブロックの取得  no7  0625
//        List<F1030101Dto> previewBlockList = f10301Service.getPreviewBlock();
//        for (F1030101Dto dto:previewBlockList){
//            if (StringUtils.isEmpty(dto.getColorId())){
//                dto.setColorId("white");
//                dto.setFontColor("black");
//            }else {
//                Color color = convertToColor(dto.getColorId());
//                if (color.getRed() + color.getBlue() + color.getGreen() > 650) {
//                    dto.setFontColor("black");
//                } else {
//                    dto.setFontColor("white");
//                }
//            }
//        }
//        reviewBlockList.addAll(previewBlockList);

        // 未計画学習ブロックの取得
        blockInfo = f10301Service.getLearnBlockInfo(StringUtils.defaultString(pageInfo.get("weekStartDay")));
        pageInfo.putAll(blockInfo);

        // 予習,復習ブロック
        pageInfo.put("reviewBlock", reviewBlockList);

        return pageInfo;
    }

    public Color convertToColor(String colorStr) {
        colorStr = colorStr.substring(1);
        return new Color(Integer.parseInt(colorStr, 16));
    }
}