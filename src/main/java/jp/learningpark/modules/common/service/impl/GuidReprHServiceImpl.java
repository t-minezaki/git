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
import jp.learningpark.modules.common.dao.GuidReprHDao;
import jp.learningpark.modules.common.entity.GuidReprHEntity;
import jp.learningpark.modules.common.service.GuidReprHService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("guidReprHService")
@Transactional
public class GuidReprHServiceImpl extends ServiceImpl<GuidReprHDao, GuidReprHEntity> implements GuidReprHService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GuidReprHEntity> page = baseMapper.selectPage(
                new Query<GuidReprHEntity>(params).getPage(),
                new QueryWrapper<GuidReprHEntity>()
        );

        return new PageUtils(page);
    }

}
