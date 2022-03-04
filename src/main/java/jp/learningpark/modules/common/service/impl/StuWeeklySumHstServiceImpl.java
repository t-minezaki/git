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
import jp.learningpark.modules.common.dao.StuWeeklySumHstDao;
import jp.learningpark.modules.common.entity.StuWeeklySumHstEntity;
import jp.learningpark.modules.common.service.StuWeeklySumHstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuWeeklySumHstService")
@Transactional
public class StuWeeklySumHstServiceImpl extends ServiceImpl<StuWeeklySumHstDao, StuWeeklySumHstEntity> implements StuWeeklySumHstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuWeeklySumHstEntity> page = baseMapper.selectPage(
                new Query<StuWeeklySumHstEntity>(params).getPage(),
                new QueryWrapper<StuWeeklySumHstEntity>()
        );

        return new PageUtils(page);
    }

}
