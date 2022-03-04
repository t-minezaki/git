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
import jp.learningpark.modules.common.dao.MstGrpDao;
import jp.learningpark.modules.common.entity.MstGrpEntity;
import jp.learningpark.modules.common.service.MstGrpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstGrpService")
@Transactional
public class MstGrpServiceImpl extends ServiceImpl<MstGrpDao, MstGrpEntity> implements MstGrpService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstGrpEntity> page = baseMapper.selectPage(
                new Query<MstGrpEntity>(params).getPage(),
                new QueryWrapper<MstGrpEntity>()
        );

        return new PageUtils(page);
    }

}
