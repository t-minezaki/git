package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.service.GuardReadingStsService;
import jp.learningpark.modules.guard.dao.F30111Dao;
import jp.learningpark.modules.guard.dto.F30111Dto;
import jp.learningpark.modules.guard.service.F30110Service;
import jp.learningpark.modules.guard.service.F30111Service;
import jp.learningpark.modules.xapi.Activitys;
import jp.learningpark.modules.xapi.Extensions;
import jp.learningpark.modules.xapi.Verbs;
import jp.learningpark.modules.xapi.XApiConstant;
import jp.learningpark.modules.xapi.XApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>塾ニュース詳細表示画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/11: xiong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F30111ServiceImpl implements F30111Service {

    /**
     * 塾ニュース詳細表示画面 Dao
     */
    @Autowired
    F30111Dao f30111Dao;

    /**
     * 塾ニュース一覧画面 Service
     */
    @Autowired
    F30110Service f30110Service;

    /**
     * 保護者お知らせ閲覧状況 Service
     */
    @Autowired
    GuardReadingStsService guardReadingStsService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @param id お知らせID
     * @param url 当画面URL
     * @return R
     */
    @Override
    public R getNoticeNews(Integer id, String url) {
        // 保護者ID
        String guardId = ShiroUtils.getUserId();
        // 生徒ID
        String stuId = (String)ShiroUtils.getSessionAttribute(GakkenConstant.STU_ID);
        //セッション・生徒組織Id
        String orgId = (String)ShiroUtils.getSessionAttribute("orgId");
        // 塾ニュースを取得し
        F30111Dto f30111Dto = f30111Dao.getNoticeNews(id, guardId, stuId);
        // 知らせ．更新日時
        String updTime = DateUtils.format(f30111Dto.getUpdDatime(), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
        // 閲覧状況区分
        GuardReadingStsEntity guardReadingStsEntity = guardReadingStsService.getById(f30111Dto.getId());
        if ("0".equals(guardReadingStsEntity.getReadingStsDiv())) {
            // 更新項目
            guardReadingStsEntity.setReadingStsDiv("1");
            guardReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
            guardReadingStsEntity.setUpdUsrId(ShiroUtils.getUserId());
            guardReadingStsService.updateById(guardReadingStsEntity);
        }
        //お知らせ未読カウント数
        Integer count = f30110Service.getNewsCount(guardId, stuId, orgId);
        //ビッグデータ
        Extensions exts = new Extensions();
        //利用者のシステムID
        exts.put(XApiConstant.EXT_KEY_USER_ID, ShiroUtils.getUserId());
        //当画面URL
        exts.put(XApiConstant.EXT_KEY_URL, url);
        //当画面訪問時間
        exts.put(XApiConstant.EXT_KEY_VISIT_TIME, DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS));
        //塾ニュースＩＤ
        exts.put(XApiConstant.EXT_KEY_NOTICE_ID, id);
        //生徒ＩＤ
        exts.put(XApiConstant.EXT_KEY_STU_ID, stuId);
        try {
            XApiUtils.saveStatement(Verbs.launched(), Activitys.academy(), exts);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return R.ok().put("noticeNews", f30111Dto).put("count", count).put("updTime", updTime);
    }
}
