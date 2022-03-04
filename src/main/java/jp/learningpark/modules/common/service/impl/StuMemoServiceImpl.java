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
import jp.learningpark.modules.common.dao.StuMemoDao;
import jp.learningpark.modules.common.entity.StuMemoEntity;
import jp.learningpark.modules.common.service.StuMemoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuMemoService")
@Transactional
public class StuMemoServiceImpl extends ServiceImpl<StuMemoDao, StuMemoEntity> implements StuMemoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuMemoEntity> page = baseMapper.selectPage(
                new Query<StuMemoEntity>(params).getPage(),
                new QueryWrapper<StuMemoEntity>()
        );

        return new PageUtils(page);
    }

}
