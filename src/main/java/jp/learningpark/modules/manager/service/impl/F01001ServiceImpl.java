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
import jp.learningpark.modules.common.dao.MstCrmschLearnPrdDao;
import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.manager.dao.F01001Dao;
import jp.learningpark.modules.manager.dao.F01002Dao;
import jp.learningpark.modules.manager.dto.F01001Dto;
import jp.learningpark.modules.manager.service.F01001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>F01001_塾時期検索一覧画面 ServiceImpl</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/08 : xiong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F01001ServiceImpl implements F01001Service {

    /**
     * F001001_ Dao
     */
    @Autowired
    private F01001Dao f01001Dao;

    /**
     * F001001_ Dao
     */
    @Autowired
    private F01002Dao f01002Dao;

    /**
     * MstCrmschLearnPrdDao
     */
    @Autowired
    private MstCrmschLearnPrdDao mstCrmschLearnPrdDao;


    /**
     * 一覧情報
     * @param orgId 組織ID
     * @param brandCd ブランドコード
     * @param pageSize
     * @param limit
     * @return
     */
    @Override
    public List<F01001Dto> getUpplevInfomation(String orgId, String brandCd, Integer pageSize, Integer limit) {
        return f01001Dao.getUpplevInfomation(orgId, brandCd,pageSize,limit);
    }

    /**
     *
     * @param orgId 組織ID
     * @param brandCd ブランドコード
     * @return
     */
    @Override
    public Integer getTotalCount(String orgId, String brandCd) {
        return f01001Dao.getTotalCount(orgId,brandCd);
    }

    /**
     * 対応する組織を組織マスタから論理削除処理行う。
     * @param id
     * @return
     */
    @Override
    public R deleteById(Integer id) {
        // 塾学習期間データが生徒教科書選択管理マスタに存在するかどうかを判定する。
        if (!f01002Dao.selectOneByCrmId(id).equals(0)){
            return R.error(MessageUtils.getMessage("MSGCOMN0081","塾学習期間"));
        }
        // 管理者ID
        String userId = ShiroUtils.getUserId();
        MstCrmschLearnPrdEntity mstCrmschLearnPrdEntity = mstCrmschLearnPrdDao.selectById(id);
        // 更新項目
        mstCrmschLearnPrdEntity.setDelFlg(1);
        mstCrmschLearnPrdEntity.setUpdUsrId(userId);
        mstCrmschLearnPrdEntity.setUpdDatime(DateUtils.getSysTimestamp());
        mstCrmschLearnPrdDao.updateById(mstCrmschLearnPrdEntity);
        f01002Dao.deleteByCrmId(id);
        return R.ok();
    }
}
