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
import jp.learningpark.modules.common.dao.AttendBookDDao;
import jp.learningpark.modules.common.entity.AttendBookDEntity;
import jp.learningpark.modules.common.service.AttendBookDService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("attendBookDService")
@Transactional
public class AttendBookDServiceImpl extends ServiceImpl<AttendBookDDao, AttendBookDEntity> implements AttendBookDService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttendBookDEntity> page = baseMapper.selectPage(
                new Query<AttendBookDEntity>(params).getPage(),
                new QueryWrapper<AttendBookDEntity>()
        );

        return new PageUtils(page);
    }

}
