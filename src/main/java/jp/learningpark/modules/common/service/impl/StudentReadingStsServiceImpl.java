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
import jp.learningpark.modules.common.dao.StudentReadingStsDao;
import jp.learningpark.modules.common.entity.StudentReadingStsEntity;
import jp.learningpark.modules.common.service.StudentReadingStsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("studentReadingStsService")
@Transactional
public class StudentReadingStsServiceImpl extends ServiceImpl<StudentReadingStsDao, StudentReadingStsEntity> implements StudentReadingStsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StudentReadingStsEntity> page = baseMapper.selectPage(
                new Query<StudentReadingStsEntity>(params).getPage(),
                new QueryWrapper<StudentReadingStsEntity>()
        );

        return new PageUtils(page);
    }

}
