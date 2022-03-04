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
import jp.learningpark.modules.common.dao.GuidReprDDao;
import jp.learningpark.modules.common.entity.GuidReprDEntity;
import jp.learningpark.modules.common.service.GuidReprDService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("guidReprDService")
@Transactional
public class GuidReprDServiceImpl extends ServiceImpl<GuidReprDDao, GuidReprDEntity> implements GuidReprDService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GuidReprDEntity> page = baseMapper.selectPage(
                new Query<GuidReprDEntity>(params).getPage(),
                new QueryWrapper<GuidReprDEntity>()
        );

        return new PageUtils(page);
    }

}
