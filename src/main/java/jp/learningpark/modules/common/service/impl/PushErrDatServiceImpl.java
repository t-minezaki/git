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
import jp.learningpark.modules.common.dao.PushErrDatDao;
import jp.learningpark.modules.common.entity.PushErrDatEntity;
import jp.learningpark.modules.common.service.PushErrDatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("pushErrDatService")
@Transactional
public class PushErrDatServiceImpl extends ServiceImpl<PushErrDatDao, PushErrDatEntity> implements PushErrDatService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PushErrDatEntity> page = baseMapper.selectPage(
                new Query<PushErrDatEntity>(params).getPage(),
                new QueryWrapper<PushErrDatEntity>()
        );

        return new PageUtils(page);
    }

}
