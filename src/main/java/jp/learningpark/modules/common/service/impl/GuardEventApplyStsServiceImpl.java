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
import jp.learningpark.modules.common.dao.GuardEventApplyStsDao;
import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.common.service.GuardEventApplyStsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("guardEventApplyStsService")
@Transactional
public class GuardEventApplyStsServiceImpl extends ServiceImpl<GuardEventApplyStsDao, GuardEventApplyStsEntity> implements GuardEventApplyStsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GuardEventApplyStsEntity> page = baseMapper.selectPage(
                new Query<GuardEventApplyStsEntity>(params).getPage(),
                new QueryWrapper<GuardEventApplyStsEntity>()
        );

        return new PageUtils(page);
    }

}
