/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.manager.dao.F08002Dao;
import jp.learningpark.modules.manager.dto.F08002AskTalkDto;
import jp.learningpark.modules.manager.dto.F08002Dto;
import jp.learningpark.modules.manager.dto.F08017Dto;
import jp.learningpark.modules.manager.service.F08002Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>F08002 イベント新規・編集画面 ServiceImpl</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/07/26 : wq: 新規<br />
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class F08002ServiceImpl implements F08002Service {

    /**
     * イベントマスタ　Dao
     */
    @Autowired
    MstEventDao mstEventDao;

    /**
     * MAX採番　Dao
     */
    @Autowired
    MstMaxIdDao mstMaxIdDao;

    /**
     * イベント配信先　Dao
     */
    @Autowired
    MstEventDeliverDao mstEventDeliverDao;

    /**
     * イベント新規・編集画面 Dao
     */
    @Autowired
    F08002Dao f08002Dao;

    /**
     * イベント新規・編集画面 Dao
     */
    @Autowired
    EventScheduleDao eventScheduleDao;

    /**
     * mstAskTalkEventDao
     */
    @Autowired
    MstAskTalkEventDao mstAskTalkEventDao;

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @param id イベントID
     * @return
     */
    @Override
    public F08002Dto getInitInfo(Integer id) {
        return f08002Dao.selectInitInfo(id);
    }

    @Override
    public List<F08017Dto> getEventAskList(Integer id, String orgId) {
        return f08002Dao.selectMstAskTalkData(id, orgId, true);
    }

    @Override
    public List<F08017Dto> getTemplateAskList(Integer id, String orgId) {
        return f08002Dao.selectMstAskTalkData(id, orgId, false);
    }

    /**
     * @param dto 引渡データ
     * @param eventCont イベント内容
     * @return
     */
    @Override
    public R doInsert(F08002Dto dto, String eventCont, List<F08002AskTalkDto> askTalkList) {

        try {
            dto.setCtgyNm(URLDecoder.decode(dto.getCtgyNm(),"UTF-8"));
            dto.setEventTitle(URLDecoder.decode(dto.getEventTitle(),"UTF-8"));
        } catch (UnsupportedEncodingException e){
            logger.error(e.getMessage());
        }
        //セッション組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();
        if (dto.getId() == null) {
            MstEventEntity mstEventEntity = new MstEventEntity();
            //MAXIDの連番を取得する
            MstMaxIdEntity mstMaxIdEntity = mstMaxIdDao.selectOne(new QueryWrapper<MstMaxIdEntity>().eq("org_id", orgId).eq("type_div", "m").eq("del_flg", 0));
            if (mstMaxIdEntity == null) {
                mstMaxIdEntity = new MstMaxIdEntity();

                //組織ID
                mstMaxIdEntity.setOrgId(orgId);
                //MAXID
                mstMaxIdEntity.setMaxId(1);
                //種類区分
                mstMaxIdEntity.setTypeDiv("m");
                //作成日時
                mstMaxIdEntity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                mstMaxIdEntity.setCretUsrId(userId);
                //更新日時
                mstMaxIdEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                mstMaxIdEntity.setUpdUsrId(userId);
                //削除フラグ
                mstMaxIdEntity.setDelFlg(0);

                mstMaxIdDao.insert(mstMaxIdEntity);
            } else {
                //MAXID
                mstMaxIdEntity.setMaxId(mstMaxIdEntity.getMaxId() + 1);
                //更新日時
                mstMaxIdEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                mstMaxIdEntity.setUpdUsrId(userId);

                mstMaxIdDao.update(mstMaxIdEntity, new QueryWrapper<MstMaxIdEntity>().eq("org_id", orgId).eq("type_div", "m").eq("del_flg", 0));
            }
            //イベントCD
            mstEventEntity.setEventCd(mstMaxIdEntity.getTypeDiv() + mstMaxIdEntity.getMaxId());
            //テンプレートID
            mstEventEntity.setTempId(dto.getTempId());
            //組織ID
            mstEventEntity.setOrgId(orgId);
            //レベルタップ区分
            mstEventEntity.setEventLevelDiv("0");
            //イベントタイトル
            mstEventEntity.setEventTitle(dto.getEventTitle());
            //イベント内容
            mstEventEntity.setEventCont(eventCont);
            //カテゴリ
            mstEventEntity.setCtgyNm(dto.getCtgyNm());
            //公開開始日時
            mstEventEntity.setPubStartDt(null);
            //公開終了日時
            mstEventEntity.setPubEndDt(null);
            //申込み可能開始日時
            mstEventEntity.setApplyStartDt(null);
            //申込み可能終了日時
            mstEventEntity.setApplyEndDt(null);
            //添付ファイルパス
            mstEventEntity.setAttachFilePath(dto.getAttachFilePath());
            //タイトル画像パス
            mstEventEntity.setTitleImgPath(dto.getTitleImgPath());
            //イベントステータス区分
            mstEventEntity.setEventStsDiv("1");
            //通知有無フラグ
            mstEventEntity.setNoitceFlg(null);
            //リマインド通知有無フラグ
            mstEventEntity.setRemandFlg(null);
            //変更可能期間
            mstEventEntity.setChgLimtDays(null);
            //関連タイプ
            mstEventEntity.setRefType(dto.getRefType());
            //1コマ時間
            mstEventEntity.setUnitTime(dto.getUnitTime());
            //作成ユーザＩＤ
            mstEventEntity.setCretUsrId(ShiroUtils.getUserId());
            //作成日時
            mstEventEntity.setCretDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstEventEntity.setUpdUsrId(ShiroUtils.getUserId());
            //更新日時
            mstEventEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //削除フラグ
            mstEventEntity.setDelFlg(0);
            try {
                mstEventDao.insert(mstEventEntity);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

            //イベント配信先登録
            for (int i = 0; i < dto.getOrgIdList().size(); i++) {
                MstEventDeliverEntity mstEventDeliverEntity = new MstEventDeliverEntity();
                String currentOrgId = dto.getOrgIdList().get(i).getOrgId();
                //組織ID
                mstEventDeliverEntity.setOrgId(currentOrgId);
                //イベントID
                mstEventDeliverEntity.setEventId(mstEventEntity.getId());
                //作成日時
                mstEventDeliverEntity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザー
                mstEventDeliverEntity.setCretUsrId(userId);
                //更新日時
                mstEventDeliverEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザー
                mstEventDeliverEntity.setUpdUsrId(userId);
                //削除フラグ
                mstEventDeliverEntity.setDelFlg(0);
                try {
                    mstEventDeliverDao.insert(mstEventDeliverEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            for (MstAskTalkEventEntity askTalkDto : askTalkList) {
                insertAskTalkEvent(orgId, userId, mstEventEntity, askTalkDto);
            }
        } else {
            //排他チェックエラーの場合、処理を中断し、エラー内容を画面の上部に表示する
            MstEventEntity mstEventEntity = mstEventDao.selectById(dto.getId());
            List<MstEventDeliverEntity> mstEventDeliverEntityList = mstEventDeliverDao.selectList(new QueryWrapper<MstEventDeliverEntity>().eq("event_id",dto.getId()).eq(
                    "del_flg",0));
            List<String> oldOrgIdList = new ArrayList<>();
            List<String> nowOrgIdList = new ArrayList<>();
            for (MstEventDeliverEntity mstEventDeliverEntity:mstEventDeliverEntityList) {
                oldOrgIdList.add(mstEventDeliverEntity.getOrgId());
            }
            for (OrgAndLowerOrgIdDto nowOrg:dto.getOrgIdList()) {
                nowOrgIdList.add(nowOrg.getOrgId());
            }
            if (mstEventEntity == null || !StringUtils.equals(mstEventEntity.getUpdDatime().toString(), dto.getUpdateStrForCheck())) {
                return R.error(MessageUtils.getMessage("MSGCOMN0019"));
            }
           if (!oldOrgIdList.equals(nowOrgIdList) || !StringUtils.equals(
                   mstEventEntity.getRefType(), dto.getRefType()) || !mstEventEntity.getUnitTime().equals(dto.getUnitTime())) {
               List<EventScheduleEntity> scheduleEntityList = eventScheduleDao.selectList(
                       new QueryWrapper<EventScheduleEntity>().eq("event_id", mstEventEntity.getId()).eq("del_flg", 0));
               if (scheduleEntityList.size() > 0) {
                   return R.error(MessageUtils.getMessage("MSGCOMN0081", "日程"));
               }
           }
            //テンプレートID
            mstEventEntity.setTempId(dto.getTempId());
            //イベントタイトル
            mstEventEntity.setEventTitle(dto.getEventTitle());
            //イベント内容
            mstEventEntity.setEventCont(eventCont);
            //カテゴリ
            mstEventEntity.setCtgyNm(dto.getCtgyNm());
            //添付ファイルパス
            mstEventEntity.setAttachFilePath(dto.getAttachFilePath());
            //タイトル画像パス
            mstEventEntity.setTitleImgPath(dto.getTitleImgPath());
            //関連タイプ
            mstEventEntity.setRefType(dto.getRefType());
            //1コマ時間
            mstEventEntity.setUnitTime(dto.getUnitTime());
            //更新日時
            mstEventEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstEventEntity.setUpdUsrId(userId);
            try {
                mstEventDao.updateById(mstEventEntity);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

            //イベント配信先の削除
            mstEventDeliverDao.delete(new QueryWrapper<MstEventDeliverEntity>().eq("event_id", dto.getId()).eq("del_flg", 0));

            //イベント配信先登録
            for (int i = 0; i < dto.getOrgIdList().size(); i++) {
                MstEventDeliverEntity mstEventDeliverEntity = new MstEventDeliverEntity();
                String currentOrgId = dto.getOrgIdList().get(i).getOrgId();
                //組織ID
                mstEventDeliverEntity.setOrgId(currentOrgId);
                //イベントID
                mstEventDeliverEntity.setEventId(mstEventEntity.getId());
                //作成日時
                mstEventDeliverEntity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザー
                mstEventDeliverEntity.setCretUsrId(userId);
                //更新日時
                mstEventDeliverEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザー
                mstEventDeliverEntity.setUpdUsrId(userId);
                //削除フラグ
                mstEventDeliverEntity.setDelFlg(0);
                try {
                    mstEventDeliverDao.insert(mstEventDeliverEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            mstAskTalkEventDao.delete(new QueryWrapper<MstAskTalkEventEntity>().eq("event_id", mstEventEntity.getId()));
            for (MstAskTalkEventEntity askTalkEventEntity : askTalkList) {
                askTalkEventEntity.setId(null);
                insertAskTalkEvent(orgId, userId, mstEventEntity, askTalkEventEntity);
            }
        }
        return R.ok();
    }

    /**
     * 更新
     * @param userId                ユーザーID
     * @param askTalkEventEntity    エンティティ
     */
    private void updateAskTalkEvent(String userId, MstAskTalkEventEntity askTalkEventEntity) {
        MstAskTalkEventEntity mstAskTalkEventEntity = mstAskTalkEventDao.selectById(askTalkEventEntity.getId());
        if (!DateUtils.format(mstAskTalkEventEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).equals(DateUtils.format(askTalkEventEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO))) {
            //排他チェックエラーの場合 MSGCOMN0019に戻る
            throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
        }
        //更新日時
        askTalkEventEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        askTalkEventEntity.setUpdUsrId(userId);
        //削除フラグ
        askTalkEventEntity.setDelFlg(0);
        mstAskTalkEventDao.updateById(askTalkEventEntity);
    }

    /**
     * 新規
     * @param orgId             組織ID
     * @param userId            ユーザーID
     * @param mstEventEntity    エンティティ
     * @param askTalkDto        エンティティ
     */
    private void insertAskTalkEvent(String orgId, String userId, MstEventEntity mstEventEntity, MstAskTalkEventEntity askTalkDto) {
        //組織ID
        askTalkDto.setOrgId(orgId);
        //テンプレートID
        askTalkDto.setEventId(mstEventEntity.getId());
        //作成日時
        askTalkDto.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        askTalkDto.setCretUsrId(userId);
        //更新日時
        askTalkDto.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        askTalkDto.setUpdUsrId(userId);
        //削除フラグ
        askTalkDto.setDelFlg(0);
        mstAskTalkEventDao.insert(askTalkDto);
    }
}
