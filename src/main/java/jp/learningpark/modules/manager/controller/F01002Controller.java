/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.manager.service.F01002Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F01002_塾時期新規・変更画面 Controller</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/09 : xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager")
@RestController
public class F01002Controller extends AbstractController {
    /**
     * F01002_service
     */
    @Autowired
    private F01002Service f01002Service;


    /**
     * 塾時期新規・変更画面
     * @param orgId 組織ID
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/F01002/init", method = RequestMethod.GET)
    public R init(Integer id,String orgId){
        // コードマスタ．コードキ
        List<MstCodDEntity> codList = f01002Service.getCodValue();
        if (codList.size() == 0){
            return R.error(MessageUtils.getMessage("MSGCOMN0017","学年"));
        }
        // 初期情報を表示するため
        if (id == null){
            return R.ok().put("codList",codList);
        }
        MstCrmschLearnPrdEntity crmList = f01002Service.getCrmList(orgId,id);
        if (crmList == null){
            return R.error(MessageUtils.getMessage("MSGCOMN0123", "塾時期")).put("codList",codList);
        }
        // 計画期間開始日
        String startDy = DateUtils.format(crmList.getPlanPrdStartDy(), Constant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        // 計画期間終了日
        String endDy = DateUtils.format(crmList.getPlanPrdEndDy(), Constant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        // 排他チェックエラーの場合
        // 更新日時
        String nowTime = DateUtils.format(crmList.getUpdDatime(),Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
        return R.ok().put("codList",codList).put("crmList",crmList).put("startDy",startDy).put("endDy",endDy).put("updateTime",nowTime);
    }

    /**
     * 登録
     * @param mstCrmschLearnPrdEntity 塾学習期間マスタ
     * @param prdStartDy 計画期間開始日
     * @param prdEndDy 計画期間終了日
     * @param updateTime 更新日時
     * @return
     */
    @RequestMapping(value = "/F01002/submit", method = RequestMethod.POST)
    public R submit(MstCrmschLearnPrdEntity mstCrmschLearnPrdEntity, @Param("prdStartDy") String prdStartDy,@Param("prdEndDy") String prdEndDy,@Param("updateTime") String updateTime){
        return f01002Service.insertOrUpdate(mstCrmschLearnPrdEntity,prdStartDy,prdEndDy,updateTime);
    }

}
