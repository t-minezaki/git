package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.guard.dao.F30409Dao;
import jp.learningpark.modules.guard.dto.F30409Dto;
import jp.learningpark.modules.guard.service.F30409Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>F30409_保護者面談の申込内容変更・キャンセル画面 ServiceImpl</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/16: yang: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F30409ServiceImpl implements F30409Service {
    //f30409Dao
    @Autowired
    F30409Dao f30409Dao;

    /**
     * 保護者が各子供のイベント申込情報を確認するため、保護者申込状況マスタから情報を取得
     * @param guardId 保護者ＩＤ
     * @param systemTime システム日時
     * @param flg 判断マーク
     * @param stuId 生徒ID
     * @return
     */
    public List<F30409Dto> getStuList(String guardId, Timestamp systemTime,Integer flg,String stuId){
        return f30409Dao.getStuList(guardId,systemTime,flg,stuId);
    }
}
