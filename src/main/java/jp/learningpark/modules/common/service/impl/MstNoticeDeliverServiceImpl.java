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
import jp.learningpark.modules.common.dao.MstNoticeDeliverDao;
import jp.learningpark.modules.common.entity.MstNoticeDeliverEntity;
import jp.learningpark.modules.common.service.MstNoticeDeliverService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstNoticeDeliverService")
@Transactional
public class MstNoticeDeliverServiceImpl extends ServiceImpl<MstNoticeDeliverDao, MstNoticeDeliverEntity> implements MstNoticeDeliverService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstNoticeDeliverEntity> page = baseMapper.selectPage(
                new Query<MstNoticeDeliverEntity>(params).getPage(),
                new QueryWrapper<MstNoticeDeliverEntity>()
        );

        return new PageUtils(page);
    }

}
