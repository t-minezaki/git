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
import jp.learningpark.modules.common.dao.StuWeeklyPlanPerfDao;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import jp.learningpark.modules.common.service.StuWeeklyPlanPerfService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuWeeklyPlanPerfService")
@Transactional
public class StuWeeklyPlanPerfServiceImpl extends ServiceImpl<StuWeeklyPlanPerfDao, StuWeeklyPlanPerfEntity> implements StuWeeklyPlanPerfService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuWeeklyPlanPerfEntity> page = baseMapper.selectPage(
                new Query<StuWeeklyPlanPerfEntity>(params).getPage(),
                new QueryWrapper<StuWeeklyPlanPerfEntity>()
        );

        return new PageUtils(page);
    }

}
