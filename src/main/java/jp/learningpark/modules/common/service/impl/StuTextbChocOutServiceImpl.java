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
import jp.learningpark.modules.common.dao.StuTextbChocOutDao;
import jp.learningpark.modules.common.entity.StuTextbChocOutEntity;
import jp.learningpark.modules.common.service.StuTextbChocOutService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuTextbChocOutService")
@Transactional
public class StuTextbChocOutServiceImpl extends ServiceImpl<StuTextbChocOutDao, StuTextbChocOutEntity> implements StuTextbChocOutService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuTextbChocOutEntity> page = baseMapper.selectPage(
                new Query<StuTextbChocOutEntity>(params).getPage(),
                new QueryWrapper<StuTextbChocOutEntity>()
        );

        return new PageUtils(page);
    }

}
