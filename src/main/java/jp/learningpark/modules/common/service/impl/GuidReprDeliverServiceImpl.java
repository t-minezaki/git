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
import jp.learningpark.modules.common.dao.GuidReprDeliverDao;
import jp.learningpark.modules.common.entity.GuidReprDeliverEntity;
import jp.learningpark.modules.common.service.GuidReprDeliverService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("guidReprDeliverService")
@Transactional
public class GuidReprDeliverServiceImpl extends ServiceImpl<GuidReprDeliverDao, GuidReprDeliverEntity> implements GuidReprDeliverService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GuidReprDeliverEntity> page = baseMapper.selectPage(
                new Query<GuidReprDeliverEntity>(params).getPage(),
                new QueryWrapper<GuidReprDeliverEntity>()
        );

        return new PageUtils(page);
    }

}
