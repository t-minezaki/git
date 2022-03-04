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
import jp.learningpark.modules.common.dao.MstNoticeDao;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.service.MstNoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstNoticeService")
@Transactional
public class MstNoticeServiceImpl extends ServiceImpl<MstNoticeDao, MstNoticeEntity> implements MstNoticeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstNoticeEntity> page = baseMapper.selectPage(
                new Query<MstNoticeEntity>(params).getPage(),
                new QueryWrapper<MstNoticeEntity>()
        );

        return new PageUtils(page);
    }

}
