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
import jp.learningpark.modules.common.dao.MstOrgSubDwnDao;
import jp.learningpark.modules.common.entity.MstOrgSubDwnEntity;
import jp.learningpark.modules.common.service.MstOrgSubDwnService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstOrgSubDwnService")
@Transactional
public class MstOrgSubDwnServiceImpl extends ServiceImpl<MstOrgSubDwnDao, MstOrgSubDwnEntity> implements MstOrgSubDwnService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstOrgSubDwnEntity> page = baseMapper.selectPage(
                new Query<MstOrgSubDwnEntity>(params).getPage(),
                new QueryWrapper<MstOrgSubDwnEntity>()
        );

        return new PageUtils(page);
    }

}
