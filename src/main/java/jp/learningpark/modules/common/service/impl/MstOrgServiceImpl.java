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
import jp.learningpark.modules.common.dao.MstOrgDao;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstOrgService")
@Transactional
public class MstOrgServiceImpl extends ServiceImpl<MstOrgDao, MstOrgEntity> implements MstOrgService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstOrgEntity> page = baseMapper.selectPage(
                new Query<MstOrgEntity>(params).getPage(),
                new QueryWrapper<MstOrgEntity>()
        );

        return new PageUtils(page);
    }

}
