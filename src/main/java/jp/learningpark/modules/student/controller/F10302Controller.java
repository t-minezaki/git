/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.StuFixdSchdlEntity;
import jp.learningpark.modules.common.service.MstColorBlockService;
import jp.learningpark.modules.common.service.StuFixdSchdlService;
import jp.learningpark.modules.common.utils.dto.SchdlDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.student.service.F10301Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 * F10302_ウィークリープラン実績管理画面 Controllerです。
 * </p>
 *
 * @author NWT : gaoli <br />
 * 変更履歴 <br />
 * 2018/10/09 : gaoli: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/student")
public class F10302Controller extends AbstractController {

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
     * 生徒固定スケジュール設定 Service
     */
    @Autowired
    private StuFixdSchdlService stuFixdSchdlService;

    /**
     *
     */
    @Autowired
    private MstColorBlockService mstColorBlockService;

    /**
     * 前週へボタン、次週へボタンの押下処理
     *
     * @return 画面情報
     */
    @RequestMapping(value = "/F10302/schdlList", method = RequestMethod.GET)
    public R getWeekSchdlList(String tgtYmd) {
        if (StringUtils.isEmpty(tgtYmd)) {
            return R.error(500, MessageUtils.getMessage("MSGCOMN0017"));
        }

        List<SchdlDto> schdlDtos = this.getSchdlList(DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYYMMDD));
//        MstColorBlockEntity color = null;
//        List<MstColorBlockEntity> colorList = new ArrayList<>();
//        for (SchdlDto schdl : schdlDtos) {
//            color = mstColorBlockService.getOne(
//                    new QueryWrapper<MstColorBlockEntity>().select("color_id").eq("stu_id", ShiroUtils.getUserId()).eq("block_type_div",
//                            schdl.getBlockTypeDiv()).eq("del_flg", 0));
//            colorList.add(color);
//        }
        // 計画図エリア情報の取得処理
        return R.ok().put("schdlList", schdlDtos);
    }

    /**
     * 学習ブロックエリアの前N週と次Ｎ週をクリック時、未計画学習ブロックの取得処理
     *
     * @return 画面情報
     */
    @RequestMapping(value = "/F10302/blockInfo", method = RequestMethod.GET)
    public R getNextMonthBlock() {
        Map<String, Object> blockInfo = this.getBlockInfo();
        // 学習のブロックエリア情報の取得処理
        R r = R.ok();
        r.put("blockInfo", blockInfo);
        r.put("stuNm", ShiroUtils.getSessionAttribute(GakkenConstant.STU_NM));
        return r;
    }

    /**
     * 計画図エリア情報の取得処理
     *
     * @param tgtYmd 対象日
     * @return 計画図エリア情報
     */
    private List<SchdlDto> getSchdlList(Date tgtYmd) {
        // 1.1 対象週開始・終了日の算出
        // 週開始日
        Date monday = DateUtils.getMonday(tgtYmd);
        monday = DateUtils.addDays(monday,-1);
        // 週終了日
        Date sunday = DateUtils.getSunday(tgtYmd);
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

        // 未計画学習ブロックの取得
        Map<String, Object> blockInfo = f10301Service.getLearnBlock();
        pageInfo.putAll(blockInfo);

        return pageInfo;
    }

}


