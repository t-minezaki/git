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
import jp.learningpark.modules.common.dao.StuCrmschStsDao;
import jp.learningpark.modules.common.entity.StuCrmschStsEntity;
import jp.learningpark.modules.common.service.StuCrmschStsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuCrmschStsService")
@Transactional
public class StuCrmschStsServiceImpl extends ServiceImpl<StuCrmschStsDao, StuCrmschStsEntity> implements StuCrmschStsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuCrmschStsEntity> page = baseMapper.selectPage(
                new Query<StuCrmschStsEntity>(params).getPage(),
                new QueryWrapper<StuCrmschStsEntity>()
        );

        return new PageUtils(page);
    }

}
