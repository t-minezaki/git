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
import jp.learningpark.modules.common.dao.StuGrpDao;
import jp.learningpark.modules.common.entity.StuGrpEntity;
import jp.learningpark.modules.common.service.StuGrpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuGrpService")
@Transactional
public class StuGrpServiceImpl extends ServiceImpl<StuGrpDao, StuGrpEntity> implements StuGrpService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuGrpEntity> page = baseMapper.selectPage(
                new Query<StuGrpEntity>(params).getPage(),
                new QueryWrapper<StuGrpEntity>()
        );

        return new PageUtils(page);
    }

}
