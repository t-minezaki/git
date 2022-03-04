/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jp.learningpark.modules.common.dao.GuardReadingStsBakDao;
import jp.learningpark.modules.common.entity.GuardReadingStsBakEntity;
import jp.learningpark.modules.common.service.GuardReadingStsBakService;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service("guardReadingStsBakService")
@Transactional
public class GuardReadingStsBakServiceImpl extends ServiceImpl<GuardReadingStsBakDao, GuardReadingStsBakEntity> implements GuardReadingStsBakService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GuardReadingStsBakEntity> page = baseMapper.selectPage(
                new Query<GuardReadingStsBakEntity>(params).getPage(),
                new QueryWrapper<GuardReadingStsBakEntity>()
        );

        return new PageUtils(page);
    }

}
