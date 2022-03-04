/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstManagerEntity;
import jp.learningpark.modules.manager.service.F00002Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * F00002 管理者基本情報登録画面 Controller
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/11 : xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/f00002")
@RestController
public class F00002Controller extends AbstractController {

    @Autowired F00002Service f00002Service;

    /**
     * 初期化
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R initial() {
        return f00002Service.initial();
    }

    /**
     * 管理者基本マスタへ更新する
     * @param mstManagerEntity 管理者基本マスタへ
     * @param updateTime 更新日時
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R updateManager(MstManagerEntity mstManagerEntity, @Param("updateTime") String updateTime) {
        return f00002Service.updateManager(mstManagerEntity,updateTime);
    }
}
