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
import jp.learningpark.modules.common.dao.AbsQaReplyDao;
import jp.learningpark.modules.common.entity.AbsQaReplyEntity;
import jp.learningpark.modules.common.service.AbsQaReplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("absQaReplyService")
@Transactional
public class AbsQaReplyServiceImpl extends ServiceImpl<AbsQaReplyDao, AbsQaReplyEntity> implements AbsQaReplyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AbsQaReplyEntity> page = baseMapper.selectPage(
                new Query<AbsQaReplyEntity>(params).getPage(),
                new QueryWrapper<AbsQaReplyEntity>()
        );

        return new PageUtils(page);
    }

}
