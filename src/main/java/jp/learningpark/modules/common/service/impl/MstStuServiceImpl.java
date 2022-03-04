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
import jp.learningpark.modules.common.dao.MstStuDao;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.service.MstStuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstStuService")
@Transactional
public class MstStuServiceImpl extends ServiceImpl<MstStuDao, MstStuEntity> implements MstStuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstStuEntity> page = baseMapper.selectPage(
                new Query<MstStuEntity>(params).getPage(),
                new QueryWrapper<MstStuEntity>()
        );

        return new PageUtils(page);
    }

}
