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
import jp.learningpark.modules.common.dao.StuTestGoalResultDDao;
import jp.learningpark.modules.common.entity.StuTestGoalResultDEntity;
import jp.learningpark.modules.common.service.StuTestGoalResultDService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuTestGoalResultDService")
@Transactional
public class StuTestGoalResultDServiceImpl extends ServiceImpl<StuTestGoalResultDDao, StuTestGoalResultDEntity> implements StuTestGoalResultDService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuTestGoalResultDEntity> page = baseMapper.selectPage(
                new Query<StuTestGoalResultDEntity>(params).getPage(),
                new QueryWrapper<StuTestGoalResultDEntity>()
        );

        return new PageUtils(page);
    }

}
