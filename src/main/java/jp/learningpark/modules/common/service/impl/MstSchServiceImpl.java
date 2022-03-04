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
import jp.learningpark.modules.common.dao.MstSchDao;
import jp.learningpark.modules.common.entity.MstSchEntity;
import jp.learningpark.modules.common.service.MstSchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstSchService")
@Transactional
public class MstSchServiceImpl extends ServiceImpl<MstSchDao, MstSchEntity> implements MstSchService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstSchEntity> page = baseMapper.selectPage(
                new Query<MstSchEntity>(params).getPage(),
                new QueryWrapper<MstSchEntity>()
        );

        return new PageUtils(page);
    }

}
