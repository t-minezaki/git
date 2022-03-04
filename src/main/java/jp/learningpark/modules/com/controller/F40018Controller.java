/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.controller;

import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dto.F40018Dto;
import jp.learningpark.modules.com.service.F40018Service;
import jp.learningpark.modules.common.GakkenConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ワンタイムパスワード入力画面</p>
 * <p>Controller</p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/4 : xie: 新規<br />
 * @version 1.0
 */
@RequestMapping("/common/F40018")
@RestController
public class F40018Controller {

    /**
     * F40018Service
     */
    @Autowired
    F40018Service f40018Service;
    private static final Logger log = LoggerFactory.getLogger(F40018Controller.class);

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public R submit(String password) {

        //ワンタイムパスワード認証を行う。
        //下記条件より、ユーザ基本マスタを元に、変更後ユーザーIDを取得する。
        F40018Dto f40018Dto = f40018Service.authenticationPassword(ShiroUtils.getUserId());
        if (f40018Dto != null){
            //ワンタイムパスワードの有効期限をチェックする
            long dd1 = f40018Dto.getUpdDatime().getTime();
            long dd2 = DateUtils.getSysTimestamp().getTime();
            double hours = (double)(dd2-dd1)/3600/1000;
            //処理を中断し、画面上部のエラーメッセージ領域でエラーメッセージ（MSGCOMN0161）を表示する。
            if (hours > 4.0){
                return R.error(MessageUtils.getMessage("MSGCOMN0161")).put("flag","1");
            }else {
                //1.2.1取得したONETIMEパスワード管理．更新日時＋4時間　＜　システム時間の場合、
                //ワンタイムパスワードをチェックする
                //1.2.1取得したONETIMEパスワード管理．ONETIMEパスワードと画面.ワンタイムパスワード一致するの場合、
                if (f40018Dto.getOneTimePw().equals(password)){
                    try {
                        f40018Service.update(ShiroUtils.getUserId(), f40018Dto.getMailad(), f40018Dto.getRoleDiv().trim());
                    } catch (Exception e){
                        //F40019_ワンタイムパスワード認証結果（失敗）画面へ遷移する。
                        return R.error().put("flag","0");
                    }
                }else {
                    //F40019_ワンタイムパスワード認証結果（失敗）画面へ遷移する。
                    return R.error().put("flag","0");
                }
            }
            /* 2020/01/05 V8.0(ph2) cuikailin add start */
            if (ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_MAIL_ADDRESS) == null){
                try{
                    String gidPK = ShiroUtils.getUserEntity().getGidpk();
                    // NWT　楊　2021/07/06　MANAMIRU1-719　Edit Start
                    log.info("---------------------------push gidpk: " + gidPK + "---------------------------");
                    if (StringUtils.isNotBlank(gidPK)){
                        CM0003.pushMailToGID(gidPK,f40018Dto.getMailad());
                    }else {
                        log.error("gidpkは空です");
                    }
                    // NWT　楊　2021/07/06　MANAMIRU1-719　Edit End
                }catch (Exception e){
                    //F40019_ワンタイムパスワード認証結果（失敗）画面へ遷移する。
                    return R.error().put("flag","0");
                }
            }
            /* 2020/01/05 V8.0(ph2) cuikailin add start */
        }
        //F40020_ワンタイムパスワード認証結果（成功）画面へ遷移する。
        return R.ok();
    }
}
