package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.manager.service.F21005Service;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 * 遅刻・欠席一覧画面
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/19 ： NWT)hxl ： 新規作成
 * @date 2019/11/19 14:52
 */
@RestController
@RequestMapping("/manager/F21005")
public class F21005Controller {
    /**
     * f21005Service
     */
    @Autowired
    F21005Service f21005Service;

    /**
     * <p>欠席の申請記録を取得する</p>
     *
     * @param day   日付
     * @param limit 検索数
     * @param page  ページ数
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public R select(String day, Integer limit, Integer page) {
        Date date = DateUtils.parse(day, Constant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        SysUserEntity user = ShiroUtils.getUserEntity();
        String roleDiv = user.getRoleDiv().trim();
        String userId = user.getUsrId();
        String orgId = user.getOrgId();
        return f21005Service.getLateAbsHistoryByDay(roleDiv,date, userId, orgId, limit, page);
    }
}
