/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F08006Dao;
import jp.learningpark.modules.manager.dto.F08006Dto;
import jp.learningpark.modules.manager.service.F08006Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F08006_イベント日程時間設定画面(POP) ServiceImpl</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/01 : yang: 新規<br />
 * @version 1.0
 */
@Service
public class F08006ServiceImpl implements F08006Service {
    /**
     * F08006Dao
     */
    @Autowired
    private F08006Dao f08006Dao;

    /**
     * 候補者リストを取得
     * @param mentorName
     * @param orgId
     * @return
     */
    @Override
    public List<F08006Dto> getMentorList(String mentorName,String orgId){
        return f08006Dao.getMentorList(mentorName,orgId);
    }

    /**
     * ユーザー存在チェック
     * @param orgId
     * @param mentorId
     * @return
     */
    @Override
    public F08006Dto getuserEntity(String orgId,String mentorId){
        return f08006Dao.getuserEntity(orgId,mentorId);
    }

}
