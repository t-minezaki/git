/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.student.dao.F11011Dao;
import jp.learningpark.modules.student.dto.F11011Dto;
import jp.learningpark.modules.student.service.F11011Service;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * <p>スマホ_メッセージ詳細</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/29 : zpa: 新規<br />
 * @version 7.0
 */
@Service
public class F11011ServiceImpl implements F11011Service {
    // 2020/11/12 zhangminghao modify start
    private final F11011Dao f11011Dao;

    public F11011ServiceImpl(F11011Dao f11011Dao) {
        this.f11011Dao = f11011Dao;
    }
    // 2020/11/12 zhangminghao modify end
    @Override
    public F11011Dto init(Integer messageId) {
        String stuId = ShiroUtils.getUserId();
        return f11011Dao.init(messageId, stuId);
    }
    // 2020/11/12 zhangminghao modify start
    @Override
    public void noticeConfirm(Integer messageId) {
        Timestamp latestTime = DateUtils.getSysTimestamp();
        String userId = ShiroUtils.getUserId();

        f11011Dao.updateOpenedFlg(messageId, userId, latestTime);
    }
    // 2020/11/12 zhangminghao modify end
}