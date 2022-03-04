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
import jp.learningpark.modules.common.dao.MentorStuDao;
import jp.learningpark.modules.common.entity.MentorStuEntity;
import jp.learningpark.modules.common.service.MentorStuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mentorStuService")
@Transactional
public class MentorStuServiceImpl extends ServiceImpl<MentorStuDao, MentorStuEntity> implements MentorStuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MentorStuEntity> page = baseMapper.selectPage(
                new Query<MentorStuEntity>(params).getPage(),
                new QueryWrapper<MentorStuEntity>()
        );

        return new PageUtils(page);
    }

}
