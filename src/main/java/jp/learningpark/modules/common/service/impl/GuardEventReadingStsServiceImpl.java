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
import jp.learningpark.modules.common.dao.GuardEventReadingStsDao;
import jp.learningpark.modules.common.entity.GuardEventReadingStsEntity;
import jp.learningpark.modules.common.service.GuardEventReadingStsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("guardEventReadingStsService")
@Transactional
public class GuardEventReadingStsServiceImpl extends ServiceImpl<GuardEventReadingStsDao, GuardEventReadingStsEntity> implements GuardEventReadingStsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GuardEventReadingStsEntity> page = baseMapper.selectPage(
                new Query<GuardEventReadingStsEntity>(params).getPage(),
                new QueryWrapper<GuardEventReadingStsEntity>()
        );

        return new PageUtils(page);
    }

}
