/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.dao.MstNoticeDao;
import jp.learningpark.modules.common.dao.MstOrgDao;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.utils.dao.CommonDao;
import jp.learningpark.modules.manager.dao.F04005Dao;
import jp.learningpark.modules.manager.dto.F04005Dto;
import jp.learningpark.modules.manager.service.F04005Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F04005 塾ニュース照会画面 ServiceImpl</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/14 : wen: 新規<br />
 * @version 1.0
 */
@Service
public class F04005ServiceImpl implements F04005Service {

    /**
     * 共通 Service
     */
    @Autowired
    private CommonDao commonDao;

    /**
     * 組織マスタ Service
     */
    @Autowired
    private MstOrgDao mstOrgDao;

    /**
     * お知らせマスタ Service
     */
    @Autowired
    private MstNoticeDao mstNoticeDao;

    /**
     * お知らせマスタ Service
     */
    @Autowired
    private F04005Dao f04005Dao;

    /**
     * @param id    セッションデータ.ID
     * @return
     */
    @Override
    public List<F04005Dto> getStuList(Integer id,String orgId) {
        //お知らせマスタを元に取得
        MstNoticeEntity mstNoticeEntity = mstNoticeDao.selectById(id);
        //配信先組織リストを取得
        List<F04005Dto> mstOrgEntityList = null;
        if (StringUtils.equals(mstNoticeEntity.getOrgId(), orgId)) {
            mstOrgEntityList = f04005Dao.getStuList(id);
        }
             else {
            mstOrgEntityList = f04005Dao.getotherStu(id, orgId);
        }
        return mstOrgEntityList;
    }
}
