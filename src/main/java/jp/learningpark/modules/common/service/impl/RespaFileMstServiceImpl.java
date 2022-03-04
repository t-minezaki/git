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
import jp.learningpark.modules.common.dao.RespaFileMstDao;
import jp.learningpark.modules.common.entity.RespaFileMstEntity;
import jp.learningpark.modules.common.service.RespaFileMstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("respaFileMstService")
@Transactional
public class RespaFileMstServiceImpl extends ServiceImpl<RespaFileMstDao, RespaFileMstEntity> implements RespaFileMstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RespaFileMstEntity> page = baseMapper.selectPage(
                new Query<RespaFileMstEntity>(params).getPage(),
                new QueryWrapper<RespaFileMstEntity>()
        );

        return new PageUtils(page);
    }

}
