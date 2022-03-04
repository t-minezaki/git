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
import jp.learningpark.modules.common.dao.StuTermPlanDao;
import jp.learningpark.modules.common.entity.StuTermPlanEntity;
import jp.learningpark.modules.common.service.StuTermPlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuTermPlanService")
@Transactional
public class StuTermPlanServiceImpl extends ServiceImpl<StuTermPlanDao, StuTermPlanEntity> implements StuTermPlanService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuTermPlanEntity> page = baseMapper.selectPage(
                new Query<StuTermPlanEntity>(params).getPage(),
                new QueryWrapper<StuTermPlanEntity>()
        );

        return new PageUtils(page);
    }

}
