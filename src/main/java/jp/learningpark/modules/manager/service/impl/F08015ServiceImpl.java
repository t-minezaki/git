/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F08015Dao;
import jp.learningpark.modules.manager.dto.F08015Dto;
import jp.learningpark.modules.manager.service.F08015Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F08015_配信設定/状況確認の代理登録画面(POP) ServiceImpl</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/19 : yang: 新規<br />
 * @version 1.0
 */
@Service
public class F08015ServiceImpl implements F08015Service {
    /**
     * F08015Dao
     */
    @Autowired
    private F08015Dao f08015Dao;

    /**
     * イベント．イベントタイトルの取得
     * @param id
     * @return
     */
    @Override
    public F08015Dto getEventSchedulePlanDel(Integer id){
        return f08015Dao.getEventSchedulePlanDel(id);
    }

    /**
     * イベン候補者取得
     * @param orgId
     * @param eventTitle
     * @return
     */
    @Override
    public List<F08015Dto> getEventList(String orgId,String eventTitle){
        return f08015Dao.getEventList(eventTitle,orgId);
    }

    /**
     * 生徒候補者取得
     *
     * @param orgId
     * @param eventId
     * @return
     */
    @Override
    public List<F08015Dto> getStuList(String orgId, Integer eventId, Boolean userFlag) {
        return f08015Dao.getStuList(orgId, eventId, userFlag);
    }

    /**
     * イベントの取得
     * @param orgId
     * @param eventTitle
     * @return
     */
    @Override
    public List<F08015Dto> getEventEntitylist(String orgId,String eventTitle){
        return f08015Dao.getEventEntity(eventTitle,orgId);
    }

    /**
     * 生徒の取得
     * @param orgId
     * @param stuName
     * @return
     */
    @Override
    public List<F08015Dto> getStuEntityList(String orgId,String stuName){
        return f08015Dao.getStuEntity(orgId,stuName);
    }

    /**
     * 先生候補者取得
     * @param mentorName
     * @param orgId
     * @return
     */
    @Override
    public List<F08015Dto> getMentorList(String mentorName,String orgId){
        return f08015Dao.getMentorList(mentorName,orgId);
    }

    /**
     * 先生の取得
     * @param displayName
     * @param eventId
     * @return
     */
    @Override
    public List<F08015Dto> getMentor(String displayName,Integer eventId){
        return f08015Dao.getMentor(displayName,eventId);
    }

    /**
     * 保護者イベント申込状況を取得
     *
     * @param geasId
     * @return
     */
    @Override
    public F08015Dto getDeliver(Integer geasId, Boolean userFlag) {
        return f08015Dao.getDeliver(geasId, userFlag);
    };

}
