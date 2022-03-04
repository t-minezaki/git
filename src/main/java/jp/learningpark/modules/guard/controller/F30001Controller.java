/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstGuardEntity;
import jp.learningpark.modules.guard.service.F30001Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>F30001_保護者基本情報登録画面</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/28 : gong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/guard/f30001")
@RestController
public class F30001Controller extends AbstractController {

    /**
     * F30001_保護者基本情報登録画面 Service
     */
    @Autowired
    private F30001Service f30001Service;

    /**
     * 初期表示
     * @return イメージの情報
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f30001Init() {
        return f30001Service.getGuard();
    }

    /**
     * 住所マスタより、郵便番号を元に、住所情報を取得し
     * @param postcd 郵便番号
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public R f30001Search(String postcd) {
        return f30001Service.searchAddr(postcd);
    }

    /**
     * 保護者基本マスタ更新
     * @param mstGuardEntity 保護者基本マスタ
     * @param updateTime 更新日時
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R f30001Submit(MstGuardEntity mstGuardEntity, @Param("updateTime") String updateTime) {
        return f30001Service.submit(mstGuardEntity,updateTime);
    }
}
