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
import jp.learningpark.modules.common.dao.TalkRecordDDao;
import jp.learningpark.modules.common.entity.TalkRecordDEntity;
import jp.learningpark.modules.common.service.TalkRecordDService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("talkRecordDService")
@Transactional
public class TalkRecordDServiceImpl extends ServiceImpl<TalkRecordDDao, TalkRecordDEntity> implements TalkRecordDService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TalkRecordDEntity> page = baseMapper.selectPage(
                new Query<TalkRecordDEntity>(params).getPage(),
                new QueryWrapper<TalkRecordDEntity>()
        );

        return new PageUtils(page);
    }

}
