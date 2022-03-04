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
import jp.learningpark.modules.common.dao.PerfStandMstDao;
import jp.learningpark.modules.common.entity.PerfStandMstEntity;
import jp.learningpark.modules.common.service.PerfStandMstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("perfStandMstService")
@Transactional
public class PerfStandMstServiceImpl extends ServiceImpl<PerfStandMstDao, PerfStandMstEntity> implements PerfStandMstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PerfStandMstEntity> page = baseMapper.selectPage(
                new Query<PerfStandMstEntity>(params).getPage(),
                new QueryWrapper<PerfStandMstEntity>()
        );

        return new PageUtils(page);
    }

}
