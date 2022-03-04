package jp.learningpark.modules.student.controller;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.student.dto.F10001Dto;
import jp.learningpark.modules.student.service.F10000Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/07/08 ： NWT)hxl ： 新規作成
 * @date 2020/07/08 11:33
 */
@RequestMapping("/student/F10000")
@RestController
public class F10000Controller {

    /**
     * f10000Service
     */
    @Autowired
    F10000Service f10000Service;

    /**
     * 初期化
     *
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R init() {
        String userId = ShiroUtils.getUserId();
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        return f10000Service.init(orgId, userId);
    }

    /**
     * <p>変更</p>
     *
     * @param dto 生徒の情報
     * @return
     */
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    public R submit(@RequestBody F10001Dto dto) {
        //生年月日
        Date birthd = DateUtils.parse(dto.getBirthdayString(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        if (birthd == null) {
            return R.error(MessageUtils.getMessage("MSGCOMD0013", "生年月日"));
        }
        dto.setBirthd(birthd);
        return f10000Service.submit(dto);
    }
}
