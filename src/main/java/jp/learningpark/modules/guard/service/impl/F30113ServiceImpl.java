package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.service.GuardReadingStsService;
import jp.learningpark.modules.guard.dao.F30113Dao;
import jp.learningpark.modules.guard.dto.F30113Dto;
import jp.learningpark.modules.guard.service.F30112Service;
import jp.learningpark.modules.guard.service.F30113Service;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * <p>塾からの連絡通知詳細画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/11: xiong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F30113ServiceImpl implements F30113Service {

    /**
     * 塾からの連絡通知詳細画面 Dao
     */
    @Autowired
    F30113Dao f30113Dao;

    /**
     * 塾からの連絡通知一覧画面 Service
     */
    @Autowired
    F30112Service f30112Service;

    /**
     * 保護者お知らせ閲覧状況 Service
     */
    @Autowired
    GuardReadingStsService guardReadingStsService;

    /**
     * @param id お知らせID
     * @return
     */
    @Override
    public R getNoticeNews(Integer id) {
        // 保護者ID
        String guardId = ShiroUtils.getUserId();
        // 生徒ID
        String stuId = (String) ShiroUtils.getSessionAttribute(GakkenConstant.STU_ID);
        //セッション・生徒組織Id
        String orgId = (String) ShiroUtils.getSessionAttribute("orgId");
        // 塾ニュースを取得し
        F30113Dto f30113Dto = f30113Dao.getNoticeNews(id, guardId, stuId);
        // 知らせ．更新日時
        //modify at 2021/08/16 for V9.02 by NWT LiGX START
        String updTime = DateUtils.format(f30113Dto.getUpdDatime(), Constant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        //modify at 2021/08/16 for V9.02 by NWT LiGX END
        //add at 2021/08/16 for V9.02 by NWT LiGX START
        //遅刻欠席連絡履歴．対象年月日
        String tgtYmd = null;
        if (null != f30113Dto.getTgtYmd()){
            if ("0".equals(f30113Dto.getAbsSts())){
                // modify for backlog166 by NWT LiGX START
                tgtYmd = DateUtils.format(f30113Dto.getTgtYmd(), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
                // modify for backlog166 by NWT LiGX END
            }else if ("1".equals(f30113Dto.getAbsSts())){
                tgtYmd = DateUtils.format(f30113Dto.getTgtYmd(), Constant.DATE_FORMAT_YYYY_MM_DD_SLASH);
            }
        }
        //add at 2021/08/16 for V9.02 by NWT LiGX END
        // 閲覧状況区分
        GuardReadingStsEntity guardReadingStsEntity = guardReadingStsService.getById(f30113Dto.getId());
        if ("0".equals(guardReadingStsEntity.getReadingStsDiv())) {
            // 更新項目
            guardReadingStsEntity.setReadingStsDiv("1");
            guardReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
            guardReadingStsEntity.setUpdUsrId(ShiroUtils.getUserId());
            guardReadingStsService.updateById(guardReadingStsEntity);
        }
        //お知らせ未読カウント数
        Integer count = f30112Service.selectNoticeUnreadCount(guardId, stuId, orgId, null);
        //modify at 2021/08/16 for V9.02 by NWT LiGX START
        return R.ok().put("noticeNews", f30113Dto).put("count", count).put("updTime",updTime).put("tgtYmd",tgtYmd);
        //modify at 2021/08/16 for V9.02 by NWT LiGX END
    }
    // 2020/11/11 zhangminghao modify start
    @Override
    public void noticeConfirm(Integer guardReadingId) {
        Timestamp latestTime = DateUtils.getSysTimestamp();
        String userId = ShiroUtils.getUserId();

        f30113Dao.updateOpenedFlg(guardReadingId, userId, latestTime);
    }
    // 2020/11/11 zhangminghao modify end
}
