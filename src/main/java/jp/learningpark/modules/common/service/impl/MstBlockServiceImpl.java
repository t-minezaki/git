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
import jp.learningpark.modules.common.dao.MstBlockDao;
import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.common.service.MstBlockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstBlockService")
@Transactional
public class MstBlockServiceImpl extends ServiceImpl<MstBlockDao, MstBlockEntity> implements MstBlockService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstBlockEntity> page = baseMapper.selectPage(
                new Query<MstBlockEntity>(params).getPage(),
                new QueryWrapper<MstBlockEntity>()
        );

        return new PageUtils(page);
    }

}
