/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstMentorEntity;
import jp.learningpark.modules.manager.service.F21001Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>F21001 メンター基本情報登録画面 Controller</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/11 : xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/f21001")
@RestController
public class F21001Controller extends AbstractController {

    @Autowired F21001Service f21001Service;

    /**
     * 初期化
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R initial() {
        return f21001Service.initial();
    }

    /**
     * メンター基本マスタへ更新する
     * @param mstMentorEntity メンター基本マスタ
     * @param updateTime 更新日時
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R updateMentor(MstMentorEntity mstMentorEntity, @Param("updateTime") String updateTime) {
        return f21001Service.updateMentor(mstMentorEntity,updateTime);
    }
}
