/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.dao.MstCrmschLearnPrdDao;
import jp.learningpark.modules.common.dao.MstLearnSeasnDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.common.entity.MstLearnSeasnEntity;
import jp.learningpark.modules.manager.dao.F01002Dao;
import jp.learningpark.modules.manager.service.F01002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * <p>F01002_塾時期新規・変更画面 ServiceImpl</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/09 : xiong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F01002ServiceImpl implements F01002Service {

    /**
     * F01002Dao
     */
    @Autowired
    F01002Dao f01002Dao;

    /**
     * 塾学習期間マスタ Service
     */
    @Autowired
    private MstCrmschLearnPrdDao mstCrmschLearnPrdDao;

    /**
     * 学習時期マスタ Service
     */
    @Autowired
    private MstLearnSeasnDao mstLearnSeasnDao;

    /**
     * 塾学習期間マスタ
     * @return  塾学習期間一覧情報
     */
    @Override
    public List<MstCodDEntity> getCodValue() {
        return f01002Dao.selectCodeValue();
    }

    /**
     * 塾学習期間マスタ
     * @param orgId 組織ID
     * @param id    ID
     * @return      学年区分情報
     */
    @Override
    public MstCrmschLearnPrdEntity getCrmList(String orgId, Integer id) {
        return f01002Dao.selectByIdOrgId(orgId,id);
    }

    /**
     * 塾学習期間マスタへ反映する。
     * @param mst        塾学習期間マスタ
     * @param prdStartDy 計画期間開始日
     * @param prdEndDy   計画期間終了日
     * @param updateTime 更新日時
     * @return           塾学習期間更新情報
     */
    @Override
    public R insertOrUpdate(MstCrmschLearnPrdEntity mst, String prdStartDy, String prdEndDy, String updateTime) {
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 管理者ID
        String userId = ShiroUtils.getUserId();
        // タイムフォーマット変換
        Date startDy = DateUtils.parse(prdStartDy, Constant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        Date endDy = DateUtils.parse(prdEndDy,Constant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        // 画面．時期終了日を画面．時期開始日以降の日付で入力すること
        if (endDy.before(startDy)){
            return R.error(MessageUtils.getMessage("MSGCOMN0024","時期終了日","時期開始日"));
        }
        //Insert
        if (mst.getId() == null){
            // 塾学習期間マスタ登録
            mst.setOrgId(orgId);
            mst.setCretUsrId(userId);
            mst.setPlanPrdStartDy(startDy);
            mst.setPlanPrdEndDy(endDy);
            mst.setCretDatime(DateUtils.getSysTimestamp());
            mst.setUpdDatime(DateUtils.getSysTimestamp());
            mst.setUpdUsrId(userId);
            mstCrmschLearnPrdDao.insert(mst);
            mst.getId();
            if ("1".equals(mst.getUseFlg())){
                f01002Dao.updateCrm(orgId,mst.getId(),mst.getSchyDiv());
            }
        }
        //Update
        else {
            // 塾学習期間データが生徒教科書選択管理マスタに存在するかどうかを判定する。
            if (!f01002Dao.selectOneByCrmId(mst.getId()).equals(0)){
                return R.error(MessageUtils.getMessage("MSGCOMN0081","塾学習期間"));
            }
            // MstCrmschLearnPrdEntity
            MstCrmschLearnPrdEntity mst1 = mstCrmschLearnPrdDao.selectById(mst.getId());
            String nowTime = DateUtils.format(mst1.getUpdDatime(),Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            // 排他チェックエラーの場合
            if (!updateTime.equals(nowTime)){
                return R.error(MessageUtils.getMessage("MSGCOMN0019"));
            }
            // 更新項目
            mst.setPlanPrdStartDy(startDy);
            mst.setPlanPrdEndDy(endDy);
            mst.setUpdDatime(DateUtils.getSysTimestamp());
            mst.setCretDatime(mst1.getCretDatime());
            mst.setCretUsrId(mst1.getCretUsrId());
            mst.setUpdUsrId(userId);
            mstCrmschLearnPrdDao.updateById(mst);
            // 画面．適用フラグ（更新後）が「適用」、且つ、画面．適用フラグ（更新前）≠画面．適用フラグ（更新後）の場合、
            if ("1".equals(mst.getUseFlg())){
                f01002Dao.updateCrm(orgId,mst.getId(),mst.getSchyDiv());
            }
            f01002Dao.deleteByCrmId(mst1.getId());
        }
        // 画面．計画期間開始日（更新後）≠画面．計画期間開始日（更新前）、又は、画面．計画期間終了日（更新後）≠画面．計画期間終了日（更新前）の場合、
        // mstLearnSeasnEntity
        MstLearnSeasnEntity mstLearnSeasnEntity = new MstLearnSeasnEntity();
        // 学習時期マスタを登録する。
        mstLearnSeasnEntity.setCrmLearnPrdId(mst.getId());
        mstLearnSeasnEntity.setLearnSeasnStartDy(startDy);
        mstLearnSeasnEntity.setLearnSeasnEndDy(endDy);
        mstLearnSeasnEntity.setCretDatime(DateUtils.getSysTimestamp());
        mstLearnSeasnEntity.setCretUsrId(userId);
        mstLearnSeasnEntity.setUpdDatime(DateUtils.getSysTimestamp());
        mstLearnSeasnEntity.setUpdUsrId(userId);
        mstLearnSeasnEntity.setDelFlg(0);

        // 画面．計画期間開始日（更新後）より、当日の曜日を算出する。
        Calendar calendar = Calendar.getInstance();
        // 指定された日付の週開始日を取得する。
        Date date1 = DateUtils.getSunday(startDy);

        Date dateEnd = null;
        int week = 1;

        // 学習時期マスタを登録する。
        while (startDy.before(endDy)){
            mstLearnSeasnEntity.setPlanLearnSeasn(week);
            if (week == 1){
                mstLearnSeasnEntity.setLearnSeasnStartDy(startDy);
                mstLearnSeasnEntity.setLearnSeasnEndDy(date1);
                //  指定された日付の週開始日を取得する。
                calendar.setTime(date1);
            }
            else {
                mstLearnSeasnEntity.setLearnSeasnStartDy(startDy);
                mstLearnSeasnEntity.setLearnSeasnEndDy(dateEnd);
                //  指定された日付の週開始日を取得する。
                calendar.setTime(dateEnd);
            }
            // 指定された日付の週終了日を取得する。
            calendar.add(Calendar.DAY_OF_MONTH,1);
            startDy = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH,6);
            dateEnd = calendar.getTime();
            if (endDy.before(dateEnd)){
                dateEnd = endDy;
            }
            mstLearnSeasnDao.insert(mstLearnSeasnEntity);
            week++;
        }
        return R.ok(MessageUtils.getMessage("MSGCOMN0014","塾学習"));
    }
}
