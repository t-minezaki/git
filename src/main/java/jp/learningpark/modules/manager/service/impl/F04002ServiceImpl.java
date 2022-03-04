/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.dao.GuardReadingStsDao;
import jp.learningpark.modules.common.dao.MstNoticeDao;
import jp.learningpark.modules.common.dao.MstNoticeDeliverDao;
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.entity.MstNoticeDeliverEntity;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.manager.dao.F04002Dao;
import jp.learningpark.modules.manager.dto.F04002Dto;
import jp.learningpark.modules.manager.dto.F04002DtoIn;
import jp.learningpark.modules.manager.service.F04002Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>F04002 塾ニュース新規画面 ServiceImpl</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/06 : wen: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F04002ServiceImpl implements F04002Service {
    /**
     * F04002 塾ニュース新規画面 Dao
     */
    @Autowired
    F04002Dao f04002Dao;

    /**
     * お知らせマスタ　dao
     */
    @Autowired
    MstNoticeDao mstNoticeDao;

    /**
     * お知らせ配信先 dao
     */
    @Autowired
    MstNoticeDeliverDao mstNoticeDeliverDao;

    /**
     * 保護者お知らせ閲覧状況登録
     */
    @Autowired
    GuardReadingStsDao guardReadingStsDao;

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 生徒りすとを取得する
     *
     * @param stuIdList
     * @return
     */
    public List<F04002Dto> getStuList(List<String> stuIdList) {
        return f04002Dao.getStuList(stuIdList);
    }

    /**
     * 生徒りすとを取得する
     *
     * @param stuIdList
     * @return
     */
    public List<F04002DtoIn> getList(List<String> stuIdList) {
        return f04002Dao.getList(stuIdList);
    }

    /**
     * DBセット
     *
     * @param dto
     * @param noticeCont
     * @param orgId
     * @return
     */
    @Override
    public R doInsert(F04002Dto dto, String noticeCont, String orgId) {
        try {
            dto.setNoticeTitle(URLDecoder.decode(dto.getNoticeTitle(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();

        //画面を元に、お知らせマスタへ登録する。
        MstNoticeEntity mstNoticeEntity = new MstNoticeEntity();
        //組織ID
        mstNoticeEntity.setOrgId(orgId);
        //お知らせタイトル
        mstNoticeEntity.setNoticeTitle(dto.getNoticeTitle());
        //お知らせ内容
        mstNoticeEntity.setNoticeCont(noticeCont);
        //お知らせタップ区分
        mstNoticeEntity.setNoticeTypeDiv("1");
        //お知らせレベル区分
        mstNoticeEntity.setNoticeLevelDiv(dto.getNoticeLevelDiv());
        //掲載予定開始日時
        mstNoticeEntity.setPubPlanStartDt(dto.getPubPlanStartDt());
        //掲載予定終了日時
        mstNoticeEntity.setPubPlanEndDt(dto.getPubPlanEndDt());
        //全体配信フラグ
        mstNoticeEntity.setAllDeliverFlg(dto.getAllDeliverFlg());
        //添付ファイルパス
        mstNoticeEntity.setAttachFilePath(dto.getAttachFilePath());
        //タイトル画像パス
        mstNoticeEntity.setTitleImgPath(dto.getTitleImgPath());
        //作成日時
        mstNoticeEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        mstNoticeEntity.setCretUsrId(userId);
        //更新日時
        mstNoticeEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        mstNoticeEntity.setUpdUsrId(userId);
        //削除フラグ
        mstNoticeEntity.setDelFlg(0);
        try {
            mstNoticeDao.insert(mstNoticeEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        //お知らせ配信先の登録
        for (int i = 0; i < dto.getStuList().size(); i++) {
            MstNoticeDeliverEntity mstNoticeDeliverEntity = new MstNoticeDeliverEntity();
            String currentOrgId = dto.getStuList().get(i).getOrgId();
            //組織ID
            mstNoticeDeliverEntity.setOrgId(currentOrgId);
            //お知らせID
            mstNoticeDeliverEntity.setNoticeId(mstNoticeEntity.getId());
            //生徒ID
            mstNoticeDeliverEntity.setStuId(dto.getStuList().get(i).getUsrId());
            //保護者ID
            mstNoticeDeliverEntity.setGuardId(dto.getStuList().get(i).getgUserId());
            //管理フラグ
            mstNoticeDeliverEntity.setMgrFlg(null);
            //作成日時
            mstNoticeDeliverEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザー
            mstNoticeDeliverEntity.setCretUsrId(userId);
            //更新日時
            mstNoticeDeliverEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザー
            mstNoticeDeliverEntity.setUpdUsrId(userId);
            //削除フラグ
            mstNoticeDeliverEntity.setDelFlg(0);
            try {
                mstNoticeDeliverDao.insert(mstNoticeDeliverEntity);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        //保護者お知らせ閲覧状況登録登録
        for (int i = 0; i < dto.getStuList().size(); i++) {
            GuardReadingStsEntity guardReadingStsEntity = new GuardReadingStsEntity();
            //組織ID
            guardReadingStsEntity.setOrgId(dto.getStuList().get(i).getOrgId());
            //お知らせID
            guardReadingStsEntity.setNoticeId(mstNoticeEntity.getId());
            //保護者ID
            if (dto.getStuList().get(i).getGId() == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0093"));
            }
            guardReadingStsEntity.setGuardId(dto.getStuList().get(i).getgUserId());
            //生徒ID
            guardReadingStsEntity.setStuId(dto.getStuList().get(i).getUsrId());
            //閲覧状況区分
            guardReadingStsEntity.setReadingStsDiv("0");
            //作成日時
            guardReadingStsEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザー
            guardReadingStsEntity.setCretUsrId(userId);
            //更新日時
            guardReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザー
            guardReadingStsEntity.setUpdUsrId(userId);
            //削除
            guardReadingStsEntity.setDelFlg(0);
            try {
                guardReadingStsDao.insert(guardReadingStsEntity);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return R.ok();
    }
}
