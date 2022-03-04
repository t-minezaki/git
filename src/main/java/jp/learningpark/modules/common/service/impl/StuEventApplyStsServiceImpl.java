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
import jp.learningpark.modules.common.dao.StuEventApplyStsDao;
import jp.learningpark.modules.common.entity.StuEventApplyStsEntity;
import jp.learningpark.modules.common.service.StuEventApplyStsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuEventApplyStsService")
@Transactional
public class StuEventApplyStsServiceImpl extends ServiceImpl<StuEventApplyStsDao, StuEventApplyStsEntity> implements StuEventApplyStsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuEventApplyStsEntity> page = baseMapper.selectPage(
                new Query<StuEventApplyStsEntity>(params).getPage(),
                new QueryWrapper<StuEventApplyStsEntity>()
        );

        return new PageUtils(page);
    }

}
