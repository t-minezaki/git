package jp.learningpark.modules.job.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MailUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.framework.utils.TmplateUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstUsrDao;
import jp.learningpark.modules.common.entity.MailSendHstEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MailSendHstService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstEventService;
import jp.learningpark.modules.job.dao.BTGKA1002Dao;
import jp.learningpark.modules.job.entity.BTGKA1002Dto;
import jp.learningpark.modules.job.service.BTGKA1002Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("BTGKA1002Service")
public class BTGKA1002ServiceImpl implements jp.learningpark.modules.job.service.BTGKA1002Service {

    /**
     * mailUtils
     */
    @Autowired
    MailUtils mailUtils;

    /**
     * mstEventService
     */
    @Autowired
    private MstEventService mstEventService;

    /**
     * mailSendHstService
     */
    @Autowired
    MailSendHstService mailSendHstService;

    /**
     * mstCodDService
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * BTGKA1002Service
     */
    @Autowired
    private BTGKA1002Service bTGKA1002Service;

    /**
     * ユーザー基本マスタ　Dao
     */
    @Autowired
    private MstUsrDao mstUsrDao;

    /**
     * BTGKA1002Dao
     */
    @Autowired
    private BTGKA1002Dao bTGKA1002Dao;
    @Value("${learningpark-domain.domain}")
    String domain;

    private Logger logger = LoggerFactory.getLogger(BTGKA1002Service.class);

    /**
     * @return List<BTGKA1002Dto>
     */
    @Override
    public List<BTGKA1002Dto> getGEASList() {
        // その日の日付をインスタンス化する
        Date today = new Date();
        // 当日の日付から明日の日付を差し引く manamiru1-573 yang 20210402 start
        Date sgdPlanDate = DateUtils.addDays(today, 1);
        //manamiru1-573 yang 20210402 end
        String dateStr = DateUtils.format(sgdPlanDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        return bTGKA1002Dao.getGEASList(dateStr);
    }

    /**
     * @param params メールパラメータ
     * @return R
     */
    @Override
    public R postEmail(Map<String, Object> params) {

        // メールアドレス
        String mailAddress = (String)params.get("mailAddress");
        // メールタイトル
        String mailTitle = (String)params.get("mailTitle");

        String guard = (String)params.get("guardId");
        MstUsrEntity mstUsrEntity = StringUtils.isEmpty(guard) ? null : mstUsrDao.selectOne(
                new QueryWrapper<MstUsrEntity>().select("after_usr_id").eq("usr_id", guard).eq("del_flg", 0));
        if (mstUsrEntity != null && !StringUtils.isEmpty(mstUsrEntity.getAfterUsrId())) {
            params.put("guardId", mstUsrEntity.getAfterUsrId());
        }

        // logger
        Logger logger = LoggerFactory.getLogger("BTGKA1002ServiceImpl");

        params.put("sgdPlanDate", DateUtils.format(new Date((Long)params.get("sgdPlanDate")), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
        //
        String content = "";
        //
        try {
            String template =
                    //                    "\t<p style = 'font-weight: bolder'>・タイトル</p>\n" +
                    //                    "\t<p>【リマインド】予約いただいた日程が近づいてまいりました。</p>\n" +
                    //                    "<br>" +
                    //                    "\t<p style = 'font-weight: bolder'>・本文</p>\n" +
                    "\t<p>${flnmNm!}${flnmLnm}様</p>\n" + "<br>" + "\t<p>お世話になっております。</p>\n" + "\t<p>${orgNm!}です。</p>\n" + "<br>" + "\t<p>ご予約いただいている日程が近づいて参りましたのでご連絡いたします。</p>\n" + "<br>" + "\t<p  style = 'font-weight: bolder'>【予約日程】</p>\n" + "\t<p>.${ctgyNm!}</p>\n" + "\t<p>.${sgdPlanDate!}</p>\n" + "<br>" + "\t<p>詳しい情報はマイページよりご確認ください。</p>\n" + "<br>" + "\t<p>ID：${guardId!}\n</p>" + "\t<p>PASS：あなたが設定したパスワード</p>\n" + "<br>" + "\t<p>※このメールは送信専用アドレスから配信されています。</p>\n" + "\t<p>ご返信いただいてもお答えできませんのでご了承ください。</p>\n";
            content = TmplateUtils.generateString(params, template);

            mailUtils.sendMail(mailAddress, mailTitle, content);

        } catch (Exception e) {

            // 送信失敗後にエラー情報を返す
            logger.error(e.getMessage());
            return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0094")).put("mailcnt", content).put("status", "1");
        }
        return R.ok().put("mailcnt", content.toString()).put("status", "0");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void postMail() {

        // 保護者イベント申込状況から対象者一覧を取得
        List<BTGKA1002Dto> BTGKA1002DtoList = bTGKA1002Service.getGEASList();

        if (BTGKA1002DtoList == null || BTGKA1002DtoList.size() <= 0) {
            // 上記取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。

            logger.error(MessageUtils.getMessage("MSGCOMB0023"));
        }

        String params = JSON.toJSONString(BTGKA1002DtoList);
        JSONArray paramsMap = JSON.parseArray(params);

        for (Map<String, Object> param : paramsMap.toJavaList(Map.class)) {

            if (param.get("mailAddress") == null || param.get("mailAddress") == "") {
                continue;
            }

            // 区分値を表示するため、引渡データ．区分より、区分値を取得し、画面で表示する。
            MstCodDEntity mstCodDEntity = mstCodDService.getOne(
                    new QueryWrapper<MstCodDEntity>().eq("cod_key", "MAIL_MSG_REMAND").eq("cod_cd", param.get("orgId")).eq("del_flg", 0));

            if (mstCodDEntity == null) {
                mstCodDEntity = mstCodDService.getOne(
                        new QueryWrapper<MstCodDEntity>().eq("cod_key", "MAIL_MSG_REMAND").eq("cod_cd", "DEFAULT").eq("del_flg", 0));
            }

            String mailTitle = mstCodDEntity.getCodValue();

            // カテゴリ
            MstEventEntity mstEventEntity = mstEventService.getOne(new QueryWrapper<MstEventEntity>().select("ctgy_nm").eq("id", param.get("id")));

            param.put("mailTitle", mailTitle);
            R r = postEmail(param);

            // メール送信履歴へ登録する。
            MailSendHstEntity mailSendHstEntity = new MailSendHstEntity();
            // 組織ID
            mailSendHstEntity.setOrgId((String)param.get("orgId"));
            // イベントID
            mailSendHstEntity.setEventId((Integer)param.get("id"));
            // カテゴリ
            mailSendHstEntity.setCtgyNm("1");
            // 生徒ID
            mailSendHstEntity.setStuId((String)param.get("stuId"));
            // 保護者ID
            mailSendHstEntity.setGuardId((String)param.get("guardId"));
            // メールアドレス
            mailSendHstEntity.setMailad((String)param.get("mailAddress"));
            // メールタイトル
            mailSendHstEntity.setMailTitle((String)param.get("mailTitle"));
            // メール内容
            mailSendHstEntity.setMailCnt((String)r.get("mailcnt"));
            // ステータス
            mailSendHstEntity.setStatus((String)r.get("status"));
            // 作成日時
            mailSendHstEntity.setCretDatime(DateUtils.toTimestamp(new Date()));
            // 作成ユーザＩＤ
            mailSendHstEntity.setCretUsrId("system");
            // 更新日時
            mailSendHstEntity.setUpdDatime(DateUtils.toTimestamp(new Date()));
            // 更新ユーザＩＤ
            mailSendHstEntity.setUpdUsrId("system");

            mailSendHstService.save(mailSendHstEntity);

            // 送信失敗
            if (StringUtils.equals((String)r.get("status"), "1")) {
                logger.error((String)r.get("msg"));
            }
        }
    }
}
