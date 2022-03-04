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
import jp.learningpark.modules.common.dao.GuardQaReplyDao;
import jp.learningpark.modules.common.entity.GuardQaReplyEntity;
import jp.learningpark.modules.common.service.GuardQaReplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("guardQaReplyService")
@Transactional
public class GuardQaReplyServiceImpl extends ServiceImpl<GuardQaReplyDao, GuardQaReplyEntity> implements GuardQaReplyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GuardQaReplyEntity> page = baseMapper.selectPage(
                new Query<GuardQaReplyEntity>(params).getPage(),
                new QueryWrapper<GuardQaReplyEntity>()
        );

        return new PageUtils(page);
    }

}
