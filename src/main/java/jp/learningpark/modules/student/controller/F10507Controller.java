/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.service.StuTestGoalResultDService;
import jp.learningpark.modules.common.service.StuTestGoalResultHService;
import jp.learningpark.modules.student.form.F10507Form;
import jp.learningpark.modules.student.service.F10507Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>テスト目標結果一覧 Controller</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/11/15 : wen: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/student/F10507")
public class F10507Controller extends AbstractController {
    /**
     * テスト目標結果一覧Service
     */
    @Autowired
    F10507Service f10507Service;

    /**
     * 生徒テスト目標結果_ヘッダService
     */
    @Autowired
    StuTestGoalResultHService stuTestGoalResultHService;

    /**
     * 生徒テスト目標結果_明細 Service
     */
    @Autowired
    StuTestGoalResultDService stuTestGoalResultDService;

    /**
     * テスト目標結果一覧初期化
     *
     * @param page  当ページ数
     * @param limit 各ページの最大記録数
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f10507init(Integer limit, Integer page, String stuId) {
        //生徒ID
        if (StringUtils.isEmpty(stuId) || StringUtils.equals("undefined", stuId)) {
            stuId = getUserCd();
        }
        //当ページの生徒テスト目標結果Listを取得する
        List<F10507Form> goalResultList = f10507Service.getGoalResultList(stuId, (page - 1) * limit);
        //生徒テスト目標結果Listの長さを取得する
        Integer totalSize = f10507Service.getGoalResultCount(stuId, null);
        return R.ok().put("page", new PageUtils(goalResultList, totalSize, limit, page)).put("stuNm", ShiroUtils.getSessionAttribute(GakkenConstant.STU_NM));
    }

    /**
     * 削除ボタン
     *
     * @param id       id
     * @param updateTm 更新日時
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R deleteBtn(Integer id, String updateTm) {
        return f10507Service.delete(id, updateTm);
    }
}
