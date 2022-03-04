/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jp.learningpark.modules.common.dao.EntryExitHstBakDao;
import jp.learningpark.modules.common.entity.EntryExitHstBakEntity;
import jp.learningpark.modules.common.service.EntryExitHstBakService;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service("entryExitHstBakService")
@Transactional
public class EntryExitHstBakServiceImpl extends ServiceImpl<EntryExitHstBakDao, EntryExitHstBakEntity> implements EntryExitHstBakService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<EntryExitHstBakEntity> page = baseMapper.selectPage(
                new Query<EntryExitHstBakEntity>(params).getPage(),
                new QueryWrapper<EntryExitHstBakEntity>()
        );

        return new PageUtils(page);
    }

}
