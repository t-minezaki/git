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
import jp.learningpark.modules.common.dao.TalkReadingStsDao;
import jp.learningpark.modules.common.entity.TalkReadingStsEntity;
import jp.learningpark.modules.common.service.TalkReadingStsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("talkReadingStsService")
@Transactional
public class TalkReadingStsServiceImpl extends ServiceImpl<TalkReadingStsDao, TalkReadingStsEntity> implements TalkReadingStsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TalkReadingStsEntity> page = baseMapper.selectPage(
                new Query<TalkReadingStsEntity>(params).getPage(),
                new QueryWrapper<TalkReadingStsEntity>()
        );

        return new PageUtils(page);
    }

}
