/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.com.service.F40008Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>F40008_パスワード変更画面 Controller</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/03 : xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/com")
@RestController
public class F40008Controller {

    /**
     * F40006 学研コミュニケーションアプリ_Service
     */
    @Autowired F40008Service f40008Service;

    /**
     * 生徒の場合のみ、保護者のメールアドレスより判定する。
     * @param newPwd 新しいパスワード
     * @param oldPwd 古いパスワード
     * @param imageId 変更後ユーザＩＤ
     * @param updDatime 更新日時
     * @return
     */
    @RequestMapping(value="/F40008/update",method = RequestMethod.POST)
    public R resetPwd(@RequestParam String imageId,@RequestParam String oldPwd,@RequestParam String newPwd,@RequestParam String updDatime,@RequestParam String gidFlg) {
        return f40008Service.resetPwd(newPwd,oldPwd,imageId,updDatime,gidFlg);
    }

    /**
     * 初期化
     * @return
     */
    @RequestMapping(value="/F40008/init",method = RequestMethod.GET)
    public R getInit() {
        return f40008Service.getInit();
    }

    @RequestMapping(value="/F40008/updateStatus",method = RequestMethod.GET)
    public R updateStatus(String afterId, String updDatime) {
        return f40008Service.updateStatus(afterId,updDatime);
    }

    @RequestMapping(value="/F40008/getStuNumber",method = RequestMethod.GET)
    public R getStuNumber(String afterId) {
        return f40008Service.getStuNumber(afterId);
    }

//    /**
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "/F40008/judgeId",method = RequestMethod.GET)
//    public R judgeId(String id){
//        return f40008Service.judgeId(id);
//    }
}
