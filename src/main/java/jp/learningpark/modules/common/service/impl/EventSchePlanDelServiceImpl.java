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
import jp.learningpark.modules.common.dao.EventSchePlanDelDao;
import jp.learningpark.modules.common.entity.EventSchePlanDelEntity;
import jp.learningpark.modules.common.service.EventSchePlanDelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("eventSchePlanDelService")
@Transactional
public class EventSchePlanDelServiceImpl extends ServiceImpl<EventSchePlanDelDao, EventSchePlanDelEntity> implements EventSchePlanDelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<EventSchePlanDelEntity> page = baseMapper.selectPage(
                new Query<EventSchePlanDelEntity>(params).getPage(),
                new QueryWrapper<EventSchePlanDelEntity>()
        );

        return new PageUtils(page);
    }

}
