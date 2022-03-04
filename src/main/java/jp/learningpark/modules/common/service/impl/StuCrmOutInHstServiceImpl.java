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
import jp.learningpark.modules.common.dao.StuCrmOutInHstDao;
import jp.learningpark.modules.common.entity.StuCrmOutInHstEntity;
import jp.learningpark.modules.common.service.StuCrmOutInHstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuCrmOutInHstService")
@Transactional
public class StuCrmOutInHstServiceImpl extends ServiceImpl<StuCrmOutInHstDao, StuCrmOutInHstEntity> implements StuCrmOutInHstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuCrmOutInHstEntity> page = baseMapper.selectPage(
                new Query<StuCrmOutInHstEntity>(params).getPage(),
                new QueryWrapper<StuCrmOutInHstEntity>()
        );

        return new PageUtils(page);
    }

}
