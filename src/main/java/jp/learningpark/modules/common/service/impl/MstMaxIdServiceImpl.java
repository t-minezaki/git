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
import jp.learningpark.modules.common.dao.MstMaxIdDao;
import jp.learningpark.modules.common.entity.MstMaxIdEntity;
import jp.learningpark.modules.common.service.MstMaxIdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstMaxIdService")
@Transactional
public class MstMaxIdServiceImpl extends ServiceImpl<MstMaxIdDao, MstMaxIdEntity> implements MstMaxIdService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstMaxIdEntity> page = baseMapper.selectPage(
                new Query<MstMaxIdEntity>(params).getPage(),
                new QueryWrapper<MstMaxIdEntity>()
        );

        return new PageUtils(page);
    }

}
