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
import jp.learningpark.modules.common.dao.TalkRecordHDao;
import jp.learningpark.modules.common.entity.TalkRecordHEntity;
import jp.learningpark.modules.common.service.TalkRecordHService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("talkRecordHService")
@Transactional
public class TalkRecordHServiceImpl extends ServiceImpl<TalkRecordHDao, TalkRecordHEntity> implements TalkRecordHService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TalkRecordHEntity> page = baseMapper.selectPage(
                new Query<TalkRecordHEntity>(params).getPage(),
                new QueryWrapper<TalkRecordHEntity>()
        );

        return new PageUtils(page);
    }

}
