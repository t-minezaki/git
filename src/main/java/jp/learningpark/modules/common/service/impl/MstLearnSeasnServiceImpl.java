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
import jp.learningpark.modules.common.dao.MstLearnSeasnDao;
import jp.learningpark.modules.common.entity.MstLearnSeasnEntity;
import jp.learningpark.modules.common.service.MstLearnSeasnService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstLearnSeasnService")
@Transactional
public class MstLearnSeasnServiceImpl extends ServiceImpl<MstLearnSeasnDao, MstLearnSeasnEntity> implements MstLearnSeasnService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstLearnSeasnEntity> page = baseMapper.selectPage(
                new Query<MstLearnSeasnEntity>(params).getPage(),
                new QueryWrapper<MstLearnSeasnEntity>()
        );

        return new PageUtils(page);
    }

}
