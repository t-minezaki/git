package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.dao.MstMessageDao;
import jp.learningpark.modules.common.dao.MstOrgDao;
import jp.learningpark.modules.common.entity.MstMessageEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.manager.dao.F21067Dao;
import jp.learningpark.modules.manager.service.F21067Service;
import jp.learningpark.modules.pop.dto.F21066Dto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/22 ： NWT)hxl ： 新規作成
 * @date 2020/05/22 14:22
 */
@Service
public class F21067ServiceImpl implements F21067Service {

    /**
     * f21067Dao
     */
    @Autowired
    F21067Dao f21067Dao;

    /**
     * mstOrgDao
     */
    @Autowired
    MstOrgDao mstOrgDao;

    /**
     * mstMessageDao
     */
    @Autowired
    MstMessageDao mstMessageDao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 全体の送信生徒を抽出
     *
     * @param orgId     組織ID
     * @param messageId メッセージＩＤ
     * @return
     */
    @Override
    public R init(String orgId, Integer messageId) {
        //組織情報を取得する
        MstOrgEntity mstOrgEntity = mstOrgDao.selectOne(new QueryWrapper<MstOrgEntity>()
                .select("org_id, org_nm")
                .eq("org_id", orgId).eq("del_flg", 0));
        //メッセージ情報を取得する
        MstMessageEntity mstMessageEntity = mstMessageDao.selectById(messageId);
        try {
            //message.titleのデコード
            mstMessageEntity.setMessageTitle(URLDecoder.decode(mstMessageEntity.getMessageTitle(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        //関連するすべての生徒情報を取得する
        List<F21066Dto> stuList = f21067Dao.getStuList(messageId);
        return R.ok().put("mstMessageEntity", mstMessageEntity).put("stuList", stuList).put("mstOrgEntity", mstOrgEntity);
    }
}
