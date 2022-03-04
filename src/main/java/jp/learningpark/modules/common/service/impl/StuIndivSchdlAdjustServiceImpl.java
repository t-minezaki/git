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
import jp.learningpark.modules.common.dao.StuIndivSchdlAdjustDao;
import jp.learningpark.modules.common.entity.StuIndivSchdlAdjustEntity;
import jp.learningpark.modules.common.service.StuIndivSchdlAdjustService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuIndivSchdlAdjustService")
@Transactional
public class StuIndivSchdlAdjustServiceImpl extends ServiceImpl<StuIndivSchdlAdjustDao, StuIndivSchdlAdjustEntity> implements StuIndivSchdlAdjustService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuIndivSchdlAdjustEntity> page = baseMapper.selectPage(
                new Query<StuIndivSchdlAdjustEntity>(params).getPage(),
                new QueryWrapper<StuIndivSchdlAdjustEntity>()
        );

        return new PageUtils(page);
    }

}
