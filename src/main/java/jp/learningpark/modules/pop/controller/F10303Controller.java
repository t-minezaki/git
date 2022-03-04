/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.pop.dto.F10303Dto;
import jp.learningpark.modules.pop.service.F10303Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>F10303 復習教科選択画面(POP)</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/10/08 : gong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/pop/F10303")
public class F10303Controller extends AbstractController {

    /**
     * 復習教科選択画面(POP) Service
     */
    @Autowired
    private F10303Service f10303Service;

    /**
     * <p>F10303 当生徒の学校で学習するすべて教科を取得し、画面で表示する。</p>
     *
     * @return 画面情報
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init() {
        //学生のID
        String stuId= getUserCd();
        //組織ID
        String orgId= ShiroUtils.getUserEntity().getOrgId();
        // 2020/11/12 zhangminghao modify start
        //教科
        List<MstCodDEntity> list;
        // デフォルト教科
        MstCodDEntity defaultSubject;
        try {
            //教科list
            list = f10303Service.getSubjtDivsByStuId(stuId,orgId);
            defaultSubject = f10303Service.getDefaultSubject();
            if (list.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "コードマスタの教科"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.error("不明なシステムエラー！");
        }
        return R.ok().put("subjtList", list).put("defaultSubject", defaultSubject);
        // 2020/11/12 zhangminghao modify end
    }

    /**
     * <p>F10303 当生徒の学校で学習するすべて教科を取得し、画面で表示する。</p>
     *
     * @return 画面情報
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R f10303submit(F10303Dto dto) {
        //学生のID
        String stuId= getUserCd();
        return f10303Service.submitFn(stuId,dto);
    }
}
