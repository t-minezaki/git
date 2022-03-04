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
import jp.learningpark.modules.common.dao.MstTmplateDao;
import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.common.service.MstTmplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstTmplateService")
@Transactional
public class MstTmplateServiceImpl extends ServiceImpl<MstTmplateDao, MstTmplateEntity> implements MstTmplateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstTmplateEntity> page = baseMapper.selectPage(
                new Query<MstTmplateEntity>(params).getPage(),
                new QueryWrapper<MstTmplateEntity>()
        );

        return new PageUtils(page);
    }

}
