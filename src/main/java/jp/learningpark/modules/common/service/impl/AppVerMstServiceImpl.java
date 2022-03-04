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
import jp.learningpark.modules.common.dao.AppVerMstDao;
import jp.learningpark.modules.common.entity.AppVerMstEntity;
import jp.learningpark.modules.common.service.AppVerMstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("appVerMstService")
@Transactional
public class AppVerMstServiceImpl extends ServiceImpl<AppVerMstDao, AppVerMstEntity> implements AppVerMstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AppVerMstEntity> page = baseMapper.selectPage(
                new Query<AppVerMstEntity>(params).getPage(),
                new QueryWrapper<AppVerMstEntity>()
        );

        return new PageUtils(page);
    }

}
