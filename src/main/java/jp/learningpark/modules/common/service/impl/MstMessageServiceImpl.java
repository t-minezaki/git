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
import jp.learningpark.modules.common.dao.MstMessageDao;
import jp.learningpark.modules.common.entity.MstMessageEntity;
import jp.learningpark.modules.common.service.MstMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstMessageService")
@Transactional
public class MstMessageServiceImpl extends ServiceImpl<MstMessageDao, MstMessageEntity> implements MstMessageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstMessageEntity> page = baseMapper.selectPage(
                new Query<MstMessageEntity>(params).getPage(),
                new QueryWrapper<MstMessageEntity>()
        );

        return new PageUtils(page);
    }

}
