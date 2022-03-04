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
import jp.learningpark.modules.common.dao.MstCrmschLearnPrdDao;
import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.common.service.MstCrmschLearnPrdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstCrmschLearnPrdService")
@Transactional
public class MstCrmschLearnPrdServiceImpl extends ServiceImpl<MstCrmschLearnPrdDao, MstCrmschLearnPrdEntity> implements MstCrmschLearnPrdService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstCrmschLearnPrdEntity> page = baseMapper.selectPage(
                new Query<MstCrmschLearnPrdEntity>(params).getPage(),
                new QueryWrapper<MstCrmschLearnPrdEntity>()
        );

        return new PageUtils(page);
    }

}
