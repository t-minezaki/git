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
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstCodDService")
@Transactional
public class MstCodDServiceImpl extends ServiceImpl<MstCodDDao, MstCodDEntity> implements MstCodDService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstCodDEntity> page = baseMapper.selectPage(
                new Query<MstCodDEntity>(params).getPage(),
                new QueryWrapper<MstCodDEntity>()
        );

        return new PageUtils(page);
    }

}
