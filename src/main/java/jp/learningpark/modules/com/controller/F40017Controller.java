/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MailUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.TmplateUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.entity.OneTimePwdEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstManagerService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.service.OneTimePwdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>メールアドレス認証画面</p>
 * <p>Controller</p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/4 : xie: 新規<br />
 * @version 1.0
 */
@RequestMapping("/common/F40017")
@RestController
public class F40017Controller {

    /**
     * MstUsrService
     */
    @Autowired
    MstUsrService mstUsrService;

    /**
     * MstCodDService
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     * MstManagerService
     */
    @Autowired
    MstManagerService mstManagerService;

    /**
     * OneTimePwdService
     */
    @Autowired
    OneTimePwdService oneTimePwdService;
    /**
     * mailUtils
     */
    @Autowired
    MailUtils mailUtils;

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init() {
        //下記コードマスタより、メール内容・タイトルを取得し、メールサーバーを基づいて、リアルタイルで自動メール送信を行う
        MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", ShiroUtils.getUserId()).eq("del_flg", 0));

        return R.ok().put("roleDiv", mstUsrEntity.getRoleDiv())
                .put("gidRuleFlg", mstUsrEntity.getGidRuleFlg())
                .put("manaRuleFlg", mstUsrEntity.getManaRuleFlg())
                .put("perlInfoRuleFlg", mstUsrEntity.getPerlInfoRuleFlg());/*.put("fstLoginFlg", mstUsrEntity.getFstLoginFlg())*/
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R submit(String mail) {
        //メールサーバーを基づいて、リアルタイルで自動メール送信を行う。

        //下記コードマスタより、メール内容・タイトルを取得し、メールサーバーを基づいて、リアルタイルで自動メール送信を行う
        MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", ShiroUtils.getUserId()).eq("del_flg", 0));
        //半角英数字6桁でランダム発番、ワンタイムパスワードとして。
        String verificationCode = GetBindNum();
        //メールアドレスを登録時のメール本文を取得するため、コードマスタより取得する。
        MstCodDEntity mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "MAIL_MSG_LOGIN").eq("cod_cd", "1").eq("del_flg", 0));
        //上記取得したメール本文に下記パラメータ（可変値）を入れ替え、メール内容を取得し、リアルタイム送信を行う。
        if (mstCodDEntity != null) {
            String template = mstCodDEntity.getCodValue() + mstCodDEntity.getCodValue3();
            String title = mstCodDEntity.getCodValue2();
            Map<String, Object> params = new HashMap<>();
            params.put("verificationCode", verificationCode);
            try {
                String content = TmplateUtils.generateString(params, template);
                mailUtils.sendMail(mail, title, content);
            } catch (Exception e) {
                logger.error(e.getMessage());
                //上記配信失敗の場合、処理を中断し、確認メッセージをポップアップ表示する
                return R.error(MessageUtils.getMessage("MSGCOMN0094")).put("message", "メール送信が失敗しました。");
            }
        }
        //ONETIMEパスワード管理へ登録する。

        OneTimePwdEntity oneTimePwdEntity = oneTimePwdService.getOne(new QueryWrapper<OneTimePwdEntity>().select("id").eq("after_usr_id", mstUsrEntity.getAfterUsrId()).eq("del_flg", 0));
        if (oneTimePwdEntity == null) {
            //存在しない場合、登録内容は以下を参照
            oneTimePwdEntity = new OneTimePwdEntity();
            oneTimePwdEntity.setCretDatime(DateUtils.getSysTimestamp());
            oneTimePwdEntity.setCretUsrId(ShiroUtils.getUserId());
        }
        oneTimePwdEntity.setAfterUsrId(mstUsrEntity.getAfterUsrId());
        oneTimePwdEntity.setMailad(mail);
        oneTimePwdEntity.setOneTimePw(verificationCode);
        oneTimePwdEntity.setUpdDatime(DateUtils.getSysTimestamp());
        oneTimePwdEntity.setUpdUsrId(ShiroUtils.getUserId());
        oneTimePwdEntity.setDelFlg(0);
        try {
            oneTimePwdService.saveOrUpdate(oneTimePwdEntity);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        // F40018_ワンタイムパスワード入力画面へ遷移する。
        return R.ok();
    }

    //検証コード生成
    private static String GetBindNum() {
        String[] beforeShuffle = new String[]{
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "a", "b", "d", "c", "e", "f", "g", "h", "i", "j",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z"};
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(3, 9);
        return result;
    }
}
