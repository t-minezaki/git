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
import jp.learningpark.modules.common.dao.MstAddrDao;
import jp.learningpark.modules.common.entity.MstAddrEntity;
import jp.learningpark.modules.common.service.MstAddrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstAddrService")
@Transactional
public class MstAddrServiceImpl extends ServiceImpl<MstAddrDao, MstAddrEntity> implements MstAddrService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstAddrEntity> page = baseMapper.selectPage(
                new Query<MstAddrEntity>(params).getPage(),
                new QueryWrapper<MstAddrEntity>()
        );

        return new PageUtils(page);
    }

}
