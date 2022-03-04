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
import jp.learningpark.modules.common.dao.RespaSubjtVerMstDao;
import jp.learningpark.modules.common.entity.RespaSubjtVerMstEntity;
import jp.learningpark.modules.common.service.RespaSubjtVerMstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("respaSubjtVerMstService")
@Transactional
public class RespaSubjtVerMstServiceImpl extends ServiceImpl<RespaSubjtVerMstDao, RespaSubjtVerMstEntity> implements RespaSubjtVerMstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RespaSubjtVerMstEntity> page = baseMapper.selectPage(
                new Query<RespaSubjtVerMstEntity>(params).getPage(),
                new QueryWrapper<RespaSubjtVerMstEntity>()
        );

        return new PageUtils(page);
    }

}
