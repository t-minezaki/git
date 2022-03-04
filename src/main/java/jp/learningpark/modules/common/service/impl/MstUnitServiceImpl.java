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
import jp.learningpark.modules.common.dao.MstUnitDao;
import jp.learningpark.modules.common.entity.MstUnitEntity;
import jp.learningpark.modules.common.service.MstUnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstUnitService")
@Transactional
public class MstUnitServiceImpl extends ServiceImpl<MstUnitDao, MstUnitEntity> implements MstUnitService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstUnitEntity> page = baseMapper.selectPage(
                new Query<MstUnitEntity>(params).getPage(),
                new QueryWrapper<MstUnitEntity>()
        );

        return new PageUtils(page);
    }

}
