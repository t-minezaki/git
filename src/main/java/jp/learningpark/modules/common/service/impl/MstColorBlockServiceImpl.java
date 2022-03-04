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
import jp.learningpark.modules.common.dao.MstColorBlockDao;
import jp.learningpark.modules.common.entity.MstColorBlockEntity;
import jp.learningpark.modules.common.service.MstColorBlockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstColorBlockService")
@Transactional
public class MstColorBlockServiceImpl extends ServiceImpl<MstColorBlockDao, MstColorBlockEntity> implements MstColorBlockService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstColorBlockEntity> page = baseMapper.selectPage(
                new Query<MstColorBlockEntity>(params).getPage(),
                new QueryWrapper<MstColorBlockEntity>()
        );

        return new PageUtils(page);
    }

}
