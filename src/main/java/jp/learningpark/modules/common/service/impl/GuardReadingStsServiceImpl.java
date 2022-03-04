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
import jp.learningpark.modules.common.dao.GuardReadingStsDao;
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.service.GuardReadingStsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("guardReadingStsService")
@Transactional
public class GuardReadingStsServiceImpl extends ServiceImpl<GuardReadingStsDao, GuardReadingStsEntity> implements GuardReadingStsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GuardReadingStsEntity> page = baseMapper.selectPage(
                new Query<GuardReadingStsEntity>(params).getPage(),
                new QueryWrapper<GuardReadingStsEntity>()
        );

        return new PageUtils(page);
    }

}
