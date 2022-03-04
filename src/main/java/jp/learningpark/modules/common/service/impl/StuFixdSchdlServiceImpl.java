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
import jp.learningpark.modules.common.dao.StuFixdSchdlDao;
import jp.learningpark.modules.common.entity.StuFixdSchdlEntity;
import jp.learningpark.modules.common.service.StuFixdSchdlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuFixdSchdlService")
@Transactional
public class StuFixdSchdlServiceImpl extends ServiceImpl<StuFixdSchdlDao, StuFixdSchdlEntity> implements StuFixdSchdlService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuFixdSchdlEntity> page = baseMapper.selectPage(
                new Query<StuFixdSchdlEntity>(params).getPage(),
                new QueryWrapper<StuFixdSchdlEntity>()
        );

        return new PageUtils(page);
    }

}
