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
import jp.learningpark.modules.common.dao.AttendBookHDao;
import jp.learningpark.modules.common.entity.AttendBookHEntity;
import jp.learningpark.modules.common.service.AttendBookHService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("attendBookHService")
@Transactional
public class AttendBookHServiceImpl extends ServiceImpl<AttendBookHDao, AttendBookHEntity> implements AttendBookHService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttendBookHEntity> page = baseMapper.selectPage(
                new Query<AttendBookHEntity>(params).getPage(),
                new QueryWrapper<AttendBookHEntity>()
        );

        return new PageUtils(page);
    }

}
