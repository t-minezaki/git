/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.manager.dto.F20004Dto;
import jp.learningpark.modules.manager.service.F20004Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F20004Conrtoller</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/09/20 : hujunjie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F20004")
public class F20004Controller extends AbstractController {

    /**
     * 週別タームプラン確認画面 Service
     */
    @Autowired
    F20004Service f20004Service;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f20004init() {

        //生徒ID
        String stuId = (String) ShiroUtils.getSessionAttribute("stuId");

        //塾Id
        String orgId = ShiroUtils.getUserEntity().getOrgId();

        //塾学習期間ID
        Integer crmLearnPrdId = (Integer) ShiroUtils.getSessionAttribute(GakkenConstant.CRM_LEARN_PRD_ID);

        //生徒名前取得
        F20004Dto stu = f20004Service.getStuInfo(stuId,orgId);
        if (stu == null) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0017", "生徒基本マスタ"));
        }
        // 生徒の週毎の学習時間を取得する
        List<F20004Dto> dataList = f20004Service.selectStuWeeklyLearnTmInfo(crmLearnPrdId, stuId);

        Map<String, F20004Dto> dtoMap = new HashMap<String, F20004Dto>();

        for (F20004Dto crmschDto : dataList) {
            // 学習時期開始日
            crmschDto.setLearnSeasnStartDyDisplay(DateUtils.format(crmschDto.getLearnSeasnStartDy(), GakkenConstant.DATE_FORMAT_M_D_SLASH) + "～");
            // 計画学習時間（分）SUM
            crmschDto.setPlanLearnTmSum(crmschDto.getPlanLearnTm());
            dtoMap.put(StringUtils.defaultString(crmschDto.getPlanLearnSeasn()),crmschDto);
        }
        return R.ok().put("dtoMap", dtoMap).put("stu", stu);
    }
}
