/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstGrpDao;
import jp.learningpark.modules.common.dao.StuGrpDao;
import jp.learningpark.modules.common.entity.MstGrpEntity;
import jp.learningpark.modules.common.entity.StuGrpEntity;
import jp.learningpark.modules.manager.dao.F00051Dao;
import jp.learningpark.modules.manager.dto.F00051Dto;
import jp.learningpark.modules.manager.service.F00051Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>F00051 グループ検索一覧画面 ServiceImpl</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/18 : wen: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F00051ServiceImpl implements F00051Service {
    /**
     * F00051 グループ検索一覧画面 Dao
     */
    @Autowired
    F00051Dao f00051Dao;

    /**
     * グループマスタ Dao
     */
    @Autowired
    MstGrpDao mstGrpDao;

    /**
     * グループマスタ Dao
     */
    @Autowired
    StuGrpDao stuGrpDao;

    /**
     *
     * @param orgIdList    組織ＩＤリステ
     * @param grpNm    グループ名
     * @param startRow 開始位置
     * @return
     */
    @Override
    public List<F00051Dto> getGrpList(List<String> orgIdList, String grpNm, Integer startRow) {
        return f00051Dao.selectGrpList(orgIdList, grpNm, startRow);
    }

    /**
     *
     * @param orgId    組織ＩＤ
     * @param grpNm    グループ名
     * @return
     */
    @Override
    public Integer getGrpListCount(String orgId, String grpNm) {
        return f00051Dao.selectGrpListCount(orgId, grpNm);
    }

    /**
     * @param grpId    グループID
     * @param updateTm 更新時間
     * @return
     */
    @Override
    public R delete(Integer grpId, String updateTm) {
        //セッショングループIDより、グループマスタ取得
        MstGrpEntity mstGrpEntity = mstGrpDao.selectById(grpId);
        //セッションユーザーID取得
        String userId = ShiroUtils.getUserId();
        //セッショングループIDより、生徒グループ管理リスト取得
        List<StuGrpEntity> stuGrpEntityList = stuGrpDao.selectList(new QueryWrapper<StuGrpEntity>().and(wrapper -> wrapper.eq("grp_id", grpId).eq("del_flg", 0)));
        if (stuGrpEntityList.size() <= 0) {
            //更新日時判断する
            if (mstGrpEntity != null && StringUtils.equals(updateTm, DateUtils.format(mstGrpEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS))) {
                //更新時間
                mstGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザーID
                mstGrpEntity.setUpdUsrId(userId);
                //削除フラグ
                mstGrpEntity.setDelFlg(1);
                //論理削除
                mstGrpDao.updateById(mstGrpEntity);
            } else {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
            }
        } else {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0081", "当グループ"));
        }
        return R.ok();
    }
}
