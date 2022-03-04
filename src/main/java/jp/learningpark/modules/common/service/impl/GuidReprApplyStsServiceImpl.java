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
import jp.learningpark.modules.common.dao.GuidReprApplyStsDao;
import jp.learningpark.modules.common.entity.GuidReprApplyStsEntity;
import jp.learningpark.modules.common.service.GuidReprApplyStsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("guidReprApplyStsService")
@Transactional
public class GuidReprApplyStsServiceImpl extends ServiceImpl<GuidReprApplyStsDao, GuidReprApplyStsEntity> implements GuidReprApplyStsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GuidReprApplyStsEntity> page = baseMapper.selectPage(
                new Query<GuidReprApplyStsEntity>(params).getPage(),
                new QueryWrapper<GuidReprApplyStsEntity>()
        );

        return new PageUtils(page);
    }

}
