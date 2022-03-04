/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.Query;
import jp.learningpark.modules.common.dao.MstAskTalkEventDao;
import jp.learningpark.modules.common.entity.MstAskTalkEventEntity;
import jp.learningpark.modules.common.service.MstAskTalkEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstAskTalkEventService")
@Transactional
public class MstAskTalkEventServiceImpl extends ServiceImpl<MstAskTalkEventDao, MstAskTalkEventEntity> implements MstAskTalkEventService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstAskTalkEventEntity> page = baseMapper.selectPage(
                new Query<MstAskTalkEventEntity>(params).getPage(),
                new QueryWrapper<MstAskTalkEventEntity>()
        );

        return new PageUtils(page);
    }

}
