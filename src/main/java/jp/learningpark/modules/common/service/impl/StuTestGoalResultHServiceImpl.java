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
import jp.learningpark.modules.common.dao.StuTestGoalResultHDao;
import jp.learningpark.modules.common.entity.StuTestGoalResultHEntity;
import jp.learningpark.modules.common.service.StuTestGoalResultHService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuTestGoalResultHService")
@Transactional
public class StuTestGoalResultHServiceImpl extends ServiceImpl<StuTestGoalResultHDao, StuTestGoalResultHEntity> implements StuTestGoalResultHService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuTestGoalResultHEntity> page = baseMapper.selectPage(
                new Query<StuTestGoalResultHEntity>(params).getPage(),
                new QueryWrapper<StuTestGoalResultHEntity>()
        );

        return new PageUtils(page);
    }

}
