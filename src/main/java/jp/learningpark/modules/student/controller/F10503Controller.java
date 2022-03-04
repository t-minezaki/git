package jp.learningpark.modules.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.StuTestGoalResultHService;
import jp.learningpark.modules.student.dto.F10503Dto;
import jp.learningpark.modules.student.form.F10503Form;
import jp.learningpark.modules.student.service.F10503Service;
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

import java.util.List;

/**
 * <p>定期テスト結果確認画面 Controller</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/13 : hujunjie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("student/F10503/")
public class F10503Controller extends AbstractController {
    /**
     * 定期テスト結果確認画面Service
     */
    @Autowired
    F10503Service f10503Service;

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
     * @param schyDiv 学年区分
     * @param url 当画面URL
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R f10503init(String testTypeDiv, String schyDiv, String url, String stuId) {
        // 生徒ID取得
        if (StringUtils.isEmpty(stuId)||StringUtils.equals(stuId,"undefined")) {
            stuId = getUserCd();
        }
        //テスト分類区分　デフォルト区分：定期テスト
        if (StringUtils.isEmpty(testTypeDiv)) {
            testTypeDiv = "1";
        }
        //学年区分
        if (StringUtils.isEmpty(schyDiv)) {
            schyDiv = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0)).getSchyDiv();
        }

        //生徒学年、学年区分取得
        F10503Form stuSchy = f10503Service.getStuSchy(stuId);
        //学年リスト取得
        List<F10503Form> schyList = f10503Service.getSchyList(stuId, testTypeDiv);
        if (schyList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "テスト成績"));
        }

        //教科タイトルリスト取得
        List<F10503Dto> subjtTitle = f10503Service.getSubjtList(StringUtils.trim(schyDiv));
        if (subjtTitle.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科")).put("schyList", schyList).put("stuSchy", stuSchy);
        }

        //教科別得点平均点順位表取得
        List<F10503Form> pointList = f10503Service.getResultPointsArea(stuId, testTypeDiv, StringUtils.trim(schyDiv));
        if (pointList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "テスト成績")).put("schyList", schyList).put("stuSchy", stuSchy);
        }

        //ビッグデータ
        if (url != null) {
            Extensions extensions = new Extensions();
            //利用者のシステムID
            extensions.put(XApiConstant.EXT_KEY_USER_ID, ShiroUtils.getUserId());
            //当画面URL
            extensions.put(XApiConstant.EXT_KEY_URL, url);
            //当画面訪問時間
            extensions.put(XApiConstant.EXT_KEY_VISIT_TIME, DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS));
            XApiUtils.saveStatement(Verbs.scored(), Activitys.examination(), extensions);
        }

        R info = new R();
        info.put("pointList", pointList);
        info.put("subjtTitle", subjtTitle);
        info.put("schyList", schyList);
        info.put("stuSchy", stuSchy);
        return info;
    }
}
