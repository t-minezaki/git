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
import jp.learningpark.modules.common.dao.MstDeviceTokenDao;
import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.common.service.MstDeviceTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstDeviceTokenService")
@Transactional
public class MstDeviceTokenServiceImpl extends ServiceImpl<MstDeviceTokenDao, MstDeviceTokenEntity> implements MstDeviceTokenService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstDeviceTokenEntity> page = baseMapper.selectPage(
                new Query<MstDeviceTokenEntity>(params).getPage(),
                new QueryWrapper<MstDeviceTokenEntity>()
        );

        return new PageUtils(page);
    }

}
