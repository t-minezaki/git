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
import jp.learningpark.modules.common.dao.MstSurgeryFormDao;
import jp.learningpark.modules.common.entity.MstSurgeryFormEntity;
import jp.learningpark.modules.common.service.MstSurgeryFormService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstSurgeryFormService")
@Transactional
public class MstSurgeryFormServiceImpl extends ServiceImpl<MstSurgeryFormDao, MstSurgeryFormEntity> implements MstSurgeryFormService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstSurgeryFormEntity> page = baseMapper.selectPage(
                new Query<MstSurgeryFormEntity>(params).getPage(),
                new QueryWrapper<MstSurgeryFormEntity>()
        );

        return new PageUtils(page);
    }

}
