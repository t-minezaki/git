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
import jp.learningpark.modules.common.dao.StuWeeklyPerfHstDao;
import jp.learningpark.modules.common.entity.StuWeeklyPerfHstEntity;
import jp.learningpark.modules.common.service.StuWeeklyPerfHstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuWeeklyPerfHstService")
@Transactional
public class StuWeeklyPerfHstServiceImpl extends ServiceImpl<StuWeeklyPerfHstDao, StuWeeklyPerfHstEntity> implements StuWeeklyPerfHstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuWeeklyPerfHstEntity> page = baseMapper.selectPage(
                new Query<StuWeeklyPerfHstEntity>(params).getPage(),
                new QueryWrapper<StuWeeklyPerfHstEntity>()
        );

        return new PageUtils(page);
    }

}
