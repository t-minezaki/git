/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.com.service.F40006Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>F40006 学研コミュニケーションアプリ Controller</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2018/12/28 : xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/com")
@RestController
public class F40006Controller {

    /**
     * F40006 学研コミュニケーションアプリ_Service
     */
    @Autowired F40006Service f40006Service;

    /**
     * 生徒の場合のみ、保護者のメールアドレスより判定する。
     * @param id
     * @param email
     * @return
     */
    @RequestMapping(value="/F40006/reset",method = RequestMethod.POST)
    public R getCompare(String id, String email,String brandCd) {
        return f40006Service.confirmId(id,email,brandCd);
    }
}
