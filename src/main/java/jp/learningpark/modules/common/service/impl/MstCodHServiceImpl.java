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
import jp.learningpark.modules.common.dao.MstCodHDao;
import jp.learningpark.modules.common.entity.MstCodHEntity;
import jp.learningpark.modules.common.service.MstCodHService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstCodHService")
@Transactional
public class MstCodHServiceImpl extends ServiceImpl<MstCodHDao, MstCodHEntity> implements MstCodHService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstCodHEntity> page = baseMapper.selectPage(
                new Query<MstCodHEntity>(params).getPage(),
                new QueryWrapper<MstCodHEntity>()
        );

        return new PageUtils(page);
    }

}
