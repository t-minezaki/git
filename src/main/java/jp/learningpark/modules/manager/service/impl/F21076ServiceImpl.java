/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.dao.MstMessageDao;
import jp.learningpark.modules.common.dao.TalkReadingStsDao;
import jp.learningpark.modules.common.dao.TalkRecordHDao;
import jp.learningpark.modules.common.entity.MstMessageEntity;
import jp.learningpark.modules.common.entity.TalkReadingStsEntity;
import jp.learningpark.modules.common.entity.TalkRecordHEntity;
import jp.learningpark.modules.manager.dto.F21076Dto;
import jp.learningpark.modules.manager.service.F21076Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>面談記録結果配信設定画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/18 : wq: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F21076ServiceImpl implements F21076Service {

    /**
     * マスタメッセージ　Dao
     */
    @Autowired
    MstMessageDao mstMessageDao;

    /**
     *　メッセージ閲覧状況　Dao
     */
    @Autowired
    TalkReadingStsDao talkReadingStsDao;

    /**
     *　面談記録　Dao
     */
    @Autowired
    TalkRecordHDao talkRecordHDao;

    /**
     * @param talkIds
     * @param userList
     * @param flg
     * @param questionFlg
     * @param interviewFlg
     * @return
     */
    @Override
    public R insert(List<Integer> talkIds, List<F21076Dto> userList, String flg, String questionFlg, String interviewFlg) {
        MstMessageEntity mstMessageEntity;
        TalkReadingStsEntity talkReadingStsEntity;
        TalkRecordHEntity talkRecordHEntity;
        for (int i = 0;i<talkIds.size();i++) {

            talkRecordHEntity = talkRecordHDao.selectById(talkIds.get(i));
            //面談実施者
            talkRecordHEntity.setMentorId(ShiroUtils.getUserId());
            //面談申込状態区分「3：配信済」が設定する。
            talkRecordHEntity.setTalkApplyStsDiv("3");
            //更新日時
            talkRecordHEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザーID
            talkRecordHEntity.setUpdUsrId(ShiroUtils.getUserId());
            try {
                talkRecordHDao.updateById(talkRecordHEntity);
            } catch (Exception e) {
                throw new RRException("面談記録の更新に失敗しました");
            }

            mstMessageEntity = new MstMessageEntity();
            //組織ID
            mstMessageEntity.setOrgId(ShiroUtils.getUserEntity().getOrgId());
            //面談記録ID
            mstMessageEntity.setTalkId(talkIds.get(i));
            //メッセージタイトル・メッセージタップ区分
            if (StringUtils.equals("0", flg)) {
                mstMessageEntity.setMessageTitle("保護者への面談結果");
                mstMessageEntity.setMessageTypeDiv("3");
            } else if (StringUtils.equals("1", flg)) {
                mstMessageEntity.setMessageTitle("生徒への面談結果");
                mstMessageEntity.setMessageTypeDiv("4");
            } else {
                mstMessageEntity.setMessageTitle("面談結果");
                mstMessageEntity.setMessageTypeDiv("5");
            }
            //メッセージレベル区分
            mstMessageEntity.setMessageLevelDiv("1");
            //作成日時
            mstMessageEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            mstMessageEntity.setCretUsrId(ShiroUtils.getUserId());
            //更新日時
            mstMessageEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstMessageEntity.setUpdUsrId(ShiroUtils.getUserId());
            try {
                mstMessageDao.insert(mstMessageEntity);
            } catch (Exception e) {
                throw new RRException("データ追加時にエラーが発生しました。");
            }

            talkReadingStsEntity = new TalkReadingStsEntity();
            //組織ID
            talkReadingStsEntity.setOrgId(ShiroUtils.getUserEntity().getOrgId());
            //メッセージID
            talkReadingStsEntity.setMessageId(mstMessageEntity.getId());
            //閲覧状況区分
            talkReadingStsEntity.setReadingStsDiv("0");
            //質問事項表示フラグ
            if (!StringUtils.isEmpty(questionFlg)) {
                talkReadingStsEntity.setAskDispFlg(questionFlg);
            }
            //面談事項表示フラグ
            if (!StringUtils.isEmpty(interviewFlg)) {
                talkReadingStsEntity.setTalkDispFlg(interviewFlg);
            }
            //作成日時
            talkReadingStsEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            talkReadingStsEntity.setCretUsrId(ShiroUtils.getUserId());
            //更新日時
            talkReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            talkReadingStsEntity.setUpdUsrId(ShiroUtils.getUserId());
            //配信先ID
            try {
                if (StringUtils.equals("0", flg)) {
                    talkReadingStsEntity.setDeliverId(userList.get(i).getGuardId());
                    talkReadingStsDao.insert(talkReadingStsEntity);
                } else if (StringUtils.equals("1", flg)) {
                    talkReadingStsEntity.setDeliverId(userList.get(i).getStuId());
                    talkReadingStsDao.insert(talkReadingStsEntity);
                } else {
                    talkReadingStsEntity.setDeliverId(userList.get(i).getGuardId());
                    talkReadingStsDao.insert(talkReadingStsEntity);
                    talkReadingStsEntity.setDeliverId(userList.get(i).getStuId());
                    talkReadingStsDao.insert(talkReadingStsEntity);
                }
            } catch (Exception e) {
                throw new RRException("配信時にエラーが発生しました。");
            }
        }
        return R.ok();
    }
}
