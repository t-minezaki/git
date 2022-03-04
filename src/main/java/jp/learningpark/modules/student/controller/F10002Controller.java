/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.student.dto.F10002Dto;
import jp.learningpark.modules.student.service.F10002Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>F10002 生徒Myページ画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/10/07 : hujunjie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/student/F10002")
public class F10002Controller extends AbstractController{
    /**
     * 生徒Myページ画面 Service
     */
    @Autowired
    private F10002Service f10002Service;

    /**
     * <p>F10002 生徒個人データを表示する</p>
     *
     * @return stuDto, birthFormat
     */
    @RequestMapping(value = "/getStuMyPage", method = RequestMethod.GET)
    @RequiresPermissions("student")
    public R getStuMyPage() {
        //生徒ID
        String stuId = getUserCd();
        //塾Id
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 生徒個人データDto
        F10002Dto stuDto = f10002Service.getStuInfo(stuId,orgId);

        // データ無しMessage
        if (stuDto == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒基本マスタ"));
        }

        // 生年月日Format(yyyy/mm/dd)
        String birthdFormat = "";
        if (stuDto.getBirthd() != null) {
            birthdFormat = DateUtils.format(stuDto.getBirthd(), GakkenConstant.DATE_FORMAT_YYYYMD);
        }
        return R.ok().put("stuDto", stuDto).put("birthd", birthdFormat);
    }
}
