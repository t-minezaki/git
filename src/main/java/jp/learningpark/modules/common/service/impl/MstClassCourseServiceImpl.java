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
import jp.learningpark.modules.common.dao.MstClassCourseDao;
import jp.learningpark.modules.common.entity.MstClassCourseEntity;
import jp.learningpark.modules.common.service.MstClassCourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstClassCourseService")
@Transactional
public class MstClassCourseServiceImpl extends ServiceImpl<MstClassCourseDao, MstClassCourseEntity> implements MstClassCourseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstClassCourseEntity> page = baseMapper.selectPage(
                new Query<MstClassCourseEntity>(params).getPage(),
                new QueryWrapper<MstClassCourseEntity>()
        );

        return new PageUtils(page);
    }

}
