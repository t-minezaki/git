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
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstTextbEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.student.dto.F10003Dto;
import jp.learningpark.modules.student.service.F10003Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F10003Service 生徒教科書選択画面</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/10/08 : gong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/student/F10003")
public class F10003Controller extends AbstractController {

    /**
     * CodMstD Service
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * F10003 Services
     */
    @Autowired
    private F10003Service f10003Service;

    /**
     * <p>初期表示</p>
     * <p>教科書選択情報を入手する</p>
     *
     * @return R 画面初期情報
     */
    @RequestMapping(value = "/getTextbChocInfo", method = RequestMethod.GET)
    public R getTextbChocInfo() {
        //生徒当学年の教科ごとの学校と塾の教科書list
        List<F10003Dto> f10003DtoList;
        //生徒当学年選択の教科書情報
        List<MstTextbEntity> texmstEntityList;
        //生徒情報
        String stuId = getUserCd();
        //塾Id
        String orgId = ShiroUtils.getUserEntity().getOrgId();

        //マスタ存在チェック
        List<MstCodDEntity> codMstDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").orderByAsc("sort").and(w -> w.eq("cod_key", "SUBJT_DIV").eq("del_flg", 0)));
        if (codMstDEntityList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "コードマスタの教科"));
        }

        //塾学習期間ID
        Integer crmLearnPrdId = (Integer) ShiroUtils.getSessionAttribute(GakkenConstant.CRM_LEARN_PRD_ID);

        try {
            //当生徒当学年の教科ごとの学校と塾の教科書情報を取得し教科書選択一覧エリアに表示する。
            f10003DtoList = f10003Service.getTextbDtoListOfSchByStuId(stuId, orgId);
            if (f10003DtoList.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科"));
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("stuId", stuId);
                map.put("schyDiv", f10003DtoList.get(0).getSchyDiv());
                map.put("orgId", orgId);
                //塾学習期間ID
                map.put("crmLearnPrdId", crmLearnPrdId);
                //教科ごとの生徒選択した教科書リストを取得し画面．教科書選択リストボックスで表示する。
                texmstEntityList = f10003Service.getTextbListOfStuByStuIdAndOrgIdAndSchyDiv(map);
                if (texmstEntityList.size() > 0) {
                    Map<Integer, MstTextbEntity> chocMap = new HashMap<>();
                    for (int n = 0; n < texmstEntityList.size(); n++) {
                        chocMap.put(texmstEntityList.get(n).getTextbId(), texmstEntityList.get(n));
                    }
                    for (int i = 0; i < f10003DtoList.size(); i++) {
                        for (int j = 0; j < f10003DtoList.get(i).getF10003TextListDtos().size(); j++) {
                            if (chocMap.get(f10003DtoList.get(i).getF10003TextListDtos().get(j).getTextbId()) != null) {
                                f10003DtoList.get(i).getF10003TextListDtos().get(j).setSelFlg("1");
                                f10003DtoList.get(i).setUpdateTimeStr(DateUtils.format(chocMap.get(f10003DtoList.get(i).getF10003TextListDtos().get(j).getTextbId()).getUpdDatime(),GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.error(500, "不明なシステムエラー！");
        }
        return R.ok().put("textbList", f10003DtoList).put("crmschLearnPrdId", crmLearnPrdId).put("schyDiv", f10003DtoList.get(0).getSchyDiv()).put("stuNm",ShiroUtils.getSessionAttribute(GakkenConstant.STU_NM));
    }

    /**
     * <p>登録ボタンの処理</p>
     *
     * @param bookDto 生徒id+教科書List
     * @return
     */
    @RequestMapping(value = "/updateChocTextb", method = RequestMethod.POST)
    public R updateChocTextb(F10003Dto bookDto) {
        return f10003Service.updated(bookDto);
    }

}
