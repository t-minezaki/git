/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.service.impl;

import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.F40009Dao;
import jp.learningpark.modules.com.service.F40009Service;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/02/27 : gong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F40009ServiceImpl implements F40009Service {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * F40009_ログインID解除画面 dao
     */
    @Autowired
    private F40009Dao f40009Dao;
    @Value("${learningpark-domain.domain}")
    String domain;

    /**
     * メール  Utils
     */
    @Autowired
    private MailUtils mailUtils;

    /**
     *
     */
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * <p>画面．ＩＤがユーザ基本マスタに存在すること。</p>
     *
     * @param afterUsrId 変更後ユーザＩＤ
     * @param brandCd    URLのブランドコード
     * @return
     */
    @Override
    public MstUsrEntity getOne(String afterUsrId, String brandCd) {
        return f40009Dao.selectOne(afterUsrId, brandCd);
    }

    /**
     * <p>①　ユーザ基本マスタ．ロール ＝ 「生徒」の場合、</p>
     *
     * @param usrId ユーザＩＤ
     * @param email
     * @return
     */
    @Override
    public MstStuEntity getWithGuard(String usrId, String email) {
        return f40009Dao.selectWithGuard(usrId, email);
    }

    /**
     * 送信する
     *
     * @param id
     * @param userId
     * @param email
     * @param brandCd
     * @return
     */
    @Override
    public R postEmail(String id, String userId, String userName, String email, String brandCd, String uuid, HttpServletRequest request) {
        try {
            String title = "マナミルログインID解除通知";
            StringBuffer sb = new StringBuffer();
            sb.append("<h1>" + id.substring(0, id.length() - 2) + "**　" + userName + "様</h1>")
                    .append("<p style='color:#000000'>パスワードを再設定した後、IDロックが解除されます。</p>")
                    .append("<p style='color:#000000'>再設定したパスワードでログインしてください。</p>")
                    .append("<p style='color:#000000'>※60分超える場合、URLが無効になる</p>")
                    .append("<p style='text-align:right'>マナミル " + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS) + "</p>");
            mailUtils.sendMail(email, title, sb.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RRException(MessageUtils.getMessage("MSGCOMN0094"));
        }
        return R.ok();
    }
}
