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
import jp.learningpark.modules.common.dao.GuidReprMstDao;
import jp.learningpark.modules.common.entity.GuidReprMstEntity;
import jp.learningpark.modules.common.service.GuidReprMstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("guidReprMstService")
@Transactional
public class GuidReprMstServiceImpl extends ServiceImpl<GuidReprMstDao, GuidReprMstEntity> implements GuidReprMstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GuidReprMstEntity> page = baseMapper.selectPage(
                new Query<GuidReprMstEntity>(params).getPage(),
                new QueryWrapper<GuidReprMstEntity>()
        );

        return new PageUtils(page);
    }

}
