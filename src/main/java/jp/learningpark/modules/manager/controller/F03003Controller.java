/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.manager.dto.F03003Dto;
import jp.learningpark.modules.manager.service.F03003Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F03003_教科書単元照会画面 Controller</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/25 : gong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F03003")
@RestController
public class F03003Controller extends AbstractController {

    /**
     * F03003_教科書単元照会画面 service
     */
    @Autowired
    private F03003Service f03003Service;

    /**
     * <p>初期表示</p>
     *
     * @param textbId 教科書ID
     * @param
     * @return
     */
    @RequestMapping(value = "/init/{textbId}", method = RequestMethod.GET)
    public R f03002Init(Integer limit, Integer page, @PathVariable Integer textbId) {
        //塾学習期間ID
        Integer crmLearnPrdId = (Integer) ShiroUtils.getSessionAttribute(GakkenConstant.CRM_LEARN_PRD_ID);

        //教科書情報と教科書単元情報を表示するため
        Integer total = f03003Service.getTexdiffCount(textbId, crmLearnPrdId);
        List<F03003Dto> f03003DtoList = f03003Service.getTexdiff(textbId, crmLearnPrdId, (page - 1) * limit, limit);
        if (f03003DtoList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科書"));
        }
        for (F03003Dto f03003Dto : f03003DtoList) {
            //学習時期開始日の表示
            f03003Dto.setPlanLearnSeasnDisply(DateUtils.format(f03003Dto.getLearnSeasnStartDy(), GakkenConstant.DATE_FORMAT_M_D_SLASH) + "～");
        }
        return R.ok().put("page", new PageUtils(f03003DtoList, total, limit, page));
    }
}

