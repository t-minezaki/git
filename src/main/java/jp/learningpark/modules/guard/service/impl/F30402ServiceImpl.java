package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.GuardEventApplyStsDao;
import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.guard.dao.F30110Dao;
import jp.learningpark.modules.guard.dao.F30112Dao;
import jp.learningpark.modules.guard.dao.F30401Dao;
import jp.learningpark.modules.guard.dao.F30402Dao;
import jp.learningpark.modules.guard.dto.F30402Dto;
import jp.learningpark.modules.guard.service.F30402Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * <p>塾からのイベント情報詳細画面</p >
 *
 * @author NWT : hujiaxing <br />
 * 変更履歴 <br />
 * 2019/07/30: hujiaxing: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F30402ServiceImpl implements F30402Service {

    /**
     * 塾からのイベント情報詳細画面
     */
    @Autowired
    F30402Dao f30402Dao;

    /**
     *
     */
    @Autowired
    F30401Dao f30401Dao;

    /**
     * 塾からの連絡通知一覧画面
     */
    @Autowired
    F30112Dao f30112Dao;

    /**
     * 保護者イベント申込状況
     */
    @Autowired
    GuardEventApplyStsDao guardEventApplyStsDao;

    /**
     * 塾ニュース一覧画面
     */
    @Autowired
    F30110Dao f30110Dao;

    /**
     * 初期化  ページ
     *
     * @param id イベントID
     * @return
     */
    @Override
    public R getEventNews(Integer id) {
        // 保護者ID
        String guardId = ShiroUtils.getUserId();
        // 生徒ID
        String stuId = (String)ShiroUtils.getSessionAttribute(GakkenConstant.STU_ID);
        //セッション・生徒組織Id
        String orgId = (String)ShiroUtils.getSessionAttribute("orgId");
        // イベント情報を取得し
        F30402Dto f30402Dto = f30402Dao.getEventNews(id, guardId, stuId);
        String msgx = "";
        //日時変換
        Timestamp applyStartTime = new Timestamp(DateUtils.parse(f30402Dto.getApplyStartDt(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
        Timestamp applyEndTime = new Timestamp(DateUtils.parse(f30402Dto.getApplyEndDt(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
        if (DateUtils.getSysTimestamp().compareTo(applyStartTime) < 0) {
            String format = DateUtils.format(f30402Dto.getApplyStartDt(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO, GakkenConstant.DATE_FORMAT_M_D_H_M);
            msgx = MessageUtils.getMessage("MSGCOMN0112", format);
        } else if (DateUtils.getSysTimestamp().compareTo(applyEndTime) > 0) {
            msgx = MessageUtils.getMessage("MSGCOMN0108");
        } else if (StringUtils.equals("1", f30402Dto.getReplyStsDiv())) {
            msgx = "本イベントは申込済です。<br />右下のメニューの「・・・」→<br />「面談・イベント予約確認」<br />からご覧ください。";
        }

        GuardEventApplyStsEntity guardEventApplyStsEntity = guardEventApplyStsDao.selectById(f30402Dto.getId());
        // 閲覧状況区分
        guardEventApplyStsEntity.setReadingStsDiv("1");
        // 閲覧日時
        guardEventApplyStsEntity.setReadTime(DateUtils.getSysTimestamp());
        // 更新ユーザー日時
        guardEventApplyStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザーID
        guardEventApplyStsEntity.setUpdUsrId(guardId);
        guardEventApplyStsDao.updateById(guardEventApplyStsEntity);

        //お知らせ未読カウント数
        Integer count1 = f30112Dao.getNoticeUnreadCount(guardId, stuId, orgId, null);
        //イベント情報未読カウント数
        Integer count = f30401Dao.getNewsCount(guardId, stuId);
        //ニュース未読カウント数
        Integer newsCount = f30110Dao.getNewsCount(guardId, stuId, orgId);

        return R.ok().put("eventNews", f30402Dto).put("count1", count1).put("count", count).put("newsCount", newsCount).put("msgx", msgx);
    }
}
