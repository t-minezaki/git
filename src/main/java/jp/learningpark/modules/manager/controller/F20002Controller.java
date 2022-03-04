/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstTextbEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.manager.dto.F20002Dto;
import jp.learningpark.modules.manager.service.F20002Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * <p>F20002生徒基本情報画面</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/10/08 : gong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F20002")
public class F20002Controller extends AbstractController {

    /**
     * F20002 Service
     */
    @Autowired
    private F20002Service f20002Service;

    /**
     * コードマスタ_明細 Extend Service
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * <p>初期表示</p>
     * <p>生徒教科書選択情報を入手する</p>
     *
     * @return
     */
    @RequestMapping(value = "/getStuInfo", method = RequestMethod.GET)
    public R getStuInfo() {
        //生徒Id
        String stuId = (String) ShiroUtils.getSessionAttribute("stuId");
        //生徒当学年の教科ごとの学校と塾の教科書list
        List<F20002Dto> f20002DtoList;
        //生徒当学年選択の教科書情報
        List<MstTextbEntity> texmstEntityList;
        List<MstCodDEntity> subjtList = new ArrayList<>();
        //生徒情報
        F20002Dto stu;
        //塾Id
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //メンター名
        String mentorNm = (String) ShiroUtils.getSessionAttribute(GakkenConstant.MENTOR_NM);

        //マスタ存在チェック
        List<MstCodDEntity> codMstDEntityList=mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd","cod_value").orderByAsc("sort").and(w->w.eq("cod_key","SUBJT_DIV").eq("del_flg",0)));
        if (codMstDEntityList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "コードマスタの教科"));
        }
        //塾学習期間ID
        Integer crmLearnPrdId = (Integer) ShiroUtils.getSessionAttribute(GakkenConstant.CRM_LEARN_PRD_ID);
        try {
            stu = f20002Service.getStuInfoByStuId(stuId, orgId);
            if (stu == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒基本マスタ"));
            }
            List<String> goodSubjtDivList = stu.getGoodSubjtDiv() == null?new ArrayList<>():Arrays.asList(stu.getGoodSubjtDiv().split(","));
            List<String> nogoodSubjtDivList = stu.getNogoodSubjtDiv() == null?new ArrayList<>():Arrays.asList(stu.getNogoodSubjtDiv().split(","));
            stu.setGoodSubjt(goodSubjtDivList);
            stu.setNogoodSubjt(nogoodSubjtDivList);
            Map<String, Object> map = new HashMap<>();
            map.put("schId", stu.getSchId());
            map.put("stuId", stuId);
            map.put("orgId", stu.getOrgId());
            map.put("schyDiv", stu.getSchyDiv());
            //塾学習期間ID
            map.put("crmLearnPrdId", crmLearnPrdId);
            //当生徒当学年の教科ごとの学校と塾の教科書情報を取得し教科書選択一覧エリアに表示する。
            f20002DtoList = f20002Service.getTextbDtoListOfSchByStuId(stuId, orgId);
            //当生徒当学年の教科ごとの学校と塾の教科書情報を取得し教科書選択一覧エリアに表示する。
            ArrayList lowSchy = new ArrayList(Arrays.asList(new String[]{"4", "5", "6", "7", "8", "9"}));
            String codCd = lowSchy.indexOf(stu.getSchyDiv().trim())==-1?"m2":"m1";
            subjtList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key","SUBJT_DIV").ne("cod_cd",codCd).orderByAsc("sort").eq("del_flg",0));
            //教科ごとの生徒選択した教科書リストを取得し画面．教科書選択リストボックスで表示する。
            texmstEntityList = f20002Service.getTextbListOfStuByStuIdAndOrgIdAndSchyDiv(map);
            if (texmstEntityList.size() > 0) {
                Map<Integer, MstTextbEntity> chocMap = new HashMap<>();
                for (int n = 0; n < texmstEntityList.size(); n++) {
                    chocMap.put(texmstEntityList.get(n).getTextbId(), texmstEntityList.get(n));
                }
                for (int i = 0; i < f20002DtoList.size(); i++) {
                    for (int j = 0; j < f20002DtoList.get(i).getF20002TextListDtos().size(); j++) {
                        if (chocMap.get(f20002DtoList.get(i).getF20002TextListDtos().get(j).getTextbId()) != null) {
                            f20002DtoList.get(i).getF20002TextListDtos().get(j).setSelFlg("1");
                            f20002DtoList.get(i).setUpdateTimeStr(DateUtils.format(chocMap.get(f20002DtoList.get(i).getF20002TextListDtos().get(j).getTextbId()).getUpdDatime(),GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
                        }
                    }
                }
            }
            if (subjtList.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科")).put("stu", stu);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.error(500, "不明なシステムエラー！");
        }
        return R.ok().put("stu", stu).put("textbList", f20002DtoList).put("crmschLearnPrdId", crmLearnPrdId).put("mentorNm",mentorNm).put("subjtList",subjtList);
    }

    /**
     * <p>登録ボタンの処理</p>
     *
     * @param bookDto 生徒id+教科書List
     * @return
     */
    @RequestMapping(value = "/updateChocTextb", method = RequestMethod.POST)
    public R updateChocTextb(String bookDto) throws UnsupportedEncodingException {
        bookDto = URLDecoder.decode(bookDto, "UTF-8");
        F20002Dto f20002Dto = JSON.parseObject(StringUtils.defaultString(bookDto), new TypeReference<F20002Dto>() {
        });
        return f20002Service.update(f20002Dto);
    }


}
