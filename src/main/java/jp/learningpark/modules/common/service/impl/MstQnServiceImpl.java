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
import jp.learningpark.modules.common.dao.MstQnDao;
import jp.learningpark.modules.common.entity.MstQnEntity;
import jp.learningpark.modules.common.service.MstQnService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstQnService")
@Transactional
public class MstQnServiceImpl extends ServiceImpl<MstQnDao, MstQnEntity> implements MstQnService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstQnEntity> page = baseMapper.selectPage(
                new Query<MstQnEntity>(params).getPage(),
                new QueryWrapper<MstQnEntity>()
        );

        return new PageUtils(page);
    }

}
