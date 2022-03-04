/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstLearnSeasnEntity;
import jp.learningpark.modules.common.entity.StuTermPlanEntity;
import jp.learningpark.modules.common.service.MstLearnSeasnService;
import jp.learningpark.modules.common.service.StuTermPlanService;
import jp.learningpark.modules.manager.dto.F20003BnumLearnLevDto;
import jp.learningpark.modules.manager.dto.F20003Dto;
import jp.learningpark.modules.manager.service.F20003Service;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F20003Conrtoller</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/09/20 : hujunjie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F20003")
public class F20003Controller extends AbstractController {

    /**
     * 学習時期マスタ Service
     */
    @Autowired
    MstLearnSeasnService learnSeasnMstService;

    /**
     * 生徒タームプラン Service
     */
    @Autowired
    StuTermPlanService stuTermPlanService;

    /**
     * 生徒タームプラン設定 Extend Service
     */
    @Autowired
    F20003Service f20003Service;

    /**
     * 　教科別のタームプラン取得
     *
     * @return
     */
    @RequestMapping(value = "/getSubjtPlanTimeInit", method = RequestMethod.GET)
    public R getSubjtTimePlanInit() {
        // 生徒ID
        String stuId = ShiroUtils.getSessionAttribute("stuId").toString();
        //塾Id
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //塾学習期間ID
        Integer crmLearnPrdId = (Integer)ShiroUtils.getSessionAttribute(GakkenConstant.CRM_LEARN_PRD_ID);
        //生徒名
        String stuNm = (String)ShiroUtils.getSessionAttribute(GakkenConstant.STU_NM);
        //メンター名
        String mentorNm = (String)ShiroUtils.getSessionAttribute(GakkenConstant.MENTOR_NM);

        try {
            // 生徒基本情報
            F20003Dto stuTextbDto = f20003Service.getStuInfoByStuId(stuId, orgId);
            if (stuTextbDto == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒基本マスタ"));
            }
            // 生徒選択教科
            List<F20003Dto> stuTextchocExtendDtoList = f20003Service.getStuTextchocList(stuId, crmLearnPrdId);
            if (stuTextchocExtendDtoList == null || stuTextchocExtendDtoList.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒教科書選択管理マスタ")).put("stuInfo", stuTextbDto).put("stuNm", stuNm).put(
                        "mentorNm", mentorNm);
            }
            return R.ok().put("stuInfo", stuTextbDto).put("textchocList", stuTextchocExtendDtoList).put("stuNm", stuNm).put("mentorNm", mentorNm);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.error();
        }
    }

    /**
     * 教科別のタームプラン取得
     *
     * @param subjtDiv 教科区分
     * @param textbId
     * @param crmschId
     * @return
     */
    @RequestMapping(value = "/getSubjtPlanTime", method = RequestMethod.GET)
    public R getSubjtTimePlan(String subjtDiv, Integer textbId, String crmschId) {
        // 生徒ID
        String stuId = ShiroUtils.getSessionAttribute("stuId").toString();
        //塾学習期間ID
        Integer crmLearnPrdId = (Integer)ShiroUtils.getSessionAttribute(GakkenConstant.CRM_LEARN_PRD_ID);
        Map<String, Object> map = new HashMap<>();
        try {
            // 生徒ID
            map.put("stuId", stuId);
            // 教科書ID
            map.put("crmschId", crmschId);
            // 学習週取得
            map.put("subjtDiv", subjtDiv);

            //1.3 　学習週選択のドロップダウンボックス内容を取得し当生徒当教科の学習時期を変更できるため、
            List<MstLearnSeasnEntity> learnSeasnMstEntityList = learnSeasnMstService.list(
                    new QueryWrapper<MstLearnSeasnEntity>().and(w->w.eq("crm_learn_prd_id", crmLearnPrdId).eq("del_flg", 0)));
            List<F20003Dto> learnSeasnExtendList = new ArrayList<>();
            if (learnSeasnMstEntityList == null || learnSeasnMstEntityList.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "学習時期マスタ"));
            }
            F20003Dto dto;
            for (MstLearnSeasnEntity entity : learnSeasnMstEntityList) {
                dto = new F20003Dto();
                dto.setLearnSeasnStartDyDisply(DateUtils.format(entity.getLearnSeasnStartDy(), GakkenConstant.DATE_FORMAT_M_D_SLASH + "～"));
                dto.setId(entity.getId());
                dto.setCrmLearnPrdId(entity.getCrmLearnPrdId());
                dto.setLearnSeasnStartDy(entity.getLearnSeasnStartDy());
                dto.setLearnSeasnEndDy(entity.getLearnSeasnEndDy());
                learnSeasnExtendList.add(dto);
            }

            // 生徒選択された教科明細
            List<F20003Dto> termPlanExtendDtoList = f20003Service.getDefaultAndTermPlan(crmLearnPrdId, stuId, textbId);
            if (termPlanExtendDtoList == null || termPlanExtendDtoList.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科書デフォルトターム情報"));
            }

            Extensions exts = new Extensions();
            JsonArray array = new JsonArray();
            JsonObject jsonObject = new JsonObject();
            for (int i = 0; i < termPlanExtendDtoList.size(); i++) {
                jsonObject = new JsonObject();
                //教科区分
                jsonObject.addProperty(XApiConstant.EXT_KEY_SUBJT_DIV, termPlanExtendDtoList.get(i) == null ? "" : termPlanExtendDtoList.get(i).getSubjtDiv());
                //単元ID
                jsonObject.addProperty(XApiConstant.EXT_KEY_UNIT_ID,
                        termPlanExtendDtoList.get(i) == null ? "" : StringUtils.defaultString(termPlanExtendDtoList.get(i).getUnitId()));
                //単元NO
                jsonObject.addProperty(XApiConstant.EXT_KEY_UNIT_NO, termPlanExtendDtoList.get(i) == null ? "" : termPlanExtendDtoList.get(i).getUnitNo());
                //時期
                MstLearnSeasnEntity mstLearnSeasnEntity = learnSeasnMstService.getById(termPlanExtendDtoList.get(i).getPlanLearnSeasnId());
                jsonObject.addProperty(XApiConstant.EXT_KEY_PLAN_LEARN_SEASN,
                        mstLearnSeasnEntity == null ? "" : DateUtils.format(mstLearnSeasnEntity.getLearnSeasnStartDy(),
                                GakkenConstant.DATE_FORMAT_M_D_SLASH) + "～");
                //目標学習時間(分)
                StuTermPlanEntity stuTermPlanEntity = stuTermPlanService.getById(termPlanExtendDtoList.get(i).getId());
                jsonObject.addProperty(
                        XApiConstant.EXT_KEY_PLAN_LEARN_TM, stuTermPlanEntity == null ? "" : StringUtils.defaultString(stuTermPlanEntity.getPlanLearnTm()));
                //メンターID
                jsonObject.addProperty(XApiConstant.EXT_KEY_MENTOR_ID, ShiroUtils.getUserId());
                //生徒ID
                jsonObject.addProperty(XApiConstant.EXT_KEY_STU_ID, stuId);
                array.add(jsonObject);
            }
            exts.put(XApiConstant.EXT_KEY_TERM_PLAN_INIT, array);
            XApiUtils.saveStatement(Verbs.launched(), Activitys.schedule(), exts);

            //Distinct
            Map<String, F20003Dto> textbDefMap = new HashMap<>();
            F20003BnumLearnLevDto dto1 = null;
            for (int i = 0; i < termPlanExtendDtoList.size(); i++) {
                if (textbDefMap.get(
                        "" + termPlanExtendDtoList.get(i).getUnitId() + termPlanExtendDtoList.get(i).getPlanLearnSeasnId() + termPlanExtendDtoList.get(
                                i).getTextbDefUnitId()) == null) {
                    textbDefMap.put(
                            "" + termPlanExtendDtoList.get(i).getUnitId() + termPlanExtendDtoList.get(i).getPlanLearnSeasnId() + termPlanExtendDtoList.get(
                                    i).getTextbDefUnitId(), termPlanExtendDtoList.get(i));
                    int n = termPlanExtendDtoList.get(i).getF20003BnumLearnLevDtoList().size();
                    F20003Dto termPlanExtendDto = textbDefMap.get(
                            "" + termPlanExtendDtoList.get(i).getUnitId() + termPlanExtendDtoList.get(i).getPlanLearnSeasnId() + termPlanExtendDtoList.get(
                                    i).getTextbDefUnitId());
                    for (int m = 0; m < n; m++) {
                        F20003BnumLearnLevDto f20003BnumLearnLevDto = termPlanExtendDtoList.get(i).getF20003BnumLearnLevDtoList().get(m);
                        f20003BnumLearnLevDto.setIsEmpty(false);
                        //計画学習時間>15
                        for (int j = 0; j < (int)Math.ceil(f20003BnumLearnLevDto.getPlanLearnTm() / 15) - 1; j++) {
                            f20003BnumLearnLevDto.setPlanLearnTm(15);
                            dto1 = new F20003BnumLearnLevDto();
                            dto1.setIsEmpty(false);
                            //計画学習時間
                            dto1.setPlanLearnTm(15);
                            //計画登録フラグ
                            dto1.setPlanRegFlg(f20003BnumLearnLevDto.getPlanRegFlg());
                            //枝番
                            dto1.setBnum(f20003BnumLearnLevDto.getBnum());
                            //学習理解度
                            dto1.setLearnLevUnds(f20003BnumLearnLevDto.getLearnLevUnds());
                            //マスを追加する
                            termPlanExtendDto.getF20003BnumLearnLevDtoList().add(dto1);
                        }
                    }
                    Collections.sort(termPlanExtendDto.getF20003BnumLearnLevDtoList(), new Comparator<F20003BnumLearnLevDto>() {
                        @Override
                        public int compare(F20003BnumLearnLevDto o2, F20003BnumLearnLevDto o1) {
                            int i = 0, j = 0;
                            if (StringUtils.isEmpty(o2.getLearnLevUnds())) {
                                i = -1;
                            } else {
                                i = Integer.parseInt(o2.getLearnLevUnds());
                            }
                            if (StringUtils.isEmpty(o1.getLearnLevUnds())) {
                                j = -1;
                            } else {
                                j = Integer.parseInt(o1.getLearnLevUnds());
                            }
                            return j - i;
                        }
                    });
                } else {
                    F20003Dto termPlanExtendDto = textbDefMap.get(
                            "" + termPlanExtendDtoList.get(i).getUnitId() + termPlanExtendDtoList.get(i).getPlanLearnSeasnId() + termPlanExtendDtoList.get(
                                    i).getTextbDefUnitId());
                    int n = termPlanExtendDtoList.get(i).getF20003BnumLearnLevDtoList().size();
                    for (int m = 0; m < n; m++) {
                        F20003BnumLearnLevDto f20003BnumLearnLevDto = termPlanExtendDtoList.get(i).getF20003BnumLearnLevDtoList().get(m);
                        f20003BnumLearnLevDto.setIsEmpty(false);
                        //計画学習時間>15
                        for (int j = 0; j < (int)Math.ceil(f20003BnumLearnLevDto.getPlanLearnTm() / 15); j++) {
                            //f20003BnumLearnLevDto.setPlanLearnTm(15);
                            dto1 = new F20003BnumLearnLevDto();
                            dto1.setIsEmpty(false);
                            //計画学習時間
                            dto1.setPlanLearnTm(15);
                            //計画登録フラグ
                            dto1.setPlanRegFlg(f20003BnumLearnLevDto.getPlanRegFlg());
                            //枝番
                            dto1.setBnum(f20003BnumLearnLevDto.getBnum());
                            //学習理解度
                            dto1.setLearnLevUnds(f20003BnumLearnLevDto.getLearnLevUnds());
                            //マスを追加する
                            termPlanExtendDto.getF20003BnumLearnLevDtoList().add(dto1);
                        }
                    }
                    Collections.sort(termPlanExtendDto.getF20003BnumLearnLevDtoList(), new Comparator<F20003BnumLearnLevDto>() {
                        @Override
                        public int compare(F20003BnumLearnLevDto o2, F20003BnumLearnLevDto o1) {
                            int i = 0, j = 0;
                            if (StringUtils.isEmpty(o2.getLearnLevUnds())) {
                                i = -1;
                            } else {
                                i = Integer.parseInt(o2.getLearnLevUnds());
                            }
                            if (StringUtils.isEmpty(o1.getLearnLevUnds())) {
                                j = -1;
                            } else {
                                j = Integer.parseInt(o1.getLearnLevUnds());
                            }
                            return j - i;
                        }
                    });
                }
            }

            //0408
            List<F20003Dto> retuList = new ArrayList<>();
            for (Map.Entry<String, F20003Dto> entry : textbDefMap.entrySet()) {
                retuList.add(entry.getValue());
            }

            //表示順番
            Collections.sort(retuList, new Comparator<F20003Dto>() {
                @Override
                public int compare(F20003Dto o2, F20003Dto o1) {
                    return (o2.getDispyOrder() != null ? o2.getDispyOrder() : 0) - (o1.getDispyOrder() != null ? o1.getDispyOrder() : 0);
                }
            });

            //tdのID生成する
            for (int i = 0; i < retuList.size(); i++) {
                F20003Dto termPlanExtendDto = retuList.get(i);
                //生徒タームプラン設定てから
                int size = termPlanExtendDto.getF20003BnumLearnLevDtoList().size();
                //青の判断
                boolean blueFlg = true;
                //selectの判断
                boolean selectFlg = true;
                for (int m = 0; m < termPlanExtendDto.getF20003BnumLearnLevDtoList().size(); m++) {
                    F20003BnumLearnLevDto f20003BnumLearnLevDto = termPlanExtendDto.getF20003BnumLearnLevDtoList().get(m);
                    f20003BnumLearnLevDto.setId((i) + "_" + m);
                    if (StringUtils.equals("0", f20003BnumLearnLevDto.getLearnLevUnds()) || StringUtils.equals(
                            "-2", f20003BnumLearnLevDto.getLearnLevUnds()) || StringUtils.equals("4",
                            f20003BnumLearnLevDto.getLearnLevUnds()) || StringUtils.equals(null, f20003BnumLearnLevDto.getLearnLevUnds())) {
                        blueFlg = false;
                    }
                    if ((f20003BnumLearnLevDto.getLearnLevUnds() != null && !StringUtils.equals(
                            f20003BnumLearnLevDto.getLearnLevUnds(), "-2")) || StringUtils.equals(f20003BnumLearnLevDto.getPlanRegFlg(), "1")) {
                        //非活性
                        selectFlg = false;
                    }
                }
                termPlanExtendDto.setBlueFlg(blueFlg);
                termPlanExtendDto.setSelectFlg(selectFlg);
                for (int o = 0; o < 8 - size; o++) {
                    F20003BnumLearnLevDto f20003BnumLearnLevDto = new F20003BnumLearnLevDto();
                    f20003BnumLearnLevDto.setId((i) + "_" + (o + size));
                    f20003BnumLearnLevDto.setIsEmpty(true);
                    termPlanExtendDto.getF20003BnumLearnLevDtoList().add(f20003BnumLearnLevDto);
                }
            }
            return R.ok().put("learnSeasnList", learnSeasnExtendList).put("termPlanList", retuList).put("listSize", retuList.size());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.error();
        }
    }

    /**
     * <p>登録処理</p>
     *
     * @param dtos
     * @return
     */
    @RequestMapping(value = "/addPlanTime", method = RequestMethod.POST)
    public R update(@RequestBody List<F20003Dto> dtos) throws Exception {

        try {
            Extensions exts = new Extensions();
            JsonArray array = new JsonArray();
            JsonObject jsonObject = new JsonObject();
            for (int i = 0; i < dtos.size(); i++) {
                jsonObject = new JsonObject();
                //教科区分
                jsonObject.addProperty(XApiConstant.EXT_KEY_SUBJT_DIV, dtos.get(i) == null ? "" : dtos.get(i).getSubjtDiv());
                //単元ID
                jsonObject.addProperty(XApiConstant.EXT_KEY_UNIT_ID, dtos.get(i) == null ? "" : StringUtils.defaultString(dtos.get(i).getUnitId()));
                //単元NO
                jsonObject.addProperty(XApiConstant.EXT_KEY_UNIT_NO, dtos.get(i).getUnitNo() == null ? "" : dtos.get(i).getUnitNo());
                //時期
                MstLearnSeasnEntity mstLearnSeasnEntity = learnSeasnMstService.getById(dtos.get(i).getPlanLearnSeasnId());
                jsonObject.addProperty(XApiConstant.EXT_KEY_PLAN_LEARN_SEASN,
                        mstLearnSeasnEntity == null ? "" : DateUtils.format(mstLearnSeasnEntity.getLearnSeasnStartDy(),
                                GakkenConstant.DATE_FORMAT_M_D_SLASH) + "～");
                //目標学習時間(分)
                StuTermPlanEntity stuTermPlanEntity = stuTermPlanService.getById(dtos.get(i).getId());
                jsonObject.addProperty(
                        XApiConstant.EXT_KEY_PLAN_LEARN_TM, stuTermPlanEntity == null ? "" : StringUtils.defaultString(stuTermPlanEntity.getPlanLearnTm()));
                //メンターID
                jsonObject.addProperty(XApiConstant.EXT_KEY_MENTOR_ID, ShiroUtils.getUserId());
                //生徒ID
                jsonObject.addProperty(XApiConstant.EXT_KEY_STU_ID, ShiroUtils.getSessionAttribute("stuId").toString());
                array.add(jsonObject);
            }
            exts.put(XApiConstant.EXT_KEY_TERM_PLAN_LOGIN, array);
            XApiUtils.saveStatement(Verbs.scheduled(), Activitys.schedule(), exts);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return f20003Service.update(dtos);
    }
}
