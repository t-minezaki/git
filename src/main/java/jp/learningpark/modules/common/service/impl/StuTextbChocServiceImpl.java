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
import jp.learningpark.modules.common.dao.StuTextbChocDao;
import jp.learningpark.modules.common.entity.StuTextbChocEntity;
import jp.learningpark.modules.common.service.StuTextbChocService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuTextbChocService")
@Transactional
public class StuTextbChocServiceImpl extends ServiceImpl<StuTextbChocDao, StuTextbChocEntity> implements StuTextbChocService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuTextbChocEntity> page = baseMapper.selectPage(
                new Query<StuTextbChocEntity>(params).getPage(),
                new QueryWrapper<StuTextbChocEntity>()
        );

        return new PageUtils(page);
    }

}
