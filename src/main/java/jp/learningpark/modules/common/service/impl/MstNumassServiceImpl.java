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
import jp.learningpark.modules.common.dao.MstNumassDao;
import jp.learningpark.modules.common.entity.MstNumassEntity;
import jp.learningpark.modules.common.service.MstNumassService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstNumassService")
@Transactional
public class MstNumassServiceImpl extends ServiceImpl<MstNumassDao, MstNumassEntity> implements MstNumassService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstNumassEntity> page = baseMapper.selectPage(
                new Query<MstNumassEntity>(params).getPage(),
                new QueryWrapper<MstNumassEntity>()
        );

        return new PageUtils(page);
    }

}
