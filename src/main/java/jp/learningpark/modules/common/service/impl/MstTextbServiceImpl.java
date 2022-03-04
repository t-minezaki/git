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
import jp.learningpark.modules.common.dao.MstTextbDao;
import jp.learningpark.modules.common.entity.MstTextbEntity;
import jp.learningpark.modules.common.service.MstTextbService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstTextbService")
@Transactional
public class MstTextbServiceImpl extends ServiceImpl<MstTextbDao, MstTextbEntity> implements MstTextbService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstTextbEntity> page = baseMapper.selectPage(
                new Query<MstTextbEntity>(params).getPage(),
                new QueryWrapper<MstTextbEntity>()
        );

        return new PageUtils(page);
    }

}
