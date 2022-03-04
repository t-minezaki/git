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
import jp.learningpark.modules.common.dao.StuGetPointHstDao;
import jp.learningpark.modules.common.entity.StuGetPointHstEntity;
import jp.learningpark.modules.common.service.StuGetPointHstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("stuGetPointHstService")
@Transactional
public class StuGetPointHstServiceImpl extends ServiceImpl<StuGetPointHstDao, StuGetPointHstEntity> implements StuGetPointHstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StuGetPointHstEntity> page = baseMapper.selectPage(
                new Query<StuGetPointHstEntity>(params).getPage(),
                new QueryWrapper<StuGetPointHstEntity>()
        );

        return new PageUtils(page);
    }

}
