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
import jp.learningpark.modules.common.dao.LateAbsHstDao;
import jp.learningpark.modules.common.entity.LateAbsHstEntity;
import jp.learningpark.modules.common.service.LateAbsHstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("lateAbsHstService")
@Transactional
public class LateAbsHstServiceImpl extends ServiceImpl<LateAbsHstDao, LateAbsHstEntity> implements LateAbsHstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LateAbsHstEntity> page = baseMapper.selectPage(
                new Query<LateAbsHstEntity>(params).getPage(),
                new QueryWrapper<LateAbsHstEntity>()
        );

        return new PageUtils(page);
    }

}
