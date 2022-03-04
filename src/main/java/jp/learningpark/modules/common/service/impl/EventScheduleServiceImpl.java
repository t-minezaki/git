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
import jp.learningpark.modules.common.dao.EventScheduleDao;
import jp.learningpark.modules.common.entity.EventScheduleEntity;
import jp.learningpark.modules.common.service.EventScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("eventScheduleService")
@Transactional
public class EventScheduleServiceImpl extends ServiceImpl<EventScheduleDao, EventScheduleEntity> implements EventScheduleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<EventScheduleEntity> page = baseMapper.selectPage(
                new Query<EventScheduleEntity>(params).getPage(),
                new QueryWrapper<EventScheduleEntity>()
        );

        return new PageUtils(page);
    }

}
