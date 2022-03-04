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
import jp.learningpark.modules.common.dao.MoveOutInHstDao;
import jp.learningpark.modules.common.entity.MoveOutInHstEntity;
import jp.learningpark.modules.common.service.MoveOutInHstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("moveOutInHstService")
@Transactional
public class MoveOutInHstServiceImpl extends ServiceImpl<MoveOutInHstDao, MoveOutInHstEntity> implements MoveOutInHstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MoveOutInHstEntity> page = baseMapper.selectPage(
                new Query<MoveOutInHstEntity>(params).getPage(),
                new QueryWrapper<MoveOutInHstEntity>()
        );

        return new PageUtils(page);
    }

}
