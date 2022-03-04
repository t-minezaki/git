/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstUnitDao;
import jp.learningpark.modules.common.dao.TextbDefTimeInfoDao;
import jp.learningpark.modules.common.entity.MstUnitEntity;
import jp.learningpark.modules.common.entity.TextbDefTimeInfoEntity;
import jp.learningpark.modules.pop.dto.F03005Dto;
import jp.learningpark.modules.pop.service.F03005Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>F03005_単元追加・編集画面 ServiceImpl</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/01/13 : wen: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F03005ServiceImpl implements F03005Service {

    /**
     * <p>単元マスタ</p>
     * <p>
     * MstUnitDao Dao
     */
    @Autowired
    MstUnitDao mstUnitDao;

    /**
     * <p>教科書デフォルトターム情報</p>
     * <p>
     * TextbDefTimeInfoDao Dao
     */
    @Autowired
    TextbDefTimeInfoDao textbDefTimeInfoDao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>単元追加・編集画面</p>
     * <p>
     * F03005 Dto
     *
     * @return
     */
    @Override
    public R addMstUnitInfo(F03005Dto f03005Dto) {
        MstUnitEntity mstUnitEntity = new MstUnitEntity();
        //セッションユーザーID
        String userId = ShiroUtils.getUserId();
        //セッション組織ＩＤ
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //新規の場合、
        if (f03005Dto.getId() == null) {
            //組織ID
            mstUnitEntity.setOrgId(orgId);
            //学年区分
            mstUnitEntity.setSchyDiv(f03005Dto.getSchyDiv());
            //教科区分
            mstUnitEntity.setSubjtDiv(f03005Dto.getSubjtDiv());
            //章名
            mstUnitEntity.setChaptNm(f03005Dto.getChaptNm());
            //節名
            mstUnitEntity.setSectnNm(f03005Dto.getSectnNm());
            //単元名
            mstUnitEntity.setUnitNm(f03005Dto.getUnitNm());
            //単元管理CDの一意チェック行う
            if (!StringUtils.isEmpty(f03005Dto.getUnitMstCd())) {
                List<MstUnitEntity> mstUnitEntityList = mstUnitDao.selectList(new QueryWrapper<MstUnitEntity>().and(wrapper -> wrapper.eq("unit_mst_cd", f03005Dto.getUnitMstCd().trim()).eq("del_flg", 0)));
                if (mstUnitEntityList.size() > 0) {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0062", "単元管理CD", "別の単元管理CD"));
                }
            }
            //単元管理CD
            mstUnitEntity.setUnitMstCd(f03005Dto.getUnitMstCd());
            //作成日時
            mstUnitEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            mstUnitEntity.setCretUsrId(userId);
            //更新日時
            mstUnitEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstUnitEntity.setUpdUsrId(userId);
            //削除フラグ
            mstUnitEntity.setDelFlg(0);
            try {
                mstUnitDao.insert(mstUnitEntity);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RRException(MessageUtils.getMessage("MSGCOMN0020"));
            }
        } else {
            mstUnitEntity = mstUnitDao.selectOne(new QueryWrapper<MstUnitEntity>().and(w -> w.eq("id", f03005Dto.getId()).eq("del_flg", 0)));
            //教科書デフォルトターム情報の存在チェックの存在チェック行う。
            List<TextbDefTimeInfoEntity> textbDefTimeInfoEntityList = textbDefTimeInfoDao.selectList(new QueryWrapper<TextbDefTimeInfoEntity>().and(wrapper -> wrapper.eq("unit_id", f03005Dto.getId()).eq("del_flg", 0)));
            if (textbDefTimeInfoEntityList.size() > 0) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0081", "単元"));
            }
            // 編集の場合、
            if (mstUnitEntity != null && StringUtils.equals(DateUtils.format(mstUnitEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS), f03005Dto.getUpdateTimeStr())) {
//                mstUnitEntity.setId(f03005Dto.getId());
                //学年区分
                mstUnitEntity.setSchyDiv(f03005Dto.getSchyDiv());
                //教科区分
                mstUnitEntity.setSubjtDiv(f03005Dto.getSubjtDiv());
                //章名
                mstUnitEntity.setChaptNm(f03005Dto.getChaptNm());
                //節名
                mstUnitEntity.setSectnNm(f03005Dto.getSectnNm());
                //単元管理CDの一意チェック行う
                if (!StringUtils.isEmpty(f03005Dto.getUnitMstCd())) {
                    List<MstUnitEntity> mstUnitEntityList = mstUnitDao.selectList(new QueryWrapper<MstUnitEntity>().and(wrapper -> wrapper.eq("unit_mst_cd", f03005Dto.getUnitMstCd().trim()).eq("del_flg", 0)));
                    if (mstUnitEntityList.size() > 0) {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0062", "単元管理CD", "別の単元管理CD"));
                    }
                }
                //単元管理CD
                mstUnitEntity.setUnitMstCd(f03005Dto.getUnitMstCd());
                //単元名
                mstUnitEntity.setUnitNm(f03005Dto.getUnitNm());
                //更新日時
                mstUnitEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                mstUnitEntity.setUpdUsrId(userId);
                try {
                    mstUnitDao.updateById(mstUnitEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            } else {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
            }
        }
        return R.ok();
    }
}
