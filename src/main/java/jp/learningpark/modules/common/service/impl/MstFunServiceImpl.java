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
import jp.learningpark.modules.common.dao.MstFunDao;
import jp.learningpark.modules.common.entity.MstFunEntity;
import jp.learningpark.modules.common.service.MstFunService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstFunService")
@Transactional
public class MstFunServiceImpl extends ServiceImpl<MstFunDao, MstFunEntity> implements MstFunService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstFunEntity> page = baseMapper.selectPage(
                new Query<MstFunEntity>(params).getPage(),
                new QueryWrapper<MstFunEntity>()
        );

        return new PageUtils(page);
    }

}
