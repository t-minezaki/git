/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;/**
 * GakkenAppApplication
 */

import jp.learningpark.modules.student.dao.F11014Dao;
import jp.learningpark.modules.student.dto.F11014Dto;
import jp.learningpark.modules.student.service.F11014Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/05/12 : lyh: 新規<br />
 * @version 1.0
 */
@Service
public class F11014ServiceImpl implements F11014Service {

    @Autowired
    F11014Dao f11014Dao;

    /**
     * @param monthStartDay 月開始日
     * @param monthEndDay 月終了日
     * @param eventId イベントID
     * @return
     */
    @Override
    public List<F11014Dto> getScheSts(Date monthStartDay, Date monthEndDay, Integer eventId) {
        return f11014Dao.selectScheSts(monthStartDay, monthEndDay, eventId);
    }

    /**
     * @param applyId 保護者イベント申込ID
     * @return
     */
    @Override
    public F11014Dto getReplyCnt(Integer applyId) {
        return f11014Dao.selectReplyCnt(applyId);
    }

    /**
     * @param tgtYmd 選択日
     * @param eventId イベントID
     * @return
     */
    @Override
    public List<F11014Dto> getTimeLine(Date tgtYmd, Integer eventId) {
        return f11014Dao.selectTimeLine(tgtYmd, eventId);
    }

    /**
     *
     * @param eventId
     * @return
     */
    @Override
    public List<F11014Dto> getAskTalk(Integer eventId) {
        return f11014Dao.getAskTalk(eventId);
    }
}