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
import jp.learningpark.modules.common.dao.MstGuardDao;
import jp.learningpark.modules.common.entity.MstGuardEntity;
import jp.learningpark.modules.common.service.MstGuardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstGuardService")
@Transactional
public class MstGuardServiceImpl extends ServiceImpl<MstGuardDao, MstGuardEntity> implements MstGuardService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstGuardEntity> page = baseMapper.selectPage(
                new Query<MstGuardEntity>(params).getPage(),
                new QueryWrapper<MstGuardEntity>()
        );

        return new PageUtils(page);
    }

}
