package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.MailUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.TmplateUtils;
import jp.learningpark.modules.common.service.MstMentorService;
import jp.learningpark.modules.manager.dao.F08014Dao;
import jp.learningpark.modules.manager.dto.F08014Dto;
import jp.learningpark.modules.manager.dto.F08014StudentDto;
import jp.learningpark.modules.manager.service.F08014Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class F08014ServiceImpl implements F08014Service {

    /**
     * mailUtils
     */
    @Autowired
    MailUtils mailUtils;

    /**
     * f08014Dao
     */
    @Autowired
    private F08014Dao f08014Dao;

    /**
     * mstMentorService
     */
    @Autowired
    MstMentorService mstMentorService;
    @Value("${learningpark-domain.domain}")
    String domain;

    /**
     * @param id 保護者イベント申込ID
     * @return
     */
    @Override
    public F08014Dto selectEvent(Integer id) {
        return f08014Dao.selectEvent(id);
    }

    /**
     * @param params メールパラメータ
     * @return
     */
    @Override
    public R postEmail(Map<String, Object> params) {

        // メールアドレス
//        String email = (String)params.get("email");
//        // メールタイトル
//        String mailTitle = (String)params.get("mailTitle");
//
//        Logger logger = LoggerFactory.getLogger("F08014ServiceImpl");

        String content = "";

//        try {
//            String template =
//                    "\t${guardNm!}様\n" +
//                    "<br>" +
//                    "<br>" +
//                    "\t<p>お世話になっております。</p>\n" +
//                    "\t<p>${orgNm!}です。</p>\n" + "<br>" +
//                    "\t<p>お知らせに新しいご案内を配信いたしましたので、</p>\n" +
//                    "\t<p>ご確認ください。</p>\n" +
//                    "<br>" +
//                    "\t<p>ID：${guardAfterId!}\n</p>" +
//                    "\t<p>PASS：あなたが設定したパスワード</p>\n" +
//                    "<br>" +
//                    "\t<p>※このメールは送信専用アドレスから配信されています。</p>\n" +
//                    "\t<p>ご返信いただいてもお答えできませんのでご了承ください。</p>\n";
//            content = TmplateUtils.generateString(params, template);
//
//            mailUtils.sendMail(email, mailTitle, content);
//
//        } catch (Exception e) {
//
//            // 送信失敗後にエラー情報を返す
//            logger.error(e.getMessage());
//            return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0094")).put("mailcnt", content.toString()).put("status", "1");
//        }
        return R.ok().put("mailcnt", content.toString()).put("status", "0");
    }

    @Override
    public List<F08014StudentDto> selectStudent(Integer espdId, Boolean userFlag) {
        return f08014Dao.selectStudent(espdId, userFlag);
    }
}
