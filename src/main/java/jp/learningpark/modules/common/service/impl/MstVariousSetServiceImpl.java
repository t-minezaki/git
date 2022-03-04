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
import jp.learningpark.modules.common.dao.MstVariousSetDao;
import jp.learningpark.modules.common.entity.MstVariousSetEntity;
import jp.learningpark.modules.common.service.MstVariousSetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstVariousSetService")
@Transactional
public class MstVariousSetServiceImpl extends ServiceImpl<MstVariousSetDao, MstVariousSetEntity> implements MstVariousSetService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstVariousSetEntity> page = baseMapper.selectPage(
                new Query<MstVariousSetEntity>(params).getPage(),
                new QueryWrapper<MstVariousSetEntity>()
        );

        return new PageUtils(page);
    }

}
