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
import jp.learningpark.modules.common.dao.MstEventDao;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.service.MstEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstEventService")
@Transactional
public class MstEventServiceImpl extends ServiceImpl<MstEventDao, MstEventEntity> implements MstEventService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstEventEntity> page = baseMapper.selectPage(
                new Query<MstEventEntity>(params).getPage(),
                new QueryWrapper<MstEventEntity>()
        );

        return new PageUtils(page);
    }

}
