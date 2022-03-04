/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */

package jp.learningpark.modules.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.Query;
import jp.learningpark.modules.job.dao.SysScheduleJobLogDao;
import jp.learningpark.modules.job.entity.SysScheduleJobLogEntity;
import jp.learningpark.modules.job.service.SysScheduleJobLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("sysScheduleJobLogService")
@Transactional
public class SysScheduleJobLogServiceImpl extends ServiceImpl<SysScheduleJobLogDao, SysScheduleJobLogEntity> implements SysScheduleJobLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysScheduleJobLogEntity> page = baseMapper.selectPage(new Query<SysScheduleJobLogEntity>(params).getPage(),
                new QueryWrapper<SysScheduleJobLogEntity>());
        return new PageUtils(page);
    }

}
