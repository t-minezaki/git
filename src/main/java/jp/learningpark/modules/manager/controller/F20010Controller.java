/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.StuTestGoalResultHService;
import jp.learningpark.modules.manager.dto.F20010Dto;
import jp.learningpark.modules.manager.dto.F20010LowLevDto;
import jp.learningpark.modules.manager.service.F20010Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>定期テスト科目別成績推移画面（PC）</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/02/18 : hujunjie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("manager/F20010/")
public class F20010Controller extends AbstractController {
    /**
     * 定期テスト結果確認画面Service
     */
    @Autowired
    F20010Service f20010Service;

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

    /**
     * 定期テスト結果確認画面初期化
     *
     * @param testTypeDiv テスト分類区分
     * @param schyDiv     学年区分
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R f20010init(String testTypeDiv, String schyDiv) {
        // 生徒ID取得
        String stuId = (String) ShiroUtils.getSessionAttribute("stuId");
        //生徒名
        String stuNm = (String) ShiroUtils.getSessionAttribute(GakkenConstant.STU_NM);
        //メンター名
        String mentorNm = (String) ShiroUtils.getSessionAttribute(GakkenConstant.MENTOR_NM);
        //テスト分類区分　デフォルト区分：定期テスト
        if (StringUtils.isEmpty(testTypeDiv)) {
            testTypeDiv = "1";
        }
        //学年区分
        if (StringUtils.isEmpty(schyDiv)) {
            schyDiv = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0)).getSchyDiv();
        }

        //生徒学年、学年区分取得
        F20010Dto stuSchy = f20010Service.getStuSchy(stuId);
        //学年リスト取得
        List<F20010Dto> schyList = f20010Service.getSchyList(stuId, testTypeDiv);
        if (schyList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "テスト成績")).put("stuNm", stuNm);
        }

        //教科タイトルリスト取得
        List<F20010LowLevDto> subjtTitle = f20010Service.getSubjtList(schyDiv);
        if (subjtTitle.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科")).put("schyList", schyList).put("stuSchy", stuSchy).put("stuNm", stuNm);
        }

        //教科別得点平均点順位表取得
        List<F20010Dto> pointList = f20010Service.getResultPointsArea(stuId, testTypeDiv, schyDiv);
        if (pointList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "テスト成績")).put("schyList", schyList).put("stuSchy", stuSchy).put("stuNm", stuNm);
        }

        R info = new R();
        info.put("pointList", pointList);
        info.put("subjtTitle", subjtTitle);
        info.put("schyList", schyList);
        info.put("stuSchy", stuSchy);
        info.put("mentorNm", mentorNm);
        info.put("stuNm", stuNm);
        return info;
    }
}
