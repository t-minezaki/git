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
import jp.learningpark.modules.common.dao.StuPointDao;
import jp.learningpark.modules.common.entity.StuPointEntity;
import jp.learningpark.modules.common.service.StuPointService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuPointService")
@Transactional
public class StuPointServiceImpl extends ServiceImpl<StuPointDao, StuPointEntity> implements StuPointService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuPointEntity> page = baseMapper.selectPage(
                new Query<StuPointEntity>(params).getPage(),
                new QueryWrapper<StuPointEntity>()
        );

        return new PageUtils(page);
    }

}
