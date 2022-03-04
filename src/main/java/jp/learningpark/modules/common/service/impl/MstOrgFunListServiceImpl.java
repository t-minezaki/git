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
import jp.learningpark.modules.common.dao.MstOrgFunListDao;
import jp.learningpark.modules.common.entity.MstOrgFunListEntity;
import jp.learningpark.modules.common.service.MstOrgFunListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstOrgFunListService")
@Transactional
public class MstOrgFunListServiceImpl extends ServiceImpl<MstOrgFunListDao, MstOrgFunListEntity> implements MstOrgFunListService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstOrgFunListEntity> page = baseMapper.selectPage(
                new Query<MstOrgFunListEntity>(params).getPage(),
                new QueryWrapper<MstOrgFunListEntity>()
        );

        return new PageUtils(page);
    }

}
