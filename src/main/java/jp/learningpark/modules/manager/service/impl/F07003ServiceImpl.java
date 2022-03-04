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
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.MstUnitDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstUnitEntity;
import jp.learningpark.modules.manager.dao.F07003Dao;
import jp.learningpark.modules.manager.dto.F07003Dto;
import jp.learningpark.modules.manager.service.F07003Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>F70003_教科メンテナンス一覧画面 ServiceImpl</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/04/02 : wen: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F07003ServiceImpl implements F07003Service {

    /**
     * F70003_教科メンテナンス一覧画面 Dao
     */
    @Autowired
    F07003Dao f07003Dao;

    /**
     * 単元マスタ Dao
     */
    @Autowired
    MstUnitDao mstUnitDao;

    /**
     * コードマスタ Dao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * @param subjtCd 教科CD
     * @param subjtNm 教科名
     * @param commonType ブロックタイプ（共通）
     * @param reviewType ブロックタイプ（復習系のみ）
     * @param startRow 開始位置
     * @return コードマスタ情報リスト
     */
    @Override
    public List<F07003Dto> getMstCodDList(String subjtCd, String subjtNm, String commonType, String reviewType, Integer startRow) {
        return f07003Dao.selectMstCodDList(subjtCd, subjtNm, commonType, reviewType, startRow);
    }

    /**
     * @param subjtCd 教科CD
     * @param subjtNm 教科名
     * @param commonType ブロックタイプ（共通）
     * @param reviewType ブロックタイプ（復習系のみ）
     * @return コードマスタ情報記録数
     */
    @Override
    public Integer gettMstCodDListCount(String subjtCd, String subjtNm, String commonType, String reviewType) {
        return f07003Dao.selectMstCodDListCount(subjtCd, subjtNm, commonType, reviewType);
    }

    /**
     * <p>削除</p>
     *
     * @param subjtCd 教科CD
     * @param updateTm 更新時間
     * @return
     */
    @Override
    public R delete(String subjtCd, String updateTm) {
        List<MstUnitEntity> mstUnitEntityList = mstUnitDao.selectList(
                new QueryWrapper<MstUnitEntity>().and(wrapper->wrapper.eq("subjt_div", subjtCd).eq("del_flg", 0)));
        if (mstUnitEntityList.size() != 0) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0092", "教科"));
        }
        //教科CDより、コードマスタ取得
        MstCodDEntity mstCodDEntity = mstCodDDao.selectOne(
                new QueryWrapper<MstCodDEntity>().and(wrapper->wrapper.eq("cod_key", "SUBJT_DIV").eq("cod_cd", subjtCd).eq("del_flg", 0)));
        //更新日時判断する
        if (mstCodDEntity != null && StringUtils.equals(
                updateTm, DateUtils.format(mstCodDEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS))) {
            mstCodDDao.delete(new QueryWrapper<MstCodDEntity>().and(wrapper->wrapper.eq("cod_key", "SUBJT_DIV").eq("cod_cd", subjtCd).eq("del_flg", 0)));
        } else {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
        }
        return R.ok();
    }
}
