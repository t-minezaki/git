/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.StuFixdSchdlEntity;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.StuFixdSchdlService;
import jp.learningpark.modules.common.utils.dto.SchdlDto;
import jp.learningpark.modules.common.utils.dto.WeekPreNextSeasonDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.service.F20006Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>学習者の進捗一覧画面(ウィークリー)（PC）Controller。</p >
 *
 * @author NWT : yangfan <br />
 * 変更履歴 <br />
 * 2018/10/17 : yangfan: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager")
public class F20006Controller extends AbstractController {

    /**
     * ブロッグマスタ Service
     */
    @Autowired
    private F20006Service f20006Service;

    /**
     * 生徒固定スケジュール設定 Extend Service
     */
    @Autowired
    private CommonService commonService;

    /**
     * 生徒基本マスタ Service
     */
    @Autowired
    private MstStuService mstStuService;

    /**
     * 生徒固定スケジュール設定 Service
     */
    @Autowired
    private StuFixdSchdlService stuFixdSchdlService;

    /**
     * 初期表示
     *
     * @return 画面情報
     */
    @RequestMapping(value = "/F20006/index", method = RequestMethod.GET)
    public R index() {
        // 生徒ID
        String stuId = ShiroUtils.getSessionAttribute("stuId").toString();

        // 画面情報
        Map<String, Object> pageInfo = new HashMap<>(8);

        // 1.1 計画図エリア情報の取得
        pageInfo.put("schdlList", this.getSchdlList(DateUtils.getSysDate(), stuId));

        // 1.2 学習のブロックエリア情報の取得
        pageInfo.put("blockInfo", this.getBlockInfo(DateUtils.getSysDate(), stuId));

        return R.ok().put("pageInfo", pageInfo);
    }

    /**
     * 前週へボタン、次週へボタンの押下処理
     *
     * @param tgtYmd 次週区分
     * @return 画面情報
     */
    @RequestMapping(value = "/F20006/schdlList", method = RequestMethod.GET)
    public R getWeekSchdlList(String tgtYmd) {
        if (StringUtils.isEmpty(tgtYmd)) {
            return R.error(500, MessageUtils.getMessage("MSGCOMN0017"));
        }
        // 生徒ID
        String stuId = ShiroUtils.getSessionAttribute("stuId").toString();

        // 計画図エリア情報の取得処理
        return R.ok().put("schdlList", this.getSchdlList(DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYYMMDD), stuId));
    }

    /**
     * 学習ブロックエリアの前一週と次一週をクリック時、未計画学習ブロックの取得処理
     *
     * @param tgtYmd 対象年月
     * @return 画面情報
     */
    @RequestMapping(value = "/F20006/blockInfo", method = RequestMethod.GET)
    public R getNextWeekBlock(String tgtYmd) {
        Map<String, Object> blockInfo;
        // 生徒ID
        String stuId = ShiroUtils.getSessionAttribute("stuId").toString();
        //メンター名
        String mentorNm = (String)ShiroUtils.getSessionAttribute(GakkenConstant.MENTOR_NM);
        //生徒名
        String stuNm = (String)ShiroUtils.getSessionAttribute(GakkenConstant.STU_NM);

        if (StringUtils.isEmpty(tgtYmd)) {
            blockInfo = this.getBlockInfo(DateUtils.getSysDate(), stuId);
        } else {
            blockInfo = this.getBlockInfo(DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYYMMDD), stuId);
        }

        // 学習のブロックエリア情報の取得処理
        R r = R.ok();
        r.put("blockInfo", blockInfo);
        r.put("mentorNm", mentorNm);
        r.put("stuNm", stuNm);
        return r;
    }

    /**
     * 計画図エリア情報の取得処理
     *
     * @param tgtYmd 対象日
     * @param stuId 生徒
     * @return 計画図エリア情報
     */
    private List<SchdlDto> getSchdlList(Date tgtYmd, String stuId) {
        // 1.1 対象週開始・終了日の算出
        // 週開始日
        Date monday = DateUtils.getMonday(tgtYmd);
        // 週終了日
        Date sunday = DateUtils.getSunday(tgtYmd);

        //0702  障害票3.5 no7
        monday = DateUtils.addDays(monday, -1);
        //生徒固定スケジュールと個別スケジュール調整情報の個数の取得
        List<StuFixdSchdlEntity> stuFixdSchdlEntityList = stuFixdSchdlService.list(new QueryWrapper<StuFixdSchdlEntity>().eq("stu_id", stuId).eq("del_flg", 0));
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
     * @param tgtYmd 対象日
     * @param stuId 生徒ID
     * @return 学習のブロックエリア情報
     */
    private Map<String, Object> getBlockInfo(Date tgtYmd, String stuId) {
        // 画面情報
        Map<String, Object> pageInfo = new HashMap<>(8);
        String mentorId = getUserCd();
        // 今週の計画学習時期を取得する

        MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0));

        //塾学習期間ID
        Integer crmLearnPrdId = 0;
        if (mstStuEntity != null) {
            crmLearnPrdId = commonService.getctCrmLearnPrdIdAfterUpdateSchy(ShiroUtils.getUserEntity().getOrgId(), ShiroUtils.getBrandcd(),
                    mstStuEntity.getSchyDiv());
        }

        // 1.2.1 計画用の学習ブロックの学習ブロックエリアで表示し計画するため、下記計画ブロックを取得する。
        // 今週の計画学習時期を取得する
        WeekPreNextSeasonDto weekPreNextSeasonDto = f20006Service.getWeekPreNextSeason(stuId, mentorId, tgtYmd, crmLearnPrdId);
        if (weekPreNextSeasonDto == null) {
            return R.error(500, MessageUtils.getMessage("MSGCOMN0017"));
        }
        // 今週
        pageInfo.put("currentLbl", weekPreNextSeasonDto.getWeekDisply());
        pageInfo.put("currentYmd", weekPreNextSeasonDto.getTgtYmd());
        // 前週
        pageInfo.put("prevLbl", weekPreNextSeasonDto.getPreWeekDisply());
        pageInfo.put("prevYmd", weekPreNextSeasonDto.getPreWeek());
        // 次週
        pageInfo.put("nextLbl", weekPreNextSeasonDto.getNextWeekDisply());
        pageInfo.put("nextYmd", weekPreNextSeasonDto.getNextWeek());

        // 今週の 学習ブロックを取得する
        pageInfo.put(
                "blockInfo", f20006Service.getWeekkyBlockInfo(stuId, weekPreNextSeasonDto.getLearnSeasnStartDy(), weekPreNextSeasonDto.getLearnSeasnEndDy()));

        return pageInfo;
    }

}
