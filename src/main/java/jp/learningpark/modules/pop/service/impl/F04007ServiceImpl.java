package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.modules.pop.dao.F04007Dao;
import jp.learningpark.modules.pop.dto.F04007Dto;
import jp.learningpark.modules.pop.service.F04007Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>保護者既読未読詳細一覧画面（ニュース）</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/06 : xiong: 新規<br />
 * @version 1.0
 */
@Service
public class F04007ServiceImpl implements F04007Service {

    @Autowired
    private F04007Dao f04007Dao;
    /**
     * お知らせ配信先、組織情報、保護者お知らせ閲覧状況より、取得する。　
     * @param noticeId セッションデータ．ＩＤ
     * @param orgId セッションデータ．組織ＩＤ
     * @param pageSize 1ページのMAX件数30件
     * @return
     */
    @Override
    public List<F04007Dto> selectGuardStuById(Integer noticeId, String orgId, Integer pageSize, String readFlg, Integer limit) {
        return f04007Dao.selectGuardStuById(noticeId,orgId,pageSize,readFlg,limit);
    }

    /**
     *
     * @param noticeId セッションデータ．ＩＤ
     * @param orgId    セッションデータ．組織ＩＤ
     * @param readFlg  既読未読フラグ
     * @return
     */
    @Override
    public Integer selectGuardCount(Integer noticeId, String orgId, String readFlg) {
        return f04007Dao.selectGuardCount(noticeId,orgId,readFlg);
    }
}
