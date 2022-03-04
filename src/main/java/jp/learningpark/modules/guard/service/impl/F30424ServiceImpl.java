/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.TalkReadingStsDao;
import jp.learningpark.modules.common.dao.TalkRecordDDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.TalkReadingStsEntity;
import jp.learningpark.modules.common.entity.TalkRecordDEntity;
import jp.learningpark.modules.guard.dao.F30424Dao;
import jp.learningpark.modules.guard.dto.F30424Dto;
import jp.learningpark.modules.guard.service.F30424Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>F30424_保護者面談記録詳細画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/21 : wq: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F30424ServiceImpl implements F30424Service {

    /**
     * 保護者面談記録詳細画面 Dao
     */
    @Autowired
    private F30424Dao f30424Dao;

    /**
     * メッセージ閲覧状況
     */
    @Autowired
    private TalkReadingStsDao talkReadingStsDao;

    /**
     * コードマスタ明細
     */
    @Autowired
    private MstCodDDao mstCodDDao;

    /**
     * 面談記録詳細
     */
    @Autowired
    private TalkRecordDDao talkRecordDDao;

    /**
     * @param guardId 保護者ID
     * @param messageId メッセージID
     * @param orgId 事項類型区分
     * @return
     */
    @Override
    public F30424Dto selectMessageInfo(String guardId, Integer messageId, String orgId) {
        return f30424Dao.getMessageInfo(guardId, messageId, orgId);
    }

    /**
     * @param messageId メッセージID
     * @return
     */
    @Override
    public R getInitData(Integer messageId) {
        TalkReadingStsEntity talkReadingStsEntity = null;
        //メッセージデータを取得
        F30424Dto f30424Dto = f30424Dao.getMessageInfo(ShiroUtils.getUserId(), messageId, ShiroUtils.getUserEntity().getOrgId());
        //上記取得できない場合、
        if (f30424Dto == null) {
            return R.error("データが存在しません");
        }
        //取得した「閲覧状況区分」が「未読」の場合、閲覧状況の更新を行う
        if (StringUtils.equals("0", f30424Dto.getReadingStsDiv())) {
            talkReadingStsEntity = talkReadingStsDao.selectById(f30424Dto.getReadingStsId());
            talkReadingStsEntity.setReadingStsDiv("1");
            talkReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
            talkReadingStsEntity.setUpdUsrId(ShiroUtils.getUserId());
            try {
                talkReadingStsDao.updateById(talkReadingStsEntity);
            } catch (Exception e) {
                return R.error("データベースの更新に失敗しました。");
            }
        }
        //マスタコードから、事項類型区分を取得する。
        List<MstCodDEntity> mstCodDEntities = mstCodDDao.selectList(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "ITEM_TYPE_DIV").eq("del_flg", 0).orderByAsc("sort"));
        //質問事項
        List<TalkRecordDEntity> talkRecordDEntityAskList = talkRecordDDao.selectList(
                new QueryWrapper<TalkRecordDEntity>().select("ask_num", "question_name", "answer_relt_cont","answer_type_div").eq("talk_id", f30424Dto.getTalkId()).eq(
                        "item_type_div", "0").isNotNull("answer_type_div").ne("answer_type_div", "").isNotNull("question_name").ne("question_name", "").eq(
                        "del_flg", 0).orderByAsc("ask_num"));
        //面談事項
        List<TalkRecordDEntity> talkRecordDEntityTalkList = talkRecordDDao.selectList(
                new QueryWrapper<TalkRecordDEntity>().select("ask_num", "question_name", "answer_relt_cont","answer_type_div").eq("talk_id", f30424Dto.getTalkId()).eq(
                        "item_type_div", "1").isNotNull("answer_type_div").ne("answer_type_div", "").isNotNull("question_name").ne("question_name", "").eq(
                        "del_flg", 0).orderByAsc("ask_num"));
        return R.ok().put("f30424Dto", f30424Dto).put("mstCodDEntities", mstCodDEntities).put("talkRecordDEntityAskList", talkRecordDEntityAskList).put(
                "talkRecordDEntityTalkList", talkRecordDEntityTalkList);
    }
}
