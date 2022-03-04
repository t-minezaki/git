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
import jp.learningpark.modules.common.dao.SysAccessPermissionDao;
import jp.learningpark.modules.common.entity.SysAccessPermissionEntity;
import jp.learningpark.modules.common.service.SysAccessPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("sysAccessPermissionService")
@Transactional
public class SysAccessPermissionServiceImpl extends ServiceImpl<SysAccessPermissionDao, SysAccessPermissionEntity> implements SysAccessPermissionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysAccessPermissionEntity> page = baseMapper.selectPage(
                new Query<SysAccessPermissionEntity>(params).getPage(),
                new QueryWrapper<SysAccessPermissionEntity>()
        );

        return new PageUtils(page);
    }

}
