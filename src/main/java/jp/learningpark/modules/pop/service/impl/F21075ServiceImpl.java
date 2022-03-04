/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.TalkRecordDDao;
import jp.learningpark.modules.common.dao.TalkRecordHDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.TalkRecordDEntity;
import jp.learningpark.modules.common.entity.TalkRecordHEntity;
import jp.learningpark.modules.pop.dao.F21075Dao;
import jp.learningpark.modules.pop.dto.F21075Dto;
import jp.learningpark.modules.pop.service.F21075Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>面談記録画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/25 : wq: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F21075ServiceImpl implements F21075Service {

    /**
     * 面談記録画面 Dao
     */
    @Autowired
    private F21075Dao f21075Dao;

    /**
     * コードマスタ明細 Dao
     */
    @Autowired
    private MstCodDDao mstCodDDao;

    /**
     * 面談記録 Dao
     */
    @Autowired
    private TalkRecordHDao talkRecordHDao;

    /**
     * 面談記録詳細 Dao
     */
    @Autowired
    private TalkRecordDDao talkRecordDDao;

    /**
     * @param talkId
     * @param eventId
     * @return
     */
    public R getInitData(Integer talkId, Integer eventId) {
        R info = R.ok();
        //イベント質問事項
        List<F21075Dto> f21075AskDtos = f21075Dao.getTalkItems(talkId, eventId, "0");
        //イベント面談事項
        List<F21075Dto> f21075Dtos = f21075Dao.getTalkItems(talkId, eventId, "1");
        info.put("f21075AskDtos", f21075AskDtos).put("f21075Dtos", f21075Dtos);
        //マスタコードから、事項類型区分を取得する。
        List<MstCodDEntity> mstCodDEntities = mstCodDDao.selectList(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "ITEM_TYPE_DIV").eq("del_flg", 0).orderByAsc("sort"));
        info.put("mstCodDEntities", mstCodDEntities);
        //質問事項
        List<TalkRecordDEntity> talkRecordDEntityAskList = talkRecordDDao.selectList(
                new QueryWrapper<TalkRecordDEntity>().select("ask_num", "question_name", "answer_type_div", "answer_relt_cont").eq("talk_id", talkId).eq("item_type_div", "0").eq(
                        "del_flg", 0).orderByAsc("ask_num"));
        info.put("talkRecordDEntityAskList", talkRecordDEntityAskList);
        //面談記録
        TalkRecordHEntity talkRecordHEntity = talkRecordHDao.selectById(talkId);
        if (talkRecordHEntity != null) {
            //代理登録フラグ
            boolean proxyFlg = f21075Dao.getProxyFlg(talkRecordHEntity.getId());
            info.put("talkRecordHEntity", talkRecordHEntity).put("proxyFlg", proxyFlg);
            if (!StringUtils.equals("0", talkRecordHEntity.getTalkApplyStsDiv())) {
                //面談事項
                List<TalkRecordDEntity> talkRecordDEntityTalkList = talkRecordDDao.selectList(
                        new QueryWrapper<TalkRecordDEntity>().select("ask_num", "question_name", "answer_relt_cont").eq("talk_id", talkId).eq("item_type_div",
                                "1").eq("del_flg", 0).orderByAsc("ask_num"));
                info.put("talkRecordDEntityTalkList", talkRecordDEntityTalkList);
            }
        }
        return info;
    }

    /**
     * @param talkId
     * @param flg
     * @param resultList
     * @param isProxy
     * @return
     */
    public R updateDB(Integer talkId, String flg, String resultList, boolean isProxy) {
        //面談情報
        List<F21075Dto> result = JSON.parseObject(StringUtils.defaultString(resultList), new TypeReference<List<F21075Dto>>() {
        });
        TalkRecordDEntity talkRecordDEntity = null;
        //編集の場合、面談詳細データを削除する。
        if (StringUtils.equals("1", flg)) {
            talkRecordDDao.delete(new QueryWrapper<TalkRecordDEntity>().eq("talk_id", talkId).eq("item_type_div", "1"));
        } else {
            TalkRecordHEntity talkRecordHEntity = talkRecordHDao.selectById(talkId);
            //面談申込状態区分
            talkRecordHEntity.setTalkApplyStsDiv("2");
            //更新ユーザーID
            talkRecordHEntity.setUpdUsrId(ShiroUtils.getUserId());
            //更新日時
            talkRecordHEntity.setUpdDatime(DateUtils.getSysTimestamp());
            try {
                talkRecordHDao.updateById(talkRecordHEntity);
            } catch (Exception e) {
                return R.error("面談記録の更新に失敗しました");
            }
        }
        //0723  代理登録の場合、質問事項を削除します。
        if (isProxy) {
            talkRecordDDao.delete(new QueryWrapper<TalkRecordDEntity>().eq("talk_id", talkId).eq("item_type_div", "0"));
        }

        for (F21075Dto f21075Dto : result) {
            talkRecordDEntity = new TalkRecordDEntity();
            //面談記録ID
            talkRecordDEntity.setTalkId(talkId);
            //事項類型区分
            talkRecordDEntity.setItemTypeDiv(f21075Dto.getItemTypeDiv());
            //質問番号
            talkRecordDEntity.setAskNum(f21075Dto.getAskNum());
            //回答形式区分
            talkRecordDEntity.setAnswerTypeDiv(f21075Dto.getAnswerTypeDiv());
            //回答結果内容
            talkRecordDEntity.setAnswerReltCont(f21075Dto.getContent());
            //設問名
            talkRecordDEntity.setQuestionName(f21075Dto.getQuestionName());
            //作成ユーザＩＤ
            talkRecordDEntity.setCretUsrId(ShiroUtils.getUserId());
            //作成日時
            talkRecordDEntity.setCretDatime(DateUtils.getSysTimestamp());
            //更新日時
            talkRecordDEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            talkRecordDEntity.setUpdUsrId(ShiroUtils.getUserId());
            //削除フラグ
            talkRecordDEntity.setDelFlg(0);

            try {
                talkRecordDDao.insert(talkRecordDEntity);
            } catch (Exception e) {
                return R.error("データベースの操作に失敗しました。");
            }
        }
        return R.ok();
    }
}
