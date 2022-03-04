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
import jp.learningpark.modules.common.dao.MstLoginDao;
import jp.learningpark.modules.common.entity.MstLoginEntity;
import jp.learningpark.modules.common.service.MstLoginService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstLoginService")
@Transactional
public class MstLoginServiceImpl extends ServiceImpl<MstLoginDao, MstLoginEntity> implements MstLoginService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstLoginEntity> page = baseMapper.selectPage(
                new Query<MstLoginEntity>(params).getPage(),
                new QueryWrapper<MstLoginEntity>()
        );

        return new PageUtils(page);
    }

}
