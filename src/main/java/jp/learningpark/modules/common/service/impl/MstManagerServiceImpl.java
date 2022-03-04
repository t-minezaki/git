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
import jp.learningpark.modules.common.dao.MstManagerDao;
import jp.learningpark.modules.common.entity.MstManagerEntity;
import jp.learningpark.modules.common.service.MstManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstManagerService")
@Transactional
public class MstManagerServiceImpl extends ServiceImpl<MstManagerDao, MstManagerEntity> implements MstManagerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstManagerEntity> page = baseMapper.selectPage(
                new Query<MstManagerEntity>(params).getPage(),
                new QueryWrapper<MstManagerEntity>()
        );

        return new PageUtils(page);
    }

}
