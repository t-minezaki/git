/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.StuTestGoalResultHEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.StuTestGoalResultHService;
import jp.learningpark.modules.student.dto.F10502Dto;
import jp.learningpark.modules.student.service.F10502Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import jp.learningpark.modules.xapi.Activitys;
import jp.learningpark.modules.xapi.Extensions;
import jp.learningpark.modules.xapi.Verbs;
import jp.learningpark.modules.xapi.XApiConstant;
import jp.learningpark.modules.xapi.XApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>F10502 テスト目標結果登録画面</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/21 : gong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/student/F10502")
@RestController
public class F10502Controller extends AbstractController {

    /**
     * コードマスタ_明細 Service
     */
    @Autowired
    private MstCodDService codMstDService;

    /**
     * F10502 テスト目標結果登録画面 Service
     */
    @Autowired
    private F10502Service f10502Service;

    /**
     * 生徒テスト目標結果_ヘッダ Service
     */
    @Autowired
    private StuTestGoalResultHService stuTestGoalResultHService;

    /**
     * 生徒マスタ　Service
     */
    @Autowired
    private MstStuService mstStuService;

    /**
     * 初期表示
     *
     * @param id 引渡データ．ID
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f10502init(Integer id, String stuId) {
        //1.1学年リスト
        List<MstCodDEntity> schyList = codMstDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(wrapper->wrapper.eq("cod_key", "SCHY_DIV").eq("del_flg", 0)).orderByAsc(
                        "sort"));

        //1.2テスト時期リスト
        List<MstCodDEntity> testPrdList = codMstDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(
                wrapper->wrapper.eq("cod_key", "TEST_TYPE_DIV").eq("del_flg", 0)).orderByAsc("sort"));

        //1.3テスト種別リスト
        List<MstCodDEntity> testDivList = codMstDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(
                wrapper->wrapper.eq("cod_key", "TEST_KIND_DIV").eq("del_flg", 0)).orderByAsc("sort"));

        //1.4教科リストを取得し
        List<MstCodDEntity> subjtList = null;
        MstStuEntity mstStuEntity = mstStuService.getOne(
                new QueryWrapper<MstStuEntity>().and(w->w.eq("stu_id",
                        StringUtils.isEmpty(stuId) || StringUtils.equals("undefined",stuId) ? getUserCd() : stuId).eq("del_flg", 0)));

        //学年
        String schy = "";
        //テスト分類
        String testType = "";
        //テスト種別
        String testKind = "";
        if (id != null && id != 0) {
            //生徒テスト目標結果_ヘッダ情報
            StuTestGoalResultHEntity stuTestGoalResultHEntity = stuTestGoalResultHService.getById(id);
            //更新日時String
            String updateTimeString = "";
            if (stuTestGoalResultHEntity.getUpdDatime() != null) {
                updateTimeString = DateUtils.format(stuTestGoalResultHEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
                //学年
                for (int i = 0; i < schyList.size(); i++) {
                    if (StringUtils.equals(stuTestGoalResultHEntity.getSchyDiv(), schyList.get(i).getCodCd())) {
                        schy = schyList.get(i).getCodValue();
                    }
                }
                //テスト分類
                for (int i = 0; i < testPrdList.size(); i++) {
                    if (StringUtils.equals(stuTestGoalResultHEntity.getTestTypeDiv(), testPrdList.get(i).getCodCd())) {
                        testType = testPrdList.get(i).getCodValue();
                    }
                }
                //テスト種別
                for (int i = 0; i < testDivList.size(); i++) {
                    if (StringUtils.equals(stuTestGoalResultHEntity.getTestKindDiv(), testDivList.get(i).getCodCd())) {
                        testKind = testDivList.get(i).getCodValue();
                    }
                }
            }
            subjtList = f10502Service.getTestSubjt(StringUtils.trim(mstStuEntity.getSchyDiv()));
            for (MstCodDEntity entity : subjtList) {
                //コードマスタ・画像パス
                if (StringUtils.isEmpty(entity.getCodValue3())) {
                    entity.setCodValue3("../img/logo2.png");
                }
                if (StringUtils.isEmpty(entity.getCodValue2())) {
                    entity.setCodValue2("#00AA71");
                }
            }
            //生徒テスト目標結果_明細情報List
            List<F10502Dto> stuTestGoalResultDEntityList = f10502Service.getWithCodById(id, stuTestGoalResultHEntity.getSchyDiv());
            for (F10502Dto dto : stuTestGoalResultDEntityList) {
                //コードマスタ・画像パス
                if (StringUtils.isEmpty(dto.getCodValue3())) {
                    dto.setCodValue3("../img/logo2.png");
                }
                if (StringUtils.isEmpty(dto.getCodValue2())) {
                    dto.setCodValue2("#00AA71");
                }
            }
            return R.ok().put("schyList", schyList).put("testPrdList", testPrdList).put("testDivList", testDivList).put(
                    "stuTextH", stuTestGoalResultHEntity).put("stuTextDList", stuTestGoalResultDEntityList).put("updateTimeString", updateTimeString).put(
                    "schyDiv", stuTestGoalResultHEntity.getSchyDiv()).put("schy", schy).put("testType", testType).put("testKind", testKind).put("subjtList",subjtList);
        } else {
            //学年
            for (int i = 0; i < schyList.size(); i++) {
                if (StringUtils.equals(StringUtils.trim(mstStuEntity.getSchyDiv()), schyList.get(i).getCodCd())) {
                    schy = schyList.get(i).getCodValue();
                    //学年区分
                    String schyDiv = schyList.get(i).getCodCd();
                    subjtList = f10502Service.getTestSubjt(schyDiv);
                    break;
                }
            }
            for (MstCodDEntity entity : subjtList) {
                //コードマスタ・画像パス
                if (StringUtils.isEmpty(entity.getCodValue3())) {
                    entity.setCodValue3("../img/logo2.png");
                }
                if (StringUtils.isEmpty(entity.getCodValue2())) {
                    entity.setCodValue2("#00AA71");
                }
            }
            return R.ok().put("schyList", schyList).put("testPrdList", testPrdList).put("testDivList", testDivList).put("schyDiv",
                    mstStuEntity.getSchyDiv()).put("subjtList", subjtList).put("schy", schy).put("testType", testType).put("testKind", testKind);
        }

    }

    /**
     * POP初期表示
     *
     * @param codCd 引渡データ．テスト分類区分
     * @return
     */
    @RequestMapping(value = "/popGetInfo", method = RequestMethod.GET)
    public R f10502Pop(String codCd) {
        //1.3テスト種別リスト
        List<MstCodDEntity> testDivList = codMstDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(
                wrapper->wrapper.eq("cod_key", "TEST_KIND_DIV").eq("del_flg", 0).likeRight("cod_cd", codCd)).orderByAsc("sort"));

        //選択肢はシステム日付から以降10年
        List<Integer> yearList = new ArrayList<>();
        Integer currentYear = Integer.parseInt(DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY));
        for (int i = 0; i <= 10; i++) {
            yearList.add(currentYear + i);
        }
        return R.ok().put("testDivList", testDivList).put("yearList", yearList);
    }

    /**
     * POP初期表示
     *
     * @param schyDiv 学年区分
     * @return
     */
    @RequestMapping(value = "/popGetSubjtList", method = RequestMethod.GET)
    public R f10502PopGetSubjtList(String schyDiv) {
        //1.3テスト種別リスト
        List<MstCodDEntity> subjtList = f10502Service.getTestSubjt(schyDiv);
        return R.ok().put("subjtList", subjtList);
    }

    /**
     * 登録処理
     *
     * @param dtos  画面情報
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@RequestBody List<F10502Dto> dtos) {
        R r = f10502Service.update(dtos);

        //ビッグデータ
        Extensions extensions = new Extensions();
        //利用者のシステムID
        extensions.put(XApiConstant.EXT_KEY_USER_ID, ShiroUtils.getUserId());
        //学年
        MstCodDEntity mstCodDEntity = codMstDService.getOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "SCHY_DIV").eq("cod_cd", dtos.get(0).getSchyDiv()).eq("del_flg", 0));
        String schy = "";
        if (mstCodDEntity != null) {
            schy = mstCodDEntity.getCodValue();
        }
        extensions.put(XApiConstant.EXT_KEY_SCHY, dtos.get(0).getSchyDiv() + ":" + schy);
        //テスト分類区分
        mstCodDEntity = codMstDService.getOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "TEST_TYPE_DIV").eq("cod_cd", dtos.get(0).getSchyDiv()).eq("del_flg", 0));
        String testType = "";
        if (mstCodDEntity != null) {
            testType = mstCodDEntity.getCodValue();
        }
        extensions.put(XApiConstant.EXT_KEY_TEST_TYPE_DIV, dtos.get(0).getTestTypeDiv() + ":" + testType);
        //テスト種別区分
        mstCodDEntity = codMstDService.getOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "TEST_KIND_DIV").eq("cod_cd", dtos.get(0).getTestKindDiv()).eq("del_flg",
                        0));
        String testKind = "";
        if (mstCodDEntity != null) {
            testKind = mstCodDEntity.getCodValue();
        }
        extensions.put(XApiConstant.EXT_KEY_TEST_KIND_DIV, dtos.get(0).getTestTypeDiv() + ":" + testKind);
        //テスト対象年
        extensions.put(XApiConstant.EXT_KEY_TEST_TGT_Y, dtos.get(0).getTestTgtY());
        //テスト対象月
        extensions.put(XApiConstant.EXT_KEY_TEST_TGT_M, dtos.get(0).getTestTgtM());
        //テスト教科別成績
        JsonArray array = new JsonArray();
        JsonObject jsonObject = null;
        for (F10502Dto dto : dtos) {
            jsonObject = new JsonObject();
            //教科区分
            mstCodDEntity = codMstDService.getOne(
                    new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "TEST_SUBJT_DIV").eq("cod_cd", dto.getSubjtDiv()).eq("cod_value_5",
                            dtos.get(0).getSchyDiv()).eq("del_flg", 0));
            String subjt = "";
            if (mstCodDEntity != null) {
                subjt = mstCodDEntity.getCodValue();
            }
            jsonObject.addProperty(XApiConstant.EXT_KEY_TEST_SUBJT_DIV, dto.getSchyDiv() + ":" + subjt);
            //目標点数
            jsonObject.addProperty(XApiConstant.EXT_KEY_GOAL_POINTS, dto.getGoalPoints());
            //結果点数
            jsonObject.addProperty(XApiConstant.EXT_KEY_RESULT_POINTS, dto.getResultPoints());
            array.add(jsonObject);
        }
        //テスト教科別成績
        extensions.put(XApiConstant.EXT_KEY_TEST_SUBJT_POINTS, array);
        try {
            XApiUtils.saveStatement(Verbs.scored(), Activitys.examination(), extensions);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return r;
    }
}
