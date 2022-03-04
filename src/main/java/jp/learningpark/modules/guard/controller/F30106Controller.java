/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.StuTestGoalResultHService;
import jp.learningpark.modules.guard.dto.F30106Dto;
import jp.learningpark.modules.guard.dto.F30106LowLevDto;
import jp.learningpark.modules.guard.service.F30106Service;
import jp.learningpark.modules.xapi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>定期テスト科目別成績推移画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/02/18 : hujunjie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("guard/F30106/")
public class F30106Controller {
    /**
     * 定期テスト結果確認画面Service
     */
    @Autowired
    F30106Service f30106Service;

    /**
     * 生徒テスト目標結果_ヘッダService
     */
    @Autowired
    StuTestGoalResultHService stuTestGoalResultHService;

    /**
     * 生徒マスタService
     */
    @Autowired
    MstStuService mstStuService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 定期テスト結果確認画面初期化
     * @param url         当画面URL
     * @param testTypeDiv テスト分類区分
     * @param schyDiv     学年区分
     * @param testTgtY    試験年
     * @param testTgtM    試験月
     * @param testKindDiv 試験タイプ
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R f30106init(String url, String testTypeDiv, String schyDiv, Integer testTgtY, Integer testTgtM, String testKindDiv) {
        // 生徒ID取得
        String stuId = (String) ShiroUtils.getSessionAttribute("stuId");
        //テスト分類区分　デフォルト区分：定期テスト
        if (StringUtils.isEmpty(testTypeDiv)) {
            testTypeDiv = "1";
        }
        //学年区分
        if (StringUtils.isEmpty(schyDiv)) {
            schyDiv = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0)).getSchyDiv();
        }

        //生徒学年、学年区分取得
        F30106Dto stuSchy = f30106Service.getStuSchy(stuId);
        //学年リスト取得
        List<F30106Dto> schyList = f30106Service.getSchyList(stuId, testTypeDiv);
        if (schyList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "テスト成績"));
        }
        schyDiv = schyDiv.trim();
        //教科タイトルリスト取得
        List<F30106LowLevDto> subjtTitle = f30106Service.getSubjtList(schyDiv, null);
        if (subjtTitle.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科")).put("schyList", schyList).put("stuSchy", stuSchy);
        }
        List<F30106LowLevDto> subjtTitle1 = f30106Service.getSubjtList(schyDiv, 0);
        List<F30106LowLevDto> subjtTitle2 = f30106Service.getSubjtList(schyDiv, 6);
        List<F30106LowLevDto> subjtTitle3 = f30106Service.getSubjtList(schyDiv, 12);
//        if (subjtTitle2.size() == 0) {
//            return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科")).put("schyList", schyList).put("stuSchy", stuSchy);
//        }

        //テスト種別・テスト対象年月リストの取得
        List<F30106Dto> tgtYMList = f30106Service.getTgtYMList(stuId, testTypeDiv, schyDiv);
        if (tgtYMList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "テスト成績")).put("schyList", schyList).put("stuSchy", stuSchy);
        }
        //最新更新日のデータ取得
        F30106Dto resultPointsNewUpDateTime = f30106Service.getResultPointsNewUpDateTime(stuId, testTypeDiv, schyDiv);
        if (resultPointsNewUpDateTime == null){
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "テスト成績")).put("schyList", schyList).put("stuSchy", stuSchy);
        }

//        List<F30106Dto> pointList;
        List<F30106Dto> pointList1;
        List<F30106Dto> pointList2;
        List<F30106Dto> pointList3;
        if (testTgtY == null || testTgtM == null) {
            //教科別得点平均点順位表取得
//            pointList = f30106Service.getResultPointsArea(stuId, testTypeDiv, schyDiv
//                    , resultPointsNewUpDateTime.getTestKindDiv(), resultPointsNewUpDateTime.getTestTgtY(), resultPointsNewUpDateTime.getTestTgtM(), null);
            pointList1 = f30106Service.getResultPointsArea(stuId, testTypeDiv, schyDiv
                    , resultPointsNewUpDateTime.getTestKindDiv(), resultPointsNewUpDateTime.getTestTgtY(), resultPointsNewUpDateTime.getTestTgtM(), 0);
            pointList2 = f30106Service.getResultPointsArea(stuId, testTypeDiv, schyDiv
                    , resultPointsNewUpDateTime.getTestKindDiv(), resultPointsNewUpDateTime.getTestTgtY(), resultPointsNewUpDateTime.getTestTgtM(), 6);
            pointList3 = f30106Service.getResultPointsArea(stuId, testTypeDiv, schyDiv
                    , resultPointsNewUpDateTime.getTestKindDiv(), resultPointsNewUpDateTime.getTestTgtY(), resultPointsNewUpDateTime.getTestTgtM(), 12);
        } else {
            //教科別得点平均点順位表取得
//            pointList = f30106Service.getResultPointsArea(stuId, testTypeDiv, schyDiv, testKindDiv, testTgtY, testTgtM, null);
            pointList1 = f30106Service.getResultPointsArea(stuId, testTypeDiv, schyDiv, testKindDiv, testTgtY, testTgtM, 0);
            pointList2 = f30106Service.getResultPointsArea(stuId, testTypeDiv, schyDiv, testKindDiv, testTgtY, testTgtM, 6);
            pointList3 = f30106Service.getResultPointsArea(stuId, testTypeDiv, schyDiv, testKindDiv, testTgtY, testTgtM, 12);
        }

        Extensions exts = new Extensions();
        // 利用者のシステムID
        exts.put(XApiConstant.EXT_KEY_USER_ID, ShiroUtils.getUserId());
        // ログイン時間
        exts.put(XApiConstant.EXT_KEY_VISIT_TIME, DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
        // 生徒ID
        exts.put(XApiConstant.EXT_KEY_STU_ID, stuId);
        // 画面URL
        exts.put(XApiConstant.EXT_KEY_URL, url);
        try {
            XApiUtils.saveStatement(Verbs.scored(), Activitys.examination(), exts);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        R info = new R();
        info.put("pointList1", pointList1);
        info.put("pointList2", pointList2);
        info.put("pointList3", pointList3);
        info.put("subjtTitle1", subjtTitle1);
        info.put("subjtTitle2", subjtTitle2);
        info.put("subjtTitle3", subjtTitle3);
        info.put("schyList", schyList);
        info.put("stuSchy", stuSchy);
        info.put("tgtYMList", tgtYMList);
        info.put("resultPointsNewUpDateTime", resultPointsNewUpDateTime);
        return info;
    }
}
