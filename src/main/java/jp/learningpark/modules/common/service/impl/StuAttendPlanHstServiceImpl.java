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
import jp.learningpark.modules.common.dao.StuAttendPlanHstDao;
import jp.learningpark.modules.common.entity.StuAttendPlanHstEntity;
import jp.learningpark.modules.common.service.StuAttendPlanHstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuAttendPlanHstService")
@Transactional
public class StuAttendPlanHstServiceImpl extends ServiceImpl<StuAttendPlanHstDao, StuAttendPlanHstEntity> implements StuAttendPlanHstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuAttendPlanHstEntity> page = baseMapper.selectPage(
                new Query<StuAttendPlanHstEntity>(params).getPage(),
                new QueryWrapper<StuAttendPlanHstEntity>()
        );

        return new PageUtils(page);
    }

}
