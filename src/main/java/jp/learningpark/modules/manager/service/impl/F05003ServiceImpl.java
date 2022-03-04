/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.models.auth.In;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.GuardReadingStsDao;
import jp.learningpark.modules.common.dao.MstNoticeDao;
import jp.learningpark.modules.common.dao.MstNoticeDeliverDao;
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.common.entity.MstNoticeDeliverEntity;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.service.MstDeviceTokenService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dao.F30112Dao;
import jp.learningpark.modules.manager.dao.F05003Dao;
import jp.learningpark.modules.manager.dto.F05003Dto;
import jp.learningpark.modules.manager.dto.F05003DtoIn;
import jp.learningpark.modules.manager.service.F05003Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F05003 知らせ編集画面 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/02/26 : gong: 新規<br />
 * 2019/06/18 : hujiaxing: mod<br />
 * @version 1.0
 */
@Service
@Transactional
public class F05003ServiceImpl implements F05003Service {
    /**
     * F05003 知らせ編集画面 Dao
     */
    @Autowired
    F05003Dao f05003Dao;
    /**
     * F05003 知らせ編集画面-通知プッシュ
     */
    @Autowired
    NoticeApiService noticeApiService;

    @Autowired
    MstDeviceTokenService mstDeviceTokenService;
    /**
     * お知らせマスター Dao
     */
    @Autowired
    private MstNoticeDao mstNoticeDao;

    /**
     * お知らせ配信先マスター  dao
     */
    @Autowired
    private MstNoticeDeliverDao mstNoticeDeliverDao;

    /**
     * 保護者お知らせ閲覧状況　dao
     */
    @Autowired
    private GuardReadingStsDao guardReadingStsDao;

    /**
     * F30112Dao
     */
    @Autowired
    private F30112Dao f30112Dao;
    /**
     * commonService
     */
    @Autowired
    private CommonService commonService;

    private final String RESEND_FLAG = "1";

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>該当組織対応する生徒、保護者リストを取得する</p>
     *
     * @param orgIdList 組織IDList
     * @return
     */
    @Override
    public List<F05003Dto> getStuAndGuardList(List<String> orgIdList) {
        return f05003Dao.selectStuAndGuardList(orgIdList);
    }

    /**
     * 登録処理
     *
     * @param dto
     * @param noticeCont 　ニュースの内容
     * @param orgId      組織ID
     * @return
     */
    @Override
    public R doInsert(F05003Dto dto, String noticeCont, String orgId) {
        //2020/12/15 wyh: mod start
        String reSentFlg = dto.getReSentFlg();
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();
        MstNoticeEntity mstNoticeEntity = mstNoticeDao.selectById(dto.getId());
        if (!(RESEND_FLAG).equals(reSentFlg)) {
            try {
                dto.setNoticeTitle(URLDecoder.decode(dto.getNoticeTitle(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
            }
            //排他チェックエラーの場合、処理を中断し、エラー内容を画面の上部に表示する
            if (mstNoticeEntity == null || !org.apache.commons.lang3.StringUtils.equals(DateUtils.format(mstNoticeEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS), dto.getUpdateStrCheck())) {
                return R.error(MessageUtils.getMessage("MSGCOMN0019"));
            }
            //お知らせタイトル
            mstNoticeEntity.setNoticeTitle(dto.getNoticeTitle());
            //お知らせ内容
            mstNoticeEntity.setNoticeCont(noticeCont);
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
            //更新日時
            mstNoticeEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstNoticeEntity.setUpdUsrId(userId);
            try {
                mstNoticeDao.updateById(mstNoticeEntity);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            //お知らせ配信先の削除
            mstNoticeDeliverDao.delete(new QueryWrapper<MstNoticeDeliverEntity>().eq("notice_id", dto.getId()));

            //お知らせ配信先の登録
            for (int i = 0; i < dto.getStuList().size(); i++) {
                MstNoticeDeliverEntity mstNoticeDeliverEntity = new MstNoticeDeliverEntity();
                String currentOrgId = dto.getStuList().get(i).getOrgId();
                //組織ID
                mstNoticeDeliverEntity.setOrgId(currentOrgId);
                //お知らせID
                mstNoticeDeliverEntity.setNoticeId(dto.getId());
                //生徒ID
                mstNoticeDeliverEntity.setStuId(dto.getStuList().get(i).getStuId());
                //保護者ID
                mstNoticeDeliverEntity.setGuardId(dto.getStuList().get(i).getGuardId());
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
            //保護者お知らせ閲覧状況の削除
            guardReadingStsDao.delete(new QueryWrapper<GuardReadingStsEntity>().eq("notice_id", dto.getId()));
            //保護者お知らせ閲覧状況の登録
            for (int i = 0; i < dto.getStuList().size(); i++) {
                //2020/12/15 wyh: mod end
                GuardReadingStsEntity guardReadingStsEntity = new GuardReadingStsEntity();
                //組織ID
                guardReadingStsEntity.setOrgId(dto.getStuList().get(i).getOrgId());
                //お知らせID
                guardReadingStsEntity.setNoticeId(dto.getId());
                //保護者ID
                if (dto.getStuList().get(i).getGuardId() == null) {
                    return R.error(MessageUtils.getMessage("MSGCOMN0093"));
                }
                guardReadingStsEntity.setGuardId(dto.getStuList().get(i).getGuardId());
                //生徒ID
                guardReadingStsEntity.setStuId(dto.getStuList().get(i).getStuId());
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
        }
        return R.ok().put("mstNoticeEntity", mstNoticeEntity);
    }

    /**
     * <p>get student dto list by student id list</p>
     *
     * @param stuIdList student id list
     * @return
     */
    @Override
    public List<F05003DtoIn> getStuByIdList(Integer noticeId) {
        return f05003Dao.selectStuByIdList(noticeId);
    }
}
