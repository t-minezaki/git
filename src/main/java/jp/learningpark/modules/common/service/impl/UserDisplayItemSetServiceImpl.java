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
import jp.learningpark.modules.common.dao.UserDisplayItemSetDao;
import jp.learningpark.modules.common.entity.UserDisplayItemSetEntity;
import jp.learningpark.modules.common.service.UserDisplayItemSetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("userDisplayItemSetService")
@Transactional
public class UserDisplayItemSetServiceImpl extends ServiceImpl<UserDisplayItemSetDao, UserDisplayItemSetEntity> implements UserDisplayItemSetService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserDisplayItemSetEntity> page = baseMapper.selectPage(
                new Query<UserDisplayItemSetEntity>(params).getPage(),
                new QueryWrapper<UserDisplayItemSetEntity>()
        );

        return new PageUtils(page);
    }

}
