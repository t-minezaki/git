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
import jp.learningpark.modules.common.dao.EntryExitHstDao;
import jp.learningpark.modules.common.entity.EntryExitHstEntity;
import jp.learningpark.modules.common.service.EntryExitHstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("entryExitHstService")
@Transactional
public class EntryExitHstServiceImpl extends ServiceImpl<EntryExitHstDao, EntryExitHstEntity> implements EntryExitHstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<EntryExitHstEntity> page = baseMapper.selectPage(
                new Query<EntryExitHstEntity>(params).getPage(),
                new QueryWrapper<EntryExitHstEntity>()
        );

        return new PageUtils(page);
    }

}
