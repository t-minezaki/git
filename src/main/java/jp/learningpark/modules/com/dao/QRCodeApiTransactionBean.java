package jp.learningpark.modules.com.dao;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.modules.com.dto.QRCodeApiDto;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.GuardReadingStsDao;
import jp.learningpark.modules.common.dao.MstNoticeDao;
import jp.learningpark.modules.common.dao.MstNoticeDeliverDao;
import jp.learningpark.modules.common.dao.VariousScannerTokenMstDao;
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.entity.MstNoticeDeliverEntity;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.entity.VariousScannerTokenMstEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/11/23 ： NWT)hxl ： 新規作成
 * @date 2020/11/23 12:40
 */
@Component
public class QRCodeApiTransactionBean {
    /**
     * mstNoticeDao
     */
    @Autowired
    MstNoticeDao mstNoticeDao;

    /**
     * mstNoticeDeliverDao
     */
    @Autowired
    MstNoticeDeliverDao mstNoticeDeliverDao;

    /**
     * guardReadingStsDao
     */
    @Autowired
    GuardReadingStsDao guardReadingStsDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer sendNotice(String loginId, Timestamp sysTimestamp, QRCodeApiDto mstStuEntity, String goSchoolFlag){
        //お知らせへ登録
        String stuName = mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm();
        MstNoticeEntity mstNoticeEntity = new MstNoticeEntity();
        mstNoticeEntity.setOrgId(mstStuEntity.getOrgId());
        mstNoticeEntity.setNoticeTitle(goSchoolFlag + "のお知らせ");
        mstNoticeEntity.setNoticeCont(stuName + "様が" + goSchoolFlag + "しました。" + "</br>" + goSchoolFlag + "日時：" + DateUtils.format(sysTimestamp,
                GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_MAIL));
        mstNoticeEntity.setNoticeTypeDiv("3");
        mstNoticeEntity.setNoticeLevelDiv("1");
        mstNoticeEntity.setPubPlanStartDt(sysTimestamp);
        mstNoticeEntity.setPubPlanEndDt(DateUtils.toTimestamp(DateUtils.addDays(sysTimestamp, 7)));
        mstNoticeEntity.setAllDeliverFlg(null);
        mstNoticeEntity.setAttachFilePath(null);
        mstNoticeEntity.setTitleImgPath(null);
        mstNoticeEntity.setCretDatime(sysTimestamp);
        mstNoticeEntity.setCretUsrId(loginId);
        mstNoticeEntity.setUpdDatime(sysTimestamp);
        mstNoticeEntity.setUpdUsrId(loginId);
        mstNoticeEntity.setDelFlg(0);
        mstNoticeDao.insert(mstNoticeEntity);
        Integer noticeId = mstNoticeEntity.getId();
        //お知らせ配信先へ登録
        MstNoticeDeliverEntity mstNoticeDeliverEntity = new MstNoticeDeliverEntity();
        mstNoticeDeliverEntity.setOrgId(mstStuEntity.getOrgId());
        mstNoticeDeliverEntity.setNoticeId(noticeId);
        mstNoticeDeliverEntity.setMgrFlg(null);
        mstNoticeDeliverEntity.setStuId(mstStuEntity.getStuId());
        mstNoticeDeliverEntity.setGuardId(mstStuEntity.getGuardId());
        mstNoticeDeliverEntity.setCretDatime(sysTimestamp);
        mstNoticeDeliverEntity.setCretUsrId(loginId);
        mstNoticeDeliverEntity.setUpdDatime(sysTimestamp);
        mstNoticeDeliverEntity.setUpdUsrId(loginId);
        mstNoticeDeliverEntity.setDelFlg(0);
        mstNoticeDeliverDao.insert(mstNoticeDeliverEntity);
//        deliverId = mstNoticeDeliverEntity.getId();
        //保護者お知らせ閲覧状況へ登録
        GuardReadingStsEntity guardReadingStsEntity = new GuardReadingStsEntity();
        guardReadingStsEntity.setOrgId(mstStuEntity.getOrgId());
        guardReadingStsEntity.setNoticeId(noticeId);
        guardReadingStsEntity.setGuardId(mstStuEntity.getGuardId());
        guardReadingStsEntity.setStuId(mstStuEntity.getStuId());
        guardReadingStsEntity.setReadingStsDiv("0");
        guardReadingStsEntity.setCretDatime(sysTimestamp);
        guardReadingStsEntity.setCretUsrId(loginId);
        guardReadingStsEntity.setUpdDatime(sysTimestamp);
        guardReadingStsEntity.setUpdUsrId(loginId);
        guardReadingStsEntity.setDelFlg(0);
        guardReadingStsDao.insert(guardReadingStsEntity);
        return noticeId;
    }
}
