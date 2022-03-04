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
import jp.learningpark.modules.common.dao.StuMonthlySumHstDao;
import jp.learningpark.modules.common.entity.StuMonthlySumHstEntity;
import jp.learningpark.modules.common.service.StuMonthlySumHstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuMonthlySumHstService")
@Transactional
public class StuMonthlySumHstServiceImpl extends ServiceImpl<StuMonthlySumHstDao, StuMonthlySumHstEntity> implements StuMonthlySumHstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuMonthlySumHstEntity> page = baseMapper.selectPage(
                new Query<StuMonthlySumHstEntity>(params).getPage(),
                new QueryWrapper<StuMonthlySumHstEntity>()
        );

        return new PageUtils(page);
    }

}
