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
import jp.learningpark.modules.common.dao.MstUserNmFunListDao;
import jp.learningpark.modules.common.entity.MstUserNmFunListEntity;
import jp.learningpark.modules.common.service.MstUserNmFunListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstUserNmFunListService")
@Transactional
public class MstUserNmFunListServiceImpl extends ServiceImpl<MstUserNmFunListDao, MstUserNmFunListEntity> implements MstUserNmFunListService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstUserNmFunListEntity> page = baseMapper.selectPage(
                new Query<MstUserNmFunListEntity>(params).getPage(),
                new QueryWrapper<MstUserNmFunListEntity>()
        );

        return new PageUtils(page);
    }

}
